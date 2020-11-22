package Screens;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Screen;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Level.Player;
import Level.PlayerListener;
import Maps.TestMap;
import Maps.TutorialMap;
import Players.Cat;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;
import Maps.OneMap;
import Maps.TwoMap;
import Maps.ThreeMap;

import java.awt.*;

// This class is for when the platformer game is actually being played
public class PlayLevelScreen extends Screen implements PlayerListener {
    protected ScreenCoordinator screenCoordinator;
    protected Map map;
    protected Player player;
    protected PlayLevelScreenState playLevelScreenState;
    protected Stopwatch screenTimer = new Stopwatch();
    protected LevelClearedScreen levelClearedScreen;
    protected LevelLoseScreen levelLoseScreen;

    //LIVES
    private SpriteFont lives = new SpriteFont("3", 34, 38, "Comic Sans", 30, new Color(238, 232, 170));
    int numLives = 0;
    public void setNumLives(int in) {
        numLives = in;
    }
    public int getNumLives() {
        return numLives;
    }
    public void updateLivesText() {
        lives.setText(String.valueOf(numLives));
    }


    //END LIVES


    public PlayLevelScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;

    }
    public void setMap(Map in) {
        this.map = in;
    }

    public void initialize() {

        // define/setup map

        this.map = screenCoordinator.getMap(); //TestMap()
        map.reset();

        // setup player
        this.player = new Cat(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        this.player.setMap(map);
        this.player.addListener(this);
        this.player.setLocation(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        this.playLevelScreenState = PlayLevelScreenState.RUNNING;
    }

    public void update() {
        // based on screen state, perform specific actions
        switch (playLevelScreenState) {
            // if level is "running" update player and map to keep game logic for the platformer level going
            case RUNNING:
                //update score
                lives.setText(String.valueOf(player.getLife()));

                player.update();
                map.update(player);
                break;
            // if level has been completed, bring up level cleared screen
            case LEVEL_COMPLETED:
                levelClearedScreen = new LevelClearedScreen();
                levelClearedScreen.initialize();
                screenTimer.setWaitTime(2500);
                playLevelScreenState = PlayLevelScreenState.LEVEL_WIN_MESSAGE;
                break;
            // if level cleared screen is up and the timer is up for how long it should stay out, go back to main menu
            case LEVEL_WIN_MESSAGE:
                if (screenTimer.isTimeUp()) {
                    levelClearedScreen = null;
                    goBackToMenu();
                }
                break;
            // if player died in level, bring up level lost screen
            case PLAYER_DEAD:
                levelLoseScreen = new LevelLoseScreen(this);
                levelLoseScreen.initialize();
                playLevelScreenState = PlayLevelScreenState.LEVEL_LOSE_MESSAGE;
                break;
            // wait on level lose screen to make a decision (either resets level or sends player back to main menu)
            case LEVEL_LOSE_MESSAGE:
                levelLoseScreen.update();
                break;
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {

        // based on screen state, draw appropriate graphics
        switch (playLevelScreenState) {
            case RUNNING:
            case LEVEL_COMPLETED:
            case PLAYER_DEAD:
                map.draw(graphicsHandler);
                player.draw(graphicsHandler);
                break;
            case LEVEL_WIN_MESSAGE:
                levelClearedScreen.draw(graphicsHandler);
                break;
            case LEVEL_LOSE_MESSAGE:
                levelLoseScreen.draw(graphicsHandler);
                break;
        }

        graphicsHandler.drawImage(ImageLoader.load("heart.png"), 20, 2, 45, 45);
        lives.draw(graphicsHandler);
    }

    public PlayLevelScreenState getPlayLevelScreenState() {
        return playLevelScreenState;
    }

    @Override
    public void onLevelCompleted() {
        playLevelScreenState = PlayLevelScreenState.LEVEL_COMPLETED;
    }

    @Override
    public void onDeath() {
        playLevelScreenState = PlayLevelScreenState.PLAYER_DEAD;
    }

    public void resetLevel() {
        initialize();
    }

    public void goBackToMenu() {
        screenCoordinator.setGameState(GameState.MENU);
    }

    // This enum represents the different states this screen can be in
    private enum PlayLevelScreenState {
        RUNNING, LEVEL_COMPLETED, PLAYER_DEAD, LEVEL_WIN_MESSAGE, LEVEL_LOSE_MESSAGE
    }
}
