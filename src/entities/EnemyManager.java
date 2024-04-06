package entities;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;

import static utilz.Constants.EnemyConstants.*;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] meleeArray, pinkstarArray, sharkArray;
    private Level currentLevel;
    
    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
    }

    public void loadEnemies(Level level) {
        this.currentLevel = level;
    }

    public void update(int[][] lvlData, Playing playing) {
        boolean isAnyActive = false;
        for (Melee melee: currentLevel.getMelees()) {
            if (melee.isActive()) {
                melee.update(lvlData, playing);
                isAnyActive = true;
            }
        }
        for (Pinkstar p : currentLevel.getPinkstars())
			if (p.isActive()) {
				p.update(lvlData, playing);
				isAnyActive = true;
			}

		for (Shark s : currentLevel.getSharks())
			if (s.isActive()) {
				s.update(lvlData, playing);
				isAnyActive = true;
			}

        if (!isAnyActive) {
            playing.setLevelCompleted(true);
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawMeleeUnits(g, xLvlOffset);
        drawPinkstars(g, xLvlOffset);
        drawSharks(g, xLvlOffset);
    }

    private void drawMeleeUnits(Graphics g, int xLvlOffset) {
        for (Melee melee: currentLevel.getMelees()) {
            if (melee.isActive()) {
                g.drawImage(meleeArray[melee.getState()][melee.getAniIndex()], (int) melee.getHitBox().x - xLvlOffset - MELEE_DRAWOFFSET_X + melee.flipX(), (int) melee.getHitBox().y - MELEE_DRAWOFFSET_Y, MELEE_ACTUAL_WIDTH * melee.flipW(), MELEE_ACTUAL_HEIGHT, null);
                /*melee.drawHitBox(g, xLvlOffset);
                melee.drawAttackBox(g, xLvlOffset);*/
            }
        }
    }

    private void drawSharks(Graphics g, int xLvlOffset) {
		for (Shark s : currentLevel.getSharks())
			if (s.isActive()) {
				g.drawImage(sharkArray[s.getState()][s.getAniIndex()], (int) s.getHitBox().x - xLvlOffset - SHARK_DRAWOFFSET_X + s.flipX(),
						(int) s.getHitBox().y - SHARK_DRAWOFFSET_Y + (int) s.getPushDrawOffset(), SHARK_ACTUAL_WIDTH * s.flipW(), SHARK_ACTUAL_HEIGHT, null);
//				s.drawHitbox(g, xLvlOffset);
//				s.drawAttackBox(g, xLvlOffset);
			}
	}

	private void drawPinkstars(Graphics g, int xLvlOffset) {
		for (Pinkstar p : currentLevel.getPinkstars())
			if (p.isActive()) {
				g.drawImage(pinkstarArray[p.getState()][p.getAniIndex()], (int) p.getHitBox().x - xLvlOffset - PINKSTAR_DRAWOFFSET_X + p.flipX(),
						(int) p.getHitBox().y - PINKSTAR_DRAWOFFSET_Y + (int) p.getPushDrawOffset(), PINKSTAR_ACTUAL_WIDTH * p.flipW(), PINKSTAR_ACTUAL_HEIGHT, null);
//				p.drawHitbox(g, xLvlOffset);
			}
	}

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for (Melee melee : currentLevel.getMelees())
            if (melee.getCurrentHealth() > 0) {
                if (melee.isActive()) {
                    if (attackBox.intersects(melee.getHitBox())) {
                        melee.hurt(10);
                        return;
                }
			}
        }

        for (Pinkstar p : currentLevel.getPinkstars()) {
            if (p.isActive()) {
				if (p.getState() == ATTACK && p.getAniIndex() >= 3)
					return;
				else {
					if (p.getState() != DEAD && p.getState() != HIT)
						if (attackBox.intersects(p.getHitBox())) {
							p.hurt(20);
							return;
						}
				}
			}
        }
			

		for (Shark s : currentLevel.getSharks()) {
            if (s.isActive()) {
				if (s.getState() != DEAD && s.getState() != HIT)
					if (attackBox.intersects(s.getHitBox())) {
						s.hurt(20);
						return;
					}
			}
        }
	}

    private void loadEnemyImgs() {
        meleeArray = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.MELEE_ATLAS), 9, 5, MELEE_DEFAULT_WIDTH, MELEE_DEFAULT_HEIGHT);
		pinkstarArray = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.PINKSTAR_ATLAS), 8, 5, PINKSTAR_DEFAULT_WIDTH, PINKSTAR_DEFAULT_HEIGHT);
		sharkArray = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.SHARK_ATLAS), 8, 5, SHARK_DEFAULT_WIDTH, SHARK_DEFAULT_HEIGHT);
    }

    private BufferedImage[][] getImgArr(BufferedImage atlas, int xSize, int ySize, int spriteW, int spriteH) {
		BufferedImage[][] tempArr = new BufferedImage[ySize][xSize];
		for (int j = 0; j < tempArr.length; j++)
			for (int i = 0; i < tempArr[j].length; i++)
				tempArr[j][i] = atlas.getSubimage(i * spriteW, j * spriteH, spriteW, spriteH);
		return tempArr;
	}

    public void resetAllEnemies() {
        for (Melee melee: currentLevel.getMelees()) {
            melee.resetEnemy();
        }

        for (Pinkstar pinkstar: currentLevel.getPinkstars()) {
            pinkstar.resetEnemy();
        }

        for (Shark shark: currentLevel.getSharks()) {
            shark.resetEnemy();
        }
    }
}
