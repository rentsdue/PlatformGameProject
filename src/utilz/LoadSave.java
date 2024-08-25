package utilz;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import entities.PlayerCharacter;

public class LoadSave {
    
    //Players and enemy-related stuff
    public static final String PLAYER_USABALL = "player_usa.png";
    public static final String PLAYER_USSRBALL = "player_ussr.png";
    public static final String PLAYER_UKBALL = "player_uk.png";
    public static final String JAPAN_ATLAS = "japan_sprite.png";
    public static final String ITALY_ATLAS = "italy_sprite.png";
    public static final String GERMANY_ATLAS = "germany_sprite.png";
    public static final String HEALTH_POWER_BAR = "health_power_bar.png";

    //Level-related stuff
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String WATER_TOP = "water_atlas_animation.png";
	public static final String WATER_BOTTOM = "water.png";
    public static final String WARSHIP = "warship.png";
    public static final String TREE_ONE_ATLAS = "tree_one_atlas.png";
	public static final String TREE_TWO_ATLAS = "tree_two_atlas.png";
    public static final String GRASS_ATLAS = "grass_atlas.png";

    //Backgrounds and UI
    public static final String MENU_BACKGROUND = "menu_background.png"; 
    public static final String GAME_BACKGROUND = "game_background.png";
    public static final String PAUSE_BACKGROUND = "pause.png";
    public static final String CAVE_BACKGROUND = "cave_background.png";
    public static final String DEATH_SCREEN = "death_screen.png";
    public static final String OPTIONS_BACKGROUND = "options_menu.png";
    public static final String CREDITS = "credits_list.png";

    //Buttons
    public static final String MENU_BUTTONS = "button_atlas.png"; 
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String COMPLETED_IMG = "completed_sprite.png";

    //Objects
    public static final String POTION_ATLAS = "potions_sprites.png";
    public static final String OBJECT_ATLAS = "objects_sprites.png";
    public static final String TRAPS = "traps.png";
    public static final String CANNON_ATLAS = "cannon_atlas.png";
    public static final String PROJECTILE = "projectile.png";
    public static final String GAME_COMPLETED = "game_completed_ui.png";

    public static BufferedImage[][] loadAnimations(PlayerCharacter pc) {
        BufferedImage img = LoadSave.GetSpriteAtlas(pc.playerAtlas);
        BufferedImage[][] animations = new BufferedImage[pc.rowA][pc.colA];
        for (int j = 0; j < animations.length; j++)
            for (int i = 0; i < animations[j].length; i++)
                animations[j][i] = img.getSubimage(i * pc.spriteW, j * pc.spriteH, pc.spriteW, pc.spriteH);

        return animations;
    }

    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
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

    public static BufferedImage[] GetAllLevels() {
        List<String> levelNames = Arrays.asList("1.png", "2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "9.png", "10.png");
        BufferedImage[] levels = new BufferedImage[levelNames.size()];

        for (int i = 0; i < levels.length; i++) {
            try (InputStream is = LoadSave.class.getResourceAsStream("/levels/" + levelNames.get(i))) {
                if (is == null) {
                    System.err.println("File not found: " + levelNames.get(i));
                    System.exit(1);
                }
                levels[i] = ImageIO.read(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return levels;
    }
    
}
