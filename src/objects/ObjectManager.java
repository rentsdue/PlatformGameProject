package objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Player;
import gamestates.Playing;
import levels.Level;
import main.java.com.example.Game;
import utilz.LoadSave;
import static utilz.Constants.ObjectConstants.*;
import static utilz.Constants.Projectiles.*;
import static utilz.HelpMethods.*;

public class ObjectManager {

	private Playing playing;
	private BufferedImage[][] potionImgs, containerImgs;
	private BufferedImage[] cannonImgs;
	private BufferedImage spikeImg;
	private BufferedImage ballImg;
	private ArrayList<Potion> potions;
	private ArrayList<GameContainer> containers;
	private ArrayList<Spike> spikes;
	private ArrayList<Cannon> cannons;
	private ArrayList<Projectile> projectiles = new ArrayList<>();

	public ObjectManager(Playing playing) {
		this.playing = playing;
		loadImgs();
	}

	public void checkObjectTouched(Rectangle2D.Float hitBox) {
		for (Potion p : potions)
			if (p.isActive()) {
				if (hitBox.intersects(p.getHitBox())) {
					p.setActive(false);
					applyEffectToPlayer(p);
				}
			}
	}

	public void applyEffectToPlayer(Potion p) {
		if (p.getObjType() == RED_POTION)
			playing.getPlayer().changeHealth(RED_POTION_VALUE);
		else
			playing.getPlayer().changePower(BLUE_POTION_VALUE);
	}

	public void checkObjectHit(Rectangle2D.Float attackbox) {
		for (GameContainer gc : containers)
			if (gc.isActive() & !gc.doAnimation) { //Ensures that the "multiple potion" bug is gone
				if (gc.getHitBox().intersects(attackbox)) {
					gc.setAnimation(true);
					int type = 0;
					if (gc.getObjType() == BARREL)
						type = 1;
					potions.add(new Potion((int) (gc.getHitBox().x + gc.getHitBox().width / 2), (int) (gc.getHitBox().y - gc.getHitBox().height / 2), type));
					return;
				}
			}
	}

	public void checkSpikesTouched(Player p) {
		for (Spike s: spikes) {
			if (s.getHitBox().intersects(p.getHitBox())){
				p.kill();
			}
		}
	}

	public void loadObjects(Level newLevel) { //Copies objectManager potions/containers into new arraylist in level, but new objects added to objectmanager will not be added to level arraylist
		potions = new ArrayList<>(newLevel.getPotions());
		containers = new ArrayList<>(newLevel.getContainers());
		spikes = newLevel.getSpikes();
		cannons = newLevel.getCannons();
		projectiles.clear();
	}

	private void loadImgs() {
		BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
		potionImgs = new BufferedImage[2][7];

		for (int j = 0; j < potionImgs.length; j++)
			for (int i = 0; i < potionImgs[j].length; i++)
				potionImgs[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);

		BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.OBJECT_ATLAS);
		containerImgs = new BufferedImage[2][8];

		for (int j = 0; j < containerImgs.length; j++)
			for (int i = 0; i < containerImgs[j].length; i++)
				containerImgs[j][i] = containerSprite.getSubimage(40 * i, 30 * j, 40, 30);
		spikeImg = LoadSave.GetSpriteAtlas(LoadSave.TRAPS);

