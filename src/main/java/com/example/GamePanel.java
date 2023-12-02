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

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private float xDelta = 100;
    private float yDelta = 100;

    private BufferedImage img;
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed=15;
    
    private int playerAction=IDLE;
    private int playerDir=-1;

    private boolean moving=false;

    public GamePanel() {
        mouseInputs = new MouseInputs(this);
        
        importImg();
        loadAnimations();

        setPanelSize();
        setFocusable(true);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void loadAnimations() {
        animations= new BufferedImage[9][6];

        for (int i=0; i<animations.length; i++) {
            for(int j=0; j<animations[i].length; j++) {
                animations[i][j]=img.getSubimage(j*64, i*40, 64, 40);
            }
        }
    }

    private void importImg() {
        InputStream is=getClass().getResourceAsStream("/player/player_sprites.png");
        try {
        img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void setPanelSize() {
        Dimension size = new Dimension(1200, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    // Getters and Setters
    public float getXDelta() {
        return this.xDelta;
    }

    public float getYDelta() {
        return this.yDelta;
    }

    public void setDirection(int direction) {
        this.playerDir=direction;
        moving=true;
    }

    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }

    public void setMoving(boolean moving) {
        this.moving=moving;
    }

    //Set Animation
    public void setAnimation() {
        if (moving) {
            playerAction=RUNNING;
        } else {
            playerAction=IDLE;
        }
    }

    public void updatePos() {
        if (moving) {
            switch (playerDir) {
                case LEFT:
                    xDelta-=5;
                    break;
                case RIGHT:
                    xDelta+=5;
                    break;
                case UP:
                    yDelta-=5;
                    break;
                case DOWN:
                    yDelta+=5;
                    break;
            }
        }
    }

    // Paint component
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateAnimationTick();

        setAnimation();
        updatePos();

        g.drawImage(animations[playerAction][aniIndex], (int) xDelta, (int) yDelta, 256, 160, null);
    }
    

    //Animation
    private void updateAnimationTick() {
        aniTick++;
        if (aniTick>=aniSpeed) {
            aniTick=0;
            aniIndex++;
            if (aniIndex>=GetSpriteAmount(playerAction)) {
                aniIndex=0;
            }
        }
    }
}
