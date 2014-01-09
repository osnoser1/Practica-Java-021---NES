/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package GUI;

import Bomberman.Configuracion.Configuracion;
import Bomberman.Core.Constantes;
import Bomberman.Core.ControlJuego;
import Dependencias.Imagenes;
import Dependencias.Mapa;
import Personajes.Bomb;
import Personajes.Bomberman;
import Personajes.Enemigo;
import Personajes.Ladrillo;
import Personajes.Personaje;
import Sonidos.Sonidos;
import Utilidades.Graficos.Sprite;
import Utilidades.Graficos.Sprite.Estado;
import motor.core.Camara;
import Utilidades.Juego.Interfaz;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Alfonso Andrés
 */
public class JPanelJuego extends Interfaz {
    
    private static JPanelJuego instance;
    private int x, y;
    private boolean derrotados, powerup, puerta;
    private Mapa mapa;
    private BufferedImage imagen, buffer;
    private Dimension SIZE, tamañoVentana;
    private CopyOnWriteArrayList<Enemigo> enemigos;
    private CopyOnWriteArrayList<Ladrillo> ladrillos;
    private Bomberman[] jugadores;
    private Camara ventana;
    private Graphics g2;
    private ControlJuego controlJuego;
    private JPanelInformacion jPanelInformacion;
    
    private JPanelJuego(JPanelContenedor jPanelContenedor) {
        super(jPanelContenedor);
        initComponents();
    }
    
    public static JPanelJuego getInstance(JPanelContenedor jPanelContenedor) {
        return instance == null ? (instance = new JPanelJuego(jPanelContenedor)) : instance;
    }

    private void initComponents() {
        SIZE = new Dimension(1240, 520);
        tamañoVentana = Configuracion.getInstance().tamañoVentana;
        ventana = new Camara(new Rectangle(620, 520), SIZE);
        ladrillos = new CopyOnWriteArrayList<>();
        enemigos = new CopyOnWriteArrayList<>();
        jugadores = new Bomberman[4];
        mapa = Mapa.getInstance();
        imagen = new BufferedImage(SIZE.width, SIZE.height, BufferedImage.TYPE_INT_RGB);
        buffer = new BufferedImage(SIZE.width, SIZE.height, BufferedImage.TYPE_INT_RGB);
        x = imagen.getWidth() / Mapa.COLUMNAS;
        y = imagen.getHeight() / Mapa.FILAS;
        jugadores[0] = new Bomberman(40, 40);
        pintarMapa(buffer.createGraphics());
        g2 = imagen.getGraphics();
        g2.drawImage(buffer, 0, 0, null);
        controlJuego = new ControlJuego(this);
        jPanelInformacion = JPanelInformacion.getInstance();
    }

    @Override
    public void reiniciar() {
        borrarEnemigos();
        ladrillos.clear();
        mapa.borrarMapa();
        pintarMapa(g2);
        ventana.reiniciar();
        primerJugador().reiniciar(1, 1);
        powerup = false;
        puerta = false;
    }

    public CopyOnWriteArrayList<Enemigo> getEnemigos() {
        return enemigos;
    }

    void setSIZE(Dimension dim) {
        int x1 = (int) Math.round(dim.width / 16.0);
        int y1 = (int) Math.round(dim.height / 14.0);
        SIZE = new Dimension(dim.width * 2 - x1, dim.height - y1);
        System.out.println(dim + " " + SIZE + " " + y1 + " " + x1);
    }

    public void pintarMapa(Graphics g) {
        g.setColor(new java.awt.Color(80, 160, 0));
        g.fillRect(x, y, SIZE.width - x * 2, SIZE.height - y * 2);
        for(int i = 0; i < Mapa.FILAS; i++) {
            for(int j = 0; j < Mapa.COLUMNAS; j++) {
                switch(mapa.getObjetoMapa(i, j)) {
                    case "A":
                        g.drawImage(Imagenes.ACERO, Math.round(j * x), Math.round(i * y), x, y, null);
                        break;
                }
            }
        }
    }