		cannonImgs = new BufferedImage[7];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CANNON_ATLAS);
		for (int i = 0; i < cannonImgs.length; i++) {
			cannonImgs[i] = temp.getSubimage(i * 40, 0, 40, 26);
		}

		ballImg = LoadSave.GetSpriteAtlas(LoadSave.BALL);
	}

	public void update(int[][] lvlData, Player player) {
		for (Potion p : potions)
			if (p.isActive())
				p.update();

		for (GameContainer gc : containers)
			if (gc.isActive())
				gc.update();

		updateCannons(lvlData, player);
		updateProjectiles(lvlData, player);
	}

	private void updateProjectiles(int[][] lvlData, Player player) {
		for (Projectile p: projectiles) {
			if (p.isActive()) {
				p.updatePosition();
			}
		}
	}

	private void updateCannons(int[][] lvlData, Player player) {
		for (Cannon c: cannons) {
			if (c.getTileY() == player.getTileY()) {
				if (IsPlayerInRange(c, player)) {
					if (IsPlayerInFrontOfCannon(c, player)) {
						if (CanCannonSeePlayer(lvlData, player.getHitBox(), c.getHitBox(), c.getTileY())) {
							shootCannon(c);
						}
					}
				}
			}
			c.update();
		}
	}

	private void shootCannon(Cannon c) {
		c.setAnimation(true);
		int dir = 1;
		if (c.objType == CANNON_RIGHT) {
			dir = -1;
		}
		projectiles.add(new Projectile((int) c.getHitBox().x, (int) c.getHitBox().y, dir));
	}

	private boolean IsPlayerInFrontOfCannon(Cannon c, Player player) {
		if (c.getObjType() == CANNON_LEFT) {
			if (c.getHitBox().x > player.getHitBox().x) {
				return true;
			}
		} else if (c.getHitBox().x < player.getHitBox().x) {
			return true;
		}
		return false;
	}

	private boolean IsPlayerInRange(Cannon c, Player player) {
		int absValue = (int) Math.abs(player.getHitBox().x - c.getHitBox().x);
		return absValue <= Game.TILES_SIZE * 5;
	}

	public void draw(Graphics g, int xLvlOffset) { //Draws objects onto screen
		drawPotions(g, xLvlOffset);
		drawContainers(g, xLvlOffset);
		drawSpikes(g, xLvlOffset);
		drawCannons(g, xLvlOffset);
		drawProjectiles(g, xLvlOffset);
	}

	private void drawProjectiles(Graphics g, int xLvlOffset) {
		for (Projectile p : projectiles) {
			if (p.isActive()) {
				g.drawImage(ballImg, (int) (p.getHitBox().x - xLvlOffset), (int) (p.getHitBox().y), BALL_WIDTH, BALL_HEIGHT, null);
			}
		}
	}

	private void drawCannons(Graphics g, int xLvlOffset) {
		for (Cannon c: cannons) {
			int x = (int) (c.getHitBox().x - xLvlOffset);
			int width = CANNON_WIDTH;

			if (c.getObjType() == CANNON_RIGHT) {
				x += width;
				width *= -1;
			}
		g.drawImage(cannonImgs[c.getAniIndex()], x, (int) (c.getHitBox().y), width, CANNON_HEIGHT, null);
		}
	}

	private void drawSpikes(Graphics g, int xLvlOffset) {
		for (Spike s: spikes) {
			g.drawImage(spikeImg, (int) (s.getHitBox().x - xLvlOffset), (int) (s.getHitBox().y - s.getyDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);
		}
	}

	private void drawContainers(Graphics g, int xLvlOffset) {
		for (GameContainer gc : containers)
			if (gc.isActive()) {
				int type = 0;
				if (gc.getObjType() == BARREL)
					type = 1;
				g.drawImage(containerImgs[type][gc.getAniIndex()], (int) (gc.getHitBox().x - gc.getxDrawOffset() - xLvlOffset), (int) (gc.getHitBox().y - gc.getyDrawOffset()), CONTAINER_WIDTH,
						CONTAINER_HEIGHT, null);
			}
	}

	private void drawPotions(Graphics g, int xLvlOffset) {
		for (Potion p : potions)
			if (p.isActive()) {
				int type = 0;
				if (p.getObjType() == RED_POTION)
					type = 1;
				g.drawImage(potionImgs[type][p.getAniIndex()], (int) (p.getHitBox().x - p.getxDrawOffset() - xLvlOffset), (int) (p.getHitBox().y - p.getyDrawOffset()), POTION_WIDTH, POTION_HEIGHT,
						null);
			}
	}

	public void resetAllObjects() {
		System.out.println("ArrayList sizes before: " + potions.size() + ", " + containers.size());
		loadObjects(playing.getLevelManager().getCurrentLevel());

		for (Potion p : potions)
			p.reset();

		for (GameContainer gc : containers)
			gc.reset();
		
		for (Cannon c: cannons) {
			c.reset();
		}

		//System.out.println("ArrayList sizes after: " + potions.size() + ", " + containers.size());
	}

}