/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package gui;

import Bomberman.Configuracion.Configuracion;
import Dependencias.Sonido;
import motor.core.input.Teclado;
import Fuentes.Fuentes;
import Dependencias.Sonidos;
import Utilidades.Juego.Interfaz;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import lenguaje.utils.ImageUtilities;

/**
 *
 * @author Alfonso Andrés
 */
public class JPanelAvisos extends Interfaz {
    
    private static JPanelAvisos instance;
    private short nivel = 1, MAX_NIVEL = 50;
    private Image imagen;
    private Dimension tamañoVentana;
    private Sonido sonido;
    private Teclado teclado;
    private Font f1;
    private Color c;

    private JPanelAvisos(final JPanelContenedor jPanelContenedor) {
        super(jPanelContenedor);
        init();
    }

    public static JPanelAvisos getInstance(final JPanelContenedor jPanelContenedor) {
        return instance == null ? (instance = new JPanelAvisos(jPanelContenedor)) : instance;
    }

    private void init() {
        imagen = ImageUtilities.createCompatibleVolatileImage(640, 560, Transparency.OPAQUE);
        c = new Color(127, 127, 127);
        f1 = Fuentes.getInstance().getJoystixMonospacce(25);
        teclado = Teclado.getInstance();
        tamañoVentana = Configuracion.getInstance().getTamañoVentana();
    }

    public void setMAX_NIVEL(short MAX_NIVEL) {
        this.MAX_NIVEL = MAX_NIVEL > 50 ? 50 : MAX_NIVEL < 1 ? 1 : MAX_NIVEL;
    }

    public void setNivel(short nivel) {
        this.nivel = nivel > 50 ? 50 : nivel < 1 ? 1 : nivel;
    }

    public void aumentarNivel() {
        if(nivel != MAX_NIVEL)
            nivel++;
    }

    public boolean finDeJuego() {
        return nivel == MAX_NIVEL;
    }

    public short getNivel() {
        return nivel;
    }

    public short getMAX_NIVEL() {
        return MAX_NIVEL;
    }

    public void iniciarJPanelStage() {
        drawString("STAGE  " + this.nivel);
        sonido = Sonidos.getInstance().play(Sonidos.LEVEL_START);
    }

    public void iniciarJPanelGameOver() {
        drawString("GAME OVER");
        sonido = Sonidos.getInstance().play(Sonidos.GAME_OVER);
    }

    public void iniciarBonusStage() {
        drawString("BONUS STAGE");
    }

    private void drawString(String string) {
        var g2D = (Graphics2D) imagen.getGraphics();
        g2D.setBackground(Color.BLACK);
        g2D.fillRect(0, 0, 640, 560);
        g2D.setColor(c);
        g2D.setFont(f1);
        g2D.drawString(string, 199, 298);
        g2D.setColor(Color.WHITE);
        g2D.drawString(string, 197, 296);
        g2D.dispose();
    }

    @Override
    public void reiniciar() {
    
    }

    @Override
    public void pintar(Graphics2D g) {
        g.drawImage(imagen, 0, 0, null);
    }

    @Override
    public void actualizar(long tiempoEnMilisegundos) {
        if (sonido != null && sonido.isPlaying())
            System.out.println("Sonido: " + sonido.getFramePosition() + " " + sonido.getFrameLength());
        switch(jPanelContenedor.escenaSeleccionada) {
            case ESCENA_STAGE:
                if (sonido == null || !sonido.isPlaying())
                    jPanelContenedor.cambiarInterfaz(Escenas.ESCENA_JUEGO);
                break;
            case ESCENA_GAME_OVER:
                if(teclado.teclaPresionada()) {
                    jPanelContenedor.cambiarInterfaz(Escenas.ESCENA_MENU);
                    Sonidos.getInstance().detener(Sonidos.GAME_OVER);
                }
                break;
            case ESCENA_BONUS:
                break;
        }
    }

    @Override
    public void setSIZE(Dimension d) {

    }

}
