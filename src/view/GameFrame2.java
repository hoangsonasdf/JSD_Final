package view;

import gamecomponent.Direction;
import gamecomponent.HomeBase;
import gamecomponent.Position;
import gamecomponent.enviroment.*;
import gamecomponent.inputhandler.KeyStateHandler;
import gamecomponent.powerup.Grenade;
import gamecomponent.powerup.Helmet;
import gamecomponent.powerup.Star;
import gamecomponent.powerup.TankUp;
import gamecomponent.tank.*;
import manager.BulletManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class GameFrame2 extends GameFrame {
    private JPanel panel;
    private final int numberOfPlayers;
    private Random random = new Random();
    private Position spawnPosition = new Position(100, 100);
    private PlayerTank playerOne;
    private PlayerTank playerTwo;
    private KeyStateHandler keyStateHandler;

    private Position playerOneSpawnPosition = new Position(40, 480);
    private Position playerTwoSpawnPosition = new Position(120, 560);
    private List<EnemyTank> enemyTanks = new ArrayList<>();
    private List<EnemyTank> availableTanks = new ArrayList<>();
    private Grenade grenade;
    private Tree tree;
    private Helmet helmet;
    private CompositeBrickWall brickWall1;
    private Star star;
    private Star star1;
    private Star star2;
    private HomeBase homeBase;
    private boolean isGameOver = false;
    private Timer enemyRespawnTimer;
    private int maxActiveTanks = 2;
    private Timer respawnTimer;
    private Timer gameTimer;




    public GameFrame2(int numberOfPlayers) {
        super(numberOfPlayers);
        this.numberOfPlayers = numberOfPlayers;
        numPlayersException(numberOfPlayers);


        setLayout(null);
        setSize(840, 720);
        getContentPane().setBackground(Color.gray);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        requestFocus();
        setVisible(true);
        setResizable(false);

        this.panel = new JPanel();
        modifyPanel(panel);
        add(panel);

        keyStateHandler = new KeyStateHandler();
        addKeyListener(keyStateHandler);

        spawnRandomEnemyTank();
        spawnRandomEnemyTank();
        startGame();
    }

    @Override
    public void numPlayersException(int numberOfPlayers) {
        super.numPlayersException(numberOfPlayers);
    }

    @Override
    public Component modifyPanel(JPanel panel) {
        return super.modifyPanel(panel);

    }

    @Override
    public void setRespawnTimer() {
        super.setRespawnTimer();
    }

    @Override
    public void setEnemyRespawnTimer() {
        super.setEnemyRespawnTimer();
    }

    @Override
    public void handleInput() {
        super.handleInput();
    }

    @Override
    public void gameOver() {
        super.gameOver();
    }

    @Override
    public void respawnPlayer() {
        super.respawnPlayer();
    }

    @Override
    public void startGame() {
        super.startGame();
    }

    @Override
    public void spawnRandomEnemyTank() {
        super.spawnRandomEnemyTank();
    }

    @Override
    public void updateGame() {
        super.updateGame();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}