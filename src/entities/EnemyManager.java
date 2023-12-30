package entities;

import gamestates.Playing;
import utilz.LoadSave;

import static utilz.Constants.EnemyConstants.*;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.util.ArrayList;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] crabArray;
    private ArrayList<Crab> crabList= new ArrayList<>();
    
    public EnemyManager(Playing playing) {
        this.playing=playing;
        loadEnemyImgs();
        addEnemies();
    }

    private void addEnemies() {
        crabList=LoadSave.getCrabs();
        System.out.println("Number of Crabs: " + crabList.size());
    }

    public void update(int[][] lvlData) {
        for (Crab crab: crabList) {
            crab.update(lvlData);
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawCrabs(g, xLvlOffset);
    }

    private void drawCrabs(Graphics g, int xLvlOffset) {
        for (Crab crab: crabList) {
            g.drawImage(crabArray[crab.getEnemyState()][crab.getAniIndex()], (int) crab.getHitBox().x-xLvlOffset-CRAB_DRAWOFFSET_X, (int) crab.getHitBox().y-CRAB_DRAWOFFSET_Y, CRAB_ACTUAL_WIDTH, CRAB_ACTUAL_HEIGHT, null);
        }
    }

    private void loadEnemyImgs() {
        crabArray= new BufferedImage[5][9];
        BufferedImage temp=LoadSave.GetSpriteAtlas(LoadSave.ENEMY_SPRITE);
        for (int j=0; j<crabArray.length; j++) {
            for (int i=0; i<crabArray[j].length; i++) {
                crabArray[j][i]=temp.getSubimage(i*CRAB_DEFAULT_WIDTH, j*CRAB_DEFAULT_HEIGHT, CRAB_DEFAULT_WIDTH, CRAB_DEFAULT_HEIGHT);
                
            }
        }
    }
}
