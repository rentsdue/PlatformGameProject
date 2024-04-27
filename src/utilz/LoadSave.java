package utilz;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class LoadSave {
    
    //Players and enemy-related stuff
    public static final String PLAYER_ATLAS = "player_sprites.png";
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

    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
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

    public static BufferedImage[] GetAllLevels() {
        URL url = LoadSave.class.getResource("/images/levels");
        if (url == null) {
            System.err.println("Error: Could not find '/images/levels' resource.");
            return new BufferedImage[0];
        }
    
        List<BufferedImage> images = new ArrayList<>();
        try (InputStream is = url.openStream()) {
            // Assuming the images are named sequentially as "1.png", "2.png", etc.
            for (int i = 1; i <= 10; i++) { // Assuming 10 images
                String imageName = i + ".png";
                InputStream imageStream = LoadSave.class.getResourceAsStream("/images/levels/" + imageName);
                if (imageStream != null) {
                    BufferedImage image = ImageIO.read(imageStream);
                    images.add(image);
                } else {
                    System.err.println("Error: Could not find image file '" + imageName + "'.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return images.toArray(new BufferedImage[0]);
    }
    
}
