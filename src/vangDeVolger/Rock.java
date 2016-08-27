package vangDeVolger;

import java.awt.*;

public class Rock extends GameObject {
    //This class doesn't add much, except that the canYouMove function now always returns false.

    //set the correct colour
    public Rock() {
        color = Color.black;
    }


    @Override
    public boolean canYouMove(DirectionEnum direction) {
        return false;
    }
}
