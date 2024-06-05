package ui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static utilz.Constants.UI.UrmButtons.*;

import java.awt.Color;
import java.awt.image.BufferedImage;

import audio.AudioPlayer;
import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;


public class GameOverOverlay {
    
    private Playing playing;
    private BufferedImage img;
    private int imgX, imgY, imgW, imgH;
    private UrmButton menu, play;

    public GameOverOverlay(Playing playing) {
        this.playing = playing;
        createImg();
        createButtons();
    }

    private void createButtons() {
        int menuX = (int) (330 * Game.SCALE);
		int playX = (int) (445 * Game.SCALE);
		int y = (int) (195 * Game.SCALE);
		play = new UrmButton(playX, y, URM_SIZE, URM_SIZE, 0);
		menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
    }

    private void createImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.DEATH_SCREEN);
        imgW = (int) (img.getWidth() * Game.SCALE);
        imgH = (int) (img.getHeight() * Game.SCALE);
        imgX = (int) (Game.GAME_WIDTH / 2 - imgW / 2);
        imgY = (int) (100 * Game.SCALE);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        g.drawImage(img, imgX, imgY, imgW, imgH, null);
        menu.draw(g);
        play.draw(g);
    }

    public void update() {
        menu.update();
        play.update();
    }

    //Returning to various gamestates
    public void returnToMainPage() {
        playing.resetAll();
        playing.getGame().getAudioPlayer().stopEffect(AudioPlayer.GAMEOVER);
        playing.setGamestate(Gamestate.MENU);
        playing.getGame().getAudioPlayer().playSong(AudioPlayer.MAIN_MUSIC);
    }

    public void restartLevel() {
        playing.getGame().getAudioPlayer().stopEffect(AudioPlayer.GAMEOVER);
        playing.resetAll();
        playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLevelIndex()); 
    }

    //Mouse and Key Events

    private boolean isIn(UrmButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

	public void mouseMoved(MouseEvent e) {
		play.setMouseOver(false);
		menu.setMouseOver(false);

		if (isIn(menu, e))
			menu.setMouseOver(true);
		else if (isIn(play, e))
			play.setMouseOver(true);
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(menu, e)) {
			if (menu.isMousePressed()) {
				returnToMainPage();
			}
		} else if (isIn(play, e))
			if (play.isMousePressed()) {
                restartLevel();
            }
        menu.resetBools();
		play.resetBools();
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(menu, e))
			menu.setMousePressed(true);
		else if (isIn(play, e))
			play.setMousePressed(true);
	}

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                restartLevel();
                break;
            case KeyEvent.VK_ESCAPE:
                returnToMainPage();
                break;
        }
    }
}
