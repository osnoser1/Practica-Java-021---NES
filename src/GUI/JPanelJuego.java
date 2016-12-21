/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Bomberman.Configuracion.Configuracion;
import Bomberman.Core.ControlJuego;
import lenguaje.utils.ImageUtilities;
import Dependencias.Imagenes;
import Dependencias.Mapa;
import Personajes.Bomb;
import Personajes.Bomberman;
import Personajes.Enemigo;
import Personajes.Ladrillo;
import Dependencias.Sonidos;
import motor.core.graphics.Sprite;
import motor.core.Camara;
import Utilidades.Juego.Interfaz;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.util.ArrayList;
import java.util.Random;
import juego.constantes.Estado;
import juego.constantes.Objetos;

/**
 *
 * @author Alfonso Andrés
 */
public class JPanelJuego extends Interfaz {

    private static JPanelJuego instance;
    private int x, y;
    private boolean derrotados, powerup, puerta;
    private final Mapa mapa;
    private final Image buffer;
    private Dimension SIZE;
    private final Dimension tamañoVentana;
    private final ArrayList<Enemigo> enemigos;
    private final ArrayList<Ladrillo> ladrillos;
    private final Bomberman[] jugadores;
    private final Camara ventana;
    private final ControlJuego controlJuego;
    private final JPanelInformacion jPanelInformacion;
    private final double escalaInternaX;

    private JPanelJuego(JPanelContenedor jPanelContenedor) {
        super(jPanelContenedor);
        SIZE = new Dimension(1240, 520);
        tamañoVentana = Configuracion.getInstance().getTamañoVentana();
        escalaInternaX = ((double)640) / (SIZE.width >> 1);
        ventana = new Camara(new Rectangle(SIZE.width >> 1, SIZE.height), SIZE);
        ladrillos = new ArrayList<>();
        enemigos = new ArrayList<>();
        jugadores = new Bomberman[1];
        mapa = Mapa.getInstance();
        buffer = ImageUtilities.createCompatibleVolatileImage(SIZE.width, SIZE.height, Transparency.OPAQUE);
        x = buffer.getWidth(null) / Mapa.COLUMNAS;
        y = buffer.getHeight(null) / Mapa.FILAS;
        Graphics2D g2d = (Graphics2D) buffer.getGraphics();
        pintarMapa(g2d);
        g2d.dispose();
        jugadores[0] = new Bomberman(40, 40);
        controlJuego = new ControlJuego(this);
        jPanelInformacion = JPanelInformacion.getInstance();
        reiniciar();
    }

    public static JPanelJuego getInstance(JPanelContenedor jPanelContenedor) {
        return instance == null ? (instance = new JPanelJuego(jPanelContenedor)) : instance;
    }

    @Override
    public final void reiniciar() {
        borrarEnemigos();
        ladrillos.clear();
        mapa.borrarMapa();
        ventana.reiniciar();
        primerJugador().reiniciar(1, 1);
        powerup = puerta = false;
        generarMapa();
    }

    public ArrayList<Enemigo> getEnemigos() {
        return enemigos;
    }

    @Override
    public void setSIZE(final Dimension dim) {
        jPanelInformacion.setSIZE(dim);
        int x1 = (int) Math.round(dim.width / 16.0);
        int y1 = (int) Math.round(dim.height / 14.0);
        SIZE = new Dimension(dim.width * 2 - x1, dim.height - y1);
        System.out.println(dim + " " + SIZE + " " + y1 + " " + x1);
    }

    private void pintarMapa(final Graphics2D g2d) {
        g2d.setColor(new java.awt.Color(80, 160, 0));
        g2d.fillRect(x, y, SIZE.width - x * 2, SIZE.height - y * 2);
        for (short i = 0; i < Mapa.FILAS; i++)
            for (short j = 0; j < Mapa.COLUMNAS; j++)
                if (mapa.contiene("A", i, j))
                    g2d.drawImage(Imagenes.ACERO, j * x, i * y, x, y, null);
    }

    public Enemigo determinarEnemigo(final int i, final int j, final String a) {
        final Sprite personaje = Objetos.getInstance(a);
        if (personaje != null)
            personaje.setLocation(j * x, i * y);
        return (Enemigo) personaje;
    }

