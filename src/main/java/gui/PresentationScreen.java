/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import bomberman.configuration.Configuration;
import dependencies.Images;
import dependencies.Sounds;
import language.utils.ImageUtilities;
import engine.core.input.Keyboard;
import fonts.Fonts;
import engine.core.input.GamePad;
import engine.core.input.GamePad.Buttons;
import utils.game.Screen;
import game.core.input.PlayerOneKeyboardController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Transparency;
import java.util.ArrayList;
import engine.core.input.IGamePadController;

/**
 *
 * @author Alfonso Andrés
 */
public class PresentationScreen extends Screen {
    
    private static PresentationScreen instance;
    private Image image, arrow;
    private ArrayList<Point> options;
    private final int optionsLength = 2;
    private int pointingOption = 0;
    private int selectedOption = -1;
    private Dimension windowSize;
    private Keyboard keyboard;
    private GamePad gamePad;
    private IGamePadController padController;
    public static final int START = 0, MAP_EDITOR = 1;
    private Font font;
    
    private PresentationScreen(JPanelContainer jPanelContainer) {
        super(jPanelContainer);
        init();
    }
    
    public static PresentationScreen getInstance(JPanelContainer jPanelContainer) {
        return instance == null ? (instance = new PresentationScreen(jPanelContainer)) : instance;
    }

    private void init() {
        padController = PlayerOneKeyboardController.getInstance();
        font = Fonts.getInstance().getJoystixMonospacce(24);
        image = ImageUtilities.createCompatibleVolatileImage(640, 560, Transparency.OPAQUE);
        arrow = Images.POINTER;
        options = new ArrayList<>();
        gamePad = new GamePad();
        windowSize = Configuration.getInstance().getWindowSize();
        keyboard = Keyboard.getInstance();
        addOptions();
    }

    private void start() {
        var g2d = (Graphics2D) image.getGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 640, 560);
        g2d.drawImage(Images.LOGO, 40, 20, 568, 347, null);
        drawStrings(g2d);
        drawArrow(g2d);
        g2d.dispose();
    }

    private void addOptions() {
        for(var i = 0; i < optionsLength; i++){
            options.add(getPoint(i));
        }
    }

    private Point getPoint(int i) {
        if(i == 0) return new Point(259,415);
        return new Point(259,455);
    }

    private void drawStrings(Graphics2D g2d) {
        for(var i = 0; i < optionsLength; i++){
            drawString(g2d, getString(i), options.get(i));
        }
    }
    
    private void drawString(Graphics2D g, String string, Point point) {
        g.setColor(new Color(127, 127, 127));
        g.setFont(font);
        g.drawString(string, point.x + 2, point.y + 2);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(string, point.x, point.y);
    }
    
    private String getString(int i){
        if(i == 0) return "START";
        else if(i == 1) return "MAP EDITOR";
        return "TOP";
    }
    
    public void nextOption() {
        System.out.println(image.getCapabilities(null).isAccelerated());
        var g2d = (Graphics2D) image.getGraphics();
        g2d.setColor(Color.BLACK);
        var point = options.get(pointingOption);
        g2d.fillRect(point.x - 55, point.y - 17, 20, 20);
        pointingOption = pointingOption == optionsLength - 1 ? 0 : ++pointingOption;
        drawArrow(g2d);
        g2d.dispose();
        System.out.println(image.getCapabilities(null).isAccelerated());
    }

    private void drawArrow(Graphics2D g2d) {
        var point = options.get(pointingOption);
        g2d.drawImage(arrow, point.x - 55, point.y - 17, 20, 20, null);
    }

    public int getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption() {
        selectedOption = pointingOption;
    }

    @Override
    public void restart() {
        selectedOption = 0;
        start();
    }

    @Override
    public void draw(final Graphics2D g2d) {
        g2d.drawImage(image, 0, 0, null);
    }

    @Override
    public void update(final long elapsedTime) {
        padController.update(gamePad);
        if(gamePad.isPress(Buttons.SELECT))
            nextOption();
        else if (gamePad.isPress(Buttons.START)) {
            setSelectedOption();
            Sounds.getInstance().stop(Sounds.TITLE_SCREEN);
            switch(selectedOption) {
                case START:
                    jPanelContainer.setScreen(Scene.STAGE);
                    break;
                case MAP_EDITOR:
                    jPanelContainer.setScreen(Scene.EDITOR);
                    break;
            }
        }
    }

    @Override
    public void setSIZE(Dimension d) {

    }

}
