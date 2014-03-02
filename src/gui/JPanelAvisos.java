/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package GUI;

import Bomberman.Configuracion.Configuracion;
import Dependencias.Sonido;
import motor.core.Teclado;
import Fuentes.Fuentes;
import Sonidos.Sonidos;
import Utilidades.Juego.Interfaz;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Alfonso Andrés
 */
public class JPanelAvisos extends Interfaz {
    
    private static JPanelAvisos instance;
    private short nivel = 1, MAX_NIVEL = 50;
    private BufferedImage imagen;
    private Fuentes fuentes;
    private Dimension tamañoVentana;
    private Sonido sonido;
    private Teclado teclado;

    private JPanelAvisos(JPanelContenedor jPanelContenedor) {
        super(jPanelContenedor);
        initComponents();
    }

    public static JPanelAvisos getInstance(JPanelContenedor jPanelContenedor) {
        return instance == null ? (instance = new JPanelAvisos(jPanelContenedor)) : instance;
    }

    private void initComponents() {
        fuentes = new Fuentes();
        teclado = Teclado.getInstance();
        tamañoVentana = Configuracion.getInstance().tamañoVentana;
        imagen = new BufferedImage(640, 560, BufferedImage.TYPE_INT_RGB);
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
        sonido  = Sonidos.getInstance().get(Sonidos.LEVEL_START);
        sonido.play();
        new Thread() {
            @Override
            public void run() {
                JPanelJuego.getInstance(null).generarMapa();
                JPanelJuego.getInstance(null).pintarImagen();
            }
        }.start();
    }

    public void iniciarJPanelGameOver() {
        drawString("GAME OVER");
        sonido = Sonidos.getInstance().get(Sonidos.GAME_OVER);
        sonido.play();
    }

    public void iniciarBonusStage() {
        drawString("BONUS STAGE");
    }

    private void drawString(String string) {
        imagen = new BufferedImage(640, 560, BufferedImage.TYPE_INT_RGB);
        Graphics g = imagen.createGraphics();
        g.setColor(new Color(127, 127, 127));
        g.setFont(fuentes.getJoystixMonospacce(24));
        g.drawString(string, 199, 298);
        g.setColor(Color.WHITE);
        g.drawString(string, 197, 296);
    }

    @Override
    public void reiniciar() {
    
    }

    @Override
    public void pintar(Graphics g) {
        g.drawImage(imagen, 0, 0, tamañoVentana.width, tamañoVentana.height, null);
    }

    @Override
    public void actualizar(long tiempoEnMilisegundos) {
        if(sonido.isPlaying())
            System.out.println("Sonido: " + sonido.getFramePosition() + " " + sonido.getFrameLength());
        switch(jPanelContenedor.escenaSeleccionada) {
            case ESCENA_STAGE:
                if(!sonido.isPlaying())
                    jPanelContenedor.cambiarInterfaz(Escenas.ESCENA_JUEGO);
                break;
            case ESCENA_GAME_OVER:
                if(teclado.teclaPresionada()) {
                    jPanelContenedor.cambiarInterfaz(Escenas.ESCENA_MENU);
                    Sonidos.getInstance().get(Sonidos.GAME_OVER).stop();
                }
                break;
            case ESCENA_BONUS:
                break;
        }
    }

}