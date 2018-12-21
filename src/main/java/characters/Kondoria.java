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
public class Kondoria extends Enemy {

    public Kondoria(int x, int y) {
        super(Images.KONDORIA, x, y, new GamePad());
        speed = SPEED_SLOWEST;
        smart = SMART_HIGH;
        wallpass = true;
        score = 1000;
        id = "K";
        initialize();
    }

}