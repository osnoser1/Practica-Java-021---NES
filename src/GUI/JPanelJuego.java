/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package GUI;

import Bomberman.Core.Constantes;
import Dependencias.Imagenes;
import Dependencias.Mapa;
import Hilos.HiloPrincipal;
import Personajes.*;
import Sonidos.Sonidos;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private CopyOnWriteArrayList<Enemigo> enemigos;
    private CopyOnWriteArrayList<Ladrillo> ladrillos;
    private Bomberman[] jugadores;
    private int posicionX;
    private Point cuartoImagen, tresCuartosImagen;

    private JPanelJuego() {
        super(new java.awt.GridLayout(Mapa.FILAS, Mapa.COLUMNAS));
        initComponents();
    }

    public static JPanelJuego getInstance() {
        return instance == null ? (instance = new JPanelJuego()) : instance;
    }

    private void initComponents() {
        SIZE = new Dimension(1240, 520);
        cuartoImagen = new Point(SIZE.width / 4, SIZE.height / 4);
        tresCuartosImagen = new Point(3 * SIZE.width / 4, 3 * SIZE.height / 4);
        setOpaque(false);
        setPreferredSize(SIZE);
        ladrillos = new CopyOnWriteArrayList<>();
        enemigos = new CopyOnWriteArrayList<>();
        jugadores = new Bomberman[4];
        mapa = Mapa.getInstance();
        imagen = new BufferedImage(SIZE.width, SIZE.height, BufferedImage.TYPE_INT_RGB);
        x = imagen.getWidth() / Mapa.COLUMNAS;
        y = imagen.getHeight() / Mapa.FILAS;
        jugadores[0] = new Bomberman(40, 40);
        pintarMapa(imagen.createGraphics());
    }

    public void reiniciar() {
        posicionX = 0;
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
        pintarMapa(g2);
        actualizarMapa();
        dibujarLadrillos(g2);
        dibujarBombas(g2);
        dibujarPersonajes(g2);
        g.drawImage(imagen, getPosicionX(), 0, SIZE.width, SIZE.height, this);
    }

    public CopyOnWriteArrayList<Enemigo> getEnemigos() {
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
//         x = (int)Math.round(SIZE.width / 31.0);
//         y = (int)Math.round(SIZE.height / 13.0);
    }

    public static int gety() {
        return y;
    }

    public static int getx() {
        return x;
    }

    public void pintarMapa(Graphics g2) {
        g2.setColor(new java.awt.Color(80, 160, 0));
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
            if(!getVisibleRect().contains(enemigo.getCentro()))
                continue;
            enemigo.pintar(g);
        }
        primerJugador().pintar(g);
    }

    private void dibujarBombas(Graphics g) {
        for(Bomb bomba : primerJugador().getBombs()) {
            bomba.pintar(g);
        }
    }

    private void dibujarLadrillos(Graphics g) {
        for(Ladrillo ladrillo : ladrillos) {
            ladrillo.pintar(g);
            if(ladrillo.ladrilloespecial != null)
                ladrillo.ladrilloespecial.Dibujar(g);
        }
    }

    public CopyOnWriteArrayList<Ladrillo> getLadrillos() {
        return ladrillos;
    }

    public void borrarLadrillo(int a, int b) {
        for(Ladrillo ladrillo : ladrillos) {
            if(ladrillo.getCenterX() == a && ladrillo.getCenterY() == b) {
                ladrillo.setEstadoActual(Personaje.Estado.MUERTE);
                if(ladrillo.getLadrilloEspecial() != null) {
                    ladrillo.getLadrilloEspecial().crearEnemigos();
                    if(!ladrillo.getLadrilloEspecial().esPuerta())
                        ladrillo.getLadrilloEspecial().eliminarPowerup();
                }
            }
        }
    }

    public void borrarJugador(int a, int b) {
        if(getPosicionX(primerJugador().getCenterX()) == getPosicionX(a) && getPosicionY(primerJugador().getCenterY()) == getPosicionY(b)) {
            borrarJugador();
        }
    }
    
    public void borrarJugador() {
        Sonidos.getInstance().detenerSonidos(Sonidos.UP, Sonidos.DOWN, Sonidos.LEFT, Sonidos.RIGHT);
        Sonidos.getInstance().getSonido(Sonidos.DEATH).play();
        primerJugador().setEstadoActual(Personaje.Estado.MUERTE);
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
                enemigo.muerte();
                JPanelInformacion.aumentarPuntaje(enemigo.getPoint());
            }
        }
    }

    public void borrarBombs(int a, int b) {
        for(Bomb bomba : primerJugador().getBombs()) {
            if(bomba.getEstadoActual() != Personaje.Estado.MUERTE && getPosicionX(bomba.getCenterX()) == getPosicionX(a) && getPosicionY(bomba.getCenterY()) == getPosicionY(b)) {
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
            if (ladrillo.getEstadoActual() != Personaje.Estado.ELIMINADO) 
                mapa.setObjeto("L", getPosicionY(ladrillo.getCenterY()), getPosicionX(ladrillo.getCenterX()));
            else if(ladrillo.getLadrilloEspecial() != null) {
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

    public void removerEnemigo(Personaje personaje) {
        enemigos.remove(personaje);
        if(enemigos.isEmpty()){
            if(!derrotados)
                Sonidos.getInstance().getSonido(Sonidos.PAUSE).play();
            derrotados = true;
        }else
            derrotados = false;
    }
    
    public void actualizar(long tiempoTranscurrido) {
        primerJugador().actualizar(this, tiempoTranscurrido);
        for(Enemigo enemigo : getEnemigos()) {
            enemigo.actualizar(this, tiempoTranscurrido);
        }
        for(Ladrillo ladrillo : getLadrillos()) {
            ladrillo.actualizar(this, tiempoTranscurrido);
        }
    }
    
    public void limpiar() {
        for(Enemigo enemigo : enemigos) {
            if(enemigo.getEstadoActual() == Personaje.Estado.ELIMINADO) {
                removerEnemigo(enemigo);
            }
        }
        for(Ladrillo ladrillo : ladrillos) {
            if(ladrillo.getEstadoActual() == Personaje.Estado.ELIMINADO &&
                    !ladrillo.isEspecial()) {
                Mapa.getInstance().setObjeto("V", JPanelJuego.getPosicionY(ladrillo.getCenterY()), JPanelJuego.getPosicionX(ladrillo.getCenterX()));
                ladrillos.remove(ladrillo);
            }
        }
        CopyOnWriteArrayList<Bomb> bombas = primerJugador().getBombs();
        for(Bomb bomba : bombas) {
            if(bomba.getEstadoActual() == Personaje.Estado.ELIMINADO) {
                bombas.remove(bomba);
            }
        }
    }

    /**
     * @param posicionX the posicionX to set
     */
    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    /**
     * @return the posicionX
     */
    public int getPosicionX() {
        return posicionX;
    }

    /**
     * @return the cuartoImagen
     */
    public Point getCuartoImagen() {
        return cuartoImagen;
    }

    /**
     * @return the tresCuartosImagen
     */
    public Point getTresCuartosImagen() {
        return tresCuartosImagen;
    }
       
}