package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BotonEspecial extends JPanel implements MouseMotionListener,MouseListener{

    private BufferedImage imagen=null;
    private Personajes.Personaje personaje=null;
    private MouseListener listener=null;
    private ActionListener listener1=null;
    private boolean Dentro;
    Timer animacion;
    public BotonEspecial(Personajes.Personaje personaje) {
        this.personaje=personaje;
        personaje.getAnimation().resetCont();
      //  personaje.getAnimation().suspend();
        initComponents();
    }
    public BotonEspecial(BufferedImage imagen) {
        this.imagen=imagen;
        
        initComponents();
    }

    
    private void initComponents() {
        this.setBackground(Color.lightGray);
    	this.addMouseMotionListener(this);
    	this.addMouseListener(this);
    	this.setBorder(BorderFactory.createLineBorder(Color.gray));
        this.animacion=new Timer(150,new AbstractAction(){
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
        personaje.getIzquierda().MovimientoSprite();
    }
    
    @Override
    public void paintComponent(Graphics g){
    	super.paintComponent(g);
    	
        if(personaje!=null){
            personaje.Dibujar((Graphics2D)g,2*getWidth()/7,getHeight()/5,3*getWidth()/7,3*getHeight()/5);
            
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
            personaje.getAnimation().resetCont();
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