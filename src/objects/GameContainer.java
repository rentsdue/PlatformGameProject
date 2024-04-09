package objects;

import static utilz.Constants.ObjectConstants.*;

import java.awt.geom.Rectangle2D;

import main.java.com.example.Game;

public class GameContainer extends GameObject {

	public GameContainer(int x, int y, int objType) {
		super(x, y, objType);
		createHitbox();
	}

	private void createHitbox() {
		if (objType == BOX) {
			initHitbox(25, 18);

			xDrawOffset = (int) (7 * Game.SCALE);
			yDrawOffset = (int) (12 * Game.SCALE);

		} else {
			initHitbox(23, 25);
			xDrawOffset = (int) (8 * Game.SCALE);
			yDrawOffset = (int) (5 * Game.SCALE);
		}

		hitBox.y += yDrawOffset + (int) (Game.SCALE * 2);
		hitBox.x += xDrawOffset / 2;
	}

	public void update() {
		if (doAnimation)
			updateAnimationTick();
	}

	public Rectangle2D.Float getHitBox() {
		return this.hitBox;
	}
}