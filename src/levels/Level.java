package levels;

import static utilz.HelpMethods.*;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import entities.Crab;
import main.java.com.example.Game;
import objects.GameContainer;
import objects.Potion;
import utilz.HelpMethods;

public class Level {
    
    private int[][] lvlData;
    private BufferedImage img;
    private ArrayList<Crab> crabs;
    private ArrayList<Potion> potions;
	private ArrayList<GameContainer> containers;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point spawnPoint;

    public Level(BufferedImage img) {
        this.img=img;
        createLevelData();
        createEnemies();
        calcLvlOffsets();
        calcPlayerSpawn();
    }

    private void calcPlayerSpawn() {
        spawnPoint=GetPlayerSpawn(img);
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

    public void createContainers() {
		containers = HelpMethods.GetContainers(img);
	}

	public void createPotions() {
		potions = HelpMethods.GetPotions(img);
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

    public Point getSpawnPoint() {
        return this.spawnPoint;
    }

    public ArrayList<Potion> getPotions() {
		return this.potions;
	}

	public ArrayList<GameContainer> getContainers() {
		return this.containers;
	}

}
