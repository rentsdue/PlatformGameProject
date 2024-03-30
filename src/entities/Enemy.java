package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

import java.awt.geom.Rectangle2D;

import main.java.com.example.Game;

import static utilz.Constants.Directions.*;
import static utilz.Constants.*;

public abstract class Enemy extends Entity {
	
	protected int enemyType;
	protected boolean firstUpdate = true;
	
	protected int walkDir = LEFT;
	protected int tileY;
	protected float attackDistance = Game.TILES_SIZE;
	protected boolean active = true;
	

	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		this.walkSpeed = 0.35f * Game.SCALE;
		maxHealth = GetMaxHealth(enemyType);
		currentHealth = maxHealth;
	}

	protected void firstUpdateCheck(int[][] lvlData) {
		if (!IsEntityOnFloor(hitBox, lvlData))
			inAir = true;
		firstUpdate = false;
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
		return absValue <= attackDistance;
	}

	protected void newState(int state) {
		this.state = state;
		aniTick = 0;
		aniIndex = 0;
	}

	public void hurt(int amount) {
		currentHealth -= amount;
		if (currentHealth <= 0)
			newState(DEAD);
		else
			newState(HIT);
	}

	protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
		if (attackBox.intersects(player.hitBox))
			player.changeHealth(-GetEnemyDamage(enemyType));
		attackChecked = true;
	}

	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= ANI_SPEED) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(enemyType, state)) {
				aniIndex = 0;

				switch (state) {
				    case ATTACK:
					case HIT:
						state = IDLE;
						break;
					case DEAD:
						active = false;
						break;
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
	}

	public boolean isActive() {
		return this.active;
	}

}