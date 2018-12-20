/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import gui.MapEditorScreen;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import juego.constantes.Objects;

/**
 *
 * @author AlfonsoAndrés
 */
public class CMapEditorScreen extends MouseAdapter{

    private static CMapEditorScreen instance;
    private String selectedObject;
    
    private CMapEditorScreen() {
    }

    public static CMapEditorScreen getInstance() {
        return instance == null ? (instance = new CMapEditorScreen()) : instance;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        draw(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        draw(e);
    }
    
    private void draw(MouseEvent e) {
        var mapEditorScreen = MapEditorScreen.getInstance();
        if("V".equals(selectedObject)){
//            mapEditorScreen.draw(mapEditorScreen.getSpriteFloor(), e.getPoint());
        }else{
            mapEditorScreen.draw(Objects.getInstance(selectedObject), e.getPoint());
        }
    }

    /**
     * @return the selectedObject
     */
    public String getSelectedObject() {
        return selectedObject;
    }

    /**
     * @param selectedObject the selectedObject to set
     */
    public void setSelectedObject(String selectedObject) {
        this.selectedObject = selectedObject;
    }
    
}
