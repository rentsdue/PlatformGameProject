package ui;
import java.awt.Rectangle;

public class PauseButton {
    protected int x, y, width, height; //Only stores size/shape data, no images required
    protected Rectangle bounds;

    public PauseButton(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        createBounds();
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    private void createBounds() {
        bounds= new Rectangle(x, y, width, height);
    }
}
