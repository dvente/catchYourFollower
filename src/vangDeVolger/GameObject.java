package vangDeVolger;


import java.awt.*;

public abstract class GameObject
{
	//There is never a GameObject that isn't either a Player, Enemy, Box, or Rock.
	//Hence, this class is abstract.

    Color color;

    public Color getColor() {
        return color;
    }

    protected abstract boolean canYouMove(DirectionEnum direction);
}