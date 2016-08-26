package vangDeVolger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomFrame extends JFrame implements KeyListener {

    private final int rowHeight = 50;
    private final int columnWidth = 50;
    private JButton pauseButton;
    private JButton playButton;
    private JPanel pane;
    private JComponent graphics;
    private Grid grid;

    //TODO deze hoort niet bij deze klasse, refactor
    private  Player player;
    //TODO deze hoort niet bij deze klasse, refactor
    private  boolean play = true;

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
            System.out.println(e);
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                player.move(DirectionEnum.DOWN);
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                player.move(DirectionEnum.UP);
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                player.move(DirectionEnum.LEFT);
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                player.move(DirectionEnum.RIGHT);
            }
        }
    }

    public class exitFrame extends JFrame {

        JFrame Frame = new JFrame();

        public exitFrame() {
            super("Game over");
        }
    }


    //TODO rename MyGraphics
    public class MyGraphics extends JComponent {

        MyGraphics(int rows, int columns) {
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
            //TODO use overrides
            if (t.isEmpty()) {
                g.setColor(Color.white);
            } else if (t.getContent() instanceof Rock) {
                g.setColor(Color.black);
            } else if (t.getContent() instanceof Player) {
                g.setColor(Color.blue);
            } else if (t.getContent() instanceof Enemy) {
                g.setColor(Color.red);
            } else if (t.getContent() instanceof Box) {
                g.setColor(new Color(156, 93, 82));
            }

            g.fillRect(i * columnWidth, j * rowHeight, columnWidth, rowHeight);
        }
    }

    // Create a constructor method
    public CustomFrame(int rows, int columns, Player p) {
        super();

        pane = new JPanel();
        player = p;
        pauseButton = new JButton("Pause");
        playButton = new JButton("Play");
        graphics = new MyGraphics(rows, columns);
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

    private void addListeners() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        // Button listeners
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(e);
                play = true;
                System.out.println(play);
            }
        });

        playButton.setFocusable(false);

        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(e);
                play = false;
                System.out.println(play);
            }
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

    public boolean continue_playing() {
        return play;
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

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
