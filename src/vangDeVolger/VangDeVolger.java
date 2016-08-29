package vangDeVolger;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

class VangDeVolger implements Observer, Subject {

    public static final Integer FPS = 60;
    private final World world;
    private final UI frame;
    private final int boxPercentage = 50;
    private final int rockPercentage = 5;
    private Timer timer;
    private List<Observer> observers;
    private boolean isRunning = true;

    private VangDeVolger() {
        observers = new LinkedList<>();

        int gridLength = 10;
        int gridWidth = 10;
        world = new World(gridLength, gridWidth, boxPercentage, rockPercentage);

        //player needs to be passed to setup observer pattern between keyboard and player
        frame = new UI(gridLength, gridWidth, world.getPlayer(), this);
        timer = new Timer();
        isRunning = true;

        //setup UI as publisher to get notifications of pause etc.
        frame.register(this);
        //setup Enemy as publisher to get notified about end game logic
        world.getEnemy().register(this);

        //setup Vang de Volger as publisher to let the game pieces know when to move
        register(world.getEnemy());
        register(world.getPlayer());
    }

    public static void main(String[] args) {
        VangDeVolger game = new VangDeVolger();
        game.startGame();
    }

    private void startGame() {
        frame.setWorld(world);
        timer.schedule(new gameLoop(), 0, 1000 / FPS);//timing mechanism at 60fps
    }

    @Override
    public void update(Object changedObject) {
        if (changedObject instanceof String) {
            if (changedObject == "win") {

                frame.repaint();
                frame.win();
                timer.cancel();

            } else if (changedObject == "lose") {

                frame.repaint();
                frame.lose();
                timer.cancel();

            } else if (changedObject == "reset") {

                world.reset(boxPercentage, rockPercentage);
                isRunning = true;
                timer.cancel();
                timer = new Timer();
                timer.schedule(new gameLoop(), 0, 1000 / FPS);

            } else if (changedObject == "pause") {

                if (isRunning) {
                    isRunning = false;
                    timer.cancel();
                } else {
                    isRunning = true;
                    timer = new Timer();
                    timer.schedule(new gameLoop(), 0, 1000 / FPS);
                }

            }
        }
    }

    @Override
    public void register(Observer obj) {

        if (obj == null)
            return;

        if (!observers.contains(obj))
            observers.add(obj);
    }

    @Override
    public void unregister(Observer obj) {
        observers.remove(obj);
    }

    @Override
    public void notifyObservers(Object changedObject) {
        for (Observer obj : observers)
            obj.update(changedObject);
    }

    private class gameLoop extends java.util.TimerTask {

        public void run() {//the actual game loop

            notifyObservers(null);//here nothing needs to be passed since the objects handle the rest themselves
            frame.repaint();

        }
    }

}
