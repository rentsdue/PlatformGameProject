package utilz;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

public class LoadSave {
    public static final String PLAYER_ATLAS = "player_sprites.png"; //Sprites
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String ENEMY_SPRITE = "enemy_sprite.png";
    public static final String HEALTH_POWER_BAR = "health_power_bar.png";

    public static final String LEVEL_ONE_DATA = "1.png"; //Levels
    
    public static final String MENU_BACKGROUND = "menu_background.png"; //Backgrounds
    public static final String GAME_BACKGROUND = "game_background.png";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String CAVE_BACKGROUND = "cave_background.png";

    public static final String MENU_BUTTONS = "button_atlas.png"; //Buttons
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String COMPLETED_IMG = "completed_sprite.png";

    public static final String POTION_ATLAS = "potions_sprites.png";
    public static final String OBJECT_ATLAS = "objects_sprites.png";
    public static final String TRAP_ATLAS = "trap_atlas.png";

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
        URL url= LoadSave.class.getResource("/images/levels"); 
        File file =null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];

        for (int i=0; i<filesSorted.length; i++) {
            for (int j=0; j<files.length; j++) {
                if (files[j].getName().equals((i + 1) + ".png")) {
                    filesSorted[i] = files[j];
                }
            }
        }
        
        for (File f: files) { //Testing purposes
        System.out.println("file "+ f.getName()); 
        }

        for (File f: files) {
        System.out.println("file sorted "+ f.getName()); 
        } 

        BufferedImage[] imgs= new BufferedImage[filesSorted.length];
        for (int i=0; i<imgs.length;i++) {
            try {
                imgs[i]=ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return imgs;
    }
    
}
