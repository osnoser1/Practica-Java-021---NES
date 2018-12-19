package gui;

import motor.core.graphics.Sprite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BotonEspecial extends JPanel implements MouseMotionListener, MouseListener{

    private Image imagen;
    private Sprite personaje;
    private MouseListener listener;
    private ActionListener listener1;
    private boolean Dentro;
    Timer animacion;
    
    public BotonEspecial(Sprite personaje) {
        this.personaje = personaje;
        initComponents();
    }
    
    public BotonEspecial(Image imagen) {
        this.imagen=imagen;
        
        initComponents();
    }
    
    private void initComponents() {
        this.setBackground(Color.lightGray);
    	this.addMouseMotionListener(this);
    	this.addMouseListener(this);
    	this.setBorder(BorderFactory.createLineBorder(Color.gray));
        this.animacion = new Timer(150, new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
    }
    
    public void addActionListener(ActionListener l){
        this.listener1=l;
    }
    
    private void update(){
//        personaje.getIzquierda().MovimientoSprite();
    }
    
    @Override
    public void paintComponent(Graphics g){
    	super.paintComponent(g);
    	
        if(personaje!=null){
//            personaje.Dibujar((Graphics2D)g,2*getWidth()/7,getHeight()/5,3*getWidth()/7,3*getHeight()/5);
            
            if(Dentro)
                animacion.start();
            else
                animacion.stop();
 
           repaint();
        }
        else if(imagen!=null){
           g.drawImage(imagen,2*getWidth()/7,getHeight()/5,3*getWidth()/7,3*getHeight()/5,null);
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e){
        if(listener!=null)
            listener.mousePressed(e);
        if(listener1!=null)
            listener1.actionPerformed(null);
    }
    
    @Override
    public void mouseDragged(MouseEvent e){}
    
    @Override
    public void mouseMoved(MouseEvent e){}
    
    @Override
    public void mouseExited(MouseEvent e){
    	this.setBackground(Color.LIGHT_GRAY);
        if(personaje!=null){
          
            Dentro=false;
//            personaje.getAnimation().resetCont();
            repaint();
        }
        
        if(listener!=null)
            listener.mouseExited(e);
    }
    
    @Override
    public void mouseEntered(MouseEvent e){
        this.setBackground(new Color(100,100,255));
        Dentro=true;
        repaint();
       if(listener!=null)
            listener.mouseEntered(e);
    }
    
    @Override
    public void mouseReleased(MouseEvent e){
        if(listener!=null)
            listener.mouseReleased(e);
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
        if(listener!=null)
            listener.mouseClicked(e);
    }
    
}