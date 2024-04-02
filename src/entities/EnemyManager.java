package entities;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;

import static utilz.Constants.EnemyConstants.*;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] meleeArray;
    private ArrayList<Melee> meleeList= new ArrayList<>();
    
    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
    }

    public void loadEnemies(Level level) {
        meleeList = level.getMeleeList();
        System.out.println("Number of Melees: " + meleeList.size());
    }

    public void update(int[][] lvlData, Player player) {
        boolean isAnyActive = false;
        for (Melee melee: meleeList) {
            if (melee.isActive()) {
                melee.update(lvlData, player);
                isAnyActive = true;
            }
        }
        if (!isAnyActive) {
            playing.setLevelCompleted(true);
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawMeleeUnits(g, xLvlOffset);
    }

    private void drawMeleeUnits(Graphics g, int xLvlOffset) {
        for (Melee melee: meleeList) {
            if (melee.isActive()) {
                g.drawImage(meleeArray[melee.getState()][melee.getAniIndex()], (int) melee.getHitBox().x - xLvlOffset - MELEE_DRAWOFFSET_X + melee.flipX(), (int) melee.getHitBox().y - MELEE_DRAWOFFSET_Y, MELEE_ACTUAL_WIDTH * melee.flipW(), MELEE_ACTUAL_HEIGHT, null);
                /*melee.drawHitBox(g, xLvlOffset);
                melee.drawAttackBox(g, xLvlOffset);*/
            }
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for (Melee melee : meleeList)
			if (melee.isActive())
				if (attackBox.intersects(melee.getHitBox())) {
					melee.hurt(10);
					return;
				}
	}

    private void loadEnemyImgs() {
        meleeArray= new BufferedImage[5][9];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.ENEMY_SPRITE);
        for (int j = 0; j < meleeArray.length; j++) {
            for (int i = 0; i < meleeArray[j].length; i++) {
                meleeArray[j][i] = temp.getSubimage(i * MELEE_DEFAULT_WIDTH, j * MELEE_DEFAULT_HEIGHT, MELEE_DEFAULT_WIDTH, MELEE_DEFAULT_HEIGHT);
            }
        }
    }

    public void resetAllEnemies() {
        for (Melee melee: meleeList) {
            melee.resetEnemy();
        }
    }
}
