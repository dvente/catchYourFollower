package vangDeVolger;

import java.awt.*;

class Player extends MovableObject implements Observer {

    private boolean paused = false;

    //this makes sure the player moves on a frame and doesn't get desynced form the enemy
    private DirectionEnum nextDir;

    public Player(Tile newTile) {
        super(newTile);
        color = Color.blue;
        nextDir = DirectionEnum.NONE;
    }

    //we'll get updates from two different subjects so we have to differentiate between
    //when we get directional input and when we can move
    @Override
    public void update(Object changedObject) {

        if (changedObject instanceof String) {
            if (changedObject == "pause") {
                paused = !paused;
                nextDir = DirectionEnum.NONE;
            }
            return;
        }

        if (changedObject != null)
            nextDir = (DirectionEnum) changedObject;

        if (!paused) {
            move(nextDir);
            nextDir = DirectionEnum.NONE;

        }
    }
}
