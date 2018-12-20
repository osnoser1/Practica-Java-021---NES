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
public class Minvo extends Enemy {

    public Minvo(int x, int y) {
        super(Images.MINVO, x, y, new GamePad());
        speed = SPEED_MID;
        smart = SMART_MID;
        score = 800;
        wallpass = false;
        id = "M";
        initialize();
    }
    
}