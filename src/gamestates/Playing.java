package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Player;
import levels.LevelManager;
import main.java.com.example.*;
import ui.PauseOverlay;

public class Playing extends State implements Statemethods {
	private Player player;
	private LevelManager levelManager;
	private PauseOverlay pauseOverlay;
	private boolean paused=true; //Shows the pause screen

	public Playing(Game game) {
		super(game);
		initClasses();
	}

	private void initClasses() {
		levelManager = new LevelManager(game);
		player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE));
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
		pauseOverlay= new PauseOverlay();
	}

	@Override
	public void update() {
		levelManager.update();
		player.update();
		pauseOverlay.update();
	}

	@Override
	public void draw(Graphics g) {
		levelManager.draw(g);
		player.render(g);
		pauseOverlay.draw(g);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			player.setAttacking(true);
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
		case KeyEvent.VK_BACK_SPACE:
			Gamestate.state = Gamestate.MENU;
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
		return player;
	}

}