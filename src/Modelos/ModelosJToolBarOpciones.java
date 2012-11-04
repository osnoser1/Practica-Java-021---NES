/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import Dependencias.ManejadorDeArchivos;
import Dependencias.Mapa;
import GUI.JPanelAvisos;
import GUI.JPanelContenedor;
import GUI.JPanelJuego;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Alfonso Andrés
 */
public class ModelosJToolBarOpciones {
    
    private JFileChooser jFileChooser;
    private FileNameExtensionFilter[] filtro;

    public ModelosJToolBarOpciones() {
        initComponents();
    }

    private void initComponents() {
        filtro = new FileNameExtensionFilter[1];
        jFileChooser = new JFileChooser(){
            @Override
            public void approveSelection() {
                File f = getSelectedFile();
                if(f.exists() && getDialogType() == SAVE_DIALOG) {
                    int result = JOptionPane.showConfirmDialog(getTopLevelAncestor(),
                            "El archivo seleccionado " + f.getName() + " ya existe.\n¿Desea sobreescribirlo?",
                            "Confirmar Guardar Como",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                    switch(result) {
                    case JOptionPane.YES_OPTION:
                        super.approveSelection();
                        return;
                    default:
                        return;
                    }
                }else if(getDialogType() == SAVE_DIALOG) {
                    int size = f.getName().length();
                    if(size > 3){
                        if(!f.getName().substring(size - 4).equalsIgnoreCase(".map")){
                            setSelectedFile(new File(f + ".map"));
                        }
                    }else{
                        setSelectedFile(new File(f + ".map"));
                    }
                }
                super.approveSelection();
            }
        };
        this.jFileChooser.removeChoosableFileFilter(this.jFileChooser.getAcceptAllFileFilter());
        initFiltrosArchivos();
    }
    
    public void start() {
//        JPanelContenedor jPanelContenedor = JPanelContenedor.getInstance();
//        JPanelAvisos jPanelAvisos = JPanelAvisos.getInstance();
//        jPanelContenedor.removeAll();
//        jPanelContenedor.add(jPanelAvisos);
//        jPanelAvisos.iniciarJPanelStage();
//        jPanelAvisos.setMAX_NIVEL((short)1);
//        new Hilos.HiloPanelPresentacion().start();
    }

    public void saveMapa() {
        int opcion = this.jFileChooser.showSaveDialog(JPanelContenedor.getInstance());
        if(opcion == JFileChooser.APPROVE_OPTION){
            guardarArchivo(this.jFileChooser.getSelectedFile());
        }
    }

    public void loadMapa() {
        int opcion = this.jFileChooser.showOpenDialog(JPanelContenedor.getInstance());
        if(opcion == JFileChooser.APPROVE_OPTION){
            cargarArchivo(this.jFileChooser.getSelectedFile());
        }
    }

    private void guardarArchivo(File selectedFile) {
        String[][] mapa = Mapa.getInstance().getMapa();
        String texto = "";
        for(int i = 0; i < Mapa.FILAS; i++){
            for(int j = 0; j < Mapa.COLUMNAS; texto += mapa[i][j] + " ", j++);  
            texto += "\r\n";
        }
        ManejadorDeArchivos.getInstance().guardarArchivo(selectedFile, texto);
    }

    private void cargarArchivo(File selectedFile) {
        String string = ManejadorDeArchivos.getInstance().cargarArchivo(selectedFile);
        String[][] mapa = new String[Mapa.FILAS][Mapa.COLUMNAS];
        for(int c = 0, i = 0, j = 0, size = string.length(), size2 = Mapa.FILAS * Mapa.COLUMNAS * 2 + Mapa.FILAS; c < size && c <= size2; c++){
            char letra = string.charAt(c);
            if(letra == '\n'){
                i++;
                j=0;
            }else if(letra != ' '){
                mapa[i][j++] = Character.toString(letra);
            }
        }
        pintarMapa(mapa);
    }

    private void initFiltrosArchivos() {
	filtro[0] = new FileNameExtensionFilter("Archivos de mapa (*.map)", "map");
        jFileChooser.setFileFilter(filtro[0]);
    }

    private void pintarMapa(String[][] mapa) {
        Mapa mapa1 = Mapa.getInstance();
        mapa1.setMapa(mapa);
        JPanelJuego jPanelJuego = JPanelJuego.getInstance(null);
        jPanelJuego.borrarEnemigos();
        for(int i = 1; i < Mapa.FILAS; i++) {
            for(int j = 1; j < Mapa.COLUMNAS; j++){
                mapa1.setObjeto(mapa[i][j], (short)i, (short)j);
                jPanelJuego.pintarCasilla(j, i);
            }
        }
    }
    
}
