/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static motor.core.GamePad.Botones.*;

/**
 *
 * @author Alfonso
 */
public class GamePad implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Botones {
        ARRIBA,
        ABAJO,
        IZQUIERDA,
        DERECHA,
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

    @SuppressWarnings("serial")
	private final HashMap<Botones, EstadoBoton> boton = new HashMap<Botones, EstadoBoton>(){{
                put(ABAJO, new EstadoBoton(false, true));
                put(ARRIBA, new EstadoBoton(false, true));
                put(DERECHA, new EstadoBoton(false, true));
                put(IZQUIERDA, new EstadoBoton(false, true));
                put(A, new EstadoBoton(false, true));
                put(B, new EstadoBoton(false, true));
                put(SELECT, new EstadoBoton(false, true));
                put(START, new EstadoBoton(false, true));
                put(R1, new EstadoBoton(false, true));
                put(R2, new EstadoBoton(false, true));
                put(L1, new EstadoBoton(false, true));
                put(L2, new EstadoBoton(false, true));
                put(NINGUNO, new EstadoBoton(true, true));
            }};

    public boolean isPress(Botones boton){
        return this.boton.get(boton).isPresionado();
    }

    public synchronized void setPress(Botones boton, boolean presionado) {
        System.out.println(boton.name() + ", " + this.boton.get(boton));
        if(!this.boton.get(boton).isActivado()) return;
        if(boton == DERECHA || boton == IZQUIERDA)
            this.boton.get(boton == DERECHA ? boton : IZQUIERDA).setPresionado(!presionado);
        this.boton.get(boton).setPresionado(presionado);
    }

    public boolean isActived(Botones boton) {
        return this.boton.get(boton).isActivado();
    }

    public void setActivated(Botones boton, boolean activado) {
        this.boton.get(boton).setActivado(activado);
        if(!activado)
            this.boton.get(boton).setPresionado(false);
    }

    public void set(GamePad pad) {
        for (final Map.Entry<Botones, EstadoBoton> botones : boton.entrySet()) {
            botones.getValue().setPresionado(pad.boton.get(botones.getKey()).isPresionado());
        }
    }

    private class EstadoBoton implements Serializable {

        private static final long serialVersionUID = 1L;

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
