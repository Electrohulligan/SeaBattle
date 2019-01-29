import javax.swing.*;

import Battle.*;
import Battle.Box;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SeaBattle extends JFrame {
    private Game game;

    private JPanel panelGamer;
    private JPanel panelPC;
    private JPanel panelLeft;
    private JPanel panelRight;
    private JPanel panelCenter;
    private JPanel panelUp;
    private JPanel panelUpLeft;
    private JPanel panelUpRight;
    private JPanel panelDown;
    private JLabel label;
    private JLabel labelLeft;
    private JLabel labelRight;

    final int COLS = 10;
    final int ROWS = 10;
    final int IMAGE_SIZE = 50;
    final int totalShipsGamers = 19;
    final int totalShipsPC = 20;

    public static void main(String[] args) {
        new SeaBattle();
    }

    private SeaBattle() {
        game = new Game(COLS, ROWS, totalShipsGamers, totalShipsPC);
        game.start();
        setImages();
        initLabel();
        initLabelleft();
        initLabelright();
        initJMenuBar();
        initPanelGamer();
        initPanelPC();
        initBorders();
        drawWindow();
        initFrame();
    }

    private void initLabel() {
        label = new JLabel("Welcome!!!");
    }
    private void initLabelleft() {
        labelLeft = new JLabel("Field of gamer");
    }
    private void initLabelright() {
        labelRight = new JLabel("Field of PC");
    }

    private void initBorders() {
        panelLeft = new JPanel();
        panelRight = new JPanel();
        panelCenter = new JPanel();
        panelUp = new JPanel();
        panelUpLeft = new JPanel();
        panelUpRight = new JPanel();
        panelDown = new JPanel();

        panelLeft.setPreferredSize(new Dimension(IMAGE_SIZE, IMAGE_SIZE * ROWS));
        panelCenter.setPreferredSize(new Dimension(IMAGE_SIZE, IMAGE_SIZE * ROWS));
        panelRight.setPreferredSize(new Dimension(IMAGE_SIZE, IMAGE_SIZE * ROWS));
        panelUpLeft.setPreferredSize(new Dimension(IMAGE_SIZE * (COLS ), IMAGE_SIZE));
        panelUpRight.setPreferredSize(new Dimension(IMAGE_SIZE * (COLS ), IMAGE_SIZE));
        panelDown.setPreferredSize(new Dimension(IMAGE_SIZE * (COLS), IMAGE_SIZE));

    }

    private void initPanelGamer() {
        panelGamer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoordsOfFieldsOfGamer())
                    g.drawImage((Image) game.getBoxGamer(coord).image, coord.x * IMAGE_SIZE,
                            coord.y * IMAGE_SIZE, this);
            }
        };
        panelGamer.setPreferredSize(new Dimension(COLS * IMAGE_SIZE, ROWS * IMAGE_SIZE));
    }

    private void initPanelPC() {
        panelPC = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoordsOfFieldsOfPC())
                    g.drawImage((Image) game.getBoxPC(coord).image, coord.x * IMAGE_SIZE,
                            coord.y * IMAGE_SIZE, this);

            }
        };

        panelPC.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1 && game.state == GameState.PLAYED && game.hitDeckState == null) {
                    try {
                        game.pressLeftButton(coord);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                else if (e.getButton() == MouseEvent.BUTTON1 && game.state == GameState.PLAYED && game.hitDeckState == HitMultipleDeckState.HIT_SECOND_DECK) {
                    try {
                        game.pressLeftButtonHitSecondDeck(coord);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                else if (e.getButton() == MouseEvent.BUTTON1 & game.state == GameState.PLAYED & game.hitDeckState == HitMultipleDeckState.HIT_THIRD_DECK) {
                    try {
                        game.pressLeftButtonHitThirdDeck(coord);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                else if
                    (e.getButton() == MouseEvent.BUTTON1 & game.state == GameState.PLAYED & game.hitDeckState == HitMultipleDeckState.HIT_FOUR_DECK)
                try {
                    game.pressLeftButtonHitFourDeck(coord);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                if (e.getButton() == MouseEvent.BUTTON3)
                    game.start();
                label.setText(getMessage());
                panelPC.repaint();
                panelGamer.repaint();
            }
        });

        panelPC.setPreferredSize(new Dimension(COLS * IMAGE_SIZE, ROWS * IMAGE_SIZE));
    }

    private void drawWindow() {
        setLayout(new GridBagLayout());

        add(panelUp, new GridBagConstraints(0, 0, 5, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));

//        add(panelUpLeft, new GridBagConstraints(0, 0, 5, 1, 1, 1,
//                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
//                new Insets(0, 0, 0, 0), 0, 0));

//        add(panelUpRight, new GridBagConstraints(1, 0, 2, 1, 1, 1,
//                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
//                new Insets(0, 0, 0, 0), 0, 0));

        add(panelLeft, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));

        add(panelGamer, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

        add(panelCenter, new GridBagConstraints(2, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));

        add(panelPC, new GridBagConstraints(3, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

        add(panelRight, new GridBagConstraints(4, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));

        add(panelDown, new GridBagConstraints(0, 2, 5, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));

        panelDown.add(label);

        panelUpLeft.setLayout(new FlowLayout());
        panelUpLeft.add(labelLeft, BorderLayout.CENTER);

        panelUpRight.setLayout(new FlowLayout());
        panelUpRight.add(labelRight, BorderLayout.WEST);

        panelUp.setLayout(new FlowLayout());
        panelUp.add(panelUpLeft, BorderLayout.EAST);
        panelUp.add(panelUpRight, BorderLayout.WEST);

        panelPC.setBorder(BorderFactory.createLineBorder(Color.green));
        panelGamer.setBorder(BorderFactory.createLineBorder(Color.blue));
    }

    private String getMessage() {
        switch (game.getState()) {
            case PLAYED:
                return "Play";
            case LOSED:
                return "You Lost!!!";
            case WINNER:
                return "You Win!!!";
            default:
                return "";
        }
    }

    private void initJMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem subGameMenuNewGame = new JMenuItem("New game");
        JMenuItem subGameMenuExit = new JMenuItem("Exit");

        setJMenuBar(jMenuBar);
        jMenuBar.add(gameMenu);
        gameMenu.add(subGameMenuNewGame);
        gameMenu.add(subGameMenuExit);

        subGameMenuNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.start();
                label.setText(getMessage());
                panelPC.repaint();
                panelGamer.repaint();
            }
        });

        subGameMenuExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("My SeaBattle");
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setIconImage(getImage("icon"));
        pack();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3)
                    game.start();
                label.setText(getMessage());
                panelPC.repaint();
                panelGamer.repaint();
            }
        });
    }

    private void setImages() {
        for (Box box : Box.values())
            box.image = getImage(box.name());
    }

    private Image getImage(String name) {
        String filename = "img/" + name.toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
}
