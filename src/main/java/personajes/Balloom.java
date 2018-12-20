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
public class Balloom extends Enemy {
    
    public Balloom(int x, int y) {
        super(Images.BALLOOM, x, y, new GamePad());
        speed = SPEED_SLOWEST;
        smart = SMART_LOW;
        score = 100;
        wallpass = false;
        id = "b";
        initialize();
    }
    
}