/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Bomberman.Core.Constantes;
import Controladores.ControladorKeyBoardJPanelJuego;
import Dependencias.Imagenes;
import Dependencias.Mapa;
import Hilos.HiloPrincipal;
import Personajes.*;
import Sonidos.Sonidos;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author Alfonso Andrés
 */
public class JPanelJuego extends javax.swing.JPanel{

//    private Bomberman Jugador;
    private boolean derrotados;
    private static JPanelJuego instance;
    private HiloPrincipal hiloPrincipal;
    private boolean Powerup, Puerta;
    private Mapa mapa;
    private BufferedImage imagen;
    private Dimension SIZE;
    private Vector<Enemigo> enemigos;
    private Vector<Ladrillo> ladrillos;
    private Bomberman[] jugadores;
    private static int x, y;
    private int posX;
    
//    
//    public JPanelJuego(){
//        super(new java.awt.GridLayout(Mapa.FILAS,Mapa.COLUMNAS));
//        initComponents();
//    }

    
    private JPanelJuego() {
        super(new java.awt.GridLayout(Mapa.FILAS,Mapa.COLUMNAS));
        initComponents();
    }
    
    public static JPanelJuego getInstance() {
        return instance == null ? (instance = new JPanelJuego()) : instance;
    }
    
    @SuppressWarnings("UseOfObsoleteCollectionType")
    private void initComponents() {
        SIZE = new Dimension(1240,520);
        setOpaque(false);
        setRequestFocusEnabled(true);
        requestFocusInWindow();
        setFocusable(true);
        setPreferredSize(SIZE);
        addKeyListener(ControladorKeyBoardJPanelJuego.getInstance());
        ladrillos = new Vector<>();
        enemigos = new Vector<>();
        jugadores = new Bomberman[4];
        mapa = Mapa.getInstance();
        imagen = new BufferedImage(SIZE.width, SIZE.height, BufferedImage.TYPE_INT_RGB);
        x = imagen.getWidth() / 31;
        y = imagen.getHeight() / 13;
        pintarMapa(imagen.createGraphics());
//        primerJugador()=new Personajes.Bomberman(x,y);
    }
    
    public void reiniciarJPanelJuego(){
        hiloPrincipal.stop();
        hiloPrincipal = null;
        borrarEnemigos();
        borrarLadrillos();
        mapa.borrarMapa();
        this.Powerup = false;
        this.Puerta = false;
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
    
    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }
    
//    public static Personajes.Bomberman getJugador() {
//        return primerJugador();
//    }
    private void modoEdicion(Graphics2D g2) {
        
    }

    public Vector<Enemigo> getEnemigos() {
        return enemigos;
    }

