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
            case JAPAN:
                japans.add(new Japan(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
                break;
            case ITALY:
                italys.add(new Italy(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
                break;
            case GERMANY:
                germanys.add(new Germany(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
                break;
            case TUTORIAL_ENEMY:
                tutorialEnemies.add(new TutorialEnemy(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
                break;
            case 100:
                spawnPoint = new Point(x * Game.TILES_SIZE, y * Game.TILES_SIZE);
                break;
            default:
                // Handle other values if necessary
                break;
        }
    }

    private void loadLevelData(int redValue, int x, int y) {
        if (redValue >= 50)
            lvlData[y][x] = 0;
        else
            lvlData[y][x] = redValue;
        
        switch (redValue) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 30:
            case 31:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
                grass.add(new Grass((int) (x * Game.TILES_SIZE), (int) (y * Game.TILES_SIZE) - Game.TILES_SIZE, getRndGrassType(x)));
                break;
            default:
                // Handle other values if necessary
                break;
        }
    }

    private void loadObjects(int blueValue, int x, int y) {
        switch (blueValue) {
            case RED_POTION:
            case BLUE_POTION:
                potions.add(new Potion(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
                break;
            case BOX:
            case BARREL:
                containers.add(new GameContainer(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
                break;
            case SPIKE:
                spikes.add(new Spike(x * Game.TILES_SIZE, y * Game.TILES_SIZE, SPIKE));
                break;
            case CANNON_LEFT:
            case CANNON_RIGHT:
                cannons.add(new Cannon(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
                break;
            case TREE_ONE:
            case TREE_TWO:
            case TREE_THREE:
                trees.add(new BackgroundTree(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
                break;
            default:
                // Handle other values if necessary
                break;
        }
    }

    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    // Getters and setters
    public int[][] getLevelData() {
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
