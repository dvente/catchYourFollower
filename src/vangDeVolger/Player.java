package vangDeVolger;

import java.awt.*;

class Player extends MovableObject {
    //This class doesn't add anything, but now we can use instanceof to determine
    //if an arbitrary GameObject/MovableObject is the player.

    public Player(Tile newTile) {
        super(newTile);
        color = Color.blue;
    }


}
