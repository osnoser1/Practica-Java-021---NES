/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characters;

import dependencies.Sounds;
import engine.core.graphics.Image;
import dependencies.Images;
import gui.GameScreen;
import utils.game.Screen;
import game.players.bomb.states.InitialState;
import game.players.bomb.states.DeathState;
import java.awt.Graphics2D;
import java.util.HashMap;
import javax.swing.Timer;
import engine.core.graphics.AnimationWrapper;
import engine.core.graphics.spritedefaultstates.NullState;

public class Bomb extends Character {

    private boolean detonate;
    private Fire fire;
    private Timer timer;
    private final Bomberman bomberman;

    public Bomb(final int x, final int y, final Bomberman player) {
        super(new Image(Images.BOMB, 1, 3, (float) 2.5), x, y);
        bomberman = player;
        id = "X";
        initialize();
        timer = player.getDETONATOR() ? null : new Timer(3200, e -> {
            detonate = true;
            timer.stop();
        });
        if (timer != null)
            timer.start();
    }

    public final void initialize() {
        super.animations = new HashMap<>() {
            {
                put(InitialState.class, new AnimationWrapper(0, "0,1,2", 400));
            }
        };
        setCurrentState(InitialState::new);
    }

    public void detonate(final Screen screen) {
        if(!isActive()) {
            return;
        }
        setActive(false);
        setCurrentState(DeathState::new);
        fire = new Fire(x, y, bomberman.getFLAMES(), (GameScreen) screen);
        var s = Sounds.getInstance().getNewSound(Sounds.EXPLOSION_1);
        if (s != null)
            s.play();
    }

    @Override
    public void update(Screen screen, long elapsedTime) {
        super.update(screen, elapsedTime);
        if (fire != null)
            fire.update(screen, elapsedTime);
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        if (fire != null)
            fire.draw(g);
    }
    
    public boolean isExplosionEnded() {
        return fire != null && fire.getCurrentState() instanceof NullState;
    }
    
    public boolean isTimeOver() {
        return detonate;
    }

    boolean hasDetonated() {
        return !isActive();
    }
    
}
