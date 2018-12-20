/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dependencias.Sounds;
import utilidades.juego.Screen;
import utilidades.juego.Screen.Scene;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
 * @author Alfonso Andrés
 */
public class JPanelContainer extends JComponent {

    private static JPanelContainer instance;
    private Screen currentScreen;
    public Scene selectedScene;

    private JPanelContainer() {
        init();
    }

    public static JPanelContainer getInstance() {
        return instance == null ? (instance = new JPanelContainer()) : instance;
    }

    private void init() {
        currentScreen = PresentationScreen.getInstance(this);
//        setIgnoreRepaint(true);
//        setFocusable(false);
        setScreen(Scene.MENU);
        Sounds.getInstance().play(Sounds.TITLE_SCREEN);
    }

    //    @Override
    public void paint(final Graphics g) {
        var g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        currentScreen.draw(g2d);
    }

//    @Override
//    public void paintAll(Graphics g) {
//    }
//
//    @Override
//    public void update(Graphics g) {
//    }
//
//    @Override
//    public void repaint() {
//    }

    public void setScreen(Scene scene) {
        selectedScene = scene;
        currentScreen.restart();
        switch (scene) {
            case MENU:
                currentScreen = PresentationScreen.getInstance(this);
                break;
            case STAGE:
                currentScreen = MessageScreen.getInstance(this);
                ((MessageScreen) currentScreen).startStageScreen();
                break;
            case GAME:
                currentScreen = GameScreen.getInstance(this);
                Sounds.getInstance().loop(Sounds.STAGE_THEME);
                ((GameScreen) currentScreen).enableIntelligence();
                JPanelInformation.getInstance().startCountdown();
                break;
            case EDITOR:
                break;
            case GAME_OVER:
                currentScreen = MessageScreen.getInstance(this);
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
        currentScreen.update(elapsedTime);
    }

}
