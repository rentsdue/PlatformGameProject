package entities;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;

import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.PlayerConstants.PLAYER_DAMAGE;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] japanArray, italyArray, germanyArray;
    private Level currentLevel;
    
    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
    }

    public void loadEnemies(Level level) {
        this.currentLevel = level;
    }

    public void update(int[][] lvlData) {
        boolean isAnyActive = false;
        for (Japan japan: currentLevel.getJapans()) {
            if (japan.isActive()) {
                japan.update(lvlData, playing);
                isAnyActive = true;
            }
        }
        for (Italy p : currentLevel.getItalys())
			if (p.isActive()) {
				p.update(lvlData, playing);
				isAnyActive = true;
			}

		for (Germany s : currentLevel.getGermanys())
			if (s.isActive()) {
				s.update(lvlData, playing);
				isAnyActive = true;
			}

        if (!isAnyActive) {
            playing.setLevelCompleted(true);
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawJapanUnits(g, xLvlOffset);
        drawItalys(g, xLvlOffset);
        drawGermanys(g, xLvlOffset);
    }

    private void drawJapanUnits(Graphics g, int xLvlOffset) {
        for (Japan japan: currentLevel.getJapans()) {
            if (japan.isActive()) {
                g.drawImage(japanArray[japan.getState()][japan.getAniIndex()], (int) japan.getHitBox().x - xLvlOffset - JAPAN_DRAWOFFSET_X + japan.flipX(), (int) japan.getHitBox().y - JAPAN_DRAWOFFSET_Y, JAPAN_ACTUAL_WIDTH * japan.flipW(), JAPAN_ACTUAL_HEIGHT, null);
                /*japan.drawHitBox(g, xLvlOffset);
                japan.drawAttackBox(g, xLvlOffset);*/
            }
        }
    }

    private void drawGermanys(Graphics g, int xLvlOffset) {
		for (Germany s : currentLevel.getGermanys())
			if (s.isActive()) {
				g.drawImage(germanyArray[s.getState()][s.getAniIndex()], (int) s.getHitBox().x - xLvlOffset - GERMANY_DRAWOFFSET_X + s.flipX(),
						(int) s.getHitBox().y - GERMANY_DRAWOFFSET_Y + (int) s.getPushDrawOffset(), GERMANY_ACTUAL_WIDTH * s.flipW(), GERMANY_ACTUAL_HEIGHT, null);
//				s.drawHitbox(g, xLvlOffset);
//				s.drawAttackBox(g, xLvlOffset);
			}
	}

	private void drawItalys(Graphics g, int xLvlOffset) {
		for (Italy p : currentLevel.getItalys())
			if (p.isActive()) {
				g.drawImage(italyArray[p.getState()][p.getAniIndex()], (int) p.getHitBox().x - xLvlOffset - ITALY_DRAWOFFSET_X + p.flipX(),
						(int) p.getHitBox().y - ITALY_DRAWOFFSET_Y + (int) p.getPushDrawOffset(), ITALY_ACTUAL_WIDTH * p.flipW(), ITALY_ACTUAL_HEIGHT, null);
//				p.drawHitbox(g, xLvlOffset);
			}
	}

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for (Japan japan : currentLevel.getJapans())
            if (japan.getCurrentHealth() > 0) {
                if (japan.isActive()) {
                    if (attackBox.intersects(japan.getHitBox())) {
                        japan.hurt(PLAYER_DAMAGE);
                        return;
                }
			}
        }

        for (Italy p : currentLevel.getItalys()) {
            if (p.isActive()) {
				if (p.getState() == ATTACK && p.getAniIndex() >= 3)
					return;
				else {
					if (p.getState() != DEAD && p.getState() != HIT)
						if (attackBox.intersects(p.getHitBox())) {
							p.hurt(PLAYER_DAMAGE);
							return;
						}
				}
			}
        }
			

		for (Germany s : currentLevel.getGermanys()) {
            if (s.isActive()) {
				if (s.getState() != DEAD && s.getState() != HIT)
					if (attackBox.intersects(s.getHitBox())) {
						s.hurt(PLAYER_DAMAGE);
						return;
					}
			}
        }
	}

    private void loadEnemyImgs() {
        japanArray = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.JAPAN_ATLAS), 9, 5, JAPAN_DEFAULT_WIDTH, JAPAN_DEFAULT_HEIGHT);
		italyArray = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.ITALY_ATLAS), 8, 5, ITALY_DEFAULT_WIDTH, ITALY_DEFAULT_HEIGHT);
		germanyArray = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.GERMANY_ATLAS), 8, 5, GERMANY_DEFAULT_WIDTH, GERMANY_DEFAULT_HEIGHT);
    }

    private BufferedImage[][] getImgArr(BufferedImage atlas, int xSize, int ySize, int spriteW, int spriteH) {
		BufferedImage[][] tempArr = new BufferedImage[ySize][xSize];
		for (int j = 0; j < tempArr.length; j++)
			for (int i = 0; i < tempArr[j].length; i++)
				tempArr[j][i] = atlas.getSubimage(i * spriteW, j * spriteH, spriteW, spriteH);
		return tempArr;
	}

    public void resetAllEnemies() {
        for (Japan japan: currentLevel.getJapans()) {
            japan.resetEnemy();
        }

        for (Italy italy: currentLevel.getItalys()) {
            italy.resetEnemy();
        }

        for (Germany germany: currentLevel.getGermanys()) {
            germany.resetEnemy();
        }
    }
}
