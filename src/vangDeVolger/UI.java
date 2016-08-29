package vangDeVolger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

class UI extends JFrame implements KeyListener, Subject {

    private final int rowHeight = 50;
    private final int columnWidth = 50;
    private final JButton pauseButton;
    private final JButton resetButton;
    private final JPanel panel;
    private final JComponent graphics;

    private List<Observer> observers;

    private World world;


    public UI(int rows, int columns, Player p, VangDeVolger v) {
        super();
        panel = new JPanel();
        pauseButton = new JButton("Pause");
        resetButton = new JButton("Reset");
        graphics = new GridView(rows, columns);
        world = null;

        observers = new LinkedList<>();
        register(p);
        register(v);

        addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();
        setContentPane(panel);
        setSize((columns + 2) * columnWidth, (rows + 1) * rowHeight);
        setLayout(new GridBagLayout());
        initLayout();
        addListeners();
        setVisible(true);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    //The following two don't do anything, but we MUST override them
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            notifyObservers(DirectionEnum.RIGHT);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            notifyObservers(DirectionEnum.LEFT);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            notifyObservers(DirectionEnum.UP);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            notifyObservers(DirectionEnum.DOWN);
        }


    }

    private void addListeners() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        // Button listeners
        resetButton.addActionListener(e -> {
            notifyObservers("reset");
        });

        resetButton.setFocusable(false);

        pauseButton.addActionListener(e -> {
            notifyObservers("pause");
        });

        pauseButton.setFocusable(false);
    }

    public void win() {
        JOptionPane.showMessageDialog(this,
                "Congratulations, you won! Either reset the game or exit the application.",
                "You won!",
                JOptionPane.PLAIN_MESSAGE);
    }

    public void lose() {
        JOptionPane.showMessageDialog(this,
                "The enemy caught you, you lost! Either reset the game or exit the application.",
                "You lost!",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void initLayout() {
        // Init all new panels
        GridBagConstraints gbc = new GridBagConstraints();

        // Set global constraints
        gbc.fill = GridBagConstraints.BOTH;

        // Graphics panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridheight = 2;
        panel.add(graphics, gbc);

        // Buttons
        gbc.gridx = 1;
        gbc.weightx = 0;
        gbc.weighty = 1;
        gbc.gridheight = 1;

        // Pause button
        gbc.gridy = 0;
        panel.add(pauseButton, gbc);

        // Play button
        gbc.gridy = 1;
        panel.add(resetButton, gbc);
    }

    public void setWorld(World newWorld) {
        world = newWorld;
    }

    public void repaint() {
        super.repaint();
        graphics.paintComponents(getGraphics());
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
        for (Observer obj : observers) {
            obj.update(changedObject);
        }
    }

    public class GridView extends JComponent {

        GridView(int rows, int columns) {
            setMinimumSize(new Dimension(rows * rowHeight, columns * columnWidth));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (world == null) {
                return;
            }

            for (int i = 0; i < world.width; i++) {
                for (int j = 0; j < world.length; j++) {
                    paintTile(g, world.tiles[i][j], i, j);
                }
            }
        }

        private void paintTile(Graphics g, Tile t, int i, int j) {
            g.setColor(t.getColor());

            g.fillRect(i * columnWidth, j * rowHeight, columnWidth, rowHeight);
        }
    }
}
