package vangDeVolger;


public abstract class GameObject
{
	//There is never a GameObject that isn't either a Player, Enemy, Box, or Rock.
	//Hence, this class is abstract.

	public abstract boolean canYouMove(DirectionEnum direction);
}