package ui;

import static utilz.Constants.UI.UrmButtons.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import audio.AudioPlayer;
import gamestates.*;
import main.Game;
import utilz.LoadSave;

public class PauseOverlay {
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH; //Sizes of the buttons
    private UrmButton menuB, replayB, unpauseB; //Menu, replay, and unpause buttons
    private AudioOptions audioOptions;
    private Playing playing;
    
    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        audioOptions = playing.getGame().getAudioOptions();
        createUrmButtons();
    }

    private void createUrmButtons() {
        int menuX = (int) (313 * Game.SCALE);
        int replayX = (int) (387 * Game.SCALE);
        int unpauseX = (int) (462 * Game.SCALE);
        int bY = (int) (325 * Game.SCALE);

        menuB = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
        replayB = new UrmButton(replayX, bY, URM_SIZE, URM_SIZE, 1);
        unpauseB = new UrmButton(unpauseX, bY, URM_SIZE, URM_SIZE, 0);
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas (LoadSave.PAUSE_BACKGROUND);
        bgW = (int) (backgroundImg.getWidth() * Game.SCALE);
        bgH = (int) (backgroundImg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (25 * Game.SCALE);
        
    }

    public void update() {
        menuB.update();
        replayB.update();
        unpauseB.update();
        audioOptions.update();
    }

    public void draw(Graphics g) {
        //Background
        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);

        //Everything else
        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);

        //Stuff audioOptions will draw
        audioOptions.draw(g);
    }

    //GameState changes
    public void returnToMainPage() {
        playing.resetAll();
        playing.setGamestate(Gamestate.MENU);
        playing.unpauseGame();
        playing.getGame().getAudioPlayer().stopSong();
        playing.getGame().getAudioPlayer().playSong(AudioPlayer.MAIN_MUSIC);
    }

    public void restartLevel() {
        playing.resetAll();
        playing.unpauseGame();
        playing.getGame().getAudioPlayer().stopSong();
        playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLevelIndex());
    }

    //Mouse and Key Event Handlers
    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);
    }

    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);

        if (isIn(e, menuB)) {
            menuB.setMouseOver(true);
        } else if (isIn(e, replayB)) {
            replayB.setMouseOver(true);
        } else if (isIn(e, unpauseB)) {
            unpauseB.setMouseOver(true);
        } else {
            audioOptions.mouseMoved(e);
        }
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuB)) {
            menuB.setMousePressed(true);
        } else if (isIn(e, replayB)) {
            replayB.setMousePressed(true);
        } else if (isIn(e, unpauseB)) {
            unpauseB.setMousePressed(true);
        } else {
            audioOptions.mousePressed(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuB)) {
            if (menuB.isMousePressed()) {
                returnToMainPage();
            }
        }  else if (isIn(e, replayB)) {
            if (replayB.isMousePressed()) {
                restartLevel();
            }
        }  else if (isIn(e, unpauseB)) {
            if (unpauseB.isMousePressed()) {
                playing.unpauseGame();
            }
        } else {
            audioOptions.mouseReleased(e);
        }
        menuB.resetBools();
        replayB.resetBools();
        unpauseB.resetBools();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                restartLevel();
                break;
            case KeyEvent.VK_ESCAPE:
                playing.unpauseGame();
                break;
        }
    }

    public boolean isIn(MouseEvent e, PauseButton b) {
        return (b.getBounds().contains(e.getX(), e.getY()));
    }

}
