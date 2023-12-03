package main.java.com.example;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import java.awt.Dimension;
import java.awt.Graphics;
import static main.java.com.example.Game.GAME_WIDTH;
import static main.java.com.example.Game.GAME_HEIGHT;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private Game game;

    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this);
        this.game=game;

        setPanelSize();
        setFocusable(true);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public Game getGame() {
        return this.game;
    }
    
    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("Width: "+ GAME_WIDTH+ "Height: "+GAME_HEIGHT);
    }

    // Paint component
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    
}
