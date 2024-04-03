package ui;

import java.awt.Graphics;
import java.awt.image.*;

import utilz.LoadSave;
import static utilz.Constants.UI.VolumeButtons.*;

public class VolumeButton extends PauseButton{
    private BufferedImage[] imgs;
    private BufferedImage slider;
    private int index = 0;
    private int buttonX, minX, maxX;
    private boolean mouseOver, mousePressed;
    private float floatValue = 0f;
    
    public VolumeButton(int x, int y, int width, int height) {
        super (x + width / 2, y, VOLUME_WIDTH, height);

        this.x = x; //Initializing section
        this.width = width;

        minX = x + VOLUME_WIDTH / 2; //Defining bounds of the volume slider
        maxX = x + width - VOLUME_WIDTH / 2;
        bounds.x -= VOLUME_WIDTH / 2;
        buttonX = x + width / 2;
        loadImgs();
    }

    private void loadImgs() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.VOLUME_BUTTONS);
        imgs= new BufferedImage[3];
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(i * VOLUME_DEFAULT_WIDTH, 0, VOLUME_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
        }
        slider = temp.getSubimage(3 * VOLUME_DEFAULT_WIDTH, 0, SLIDER_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
    }

    public void update() {
        index = 0;
        if (mouseOver) {
            index = 1;
        }
        if (mousePressed) {
            index = 2;
        }
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    public void draw (Graphics g) {
        g.drawImage(slider, x, y, width, height, null);
        g.drawImage(imgs[index], buttonX - VOLUME_WIDTH / 2, y, VOLUME_WIDTH, height, null);
    }

    public void changeX(int x) {
        if (x < minX) {
            buttonX = minX;
        } else if (x > maxX) {
            buttonX = maxX;
        } else {
            buttonX = x;
        }

        bounds.x = buttonX - VOLUME_WIDTH / 2;
        updateFloatValue();
    }

    private void updateFloatValue() {
        float range = maxX - minX;
        float value = buttonX - minX;
        floatValue = value / range;
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

    public float getFloatValue() {
        return this.floatValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }

}
