/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core.input;

import java.util.HashMap;
import static motor.core.input.GamePad.Botones.*;

/**
 *
 * @author AlfonsoAndrés
 */
public class GamePad {

    public enum Botones {
        ARRIBA,
        ABAJO,
        IZQUIERDA,
        DERECHA,
        AIARRIBA,
        AIABAJO,
        AIIZQUIERDA,
        AIDERECHA,
        ADARRIBA,
        ADABAJO,
        ADIZQUIERDA,
        ADDERECHA,
        A,
        B,
        R1,
        R2,
        L1,
        L2,
        START,
        SELECT,
        NINGUNO
    }

    private final HashMap<Botones, EstadoBoton> botones = new HashMap<>() {
        {
            for (var value : Botones.values()) {
                put(value, new EstadoBoton(false, true));
            }
            put(NINGUNO, new EstadoBoton(true, true));
        }
    };

    public boolean isPress(Botones boton) {
        return this.botones.get(boton).isPresionado();
    }

    public void setPress(Botones boton, boolean presionado) {
        if (!this.botones.get(boton).isActivado()) {
            return;
        }
        this.botones.get(boton).setPresionado(presionado);
    }

    public boolean isActivated(Botones boton) {
        return this.botones.get(boton).isActivado();
    }

    public void setActivated(Botones boton, boolean activado) {
        this.botones.get(boton).setActivado(activado);
        if (!activado) {
            this.botones.get(boton).setPresionado(false);
        }
    }

    private class EstadoBoton {

        private boolean presionado, activado;

        public EstadoBoton(boolean presionado, boolean activado) {
            this.presionado = presionado;
            this.activado = activado;
        }

        public boolean isPresionado() {
            return presionado;
        }

        public boolean isActivado() {
            return activado;
        }

        public void setActivado(boolean activado) {
            this.activado = activado;
        }

        public void setPresionado(boolean presionado) {
            this.presionado = presionado;
        }

    }

}
