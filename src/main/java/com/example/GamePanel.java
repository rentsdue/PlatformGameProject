package main.java.com.example;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import java.awt.Graphics;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel{
    private MouseInputs mouseInputs;
    private int xDelta=100;
    private int yDelta=100;
    
    public GamePanel() {
        mouseInputs= new MouseInputs(this); //part of constructor

        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void changeXDelta(int value) {
        this.xDelta+=value;
        repaint();
    }

    public void changeYDelta (int value) {
        this.yDelta+=value;
        repaint();
    }

    public void setRectPos(int x, int y) {
        this.xDelta=x;
        this.yDelta=y;
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(xDelta, yDelta, 200, 50);
    }
}
