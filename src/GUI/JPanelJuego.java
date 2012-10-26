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
public class JPanelJuego extends javax.swing.JPanel {
    
    private static JPanelJuego instance;
    private static int x, y;
    private boolean derrotados;
    private HiloPrincipal hiloPrincipal;
    private boolean Powerup, Puerta;
    private Mapa mapa;
    private BufferedImage imagen;
    private Dimension SIZE;
    private Vector<Enemigo> enemigos;
    private Vector<Ladrillo> ladrillos;
    private Bomberman[] jugadores;
    private int posX;

    private JPanelJuego() {
        super(new java.awt.GridLayout(Mapa.FILAS, Mapa.COLUMNAS));
        initComponents();
    }

    public static JPanelJuego getInstance() {
        return instance == null ? (instance = new JPanelJuego()) : instance;
    }

    private void initComponents() {
        SIZE = new Dimension(1240, 520);
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
        x = imagen.getWidth() / Mapa.COLUMNAS;
        y = imagen.getHeight() / Mapa.FILAS;
        jugadores[0] = new Bomberman(40, 40);
        pintarMapa(imagen.createGraphics());
    }

    public void reiniciarJPanelJuego() {
        hiloPrincipal.stop();
        borrarEnemigos();
        borrarLadrillos();
        mapa.borrarMapa();
        primerJugador().reiniciar(1, 1);
        Powerup = false;
        Puerta = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics g2 = imagen.getGraphics();
        pintarMapa((Graphics2D)g2);
        actualizarMapa();
        dibujarLadrillos(g2);
        DibujarBombs(g2);
        dibujarPersonajes(g2);
        g.drawImage(imagen, posX, 0, SIZE.width, SIZE.height, this);
    }

    public Vector<Enemigo> getEnemigos() {
        return enemigos;
    }

    void setSIZE(Dimension dim) {
        int x1 = (int) Math.round(dim.width / 16.0);
        int y1 /* = (int)Math.round(dim.height / 13.0) */;
//      if(!editing) {
        y1 = (int) Math.round(dim.height / 14.0);
//      SIZE = new Dimension(dim.width * 2 - x1, dim.height - y1);
//        } else {
//      y1 = (int)Math.round(dim.height / 26.0);
        SIZE = new Dimension(dim.width * 2 - x1, dim.height - y1);
//      }
//      SIZE = new Dimension(dim.width * 2 - x1, dim.height - y1);
        System.out.println(dim + " " + SIZE + " " + y1 + " " + x1);
        setPreferredSize(SIZE);
        setSize(SIZE);
        double x0 = x;
        double y0 = y;
//         x = (int)Math.round(SIZE.width / 31.0);
//         y = (int)Math.round(SIZE.height / 13.0);
        escalamientoSprite(x0, y0);
    }

    public static int gety() {
        return y;
    }

    public static int getx() {
        return x;
    }

    public void pintarMapa(Graphics2D g2) {
        g2.setColor(new java.awt.Color(80, 160, 0));
        // x = (int)Math.round(SIZE.width / 31.0);
        // y = (int)Math.round(SIZE.height / 13.0);
        g2.fillRect(x, y, SIZE.width - x * 2, SIZE.height - y * 2);
        for(int i = 0; i < Mapa.FILAS; i++) {
            for(int j = 0; j < Mapa.COLUMNAS; j++) {
                switch(mapa.getObjetoMapa(i, j)) {
                    case "A":
                        g2.drawImage(Imagenes.ACERO, Math.round(j * x), Math.round(i * y), x, y, this);
                        break;
                }
            }
        }
    }

    public static short getPosicionX(int X) {
        return (short)(X / x);
    }

    public static short getPosicionY(int Y) {
        return (short)(Y / y);
    }

