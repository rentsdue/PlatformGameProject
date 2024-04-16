package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Directions.*;
import static utilz.Constants.*;

import java.awt.geom.Rectangle2D;
import gamestates.Playing;
import main.java.com.example.Game;
import objects.*;

public abstract class Enemy extends Entity {
	protected int enemyType;
	protected boolean firstUpdate = true;
	protected int walkDir = LEFT;
	protected int tileY;
	protected float attackDistance = Game.TILES_SIZE;
	protected boolean active = true;
	protected boolean attackChecked;
	protected int attackBoxOffsetX;

	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		maxHealth = GetMaxHealth(enemyType);
		currentHealth = maxHealth;
		walkSpeed = Game.SCALE * 0.35f;
	}

	protected void updateAttackBox() {
		attackBox.x = hitBox.x - attackBoxOffsetX;
		attackBox.y = hitBox.y;
	}

	protected void updateAttackBoxFlip() {
		if (walkDir == RIGHT)
			attackBox.x = hitBox.x + hitBox.width;
		else
			attackBox.x = hitBox.x - attackBoxOffsetX;

		attackBox.y = hitBox.y;
	}

	protected void initAttackBox(int w, int h, int attackBoxOffsetX) {
		attackBox = new Rectangle2D.Float(x, y, (int) (w * Game.SCALE), (int) (h * Game.SCALE));
		this.attackBoxOffsetX = (int) (Game.SCALE * attackBoxOffsetX);
	}

	protected void firstUpdateCheck(int[][] lvlData) {
		if (!IsEntityOnFloor(hitBox, lvlData))
			inAir = true;
		firstUpdate = false;
	}

	protected void inAirChecks(int[][] lvlData, Playing playing) {
		if (state != HIT && state != DEAD) {
			updateInAir(lvlData);
			playing.getObjectManager().checkSpikesTouched(this);
			if (IsEntityInWater(hitBox, lvlData))
				hurt(maxHealth);
		}
	}

	protected void updateInAir(int[][] lvlData) {
		if (CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlData)) {
			hitBox.y += airSpeed;
			airSpeed += GRAVITY;
		} else {
			inAir = false;
			hitBox.y = GetEntityYPosUnderRoofOrAboveFloor(hitBox, airSpeed);
			tileY = (int) (hitBox.y / Game.TILES_SIZE);
		}
	}

	protected void move(int[][] lvlData) {
		float xSpeed = 0;

		if (walkDir == LEFT)
			xSpeed = -walkSpeed;
		else
			xSpeed = walkSpeed;

		if (CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData))
			if (IsFloor(hitBox, xSpeed, lvlData)) {
				hitBox.x += xSpeed;
				return;
			}

		changeWalkDir();
	}

	protected void turnTowardsPlayer(Player player) {
		if (player.hitBox.x > hitBox.x)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}

	protected boolean canSeePlayer(int[][] lvlData, Player player) {
		int playerTileY = (int) (player.getHitBox().y / Game.TILES_SIZE);
		if (playerTileY == tileY)
			if (isPlayerInRange(player)) {
				if (IsSightClear(lvlData, hitBox, player.hitBox, tileY))
					return true;
			}
		return false;
	}

	protected boolean isPlayerInRange(Player player) {
		int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
		return absValue <= attackDistance * 5;
	}

	protected boolean IsPlayerCloseForAttack(Player player) {
		int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
		switch (enemyType) {
		case JAPAN -> {
			return absValue <= attackDistance;
		}
		case GERMANY -> {
			return absValue <= attackDistance * 2;
		}
		}
		return false;
	}

	//Interactions with spikes
	protected void turnAwayFromSpike(Spike spike) {
		if (spike.getHitBox().x > hitBox.x)
			walkDir = LEFT;
		else
			walkDir = RIGHT;
	}

	protected boolean canSeeSpike(int[][] lvlData, Spike spike) {
		int spikeTileY = (int) (spike.getHitBox().y / Game.TILES_SIZE);
		if (spikeTileY == tileY)
			if (IsSpikeInRange(spike)) {
				if (IsSightClear(lvlData, hitBox, spike.getHitBox(), tileY))
					return true;
			}

		return false;
	}

	protected boolean IsSpikeInRange(Spike spike) {
		int absValue = (int) Math.abs(spike.getHitBox().x - hitBox.x);
		return absValue <= attackDistance;
	} 

	//Interactions with cannons
	protected void turnAwayFromCannon(Cannon cannon) {
		if (cannon.getHitBox().x > hitBox.x)
			walkDir = LEFT;
		else
			walkDir = RIGHT;
	}

	protected boolean canSeeCannon(int[][] lvlData, Cannon cannon) {
		int cannonTileY = (int) (cannon.getHitBox().y / Game.TILES_SIZE);
		if (cannonTileY == tileY)
			if (IsCannonInRange(cannon)) {
				if (IsSightClear(lvlData, hitBox, cannon.getHitBox(), tileY))
					return true;
			}

		return false;
	}

	protected boolean IsCannonInRange(Cannon cannon) {
		int absValue = (int) Math.abs(cannon.getHitBox().x - hitBox.x);
		return absValue <= attackDistance;
	}

	//Game container interactions
	protected void turnAwayFromGameContainer(GameContainer gc) {
		if (gc.getHitBox().x > hitBox.x)
			walkDir = LEFT;
		else
			walkDir = RIGHT;
	}

	protected boolean canSeeContainer(int[][] lvlData, GameContainer gc) {
		int gcTileY = (int) (gc.getHitBox().y / Game.TILES_SIZE);
		if (gcTileY == tileY)
			if (IsGameContainerInRange(gc)) {
				if (IsSightClear(lvlData, hitBox, gc.getHitBox(), tileY))
					return true;
			}

		return false;
	}

	protected boolean IsGameContainerInRange(GameContainer gc) {
		int absValue = (int) Math.abs(gc.getHitBox().x - hitBox.x);
		return absValue <= attackDistance;
	}


	public void hurt(int amount) {
		currentHealth -= amount;
		if (currentHealth <= 0)
			newState(DEAD);
		else {
			newState(HIT);
			if (walkDir == LEFT)
				pushBackDir = RIGHT;
			else
				pushBackDir = LEFT;
			pushBackOffsetDir = UP;
			pushDrawOffset = 0;
		}
	}

	protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
		if (attackBox.intersects(player.hitBox))
			player.changeHealth(-GetEnemyDamage(enemyType), this);
		else {
			if (enemyType == GERMANY)
				return;
		}
		attackChecked = true;
	}

	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= ANI_SPEED) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(enemyType, state)) {
				if (enemyType == JAPAN || enemyType == GERMANY || enemyType == TUTORIAL_ENEMY) {
					aniIndex = 0;

					switch (state) {
					case ATTACK, HIT -> state = IDLE;
					case DEAD -> active = false;
					}
				} else if (enemyType == ITALY) {
					if (state == ATTACK)
						aniIndex = 3;
					else {
						aniIndex = 0;
						if (state == HIT) {
							state = IDLE;

						} else if (state == DEAD)
							active = false;
					}
				}
			}
		}
	}

	protected void changeWalkDir() {
		if (walkDir == LEFT)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}

	public void resetEnemy() {
		hitBox.x = x;
		hitBox.y = y;
		firstUpdate = true;
		currentHealth = maxHealth;
		newState(IDLE);
		active = true;
		airSpeed = 0;

		pushDrawOffset = 0;

	}

	public int flipX() {
		if (walkDir == RIGHT)
			return width;
		else
			return 0;
	}

	public int flipW() {
		if (walkDir == RIGHT)
			return -1;
		else
			return 1;
	}

	//Getters and setters
	public boolean isActive() {
		return this.active;
	}

	public float getPushDrawOffset() {
		return this.pushDrawOffset;
	}

}