    void setSIZE(Dimension dim) {
        
        int x1=(int)Math.round(dim.width/16.0);
        int y1/*=(int)Math.round(dim.height/13.0)*/;
//        if(!editing){
//            y1=(int)Math.round(dim.height/14.0);
//            this.SIZE=new Dimension(dim.width*2-x1,dim.height-y1);
//        }else{
            y1=(int)Math.round(dim.height/26.0);
            this.SIZE=new Dimension(dim.width*2-x1,dim.height-y1);
//        }
        
//        this.SIZE=new Dimension(dim.width*2-x1,dim.height-y1);
        System.out.println(dim+" "+SIZE+" "+y1+" "+x1);
        this.setPreferredSize(SIZE);
        this.setSize(SIZE);
        double x0=x;
        double y0=y;
        //x=(int)Math.round(SIZE.width/31.0);
        //y=(int)Math.round(SIZE.height/13.0);
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
        //x=(int)Math.round(SIZE.width/31.0);
        //y=(int)Math.round(SIZE.height/13.0);

        g2.fillRect(x,y,SIZE.width-x*2,SIZE.height-y*2);
        for(int i=0;i<Mapa.FILAS;i++) {
            for(int j=0;j<Mapa.COLUMNAS;j++){
                switch (mapa.getObjetoMapa(i,j)) {
                    case "A":
                        g2.drawImage(Imagenes.ACERO, Math.round(j*x),(int)Math.round(i*y),(int)x,(int)y,this);
                        break;
                }
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
        if(mapa.getObjetoMapa(j,i).equals("A"))
            return;
        if(primerJugador()!=null&&getPosicionX(primerJugador().getCenterX())==i&&getPosicionY(primerJugador().getCenterY())==j)
            jugadores[0] = null;
        for(int k=0;k<enemigos.size();k++) {
            if(getPosicionX(enemigos.get(k).getCenterX())==i&&getPosicionY(enemigos.get(k).getCenterY())==j&&(enemigos.get(k).getIdentificacion() == null ? Mapa.getObjeto() != null : !enemigos.get(k).getIdentificacion().equals(Mapa.getObjeto())))
                enemigos.remove(k);
        }
        
        mapa.setObjetoMapa((short)j,(short)i);
        BufferedImage img=null;
        
        switch(Mapa.getObjeto()){
            case "B":
                jugadores[0] = new Bomberman(i*x,j*y);
                break;
            case "L":
                ladrillos.add(new Ladrillo(i*x,j*y,c));
                break;
            case "V":
                break;
            default:
               enemigos.add(determinarEnemigo(i, j, Mapa.getObjeto()));
//               if(editing)
//                   enemigos.get(enemigos.size()-1).getInteligencia().getTimer().stop();
                break;
        }
        imagen.createGraphics().drawImage(img,imagen.getWidth()/31*i,imagen.getHeight()/13*j,imagen.getWidth()/31,imagen.getHeight()/13, this);
    }
    public Enemigo determinarEnemigo(int i, int j, String a){
        Personaje personaje = Constantes.Objetos.getInstance(a);
        if(personaje != null)
            personaje.setLocation(i * x, j * y);
        return (Enemigo)personaje;
    }
    
    private void DibujarPersonajes(Graphics g) {
        for(int i=0;i<enemigos.size();i++) {
            if(enemigos.get(i)!=null)
                enemigos.get(i).DibujarEnemigo((Graphics2D)g);
        }

       if(primerJugador()!=null)
            primerJugador().DibujarJugador((Graphics2D)g);
       
    }

    private void escalamientoSprite(double x0, double y0) {

       mapa.mostrarMapa();
        if(primerJugador()!=null){
            primerJugador().setX((int)Math.round((primerJugador().getX()/x0)*x));
            primerJugador().setY((int)Math.round((primerJugador().getY()/y0)*y));
            if(primerJugador().getBombs()!=null){

                for(int i=0;i<primerJugador().getBombs().size();i++){
                    if(primerJugador().getBombs().get(i)!=null){
                        primerJugador().getBombs().get(i).setX((int)Math.round((primerJugador().getBombs().get(i).getX()/x0)*x));
                        primerJugador().getBombs().get(i).setY((int)Math.round((primerJugador().getBombs().get(i).getY()/y0)*y));

                    if(primerJugador().getBombs().get(i).getFire()!=null){
                        primerJugador().getBombs().get(i).getFire().setX((int)Math.round((primerJugador().getBombs().get(i).getFire().getX()/x0)*x));
                        primerJugador().getBombs().get(i).getFire().setY((int)Math.round((primerJugador().getBombs().get(i).getFire().getY()/y0)*y));
                        }
                    }
                }
            }
            
        }
        for(int i=0;i<ladrillos.size();i++){
            ladrillos.get(i).setX((int)Math.round(ladrillos.get(i).getX()/x0*x));
            ladrillos.get(i).setY((int)Math.round(ladrillos.get(i).getY()/y0*y));
        }
        for(int i=0;i<enemigos.size();i++){
            enemigos.get(i).setX((int)Math.round(enemigos.get(i).getX()/x0*x));
            enemigos.get(i).setY((int)Math.round(enemigos.get(i).getY()/y0*y));
        }
    }

    private void DibujarBombs(Graphics g) {
        if(primerJugador()==null)
            return;
        if(primerJugador().getBombs()!=null)
            for(int i=0;i<primerJugador().getBombs().size();i++){
                if(primerJugador().getBombs().get(i) !=null)
                    primerJugador().getBombs().get(i).Dibujar((Graphics2D)g);
                
                if(primerJugador().getBombs().get(i).getFire()!=null)
                    primerJugador().getBombs().get(i).getFire().DibujarFire(g);
            }
    }


    private void DibujarLadrillos(Graphics g) {
        for(Ladrillo ladrillo : ladrillos){
            ladrillo.Dibujar((Graphics2D)g);
               if(ladrillo.ladrilloespecial != null)
                   ladrillo.ladrilloespecial.Dibujar(g);
        }
    }

    public Vector<Ladrillo> getLadrillos() {
        return ladrillos;
    }
    public void borrarLadrillo(int a,int b){
        for(int i=0;i<ladrillos.size();i++) {
            if(ladrillos.get(i)!=null&&getPosicionX(ladrillos.get(i).getCenterX())==getPosicionX(a)&&getPosicionY(ladrillos.get(i).getCenterY())==getPosicionY(b)){
                ladrillos.get(i).start(i);
                
                if(ladrillos.get(i).getAnimation()==null&&ladrillos.get(i).getLadrilloEspecial()!=null&&ladrillos.get(i).getLadrilloEspecial().getImagen()!=null){
                    ladrillos.get(i).getLadrilloEspecial().CrearEnemigos();
                    if(!ladrillos.get(i).getLadrilloEspecial().esPuerta())
                        ladrillos.get(i).getLadrilloEspecial().EliminarPowerup();
                }
            }
        }
        
    }
    public void borrarJugador(int a,int b){

        if(getPosicionX(primerJugador().getCenterX())==getPosicionX(a)&&getPosicionY(primerJugador().getCenterY())==getPosicionY(b)){
            primerJugador().Muerte(null);

        }
    }
    public void borrarEnemigos() {
        for(int size=enemigos.size(),i=0;i<size;i++) {
            enemigos.get(i).detenerInteligencia();
        }
        enemigos.clear();
    }
    public void borrarEnemigo(int a,int b){
        for(int i=0;i<enemigos.size();i++){
            if(getPosicionX(enemigos.get(i).getCenterX())==getPosicionX(a)&&getPosicionY(enemigos.get(i).getCenterY())==getPosicionY(b)){
                enemigos.get(i).Muerte(enemigos.get(i));
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
    public void borrarBombs(int a,int b){
        
        if(primerJugador()!=null&&primerJugador().getBombs()!=null)
            for(int i=0;i<primerJugador().getBombs().size();i++){
                if(primerJugador().getBombs().get(i)!=null&&primerJugador().getBombs().get(i).getAnimation()!=null)
                  if(getPosicionX(primerJugador().getBombs().get(i).getCenterX())==getPosicionX(a)&&getPosicionY(primerJugador().getBombs().get(i).getCenterY())==getPosicionY(b)){ 
                        primerJugador().getBombs().get(i).detonar();
                           break;

                    }
        }
    }

    private void borrarLadrillos() {
        ladrillos.clear();
    }

    public int getCantidadEnemigos(){
        return enemigos.size();
    }
    
    private void ActualizarMapa() {
        mapa.borrarMapa();
        if(primerJugador()!=null)
                mapa.setObjetoMapa(primerJugador().getIdentificacion(), getPosicionY(primerJugador().getCenterY()), getPosicionX(primerJugador().getCenterX()));
        
        for(int i=0;i<enemigos.size();i++){
           
            if(enemigos.get(i)!=null)             
                mapa.setObjetoMapa(enemigos.get(i).getIdentificacion(), getPosicionY(enemigos.get(i).getCenterY()), getPosicionX(enemigos.get(i).getCenterX()));  
            
            
        }
        for(int i=0;i<ladrillos.size();i++) {
            if(ladrillos.get(i)!=null&&ladrillos.get(i).getAnimation()!=null)
                mapa.setObjetoMapa("L", getPosicionY(ladrillos.get(i).getCenterY()), getPosicionX(ladrillos.get(i).getCenterX()));
            else if(ladrillos.get(i)!=null&&ladrillos.get(i).getAnimation()==null&&ladrillos.get(i).getLadrilloEspecial()!=null&&ladrillos.get(i).getLadrilloEspecial().getImagen()!=null){
                if(!ladrillos.get(i).getLadrilloEspecial().esPuerta())
                    mapa.setObjetoMapa("S", getPosicionY(ladrillos.get(i).getLadrilloEspecial().getCenterY()), getPosicionX(ladrillos.get(i).getLadrilloEspecial().getCenterX()));
                else
                    mapa.setObjetoMapa("Q", getPosicionY(ladrillos.get(i).getLadrilloEspecial().getCenterY()), getPosicionX(ladrillos.get(i).getLadrilloEspecial().getCenterX()));
               
            }
        }
        if(primerJugador()!=null&&primerJugador().getBombs()!=null)
            for(int i=0;i<primerJugador().getBombs().size();i++) {
            if(primerJugador().getBombs().get(i) !=null)
               mapa.setObjetoMapa("X", getPosicionY(primerJugador().getBombs().get(i).getCenterY()), getPosicionX(primerJugador().getBombs().get(i).getCenterX()));
        }

    }
    public void activarInteligencias(){
        for(int i=0;i<enemigos.size();i++) {
            if(enemigos.get(i)!=null)
                enemigos.get(i).getInteligencia().getTimer().start();
        }
 
    }

    public BufferedImage getImagen() {
        return imagen;
    }

    public Bomberman[] getJugadores() {
        return jugadores;
    }
    
    public Bomberman getJugador(int i){
        return jugadores[i];
    }
    
    public void fijarJugador(int index, Bomberman bomberman){
        jugadores[index] = bomberman;
    }
    
    public Bomberman primerJugador(){
        return jugadores[0];
    }
    
    public Enemigo getEnemigo(int i){
        return enemigos.get(i);
    }
}