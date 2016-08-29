package vangDeVolger;


import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Enemy extends MovableObject implements Subject, Observer {

    private List<Observer> observers;
    private int frameCount = 0;
    private boolean paused = false;

    public Enemy(Tile newTile) {
        super(newTile);
        color = Color.red;
        this.observers = new LinkedList<>();
    }

    //Enemy can't move boxes, hence the @Override
    @Override
    void move(DirectionEnum direction) {
        Tile directionTile = (getNeighbourTile(direction));

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
        pred.put(this.myTile, null);
        dist.put(this.myTile, 0);
        Tile current;
        Tile nextMove = null;

        while ((current = queue.poll()) != null) {
            if (current.getContent() instanceof Player) {
                Tile temp = pred.get(current);
                while (pred.get(temp) != myTile)
                    temp = pred.get(temp);
                nextMove = temp;
            }
            for (Tile neighbour : current.neighbourMap.values()) {
                if (!pred.containsKey(neighbour) && (neighbour.isEmpty() || neighbour.getContent() instanceof Player)) {
                    dist.put(neighbour, dist.get(current) + 1);
                    pred.put(neighbour, current);
                    queue.add(neighbour);
                }

            }
        }

        if (getNeighbourTile(DirectionEnum.UP) == nextMove)
            return DirectionEnum.UP;

        else if (getNeighbourTile(DirectionEnum.DOWN) == nextMove)
            return DirectionEnum.DOWN;

        else if (getNeighbourTile(DirectionEnum.LEFT) == nextMove)
            return DirectionEnum.LEFT;

        else if (getNeighbourTile(DirectionEnum.RIGHT) == nextMove)
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

        DirectionEnum dir;
        if (changedObject instanceof String) {
            if (changedObject == "pause") {
                paused = !paused;
            }
            return;
        }

        if (!paused) {
            frameCount++;

            boolean canStillMove = false;
            for (Tile neighbour : myTile.neighbourMap.values()) {
                if (neighbour.isEmpty() || neighbour.getContent() instanceof Player) {
                    canStillMove = true;
                    break;
                }
            }
            if (!canStillMove)
                notifyObservers("win");

            //check for target first otherwise BFS buggers up
            for (Tile neighbour : myTile.neighbourMap.values()) {
                if (neighbour.getContent() instanceof Player) {
                    notifyObservers("lose");
                }
            }

            dir = BFS();

            //enemy moves every second
            if (frameCount % VangDeVolger.FPS == 0)
                move(dir);
        }

    }

}

