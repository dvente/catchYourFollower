package vangDeVolger;

public class Player extends MovableObject {
    //This class doesn't add anything, but now we can use instanceof to determine
    //if an arbitrary GameObject/MovableObject is the player.

    public Player(Tile newTile) {
        super(newTile);
    }



    //TODO Gebruik hier methoden om buren te benaderen ipv deze directe access. Iets als `myTile.getNeighbourTile(direction).isEmpty()` is hier veel beter.
    //return ( myTile.burenMap.get(direction).isEmpty() || myTile.burenMap.get(direction).getContent().canYouMove(direction) );


}
