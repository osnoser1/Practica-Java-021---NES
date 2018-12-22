/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dependencies.Sounds;
import engine.core.game.Game;
import engine.core.game.GameScene;
import engine.core.game.Screen;

import java.awt.*;

/**
 * @author Alfonso Andrés
 */
public class BombermanGame implements Game {

    private Scene selectedScene;
    private Screen currentScreen;

    public BombermanGame() {
        init();
    }

    private void init() {
        currentScreen = PresentationScreen.getInstance();
        setScreen(Scene.MENU);
        Sounds.getInstance().play(Sounds.TITLE_SCREEN);
    }

    public void paint(final Graphics g) {
        currentScreen.draw((Graphics2D) g);
    }

    public void setScreen(GameScene scene) {
        selectedScene = (Scene) scene;
        currentScreen.restart();
        switch (this.selectedScene) {
            case MENU:
                currentScreen = PresentationScreen.getInstance();
                break;
            case STAGE:
                currentScreen = MessageScreen.getInstance();
                ((MessageScreen) currentScreen).startStageScreen();
                break;
            case GAME:
                currentScreen = GameScreen.getInstance();
                Sounds.getInstance().loop(Sounds.STAGE_THEME);
                ((GameScreen) currentScreen).enableIntelligence();
                JPanelInformation.getInstance().startCountdown();
                break;
            case EDITOR:
                break;
            case GAME_OVER:
                currentScreen = MessageScreen.getInstance();
                ((MessageScreen) currentScreen).startGameOverScreen();
                break;
            case BONUS:
                break;
        }
    }

    public void setSIZE(Dimension dim) {
        currentScreen.setSIZE(dim);
    }

    public void update(long elapsedTime) {
        currentScreen.update(elapsedTime, this);
    }

    @Override
    public Scene getSelectedScene() {
        return selectedScene;
    }

    public enum Scene implements GameScene {
        MENU,
        STAGE,
        GAME,
        BONUS,
        GAME_OVER,
        EDITOR
    }
}
