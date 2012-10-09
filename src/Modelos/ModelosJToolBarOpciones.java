/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import Dependencias.ManejadorDeArchivos;
import Dependencias.Mapa;
import GUI.JPanelAvisos;
import GUI.JPanelContenedor;
import GUI.JPanelGrafico;
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
    
    private JPanelContenedor jpanelcontenedor1;
    private JFileChooser jfilechooser1;
    private FileNameExtensionFilter[] filtro;

    public ModelosJToolBarOpciones(JPanelContenedor jpanelcontenedor1) {
        this.jpanelcontenedor1 = jpanelcontenedor1;
        initComponents();
    }

    private void initComponents() {
        this.filtro=new FileNameExtensionFilter[1];
        this.jfilechooser1=new JFileChooser(){
            @Override
            public void approveSelection() {
                File f = getSelectedFile();
                if(f.exists() && getDialogType() == SAVE_DIALOG) {
                    int result = JOptionPane.showConfirmDialog(getTopLevelAncestor(),
                            "El archivo seleccionado "+f.getName()+" ya existe.\n¿Desea sobreescribirlo?",
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
                    int size=f.getName().length();
                    if(size>3){
                        if(!f.getName().substring(size-4).equalsIgnoreCase(".map")){
                            setSelectedFile(new File(f+".map"));
                        }
                    }else{
                        setSelectedFile(new File(f+".map"));
                    }
                }
                super.approveSelection();
            }
        };
        this.jfilechooser1.removeChoosableFileFilter(this.jfilechooser1.getAcceptAllFileFilter());
        initFiltrosArchivos();
    }
    
    public void start() {
        JPanelGrafico jPanelGrafico = this.jpanelcontenedor1.getJPanelGrafico();
        JPanelAvisos jPanelAvisos = this.jpanelcontenedor1.getJPanelAvisos();
        this.jpanelcontenedor1.removeAll();
        this.jpanelcontenedor1.add(this.jpanelcontenedor1.getJBarraMenu(),java.awt.BorderLayout.NORTH);
        this.jpanelcontenedor1.add(jPanelAvisos);
        jPanelAvisos.iniciarJPanelStage();
        jPanelAvisos.setMAX_NIVEL((short)1);
        new Hilos.HiloPanelPresentacion(jpanelcontenedor1).start();
    }

    public void saveMapa() {
        int opcion=this.jfilechooser1.showSaveDialog(jpanelcontenedor1);
        if(opcion==JFileChooser.APPROVE_OPTION){
            guardarArchivo(this.jfilechooser1.getSelectedFile());
        }
    }

    public void loadMapa() {
        int opcion=this.jfilechooser1.showOpenDialog(jpanelcontenedor1);
        if(opcion==JFileChooser.APPROVE_OPTION){
            cargarArchivo(this.jfilechooser1.getSelectedFile());
        }
    }

    private void guardarArchivo(File selectedFile) {
        String[][] mapa=new String[Mapa.FILAS][Mapa.COLUMNAS];
        String texto="";
        mapa=this.jpanelcontenedor1.getJPanelGrafico().getMapa().getMapa();
        for(int i=0;i<Mapa.FILAS;i++){
            for(int j=0;j<Mapa.COLUMNAS;texto+=mapa[i][j]+" ",j++);  
            texto+="\r\n";
        }
        ManejadorDeArchivos.getInstance().guardarArchivo(selectedFile, texto);
    }

    private void cargarArchivo(File selectedFile) {
        String string = ManejadorDeArchivos.getInstance().cargarArchivo(selectedFile);
        String[][] mapa=new String[Mapa.FILAS][Mapa.COLUMNAS];
        for(int c=0,i=0,j=0,size=string.length(),size2=Mapa.FILAS*Mapa.COLUMNAS*2+Mapa.FILAS;c<size&&c<=size2;c++){
            char letra=string.charAt(c);
            if(letra=='\n'){
                i++;
                j=0;
            }else if(letra!=' '){
                mapa[i][j++]=Character.toString(letra);
            }
        }
        pintarMapa(this.jpanelcontenedor1.getJPanelGrafico(),mapa);
    }

    private void initFiltrosArchivos() {
        int c=0;
	filtro[c++]=new FileNameExtensionFilter("Archivos de mapa (*.map)","map");
        for(c=0;c<1;c++)
            jfilechooser1.setFileFilter(filtro[c]);
    }

    private void pintarMapa(JPanelGrafico jPanelGrafico, String[][] mapa) {
        Mapa mapa1 = jPanelGrafico.getMapa();
        mapa1.setMapa(mapa);
        mapa1.mostrarMapa();
        JPanelJuego jPanelJuego = jPanelGrafico.getJPanelJuego();
        jPanelJuego.borrarEnemigos();
        for(int i=1;i<Mapa.FILAS;i++)
            for(int j=1;j<Mapa.COLUMNAS;j++){
                Mapa.setObjeto(mapa1.getObjetoMapa(i,j));
                jPanelJuego.pintarCasilla(j,i);
            }
        jPanelJuego.repaint();
    }
    
}
