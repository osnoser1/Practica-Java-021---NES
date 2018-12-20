/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package personajes;

import dependencias.Images;
import motor.core.graphics.Image;
import motor.core.graphics.Sprite;

/**
 * 
 * @author AlfonsoAndres
 */
public class SteelBlock extends Sprite {

    public SteelBlock(int x, int y) {
        super(new Image(Images.STEEL_BLOCK, 1, 1, 1), x, y);
    }

}
