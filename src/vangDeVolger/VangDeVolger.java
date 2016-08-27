package vangDeVolger;

import java.util.Timer;

class VangDeVolger {

    private final Grid grid;
    private final Enemy enemy;
    private final CustomFrame frame;

    //    private static boolean play = true;
    private final Timer timer;
    private final boolean isRunning;

    private final Integer FPS = 60;


    private VangDeVolger() {
        int boxPercentage = 40;
        int rockPercentage = 1;
        int gridLength = 10;
        int gridWidth = 10;
        grid = new Grid(gridLength, gridWidth, boxPercentage, rockPercentage);
//        player = new Player(grid.getInitPlayerTile());
//        enemy = new Enemy(grid.getInitEnemyTile());
        Player player = new Player(grid.getInitPlayerTile());
        enemy = new Enemy(grid.getInitEnemyTile());
        frame = new CustomFrame(gridLength, gridWidth, player);
        timer = new Timer();
        isRunning = true;
        //The following is done in the constructor of Player resp. Enemy (actually in the MovableObject constructor)
        //grid.tiles[1][1].setContent(player);
        //grid.tiles[grid.length - 2][grid.width - 2].setContent(enemy);
        //TODO niet hun verantwoordelijkhied refactor
    }

    public static void main(String[] args) {
        VangDeVolger game = new VangDeVolger();
        game.startGame();
    }

    private void startGame(){
        frame.setGrid(grid);
        timer.schedule(new gameLoop(),0, 1000/FPS);//timing mechanism at 60fps
    }

    private class gameLoop extends java.util.TimerTask{

        private Integer frameCount = 0;

        public void run(){//the actual game loop
            frameCount += 1;
            //TODO improve logic for win/loose conditions
            boolean freedom = false;
            for (Tile neighbour : enemy.myTile.neighbourMap.values()) {
                freedom = freedom || neighbour.isEmpty();
            }
            if (!freedom)
                frame.system_exit(); //Game over!

            if (frameCount % (FPS * 5) == 0)//Enemy moves every second
                enemy.AI();

            frame.repaint();

            if(!isRunning){
                timer.cancel();
            }
        }
    }

}
