package main.java.com.example;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel{
    private MouseInputs mouseInputs;
    private float xDelta=100;
    private float yDelta=100;

    private float xDir=0.003f;
    private float yDir=0.003f;

    private int frames=0;
    private long lastCheck;

    private BufferedImage img;


    public GamePanel() {
        mouseInputs= new MouseInputs(this); //part of constructor
        importImg();
        setPanelSize();

        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void importImg() {
        InputStream is= getClass().getResourceAsStream("/game_sprite__player__by_monkey22mm_d6gfouv-fullview.png");
        
        try {
            img= ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPanelSize(){
        Dimension size= new Dimension(1200, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
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

    //Paint component
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
       /*  g.drawImage(null, x, y, getFocusCycleRootAncestor()); */

        frames++;
        if (System.currentTimeMillis()-lastCheck>=1000) {
            lastCheck=System.currentTimeMillis();
            System.out.println("FPS: "+ frames);
            frames=0;
        }
        repaint();
    }
}
