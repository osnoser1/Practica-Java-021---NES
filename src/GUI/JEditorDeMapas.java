/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controladores.CJEditorDeMapas;
import Dependencias.Imagenes;
import Dependencias.Mapa;
import Personajes.Animation;
import Personajes.Sprite;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author AlfonsoAndrés
 */
public class JEditorDeMapas extends JPanel{
    
    private static JEditorDeMapas instance;
    private PanelEditor panelEditor;
    private Dimension[] relacionDeTamaño = { new Dimension(656, 620), new Dimension(1240, 520) };
    private Point posicionBombeman;
    
    private JEditorDeMapas() {
        super(new BorderLayout());
        initComponents();
    }
    
    public static JEditorDeMapas getInstance() {
        return instance == null ? (instance = new JEditorDeMapas()) : instance;
    }

    private void initComponents() {
        panelEditor = new PanelEditor();
        posicionBombeman = new Point(40, 40);
        JScrollPane aux = new JScrollPane(panelEditor);
        add(aux);
        add(JToolBarBotonesEditor.getInstance(), BorderLayout.EAST);
        add(JToolBarOpcionesEditor.getInstance(), BorderLayout.SOUTH);
        panelEditor.setRequestFocusEnabled(true);
        panelEditor.requestFocusInWindow();
        panelEditor.setFocusable(true);
        panelEditor.addMouseListener(CJEditorDeMapas.getInstance());
        panelEditor.addMouseMotionListener(CJEditorDeMapas.getInstance());
        pintar(Sprite.getInstance("B"), posicionBombeman);
    }
    
    public void escalamiento(Dimension tamañoJFrame) {
        panelEditor.setSize(relacionDeTamaño[0].width * tamañoJFrame.width / getWidth(), 
                relacionDeTamaño[0].height * tamañoJFrame.height / getHeight());
        panelEditor.repaint();
    }
    
    public void pintar(Sprite sprite, Point posicion) {
        //No simplificar
        int x = posicion.x * Mapa.COLUMNAS / panelEditor.getWidth() * relacionDeTamaño[1].width / Mapa.COLUMNAS;
        int y = posicion.y * Mapa.FILAS / panelEditor.getHeight() * relacionDeTamaño[1].height / Mapa.FILAS;
        Graphics g = panelEditor.getImagen().getGraphics();
        if("B".equals(sprite.getIdentificacion())){
            g.drawImage(Imagenes.PISO, posicionBombeman.x, posicionBombeman.y, relacionDeTamaño[1].width / Mapa.COLUMNAS, relacionDeTamaño[1].height / Mapa.FILAS, null);
            Mapa.getInstance().setObjetoMapa("V", 
                (short)(Mapa.FILAS * posicionBombeman.y / panelEditor.getHeight()), 
                (short)(Mapa.COLUMNAS * posicionBombeman.x / panelEditor.getWidth()));
            posicionBombeman = new Point(x, y);
        }
        if(!noEstaSobreElBomberman(x, y) && !"B".equals(sprite.getIdentificacion()))
            return;
        g.drawImage(Imagenes.PISO, x, y, relacionDeTamaño[1].width / Mapa.COLUMNAS, relacionDeTamaño[1].height / Mapa.FILAS, null);
        g.drawImage(sprite.getSpriteActual(), x, y, relacionDeTamaño[1].width / Mapa.COLUMNAS, relacionDeTamaño[1].height / Mapa.FILAS, null);
        Mapa.getInstance().setObjetoMapa(sprite.getIdentificacion(), 
                (short)(Mapa.FILAS * posicion.y / panelEditor.getHeight()), 
                (short)(Mapa.COLUMNAS * posicion.x / panelEditor.getWidth()));
        panelEditor.repaint();
    }
    
    public Sprite getSpritePiso() {
        return new Sprite(new Animation(
                new ArrayList<BufferedImage>(){{ 
                    add(Imagenes.PISO);
                }}));
    }

    private boolean noEstaSobreElBomberman(int x, int y) {
        return x != posicionBombeman.x || y != posicionBombeman.y;
    }
    
    private class PanelEditor extends JPanel {

        private BufferedImage imagen;
        
        PanelEditor() {
            setLayout(null);
            setSize(relacionDeTamaño[1]);
            setPreferredSize(getSize());
            initImagen();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }

        public BufferedImage getImagen() {
            return imagen;
        }

        private void initImagen() {
            imagen = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = imagen.getGraphics();
            int x = getWidth() / Mapa.COLUMNAS;
            int y = getHeight() / Mapa.FILAS;
            Mapa mapa = Mapa.getInstance();
            for(short i = 0; i < Mapa.FILAS; i++) {
                for(int j = 0; j < Mapa.COLUMNAS; j++) {
                    switch (mapa.getObjetoMapa(i, j)) {
                        case "V":
                            g.drawImage(Imagenes.PISO, j * x, i * y, x, y, this);
                            break;
                        case "A":
                            g.drawImage(Imagenes.ACERO, j * x, i * y, x, y, this);
                            break;
                    }
                }
            }
        }
        
    }
    
}
