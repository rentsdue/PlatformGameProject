package utilz;

import static utilz.Constants.EnemyConstants.ENEMY_CRAB;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.*;
import main.java.com.example.Game;

public class LoadSave {
    public static final String PLAYER_ATLAS= "player_sprites.png"; //Sprites
    public static final String LEVEL_ATLAS="outside_sprites.png";
    public static final String ENEMY_SPRITE="enemy_sprite.png";
    public static final String HEALTH_POWER_BAR="health_power_bar.png";

    public static final String LEVEL_ONE_DATA="level_one_data_long.png"; //Levels
    
    public static final String MENU_BACKGROUND="menu_background.png"; //Backgrounds
    public static final String GAME_BACKGROUND="game_background.png";
    public static final String PAUSE_BACKGROUND="pause_menu.png";
    public static final String CAVE_BACKGROUND="cave_background.png";

    public static final String MENU_BUTTONS="button_atlas.png"; //Buttons
    public static final String SOUND_BUTTONS="sound_button.png";
    public static final String URM_BUTTONS="urm_buttons.png";
    public static final String VOLUME_BUTTONS="volume_buttons.png";

    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img=null;
        InputStream is = LoadSave.class.getResourceAsStream("/images/" + fileName);
		try {
			img = ImageIO.read(is);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}

    public static int[][] GetLevelData() {
        BufferedImage img=GetSpriteAtlas(LEVEL_ONE_DATA);
        int[][] lvlData= new int [img.getHeight()][img.getWidth()];

        for (int j=0; j<img.getHeight(); j++) {
            for (int i=0; i<img.getWidth(); i++) {
                Color color= new Color(img.getRGB(i, j));
                int value= color.getRed();
                if (value>=48) {
                    value=0;
                }
                lvlData[j][i]=value;
            }
        }
        return lvlData;
    }

    public static ArrayList<Crab> getCrabs() {
        BufferedImage img=GetSpriteAtlas(LEVEL_ONE_DATA);
        ArrayList<Crab> list= new ArrayList<>();
        for (int j=0; j<img.getHeight(); j++) {
            for (int i=0; i<img.getWidth(); i++) {
                Color color= new Color(img.getRGB(i, j));
                int value=color.getGreen();
                if (value==ENEMY_CRAB) {
                    list.add(new Crab(i*Game.TILES_SIZE, j*Game.TILES_SIZE));
                }
            }
        }
        return list;
    }
    
}
