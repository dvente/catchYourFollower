package vangDeVolger;

public abstract class MovableObject extends GameObject {

    Tile myTile;

    MovableObject(Tile newTile) {
        myTile = newTile;
        myTile.setContent(this);
    }

    @Override
    public boolean canYouMove(DirectionEnum direction) {
        return (getNeighbourTile(direction).isEmpty()
                || getNeighbourTile(direction).getContent() instanceof Player
                || direction == DirectionEnum.NONE
                || getNeighbourTile(direction).getContent().canYouMove(direction));
    }

    Tile getNeighbourTile(DirectionEnum direction) {
        return myTile.neighbourMap.get(direction);
    }

    void move(DirectionEnum direction) {
        Tile directionTile = getNeighbourTile(direction);

        //The null clause should never happen, since every tile where a player or box can be in has a neighbor, the boundary rocks.
        //the None clause is to break recursion of move
        if (directionTile == null || direction == DirectionEnum.NONE)
            return;

        if (directionTile.isEmpty()) {

            myTile.setContent(null);
            directionTile.setContent(this);
            myTile = directionTile;

        } else if (getNeighbourTile(direction).getContent().canYouMove(direction)) {

            ((MovableObject) (getNeighbourTile(direction).getContent())).move(direction);
            myTile.setContent(null);
            directionTile.setContent(this);
            myTile = directionTile;
        }
    }
}
