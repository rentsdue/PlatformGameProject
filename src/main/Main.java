package main;

public class Main {
    private static Game game;

    public static void main(String[] args) {
        init();
    }

    public static void init() {
        game = new Game();
    }

    public static void update() {
        if (game != null) {
            game.update();
        }
    }

    public static void render() {
        if (game != null) {
            game.render();  // We'll modify Game class to handle HTML canvas
        }
    }
}
