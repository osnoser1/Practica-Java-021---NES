/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package motor.core.map;

import motor.core.graphics.Sprite;

/**
 * @author AlfonsoAndres
 */
class SpritePosition {

    private final Sprite sprite;
    private Position currentPosition;
    private Position previousPosition;

    public SpritePosition(Sprite sprite) {
        var center = sprite.getCenter();
        this.sprite = sprite;
        this.currentPosition = new Position(center.y / sprite.getHeight(), center.x / sprite.getWidth());
        this.previousPosition = (Position) currentPosition.clone();
    }

    public boolean update() {
        var center = sprite.getCenter();
        var newCurrentPosition = new Position(center.y / sprite.getHeight(), center.x / sprite.getWidth());
        if (currentPosition.equals(newCurrentPosition)) {
            return false;
        }
        previousPosition = currentPosition;
        currentPosition = newCurrentPosition;
        return true;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public Position getPreviousPosition() {
        return previousPosition;
    }

}
