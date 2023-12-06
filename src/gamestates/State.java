package gamestates;

import main.java.com.example.*;

public class State {

	protected Game game;

	public State(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}
}