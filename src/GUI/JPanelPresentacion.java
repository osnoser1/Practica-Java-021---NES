/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Dependencias.Imagenes;
import Dependencias.ManejadorDeArchivos;
import Dependencias.Ubicacion;
import Fuentes.Fuentes;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Alfonso Andrés
 */
public class JPanelPresentacion extends javax.swing.JPanel {
    
    private static JPanelPresentacion instance;
    private BufferedImage imagen,flecha;
    private ArrayList<Point> opciones;
    private ManejadorDeArchivos manejadordearchivos1;
    private Fuentes fuentes;
    private final int cantidadOpciones=2;
    private int opcionApuntando=0;
    private int opcionSeleccionada=-1;
    public static final int START=0,MAP_EDITOR=1;
    
    private JPanelPresentacion() {
        super();
        initComponents();
    }
    
    public static JPanelPresentacion getInstance() {
        return instance == null ? (instance = new JPanelPresentacion()) : instance;
    }

    private void initComponents() {
        this.setOpaque(false);
        this.setRequestFocusEnabled(true);
        this.requestFocusInWindow();
        this.setFocusable(true);
        fuentes=new Fuentes();
        opciones=new ArrayList<>();
        agregarOpciones();
        this.manejadordearchivos1=ManejadorDeArchivos.getInstance();
        crearFlecha();
        iniciar();
    }

    public void iniciar() {
        imagen=new BufferedImage(640,560,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2=imagen.createGraphics();
        g2.drawImage(Imagenes.LOGO,40,20,568,347,null);
        drawStrings(g2);
        pintarFlecha(g2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagen,0,0,this.getWidth(),this.getHeight(),null);
    }

    private void agregarOpciones() {
        for(int i=0;i<cantidadOpciones;i++){
            opciones.add(getPoint(i));
        }
    }

    private Point getPoint(int i) {
        if(i==0) return new Point(259,415);
        return new Point(259,455);
    }

    private void drawStrings(Graphics2D g2) {
        for(int i=0;i<this.cantidadOpciones;i++){
            drawString(g2,getString(i),opciones.get(i));
        }
    }
    
    private void drawString(Graphics2D g2,String string,Point point){
        g2.setColor(new Color(127,127,127));
        g2.setFont(fuentes.getJoystixMonospacce(25));
        g2.drawString(string,point.x+1,point.y+1);
        g2.setFont(fuentes.getJoystixMonospacce(24));
        g2.setColor(Color.WHITE);
        g2.drawString(string,point.x,point.y);
    }
    
    private String getString(int i){
        if(i==0) return "START";
        else if(i==1) return "MAP EDITOR";
        return "TOP";
    }
    
    public void siguienteOpcion(){
        opcionApuntando=opcionApuntando==cantidadOpciones-1?opcionApuntando=0:++opcionApuntando;
        iniciar();
        repaint();
    }

    private void crearFlecha() {
        flecha=this.manejadordearchivos1.loadBufferedImageJAR(Ubicacion.APUNTADOR);
    }

    private void pintarFlecha(Graphics2D g2) {
        Point point=opciones.get(this.opcionApuntando);
        g2.drawImage(flecha,point.x-55,point.y-17,20,20,this);
    }

    public int getOpcionSeleccionada() {
        return opcionSeleccionada;
    }

    public void setOpcionSeleccionada() {
        this.opcionSeleccionada=opcionApuntando;
    }

}
