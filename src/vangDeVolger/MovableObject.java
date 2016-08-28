package vangDeVolger;

public abstract class MovableObject extends GameObject {
    //Gives a MovableObject its tile, and gives that tile said MovableObject

    Tile myTile;

    public MovableObject(Tile newTile) {
        myTile = newTile;
        myTile.setContent(this);
    }

    public MovableObject() {
        //this does nothinig but makes sure that
        //enemy and player can be singeltons
    }

    public void setMyTile(Tile myTile) {
        this.myTile = myTile;
    }

    @Override
    public boolean canYouMove(DirectionEnum direction) {
        return (myTile.neighbourMap.get(direction).isEmpty() || direction == DirectionEnum.NONE || myTile.neighbourMap.get(direction).getContent().canYouMove(direction));
    }

    public void move(DirectionEnum direction) {
        Tile directionTile = (myTile.neighbourMap.get(direction));

        //The null clause should never happen, since every tile where a player or box can be in has a neighbor, the boundary rocks.
        //the None clause is to break recursion of move
        if (directionTile == null || direction == DirectionEnum.NONE) {
            return;
        }

        if (directionTile.isEmpty()) {
            myTile.setContent(null);
            directionTile.setContent(this);
            myTile = directionTile;
        } else if (myTile.neighbourMap.get(direction).getContent().canYouMove(direction)) {
            ((MovableObject) (myTile.neighbourMap.get(direction).getContent())).move(direction);
            myTile.setContent(null);
            directionTile.setContent(this);
            myTile = directionTile;
        }
    }
}
