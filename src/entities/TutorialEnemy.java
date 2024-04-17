package entities;
import gamestates.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.EnemyConstants.*;

public class TutorialEnemy extends Enemy {

    public TutorialEnemy(float x, float y) {
        super(x, y, TUTORIAL_ENEMY_WIDTH, TUTORIAL_ENEMY_HEIGHT, TUTORIAL_ENEMY);
        initHitBox(22, 19);
        initAttackBox(82, 19, 30);
    }

    public void update(int[][] lvlData, Playing playing) {
        updateBehavior(lvlData, playing);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateBehavior(int[][] lvlData, Playing playing) {
		if (firstUpdate)
			firstUpdateCheck(lvlData);
		if (inAir) {
			inAirChecks(lvlData, playing);
		} else {
			switch (state) {
			case IDLE:
				if (IsFloor(hitBox, lvlData))
					newState(RUNNING);
				else
					inAir = true;
				break;
			case RUNNING:
				if (canSeePlayer(lvlData, playing.getPlayer())) {
					turnTowardsPlayer(playing.getPlayer());
					if (IsPlayerCloseForAttack(playing.getPlayer()))
						newState(ATTACK);
				}
				move(lvlData);
				break;
			case ATTACK:
				if (aniIndex == 0)
					attackChecked = false;
				if (aniIndex == 3 && !attackChecked)
					checkPlayerHit(attackBox, playing.getPlayer());
				break;
			case HIT:
				if (aniIndex <= GetSpriteAmount(enemyType, state) - 2)
					pushBack(pushBackDir, lvlData, 2f);
				updatePushBackDrawOffset();
				break;
			}
		}
	}
}
