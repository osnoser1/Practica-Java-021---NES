/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core;

import java.util.HashMap;
import motor.core.GamePad.Botones;
import lenguaje.utils.Pareja;
import motor.core.Colisiones.Colision;
import motor.core.EstadosSprite.Estado;

/**
 *
 * @author Alfonso
 */
public class Automata {

    private final HashMap<Integer, Relacion> hashMap;

    public Automata(HashMap<Integer, Relacion> hashMap) {
        this.hashMap = hashMap;
    }

    public HashMap<Integer, Relacion> getHashMap() {
        return hashMap;
    }

    public void comprobarAutomata(Personaje p) {
        final Triple[] triples = hashMap.get(p.getEstadoActual().ordinal()).getTriples();
        final Pareja<Botones, Boolean>[] estadoTeclas = hashMap.get(p.getEstadoActual().ordinal()).getEstadoTeclas();
        setEstadoTeclas(p, estadoTeclas);
        for (final Automata.Triple triple : triples) {
            if(Colisiones.colision(triple.getColision(), p) && press(p, triple.getBotones())) {
                p.setEstadoActual(triple.getEstado());
                break;
            }
        }
    }

    private boolean press(Personaje p, Pareja<Botones, Boolean>[] botones) {
        for (final Pareja<Botones, Boolean> pareja : botones) {
            if(!(pareja.getSegundo() ? p.isPress(pareja.getPrimero()) : !p.isPress(pareja.getPrimero())))
                return false;
        }
        return true;
    }

    private void setEstadoTeclas(Personaje p, Pareja<Botones, Boolean>[] estadoTeclas) {
        if(estadoTeclas == null)
            return;
        for (final Pareja<Botones, Boolean> pareja : estadoTeclas) {
            p.setEstadoTecla(pareja.getPrimero(), pareja.getSegundo());
        }
    }

    public static class T<A, B, C> {

        public static Triple de(Pareja<Colision, Boolean> colision, Pareja<Botones, Boolean>[] botones, Estado estado) {
            return new Triple(colision, botones, estado);
        }

    }

    public static class L {

        public static Pareja<Botones, Boolean>[] de(Pareja<Botones, Boolean>... parejas) {
            return parejas;
        }

        public static Pareja<Botones, Boolean>[] de(Botones... parejas) {
            final Pareja<Botones, Boolean>[] parejas1 = new Pareja[parejas.length];
            int i = 0;
            for (final Botones botones : parejas) {
                parejas1[i++] = P.de(botones);
            }
            return parejas1;
        }

    }

    public static class P<A, B> {

        public static Pareja<Botones, Boolean> de(Botones b) {
            return Pareja.de(b, true);
        }

        public static Pareja<Colision, Boolean> de(Colision c) {
            return Pareja.de(c, true);
        }

        public static<A, B> Pareja<A, B> de(A primero, B segundo) {
            return Pareja.de(primero, segundo);
        }

    }

    public static class Relacion {

        private final lenguaje.utils.Pareja<Triple[], Pareja<Botones, Boolean>[]> pareja;

        public Relacion() {
            pareja = new lenguaje.utils.Pareja<>(null, null);
        }

        public Relacion(Triple[] triples, Pareja<Botones, Boolean>[] list) {
            pareja = new lenguaje.utils.Pareja<>(triples, list);
        }

        public Triple[] getTriples() {
            return pareja.getPrimero();
        }

        public Pareja<Botones, Boolean>[] getEstadoTeclas() {
            return pareja.getSegundo();
        }

    }

    public static class Triple {

        private final lenguaje.utils.Triple<Pareja<Colision, Boolean>, Pareja<Botones, Boolean>[], Estado> triple;

        public Triple() {
            triple = new lenguaje.utils.Triple<>(null, null, null);
        }

        public Triple(Pareja<Colision, Boolean> colision, Pareja<Botones, Boolean>[] botones, Estado estado) {
            triple = new lenguaje.utils.Triple<>(colision, botones, estado);
        }

        public Pareja<Colision, Boolean> getColision() {
            return triple.getPrimero();
        }

        public Pareja<Botones, Boolean>[] getBotones() {
            return triple.getSegundo();
        }
        public Estado getEstado() {
            return triple.getTercero();
        }

        public void setEstado(Estado estado) {
            triple.setTercero(estado);
        }

    }

}
