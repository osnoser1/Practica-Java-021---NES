/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.core.graphics;

import engine.core.AnimationControl;

/**
 * @author AlfonsoAndres
 */
public class AnimationWrapper {

    public final int row;
    public final AnimationControl animation;

    public AnimationWrapper(int row, String frames, long frameTime) {
        this.row = row;
        this.animation = new AnimationControl(frames, frameTime);
    }

}
