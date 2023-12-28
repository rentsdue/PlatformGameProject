package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Player;
import levels.LevelManager;
import main.java.com.example.*;
import ui.PauseOverlay;
import utilz.LoadSave;

public class Playing extends State implements Statemethods {
	private Player player;
	private LevelManager levelManager;
	private PauseOverlay pauseOverlay;
	private boolean paused=false; //Shows the pause screen, but set to false so that it doesn't appear immediately

	private int xLvlOffset;
	private int leftBorder= (int) (0.2*Game.GAME_WIDTH);
	private int rightBorder=  (int) (0.8*Game.GAME_WIDTH); //If these need to be modified just in case, the 2 numbers need to add up to 1.
	private int lvlTilesWide= LoadSave.GetLevelData()[0].length;
	private int maxTilesOffset= lvlTilesWide-Game.TILES_IN_WIDTH;
	private int maxLvlOffsetX= maxTilesOffset*Game.TILES_SIZE;

	public Playing(Game game) {
		super(game);
		initClasses();
	}

	private void initClasses() {
		levelManager = new LevelManager(game);
		player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE));
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
		pauseOverlay= new PauseOverlay(this);
	}

	@Override
	public void update() {
		if (!paused) {
			levelManager.update();
			player.update();
			checkCloseToBorder();
		} else {
			pauseOverlay.update();
		}
	}

	private void checkCloseToBorder() {
		int playerX= (int) player.getHitBox().x;
		int diff= playerX-xLvlOffset;

		//Checks close to border
		if (diff> rightBorder) {
			xLvlOffset +=diff-rightBorder;
		} else if (diff< leftBorder) {
			xLvlOffset +=diff-leftBorder;
		}

		if (xLvlOffset> maxLvlOffsetX) {
			xLvlOffset= maxLvlOffsetX;
		} else if (xLvlOffset <0) {
			xLvlOffset=0;
		}
	}

	@Override
	public void draw(Graphics g) {
		levelManager.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);

		if (paused) {
			g.setColor(new Color(0, 0, 0, 100));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		}
	}

	public void unpauseGame() {
		paused=false;
	}

	//Key/Mouse Events

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			player.setAttacking(true);
	}

	public void mouseDragged(MouseEvent e) {
		if (paused) {
			pauseOverlay.mouseDragged(e);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
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
			paused= !paused;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
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

	@Override
	public void mousePressed(MouseEvent e) {
		if (paused) {
			pauseOverlay.mousePressed(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (paused) {
			pauseOverlay.mouseReleased(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (paused) {
			pauseOverlay.mouseMoved(e);
		}
	}

	public void windowFocusLost() {
		player.resetDirBooleans();
	}

	public Player getPlayer() {
		return this.player;
	}

}