    public void pintarCasilla(int i, int j) {
        int c = -1;
        if (!Puerta) {
            Puerta = true;
            c = Imagenes.LADRILLO_ESPECIAL.size() - 1;
        } else if(!Powerup) {
            Powerup = true;
            c = new Random().nextInt(6);
        }
        if(mapa.getObjetoMapa(i, j).equals("A"))
            return;
        switch(Mapa.getInstance().getObjetoMapa(i, j)){
            case "B":
                jugadores[0].fijarCasilla(i, j);
                break;
            case "L":
                ladrillos.add(new Ladrillo(j * x, i * y, c));
                break;
            default:
                enemigos.add(determinarEnemigo(i, j, Mapa.getInstance().getObjetoMapa(i, j)));
        }
    }

    public Enemigo determinarEnemigo(int i, int j, String a) {
        Personaje personaje = Constantes.Objetos.getInstance(a);
        if(personaje != null)
            personaje.setLocation(j * x, i * y);
        return (Enemigo)personaje;
    }

    private void dibujarPersonajes(Graphics g) {
        for(Enemigo enemigo : enemigos) {
            enemigo.DibujarEnemigo((Graphics2D)g);
        }
//        primerJugador().DibujarJugador((Graphics2D)g);
        primerJugador().pintar(g);
    }

    private void escalamientoSprite(double x0, double y0) {
        primerJugador().setX((int)Math.round(primerJugador().getX() / x0 * x));
        primerJugador().setY((int)Math.round(primerJugador().getY() / y0 * y));
        for(Bomb bomba : primerJugador().getBombs()) {
            bomba.setX((int)Math.round(bomba.getX() / x0 * x));
            bomba.setY((int)Math.round(bomba.getY() / y0 * y));
            if(bomba.getFire() != null) {
                bomba.getFire().setX((int)Math.round(bomba.getFire().getX() / x0 * x));
                bomba.getFire().setY((int)Math.round(bomba.getFire().getY() / y0 * y));
            }
        }
        for(Ladrillo ladrillo : ladrillos) {
            ladrillo.setX((int)Math.round(ladrillo.getX() / x0 * x));
            ladrillo.setY((int)Math.round(ladrillo.getY() / y0 * y));
        }
        for(Enemigo enemigo : enemigos) {
            enemigo.setX((int)Math.round(enemigo.getX() / x0 * x));
            enemigo.setY((int)Math.round(enemigo.getY() / y0 * y));
        }
    }

    private void DibujarBombs(Graphics g) {
        for(Bomb bomba : primerJugador().getBombs()) {
            bomba.Dibujar((Graphics2D) g);
            if(bomba.getFire() != null)
                bomba.getFire().dibujarFuego(g);
        }
    }

    private void dibujarLadrillos(Graphics g) {
        for(Ladrillo ladrillo : ladrillos) {
            ladrillo.Dibujar((Graphics2D) g);
            if(ladrillo.ladrilloespecial != null)
                ladrillo.ladrilloespecial.Dibujar(g);
        }
    }

    public Vector<Ladrillo> getLadrillos() {
        return ladrillos;
    }

    public void borrarLadrillo(int a, int b) {
        for(Ladrillo ladrillo : ladrillos) {
            if(getPosicionX(ladrillo.getCenterX()) == getPosicionX(a) && getPosicionY(ladrillo.getCenterY()) == getPosicionY(b)) {
                ladrillo.start();
                if(ladrillo.getAnimation() == null && ladrillo.getLadrilloEspecial() != null && ladrillo.getLadrilloEspecial().getImagen() != null) {
                    ladrillo.getLadrilloEspecial().CrearEnemigos();
                    if(!ladrillo.getLadrilloEspecial().esPuerta())
                        ladrillo.getLadrilloEspecial().EliminarPowerup();
                }
            }
        }
    }

    public void borrarJugador(int a, int b) {
        if(getPosicionX(primerJugador().getCenterX()) == getPosicionX(a) && getPosicionY(primerJugador().getCenterY()) == getPosicionY(b))
            primerJugador().Muerte(null);
    }

