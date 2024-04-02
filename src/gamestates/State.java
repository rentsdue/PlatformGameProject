package gamestates;

import ui.MenuButton;

import java.awt.event.MouseEvent;

import audio.AudioPlayer;
import main.java.com.example.Game;


public class State {

	protected Game game;

	public State(Game game) {
		this.game = game;
	}

	public boolean isIn(MouseEvent e, MenuButton mb) {
		return mb.getBounds().contains(e.getX(), e.getY());
	}

	public Game getGame() {
		return this.game;
	}

	//We don't need this method for now
	public void setGamestate(Gamestate state) {
		switch (state) {
		case MENU:
			game.getAudioPlayer().playSong(AudioPlayer.MAIN_MUSIC);
		case PLAYING:
			game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLevelIndex()); //Change this if needed later
		default:
			break;
		}
		Gamestate.state = state;
	} 
}