/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Bomberman.Configuracion.Configuracion;
import lenguaje.utils.ImageUtilities;
import Dependencias.Imagenes;
import motor.core.input.Teclado;
import Fuentes.Fuentes;
import Dependencias.Sonidos;
import motor.core.input.GamePad;
import motor.core.input.GamePad.Botones;
import Utilidades.Juego.Interfaz;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Transparency;
import java.util.ArrayList;

/**
 *
 * @author Alfonso Andrés
 */
public class JPanelPresentacion extends Interfaz {
    
    private static JPanelPresentacion instance;
    private Image imagen, flecha;
    private ArrayList<Point> opciones;
    private final int cantidadOpciones = 2;
    private int opcionApuntando = 0;
    private int opcionSeleccionada = -1;
    private Dimension tamañoVentana;
    private Teclado teclado;
    private GamePad gamePad;
    public static final int START = 0, MAP_EDITOR = 1;
    private Font f1, f2;
    
    private JPanelPresentacion(JPanelContenedor jPanelContenedor) {
        super(jPanelContenedor);
        init();
    }
    
    public static JPanelPresentacion getInstance(JPanelContenedor jPanelContenedor) {
        return instance == null ? (instance = new JPanelPresentacion(jPanelContenedor)) : instance;
    }

    private void init() {
        f1 = Fuentes.getInstance().getJoystixMonospacce(25);
        f2 = Fuentes.getInstance().getJoystixMonospacce(24);
        imagen = ImageUtilities.createCompatibleVolatileImage(640, 560, Transparency.OPAQUE);
        flecha = Imagenes.APUNTADOR;
        opciones = new ArrayList<>();
        gamePad = new GamePad();
        tamañoVentana = Configuracion.getInstance().getTamañoVentana();
        teclado = Teclado.getInstance();
        agregarOpciones();
    }

    private void iniciar() {
        Graphics2D g2d = (Graphics2D) imagen.getGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 640, 560);
        g2d.drawImage(Imagenes.LOGO, 40, 20, 568, 347, null);
        drawStrings(g2d);
        pintarFlecha(g2d);
        g2d.dispose();
    }

    private void agregarOpciones() {
        for(int i = 0; i < cantidadOpciones; i++){
            opciones.add(getPoint(i));
        }
    }

    private Point getPoint(int i) {
        if(i == 0) return new Point(259,415);
        return new Point(259,455);
    }

    private void drawStrings(Graphics2D g2d) {
        for(int i = 0; i < cantidadOpciones; i++){
            drawString(g2d, getString(i), opciones.get(i));
        }
    }
    
    private void drawString(Graphics2D g, String string, Point point) {
        g.setColor(new Color(127, 127, 127));
        g.setFont(f1);
        g.drawString(string, point.x + 1, point.y + 1);
        g.setFont(f2);
        g.setColor(Color.WHITE);
        g.drawString(string, point.x, point.y);
    }
    
    private String getString(int i){
        if(i == 0) return "START";
        else if(i == 1) return "MAP EDITOR";
        return "TOP";
    }
    
    public void siguienteOpcion() {
        System.out.println(imagen.getCapabilities(null).isAccelerated());
        Graphics2D g2d = (Graphics2D) imagen.getGraphics();
        g2d.setColor(Color.BLACK);
        Point point = opciones.get(opcionApuntando);
        g2d.fillRect(point.x - 55, point.y - 17, 20, 20);
        opcionApuntando = opcionApuntando == cantidadOpciones - 1 ? 0 : ++opcionApuntando;
        pintarFlecha(g2d);
        g2d.dispose();
        System.out.println(imagen.getCapabilities(null).isAccelerated());
    }

    private void pintarFlecha(Graphics2D g2d) {
        Point point = opciones.get(opcionApuntando);
        g2d.drawImage(flecha, point.x - 55, point.y - 17, 20, 20, null);
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
    public void pintar(final Graphics2D g2d) {
        g2d.drawImage(imagen, 0, 0, null);
    }

    @Override
    public void actualizar(final long tiempoEnMilisegundos) {
        if(teclado.teclaPresionada(gamePad.get(Botones.SELECT)))
            siguienteOpcion();
        else if (teclado.teclaPresionada(gamePad.get(Botones.START))) {
            setOpcionSeleccionada();
            Sonidos.getInstance().detener(Sonidos.TITLE_SCREEN);
            switch(opcionSeleccionada) {
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
    public void setSIZE(Dimension d) {

    }

}
