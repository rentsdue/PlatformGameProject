package ui;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

import utilz.LoadSave;
import static utilz.Constants.UI.PauseButtons.*;

public class SoundButton extends PauseButton{
    private BufferedImage[][] soundImgs;
    private boolean mouseOver, mousePressed;
    private boolean muted; //Determines if pause button is on first or second row on image array
    private int rowIndex, columnIndex;

    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadSoundImgs();
    }
    
    private void loadSoundImgs() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SOUND_BUTTONS);
        soundImgs = new BufferedImage[2][3];
        for (int j = 0; j < soundImgs.length; j++) {
            for (int i = 0; i<soundImgs[j].length; i++) {
                soundImgs[j][i] = temp.getSubimage(i*SOUND_SIZE_DEFAULT, j*SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
            }
        }
    }

    public void update() {
        if (muted) { 
            rowIndex = 1;
        } else {
            rowIndex = 0;
        }

        columnIndex = 0; //Allows for manipulation of buttons
        if (mouseOver) {
            columnIndex = 1;
        } 

        if (mousePressed) {
            columnIndex = 2;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(soundImgs[rowIndex][columnIndex], x, y, width, height, null);
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    //Getters and setters

    public boolean isMouseOver() {
        return this.mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMuted() {
        return this.muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }


}
