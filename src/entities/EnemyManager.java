package entities;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;

import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.PlayerConstants.ATTACK;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] japanArray, italyArray, germanyArray, tutorialEnemyArray;
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
        for (Japan j: currentLevel.getJapans()) {
            if (j.isActive()) {
                j.update(lvlData, playing);
                isAnyActive = true;
            }
        }
        for (Italy i : currentLevel.getItalys()) {
            if (i.isActive()) {
				i.update(lvlData, playing);
				isAnyActive = true;
			}
        }
		for (Germany g : currentLevel.getGermanys()) {
            if (g.isActive()) {
				g.update(lvlData, playing);
				isAnyActive = true;
			}
        }
        for (TutorialEnemy t : currentLevel.getTutorialEnemies()) {
            if (t.isActive()) {
				t.update(lvlData, playing);
				isAnyActive = true;
			}
        }
			
        if (!isAnyActive) {
            playing.setLevelCompleted(true);
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawTutorialEnemies(g, xLvlOffset);
        drawJapanUnits(g, xLvlOffset);
        drawItalyUnits(g, xLvlOffset);
        drawGermanyUnits(g, xLvlOffset);
    }

    private void drawJapanUnits(Graphics g, int xLvlOffset) {
        for (Japan j: currentLevel.getJapans()) {
            if (j.isActive()) {
                g.drawImage(japanArray[j.getState()][j.getAniIndex()], (int) j.getHitBox().x - xLvlOffset - JAPAN_DRAWOFFSET_X + j.flipX(), (int) j.getHitBox().y - JAPAN_DRAWOFFSET_Y, JAPAN_ACTUAL_WIDTH * j.flipW(), JAPAN_ACTUAL_HEIGHT, null);
                // j.drawHitBox(g, xLvlOffset);
                // j.drawAttackBox(g, xLvlOffset);
            }
        }
    }

    private void drawGermanyUnits(Graphics g, int xLvlOffset) {
		for (Germany d : currentLevel.getGermanys())
			if (d.isActive()) {
				g.drawImage(germanyArray[d.getState()][d.getAniIndex()], (int) d.getHitBox().x - xLvlOffset - GERMANY_DRAWOFFSET_X + d.flipX(),
						(int) d.getHitBox().y - GERMANY_DRAWOFFSET_Y + (int) d.getPushDrawOffset(), GERMANY_ACTUAL_WIDTH * d.flipW(), GERMANY_ACTUAL_HEIGHT, null);
//				d.drawHitBox(g, xLvlOffset);
//				d.drawAttackBox(g, xLvlOffset);
			}
	}

	private void drawItalyUnits(Graphics g, int xLvlOffset) {
		for (Italy i : currentLevel.getItalys())
			if (i.isActive()) {
				g.drawImage(italyArray[i.getState()][i.getAniIndex()], (int) i.getHitBox().x - xLvlOffset - ITALY_DRAWOFFSET_X + i.flipX(),
						(int) i.getHitBox().y - ITALY_DRAWOFFSET_Y + (int) i.getPushDrawOffset(), ITALY_ACTUAL_WIDTH * i.flipW(), ITALY_ACTUAL_HEIGHT, null);
//				i.drawHitBox(g, xLvlOffset);
			}
	}

    private void drawTutorialEnemies(Graphics g, int xLvlOffset) {
		for (TutorialEnemy t : currentLevel.getTutorialEnemies())
			if (t.isActive()) {
				g.drawImage(tutorialEnemyArray[t.getState()][t.getAniIndex()], (int) t.getHitBox().x - xLvlOffset - JAPAN_DRAWOFFSET_X + t.flipX(),
						(int) t.getHitBox().y - JAPAN_DRAWOFFSET_Y + (int) t.getPushDrawOffset(), JAPAN_ACTUAL_WIDTH * t.flipW(), JAPAN_ACTUAL_HEIGHT, null);
                //t.drawHitBox(g, xLvlOffset);
			}
	}

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for (Japan japan : currentLevel.getJapans())
            if (japan.getCurrentHealth() > 0) {
                if (japan.isActive()) {
                    if (attackBox.intersects(japan.getHitBox())) {
                        japan.hurt(playing.getPlayer().getPlayerCharacter().damageDealt);
                        return;
                }
			}
        }

        for (TutorialEnemy t : currentLevel.getTutorialEnemies())
            if (t.getCurrentHealth() > 0) {
                if (t.isActive()) {
                    if (attackBox.intersects(t.getHitBox())) {
                        t.hurt(playing.getPlayer().getPlayerCharacter().damageDealt);
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
							p.hurt(playing.getPlayer().getPlayerCharacter().damageDealt);
							return;
						}
				}
			}
        }
			
		for (Germany s : currentLevel.getGermanys()) {
            if (s.isActive()) {
				if (s.getState() != DEAD && s.getState() != HIT)
					if (attackBox.intersects(s.getHitBox())) {
						s.hurt(playing.getPlayer().getPlayerCharacter().damageDealt);
						return;
					}
			}
        }
	}

    private void loadEnemyImgs() {
        japanArray = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.JAPAN_ATLAS), 8, 5, JAPAN_DEFAULT_WIDTH, JAPAN_DEFAULT_HEIGHT);
        tutorialEnemyArray = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.JAPAN_ATLAS), 8, 5, JAPAN_DEFAULT_WIDTH, JAPAN_DEFAULT_HEIGHT);
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

        for (TutorialEnemy t: currentLevel.getTutorialEnemies()) {
            t.resetEnemy();
        }

        for (Italy italy: currentLevel.getItalys()) {
            italy.resetEnemy();
        }

        for (Germany germany: currentLevel.getGermanys()) {
            germany.resetEnemy();
        }
    }
}
