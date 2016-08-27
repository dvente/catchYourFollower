package vangDeVolger;


class Grid
{
    public final Tile[][] tiles;

    //Assume both are >= 2
    public final int length;
    public final int width;

    //Sets the dimensions and creates the tiles
    Grid(int newLength, int newWidth, int boxPercentage, int rockPercentage)
    {
        length = newLength;
        width = newWidth;

        tiles = new Tile[length][];
        for (int a = 0; a < length; a++) tiles[a] = new Tile[width];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                tiles[i][j] = new Tile();
            }
        }

        setGrid(boxPercentage, rockPercentage);
    }

    private static boolean randBool(double perc) {
        return Math.random() < perc;
    }

    //Puts the rocks on the boundaries, puts random boxes, and makes sure the neighbourMap has the right members.
    private void setGrid(int boxPercentage, int rockPercentage) {
        int i, j;
        //pad the edges of the world with rocks so players can't exit the world
        for (j = 0; j < width; j++) {
            tiles[0][j].setContent(new Rock());
            tiles[length - 1][j].setContent(new Rock());
        }
        for (i = 0; i < length; i++) {
            tiles[i][0].setContent(new Rock());
            tiles[i][width - 1].setContent(new Rock());
        }

        for (i = 1; i < length - 1; i++) {
            for (j = 1; j < width - 1; j++) {
                if (!(i == 0 && j == 0) && !(i == length - 1 && j == width - 1)) {
                    if (randBool((double) boxPercentage / 100) && tiles[i][j].isEmpty()) {
                        tiles[i][j].setContent(new Box(tiles[i][j]));
                    } else if (randBool((double) rockPercentage / 100) && tiles[i][j].isEmpty()) {
                        tiles[i][j].setContent(new Rock());
                    }
                }
            }
        }


        for (i = 0; i < length; i++) {
            for (j = 0; j < width; j++) {
                if (i == 0) tiles[i][j].neighbourMap.put(vangDeVolger.DirectionEnum.LEFT, null);
                if (i > 0) tiles[i][j].neighbourMap.put(vangDeVolger.DirectionEnum.LEFT, tiles[i - 1][j]);

                if (i == length - 1) tiles[i][j].neighbourMap.put(vangDeVolger.DirectionEnum.RIGHT, null);
                if (i < length - 1) tiles[i][j].neighbourMap.put(vangDeVolger.DirectionEnum.RIGHT, tiles[i + 1][j]);

                if (j == 0) tiles[i][j].neighbourMap.put(vangDeVolger.DirectionEnum.UP, null);
                if (j > 0) tiles[i][j].neighbourMap.put(vangDeVolger.DirectionEnum.UP, tiles[i][j - 1]);

                if (j == width - 1) tiles[i][j].neighbourMap.put(vangDeVolger.DirectionEnum.DOWN, null);
                if (j < width - 1) tiles[i][j].neighbourMap.put(vangDeVolger.DirectionEnum.DOWN, tiles[i][j + 1]);
            }
        }
    }

    //Used by Game to set player/enemy so tiles can be private
    public Tile getInitPlayerTile()
    {
        return tiles[1][1];
    }

    public Tile getInitEnemyTile()
    {
        return tiles[width - 2][length - 2];
    }
}