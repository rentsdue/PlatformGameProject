package main;

import java.awt.Graphics;

import audio.AudioPlayer;
import gamestates.*;
import ui.AudioOptions;

public class Game {
    private GameOptions gameOptions;
    private AudioOptions audioOptions;
    private Playing playing;
    private Menu menu;
    private Credits credits;
    private AudioPlayer audioPlayer;

    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.5f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    public Game() {
        initClasses();
        startGameLoop();
    }

    private void initClasses() {
        audioOptions = new AudioOptions(this);
        audioPlayer = new AudioPlayer();
        menu = new Menu(this);
        playing = new Playing(this);
        gameOptions = new GameOptions(this);
        credits = new Credits(this);
    }

    private void startGameLoop() {
        new Thread(this::run).start();
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
            case QUIT:
                System.exit(0);
                break;
        }
    }

    public void render() {
        // JavaScript will call a render method, so we won't use Graphics here
        // Instead, this can trigger a JavaScript method to handle the rendering
        renderNative();
    }

    private native void renderNative(); // Native method to be implemented in JavaScript

    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                render();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    public void windowFocusLost() {
        if (Gamestate.state == Gamestate.PLAYING)
            playing.getPlayer().resetDirBooleans();
    }

	//Getters and Setters
	public GameOptions getGameOptions() {
		return this.gameOptions;
	}

	public void setGameOptions(GameOptions gameOptions) {
		this.gameOptions = gameOptions;
	}

	public AudioOptions getAudioOptions() {
		return this.audioOptions;
	}

	public void setAudioOptions(AudioOptions audioOptions) {
		this.audioOptions = audioOptions;
	}

	public Playing getPlaying() {
		return this.playing;
	}

	public void setPlaying(Playing playing) {
		this.playing = playing;
	}

	public Menu getMenu() {
		return this.menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Credits getCredits() {
		return this.credits;
	}

	public void setCredits(Credits credits) {
		this.credits = credits;
	}

	public AudioPlayer getAudioPlayer() {
		return this.audioPlayer;
	}

	public void setAudioPlayer(AudioPlayer audioPlayer) {
		this.audioPlayer = audioPlayer;
	}
}
