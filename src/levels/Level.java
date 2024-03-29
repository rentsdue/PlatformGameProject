package levels;

import static utilz.HelpMethods.*;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import entities.Melee;
import main.java.com.example.Game;
import objects.*;
import utilz.HelpMethods;

public class Level {
    
    private int[][] lvlData;
    private BufferedImage img;
    private ArrayList<Melee> melees;
    private ArrayList<Potion> potions;
    private ArrayList<Spike> spikes;
    private ArrayList<Cannon> cannons;
	private ArrayList<GameContainer> containers;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point spawnPoint;

    public Level(BufferedImage img) {
        this.img=img;
        createLevelData();
        createEnemies();
        createPotions();
        createContainers();
        createSpikes();
        createCannons();
        calcLvlOffsets();
        calcPlayerSpawn();
    }

    private void createCannons() {
        cannons = HelpMethods.GetCannons(img);
    }

    private void createSpikes() {
        spikes = HelpMethods.GetSpikes(img);
    }

    private void calcPlayerSpawn() {
        spawnPoint = GetPlayerSpawn(img);
    }

    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide-Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE*maxTilesOffset;
    }

    private void createEnemies() {
        melees = GetMelees(img);
    }

    private void createLevelData() {
        lvlData = GetLevelData(img);
    }

    public void createContainers() {
		containers = HelpMethods.GetContainers(img);
	}

	public void createPotions() {
		potions = HelpMethods.GetPotions(img);
	}

    public int getSpriteIndex(int x, int y) {
        return lvlData [y][x];
    }

    //Getters and setters
    public int [][] getLevelData() {
        return this.lvlData;
    }

    public int getLvlOffset() {
        return this.maxLvlOffsetX;
    }

    public ArrayList<Melee> getMeleeList() {
        return this.melees;
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

    public ArrayList<Spike> getSpikes() {
        return this.spikes;
    }

    public void setSpikes(ArrayList<Spike> spikes) {
        this.spikes = spikes;
    }

    public ArrayList<Cannon> getCannons() {
        return this.cannons;
    }

    public void setCannons(ArrayList<Cannon> cannons) {
        this.cannons = cannons;
    }

}
