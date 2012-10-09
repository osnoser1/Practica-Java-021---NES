/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Dependencias.Imagenes;
import Dependencias.Mapa;
import Hilos.HiloPrincipal;
import Personajes.*;
import Sonidos.Sonidos;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Alfonso Andrés
 */
public class JPanelJuego extends javax.swing.JPanel{

    public  static Personajes.Bomberman Jugador=null;
    private static boolean derrotados=false;


    HiloPrincipal hiloPrincipal;
    boolean editing=false,Powerup=false,Puerta=false;
    private static Mapa mapa1;
    BufferedImage imagen;
    Dimension SIZE;
    static ArrayList<Personajes.Enemigo> enemigos;
    static ArrayList<Personajes.Ladrillo> Ladrillos;
    private static int x,y;
    private int posX;
    private static JPanelGrafico jPanelGrafico1;
    
//    
//    public JPanelJuego(){
//        super(new java.awt.GridLayout(Mapa.FILAS,Mapa.COLUMNAS));
//        initComponents();
//    }

    public JPanelJuego(JPanelGrafico jPanelGrafico1) {
        super(new java.awt.GridLayout(Mapa.FILAS,Mapa.COLUMNAS));
        JPanelJuego.jPanelGrafico1=jPanelGrafico1;
        initComponents();
    }
    
    private void initComponents() {
        SIZE=new Dimension(1240,520);
        this.setOpaque(false);
        this.setRequestFocusEnabled(true);
        this.requestFocusInWindow();
        this.setFocusable(true);
        this.setPreferredSize(SIZE);
        Ladrillos=new ArrayList<>();
        enemigos=new ArrayList<>();
        mapa1=new Mapa();
        imagen=new BufferedImage(SIZE.width,SIZE.height,BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g2=imagen.createGraphics();
           pintarMapa(g2);
//        Jugador=new Personajes.Bomberman(x,y);

    }
    
    public void reiniciarJPanelJuego(){
        hiloPrincipal.stop();
        hiloPrincipal=null;
        Jugador=null;
        borrarEnemigos();
        borrarLadrillos();
        mapa1.borrarMapa();
        this.Powerup=false;
        this.Puerta=false;
    }

    public HiloPrincipal getHiloPrincipal() {
        return hiloPrincipal;
    }

    public void setHiloPrincipal(HiloPrincipal hiloPrincipal) {
        this.hiloPrincipal = hiloPrincipal;
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        
        
        g.drawImage(imagen,posX,0,SIZE.width,SIZE.height, this);
        pintarMapa((Graphics2D)g);
        ActualizarMapa();
        DibujarLadrillos(g);
        DibujarBombs(g);
        DibujarPersonajes(g);
        
        
    }

    public static JPanelGrafico getJPanelGrafico() {
        return jPanelGrafico1;
    }
    
    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }
    
