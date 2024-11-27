
# Angry Birds Game (LibGDX)

This is a recreation of the classic Angry Birds game built using the **LibGDX** framework. The project demonstrates a simple version of the game featuring different types of birds, pigs, and blocks.


## Project Structure

```plaintext
src/main/java/com/yourgame/angrybirds/
│
├── AngryBirds.java           # Main class to run the game
├── screens/                  # Contains all screen classes
│   ├── MainMenuScreen.java   # Main menu of the game
│   ├── GameScreen.java       # Main gameplay screen
│   ├── PauseScreen.java      # Pause screen functionality
│   └── ...                   # Other screens as needed
│
├── birds/                    # Package for different bird types
│   ├── Bird.java
│   ├── BombBird.java
│   ├── ChuckwBird.java
│   └── RedBird.java
│
├── pigs/                     # Package for different pig types
│   ├── Pig
│   ├── Corporal.java
│   ├── King.java
│   └── Piggy.java
│
├── blocks/                   # Package for different block types
│   ├── Blocks.java
│   ├── StoneBlocks.java
│   ├── GlassBlocks.java
│   └── WoodBlocks.java
└── Assets                    #Helps in loading assets(help taken from youtube channel yt: coding dutchman)

```
## Running the program
To run the program you simply need to locate to the directory and  run the code " ./gradlew lwjgl3:run --stacktrace "
