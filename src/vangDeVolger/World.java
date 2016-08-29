package vangDeVolger;


class World {
    final Tile[][] tiles;

    //Assume both are >= 2
    final int length, width;
    private final Player player;
    private final Enemy enemy;

    //Sets the dimensions and creates the tiles
    World(int newLength, int newWidth, int boxPercentage, int rockPercentage) {
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
        player = new Player(tiles[1][1]);
        enemy = new Enemy(tiles[width - 2][length - 2]);

    }

    private static boolean randBool(double perc) {
        return Math.random() < perc;
    }

    public Player getPlayer() {
        return player;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void reset(int boxPercentage, int rockPercentage) {
        setGrid(boxPercentage, rockPercentage);
        player.myTile.setContent(null);
        player.myTile = tiles[1][1];
        player.myTile.setContent(player);
        enemy.myTile.setContent(null);
        enemy.myTile = tiles[width - 2][length - 2];
        enemy.myTile.setContent(enemy);

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
                if (!(i == 1 && j == 1) && !(i == length - 2 && j == width - 2)) {//don't spawn obstacles on top of player or enemy
                    if (randBool((double) boxPercentage / 100) && tiles[i][j].isEmpty()) {
                        tiles[i][j].setContent(new Box(tiles[i][j]));
                    } else if (randBool((double) rockPercentage / 100) && tiles[i][j].isEmpty()) {
                        tiles[i][j].setContent(new Rock());
                    } else
                        tiles[i][j].setContent(null);
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

                tiles[i][j].neighbourMap.put(DirectionEnum.NONE, tiles[i][j]);
            }
        }
    }
}