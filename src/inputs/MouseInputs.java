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
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
       
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton()==MouseEvent.BUTTON1) {
            gamePanel.getGame().getPlayer().setAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
  
    }

    @Override
    public void mouseReleased(MouseEvent e) {
  
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    
}
