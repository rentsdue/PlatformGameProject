package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Directions.*;

import gamestates.Playing;
import objects.*;

public class Italy extends Enemy {

	private boolean preRoll = true;
	private int tickSinceLastDmgToPlayer;
	private int tickAfterRollInIdle;
	private int rollDurationTick, rollDuration = 300;

	public Italy(float x, float y) {
		super(x, y, ITALY_ACTUAL_WIDTH, ITALY_ACTUAL_HEIGHT, ITALY);
		initHitBox(17, 21);
	}

	public void update(int[][] lvlData, Playing playing) {
		updateBehavior(lvlData, playing);
		updateAnimationTick();
	}

	private void updateBehavior(int[][] lvlData, Playing playing) {
		if (firstUpdate)
			firstUpdateCheck(lvlData);

		if (inAir)
			inAirChecks(lvlData, playing);
		else {
			switch (state) {
			case IDLE:
				preRoll = true;
				if (tickAfterRollInIdle >= 120) {
					if (IsFloor(hitBox, lvlData))
						newState(RUNNING);
					else
						inAir = true;
					tickAfterRollInIdle = 0;
					tickSinceLastDmgToPlayer = 60;
				} else
					tickAfterRollInIdle++;
				break;
			case RUNNING:
				if (canSeePlayer(lvlData, playing.getPlayer())) {
					newState(ATTACK);
					setWalkDir(playing.getPlayer());
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
				move(lvlData, playing);
				break;
			case ATTACK:
				if (preRoll) {
					if (aniIndex >= 3)
						preRoll = false;
				} else {
					move(lvlData, playing);
					checkDmgToPlayer(playing.getPlayer());
					checkRollOver(playing);
				}
				break;
			case HIT:
				if (aniIndex <= GetSpriteAmount(enemyType, state) - 2)
					pushBack(pushBackDir, lvlData, 2f);
				updatePushBackDrawOffset();
				tickAfterRollInIdle = 120;

				break;
			}
		}
	}

	private void checkDmgToPlayer(Player player) {
		if (hitBox.intersects(player.getHitBox()))
			if (tickSinceLastDmgToPlayer >= 60) {
				tickSinceLastDmgToPlayer = 0;
				player.changeHealth(-GetEnemyDamage(enemyType), this);
			} else
				tickSinceLastDmgToPlayer++;
	}

	private void setWalkDir(Player player) {
		if (player.getHitBox().x > hitBox.x)
			walkDir = RIGHT;
		else
			walkDir = LEFT;

	}

	protected void move(int[][] lvlData, Playing playing) {
		float xSpeed = 0;

		if (walkDir == LEFT)
			xSpeed = -walkSpeed;
		else
			xSpeed = walkSpeed;

		if (state == ATTACK)
			xSpeed *= 2;

		if (CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData))
			if (IsFloor(hitBox, xSpeed, lvlData)) {
				hitBox.x += xSpeed;
				return;
			}

		if (state == ATTACK) {
			rollOver(playing);
			rollDurationTick = 0;
		}

		changeWalkDir();

	}

	private void checkRollOver(Playing playing) {
		rollDurationTick++;
		if (rollDurationTick >= rollDuration) {
			rollOver(playing);
			rollDurationTick = 0;
		}
	}

	private void rollOver(Playing playing) {
		newState(IDLE);
	}

}