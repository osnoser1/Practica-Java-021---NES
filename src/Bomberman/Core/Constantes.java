/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bomberman.Core;

import Personajes.Balloom;
import Personajes.Bomberman;
import Personajes.Doll;
import Personajes.Kondoria;
import Personajes.Minvo;
import Personajes.Oneal;
import Personajes.Ovapi;
import Personajes.Pass;
import Personajes.Personaje;
import Personajes.Pontan;

/**
 *
 * @author AlfonsoAndrés
 */
public interface Constantes {
    
    public enum Objetos{
        BOMBERMAN("B"),
        BALLOM("b"),
        DOLL("D"),
        FIRE,
        KONDORIA("K"),
        LADRILLO("L"),
        MINVO("M"),
        ONEAL("O"),
        OVAPI("o"),
        PASS("P"),
        PONTAN("p"),
        PISO("V");
        
        private String value;

        private Objetos(String value) {
            this.value = value;
        }

        private Objetos() { }

        public String getValue() {
            return value;
        }
        
        public static Objetos[] getEnemigos(){
            return new Objetos[]{ BALLOM, DOLL, KONDORIA, MINVO, ONEAL, OVAPI, PASS, PONTAN };
        }
        
        public static Personaje getInstance(String identificador){
            if(identificador.equals(BOMBERMAN.value))
                return new Bomberman(0, 0);
            if(identificador.equals(BALLOM.value))
                return new Balloom(0, 0);
            if(identificador.equals(DOLL.value))
                return new Doll(0, 0);
            if(identificador.equals(KONDORIA.value))
                return new Kondoria(0, 0);
            if(identificador.equals(MINVO.value))
                return new Minvo(0, 0);
            if(identificador.equals(ONEAL.value))
                return new Oneal(0, 0);
            if(identificador.equals(OVAPI.value))
                return new Ovapi(0, 0);
            if(identificador.equals(PASS.value))
                return new Pass(0, 0);
            if(identificador.equals(PONTAN.value))
                return new Pontan(0, 0);
            return null;
        }
        
    }
    
}
