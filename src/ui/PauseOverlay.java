package ui;

import static utilz.Constants.UI.PauseButtons.SOUND_SIZE;
import static utilz.Constants.UI.UrmButtons.URM_SIZE;
import static utilz.Constants.UI.VolumeButtons.VOLUME_HEIGHT;
import static utilz.Constants.UI.VolumeButtons.SLIDER_WIDTH;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gamestates.*;

import java.awt.event.*;

import main.java.com.example.Game;
import utilz.LoadSave;

public class PauseOverlay {
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH; //Sizes of the buttons
    private SoundButton musicButton, sfxButton; //Creates the music and sound effects buttons
    private UrmButton menuB, replayB, unpauseB; //Menu, replay, and unpause buttons
    private VolumeButton volumeB;
    private Playing playing;
    
    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        createSoundButtons();
        createUrmButtons();
        createVolumeButton();
    }

    //How the Sound and URM buttons are made
    private void createVolumeButton() {
        int vX = (int) (309 * Game.SCALE);
        int vY = (int) (278 * Game.SCALE);
        volumeB = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    private void createUrmButtons() {
        int menuX = (int) (313 * Game.SCALE);
        int replayX = (int) (387 * Game.SCALE);
        int unpauseX = (int) (462 * Game.SCALE);
        int bY = (int) (325 * Game.SCALE);

        menuB= new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
        replayB= new UrmButton(replayX, bY, URM_SIZE, URM_SIZE, 1);
        unpauseB= new UrmButton(unpauseX, bY, URM_SIZE, URM_SIZE, 0);
    }

    private void createSoundButtons() {
        int soundX = (int) (450 * Game.SCALE);
        int musicY = (int) (140 * Game.SCALE);
        int sfxY = (int) (186 * Game.SCALE);
        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas (LoadSave.PAUSE_BACKGROUND);
        bgW = (int) (backgroundImg.getWidth() * Game.SCALE);
        bgH = (int) (backgroundImg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int)(25*Game.SCALE);
        
    }

    public void update() {
        musicButton.update();
        sfxButton.update();
        menuB.update();
        replayB.update();
        unpauseB.update();
        volumeB.update();
    }

    public void draw(Graphics g) {
        //Background
        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);
    
        //Sound buttons
        musicButton.draw(g);
        sfxButton.draw(g);

        //Urm buttons
        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);

        //Volume slider
        volumeB.draw(g);
    }

    public void mouseDragged(MouseEvent e) {
        if (volumeB.isMousePressed()) {
            volumeB.changeX(e.getX());
        }
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);
        volumeB.setMouseOver(false);

        if (isIn(e, musicButton)) {
            musicButton.setMouseOver(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMouseOver(true);
        } else if (isIn(e, menuB)) {
            menuB.setMouseOver(true);
        } else if (isIn(e, replayB)) {
            replayB.setMouseOver(true);
        } else if (isIn(e, unpauseB)) {
            unpauseB.setMouseOver(true);
        } else if (isIn(e, volumeB)) {
            volumeB.setMouseOver(true);
        }
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        } else if (isIn(e, menuB)) {
            menuB.setMousePressed(true);
        } else if (isIn(e, replayB)) {
            replayB.setMousePressed(true);
        } else if (isIn(e, unpauseB)) {
            unpauseB.setMousePressed(true);
        } else if (isIn(e, volumeB)) {
            volumeB.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, musicButton)) {
            if (musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
            }
        } else if (isIn(e, sfxButton)) {
            if (sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
            }
        } else if (isIn(e, menuB)) {
            if (menuB.isMousePressed()) {
                Gamestate.state=Gamestate.MENU;
                playing.unpauseGame();
            }
        }  else if (isIn(e, replayB)) {
            if (replayB.isMousePressed()) {
                playing.resetAll();
                playing.unpauseGame();
            }
        }  else if (isIn(e, unpauseB)) {
            if (unpauseB.isMousePressed()) {
                playing.unpauseGame();
            }
        }

        musicButton.resetBools();
        sfxButton.resetBools();
        menuB.resetBools();
        replayB.resetBools();
        unpauseB.resetBools();
        volumeB.resetBools();
    }

    private boolean isIn(MouseEvent e, PauseButton b) {
        return (b.getBounds().contains(e.getX(), e.getY()));
    }

}
