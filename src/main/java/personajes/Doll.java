/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package personajes;

import dependencias.Images;
import motor.core.input.GamePad;

/**
 *
 * @author hp
 */
public class Doll extends Enemy {
    
    public Doll(int x, int y) {
        super(Images.DOLL, x, y, new GamePad());
        speed = SPEED_SLOW;
        smart = SMART_LOW;
        wallpass = false;
        score = 400;
        id = "D";
        initialize();
    }
    
}