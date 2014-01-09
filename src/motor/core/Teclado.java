/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import lenguaje.utils.Pareja;
import motor.core.GamePad.Botones;

/**
 *
 * @author AlfonsoAndrés
 */
@SuppressWarnings("serial")
public class Teclado {

    public enum Control {

        UNO, DOS, TRES, CUATRO;

        private HashMap<Integer, Botones> configuracion;

        private Control(HashMap<Integer, Botones> configuracion) {
            this.configuracion = configuracion;
        }

        private Control() {
        }

        public void setConfiguracion(HashMap<Integer, Botones> configuracion) {
            this.configuracion = configuracion;
        }

        public HashMap<Integer, Botones> getConfiguracion() {
            return configuracion;
        }

    }

    public enum EstadoTecla {

        PRESIONADA,
        LIBERADA,
    }

    private static Teclado instance;
    private final HashMap<Integer, EstadoTecla> teclas;
    private final ArrayList<Pareja<GamePad, Control>> gamePad3s;

    static {
        Control.UNO.setConfiguracion(new HashMap<Integer, Botones>() {
            {
                put(KeyEvent.VK_M, Botones.ABAJO);
                put(KeyEvent.VK_J, Botones.ARRIBA);
                put(KeyEvent.VK_COMMA, Botones.DERECHA);
                put(KeyEvent.VK_N, Botones.IZQUIERDA);
                put(KeyEvent.VK_X, Botones.A);
                put(KeyEvent.VK_Z, Botones.B);
                put(KeyEvent.VK_SHIFT, Botones.SELECT);
                put(KeyEvent.VK_ENTER, Botones.START);
                put(KeyEvent.VK_A, Botones.R1);
                put(KeyEvent.VK_D, Botones.R2);
                put(KeyEvent.VK_S, Botones.L1);
                put(KeyEvent.VK_F, Botones.L2);
            }
        });
        Control.DOS.setConfiguracion(new HashMap<Integer, Botones>() {
            {
                put(KeyEvent.VK_DOWN, Botones.ABAJO);
                put(KeyEvent.VK_UP, Botones.ARRIBA);
                put(KeyEvent.VK_RIGHT, Botones.DERECHA);
                put(KeyEvent.VK_LEFT, Botones.IZQUIERDA);
                put(KeyEvent.VK_2, Botones.A);
                put(KeyEvent.VK_1, Botones.B);
                put(KeyEvent.VK_SHIFT, Botones.SELECT);
                put(KeyEvent.VK_ENTER, Botones.START);
            }
        });
    }

    private Teclado() {
        teclas = new HashMap<>();
        gamePad3s = new ArrayList<>();
    }

    public static Teclado getInstance() {
        return instance == null ? (instance = new Teclado()) : instance;
    }

    public void presionarTecla(int keyCode) {
        teclas.put(keyCode, EstadoTecla.PRESIONADA);
        for (final Pareja<GamePad, Control> pareja : gamePad3s) {
            final HashMap<Integer, Botones> hm = pareja.getSegundo().getConfiguracion();
            if (!hm.containsKey(keyCode)) {
                continue;
            }
            pareja.getPrimero().setPress(hm.get(keyCode), true);
        }
    }

    public void liberarTecla(int keyCode) {
        teclas.put(keyCode, EstadoTecla.LIBERADA);
        for (final Pareja<GamePad, Control> pareja : gamePad3s) {
            final HashMap<Integer, Botones> hm = pareja.getSegundo().getConfiguracion();
            if (!hm.containsKey(keyCode)) {
                continue;
            }
            pareja.getPrimero().setPress(hm.get(keyCode), false);
        }
    }

    public boolean teclaPresionada(int keyCode) {
        return teclas.containsKey(keyCode) && teclas.get(keyCode) == EstadoTecla.PRESIONADA;
    }

    public boolean teclaPresionada() {
        for (final EstadoTecla estado : teclas.values()) {
            if (estado == EstadoTecla.PRESIONADA) {
                return true;
            }
        }
        return false;
    }

    public boolean teclaLiberada(int keyCode) {
        return !teclaPresionada(keyCode);
    }

    public void asociar(GamePad gp, Control control) {
        gamePad3s.add(new Pareja<>(gp, control));
    }

    public void desasociar(GamePad gp) {
        gamePad3s.remove(gp);
    }

}
