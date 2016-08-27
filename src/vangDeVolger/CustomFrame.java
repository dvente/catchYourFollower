package vangDeVolger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


//TODO reset button
//TODO pause/restart button
//TODO win/loose panels so game remains active after game end

class CustomFrame extends JFrame implements KeyListener {

    private final int rowHeight = 50;
    private final int columnWidth = 50;
    private final JButton pauseButton;
    private final JButton playButton;
    private final JPanel pane;
    private final JComponent graphics;
    //TODO deze hoort niet bij deze klasse, refactor
    private final Player player;
    private Grid grid;
    //TODO deze hoort niet bij deze klasse, refactor
    private boolean play = true;

    public CustomFrame(int rows, int columns, Player p) {
        super();
        pane = new JPanel();
        player = p;
        pauseButton = new JButton("Pause");
        playButton = new JButton("Play");
        graphics = new GridView(rows, columns);
        grid = null;
        addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();
        setContentPane(pane);
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
        //TODO Stuur zulke events door naar een spel-klasse als `VangDeVolger` die wordt meegegeven aan deze UI klasse (via de constructor bijv), pas niet vanuit de UI direct deze structuren aan.
        if (play) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                player.move(DirectionEnum.RIGHT);
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                player.move(DirectionEnum.LEFT);
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                player.move(DirectionEnum.UP);
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                player.move(DirectionEnum.DOWN);
            }
        }
    }

    private void addListeners() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        // Button listeners
        playButton.addActionListener(e -> {
            System.out.println(e);
            play = true;
            System.out.println(play);
        });

        playButton.setFocusable(false);

        pauseButton.addActionListener(e -> {
            System.out.println(e);
            play = false;
            System.out.println(play);
        });

        pauseButton.setFocusable(false);
    }

    public void system_exit() {
        play = false;
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        JPanel endPane = new JPanel();
        JLabel endText = new JLabel();
        endText.setText("<html><br/><br/><br/>Game over, the enemy caught you! <br/> Click upper right corner to exit.</html>");
        endPane.add(endText, gbc);
        setContentPane(endPane);
        setVisible(true);
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
        pane.add(graphics, gbc);

        // Buttons
        gbc.gridx = 1;
        gbc.weightx = 0;
        gbc.weighty = 1;
        gbc.gridheight = 1;

        // Pause button
        gbc.gridy = 0;
        pane.add(pauseButton, gbc);

        // Play button
        gbc.gridy = 1;
        pane.add(playButton, gbc);
    }

    public void setGrid(Grid newGrid) {
        grid = newGrid;
    }

    public void repaint() {
        super.repaint();
        graphics.paintComponents(getGraphics());
    }

    private class exitFrame extends JFrame {

        JFrame Frame = new JFrame();

        public exitFrame() {
            super("Game over");
        }
    }


    public class GridView extends JComponent {

        GridView(int rows, int columns) {
            setMinimumSize(new Dimension(rows * rowHeight, columns * columnWidth));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (grid == null) {
                return;
            }

            for (int i = 0; i < grid.width; i++) {
                for (int j = 0; j < grid.length; j++) {
                    paintTile(g, grid.tiles[i][j], i, j);
                }
            }
        }

        private void paintTile(Graphics g, Tile t, int i, int j) {
            g.setColor(t.getColor());

            g.fillRect(i * columnWidth, j * rowHeight, columnWidth, rowHeight);
        }
    }
}
