package entities;

import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

import gamestates.Playing;
import objects.*;

public class Germany extends Enemy {

	public Germany(float x, float y) {
		super(x, y, GERMANY_ACTUAL_WIDTH, GERMANY_ACTUAL_HEIGHT, GERMANY);
		initHitBox(18, 22);
		initAttackBox(20, 20, 20);
	}

	public void update(int[][] lvlData, Playing playing) {
		updateBehavior(lvlData, playing);
		updateAnimationTick();
		updateAttackBoxFlip();
	}

	private void updateBehavior(int[][] lvlData, Playing playing) {
		if (firstUpdate)
			firstUpdateCheck(lvlData);

		if (inAir)
			inAirChecks(lvlData, playing);
		else {
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
					if (IsPlayerCloseForAttack(playing.getPlayer())) {
						newState(ATTACK);
					}
				}
				for (Spike s: playing.getLevelManager().getCurrentLevel().getSpikes()) {
					if (canSeeSpike(lvlData, s)) {
						turnAwayFromSpike(s);
					}
				}
				for (Cannon c: playing.getLevelManager().getCurrentLevel().getCannons()) {
					if (canSeeCannon(lvlData, c)) {
						turnAwayFromCannon(c);
					}
				}
				for (GameContainer gc: playing.getLevelManager().getCurrentLevel().getContainers()) {
					if (canSeeContainer(lvlData, gc)) {
						turnAwayFromGameContainer(gc);
					}
				}
				move(lvlData);
			case ATTACK:
				if (aniIndex == 0)
					attackChecked = false;
				else if (aniIndex == 3) {
					if (!attackChecked)
						checkPlayerHit(attackBox, playing.getPlayer());
					attackMove(lvlData, playing);
				}

				break;
	 		case HIT:
				if (aniIndex <= GetSpriteAmount(enemyType, state) - 2)
					pushBack(pushBackDir, lvlData, 2f);
				updatePushBackDrawOffset();
				break;
			}
		}
	}

	protected void attackMove(int[][] lvlData, Playing playing) {
		float xSpeed = 0;

		if (walkDir == LEFT)
			xSpeed = -walkSpeed;
		else
			xSpeed = walkSpeed;

		if (CanMoveHere(hitBox.x + xSpeed * 4, hitBox.y, hitBox.width, hitBox.height, lvlData))
			if (IsFloor(hitBox, xSpeed * 4, lvlData)) {
				hitBox.x += xSpeed * 4;
				return;
			}
		newState(IDLE);
	}
}