    public static Personajes.Bomberman getJugador() {
        return Jugador;
    }
    private void modoEdicion(Graphics2D g2) {
        
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    public static Mapa getMapa() {
        return mapa1;
    }

    public void setMapa(Mapa mapa) {
        JPanelJuego.mapa1 = mapa;
    }

    public static ArrayList<Enemigo> getenemigos() {
        return enemigos;
    }




    void setSIZE(Dimension dim) {
        
        int x1=(int)Math.round(dim.width/16.0);
        int y1/*=(int)Math.round(dim.height/13.0)*/;
        if(!editing){
            y1=(int)Math.round(dim.height/14.0);
            this.SIZE=new Dimension(dim.width*2-x1,dim.height-y1);
        }else{
            y1=(int)Math.round(dim.height/26.0);
            this.SIZE=new Dimension(dim.width*2-x1,dim.height-y1);
        }
        
//        this.SIZE=new Dimension(dim.width*2-x1,dim.height-y1);
        System.out.println(dim+" "+SIZE+" "+y1+" "+x1);
        this.setPreferredSize(SIZE);
        this.setSize(SIZE);
        double x0=x;
        double y0=y;
        x=(int)Math.round(SIZE.width/31.0);
        y=(int)Math.round(SIZE.height/13.0);
        escalamientoSprite(x0,y0);
        
        // this.imagen=ImageUtilities.convertImage(this.imagen.getScaledInstance(this.SIZE.width,this.SIZE.height,Image.SCALE_DEFAULT));
    }
    

    public static int gety() {
        return y;
    }

    public static int getx() {
        return x;
    }
    public void pintarMapa(Graphics2D g2) {
        g2.setColor(new java.awt.Color(80,160,0));
        x=(int)Math.round(SIZE.width/31.0);
        y=(int)Math.round(SIZE.height/13.0);

        g2.fillRect(x,y,SIZE.width-x*2,SIZE.height-y*2);
        for(int i=0;i<Mapa.FILAS;i++)
            for(int j=0;j<Mapa.COLUMNAS;j++){
                switch (mapa1.getObjetoMapa(i,j)) {
                    case "A":
                        g2.drawImage(Imagenes.ACERO,(int)Math.round(j*x),(int)Math.round(i*y),(int)x,(int)y,this);
                        break;
                }
            }
    }
    public static short getPosicionX(int X){
        return (short)(X/x);
    }
    public static short getPosicionY(int Y){
        return (short)(Y/y);
    }
    public void pintarCasilla(int i, int j) {
        int c=-1;
        if(!Puerta){
            Puerta=true;
            c=Imagenes.LADRILLO_ESPECIAL.size()-1;
        }
        else if(!Powerup){
            Powerup=true;
            c=new Random().nextInt(6);
        }
        if(mapa1.getObjetoMapa(j,i).equals("A"))
            return;
        if(Jugador!=null&&getPosicionX(Jugador.getCenterX())==i&&getPosicionY(Jugador.getCenterY())==j)
            Jugador=null;
        for(int k=0;k<enemigos.size();k++)
            if(getPosicionX(enemigos.get(k).getCenterX())==i&&getPosicionY(enemigos.get(k).getCenterY())==j&&(enemigos.get(k).getIdentificacion() == null ? Mapa.getObjeto() != null : !enemigos.get(k).getIdentificacion().equals(Mapa.getObjeto())))
                enemigos.remove(k);
        
        mapa1.setObjetoMapa((short)j,(short)i);
        BufferedImage img=null;
        
        switch(Mapa.getObjeto()){
            case "B":
                Jugador=new Bomberman(i*x,j*y);
                break;
            case "L":
                Ladrillos.add(new Ladrillo(i*x,j*y,c));
                break;
            case "V":
                break;
            default:
               enemigos.add(DeterminarEnemigo(i,j,Mapa.getObjeto()));
               if(editing)
                   enemigos.get(enemigos.size()-1).getInteligencia().getTimer().stop();
                break;
        }
        imagen.createGraphics().drawImage(img,imagen.getWidth()/31*i,imagen.getHeight()/13*j,imagen.getWidth()/31,imagen.getHeight()/13, this);
    }
    public Enemigo DeterminarEnemigo(int i,int j,String a){
        switch(a){
            case "b":
                return new Balloom(i*x,j*y);    
                
            case "O":
                return new Oneal(i*x,j*y); 
                
            case "D":
                return new Doll(i*x,j*y);
                
            case "M":
                return new Minvo(i*x,j*y);    
                
            case "K":
                return new Kondoria(i*x,j*y); 
                
            case "o":
                return new Ovapi(i*x,j*y);
                
            case "P":
                return new Pass(i*x,j*y);
                
            case "p":
                return new Pontan(i*x,j*y);
                
        }
        return null;
    }
    private void DibujarPersonajes(Graphics g) {
       
        for(int i=0;i<enemigos.size();i++)
            if(enemigos.get(i)!=null)
                enemigos.get(i).DibujarEnemigo((Graphics2D)g);

       if(Jugador!=null)
            Jugador.DibujarJugador((Graphics2D)g);
       
    }

    private void escalamientoSprite(double x0, double y0) {

       mapa1.mostrarMapa();
        if(Jugador!=null){
            Jugador.setX((int)Math.round((Jugador.getX()/x0)*x));
            Jugador.setY((int)Math.round((Jugador.getY()/y0)*y));
            if(Jugador.getBombs()!=null){

                for(int i=0;i<Jugador.getBombs().size();i++){
                    if(Jugador.getBombs().get(i)!=null){
                        Jugador.getBombs().get(i).setX((int)Math.round((Jugador.getBombs().get(i).getX()/x0)*x));
                        Jugador.getBombs().get(i).setY((int)Math.round((Jugador.getBombs().get(i).getY()/y0)*y));

                    if(Jugador.getBombs().get(i).getFire()!=null){
                        Jugador.getBombs().get(i).getFire().setX((int)Math.round((Jugador.getBombs().get(i).getFire().getX()/x0)*x));
                        Jugador.getBombs().get(i).getFire().setY((int)Math.round((Jugador.getBombs().get(i).getFire().getY()/y0)*y));
                        }
                    }
                }
            }
            
        }
        for(int i=0;i<Ladrillos.size();i++){
            Ladrillos.get(i).setX((int)Math.round(Ladrillos.get(i).getX()/x0*x));
            Ladrillos.get(i).setY((int)Math.round(Ladrillos.get(i).getY()/y0*y));
        }
        for(int i=0;i<enemigos.size();i++){
            enemigos.get(i).setX((int)Math.round(enemigos.get(i).getX()/x0*x));
            enemigos.get(i).setY((int)Math.round(enemigos.get(i).getY()/y0*y));
        }
    }

    private void DibujarBombs(Graphics g) {
        if(Jugador==null)
            return;
        if(Jugador.getBombs()!=null)
            for(int i=0;i<Jugador.getBombs().size();i++){
                if(Jugador.getBombs().get(i) !=null)
                    Jugador.getBombs().get(i).Dibujar((Graphics2D)g);
                
                if(Jugador.getBombs().get(i).getFire()!=null)
                    Jugador.getBombs().get(i).getFire().DibujarFire(g);
            }
    }


    private void DibujarLadrillos(Graphics g) {
        
        for(int i=0;i<Ladrillos.size();i++){
               Ladrillos.get(i).Dibujar((Graphics2D)g);
               if(Ladrillos.get(i).ladrilloespecial!=null)
                   Ladrillos.get(i).ladrilloespecial.Dibujar(g);
        }
    }

    public static ArrayList<Ladrillo> getLadrillos() {
        return Ladrillos;
    }
    public static void borrarLadrillo(int a,int b){
        for(int i=0;i<Ladrillos.size();i++)
            if(Ladrillos.get(i)!=null&&getPosicionX(Ladrillos.get(i).getCenterX())==getPosicionX(a)&&getPosicionY(Ladrillos.get(i).getCenterY())==getPosicionY(b)){
                Ladrillos.get(i).start(i);
                
                if(Ladrillos.get(i).getAnimation()==null&&Ladrillos.get(i).getLadrilloEspecial()!=null&&Ladrillos.get(i).getLadrilloEspecial().getImagen()!=null){
                    Ladrillos.get(i).getLadrilloEspecial().CrearEnemigos();
                    if(!Ladrillos.get(i).getLadrilloEspecial().esPuerta())
                        Ladrillos.get(i).getLadrilloEspecial().EliminarPowerup();
                }
            }
        
    }
    public static void borrarJugador(int a,int b){

        if(getPosicionX(Jugador.getCenterX())==getPosicionX(a)&&getPosicionY(Jugador.getCenterY())==getPosicionY(b)){
            Jugador.Muerte(-1);

        }
    }
    public static void borrarEnemigos() {
        for(int size=enemigos.size(),i=0;i<size;i++)
            enemigos.get(i).detenerInteligencia();
        enemigos.clear();
    }
    public static void borrarEnemigo(int a,int b){
        for(int i=0;i<enemigos.size();i++){
            if(getPosicionX(enemigos.get(i).getCenterX())==getPosicionX(a)&&getPosicionY(enemigos.get(i).getCenterY())==getPosicionY(b)){
                enemigos.get(i).Muerte(i);
                JPanelInformacion.aumentarPuntaje(enemigos.get(i).getPoint());
    
            }
        }
        if(enemigos.isEmpty()){
            if(!derrotados)
                Sonidos.getInstance().getSonido(Sonidos.PAUSE).play();
            derrotados=true;
        }else{
            derrotados=false;
        }
    }
    public static void borrarBombs(int a,int b){
        
        if(Jugador!=null&&Jugador.getBombs()!=null)
            for(int i=0;i<Jugador.getBombs().size();i++){
                if(Jugador.getBombs().get(i)!=null&&Jugador.getBombs().get(i).getAnimation()!=null)
                  if(getPosicionX(Jugador.getBombs().get(i).getCenterX())==getPosicionX(a)&&getPosicionY(Jugador.getBombs().get(i).getCenterY())==getPosicionY(b)){ 
                        Jugador.getBombs().get(i).detonar(i);
                           break;

                    }
        }
    }

    private void borrarLadrillos() {
        Ladrillos.clear();
    }

    public int getCantidadEnemigos(){
        return enemigos.size();
    }
    
    private void ActualizarMapa() {
         mapa1=new Mapa();
         if(Jugador!=null)
                mapa1.setObjetoMapa(getPosicionY(Jugador.getCenterY()), getPosicionX(Jugador.getCenterX()), Jugador.getIdentificacion());
        
        for(int i=0;i<enemigos.size();i++){
           
            if(enemigos.get(i)!=null)             
                mapa1.setObjetoMapa(getPosicionY(enemigos.get(i).getCenterY()), getPosicionX(enemigos.get(i).getCenterX()), enemigos.get(i).getIdentificacion());  
            
            
        }
        for(int i=0;i<Ladrillos.size();i++)
               if(Ladrillos.get(i)!=null&&Ladrillos.get(i).getAnimation()!=null)
                   mapa1.setObjetoMapa(getPosicionY(Ladrillos.get(i).getCenterY()), getPosicionX(Ladrillos.get(i).getCenterX()),"L");
               else if(Ladrillos.get(i)!=null&&Ladrillos.get(i).getAnimation()==null&&Ladrillos.get(i).getLadrilloEspecial()!=null&&Ladrillos.get(i).getLadrilloEspecial().getImagen()!=null){
                   if(!Ladrillos.get(i).getLadrilloEspecial().esPuerta())
                       mapa1.setObjetoMapa(getPosicionY(Ladrillos.get(i).getLadrilloEspecial().getCenterY()), getPosicionX(Ladrillos.get(i).getLadrilloEspecial().getCenterX()),"S");
                   else
                       mapa1.setObjetoMapa(getPosicionY(Ladrillos.get(i).getLadrilloEspecial().getCenterY()), getPosicionX(Ladrillos.get(i).getLadrilloEspecial().getCenterX()),"Q");
                  
               }
        if(Jugador!=null&&Jugador.getBombs()!=null)
            for(int i=0;i<Jugador.getBombs().size();i++)
                if(Jugador.getBombs().get(i) !=null)
                   mapa1.setObjetoMapa(getPosicionY(Jugador.getBombs().get(i).getCenterY()), getPosicionX(Jugador.getBombs().get(i).getCenterX()), "X");

    }
    public void activarInteligencias(){
        for(int i=0;i<enemigos.size();i++)
            if(enemigos.get(i)!=null)
                enemigos.get(i).getInteligencia().getTimer().start();
 
    }
            
    
}