    @Override
    public void pintar(final Graphics2D g) {
        jPanelInformacion.pintar(g);
//        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        Rectangle posicion = ventana.getPosicion();
        Graphics2D g2d = g;
        g2d.translate(0, jPanelInformacion.getAlto());
        g2d.scale(escalaInternaX, 1);
        g2d.drawImage(buffer, 0, 0, posicion.width, posicion.height, posicion.x, posicion.y, posicion.x + posicion.width, posicion.y + posicion.height, null);
        g2d.translate(-posicion.x, posicion.y);
        pintarEscena(g2d);
        g2d.dispose();
//      reportAcelMemory();
    }

    private void pintarEscena(final Graphics2D g2d) {
        dibujarLadrillos(g2d);
        dibujarBombas(g2d);
        dibujarPersonajes(g2d);
        controlJuego.pintar(g2d);
    }

    private void dibujarPersonajes(final Graphics2D g) {
        for (final Enemigo enemigo : enemigos)
            if (ventana.contiene(enemigo))
                enemigo.pintar(g);
        primerJugador().pintar(g);
    }

    private void dibujarBombas(Graphics2D g) {
        for (final Bomb bomba : primerJugador().getBombs())
            if (ventana.contiene(bomba)) {
                bomba.pintar(g);
            }
    }

    private void dibujarLadrillos(Graphics2D g) {
        for (final Ladrillo ladrillo : ladrillos) {
            if (!ventana.contiene(ladrillo))
                continue;
            ladrillo.pintar(g);
        }
    }

    public void borrarLadrillo(final int posMapaX, final int posMapaY) {
        for (final Ladrillo ladrillo : ladrillos) {
            if (ladrillo.getPosicionMapa().x != posMapaX || ladrillo.getPosicionMapa().y != posMapaY)
                continue;
            ladrillo.setEstadoActual(Estado.MUERTE.val());
            if (ladrillo.getLadrilloEspecial() == null)
                return;
            ladrillo.getLadrilloEspecial().crearEnemigos(this);
            if (!ladrillo.getLadrilloEspecial().esPuerta())
                ladrillo.getLadrilloEspecial().eliminarPowerup();
            return;
        }
    }

    public void borrarJugador(final int posMapaX, final int posMapaY) {
        if (primerJugador().getPosicionMapa().x != posMapaX || primerJugador().getPosicionMapa().y != posMapaY)
            return;
        borrarJugador();
    }

    public void borrarJugador() {
        Sonidos.getInstance().detener(Sonidos.UP, Sonidos.DOWN, Sonidos.LEFT, Sonidos.RIGHT);
        Sonidos.getInstance().play(Sonidos.DEATH);
        primerJugador().setEstadoActual(Estado.MUERTE.val());
    }

    public void borrarEnemigos() {
        for (Enemigo enemigo : enemigos)
            enemigo.detenerInteligencia();
        enemigos.clear();
    }

    public void borrarEnemigo(final int posMapaX, final int posMapaY) {
        for (final Enemigo enemigo : enemigos)
            if (enemigo.getPosicionMapa().x == posMapaX && enemigo.getPosicionMapa().y == posMapaY) {
                enemigo.muerte();
                jPanelInformacion.aumentarPuntaje(enemigo.getPuntaje());
            }
    }

    public void borrarBombs(final int posMapaX, final int posMapaY) {
        for (final Bomb bomba : primerJugador().getBombs())
            if (bomba.getEstadoActual() != Estado.MUERTE.val()
                    && bomba.getPosicionMapa().x == posMapaX && bomba.getPosicionMapa().y == posMapaY) {
                bomba.detonar(this);
                return;
            }
    }

    public int getCantidadEnemigos() {
        return enemigos.size();
    }

    public void activarInteligencias() {
        for (final Enemigo enemigo : enemigos)
            enemigo.getInteligencia().iniciar();
    }

    public Bomberman primerJugador() {
        return jugadores[0];
    }

