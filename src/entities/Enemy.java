package entities;
import static utilz.Constants.EnemyConstants.GetSpriteAmount;

public abstract class Enemy extends Entity {
    private int aniIndex, enemyState, enemyType;
    private int aniTick, aniSpeed=25;
    
    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType=enemyType;
        initHitBox(x, y, width, height);
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >=aniSpeed) {
            aniTick=0;
            aniIndex++;
            if (aniIndex>=GetSpriteAmount(enemyType, enemyState)) {
                aniIndex=0;
            }
        } 
    }

    public void update() {
        updateAnimationTick();
    }

    //Getters and setters

    public int getAniIndex() {
        return this.aniIndex;
    }

    public void setAniIndex(int aniIndex) {
        this.aniIndex = aniIndex;
    }

    public int getEnemyState() {
        return this.enemyState;
    }

    public void setEnemyState(int enemyState) {
        this.enemyState = enemyState;
    }

    public int getEnemyType() {
        return this.enemyType;
    }

    public void setEnemyType(int enemyType) {
        this.enemyType = enemyType;
    }

    public int getAniTick() {
        return this.aniTick;
    }

    public void setAniTick(int aniTick) {
        this.aniTick = aniTick;
    }

    public int getAniSpeed() {
        return this.aniSpeed;
    }

    public void setAniSpeed(int aniSpeed) {
        this.aniSpeed = aniSpeed;
    }
}
