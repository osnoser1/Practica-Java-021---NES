/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controllers.CMapEditorScreen;
import dependencies.Images;
import engine.core.map.Map;
import engine.core.graphics.Sprite;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import game.constants.Objects;

/**
 *
 * @author AlfonsoAndrés
 */
public class MapEditorScreen extends JPanel {

    private static MapEditorScreen instance;
    private PanelEditor panelEditor;
    private final Dimension[] sizeRatio = {new Dimension(656, 620), new Dimension(1240, 520)};
    private Point bombermanPosition;

    private MapEditorScreen() {
        super(new BorderLayout());
        initComponents();
    }

    public static MapEditorScreen getInstance() {
        return instance == null ? (instance = new MapEditorScreen()) : instance;
    }

    private void initComponents() {
        panelEditor = new PanelEditor();
        bombermanPosition = new Point(40, 40);
        var aux = new JScrollPane(panelEditor);
        add(aux);
        add(JToolBarEditorButtons.getInstance(), BorderLayout.EAST);
        add(JToolBarEditorOptions.getInstance(), BorderLayout.SOUTH);
        panelEditor.setRequestFocusEnabled(true);
        panelEditor.requestFocusInWindow();
        panelEditor.setFocusable(true);
        panelEditor.addMouseListener(CMapEditorScreen.getInstance());
        panelEditor.addMouseMotionListener(CMapEditorScreen.getInstance());
        draw(Objects.getInstance("B"), bombermanPosition);
    }

    public void scale(Dimension size) {
        panelEditor.setSize(sizeRatio[0].width * size.width / getWidth(),
                sizeRatio[0].height * size.height / getHeight());
        panelEditor.repaint();
    }

    public void draw(Sprite sprite, Point position) {
        // No simplify
        var x = position.x * Map.COLUMNS / panelEditor.getWidth() * sizeRatio[1].width / Map.COLUMNS;
        var y = position.y * Map.ROWS / panelEditor.getHeight() * sizeRatio[1].height / Map.ROWS;
        var g = panelEditor.getImage().getGraphics();
        if ("B".equals(sprite.getId())) {
            g.drawImage(Images.FLOOR, bombermanPosition.x, bombermanPosition.y, sizeRatio[1].width / Map.COLUMNS, sizeRatio[1].height / Map.ROWS, null);
//            Map.getInstance().setObject("V",
//                    (short) (Map.ROWS * bombermanPosition.y / panelEditor.getHeight()),
//                    (short) (Map.COLUMNS * bombermanPosition.x / panelEditor.getWidth()));
            bombermanPosition = new Point(x, y);
        }
        if (!isNotOnThePlayer(x, y) && !"B".equals(sprite.getId()))
            return;
        g.drawImage(Images.FLOOR, x, y, sizeRatio[1].width / Map.COLUMNS, sizeRatio[1].height / Map.ROWS, null);
//        g.drawImage(sprite.getSpriteActual(), x, y, sizeRatio[1].width / Map.COLUMNS, sizeRatio[1].height / Map.ROWS, null);
//        Map.getInstance().setObject(sprite.getId(),
//                (short) (Map.ROWS * position.y / panelEditor.getHeight()),
//                (short) (Map.COLUMNS * position.x / panelEditor.getWidth()));
        panelEditor.repaint();
    }

    private boolean isNotOnThePlayer(int x, int y) {
        return x != bombermanPosition.x || y != bombermanPosition.y;
    }

    private class PanelEditor extends JPanel {

        private BufferedImage image;

        PanelEditor() {
            setLayout(null);
            setSize(sizeRatio[1]);
            setPreferredSize(getSize());
            initImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }

        public BufferedImage getImage() {
            return image;
        }

        private void initImage() {
            image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            var g = image.getGraphics();
            var x = getWidth() / Map.COLUMNS;
            var y = getHeight() / Map.ROWS;
            var map = Map.getInstance();
//            for (short i = 0; i < Map.ROWS; i++)
//                for (int j = 0; j < Map.COLUMNS; j++)
//                    switch (map.getObject(i, j)) {
//                        case "V":
//                            g.drawImage(Images.FLOOR, j * x, i * y, x, y, this);
//                            break;
//                        case "A":
//                            g.drawImage(Images.STEEL_BLOCK, j * x, i * y, x, y, this);
//                            break;
//                    }
        }

    }

}
