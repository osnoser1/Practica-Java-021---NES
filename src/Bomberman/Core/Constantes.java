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
import motor.core.Personaje;
import Personajes.Pontan;
import motor.core.Sprite;

/**
 *
 * @author AlfonsoAndrés
 */
public interface Constantes {
    
    public enum Objetos{
        LADRILLO("L"),
        PISO("V"),
        BOMBERMAN("B"),
        BALLOM("b"),
        ONEAL("O"),
        DOLL("D"),
        MINVO("M"),
        KONDORIA("K"),
        OVAPI("o"),
        PASS("P"),
        PONTAN("p"),
        FIRE;
        
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
        
        public static Sprite getInstance(String identificador){
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