    public void borrarEnemigos() {
        for(Enemigo enemigo : enemigos) {
            enemigo.detenerInteligencia();
        }
        enemigos.clear();
    }

    public void borrarEnemigo(int a, int b) {
        for(Enemigo enemigo : enemigos) {
            if(getPosicionX(enemigo.getCenterX()) == getPosicionX(a) && getPosicionY(enemigo.getCenterY()) == getPosicionY(b)){
                enemigo.Muerte(enemigo);
                JPanelInformacion.aumentarPuntaje(enemigo.getPoint());
            }
        }
    }

    public void borrarBombs(int a, int b) {
        for(Bomb bomba : primerJugador().getBombs()) {
            if(bomba.getAnimation() != null)
                if(getPosicionX(bomba.getCenterX()) == getPosicionX(a) && getPosicionY(bomba.getCenterY()) == getPosicionY(b)) {
                    bomba.detonar();
                    return;
                }
        }
    }

    private void borrarLadrillos() {
        ladrillos.clear();
    }

    public int getCantidadEnemigos() {
        return enemigos.size();
    }

    private void actualizarMapa() {
        mapa.borrarMapa();
        mapa.setObjeto(primerJugador().getIdentificacion(), getPosicionY(primerJugador().getCenterY()), getPosicionX(primerJugador().getCenterX()));
        for(Enemigo enemigo : enemigos) {
            mapa.setObjeto(enemigo.getIdentificacion(), getPosicionY(enemigo.getCenterY()), getPosicionX(enemigo.getCenterX()));
        }
        for(Ladrillo ladrillo : ladrillos) {
            if (ladrillo.getAnimation() != null) 
                mapa.setObjeto("L", getPosicionY(ladrillo.getCenterY()), getPosicionX(ladrillo.getCenterX()));
            else if(ladrillo.getAnimation() == null && ladrillo.getLadrilloEspecial() != null && ladrillo.getLadrilloEspecial().getImagen() != null) {
                if(!ladrillo.getLadrilloEspecial().esPuerta())
                    mapa.setObjeto("S", getPosicionY(ladrillo.getLadrilloEspecial().getCenterY()), getPosicionX(ladrillo.getLadrilloEspecial().getCenterX()));
                else
                    mapa.setObjeto("Q", getPosicionY(ladrillo.getLadrilloEspecial().getCenterY()), getPosicionX(ladrillo.getLadrilloEspecial().getCenterX()));
            }
        }
        for(Bomb bomba : primerJugador().getBombs()) {
            mapa.setObjeto("X", getPosicionY(bomba.getCenterY()), getPosicionX(bomba.getCenterX()));
        }
    }

    public void activarInteligencias() {
        for (Enemigo enemigo : enemigos) {
            enemigo.getInteligencia().iniciarInteligencia();
        }
    }

    public BufferedImage getImagen() {
        return imagen;
    }

    public Bomberman[] getJugadores() {
        return jugadores;
    }

    public Bomberman getJugador(int i) {
        return jugadores[i];
    }

    public void fijarJugador(int index, Bomberman bomberman) {
        jugadores[index] = bomberman;
    }

    public Bomberman primerJugador() {
        return jugadores[0];
    }

    public Enemigo getEnemigo(int i) {
        return enemigos.get(i);
    }

    public void iniciarHiloPrincipal() {
        hiloPrincipal = new HiloPrincipal(this, (short) 60);
        hiloPrincipal.start();
    }

    public void detenerHiloPrincipal() {
        hiloPrincipal.stop();
    }

    public void removerEnemigo(Personaje personaje) {
        enemigos.remove(personaje);
        if(enemigos.isEmpty()){
            if(!derrotados)
                Sonidos.getInstance().getSonido(Sonidos.PAUSE).play();
            derrotados = true;
        }else
            derrotados = false;
    }
    
}
