package vangDeVolger;

import java.util.Timer;

class VangDeVolger {

    private Grid grid;
    private Player player;
    private Enemy enemy;
    private CustomFrame frame;

    //    private static boolean play = true;
    private Timer timer;
    private boolean isRunning;

    private Integer FPS = 60;


    private VangDeVolger() {
        int percentage = 15;
        int gridLength = 10;
        int gridWidth = 10;
        grid = new Grid(gridLength, gridWidth, percentage);
        player = new Player(grid.tiles[1][1]);
        enemy = new Enemy(grid.tiles[gridLength - 2][gridWidth - 2], gridLength, gridWidth, player);
        frame = new CustomFrame(gridLength, gridWidth, player, enemy);
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
            for (Tile buur : enemy.myTile.burenMap.values()) {
                if (buur.getContent() instanceof Player) {
                    frame.system_exit(); //Game over!
                }
            }


            if(frameCount%(FPS) == 0)//Enemy moves every second
                enemy.AI();

            frame.repaint();

            if(!isRunning){
                timer.cancel();
            }
        }
    }

    public void mainLoop() {


//        long startTime;
//        boolean freedom;
//        DirectionEnum direction;
//        startTime = System.nanoTime();
//
//        while (true) {
//            frame.repaint();
//
//            if (System.nanoTime() - startTime > 10000000 && frame.continue_playing()) {
//                freedom = false;
//                for (Tile buur : enemy.myTile.burenMap.values()) {
//                    if (buur.getContent() instanceof Player) {
//                        frame.system_exit(); //Game over!
//                    }
//                    freedom = freedom || buur.isEmpty();
//                }
//
//                this.enemy.move(enemy.randomMove());
//                startTime = System.nanoTime();
//            }
//        }
    }
}
