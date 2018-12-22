package engine.core.game;

import java.awt.*;

public interface Game {
    GameScene getSelectedScene();

    void paint(Graphics g);

    void setScreen(GameScene scene);

    void update(long elapsedTime);
}
