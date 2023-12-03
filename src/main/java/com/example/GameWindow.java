package main.java.com.example;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class GameWindow {
    private JFrame jframe;
    private static int width = 400;
    private static int height = 400;

    public GameWindow(GamePanel gamePanel) {
        jframe = new JFrame();

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setLocationRelativeTo(null);
        jframe.setResizable(false);
        jframe.pack();
        jframe.setVisible(true);
        jframe.addWindowFocusListener(new WindowFocusListener() {

            @Override //Work on this later
            public void windowGainedFocus(WindowEvent e) {
                
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }
            
        }); {

        };
    }

    public JFrame getJFrame() {
        return jframe;
    }

    // Getters
    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }
}
