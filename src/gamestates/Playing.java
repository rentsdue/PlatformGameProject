package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.java.com.example.Game;
import objects.ObjectManager;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;

public class Playing extends State implements Statemethods {
	private Player player;
	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private ObjectManager objectManager;
	private PauseOverlay pauseOverlay;
	private GameOverOverlay gameOverOverlay;
	private LevelCompletedOverlay levelCompletedOverlay;
	private boolean paused = false;

	private int xLvlOffset;
	private int leftBorder = (int) (0.5 * Game.GAME_WIDTH); //This can be modified so long as they add up to 1
	private int rightBorder = (int) (0.5 * Game.GAME_WIDTH);
	private int maxLvlOffsetX;

	private BufferedImage backgroundImg;

	private boolean gameOver;
	private boolean lvlCompleted;

	public Playing(Game game) {
		super(game);
		initClasses();
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.CAVE_BACKGROUND);
		calcLvlOffset();
		loadStartLevel();
	}

	public void loadNextLevel() {
		resetAll();
		levelManager.loadNextLevel();
		player.setSpawn(levelManager.getCurrentLevel().getSpawnPoint());
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
		if (paused) {
			pauseOverlay.update();
		} else if (lvlCompleted) {
			levelCompletedOverlay.update();
		} else if (!gameOver) {
			levelManager.update();
			objectManager.update();
			player.update();
			enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
			checkCloseToBorder();
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
		levelManager.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);
		enemyManager.draw(g, xLvlOffset);
		objectManager.draw(g, xLvlOffset);

		if (paused) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		} else if (gameOver)
			gameOverOverlay.draw(g);
		else if (lvlCompleted)
			levelCompletedOverlay.draw(g);
	}

	public void resetAll() {
		gameOver = false;
		paused = false;
		lvlCompleted = false;
		player.resetAll();
		enemyManager.resetAllEnemies();
		objectManager.resetAllObjects();
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
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

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!gameOver)
			if (e.getButton() == MouseEvent.BUTTON1)
				player.setAttacking(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (gameOver)
			gameOverOverlay.keyPressed(e);
		else
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
				player.setJump(true);
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
		if (!gameOver)
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
			case KeyEvent.VK_SPACE:
				player.setJump(false);
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
		if (!gameOver)
			if (paused)
				pauseOverlay.mouseDragged(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				pauseOverlay.mousePressed(e);
			else if (lvlCompleted)
				levelCompletedOverlay.mousePressed(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				pauseOverlay.mouseReleased(e);
			else if (lvlCompleted)
				levelCompletedOverlay.mouseReleased(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				pauseOverlay.mouseMoved(e);
			else if (lvlCompleted)
				levelCompletedOverlay.mouseMoved(e);
		}
	}

	public void setLevelCompleted(boolean levelCompleted) {
		this.lvlCompleted = levelCompleted;
	}

	public void setMaxLvlOffset(int lvlOffset) {
		this.maxLvlOffsetX = lvlOffset;
	}

	public void unpauseGame() {
		paused = false;
	}

	public void windowFocusLost() {
		player.resetDirBooleans();
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

}