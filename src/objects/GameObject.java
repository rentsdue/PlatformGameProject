package objects;

import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.ObjectConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.java.com.example.Game;

public class GameObject {

	protected int x, y, objType;
	protected Rectangle2D.Float hitBox;
	protected boolean doAnimation, active = true;
	protected int aniTick, aniIndex;
	protected int xDrawOffset, yDrawOffset;

	public GameObject(int x, int y, int objType) {
		this.x = x;
		this.y = y;
		this.objType = objType;
	}

	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= ANI_SPEED) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(objType)) {
				aniIndex = 0;
				if (objType == BARREL || objType == BOX) {
					doAnimation = false;
					active = false;
				}
			}
		}
	}

	public void reset() {
		aniIndex = 0;
		aniTick = 0;
		active = true;

		if (objType == BARREL || objType == BOX)
			doAnimation = false;
		else
			doAnimation = true;
	}

	protected void initHitbox(int width, int height) {
		hitBox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
	}

	public void drawHitbox(Graphics g, int xLvlOffset) {
		g.setColor(Color.PINK);
		g.drawRect((int) hitBox.x - xLvlOffset, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
	}

	public int getObjType() {
		return this.objType;
	}

	public Rectangle2D.Float getHitBox() {
		return this.hitBox;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setAnimation(boolean doAnimation) {
		this.doAnimation = doAnimation;
	}

	public int getxDrawOffset() {
		return this.xDrawOffset;
	}

	public int getyDrawOffset() {
		return this.yDrawOffset;
	}

	public int getAniIndex() {
		return this.aniIndex;
	}

}