package vangDeVolger;

import java.util.EnumMap;

public class Tile {
    //TODO revert xy back to private
    public final int x, y; //set in constructor and never used after that
    private boolean isEmpty;
    private GameObject content;

    EnumMap<DirectionEnum, Tile> burenMap = new EnumMap<DirectionEnum, Tile>(DirectionEnum.class);

    public Tile(int newX, int newY) {
        x = newX;
        y = newY;
        isEmpty = true;
    }

    //The rest is a bunch of accessor and mutator functions
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setContent(GameObject newContent) {
        content = newContent;
        isEmpty = (content == null);
    }

    public GameObject getContent() {
        return content;
    }
}
