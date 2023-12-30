package entities;
import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.Directions.*;
import static utilz.HelpMethods.*;

import main.java.com.example.Game;

public class Crab extends Enemy {
    
    
    public Crab(float x, float y) {
        super(x, y, CRAB_ACTUAL_WIDTH, CRAB_ACTUAL_HEIGHT, ENEMY_CRAB);
        initHitBox(x, y, (int) (22*Game.SCALE), (int)(19*Game.SCALE));
    }

    public void update(int[][] lvlData) {
        updateMove(lvlData);
        updateAnimationTick();
    }

    private void updateMove(int[][] lvlData) {
        if(firstUpdate) {
            if (!IsEntityOnFloor(hitBox, lvlData)) {
                inAir=true;
            }
            firstUpdate=false;
        }
        if (inAir) {
            if (CanMoveHere(hitBox.x, hitBox.y + fallSpeed, hitBox.width, hitBox.height, lvlData)) {
                hitBox.y +=fallSpeed;
                fallSpeed +=gravity;
            } else {
                inAir=false;
                hitBox.y=GetEntityYPosUnderRoofOrAboveFloor(hitBox, fallSpeed);
            }
        } else {
            switch(enemyState) {
                case IDLE:
                    enemyState=RUNNING;
                    break;
                case RUNNING:
                    float xSpeed=0;
                    if (walkDir==LEFT) {
                        xSpeed=-walkSpeed;
                    } else {
                        xSpeed=walkSpeed;
                    }
                    if (CanMoveHere(hitBox.x+xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData)) {
                        if (IsFloor(hitBox, xSpeed, lvlData)) {
                            hitBox.x+=xSpeed;
                            return;
                        }
                    }
                    changeWalkDir();
                    break;
            }
        }
    }
}
