package levels;

import static utilz.HelpMethods.*;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import entities.Crab;
import main.java.com.example.Game;

public class Level {
    
    private int[][] lvlData;
    private BufferedImage img;
    private ArrayList<Crab> crabs;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;

    public Level(BufferedImage img) {
        this.img=img;
        createLevelData();
        createEnemies();
        calcLvlOffsets();
    }

    private void calcLvlOffsets() {
        lvlTilesWide=img.getWidth();
        maxTilesOffset=lvlTilesWide-Game.TILES_IN_WIDTH;
        maxLvlOffsetX=Game.TILES_SIZE*maxTilesOffset;
    }

    private void createEnemies() {
        crabs=GetCrabs(img);
    }

    private void createLevelData() {
        lvlData= GetLevelData(img);
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int [][] getLevelData() {
        return this.lvlData;
    }

    public int getLvlOffset() {
        return this.maxLvlOffsetX;
    }

    public ArrayList<Crab> getCrabList() {
        return this.crabs;
    }
}
