/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controladores.ControladorJToolBarOpciones;
import Controladores.ControladorKeyBoardJPanelJuego;
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
import javax.swing.JScrollPane;

/**
 *
 * @author Alfonso Andrés
 */
public class JPanelGrafico extends javax.swing.JPanel{

    private JPanelInformacion jpanelinformacion1;
    public JPanelJuego jpaneljuego1;
    private JPanelContenedor jpanelcontenedor1;
    private JToolBarBotonesEditor jtoolbarbotoneseditor1;
    private JToolBarOpcionesEditor jtoolbaropcioneseditor1;
    private Controladores.ControladorMouseJPanelJuego controlador;
    private ControladorKeyBoardJPanelJuego controladorKeyBoardJPanelJuego; 
    private JPanel jpanel1;
    private boolean modoEdicion=false;
    private JScrollPane jscrollpane1;

    JPanelGrafico(JPanelContenedor aThis) {
        super(new java.awt.BorderLayout());
        this.jpanelcontenedor1=aThis;
        initComponents();
    }

    private void initComponents() {
        this.setVisible(false);
        
        this.jpanelinformacion1=new JPanelInformacion();

        this.jpanel1=new JPanel(new java.awt.FlowLayout(0,0,0));
        this.jscrollpane1=new JScrollPane();
        this.jtoolbarbotoneseditor1= new JToolBarBotonesEditor();
        this.jtoolbaropcioneseditor1 = new JToolBarOpcionesEditor(new ControladorJToolBarOpciones(this.jpanelcontenedor1));
        this.jpanel1.setBackground(new Color(188,188,188));
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Component c = (Component)e.getSource();
                escalamientoJPanel(c.getSize());
               // System.out.println("Prueba evento componentResized "+c.getSize());
            }
        });
        this.jpaneljuego1=new JPanelJuego(this);
        this.controlador=new Controladores.ControladorMouseJPanelJuego(jpaneljuego1);
        this.controladorKeyBoardJPanelJuego = new ControladorKeyBoardJPanelJuego(jpaneljuego1);
    }
    
    public Dependencias.Mapa getMapa(){
        return GUI.JPanelJuego.getMapa();
    }

    public JPanelContenedor getJPanelContenedor() {
        return jpanelcontenedor1;
    }
    public void escalamientoJPanel(Dimension dim){
        jpaneljuego1.setSIZE(dim);
        jpanelinformacion1.setSIZE(dim);
    }

    public ControladorKeyBoardJPanelJuego getControladorKeyBoardJPanelJuego() {
        return controladorKeyBoardJPanelJuego;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void generarMapa() {
        if(modoEdicion){
            return;
        }
        
        boolean d;
        Random random=new Random();
        int a,b,c;
        
        Mapa.setObjeto("B");
        JPanelJuego.getMapa().setObjetoMapa((short)1,(short)1);
            
        for(int i=0;i<55;i++){
            do{
                d=true;
            a=random.nextInt(30);
            b=random.nextInt(12);
            if("V".equals(JPanelJuego.getMapa().getObjetoMapa(b,a))){
                d=false;
                Mapa.setObjeto("L");
                JPanelJuego.getMapa().setObjetoMapa((short)b,(short)a);
                jpaneljuego1.pintarCasilla(a, b);
            }
            }while(d);
        }
         for(int i=0;i<10;i++){
            do{ 
                d=true;
                a=random.nextInt(30);
                b=random.nextInt(12);
                c=random.nextInt(8);
            
                if("V".equals(JPanelJuego.getMapa().getObjetoMapa(b, a))){
                    d=false;
                    Mapa.setObjeto(DeterminarEnemigo(c));
                    JPanelJuego.getMapa().setObjetoMapa((short)b,(short)a);
                    jpaneljuego1.pintarCasilla(a, b);
                }
            }while(d);
        }
        
    }
    
    public void iniciarJuego() {
//        this.jpaneljuego1=null;
//        this.jpaneljuego1=new JPanelJuego(this);
        if(modoEdicion){
            this.jpaneljuego1.removeMouseListener(controlador);
            this.jpaneljuego1.removeMouseMotionListener(controlador);
            this.remove(this.jscrollpane1);
            this.jpanelcontenedor1.remove(this.jtoolbarbotoneseditor1);
            this.jpanelcontenedor1.remove(this.jtoolbaropcioneseditor1);
            this.jtoolbarbotoneseditor1.setVisible(false);
            this.jtoolbaropcioneseditor1.setVisible(false);
            modoEdicion=false;
            this.jpaneljuego1.setEditing(modoEdicion);
        }else{
            this.jpaneljuego1.setHiloPrincipal(new HiloPrincipal(this.jpaneljuego1, (short)60));
            this.jpaneljuego1.getHiloPrincipal().start();
            generarMapa();
            JPanelJuego.Jugador=new Bomberman(JPanelJuego.getx(),JPanelJuego.gety());
        }
        this.jpaneljuego1.removeKeyListener(controladorKeyBoardJPanelJuego);
        this.add(jpanelinformacion1,java.awt.BorderLayout.NORTH);
        this.jpanel1.add(jpaneljuego1);
        this.add(jpanel1);
        Sonidos.getInstance().getSonido(Sonidos.STAGE_THEME).loop();
        this.setVisible(true);
        this.jpaneljuego1.requestFocus();
        jpanelinformacion1.iniciarCuentaRegresiva();
        controladorKeyBoardJPanelJuego=new ControladorKeyBoardJPanelJuego(jpaneljuego1);
        this.jpaneljuego1.addKeyListener(controladorKeyBoardJPanelJuego);
        this.jpaneljuego1.setHiloPrincipal(new HiloPrincipal(this.jpaneljuego1, (short)60));
        this.jpaneljuego1.getHiloPrincipal().start();
    }

    public void modoEditor() {
        if(!modoEdicion){
            this.jpaneljuego1.removeKeyListener(controladorKeyBoardJPanelJuego);
            this.remove(this.jpanelinformacion1);
            modoEdicion=true;
            this.jpaneljuego1.setEditing(modoEdicion);
        }
        this.jpaneljuego1.addMouseListener(controlador);
        this.jpaneljuego1.addMouseMotionListener(controlador);
        jscrollpane1.getViewport().setView(this.jpaneljuego1);
        jscrollpane1.setFocusable(false);
        jscrollpane1.setRequestFocusEnabled(false);
        this.jpanelcontenedor1.add(this.jtoolbarbotoneseditor1,java.awt.BorderLayout.EAST);
        this.jpanelcontenedor1.add(this.jtoolbaropcioneseditor1,java.awt.BorderLayout.SOUTH);
        this.add(jscrollpane1);
        this.setVisible(true);
    }


    public JToolBarOpcionesEditor getJToolBarOpcionesEditor() {
        return jtoolbaropcioneseditor1;
    }

    public JToolBarBotonesEditor getJToolBarBotonesEditor() {
        return jtoolbarbotoneseditor1;
    }
    
    public JPanelJuego getJPanelJuego() {
        return this.jpaneljuego1;
    }


    public void setJPanelJuego(JPanelJuego jpaneljuego1) {
        this.jpaneljuego1 = jpaneljuego1;
    }
    public JPanelInformacion getJPanelInformacion() {
        return jpanelinformacion1;
    }

    public String DeterminarEnemigo(int c) {
        if(c==0) return "b";
        else if(c==1) return "D";
        else if(c==2) return "K";
        else if(c==3) return "M";
        else if(c==4) return "O";
        else if(c==5) return "o";
        else if(c==6) return "P";
        else return "p";
    }
    
}
