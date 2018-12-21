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
public class Pontan extends Enemy {

    public Pontan(int x, int y) {
        super(Images.PONTAN, x, y, new GamePad());
        speed = SPEED_MID;
        smart = SMART_HIGH;
        score = 8000;
        wallpass = true;
        id = "p";
        initialize();
    }

}