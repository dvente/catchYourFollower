package vangDeVolger;


import java.util.*;

public class Enemy extends MovableObject {

    //gateway function
    public void AI() {
        DirectionEnum dir = BFS();
        System.out.println(dir);
        move(dir);
    }

    //Enemy can't move boxes, hence the @Override
    @Override
    public void move(DirectionEnum direction) {
        Tile directionTile = (myTile.burenMap.get(direction));

        if (directionTile == null) return;

        if (directionTile.isEmpty()) {
            myTile.setContent(null);
            directionTile.setContent(this);
            myTile = directionTile;
        }
    }

    public Enemy(Tile newTile, int length, int width) {
        super(newTile);
        int length1 = length;
        int width1 = width;
    }

    public DirectionEnum randomMove() {
        return (DirectionEnum.randomDirection());
    }

    public DirectionEnum BFS(){

        LinkedList<Tile> queue = new LinkedList<>();
        Map<Tile, Tile> pred = new HashMap<>();
        Map<Tile, Integer> dist = new HashMap<>();

        queue.add(this.myTile);
        pred.put(this.myTile,null);
        dist.put(this.myTile,0);
        Tile current;
        Tile nextMove = null;

        while((current = queue.poll()) != null){
            if(current.getContent() instanceof Player){
                System.out.println("Found him!");
                Tile temp = pred.get(current);
                while(pred.get(temp)!= myTile)
                    temp = pred.get(temp);
                nextMove = temp;
            }
            for (Tile neighbour: current.burenMap.values()) {
                if(!pred.containsKey(neighbour) && (neighbour.isEmpty() || neighbour.getContent() instanceof Player)){
                    System.out.println("Expanding ("+Integer.toString(neighbour.getX())+","+Integer.toString(neighbour.getY())+")");
                    dist.put(neighbour,dist.get(current)+1);
                    pred.put(neighbour,current);
                    queue.add(neighbour);
                }

            }
        }

        if (myTile.burenMap.get(DirectionEnum.UP) == nextMove)
            return DirectionEnum.UP;

        else if (myTile.burenMap.get(DirectionEnum.DOWN) == nextMove)
            return DirectionEnum.DOWN;

        else if(myTile.burenMap.get(DirectionEnum.LEFT) == nextMove)
            return DirectionEnum.LEFT;

        else if(myTile.burenMap.get(DirectionEnum.RIGHT) == nextMove)
            return DirectionEnum.RIGHT;
        else
            return DirectionEnum.NONE;

    }//BFS
}

