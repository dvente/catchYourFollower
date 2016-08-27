package vangDeVolger;

import java.awt.*;
import java.util.EnumMap;

public class Tile {

    final EnumMap<DirectionEnum, Tile> neighbourMap = new EnumMap<>(DirectionEnum.class);
    private boolean isEmpty;
    private GameObject content;

    public Tile() {
        isEmpty = true;
    }

    public Color getColor() {
        if (isEmpty())
            return Color.white;
        else
            return content.getColor();
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public GameObject getContent() {
        return content;
    }

    public void setContent(GameObject newContent) {
        content = newContent;
        isEmpty = (content == null);
    }
}
