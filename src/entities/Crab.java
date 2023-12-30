package entities;
import static utilz.Constants.EnemyConstants.*;

import main.java.com.example.Game;

public class Crab extends Enemy {
    
    
    public Crab(float x, float y) {
        super(x, y, CRAB_ACTUAL_WIDTH, CRAB_ACTUAL_HEIGHT, ENEMY_CRAB);
        initHitBox(x, y, (int) (22*Game.SCALE), (int)(19*Game.SCALE));
    }

    public void update(int[][] lvlData, Player player) {
        updateMove(lvlData, player);
        updateAnimationTick();
    }

    private void updateMove(int[][] lvlData, Player player) {
        if(firstUpdate) {
            firstUpdateCheck(lvlData);
        }
        if (inAir) {
            updateInAir(lvlData);
        } else {
            switch(enemyState) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if (canSeePlayer(lvlData, player)) {
                        turnTowardsPlayer(player);
                    }
                    if (isPlayerCloseForAttack(player)) {
                        newState(ATTACK);
                    }
                    move(lvlData);
                    break;
            }
        }
    }
}
