package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;
import gamestates.Playing;
import objects.*;

public class Japan extends Enemy {

	public Japan(float x, float y) {
		super(x, y, JAPAN_ACTUAL_WIDTH, JAPAN_ACTUAL_HEIGHT, JAPAN);
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
				for (Cannon c: playing.getLevelManager().getCurrentLevel().getCannons()) { 
					if (canSeeCannon(lvlData, c)) { if (canSeePlayer(lvlData, playing.getPlayer())) { 
						turnTowardsPlayer(playing.getPlayer()); 
						if (IsPlayerCloseForAttack(playing.getPlayer())) {
							 newState(ATTACK); 
						} 
					} else { 
						turnAwayFromCannon(c); 
					} 
				} 
			} 
				for (GameContainer gc: playing.getLevelManager().getCurrentLevel().getContainers()) {
					if (canSeeContainer(lvlData, gc)) { 
						if (canSeePlayer(lvlData, playing.getPlayer())) { 
							turnTowardsPlayer(playing.getPlayer()); if 
							(IsPlayerCloseForAttack(playing.getPlayer())) { 
								newState(ATTACK); 
							} 
						} else { 
							turnAwayFromGameContainer(gc); } 
						} 
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