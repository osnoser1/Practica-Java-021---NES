/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package gui;

import fuentes.Fonts;
import lenguaje.utils.ImageUtilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Timer;

/**
 *
 * @author Alfonso Andrés
 */
public class JPanelInformation {
    
    private int score;
    private static JPanelInformation instance;
    private int remainingTime = 200, remainingLives = 2;
    private final int optionsLength = 3;
    private Image image;
    private Timer timer;
    private Dimension SIZE;
    private Color background;
    private Font font;
    private Point[] pos;
    private boolean change;


    private JPanelInformation() {
        super();
        initComponents();
    }

    public static JPanelInformation getInstance() {
        return instance == null ? (instance = new JPanelInformation()) : instance;
    }

    private void initComponents() {
        SIZE = new Dimension(640, 60);
        font = Fonts.getInstance().getJoystixMonospacce(24);
        background = new Color(188, 188, 188);
        pos = new Point[]{new Point(20, 37), new Point(360, 37), new Point(480, 37)};
        image = ImageUtilities.createCompatibleVolatileImage(640, 60, Transparency.OPAQUE);
        var g2 = (Graphics2D) image.getGraphics();
        g2.setColor(background);
        g2.fillRect(0, 0, 640, 60);
        g2.dispose();
        timer = new Timer(1000, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decreaseCounter();
            }
        });
    }
    
    private void drawString(final Graphics g2, final String string, final Point point) {
        g2.setColor(background);
        g2.fillRect(point.x, point.y - 25, 300, 30);
        g2.setColor(Color.BLACK);
        g2.setFont(font);
        g2.drawString(string, point.x + 2, point.y + 2);
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        g2.drawString(string, point.x, point.y);
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void setScore(int core) {
        score = core;
    }

    public void increaseScore(int count) {
        score += count;
    }

    public int getScore() {
        return score;
    }

    public void setRemainingLives(int remainingLives) {
        this.remainingLives = remainingLives;
    }

    public void decreaseRemainingLives() {
        remainingLives = remainingLives < 0 ? -1 : --this.remainingLives;
    }

    public int getRemainingLives() {
        return remainingLives;
    }

    private void drawStrings(Graphics g2) {
        for(var i = 0; i < this.optionsLength; i++) {
            drawString(g2, getString(i), pos[i]);
        }
    }

    private String getString(int i) {
        if(i == 0)
            return "TIME " + remainingTime();
        if(i == 1)
            return score();
        return "LEFT " + remainingLives;
    }

    private String score() {
        return score + "";
    }

    private String remainingTime() {
        return remainingTime + "";
    }

    public void startCountdown() {
        remainingTime = 200;
        change = true;
        timer.start();
    }

    public void stopCountdown() {
        timer.stop();
        change = false;
    }

    private void decreaseCounter() {
        if(remainingTime == 0) {
            stopCountdown();
            return;
        }
        remainingTime--;
        change = true;
    }

    public void setSIZE(Dimension dim) {
        var y = (int)Math.round(dim.height / 14.0);
        SIZE = new Dimension(dim.width, y + y / 2);
        System.out.println(dim + " " + SIZE + " " + y);
    }
    
    public int getHeight() {
        return SIZE.height;
    }
    
    public void draw(final Graphics2D g2) {
        if (change) {
            var g2d = (Graphics2D) image.getGraphics();
            drawStrings(g2d);
            g2d.dispose();
            change = false;
        }
        g2.drawImage(image, 0, 0, null);
    }
    
}
