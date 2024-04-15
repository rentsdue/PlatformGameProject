package utilz;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

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
        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];

        for (int i=0; i<filesSorted.length; i++) {
            for (int j = 0; j < files.length; j++) {
                if (files[j].getName().equals((i + 1) + ".png")) {
                    filesSorted[i] = files[j];
                }
            }
        }
        
        /* for (File f: files) { //Testing purposes
        System.out.println("file "+ f.getName()); 
        }

        for (File f: files) {
        System.out.println("file sorted "+ f.getName()); 
        } */

        BufferedImage[] imgs = new BufferedImage[filesSorted.length];
        for (int i = 0; i < imgs.length; i++) {
            try {
                imgs[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return imgs;
    }
    
}
