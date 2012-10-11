/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Dependencias.Mapa;
import GUI.JEditorDeMapas;
import Personajes.Sprite;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author AlfonsoAndrés
 */
public class CJEditorDeMapas extends MouseAdapter{

    private static CJEditorDeMapas instance;
    
    private CJEditorDeMapas() {
    }

    public static CJEditorDeMapas getInstance() {
        return instance == null ? (instance = new CJEditorDeMapas()) : instance;
    }

    public static void eliminarInstancia(){
        instance = null;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pintar(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        pintar(e);
    }
    
    private void pintar(MouseEvent e) {
        JEditorDeMapas jEditorDeMapas = JEditorDeMapas.getInstance();
        if("V".equals(Mapa.getObjeto())){
            jEditorDeMapas.pintar(jEditorDeMapas.getSpritePiso(), e.getPoint());
        }else{
            jEditorDeMapas.pintar(Sprite.getInstance(Mapa.getObjeto()), e.getPoint());
        }
    }
    
}
