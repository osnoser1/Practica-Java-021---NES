/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import bomberman.configuration.Configuration;
import dependencies.Sound;
import dependencies.Sounds;
import engine.core.game.Game;
import engine.core.game.Screen;
import engine.core.input.Keyboard;
import fonts.Fonts;
import gui.BombermanGame.Scene;
import language.utils.ImageUtilities;

import java.awt.*;

/**
 * @author Alfonso Andrés
 */
public class MessageScreen extends Screen {

    private static MessageScreen instance;
    private short level = 1, MAX_LEVEL = 50;
    private Image image;
    private Dimension windowSize;
    private Sound sound;
    private Keyboard keyboard;
    private Font f1;
    private Color c;

    private MessageScreen() {
        init();
    }

    public static MessageScreen getInstance() {
        return instance == null ? (instance = new MessageScreen()) : instance;
    }

    private void init() {
        image = ImageUtilities.createCompatibleVolatileImage(640, 560, Transparency.OPAQUE);
        c = new Color(127, 127, 127);
        f1 = Fonts.getInstance().getJoystixMonospacce(25);
        keyboard = Keyboard.getInstance();
        windowSize = Configuration.getInstance().getWindowSize();
    }

    public void increaseLevel() {
        if (level != MAX_LEVEL)
            level++;
    }

    public boolean endgame() {
        return level == MAX_LEVEL;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level > 50 ? 50 : level < 1 ? 1 : level;
    }

    public short getMAX_LEVEL() {
        return MAX_LEVEL;
    }

    public void setMAX_LEVEL(short MAX_LEVEL) {
        this.MAX_LEVEL = MAX_LEVEL > 50 ? 50 : MAX_LEVEL < 1 ? 1 : MAX_LEVEL;
    }

    public void startStageScreen() {
        drawString("STAGE  " + this.level);
        sound = Sounds.getInstance().play(Sounds.LEVEL_START);
    }

    public void startGameOverScreen() {
        drawString("GAME OVER");
        sound = Sounds.getInstance().play(Sounds.GAME_OVER);
    }

    public void startBonusStage() {
        drawString("BONUS STAGE");
    }

    private void drawString(String string) {
        var g2D = (Graphics2D) image.getGraphics();
        g2D.setBackground(Color.BLACK);
        g2D.fillRect(0, 0, 640, 560);
        g2D.setColor(c);
        g2D.setFont(f1);
        g2D.drawString(string, 199, 298);
        g2D.setColor(Color.WHITE);
        g2D.drawString(string, 197, 296);
        g2D.dispose();
    }

    @Override
    public void restart() {

    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, 0, 0, null);
    }

    @Override
    public void update(final long elapsedTime, final Game game) {
        if (sound != null && sound.isPlaying())
            System.out.println("Sound: " + sound.getFramePosition() + " " + sound.getFrameLength());
        final var scene = (Scene) game.getSelectedScene();
        switch (scene) {
            case STAGE:
                if (sound == null || !sound.isPlaying())
                    game.setScreen(Scene.GAME);
                break;
            case GAME_OVER:
                if (keyboard.isKeyPressed()) {
                    game.setScreen(Scene.MENU);
                    Sounds.getInstance().stop(Sounds.GAME_OVER);
                }
                break;
            case BONUS:
                break;
        }
    }

    @Override
    public void setSIZE(Dimension d) {

    }

}
