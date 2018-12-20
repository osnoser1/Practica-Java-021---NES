package characters;

import game.players.bomberman.states.*;
import gui.GameScreen;
import engine.core.graphics.Image;
import engine.core.input.GamePad;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import engine.core.graphics.AnimationWrapper;

public class Enemy extends Character {

    int score;

    public Enemy(final java.awt.Image image, final int x, final int y, final GamePad gamePad) {
        super(new Image(image, 6, 5, (float) 2.5), x, y);
        this.gamePad = gamePad;
        initialize();
    }

    final void initialize() {
        animations = new HashMap<>() {
            {
                put(InitialState.class, new AnimationWrapper(0, "0", 4000 / 60));
                put(UpState.class, new AnimationWrapper(1, "0,1,2", 4000 / 60));
                put(DownState.class, new AnimationWrapper(2, "0,1,2", 4000 / 60));
                put(RightState.class, new AnimationWrapper(3, "0,1,2", 4000 / 60));
                put(LeftState.class, new AnimationWrapper(4, "0,1,2", 4000 / 60));
                put(DeathState.class, new AnimationWrapper(5, "0,1,2,3,4", 500));
            }
        };
        setCurrentState(LeftState::new);
        intelligence = new Intelligence(this);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    @Override
    public void draw(final Graphics2D g) {
        super.draw(g);
        if (currentState instanceof DeathState) {
            g.setColor(Color.white);
            g.drawString("" + score, getX() + image.getWidth() / 5, getCenter().y);
        }
    }

    public void death(GameScreen gameScreen) {
        setCurrentState(DeathState::new);
        gameScreen.getMap().delete(this);
        stopIntelligence();
    }

}
