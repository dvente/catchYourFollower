package vangDeVolger;

import java.awt.*;

class Box extends MovableObject {
    //This class doesn't add anything, but now we can use instanceof to determine
    //if an arbitrary GameObject/MovableObject is a box, and hence determine if
    //the player can move it.

    public Box(Tile newTile) {
        super(newTile);
        color = new Color(156, 93, 82);
    }
}
