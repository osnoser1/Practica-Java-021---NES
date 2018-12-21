package gui;

import controllers.CSpecialPanel;
import dependencies.Images;
import game.constants.Objects;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class JToolBarEditorButtons extends JToolBar {

    private static JToolBarEditorButtons instance;
    private final int buttonsLength = 11;
    private ArrayList<SpecialButton> buttons;

    private JToolBarEditorButtons() {
        super(JToolBar.VERTICAL);
        initComponents();
    }

    public static JToolBarEditorButtons getInstance() {
        return instance == null ? (instance = new JToolBarEditorButtons()) : instance;
    }

    private void initComponents() {
        this.setLayout(new java.awt.GridLayout(6, 2, 5, 5));
        this.setPreferredSize(new java.awt.Dimension(150, 560));
        this.setBackground(Color.white);
        this.setBorder(javax.swing.BorderFactory.createLineBorder(Color.gray));
        buttons = new ArrayList<>();
        initSpecialButtons();
    }

    private void initSpecialButtons() {
        var objects = Objects.values();
        for (var i = 0; i < this.buttonsLength; i++) {
            if (i > 1) {
                buttons.add(new SpecialButton(Objects.getInstance(objects[i].getValue())));
            } else {
                buttons.add(new SpecialButton(getImage(i)));
            }
            var button = buttons.get(i);
            button.addActionListener(new CSpecialPanel(button));
            button.setName(objects[i].name());
            this.add(buttons.get(i));
        }
    }

    private Image getImage(int i) {
//        int option=0;
//        if(i == option++) return new Brick(0, 0, 0).getImage().getSubImage(0, 0);
        return Images.FLOOR;
    }

}
