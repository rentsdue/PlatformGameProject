package levels;

import java.awt.image.BufferedImage;

import main.java.com.example.Game;
import utilz.LoadSave;

public class LevelManager {
    private Game game;
    private BufferedImage levelSprite;

    public LevelManager(Game game) {
        this.game=game;
        levelSprite= LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
    }
}
