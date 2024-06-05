package levels;

import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.ObjectConstants.*;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.*;
import main.Game;
import objects.*;

public class Level {
    
    private int[][] lvlData;
    private BufferedImage img;

    private ArrayList<Japan> japans = new ArrayList<>();
    private ArrayList<Potion> potions = new ArrayList<>();
    private ArrayList<Spike> spikes = new ArrayList<>();
    private ArrayList<Cannon> cannons = new ArrayList<>();
	private ArrayList<GameContainer> containers = new ArrayList<>();
    private ArrayList<Italy> italys = new ArrayList<>();
    private ArrayList<Germany> germanys = new ArrayList<>();
    private ArrayList<TutorialEnemy> tutorialEnemies = new ArrayList<>();
    private ArrayList<BackgroundTree> trees = new ArrayList<>();
    private ArrayList<Grass> grass = new ArrayList<>();

    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point spawnPoint;

    public Level(BufferedImage img) {
        this.img = img;
        lvlData = new int[img.getHeight()][img.getWidth()];
		loadLevel();
        calcLvlOffsets();
    }

    private void loadLevel() {
		for (int y = 0; y < img.getHeight(); y++)
			for (int x = 0; x < img.getWidth(); x++) {
				Color c = new Color(img.getRGB(x, y));
				int red = c.getRed();
				int green = c.getGreen();
				int blue = c.getBlue();

				loadLevelData(red, x, y);
				loadEntities(green, x, y);
				loadObjects(blue, x, y);
			}
	}

    private int getRndGrassType(int xPos) {
		return xPos % 2;
	}

	private void loadEntities(int greenValue, int x, int y) {
		switch (greenValue) {
            case JAPAN -> japans.add(new Japan(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
            case ITALY -> italys.add(new Italy(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
            case GERMANY -> germanys.add(new Germany(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
            case TUTORIAL_ENEMY -> tutorialEnemies.add(new TutorialEnemy(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
            case 100 -> spawnPoint = new Point(x * Game.TILES_SIZE, y * Game.TILES_SIZE);
            }
	}

    private void loadLevelData(int redValue, int x, int y) {
		if (redValue >= 50)
			lvlData[y][x] = 0;
		else
			lvlData[y][x] = redValue;
		switch (redValue) {
		case 0, 1, 2, 3, 30, 31, 33, 34, 35, 36, 37, 38, 39 -> grass.add(new Grass((int) (x * Game.TILES_SIZE), (int) (y * Game.TILES_SIZE) - Game.TILES_SIZE, getRndGrassType(x)));
		}
	}

    private void loadObjects(int blueValue, int x, int y) {
		switch (blueValue) {
		case RED_POTION, BLUE_POTION -> potions.add(new Potion(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
		case BOX, BARREL -> containers.add(new GameContainer(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
		case SPIKE -> spikes.add(new Spike(x * Game.TILES_SIZE, y * Game.TILES_SIZE, SPIKE));
		case CANNON_LEFT, CANNON_RIGHT -> cannons.add(new Cannon(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
        case TREE_ONE, TREE_TWO, TREE_THREE -> trees.add(new BackgroundTree(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
		}
	}


    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
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

    public ArrayList<Japan> getJapans() {
        return this.japans;
    }

    public ArrayList<Italy> getItalys() {
        return this.italys;
    }

    public ArrayList<Germany> getGermanys() {
        return this.germanys;
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

    public ArrayList<TutorialEnemy> getTutorialEnemies() {
        return this.tutorialEnemies;
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

    public ArrayList<BackgroundTree> getTrees() {
        return this.trees;
    }

    public ArrayList<Grass> getGrasses() {
        return this.grass;
    }
}
