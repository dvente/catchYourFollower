package vangDeVolger;

public class Rock extends GameObject {
    //This class doesn't add much, except that the canYouMove function now always returns false.
    //Also note that we haven't overloaded the constructor to take an argument of type Tile, since
    //knowing the tile of a rock is completely useless anyway ;)

    @Override
    public boolean canYouMove(DirectionEnum direction) {
        return false;
    }
}
