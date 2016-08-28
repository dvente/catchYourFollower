package vangDeVolger;


import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Enemy extends MovableObject implements Subject, Observer {
    //Enemy is a singelton class
    private static Enemy instance = null;
    private List<Observer> observers;
    private boolean canStillMove = false;
    private int frameCount = 0;

    private Enemy(Tile newTile) {
        super(newTile);
        color = Color.red;
        this.observers = new LinkedList<>();
    }


    private Enemy() {
        color = Color.red;
        this.observers = new LinkedList<>();
    }

    public static Enemy getInstance() {
        if (instance == null)
            instance = new Enemy();
        return instance;
    }

    public static void reset() {
        instance = new Enemy();
    }

    //Enemy can't move boxes, hence the @Override
    @Override
    public void move(DirectionEnum direction) {
        Tile directionTile = (myTile.neighbourMap.get(direction));

        if (directionTile == null) return;

        if (directionTile.isEmpty()) {
            myTile.setContent(null);
            directionTile.setContent(this);
            myTile = directionTile;
        }
    }

    private DirectionEnum randomMove() {
        return (DirectionEnum.randomDirection());
    }

    private DirectionEnum BFS() {

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
                Tile temp = pred.get(current);
                while(pred.get(temp)!= myTile)
                    temp = pred.get(temp);
                nextMove = temp;
            }
            for (Tile neighbour : current.neighbourMap.values()) {
                if(!pred.containsKey(neighbour) && (neighbour.isEmpty() || neighbour.getContent() instanceof Player)){
                    dist.put(neighbour,dist.get(current)+1);
                    pred.put(neighbour,current);
                    queue.add(neighbour);
                }

            }
        }

        if (myTile.neighbourMap.get(DirectionEnum.UP) == nextMove)
            return DirectionEnum.UP;

        else if (myTile.neighbourMap.get(DirectionEnum.DOWN) == nextMove)
            return DirectionEnum.DOWN;

        else if (myTile.neighbourMap.get(DirectionEnum.LEFT) == nextMove)
            return DirectionEnum.LEFT;

        else if (myTile.neighbourMap.get(DirectionEnum.RIGHT) == nextMove)
            return DirectionEnum.RIGHT;
        else {
            return randomMove();
        }


    }//BFS

    @Override
    public void register(vangDeVolger.Observer obj) {

        if (obj == null)
            return;

        if (!observers.contains(obj))
            observers.add(obj);
    }

    @Override
    public void unregister(vangDeVolger.Observer obj) {
        observers.remove(obj);
    }

    @Override
    public void notifyObservers(Object changedObject) {
        for (Observer obj : observers)
            obj.update(changedObject);
    }

    @Override
    public void update(Object changedObject) {
        frameCount++;

        canStillMove = false;
        for (Tile neighbour : myTile.neighbourMap.values()) {
            if (neighbour.isEmpty()) {
                canStillMove = true;
                break;
            }
        }
        if (!canStillMove)
            notifyObservers("win");

        DirectionEnum dir = BFS();

        if (myTile.neighbourMap.get(dir).getContent() != null)
            if (myTile.neighbourMap.get(dir).getContent() instanceof Player)
                notifyObservers("lose");

        if (frameCount % 60 * 5 == 0)
            move(dir);

    }

}

