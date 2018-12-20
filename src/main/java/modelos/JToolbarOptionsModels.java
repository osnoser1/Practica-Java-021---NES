/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Alfonso Andrés
 */
public class JToolbarOptionsModels {

    private JFileChooser jFileChooser;
    private FileNameExtensionFilter[] filter;

    /*

     public JToolbarOptionsModels() {
        initComponents();
    }

     private void initComponents() {
        filter = new FileNameExtensionFilter[1];
        jFileChooser = new JFileChooser() {
            @Override
            public void approveSelection() {
                File f = getSelectedFile();
                if (f.exists() && getDialogType() == SAVE_DIALOG) {
                    int result = JOptionPane.showConfirmDialog(getTopLevelAncestor(),
                            "The selected file " + f.getName() + " already exists.\nDo you want to overwrite it?",
                            "Confirm save as",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                    switch (result) {
                        case JOptionPane.YES_OPTION:
                            super.approveSelection();
                            return;
                        default:
                            return;
                    }
                } else if (getDialogType() == SAVE_DIALOG) {
                    var size = f.getName().length();
                    if (size > 3) {
                        if (!f.getName().substring(size - 4).equalsIgnoreCase(".map"))
                            setSelectedFile(new File(f + ".map"));
                    } else
                        setSelectedFile(new File(f + ".map"));
                }
                super.approveSelection();
            }
        };
        this.jFileChooser.removeChoosableFileFilter(this.jFileChooser.getAcceptAllFileFilter());
        initFileFilter();
    }

    public void start() {
//        var jPanelContainer = JPanelContainer.getInstance();
//        var messageScreen = MessageScreen.getInstance(jPanelContainer);
//        jPanelContainer.removeAll();
//        jPanelContainer.add(messageScreen);
//        messageScreen.startStageScreen();
//        messageScreen.setMAX_LEVEL((short)1);
//        new Thread.threadPresentationPanel().start();
    }

    public void saveMap() {
        var option = this.jFileChooser.showSaveDialog(JPanelContainer.getInstance());
        if (option == JFileChooser.APPROVE_OPTION)
            saveFile(this.jFileChooser.getSelectedFile());
    }

    public void loadMap() {
        var option = this.jFileChooser.showOpenDialog(JPanelContainer.getInstance());
        if (option == JFileChooser.APPROVE_OPTION)
            loadFile(this.jFileChooser.getSelectedFile());
    }

    private void saveFile(File selectedFile) {
        String[][] map = Map.getInstance().getMap();
        var text = "";
        for (var i = 0; i < Map.ROWS; i++) {
            for (var j = 0; j < Map.COLUMNS; text += map[i][j] + " ", j++);
            text += "\r\n";
        }
        FileManager.getInstance().saveFile(selectedFile, text);
    }

    private void loadFile(File selectedFile) {
        var string = FileManager.getInstance().loadFile(selectedFile);
        var map = new String[Map.ROWS][Map.COLUMNS];
        for (int c = 0, i = 0, j = 0, size = string.length(), size2 = Map.ROWS * Map.COLUMNS * 2 + Map.ROWS; c < size && c <= size2; c++) {
            var letter = string.charAt(c);
            if (letter == '\n') {
                i++;
                j = 0;
            } else if (letter != ' ')
                map[i][j++] = Character.toString(letter);
        }
        drawMap(map);
    }

    private void initFileFilter() {
        filter[0] = new FileNameExtensionFilter("Map files (*.map)", "map");
        jFileChooser.setFileFilter(filter[0]);
    }

     private void drawMap(String[][] map) {
        var map1 = Map.getInstance();
        map1.setMap(map);
        var gameScreen = GameScreen.getInstance(null);
        gameScreen.eraseEnemies();
        for (var i = 1; i < Map.ROWS; i++)
            for (var j = 1; j < Map.COLUMNS; j++) {
                map1.setObject(map[i][j], (short) i, (short) j);
                gameScreen.drawTile(j, i);
            }
    }

*/

}