    public void pintarCasilla(int i, int j) {
        int c = -1;
        if (!puerta) {
            puerta = true;
            c = Imagenes.LADRILLO_ESPECIAL.size() - 1;
        } else if(!powerup) {
            powerup = true;
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
        Sprite personaje = Constantes.Objetos.getInstance(a);
        if(personaje != null)
            personaje.setLocation(j * x, i * y);
        return (Enemigo)personaje;
    }

    private void dibujarPersonajes(Graphics g) {
        for(Enemigo enemigo : enemigos) {
            if(ventana.contiene(enemigo.getRectagulo()))
                enemigo.pintar(g);
        }
        primerJugador().pintar(g);
    }

    private void dibujarBombas(Graphics g) {
        for(Bomb bomba : primerJugador().getBombs()) {
            if(ventana.contiene(bomba.getRectagulo()))
                bomba.pintar(g);
        }
    }

    private void dibujarLadrillos(Graphics g) {
        for(Ladrillo ladrillo : ladrillos) {
            if(!ventana.contiene(ladrillo.getRectagulo()))
                continue;
            ladrillo.pintar(g);
            if(ladrillo.ladrilloespecial != null)
                ladrillo.ladrilloespecial.Dibujar(g);
        }
    }

    public CopyOnWriteArrayList<Ladrillo> getLadrillos() {
        return ladrillos;
    }

    public void borrarLadrillo(Point posicionMapa) {
        for(Ladrillo ladrillo : ladrillos) {
            if(ladrillo.getPosicionMapa().equals(posicionMapa)) {
                ladrillo.setEstadoActual(Personaje.Estado.MUERTE);
                if(ladrillo.getLadrilloEspecial() != null) {
                    ladrillo.getLadrilloEspecial().crearEnemigos();
                    if(!ladrillo.getLadrilloEspecial().esPuerta())
                        ladrillo.getLadrilloEspecial().eliminarPowerup();
                }
            }
        }
    }

    public void borrarJugador(Point posicionMapa) {
        if(primerJugador().getPosicionMapa().equals(posicionMapa)) {
            borrarJugador();
        }
    }
    
    public void borrarJugador() {
        Sonidos.getInstance().detener(Sonidos.UP, Sonidos.DOWN, Sonidos.LEFT, Sonidos.RIGHT);
        Sonidos.getInstance().get(Sonidos.DEATH).play();
        primerJugador().setEstadoActual(Personaje.Estado.MUERTE);
    }
    
    public void borrarEnemigos() {
        for(Enemigo enemigo : enemigos) {
            enemigo.detenerInteligencia();
        }
        enemigos.clear();
    }

    public void borrarEnemigo(Point posicionMapa) {
        for(Enemigo enemigo : enemigos) {
            if(enemigo.getPosicionMapa().equals(posicionMapa)){
                enemigo.muerte();
                JPanelInformacion.getInstance().aumentarPuntaje(enemigo.getPuntaje());
            }
        }
    }

    public void borrarBombs(Point posicionMapa) {
        for(Bomb bomba : primerJugador().getBombs()) {
            if(bomba.getEstadoActual() != Personaje.Estado.MUERTE && bomba.getPosicionMapa().equals(posicionMapa)) {
                bomba.detonar();
                return;
            }
        }
    }

    public int getCantidadEnemigos() {
        return enemigos.size();
    }

    private void actualizarMapa() {
        mapa.borrarMapa();
        for(Bomberman jugador : jugadores) {
            if(jugador == null)
                continue;
            mapa.setObjeto(jugador.getIdentificacion(), jugador.getPosicionMapa());
        }
        for(Enemigo enemigo : enemigos) {
            if(enemigo.getEstadoActual() != Personaje.Estado.MUERTE)
                mapa.setObjeto(enemigo.getIdentificacion(), enemigo.getPosicionMapa());
        }
        for(Ladrillo ladrillo : ladrillos) {
            if (ladrillo.getEstadoActual() != Personaje.Estado.ELIMINADO) 
                mapa.setObjeto("L", ladrillo.getPosicionMapa());
            else if(ladrillo.getLadrilloEspecial() != null) {
                if(!ladrillo.getLadrilloEspecial().esPuerta())
                    mapa.setObjeto("S", ladrillo.getPosicionMapa());
                else
                    mapa.setObjeto("Q", ladrillo.getPosicionMapa());
            }
        }
        for(Bomb bomba : primerJugador().getBombs()) {
            mapa.setObjeto("X", bomba.getPosicionMapa());
        }
    }

    public void activarInteligencias() {
        for (Enemigo enemigo : enemigos) {
            enemigo.getInteligencia().iniciar();
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

    @SuppressWarnings("element-type-mismatch")
    public void removerEnemigo(Personaje personaje) {
        enemigos.remove(personaje);
        if(enemigos.isEmpty()) {
            if(!derrotados)
                Sonidos.getInstance().get(Sonidos.PAUSE).play();
            derrotados = true;
        } else
            derrotados = false;
    }
    
    @Override
    public void actualizar(long tiempoTranscurrido) {
        ventana.actualizar(primerJugador().getCentro());
        actualizarJugador(tiempoTranscurrido);
        for(Enemigo enemigo : getEnemigos()) {
            enemigo.borrar(g2, buffer);
            enemigo.actualizar(this, tiempoTranscurrido);
            if(enemigo.getEstadoActual() == Personaje.Estado.ELIMINADO) {
                removerEnemigo(enemigo);
            }
        }
        for(Ladrillo ladrillo : ladrillos) {
            ladrillo.borrar(g2, buffer);
            ladrillo.actualizar(this, tiempoTranscurrido);
            if(ladrillo.getEstadoActual() == Personaje.Estado.ELIMINADO &&
                    !ladrillo.isEspecial()) {
                Mapa.getInstance().setObjeto("V", ladrillo.getPosicionMapa());
                ladrillos.remove(ladrillo);
            }
        }
        controlJuego.actualizar();
    }

    @Override
    public void pintar(Graphics g) {
        jPanelInformacion.pintar(g);
        Rectangle posicion = ventana.getPosicion();
        actualizarMapa();
        pintarImagen();
        g.create(0, jPanelInformacion.getAlto(), tamañoVentana.width, tamañoVentana.height).drawImage(imagen.getSubimage(posicion.x, posicion.y, posicion.width, posicion.height), 0, 0, tamañoVentana.width, SIZE.height, null);
    }
    
    public void pintarImagen() {
        dibujarLadrillos(g2);
        dibujarBombas(g2);
        dibujarPersonajes(g2);
        controlJuego.pintar(g2);
    }
    
    public void generarMapa() {
        Random random = new Random();
        int a, b, c;
        mapa.setObjeto("B", (short)1, (short)1);
        for(int i = 0; i < 55; i++) {
            do {
                a = random.nextInt(30);
                b = random.nextInt(12);
                if(a < 3 && b == 1 || a == 1 && b < 3)
                    continue;
                if("V".equals(mapa.getObjetoMapa(b, a))) {
                    mapa.setObjeto("L", (short)b, (short)a);
                    pintarCasilla(b, a);
                    break;
                }
            } while(true);
        }
        for(int i = 0; i < 10; i++) {
            do {
                a = random.nextInt(30);
                b = random.nextInt(12);
                c = random.nextInt(8);
                if("V".equals(mapa.getObjetoMapa(b, a))) {
                    mapa.setObjeto(determinarEnemigo(c), (short)b, (short)a);
                    pintarCasilla(b, a);
                    break;
                }
            } while(true);
        }
    }
        
    public String determinarEnemigo(int c) {
        return c > 6 ? Constantes.Objetos.PONTAN.getValue() : Constantes.Objetos.getEnemigos()[c].getValue();
    }

    private void actualizarJugador(long tiempoTranscurrido) {
        primerJugador().borrar(g2, buffer);
        primerJugador().actualizar(this, tiempoTranscurrido);
        if(primerJugador().getEstadoActual() == Estado.ELIMINADO) {
            if(Sonidos.getInstance().get(Sonidos.JUST_DIED).isPlaying())
                return;
            jPanelInformacion.disminuirVidasRestantes();
            jPanelInformacion.detenerCuentaRegresiva();
            if(jPanelInformacion.getVidasRestantes() < 0) {
                jPanelInformacion.setVidasRestantes(2);
                JPanelAvisos.getInstance(null).setNivel((short)1);
                jPanelContenedor.cambiarInterfaz(Escenas.ESCENA_GAME_OVER);
            } else
                jPanelContenedor.cambiarInterfaz(Escenas.ESCENA_STAGE);
        } else if(primerJugador().isEntroALaPuerta()) {
            if(Sonidos.getInstance().get(Sonidos.LEVEL_COMPLETE).isPlaying())
                return;
            jPanelInformacion.detenerCuentaRegresiva();
            JPanelAvisos.getInstance(null).aumentarNivel();
            if(JPanelAvisos.getInstance(null).finDeJuego()) {
                
            } else
                jPanelContenedor.cambiarInterfaz(Escenas.ESCENA_STAGE);
        }
    }
       
}