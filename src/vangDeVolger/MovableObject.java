package vangDeVolger;

public abstract class MovableObject extends GameObject {
    //Gives a MovableObject its tile, and gives that tile said MovableObject

    MovableObject(Tile newTile) {
        myTile = newTile;
        myTile.setContent(this);
    }

    @Override
    public boolean canYouMove(DirectionEnum direction) {
        return (myTile.burenMap.get(direction).isEmpty() || myTile.burenMap.get(direction).getContent().canYouMove(direction));
    }

    public void move(DirectionEnum direction) {
        Tile directionTile = (myTile.burenMap.get(direction));

        //This should never happen, since every tile where a player or box can be in has a neighbor, the boundary rocks.
        //But just to be sure...
        if (directionTile == null) {
            return;
        }

        if (directionTile.isEmpty()) {
            myTile.setContent(null);
            directionTile.setContent(this);
            myTile = directionTile;
        } else if (myTile.burenMap.get(direction).getContent().canYouMove(direction)) {
            ((MovableObject) (myTile.burenMap.get(direction).getContent())).move(direction);
            myTile.setContent(null);
            directionTile.setContent(this);
            myTile = directionTile;
        }
    }

    protected Tile myTile;
}
