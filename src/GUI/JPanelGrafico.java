
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Bomberman.Core.Constantes;
import Dependencias.Mapa;
import Hilos.HiloPrincipal;
import Personajes.Bomberman;
import Sonidos.Sonidos;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Alfonso Andrés
 */
public class JPanelGrafico extends javax.swing.JPanel implements Constantes{
    
    private static JPanelGrafico instance;
    private JPanel jPanel;

    private JPanelGrafico() {
        super(new java.awt.BorderLayout());
        initComponents();
    }

    public static JPanelGrafico getInstance() {
        return instance == null ? (instance = new JPanelGrafico()) : instance;
    }
    
    private void initComponents() {
        this.setVisible(false);
        this.jPanel=new JPanel(new java.awt.FlowLayout(0,0,0));
        this.jPanel.setBackground(new Color(188,188,188));
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Component c = (Component)e.getSource();
                escalamientoJPanel(c.getSize());
               // System.out.println("Prueba evento componentResized "+c.getSize());
            }
        });
    }

    public void escalamientoJPanel(Dimension dim){
        JPanelJuego.getInstance().setSIZE(dim);
        JPanelInformacion.getInstance().setSIZE(dim);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void generarMapa() {
        Random random = new Random();
        int a, b, c;
        Mapa.setObjeto("B");
        JPanelJuego jPanelJuego = JPanelJuego.getInstance();
        Mapa.getInstance().setObjetoMapa((short)1,(short)1);
        for(int i = 0; i < 55; i++){
            do{
                a = random.nextInt(30);
                b = random.nextInt(12);
                if(a < 3 && b == 1 || a == 1 && b < 3)
                    continue;
                if("V".equals(Mapa.getInstance().getObjetoMapa(b, a))){
                    Mapa.setObjeto("L");
                    Mapa.getInstance().setObjetoMapa((short)b,(short)a);
                    jPanelJuego.pintarCasilla(a, b);
                    break;
                }
            }while(true);
        }
        for(int i = 0; i < 10; i++){
            do{ 
                a = random.nextInt(30);
                b = random.nextInt(12);
                c = random.nextInt(8);
                if("V".equals(Mapa.getInstance().getObjetoMapa(b, a))){
                    Mapa.setObjeto(determinarEnemigo(c));
                    Mapa.getInstance().setObjetoMapa((short)b,(short)a);
                    jPanelJuego.pintarCasilla(a, b);
                    break;
                }
            }while(true);
        }
    }
    
    public void iniciarJuego() {
        JPanelJuego jPanelJuego = JPanelJuego.getInstance();
        generarMapa();
        jPanelJuego.fijarJugador(0, new Bomberman(JPanelJuego.getx(),JPanelJuego.gety()));
        jPanelJuego.setHiloPrincipal(new HiloPrincipal(jPanelJuego, (short)60));
        jPanelJuego.getHiloPrincipal().start();
        this.add(JPanelInformacion.getInstance(), java.awt.BorderLayout.NORTH);
        this.jPanel.add(jPanelJuego);
        this.add(jPanel);
        Sonidos.getInstance().getSonido(Sonidos.STAGE_THEME).loop();
        this.setVisible(true);
        jPanelJuego.requestFocus();
        JPanelInformacion.getInstance().iniciarCuentaRegresiva();
        jPanelJuego.setHiloPrincipal(new HiloPrincipal(jPanelJuego, (short)60));
        jPanelJuego.getHiloPrincipal().start();
    }
    
    public String determinarEnemigo(int c) {
        if(c > 6){
            return Objetos.PONTAN.getValue();
        }
        return Objetos.getEnemigos()[c].getValue();
    }
    
}
