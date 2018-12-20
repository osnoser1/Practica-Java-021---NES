/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characters;

import dependencies.Images;
import engine.core.input.GamePad;


/**
 *
 * @author hp
 */
public class Oneal extends Enemy {
    
    public Oneal(int x, int y) {
        super(Images.ONEAL, x, y, new GamePad());
        speed = SPEED_SLOW;
        smart = SMART_MID;
        score = 200;
        wallpass = false;
        id = "O";
        intelligence = new Intelligence(this);
        initialize();
    }
    
}
