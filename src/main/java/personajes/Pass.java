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
public class Pass extends Enemy {
    
    public Pass(int x, int y) {
        super(Images.PASS, x, y, new GamePad());
        speed = SPEED_MID;
        smart = SMART_HIGH;
        score = 4000;
        wallpass = false;
        id = "P";
        intelligence = new Intelligence(this);
        initialize();
    }
    
}