package vangDeVolger;


import java.util.*;

public class Enemy extends MovableObject
{
    //gateway function
    public void AI(){
        move(dijkstra());
    }

    //Enemy can't move boxes, hence the @Override
    @Override
    public void move(DirectionEnum direction)
    {
        Tile directionTile = (myTile.burenMap.get(direction));

        if(directionTile == null) return;

        if (directionTile.isEmpty())
        {
            myTile.setContent(null);
            directionTile.setContent(this);
            myTile = directionTile;
        }
    }

    public Enemy(Tile newTile, int length, int width)
    {
        super(newTile);
        int length1 = length;
        int width1 = width;
    }

    public DirectionEnum randomMove()
    {
        return (DirectionEnum.randomDirection());
    }

    //TODO implement dijkstra
    public DirectionEnum dijkstra(){
        Map<Tile, Tile> pre = new HashMap<Tile, Tile>();
        Map<Tile, Integer> dist = new HashMap<Tile, Integer>();
        Tile source = myTile;



        return DirectionEnum.NONE;
    }
}
