package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import entities.*;
import levels.LevelManager;
import main.java.com.example.Game;
import objects.*;
import ui.*;
import utilz.LoadSave;

public class Playing extends State implements Statemethods {
	private Player player;
	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private ObjectManager objectManager;
	private PauseOverlay pauseOverlay;
	private GameOverOverlay gameOverOverlay;
	private GameCompletedOverlay gameCompletedOverlay;
	private LevelCompletedOverlay levelCompletedOverlay;
	private boolean paused = false;
	private boolean drawShip = false;
	private BufferedImage[] shipImgs;

	private int xLvlOffset;
	private int leftBorder = (int) (0.5 * Game.GAME_WIDTH); //This can be modified so long as they add up to 1
	private int rightBorder = (int) (0.5 * Game.GAME_WIDTH);
	private int maxLvlOffsetX;
	private int shipAni, shipTick, shipDir = 1;
	private float shipHeightDelta, shipHeightChange = 0.05f * Game.SCALE;

	private BufferedImage backgroundImg;

	private boolean gameOver, lvlCompleted, gameCompleted, playerDying;

	public Playing(Game game) {
		super(game);
		initClasses();
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.CAVE_BACKGROUND);
		shipImgs = new BufferedImage[4];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.WARSHIP);
		for (int i = 0; i < shipImgs.length; i++)
			shipImgs[i] = temp.getSubimage(i * 78, 0, 78, 72);
		calcLvlOffset();
		loadStartLevel();
	}

	public void loadNextLevel() {
		levelManager.setLevelIndex(levelManager.getLevelIndex() + 1);
		levelManager.loadNextLevel();
		player.setSpawn(levelManager.getCurrentLevel().getSpawnPoint());
		resetAll();
		if (levelManager.getLevelIndex() == 2) {
			drawShip = true;
		}
	}

	private void loadStartLevel() {
		enemyManager.loadEnemies(levelManager.getCurrentLevel());
		objectManager.loadObjects(levelManager.getCurrentLevel());
	}

	private void calcLvlOffset() {
		maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
	}

	private void initClasses() {
		levelManager = new LevelManager(game);
		enemyManager = new EnemyManager(this);
		objectManager = new ObjectManager(this);

		player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE), this);
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
		player.setSpawn(levelManager.getCurrentLevel().getSpawnPoint());

		pauseOverlay = new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
		levelCompletedOverlay = new LevelCompletedOverlay(this);
	}

	@Override
	public void update() {
		if (paused)
			pauseOverlay.update();
		else if (lvlCompleted)
			levelCompletedOverlay.update();
		else if (gameOver)
			gameOverOverlay.update();
		else if (playerDying)
			player.update();
		else {
			levelManager.update();
			objectManager.update(levelManager.getCurrentLevel().getLevelData(), player);
			player.update();
			enemyManager.update(levelManager.getCurrentLevel().getLevelData());
			checkCloseToBorder();
			if (drawShip)
				updateShipAni();
		}
	}

	private void checkCloseToBorder() {
		int playerX = (int) player.getHitBox().x;
		int diff = playerX - xLvlOffset;

		if (diff > rightBorder)
			xLvlOffset += diff - rightBorder;
		else if (diff < leftBorder)
			xLvlOffset += diff - leftBorder;

		if (xLvlOffset > maxLvlOffsetX)
			xLvlOffset = maxLvlOffsetX;
		else if (xLvlOffset < 0)
			xLvlOffset = 0;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		if (drawShip)
			g.drawImage(shipImgs[shipAni], (int) (100 * Game.SCALE) - xLvlOffset, (int) ((288 * Game.SCALE) + shipHeightDelta), (int) (78 * Game.SCALE), (int) (72 * Game.SCALE), null);
			levelManager.draw(g, xLvlOffset);
			objectManager.draw(g, xLvlOffset);
			enemyManager.draw(g, xLvlOffset);
			player.render(g, xLvlOffset);
			objectManager.drawBackgroundTrees(g, xLvlOffset);
		if (paused) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		} else if (gameOver)
			gameOverOverlay.draw(g);
		else if (lvlCompleted)
			levelCompletedOverlay.draw(g);
	}

	private void updateShipAni() {
		shipTick++;
		if (shipTick >= 35) {
			shipTick = 0;
			shipAni++;
			if (shipAni >= 4)
				shipAni = 0;
		}

		shipHeightDelta += shipHeightChange * shipDir;
		shipHeightDelta = Math.max(Math.min(10 * Game.SCALE, shipHeightDelta), 0);

		if (shipHeightDelta == 0)
			shipDir = 1;
		else if (shipHeightDelta == 10 * Game.SCALE)
			shipDir = -1;

	}

	public void resetAll() {
		gameOver = false;
		paused = false;
		lvlCompleted = false;
		playerDying = false;
		player.resetAll();
		enemyManager.resetAllEnemies();
		objectManager.resetAllObjects();
	}

	public void checkObjectHit(Rectangle2D.Float attackBox) {
		objectManager.checkObjectHit(attackBox);
	}

	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		enemyManager.checkEnemyHit(attackBox);
	}

	public void checkPotionTouched(Rectangle2D.Float hitBox) {
		objectManager.checkObjectTouched(hitBox);
	}

	public void checkSpikesTouched(Player p) {
        objectManager.checkSpikesTouched(p);
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!gameOver) {
			if (e.getButton() == MouseEvent.BUTTON1)
				player.setAttacking(true);
			else if (e.getButton() == MouseEvent.BUTTON3)
				player.powerAttack();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!gameOver && !gameCompleted && !lvlCompleted)
		switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setLeft(true);
				break;
			case KeyEvent.VK_LEFT:
				player.setLeft(true);
				break;
			case KeyEvent.VK_D:
				player.setRight(true);
				break;
			case KeyEvent.VK_RIGHT:
				player.setRight(true);
				break;
			case KeyEvent.VK_SPACE:
				player.setAttacking(true);
				break;
			case KeyEvent.VK_UP:
				player.setJump(true);
				break;
			case KeyEvent.VK_W:
				player.setJump(true);
				break;
			case KeyEvent.VK_ESCAPE:
				paused = !paused;
				break;
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (!gameOver && !gameCompleted && !lvlCompleted)
		switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setLeft(false);
				break;
			case KeyEvent.VK_LEFT:
				player.setLeft(false);
				break;
			case KeyEvent.VK_D:
				player.setRight(false);
				break;
			case KeyEvent.VK_RIGHT:
				player.setRight(false);
				break;
			case KeyEvent.VK_UP:
				player.setJump(false);
				break;
			case KeyEvent.VK_W:
				player.setJump(false);
				break;
			}
	}

	public void mouseDragged(MouseEvent e) {
		if (!gameOver && !gameCompleted && !lvlCompleted)
			if (paused)
				pauseOverlay.mouseDragged(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (gameOver)
			gameOverOverlay.mousePressed(e);
		else if (paused)
			pauseOverlay.mousePressed(e);
		else if (lvlCompleted)
			levelCompletedOverlay.mousePressed(e);
		else if (gameCompleted)
			gameCompletedOverlay.mousePressed(e);

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (gameOver)
			gameOverOverlay.mouseReleased(e);
		else if (paused)
			pauseOverlay.mouseReleased(e);
		else if (lvlCompleted)
			levelCompletedOverlay.mouseReleased(e);
		else if (gameCompleted)
			gameCompletedOverlay.mouseReleased(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (gameOver)
			gameOverOverlay.mouseMoved(e);
		else if (paused)
			pauseOverlay.mouseMoved(e);
		else if (lvlCompleted)
			levelCompletedOverlay.mouseMoved(e);
		else if (gameCompleted)
			gameCompletedOverlay.mouseMoved(e);
	}

	public void setLevelCompleted(boolean levelCompleted) {
		game.getAudioPlayer().lvlCompleted();
		if (levelManager.getLevelIndex() + 1 >= levelManager.getAmountOfLevels()) {
			// No more levels
			gameCompleted = true;
			levelManager.setLevelIndex(0);
			levelManager.loadNextLevel();
			resetAll();
			return;
		}
		this.lvlCompleted = levelCompleted;
	}

	public void unpauseGame() {
		paused = false;
	}

	public void windowFocusLost() {
		player.resetDirBooleans();
	}

	//Getters and setters
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public void resetGameCompleted() {
		this.gameCompleted = false;
	}

	public void setGameCompleted() {
		this.gameCompleted = true;
	}

	public void setMaxLvlOffset(int lvlOffset) {
		this.maxLvlOffsetX = lvlOffset;
	}

	public Player getPlayer() {
		return this.player;
	}

	public EnemyManager getEnemyManager() {
		return this.enemyManager;
	}

	public ObjectManager getObjectManager() {
		return this.objectManager;
	}

	public LevelManager getLevelManager() {
		return this.levelManager;
	}

	public void setLevelManager(LevelManager levelManager) {
		this.levelManager = levelManager;
	}

    public void setPlayerDying(boolean playerDying) {
        this.playerDying = playerDying;
    }

}