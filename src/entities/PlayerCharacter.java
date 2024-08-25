package entities;

import main.Game;
import utilz.LoadSave;

import static utilz.Constants.PlayerConstants.*;

public enum PlayerCharacter {

    UKBALL(5, 6, 3, 1, 3, 4, 8,
            0, 1, 2, 3, 4, 5, 6,
            LoadSave.PLAYER_UKBALL, 7, 8, 64, 40,
            20, 27, 21, 7, 100, 25, 0.03f * Game.SCALE),
    USABALL(5, 6, 3, 1, 3, 4, 8,
            0, 1, 2, 3, 4, 5, 6,
            LoadSave.PLAYER_USABALL, 7, 8, 64, 40,
            20, 27, 21, 7, 75, 50, 0.03f * Game.SCALE),
    USSRBALL(5, 6, 3, 1, 3, 4, 8,
            0, 1, 2, 3, 4, 5, 6,
            LoadSave.PLAYER_USSRBALL, 7, 8, 64, 40,
            20, 27, 21, 7, 75, 25, 0.02f * Game.SCALE);


    public int spriteA_IDLE, spriteA_RUNNING, spriteA_JUMP, spriteA_FALLING, spriteA_ATTACK, spriteA_HIT, spriteA_DEAD;
    public int rowIDLE, rowRUNNING, rowJUMP, rowFALLING, rowATTACK, rowHIT, rowDEAD;
    public String playerAtlas;
    public int rowA, colA;
    public int spriteW, spriteH;
    public int hitBoxW, hitBoxH;
    public int xDrawOffset, yDrawOffset;
    public int maxHealth, damageDealt;
    public float gravity;

    PlayerCharacter(int spriteA_IDLE, int spriteA_RUNNING, int spriteA_JUMP, int spriteA_FALLING, int spriteA_ATTACK, int spriteA_HIT, int spriteA_DEAD,
                    int rowIDLE, int rowRUNNING, int rowJUMP, int rowFALLING, int rowATTACK, int rowHIT, int rowDEAD,
                    String playerAtlas, int rowA, int colA, int spriteW, int spriteH,
                    int hitBoxW, int hitBoxH,
                    int xDrawOffset, int yDrawOffset, int maxHealth, int damageDealt, float gravity) {

        this.spriteA_IDLE = spriteA_IDLE;
        this.spriteA_RUNNING = spriteA_RUNNING;
        this.spriteA_JUMP = spriteA_JUMP;
        this.spriteA_FALLING = spriteA_FALLING;
        this.spriteA_ATTACK = spriteA_ATTACK;
        this.spriteA_HIT = spriteA_HIT;
        this.spriteA_DEAD = spriteA_DEAD;

        this.rowIDLE = rowIDLE;
        this.rowRUNNING = rowRUNNING;
        this.rowJUMP = rowJUMP;
        this.rowFALLING = rowFALLING;
        this.rowATTACK = rowATTACK;
        this.rowHIT = rowHIT;
        this.rowDEAD = rowDEAD;

        this.playerAtlas = playerAtlas;
        this.rowA = rowA;
        this.colA = colA;
        this.spriteW = spriteW;
        this.spriteH = spriteH;

        this.hitBoxW = hitBoxW;
        this.hitBoxH = hitBoxH;

        this.xDrawOffset = (int) (xDrawOffset * Game.SCALE);
        this.yDrawOffset = (int) (yDrawOffset * Game.SCALE);

        this.maxHealth = maxHealth;
        this.damageDealt = damageDealt;
        this.gravity = gravity;
    }

    public int getSpriteAmount(int player_action) {
        return switch (player_action) {
            case IDLE -> spriteA_IDLE;
            case RUNNING -> spriteA_RUNNING;
            case JUMP -> spriteA_JUMP;
            case FALLING -> spriteA_FALLING;
            case ATTACK -> spriteA_ATTACK;
            case HIT -> spriteA_HIT;
            case DEAD -> spriteA_DEAD;
            default -> 1;
        };
    }

    public int getRowIndex(int player_action) {
        return switch (player_action) {
            case IDLE -> rowIDLE;
            case RUNNING -> rowRUNNING;
            case JUMP -> rowJUMP;
            case FALLING -> rowFALLING;
            case ATTACK -> rowATTACK;
            case HIT -> rowHIT;
            case DEAD -> rowDEAD;
            default -> 1;
        };
    }

}