/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characters;

import dependencies.Images;
import engine.core.input.GamePad;


/**
 * @author hp
 */
public class Ovapi extends Enemy {

    public Ovapi(int x, int y) {
        super(Images.OVAPI, x, y, new GamePad());
        speed = SPEED_SLOWEST;
        smart = SMART_MID;
        score = 2000;
        wallpass = true;
        id = "o";
        initialize();
    }

}