    @Override
    public void actualizar(final long tiempoTranscurrido) {
        ventana.actualizar(primerJugador().getCentro());
        actualizarJugador(tiempoTranscurrido);
        for (int i = 0; i < enemigos.size(); i++) {
            final Enemigo enemigo = enemigos.get(i);
            enemigo.actualizar(this, tiempoTranscurrido);
            if(enemigo.getEstadoActual() == Estado.MUERTE.val()) {
                mapa.remover(enemigo);
            } else if (enemigo.getEstadoActual() == Estado.ELIMINADO.val()) {
                enemigos.remove(i--);
                if (enemigos.isEmpty()) {
                    if (!derrotados)
                        Sonidos.getInstance().play(Sonidos.PAUSE);
                    derrotados = true;
                } else
                    derrotados = false;
            } else {
                mapa.actualizar(enemigo);
            }
        }
        for (int i = 0; i < ladrillos.size(); i++) {
            final Ladrillo ladrillo = ladrillos.get(i);
            ladrillo.actualizar(this, tiempoTranscurrido);
            if (ladrillo.getEstadoActual() == Estado.ELIMINADO.val()
                    && !ladrillo.isEspecial()) {
                mapa.remover(ladrillo);
                ladrillos.remove(i--);
            }
        }
        controlJuego.actualizar();
    }

    private void generarMapa() {
        final Random r = new Random();
        int c, f, d;
        mapa.actualizar(primerJugador());
        for (int i = 0; i < 55; i++)
            do {
                c = r.nextInt(30);
                f = r.nextInt(12);
                if (c < 3 && f == 1 || c == 1 && f < 3)
                    continue;
                if (mapa.estaVacio(f, c)) {
                    agregarCasilla(r, "L", f, c);
                    break;
                }
            } while (true);
        for (int i = 0; i < 10; i++)
            do {
                c = r.nextInt(30);
                f = r.nextInt(12);
                d = r.nextInt(8);
                if (mapa.estaVacio(f, c)) {
                    agregarCasilla(r, determinarEnemigo(d), f, c);
                    break;
                }
            } while (true);
        mapa.mostrar();
    }

    private void agregarCasilla(final Random r, final String objeto, final int i, final int j) {
        int c = -1;
        if (!puerta) {
            puerta = true;
            c = 8;
        } else if (!powerup) {
            powerup = true;
            c = r.nextInt(6);
        }
        final Sprite s = objeto.equals("L") ? new Ladrillo(j * x, i * y, c) : determinarEnemigo(i, j, objeto);
        mapa.agregar(s);
        if (objeto.equals("L"))
            ladrillos.add((Ladrillo) s);
        else
            enemigos.add((Enemigo) s);
    }

    public String determinarEnemigo(final int c) {
        return c > 6 ? Objetos.PONTAN.getValue() : Objetos.getEnemigos()[c].getValue();
    }

    private void actualizarJugador(final long tiempoTranscurrido) {
        final Bomberman b = primerJugador();
        b.actualizar(this, tiempoTranscurrido);
        mapa.actualizar(b);
        if (b.getEstadoActual() == Estado.ELIMINADO.val()) {
            mapa.remover(b);
            if (Sonidos.getInstance().isPlaying(Sonidos.JUST_DIED))
                return;
            jPanelInformacion.disminuirVidasRestantes();
            jPanelInformacion.detenerCuentaRegresiva();
            if (jPanelInformacion.getVidasRestantes() < 0) {
                jPanelInformacion.setVidasRestantes(2);
                JPanelAvisos.getInstance(null).setNivel((short) 1);
                jPanelContenedor.cambiarInterfaz(Interfaz.Escenas.ESCENA_GAME_OVER);
            } else
                jPanelContenedor.cambiarInterfaz(Interfaz.Escenas.ESCENA_STAGE);
        } else if (b.isEntroALaPuerta()) {
            if (Sonidos.getInstance().isPlaying(Sonidos.LEVEL_COMPLETE))
                return;
            jPanelInformacion.detenerCuentaRegresiva();
            JPanelAvisos.getInstance(null).aumentarNivel();
            if (JPanelAvisos.getInstance(null).finDeJuego()) {

            } else
                jPanelContenedor.cambiarInterfaz(Interfaz.Escenas.ESCENA_STAGE);
        }
    }

    public Mapa getMapa() {
        return mapa;
    }

    public boolean anyEnemy(int posicionX, int posicionY) {
        return mapa.contiene(posicionX, posicionY, Objetos.getValoresEnemigos());
    }

}
