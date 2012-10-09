/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Dependencias.ManejadorDeArchivos;
import Fuentes.Fuentes;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author Alfonso Andrés
 */
public class JPanelInformacion extends javax.swing.JPanel{

    private BufferedImage imagen;
    private Fuentes fuentes;
    private ManejadorDeArchivos manejadordearchivos1;
    private int tiempoRestante=200,vidasRestantes=2;
    private int cantidadOpciones=3;
    public static int puntaje=0;
    private Timer timer;
    private Dimension SIZE;
    
    public JPanelInformacion() {
        super();
        initComponents();
    }

    private void initComponents() {
        SIZE=new Dimension(640,60);
        this.setBackground(new Color(188,188,188));
        this.setPreferredSize(new java.awt.Dimension(SIZE.width,SIZE.height));
        this.fuentes=new Fuentes();
        this.manejadordearchivos1=ManejadorDeArchivos.getInstance();
        iniciar();
        timer=new Timer(1000,new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                disminuirContador();
            }            
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagen,0,0,SIZE.width,SIZE.height, this);
    }
    
    private void drawString(Graphics2D g2,String string,Point point){
        g2.setColor(Color.BLACK);
        g2.setFont(fuentes.getJoystixMonospacce(25));
        g2.drawString(string,point.x+1,point.y+1);
        g2.setFont(fuentes.getJoystixMonospacce(24));
        g2.setColor(Color.WHITE);
        g2.drawString(string,point.x,point.y);
    }

    private void iniciar() {
        imagen=new BufferedImage(640,60,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2=imagen.createGraphics();
        g2.setColor(new Color(188,188,188));
        g2.fillRect(0,0,639,69);
        drawStrings(g2);
    }

    public int getTiempoRestante() {
        return tiempoRestante;
    }

    public void setTiempoRestante(int tiempoRestante) {
        this.tiempoRestante = tiempoRestante;
    }

    public static void setPuntaje(int punt) {
        JPanelInformacion.puntaje = punt;
    }

    public static void aumentarPuntaje(int punt){
        JPanelInformacion.puntaje+=punt;
    }
    
    public int getPuntaje() {
        return puntaje;
    }

    public void setVidasRestantes(int vidasRestantes) {
        this.vidasRestantes = vidasRestantes;
    }

    public void disminuirVidasRestantes(){
        this.vidasRestantes=this.vidasRestantes<0?-1:--this.vidasRestantes;
    }
    
    public int getVidasRestantes() {
        return vidasRestantes;
    }
    
    private void drawStrings(Graphics2D g2) {
        for(int i=0;i<this.cantidadOpciones;i++){
            drawString(g2,getString(i),getPosicion(i));
        }
    }
    
    private String getString(int i){
        if(i==0) return "TIME "+tiempoRestante();
        else if(i==1) return puntaje();
        return "LEFT "+vidasRestantes;
    }

    private Point getPosicion(int i) {
        if(i==0) return new Point(20,37);
        else if(i==1) return new Point(360,37);
        return new Point(480,37);
    }

    private String puntaje() {
        return puntaje+"";
    }

    private String tiempoRestante() {
        return tiempoRestante+"";
    }

    public void iniciarCuentaRegresiva() {
        tiempoRestante=200;
        timer.start();
    }
    
    public void detenerCuentaRegresiva(){
        timer.stop();
        iniciar();
        repaint();
    }
    
    private void disminuirContador() {
        if(tiempoRestante==0){
            timer.stop();
            return;
        }
        tiempoRestante--;
        iniciar();
        repaint();
    }

    public void setSIZE(Dimension dim) {
        int y=(int)Math.round(dim.height/14.0);
        SIZE=new Dimension(dim.width,y+y/2);
        System.out.println(dim+" "+SIZE+" "+y);
        this.setPreferredSize(SIZE);
        this.setSize(SIZE);
      //  this.updateUI();
        SwingUtilities.updateComponentTreeUI(this);
        
    }
}
