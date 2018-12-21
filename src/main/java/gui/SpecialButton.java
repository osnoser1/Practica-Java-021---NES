package gui;

import engine.core.graphics.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SpecialButton extends JPanel implements MouseMotionListener, MouseListener {

    Timer animation;
    private Image image;
    private Sprite character;
    private MouseListener listener;
    private ActionListener listener1;
    private boolean inside;

    public SpecialButton(Sprite character) {
        this.character = character;
        initComponents();
    }

    public SpecialButton(Image image) {
        this.image = image;

        initComponents();
    }

    private void initComponents() {
        this.setBackground(Color.lightGray);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.setBorder(BorderFactory.createLineBorder(Color.gray));
        this.animation = new Timer(150, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
    }

    public void addActionListener(ActionListener l) {
        this.listener1 = l;
    }

    private void update() {
//        character.getLeft().spriteMovement();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (character != null) {
//            character.draw((Graphics2D)g,2*getWidth()/7,getHeight()/5,3*getWidth()/7,3*getHeight()/5);

            if (inside)
                animation.start();
            else
                animation.stop();

            repaint();
        } else if (image != null) {
            g.drawImage(image, 2 * getWidth() / 7, getHeight() / 5, 3 * getWidth() / 7, 3 * getHeight() / 5, null);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (listener != null)
            listener.mousePressed(e);
        if (listener1 != null)
            listener1.actionPerformed(null);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setBackground(Color.LIGHT_GRAY);
        if (character != null) {

            inside = false;
//            character.getAnimation().resetCont();
            repaint();
        }

        if (listener != null)
            listener.mouseExited(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setBackground(new Color(100, 100, 255));
        inside = true;
        repaint();
        if (listener != null)
            listener.mouseEntered(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (listener != null)
            listener.mouseReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (listener != null)
            listener.mouseClicked(e);
    }

}