package vangDeVolger;

import java.awt.*;

class Player extends MovableObject implements Observer {

    //Player is a singelton class
    private static Player instance = null;
    private DirectionEnum nextDir;
    public Player(Tile newTile) {
        super(newTile);
        color = Color.blue;
        nextDir = DirectionEnum.NONE;
    }

    private Player() {
        color = Color.blue;
        nextDir = DirectionEnum.NONE;
    }

    //TODO maybe pass tile here?
    public static Player getInstance() {
        if (instance == null)
            instance = new Player();
        return instance;
    }

    public static void reset() {
        instance = new Player();
    }

    //we'll get updates from two different subjects so we have to differentiate between
    //when we get directional input and when we can move
    @Override
    public void update(Object changedObject) {

        //String updates don't concern the player
        if (changedObject instanceof String) {
            return;
        }

        if (changedObject != null)
            nextDir = (DirectionEnum) changedObject;

        move(nextDir);
        nextDir = DirectionEnum.NONE;
    }

}
