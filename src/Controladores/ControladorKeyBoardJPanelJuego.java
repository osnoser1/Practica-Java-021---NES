/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;
import Dependencias.Metodos;
import Dialog.Controles;
import GUI.JPanelJuego;
import Sonidos.Sonidos;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author hp
 */
public class ControladorKeyBoardJPanelJuego implements KeyListener,Runnable {
    
    private JPanelJuego jpaneljuego;
    private ArrayList<KeyEvent> keys;
    private Robot robot;
    private static int vecesQueEntro=0;
    private boolean arriba=false,abajo=false,izquierda=false,derecha=false;
    private Thread thread;
    
    public ControladorKeyBoardJPanelJuego(JPanelJuego jpaneljuego) {
        this.jpaneljuego=jpaneljuego;
        keys=new ArrayList<>();
        try {
            robot=new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(ControladorKeyBoardJPanelJuego.class.getName()).log(Level.SEVERE, null, ex);
        }
        thread=new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while(true){
            Metodos.sleep(30);
            if(/*get.getKeyCode()==Controles.getDerecha()*/derecha){
                Sonidos.getInstance().getSonido(Sonidos.RIGHT).play();
                JPanelJuego.getJugador().MovimientoDerecha();
                System.out.println((JPanelJuego.getJugador().AvanzarX()&&JPanelJuego.getJugador().getX()>=jpaneljuego.getWidth()/4&&JPanelJuego.getJugador().getX()<=(3*jpaneljuego.getWidth()/4-30)));
                if(JPanelJuego.getJugador().AvanzarX()&&JPanelJuego.getJugador().getX()>=jpaneljuego.getWidth()/4&&JPanelJuego.getJugador().getX()<=(3*jpaneljuego.getWidth()/4-30)){
                    jpaneljuego.setLocation(jpaneljuego.getX()-JPanelJuego.getJugador().getSpeed(), 0);

                }
            }
            else if(/*get.getKeyCode()==Controles.getIzquierda()*/izquierda){
                JPanelJuego.getJugador().MovimientoIzquierda();
                Sonidos.getInstance().getSonido(Sonidos.LEFT).play();
                    if(JPanelJuego.getJugador().AvanzarX()&&JPanelJuego.getJugador().getX()>=jpaneljuego.getWidth()/4&&JPanelJuego.getJugador().getX()<=(3*jpaneljuego.getWidth()/4-30)){
                    jpaneljuego.setLocation(jpaneljuego.getX()-JPanelJuego.getJugador().getSpeed(), 0);
                    }
            }
            if(/*get.getKeyCode()==Controles.getArriba()*/arriba){
                JPanelJuego.getJugador().MovimientoArriba();
                Sonidos.getInstance().getSonido(Sonidos.UP).play();
            }
            else if(/*get.getKeyCode()==Controles.getAbajo()*/abajo){
                JPanelJuego.getJugador().MovimientoAbajo();
                Sonidos.getInstance().getSonido(Sonidos.DOWN).play();
            }
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
       
        
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println((++vecesQueEntro));
        if(e.getKeyCode()==Controles.getArriba())
            arriba=true;
        else if(e.getKeyCode()==Controles.getAbajo())
            abajo=true;
        else if(e.getKeyCode()==Controles.getIzquierda()){
            izquierda=true;
        }else if(e.getKeyCode()==Controles.getDerecha())
            derecha=true;
        if(!compararKeyEvent(e))
            keys.add(e);
        ejecutarAcciones(e);        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        eliminarKeyEvent(e);
    }

    private void ejecutarAcciones(KeyEvent e) {
       // System.out.println("x: "+JPanelJuego.getJugador().getX()+" y: "+JPanelJuego.getJugador().getY()+" i: "+JPanelJuego.getPosicionX(JPanelJuego.getJugador().getCenterX())+" j: "+JPanelJuego.getPosicionY(JPanelJuego.getJugador().getCenterY()));
            KeyEvent get = e;
            if(JPanelJuego.getJugador()!=null){
                if(get.getKeyCode()==Dialog.Controles.getA()){
                    int y=JPanelJuego.getPosicionY(JPanelJuego.getJugador().getY());
                    int x=JPanelJuego.getPosicionX(JPanelJuego.getJugador().getX());
 //                   if(jpaneljuego.getMapa().getObjetoMapa(y,x)==")
                    JPanelJuego.getJugador().CrearBomb();
                }
                if(get.getKeyCode()==Dialog.Controles.getB()){
                    if(JPanelJuego.getJugador().getDETONATOR()&&JPanelJuego.getJugador().bombasPuestas()!=0)
                        JPanelJuego.getJugador().getBombs().get(0).detonar(0);
                }
                if(get.getKeyCode()==Dialog.Controles.getStart());

               // JPanelJuego.getMapa().setObjetoMapa(JPanelJuego.getPosicionY(JPanelJuego.getJugador().getCenterY()),JPanelJuego.getPosicionX(JPanelJuego.getJugador().getCenterX()),"B");
                JPanelJuego.getMapa().mostrarMapa();
            }
    }

    private boolean compararKeyEvent(KeyEvent e) {
        for(int size=keys.size(),i=0;i<size;i++)
            if(e.getKeyCode()==keys.get(i).getKeyCode())
                return true;
        return false;
    }

    private void eliminarKeyEvent(KeyEvent e) {
        if(e.getKeyCode()==Controles.getArriba()){
            arriba=false;
            Sonidos.getInstance().getSonido(Sonidos.UP).stop();
        }if(e.getKeyCode()==Controles.getAbajo()){
            abajo=false;
            Sonidos.getInstance().getSonido(Sonidos.DOWN).stop();
        }if(e.getKeyCode()==Controles.getIzquierda()){
            izquierda=false;
            Sonidos.getInstance().getSonido(Sonidos.LEFT).stop();
        }if(e.getKeyCode()==Controles.getDerecha()){
            derecha=false;
            Sonidos.getInstance().getSonido(Sonidos.RIGHT).stop();
        }
    }

    public void setMovimiento(Thread object) {
        thread.stop();
        thread=null;
    }



    
}
