package entities;

public abstract class Enemy extends Entity {
    private int animationIndex, enemyState, enemyType;
    private int aniTick, aniSpeed=25;
    
    public Enemy(float x, float y, int width, int height) {
        super(x, y, width, height);
    }
}
