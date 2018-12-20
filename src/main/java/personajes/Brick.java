/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package personajes;

import motor.core.graphics.Image;
import dependencias.Images;
import motor.core.map.Map;
import utilidades.juego.Screen;
import game.players.ladrillo.states.DeathState;
import motor.core.graphics.Sprite;
import java.awt.Graphics2D;
import java.util.HashMap;
import motor.core.graphics.AnimationWrapper;
import motor.core.graphics.spritedefaultstates.EmptyState;

/**
 *
 * @author hp
 */
public class Brick extends Sprite {

    private final int type;
    private boolean isSpecial;
    private SpecialBrick specialBrick;

    public Brick(final int x, final int y, final int type) {
        super(new Image(Images.BRICK, 6, 6, (float) 2.5), x, y);
        id = "L";
        this.type = type;
        if (type != -1)
            isSpecial = true;
        initialize();
    }

    public final void initialize() {
        super.animations = new HashMap<>() {
            {
                put(EmptyState.class, new AnimationWrapper(0, "0", 4000 / 60));
                put(DeathState.class, new AnimationWrapper(5, "0,1,2,3,4,5", 4000 / 60));
            }
        };
        setCurrentState(EmptyState::new);
    }

    public SpecialBrick getSpecialBrick() {
        return specialBrick;
    }

    @Override
    public void update(Screen screen, long elapsedTime) {
        super.update(screen, elapsedTime);
        if (specialBrick != null)
            specialBrick.update(screen, elapsedTime);
    }

    @Override
    public void draw(final Graphics2D g) {
        super.draw(g);
        if (specialBrick != null)
            specialBrick.draw(g);
    }

    public boolean isSpecial() {
        return isSpecial && !specialBrick.isRemovedStatus();
    }

    public void activateSpecialBrick() {
        if(!isSpecial) {
            return;
        }
        var map = Map.getInstance();
        specialBrick = new SpecialBrick(x, y, type);
        map.delete(this);
        id = "Q";
        map.add(specialBrick);
    }

    public void destroy() {
        setCurrentState(DeathState::new);
    }

}
