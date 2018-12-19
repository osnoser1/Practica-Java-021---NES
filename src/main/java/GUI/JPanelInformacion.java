/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package gui;

import lenguaje.utils.ImageUtilities;
import Fuentes.Fuentes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
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
    private final int cantidadOpciones = 3;
    private Image imagen;
    private Timer timer;
    private Dimension SIZE;
    private Color fondo;
    private Font font;
    private Point[] pos;
    private boolean cambio;


    private JPanelInformacion() {
        super();
        initComponents();
    }

    public static JPanelInformacion getInstance() {
        return instance == null ? (instance = new JPanelInformacion()) : instance;
    }

    private void initComponents() {
        SIZE = new Dimension(640, 60);
        font = Fuentes.getInstance().getJoystixMonospacce(24);
        fondo = new Color(188, 188, 188);
        pos = new Point[]{new Point(20, 37), new Point(360, 37), new Point(480, 37)};
        imagen = ImageUtilities.createCompatibleVolatileImage(640, 60, Transparency.OPAQUE);
        var g2 = (Graphics2D) imagen.getGraphics();
        g2.setColor(fondo);
        g2.fillRect(0, 0, 640, 60);
        g2.dispose();
        timer = new Timer(1000, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disminuirContador();
            }
        });
    }
    
    private void drawString(final Graphics g2, final String string, final Point point) {
        g2.setColor(fondo);
        g2.fillRect(point.x, point.y - 25, 300, 30);
        g2.setColor(Color.BLACK);
        g2.setFont(font);
        g2.drawString(string, point.x + 2, point.y + 2);
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        g2.drawString(string, point.x, point.y);
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
        for(var i = 0; i < this.cantidadOpciones; i++) {
            drawString(g2, getString(i), pos[i]);
        }
    }

    private String getString(int i) {
        if(i == 0)
            return "TIME " + tiempoRestante();
        if(i == 1)
            return puntaje();
        return "LEFT " + vidasRestantes;
    }

    private String puntaje() {
        return puntaje + "";
    }

    private String tiempoRestante() {
        return tiempoRestante + "";
    }

    public void iniciarCuentaRegresiva() {
        tiempoRestante = 200;
        cambio = true;
        timer.start();
    }

    public void detenerCuentaRegresiva() {
        timer.stop();
        cambio = false;
    }

    private void disminuirContador() {
        if(tiempoRestante == 0) {
            detenerCuentaRegresiva();
            return;
        }
        tiempoRestante--;
        cambio = true;
    }

    public void setSIZE(Dimension dim) {
        var y = (int)Math.round(dim.height / 14.0);
        SIZE = new Dimension(dim.width, y + y / 2);
        System.out.println(dim + " " + SIZE + " " + y);
    }
    
    public int getAlto() {
        return SIZE.height;
    }
    
    public void pintar(final Graphics2D g2) {
        if (cambio) {
            var g2d = (Graphics2D) imagen.getGraphics();
            drawStrings(g2d);
            g2d.dispose();
            cambio = false;
        }
        g2.drawImage(imagen, 0, 0, null);
    }
    
}
