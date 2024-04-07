package utilz;

import java.awt.geom.Rectangle2D;
import main.java.com.example.Game;
import objects.*;

public class HelpMethods {

	//"Can" help methods
	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) { //Checks if the character can move or if it collides
		if (!IsSolid(x, y, lvlData)) 
			if (!IsSolid(x + width, y + height, lvlData))
				if (!IsSolid(x + width, y, lvlData))
					if (!IsSolid(x, y + height, lvlData))
						return true;
		return false;
	}

	public static boolean CanCannonSeePlayer(int[][] lvlData, Rectangle2D.Float firstHitBox, Rectangle2D.Float secondHitBox, int yTile) {
		int firstXTile = (int) (firstHitBox.x / Game.TILES_SIZE);
		int secondXTile = (int) (secondHitBox.x / Game.TILES_SIZE);

		if (firstXTile > secondXTile)
			return IsAllTilesClear(secondXTile, firstXTile, yTile, lvlData);
		else
			return IsAllTilesClear(firstXTile, secondXTile, yTile, lvlData);
	}

	//"Is" help methods

	public static boolean IsProjectileHittingLevel(Projectile p, int[][] lvlData) {
		return IsSolid(p.getHitBox().x + p.getHitBox().width / 2, p.getHitBox().y + p.getHitBox().height / 2, lvlData);
	}

	public static boolean IsAllTilesClear(int xStart, int xEnd, int y, int[][] lvlData) {
		for (int i = 0; i < xEnd - xStart; i++) {
			if (IsTileSolid(xStart + i, y, lvlData)) 
				return false;
		}
		return true;
	}

	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		int maxWidth = lvlData[0].length*Game.TILES_SIZE;
		if (x < 0 || x >= maxWidth)
			return true;
		if (y < 0 || y >= Game.GAME_HEIGHT)
			return true;

		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;

		return IsTileSolid((int) xIndex, (int) yIndex, lvlData);
	}

	public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
		int value = lvlData[yTile][xTile];
		switch (value) {
		case 11, 48, 49:
			return false;
		default:
			return true;
		}

	}

	public static boolean IsEntityOnFloor(Rectangle2D.Float hitBox, int[][] lvlData) {
		// Check the pixel below bottomleft and bottomright
		if (!IsSolid(hitBox.x, hitBox.y + hitBox.height + 1, lvlData))
			if (!IsSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, lvlData))
				return false;

		return true;
	}

	public static boolean IsFloor(Rectangle2D.Float hitBox, float xSpeed, int[][] lvlData) {
		if (xSpeed > 0) {
			return IsSolid(hitBox.x + hitBox.width + xSpeed, hitBox.y + hitBox.height + 1, lvlData);
		} else {
			return IsSolid(hitBox.x + xSpeed, hitBox.y + hitBox.height + 1, lvlData);
		}
	}

	public static boolean IsFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
			if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
				return false;
		return true;
	}

	public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
		if (IsAllTilesClear(xStart, xEnd, y, lvlData)) {
			for (int i = 0; i < xEnd - xStart; i++) {
				if (!IsTileSolid(xStart + i, y + 1, lvlData))
					return false;
			}
		}
		return true;
	}

	public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float enemyBox, Rectangle2D.Float playerBox, int yTile) {
		int firstXTile = (int) (enemyBox.x / Game.TILES_SIZE);

		int secondXTile;
		if (IsSolid(playerBox.x, playerBox.y + playerBox.height + 1, lvlData))
			secondXTile = (int) (playerBox.x / Game.TILES_SIZE);
		else
			secondXTile = (int) ((playerBox.x + playerBox.width) / Game.TILES_SIZE);

		if (firstXTile > secondXTile)
			return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
		else
			return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);
	}

	public static boolean IsEntityInWater(Rectangle2D.Float hitbox, int[][] lvlData) {
		if (GetTileValue(hitbox.x, hitbox.y + hitbox.height, lvlData) != 48)
			if (GetTileValue(hitbox.x + hitbox.width, hitbox.y + hitbox.height, lvlData) != 48)
				return false;
		return true;
	}

	//"Getting" help methods
	private static int GetTileValue(float xPos, float yPos, int[][] lvlData) {
		int xCord = (int) (xPos / Game.TILES_SIZE);
		int yCord = (int) (yPos / Game.TILES_SIZE);
		return lvlData[yCord][xCord];
	}

	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitBox, float xSpeed) {
		int currentTile = (int) (hitBox.x / Game.TILES_SIZE);
		if (xSpeed > 0) {
			// Right
			int tileXPos = currentTile * Game.TILES_SIZE;
			int xOffset = (int) (Game.TILES_SIZE - hitBox.width);
			return tileXPos + xOffset - 1;
		} else
			// Left
			return currentTile * Game.TILES_SIZE;
	}

	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitBox, float airSpeed) {
		int currentTile = (int) (hitBox.y / Game.TILES_SIZE);
		if (airSpeed > 0) {
			// Falling - touching floor
			int tileYPos = currentTile * Game.TILES_SIZE;
			int yOffset = (int) (Game.TILES_SIZE - hitBox.height);
			return tileYPos + yOffset - 1;
		} else
			// Jumping
			return currentTile * Game.TILES_SIZE;

	}
}

