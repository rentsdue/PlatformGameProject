package main.java.com.example;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class GamePanel extends JPanel{
    private MouseInputs mouseInputs;
    private float xDelta=100;
    private float yDelta=100;

    private float xDir=0.003f;
    private float yDir=0.003f;

    private int frames=0;
    private long lastCheck;
    private Color color=new Color(150, 20, 90);

    private Random random;

    public GamePanel() {
        random= new Random();
        mouseInputs= new MouseInputs(this); //part of constructor

        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    //Getters

    public float getXDelta() {
        return this.xDelta;
    }

    public float getYDelta() {
        return this.yDelta;
    }

    public float getXDir() {
        return this.xDir;
    }

    public float getYDir() {
        return this.yDir;
    }

    public int getFrames() {
        return this.frames;
    }

    public long getLastCheck() {
        return this.lastCheck;
    }

    //Changing values for the rectangle

    public void changeXDelta(int value) {
        this.xDelta+=value;
    }

    public void changeYDelta (int value) {
        this.yDelta+=value;;
    }

    public void setRectPos(int x, int y) {
        this.xDelta=x;
        this.yDelta=y;
    }

    public void updateRectangle() {
        xDelta+=xDir;
        if (xDelta>GameWindow.getWidth() || xDelta<0) {
            xDir*=-1;
            color=getRndColor();
        }
        yDelta+=yDir;
        if (yDelta>GameWindow.getHeight() || yDelta<0) {
            yDir*=-1;
            color=getRndColor();
        }
    }

    //Paint component

    private Color getRndColor() {
        int r=random.nextInt(255);
        int g=random.nextInt(255);
        int b=random.nextInt(255);
        return new Color(r, g, b);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        updateRectangle();
        g.setColor(color);
        g.fillRect((int) xDelta, (int) yDelta, 200, 50);

        frames++;
        if (System.currentTimeMillis()-lastCheck>=1000) {
            lastCheck=System.currentTimeMillis();
            System.out.println("FPS: "+ frames);
            frames=0;
        }
        repaint();
    }
}
