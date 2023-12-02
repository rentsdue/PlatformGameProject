package main.java.com.example;
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
