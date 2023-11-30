package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.java.com.example.GamePanel;

public class MouseInputs implements MouseListener,MouseMotionListener{

    private GamePanel gamePanel;

    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel=gamePanel;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("The mouse was dragged!");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        gamePanel.setRectPos(e.getX(), e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("The mouse was clicked!");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("The mouse was pressed!");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("The mouse was released!");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       System.out.println("Entered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Exited");
    }
    
}
