/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import gui.JEditorDeMapas;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import juego.constantes.Objetos;

/**
 *
 * @author AlfonsoAndrés
 */
public class CJEditorDeMapas extends MouseAdapter{

    private static CJEditorDeMapas instance;
    private String objetoSeleccionado;
    
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
        if("V".equals(objetoSeleccionado)){
//            jEditorDeMapas.pintar(jEditorDeMapas.getSpritePiso(), e.getPoint());
        }else{
            jEditorDeMapas.pintar(Objetos.getInstance(objetoSeleccionado), e.getPoint());
        }
    }

    /**
     * @return the objetoSeleccionado
     */
    public String getObjetoSeleccionado() {
        return objetoSeleccionado;
    }

    /**
     * @param objetoSeleccionado the objetoSeleccionado to set
     */
    public void setObjetoSeleccionado(String objetoSeleccionado) {
        this.objetoSeleccionado = objetoSeleccionado;
    }
    
}
