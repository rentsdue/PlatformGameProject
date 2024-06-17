package main;

import audio.AudioPlayer;
import gamestates.*;
import ui.AudioOptions;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonWriter;
import java.io.StringWriter;

public class Game {

    private GameOptions gameOptions;
    private AudioOptions audioOptions;
    private Playing playing;
    private Menu menu;
    private Credits credits;
    private AudioPlayer audioPlayer;

    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.5f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    public Game() {
        initClasses();
    }

    private void initClasses() {
        audioOptions = new AudioOptions(this);
        audioPlayer = new AudioPlayer();
        menu = new Menu(this);
        playing = new Playing(this);
        gameOptions = new GameOptions(this);
        credits = new Credits(this);
    }

    public void update() {
        switch (Gamestate.state) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                playing.startInstructionsTimer();
                playing.getTimer().start();
                break;
            case OPTIONS:
                gameOptions.update();
                break;
            case CREDITS:
                credits.update();
                break;
            default:
                System.exit(0);
                break;
        }
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("state", Gamestate.state.toString())
                // Add other necessary state details here
                .build();
    }

    public String render() {
        JsonObject gameState = toJson();
        StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
            jsonWriter.writeObject(gameState);
        }
        return stringWriter.toString();
    }

	public GameOptions getGameOptions() {
		return gameOptions;
	}

	public void setGameOptions(GameOptions gameOptions) {
		this.gameOptions = gameOptions;
	}

	public AudioOptions getAudioOptions() {
		return audioOptions;
	}

	public void setAudioOptions(AudioOptions audioOptions) {
		this.audioOptions = audioOptions;
	}

	public Playing getPlaying() {
		return playing;
	}

	public void setPlaying(Playing playing) {
		this.playing = playing;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Credits getCredits() {
		return credits;
	}

	public void setCredits(Credits credits) {
		this.credits = credits;
	}

	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}

	public void setAudioPlayer(AudioPlayer audioPlayer) {
		this.audioPlayer = audioPlayer;
	}

    // Getters and setters...

	
}
