package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;

import main.java.com.example.Game;
import utilz.LoadSave;

import static utilz.Constants.PlayerConstants.ATTACK_1;
import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.HelpMethods.*;
import static utilz.Constants.PlayerConstants.IDLE;
import static utilz.Constants.PlayerConstants.RUNNING;

public class Player extends Entity {
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed=25;
    private int playerAction=IDLE;
    private boolean moving=false, attacking=false;
    private boolean left, up, right, down, jump;
    private float playerSpeed=2.0f;
    private int[][] lvlData;

    //Jumping/Gravity
    private float xDrawOffset= 21*Game.SCALE;
    private float yDrawOffset= 4*Game.SCALE;
    private float airSpeed=0f;
    private float gravity=0.04f*Game.SCALE;
    private float jumpSpeed= -2.25f*Game.SCALE;
    private float fallSpeedAfterCollision=0.5f*Game.SCALE;
    private boolean inAir=false;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitBox(x, y, 20*Game.SCALE, 28*Game.SCALE);
    }

    public void update() {
        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int) (hitBox.x-xDrawOffset), (int) (hitBox.y-yDrawOffset), width, height, null);
        drawHitBox(g);
    }
    

    private void loadAnimations() {
        BufferedImage img=LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[9][6]; 
            for (int i = 0; i < animations.length; i++) {
                for (int j = 0; j < animations[i].length; j++) {
                    animations[i][j] = img.getSubimage(j * 64, i * 40, 64, 40);
                }
            }
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData=lvlData;
    }

    public void resetDirBooleans() {
        up=false;
        down=false;
        left=false;
        right=false;
    }

    public void updatePos() {  
        moving=false;
        if (jump) {
            jump();
        }
        if (!left && !right && !inAir) {
            return;
        }

        float xSpeed=0;
        
        if (left) {
            xSpeed-=playerSpeed;
        } else if (right && !left) {
            xSpeed+=playerSpeed;
        }

        if (inAir) {
            if (CanMoveHere(hitBox.x, hitBox.y+ airSpeed, hitBox.width, hitBox.height, lvlData)) {
                hitBox.y +=airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitBox.y=GetEntityYPosUnderRoofOrAboveFloor(hitBox, airSpeed); 
                if (airSpeed>0) {
                    resetInAir();
                } else {
                    airSpeed= fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }

        } else {
            updateXPos(xSpeed);
        }
        moving=true;
    }

    private void jump() {
        if (inAir) {
            return;
        }
        inAir=true;
        airSpeed=jumpSpeed;
    }

    private void resetInAir() {
        inAir=false;
        airSpeed=0;
    }

    public void setAnimation() {
        int startAni=playerAction;
        
        if (moving) {
            playerAction=RUNNING;
        } else {
            playerAction=IDLE;
        }

        if (attacking) {
            playerAction=ATTACK_1;
        }

        if (startAni !=playerAction) {
            resetAniTick();
        }
    }

    private void resetAniTick() {
        aniTick=0;
        aniIndex=0;
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick>=aniSpeed) {
            aniTick=0;
            aniIndex++;
            if (aniIndex>=GetSpriteAmount(playerAction)) {
                aniIndex=0;
                attacking=false;
            }
        }
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitBox.x+xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData)) {
            hitBox.x +=xSpeed;
            moving=true;
        } else {
            hitBox.x= GetEntityXPosNextToWall(hitBox, xSpeed);
        }
    }

    //Getters and Setters

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setAttacking(boolean attacking) {
        this.attacking=attacking;
    }
}
