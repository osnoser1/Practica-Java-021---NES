/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Bomberman.Configuracion.Configuracion;
import Dependencias.Imagenes;
import lenguaje.utils.ManejadorDeArchivos;
import motor.core.Teclado;
import Dependencias.Ubicacion;
import Fuentes.Fuentes;
import Sonidos.Sonidos;
import core.java.JavaImage;
import motor.core.GamePad;
import motor.core.GamePad.Botones;
import motor.core.Interfaz;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import motor.utils.Graphics;
import sun.nio.cs.ext.TIS_620;

/**
 *
 * @author Alfonso Andrés
 */
public class JPanelPresentacion extends Interfaz {

    private static JPanelPresentacion instance;
    private JavaImage flecha;
    private ArrayList<Point> opciones;
    private ManejadorDeArchivos manejadorDeArchivos;
    private Fuentes fuentes;
    private final int cantidadOpciones = 2;
    private int opcionApuntando = 0;
    private int opcionSeleccionada = -1;
    private Dimension tamañoVentana;
    private Teclado teclado;
    private GamePad gamePad;
    public static final int START = 0, MAP_EDITOR = 1;

    private JPanelPresentacion(ContenedorGrafico jPanelContenedor) {
        super(jPanelContenedor);
        initComponents();
    }

    public static JPanelPresentacion getInstance(ContenedorGrafico jPanelContenedor) {
        return instance == null ? (instance = new JPanelPresentacion(jPanelContenedor)) : instance;
    }

    private void initComponents() {
        fuentes = new Fuentes();
        opciones = new ArrayList<>();
        gamePad = new GamePad();
        tamañoVentana = Configuracion.getInstance().tamañoVentana;
        teclado = Teclado.getInstance();
        agregarOpciones();
        manejadorDeArchivos = ManejadorDeArchivos.getInstance();
        crearFlecha();
        iniciar();
    }

    public void iniciar() {
        imagen = new JavaImage(640, 560, BufferedImage.TYPE_INT_RGB);
        Graphics g = imagen.getGraphics();
        g.drawImage(Imagenes.LOGO, 40, 20, 568, 347);
        drawStrings(g);
        pintarFlecha(g);
    }

    private void agregarOpciones() {
        for (int i = 0; i < cantidadOpciones; i++) {
            opciones.add(getPoint(i));
        }
    }

    private Point getPoint(int i) {
        if (i == 0) {
            return new Point(259, 415);
        }
        return new Point(259, 455);
    }

    private void drawStrings(Graphics g) {
        for (int i = 0; i < cantidadOpciones; i++) {
            drawString(g, getString(i), opciones.get(i));
        }
    }

    private void drawString(Graphics g, String string, Point point) {
        g.setColor(new Color(127, 127, 127));
        g.setFont(fuentes.getJoystixMonospacce(25));
        g.drawString(string, point.x + 1, point.y + 1);
        g.setFont(fuentes.getJoystixMonospacce(24));
        g.setColor(Color.WHITE);
        g.drawString(string, point.x, point.y);
    }

    private String getString(int i) {
        if (i == 0) {
            return "START";
        } else if (i == 1) {
            return "MAP EDITOR";
        }
        return "TOP";
    }

    public void siguienteOpcion() {
        Graphics g = imagen.getGraphics();
        g.setColor(Color.BLACK);
        Point point = opciones.get(opcionApuntando);
        g.fillRect(point.x - 55, point.y - 17, 20, 20);
        opcionApuntando = opcionApuntando == cantidadOpciones - 1 ? 0 : ++opcionApuntando;
        pintarFlecha(g);
    }

    private void crearFlecha() {
        flecha = JavaImage.get(manejadorDeArchivos.loadBufferedImageJAR(new Ubicacion().APUNTADOR));
    }

    private void pintarFlecha(Graphics g) {
        Point point = opciones.get(opcionApuntando);
        g.drawImage(flecha, point.x - 55, point.y - 17, 20, 20);
    }

    public int getOpcionSeleccionada() {
        return opcionSeleccionada;
    }

    public void setOpcionSeleccionada() {
        opcionSeleccionada = opcionApuntando;
    }

    @Override
    public void reiniciar() {
        opcionSeleccionada = 0;
        iniciar();
    }

    @Override
    public void pintar(Graphics g) {
        g.drawImage(imagen, 0, 0, size.width, tamañoVentana.height);
    }

    @Override
    public void actualizar(long tiempoEnMilisegundos) {
        if (teclado.teclaPresionada(gamePad.get(Botones.SELECT))) {
            siguienteOpcion();
        }
        if (teclado.teclaPresionada(gamePad.get(Botones.START))) {
            setOpcionSeleccionada();
            Sonidos.getInstance().get(Sonidos.TITLE_SCREEN).stop();
            switch (opcionSeleccionada) {
                case START:
                    jPanelContenedor.cambiarInterfaz(Escenas.ESCENA_STAGE);
                    break;
                case MAP_EDITOR:
                    jPanelContenedor.cambiarInterfaz(Escenas.ESCENA_EDITOR);
                    break;
            }
        }
    }

    @Override
    public void setSize(Dimension size) {
        this.size = size;
    }
}
