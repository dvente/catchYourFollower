package vangDeVolger;


import java.util.*;

public class Enemy extends MovableObject {
    private Set<Tile> setNodes;
    private Set<Tile> unSetNodes;
    private Map<Tile, Tile> predecessors;
    private Map<Tile, Integer> distance;

    private Player player;

    //gateway function
    public void AI() {
        DirectionEnum dir = dijkstra();
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

    public Enemy(Tile newTile, int length, int width, Player p) {
        super(newTile);
        int length1 = length;
        int width1 = width;
        player = p;
    }

    public DirectionEnum randomMove() {
        return (DirectionEnum.randomDirection());
    }

    //TODO implement dijkstra
    //TODO change back to directionEnum
    //TODO implement how to deal with obstacles
    public DirectionEnum dijkstra() {

        setNodes = new HashSet<Tile>();
        unSetNodes = new HashSet<Tile>();
        distance = new HashMap<Tile, Integer>();
        predecessors = new HashMap<Tile, Tile>();
        Tile source = myTile;
        distance.put(source, 0);
        unSetNodes.add(source);
        while (unSetNodes.size() > 0) {
            Tile node = getMinimum(unSetNodes);
            setNodes.add(node);
            unSetNodes.remove(node);
            findMinimalDistances(node);
        }
        LinkedList<Tile> path = getPath(player.myTile);
        path.remove();
        Tile nextMove = path.remove();

        System.out.println("("+Integer.toString(nextMove.getX())+","+Integer.toString(nextMove.getY())+")");

        System.out.println();
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
    }

    private void findMinimalDistances(Tile node) {
        List<Tile> adjacentNodes = getNeighbors(node);
        for (Tile target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + 1) {
                distance.put(target, getShortestDistance(node)
                        + 1);
                predecessors.put(target, node);
                unSetNodes.add(target);
            }
        }

    }

    private List<Tile> getNeighbors(Tile node) {
        List<Tile> neighbours = new ArrayList<Tile>();
        for (Tile neighbour : node.burenMap.values()) {
            if (neighbour != null)
                neighbours.add(neighbour);
        }
        return neighbours;

    }

    private Tile getMinimum(Set<Tile> Tiles) {
        Tile minimum = null;
        for (Tile tile : Tiles) {
            if (minimum == null) {
                minimum = tile;
            } else {
                if (getShortestDistance(tile) < getShortestDistance(minimum) && tile.isEmpty()) {
                    minimum = tile;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Tile Tile) {
        return setNodes.contains(Tile);
    }

    private int getShortestDistance(Tile destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<Tile> getPath(Tile target) {
        LinkedList<Tile> path = new LinkedList<Tile>();
        Tile step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }
}
//        Set<Tile> unSetNodes = new HashSet<Tile>();
//        Set<Tile> setNodes = new HashSet<Tile>();
//        Map<Tile, Tile> pre = new HashMap<Tile, Tile>();
//        Map<Tile, Integer> dist = new HashMap<Tile, Integer>();
//        Tile source = myTile;
//
//        dist.put(source,0);
//        unSetNodes.add(source);
//
//        while (unSetNodes.size() > 0) {
//            Tile node = getMinimum(unSetNodes);
//            setNodes.add(node);
//            unSetNodes.remove(node);
//            findMinimalDistances(node);
//        }
//
//        return DirectionEnum.NONE;
//    }
//
//    private void findMinimalDistances(Tile node) {
//        List<Tile> adjacentNodes = getNeighbors(node);
//        for (Tile target : adjacentNodes) {
//            if (getShortestDistance(target) > getShortestDistance(node)
//                    + getDistance(node, target)) {
//                distance.put(target, getShortestDistance(node)
//                        + getDistance(node, target));
//                predecessors.put(target, node);
//                unSetNodes.add(target);
//            }
//        }
//
//    }

