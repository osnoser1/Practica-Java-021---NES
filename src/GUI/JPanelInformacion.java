/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package GUI;

import Fuentes.Fuentes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.AbstractAction;
import javax.swing.Timer;

/**
 *
 * @author Alfonso Andrés
 */
public class JPanelInformacion {
    
    private int puntaje;
    private static JPanelInformacion instance;
    private int tiempoRestante = 200, vidasRestantes = 2;
    private int cantidadOpciones = 3;
    private BufferedImage imagen;
    private Fuentes fuentes;
    private Timer timer;
    private Dimension SIZE;

    private JPanelInformacion() {
        super();
        initComponents();
    }

    public static JPanelInformacion getInstance() {
        return instance == null ? (instance = new JPanelInformacion()) : instance;
    }

    private void initComponents() {
        SIZE = new Dimension(640, 60);
        fuentes = new Fuentes();
        iniciar();
        timer = new Timer(1000, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disminuirContador();
            }
        });
    }
    
    private void drawString(Graphics g2, String string, Point point) {
        g2.setColor(Color.BLACK);
        g2.setFont(fuentes.getJoystixMonospacce(25));
        g2.drawString(string, point.x + 1, point.y + 1);
        g2.setFont(fuentes.getJoystixMonospacce(24));
        g2.setColor(Color.WHITE);
        g2.drawString(string, point.x, point.y);
    }

    private void iniciar() {
        imagen = new BufferedImage(640, 60, BufferedImage.TYPE_INT_RGB);
        Graphics g2 = imagen.createGraphics();
        g2.setColor(new Color(188, 188, 188));
        g2.fillRect(0, 0, 639, 69);
        drawStrings(g2);
    }

    public int getTiempoRestante() {
        return tiempoRestante;
    }

    public void setTiempoRestante(int tiempoRestante) {
        this.tiempoRestante = tiempoRestante;
    }

    public void setPuntaje(int punt) {
        puntaje = punt;
    }

    public void aumentarPuntaje(int punt) {
        puntaje += punt;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setVidasRestantes(int vidasRestantes) {
        this.vidasRestantes = vidasRestantes;
    }

    public void disminuirVidasRestantes() {
        vidasRestantes = vidasRestantes < 0 ? -1 : --this.vidasRestantes;
    }

    public int getVidasRestantes() {
        return vidasRestantes;
    }

    private void drawStrings(Graphics g2) {
        for(int i = 0; i < this.cantidadOpciones; i++) {
            drawString(g2, getString(i), getPosicion(i));
        }
    }

    private String getString(int i) {
        if(i == 0)
            return "TIME " + tiempoRestante();
        if(i == 1)
            return puntaje();
        return "LEFT " + vidasRestantes;
    }

    private Point getPosicion(int i) {
        if(i == 0)
            return new Point(20, 37);
        if(i == 1)
            return new Point(360, 37);
        return new Point(480, 37);
    }

    private String puntaje() {
        return puntaje + "";
    }

    private String tiempoRestante() {
        return tiempoRestante + "";
    }

    public void iniciarCuentaRegresiva() {
        tiempoRestante = 200;
        timer.start();
    }

    public void detenerCuentaRegresiva() {
        timer.stop();
        iniciar();
    }

    private void disminuirContador() {
        if(tiempoRestante == 0) {
            timer.stop();
            return;
        }
        tiempoRestante--;
        iniciar();
    }

    public void setSIZE(Dimension dim) {
        int y = (int)Math.round(dim.height / 14.0);
        SIZE = new Dimension(dim.width, y + y / 2);
        System.out.println(dim + " " + SIZE + " " + y);
    }
    
    public int getAlto() {
        return SIZE.height;
    }
    
    public void pintar(Graphics g) {
        g.drawImage(imagen, 0, 0, SIZE.width, SIZE.height, null);
    }
    
}
