# Platform Game - Inspired by [KaarinGaming's Platformer Tutorial](https://github.com/KaarinGaming/PlatformerTutorial/)

This platform game is a project inspired by the fantastic work done by KaarinGaming in the Platformer Tutorial repository. The original repository provided valuable insights and guidance, serving as a foundation for the development of this platform game.

## Overview

This platform game aims to showcase various game development concepts, including player controls, level design, enemy interactions, and object management. The project is implemented in Java and uses the Swing library for graphical rendering.

## Features

- **Player Controls:** Responsive and intuitive controls for character movement, jumping, and attacking.
- **Level Design:** Engaging levels with obstacles, enemies, and interactive elements.
- **Enemy Interactions:** Dynamic enemy behavior and combat interactions.
- **Object Management:** Efficient handling of game objects, including potions, containers, and other entities.

## Getting Started

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/PlatformGameProject.git
    ```

2. Open the project in your preferred Java development environment.

3. Run the game by typing:

    ```bash
    ./gradlew run
    ```

### Running the Game on CheerpJ

To run your game in a browser using CheerpJ, follow these steps:

1. **Build the JAR file:**

   Use Gradle to build the JAR file for your game:

   ```bash
   ./gradlew build
   ```

   This will generate a JAR file. Rename the JAR file to `PlatformGameProject.jar` and place it into the main repository folder.

2. **Prepare the CheerpJ environment:**

   - Inside the `PlatformGameProject` folder (aka the main repository folder), make sure you have the `PlatformGameProject.jar` file.
   - You should also have the necessary CheerpJ runtime files available, or you can link them from CheerpJ's CDN, which has already been done on the repository's index.html file.

3. **Start a local server:**

   You can use `npx` to run a simple HTTP server to serve your files. Run the following command in your terminal:

   ```bash
    npx http-server -p 8080
   ```

   This will start a local HTTP server and serve the contents of the `PlatformGameProject` folder, including the JAR file.

4. **Run the game in the browser:**

   Open a web browser and navigate to either of the following:

   ```
    http://192.168.56.1:8080
    http://192.168.0.103:8080
    http://127.0.0.1:8080
   ```

   You should now be able to run your Java game in the browser using CheerpJ Currently, it is still in development since it is currently too slow to run on the browser, so the game loop is currently a work in progress and needs to be heavily modified!

## Credits

This project is inspired by [KaarinGaming's Platformer Tutorial](https://github.com/KaarinGaming/PlatformerTutorial/). Special thanks to KaarinGaming for their comprehensive tutorial and open-source contributions.

## Contributing

Contributions are welcome! Feel free to submit bug reports, feature requests, or even pull requests to help improve the project.

## License

This project is licensed under the [MIT License](LICENSE), allowing for both personal and commercial use.
