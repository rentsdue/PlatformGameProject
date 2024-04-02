package audio;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.*;

public class AudioPlayer {
    
    //Background music constants (add more if needed later)
    public static final int MAIN_MUSIC = 0; 

    //In-game sound effects
    public static final int DIE = 0;
    public static final int JUMP = 1;
    public static final int GAMEOVER = 2;
    public static final int LVL_COMPLETED = 3;
    public static final int ATTACK_1 = 4;
    public static final int ATTACK_2 = 5;
    public static final int ATTACK_3 = 6;

    private Clip[] songs, soundEffects;
    private int currentSongId = 0;
    private float volume = 1f;
    private boolean songMute, effectMute;
    private Random rand = new Random();

    public AudioPlayer() {
        loadSongs();
        loadEffects();
        playSong(MAIN_MUSIC);
    }

    private void loadSongs() {
        String[] names = {"main_music"};
        songs = new Clip[names.length];
        for (int i = 0; i < songs.length; i++) {
            songs[i] = getClip(names[i]);
        }
    }

    private void loadEffects() {
        //Order must be consistent with the order of constants labelled above
        String[] effectNames = {"die", "jump", "gameover", "lvlcompleted", "attack1", "attack2", "attack3"};
        soundEffects = new Clip [effectNames.length];
        for (int i = 0; i < soundEffects.length; i++) {
            soundEffects[i] = getClip(effectNames[i]);
        }
        updateEffectsVolume();
    }

    //Changing the sound volume
    public void toggleSongMute() {
        this.songMute = !songMute;
        for (Clip c: songs) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMute);
        }
    }

    public void toggleEffectMute() {
        this.effectMute = !effectMute;
        for (Clip c: soundEffects) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(effectMute);
        }
        if (!effectMute) {
            playEffect(JUMP);
        }
    }

    //Playing the audio files
    public void playSong(int song) {
        stopSong();
        currentSongId = song;
        updateSongVolume();
        songs[currentSongId].setMicrosecondPosition(0);
        soundEffects[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
        
    }

    public void playEffect(int effect){
        soundEffects[effect].setMicrosecondPosition(0);
        songs[currentSongId].start();
    }

    public void playAttackSound() {
        int start = 4;
        start += rand.nextInt(3);
        playEffect(start);
    }

    //Actually updating the volume
    private void updateSongVolume() {
        FloatControl gainControl = (FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum();
        gainControl.setValue(gain);
    }

    private void updateEffectsVolume() {
        for (Clip c: soundEffects) {
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }

    public void setVolume(float volume) {
        this.volume = volume;
        updateSongVolume();
        updateEffectsVolume();
    }

    public void stopSong() {
        if(songs[currentSongId].isActive()) {
            songs[currentSongId].stop();
        }
    }

    public void setLevelSong(int lvlIndex) {
        //We don't need this method for now if all of the songs are the same for each level
        if (lvlIndex % 2 == 0)
			playSong(MAIN_MUSIC);
		else
			playSong(MAIN_MUSIC);
    }

    public void lvlCompleted() {
        stopSong();
        playEffect(LVL_COMPLETED);
    }

    //Getting the audio clips
    private Clip getClip(String name) {
        URL url= getClass().getResource("/sfx/" + name + ".wav");
        AudioInputStream audio;
        try {
            audio = AudioSystem.getAudioInputStream(url);
            Clip c = AudioSystem.getClip();
            c.open(audio);
            return c;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
