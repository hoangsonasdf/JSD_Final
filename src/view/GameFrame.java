package view;

import gamecomponent.Direction;
import gamecomponent.HomeBase;
import gamecomponent.Position;
import gamecomponent.enviroment.CompositeBrickWall;
import gamecomponent.enviroment.Tree;
import gamecomponent.inputhandler.KeyStateHandler;
import gamecomponent.powerup.Grenade;
import gamecomponent.powerup.Helmet;
import gamecomponent.powerup.Star;
import gamecomponent.tank.*;
import manager.BulletManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class GameFrame extends JFrame {
    private final int numberOfPlayers;
    private Random random = new Random();
    Position spawnPosition = new Position(100, 100);
    private PlayerTank playerOne;
    private PlayerTank playerTwo;
    private KeyStateHandler keyStateHandler;
    private Timer inputTimer;
    private Position playerOneSpawnPosition = new Position(100, 500);
    private Position playerTwoSpawnPosition = new Position(700, 500);
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

    public GameFrame(int numberOfPlayers) {
        if (numberOfPlayers < 1 || numberOfPlayers > 2) {
            throw new IllegalArgumentException("Number of players must be 1 or 2");
        }

        this.numberOfPlayers = numberOfPlayers;
        setLayout(null);
        setSize(800, 600);
//        getContentPane().setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeBase = new HomeBase(new Position(15, 15));
        add(homeBase);


        brickWall1 = new CompositeBrickWall(new Position(0, 80));
//        brickWall1.addToFrame(this);

        tree = new Tree(new Position(50, 50));
        add(tree);

        helmet = new Helmet(new Position(150, 50));
        add(helmet);

        grenade = new Grenade(new Position(50, 150));
        add(grenade);

        star = new Star(new Position(123, 342));
        add(star);

        star1 = new Star(new Position(543, 342));
        add(star1);

        star2 = new Star(new Position(123, 234));
        add(star2);


        keyStateHandler = new KeyStateHandler();
        addKeyListener(keyStateHandler);

        playerOne = new PlayerTank(playerOneSpawnPosition, PlayerTankEnum.PLAYER1);

        add(playerOne);
        if (numberOfPlayers == 2){
            playerTwo = new PlayerTank(playerTwoSpawnPosition, PlayerTankEnum.PLAYER2);
            add(playerTwo);
        }

        availableTanks.add(new BasicTank(new Position(300, 300)));
        availableTanks.add(new ArmorTank(new Position(400, 350)));
        availableTanks.add(new FastTank(new Position(500, 400)));
        availableTanks.add(new PowerTank(new Position(200, 500)));

        spawnRandomEnemyTank();
        spawnRandomEnemyTank();

        inputTimer = new Timer(16, e -> handleInput()); // 60 FPS
        inputTimer.start();

        respawnTimer = new Timer(3000, e -> {
            respawnPlayer();
            ((Timer)e.getSource()).stop();
        });
        respawnTimer.setRepeats(false);


        enemyRespawnTimer = new Timer(3000, e -> {
            spawnRandomEnemyTank();
            ((Timer) e.getSource()).stop();
        });
        enemyRespawnTimer.setRepeats(false);

        setFocusable(true);
        requestFocus();
        setVisible(true);
        setResizable(false);
        startGame();
    }

    private void handleInput() {
        if (playerOne != null && playerOne.isActive()) {
            if (keyStateHandler.isKeyPressed(KeyEvent.VK_UP)) {
                playerOne.setDirection(Direction.U);
                playerOne.move();
            }
            if (keyStateHandler.isKeyPressed(KeyEvent.VK_DOWN)) {
                playerOne.setDirection(Direction.D);
                playerOne.move();
            }
            if (keyStateHandler.isKeyPressed(KeyEvent.VK_LEFT)) {
                playerOne.setDirection(Direction.L);
                playerOne.move();
            }
            if (keyStateHandler.isKeyPressed(KeyEvent.VK_RIGHT)) {
                playerOne.setDirection(Direction.R);
                playerOne.move();
            }
            if (keyStateHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
                playerOne.attempFire();
            }
        }

        if (numberOfPlayers == 2 && playerTwo != null && playerTwo.isActive()) {
            if (keyStateHandler.isKeyPressed(KeyEvent.VK_W)) {
                playerTwo.setDirection(Direction.U);
                playerTwo.move();
            }
            if (keyStateHandler.isKeyPressed(KeyEvent.VK_S)) {
                playerTwo.setDirection(Direction.D);
                playerTwo.move();
            }
            if (keyStateHandler.isKeyPressed(KeyEvent.VK_A)) {
                playerTwo.setDirection(Direction.L);
                playerTwo.move();
            }
            if (keyStateHandler.isKeyPressed(KeyEvent.VK_D)) {
                playerTwo.setDirection(Direction.R);
                playerTwo.move();
            }
            if (keyStateHandler.isKeyPressed(KeyEvent.VK_SPACE)) {
                playerTwo.attempFire();
            }
        }
    }

    private void respawnPlayer() {
        if (playerOne != null && !playerOne.isActive()) {
            if (playerOne.getLife() > 0) {
                PlayerTank newPlayerOne = new PlayerTank(playerOneSpawnPosition, PlayerTankEnum.PLAYER1);
                newPlayerOne.setLife(playerOne.getLife());
                newPlayerOne.setPoint(playerOne.getPoint());
                newPlayerOne.setTier(playerOne.getTier());

                remove(playerOne);
                playerOne = newPlayerOne;
                add(playerOne);
                playerOne.updateTierState();
            }
        }

        if (numberOfPlayers == 2 && playerTwo != null && !playerTwo.isActive()) {
            if (playerTwo.getLife() > 0) {
                PlayerTank newPlayerTwo = new PlayerTank(playerTwoSpawnPosition,PlayerTankEnum.PLAYER2);
                newPlayerTwo.setLife(playerTwo.getLife());
                newPlayerTwo.setPoint(playerTwo.getPoint());
                newPlayerTwo.setTier(playerTwo.getTier());

                remove(playerTwo);
                playerTwo = newPlayerTwo;
                add(playerTwo);
                playerTwo.updateTierState();
            }
        }
    }

    private void gameOver() {
        isGameOver = true;

        if (gameTimer != null) {
            gameTimer.stop();
        }
        if (respawnTimer != null) {
            respawnTimer.stop();
        }

        JLabel gameOverLabel = new JLabel("GAME OVER");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 48));
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setBounds(getWidth()/2 - 150, getHeight()/2 - 50, 300, 100);
        add(gameOverLabel);
        repaint();
    }

    public void startGame() {
        gameTimer = new Timer(1000 / 60, e -> {
            updateGame();
        });
        gameTimer.start();
    }

    private void spawnRandomEnemyTank() {
        if (availableTanks.size() > 0 && enemyTanks.size() < maxActiveTanks) {

            int randomIndex = random.nextInt(availableTanks.size());
            EnemyTank tank = availableTanks.get(randomIndex);

            enemyTanks.add(tank);
            availableTanks.remove(randomIndex);

            add(tank);
            repaint();
        }
    }

    private void updateGame() {
        BulletManager.getInstance().updateBullets(this);
        if (isGameOver) {
            return;
        }
        if (homeBase.getHealth() <= 0){
            gameOver();
        }

        if ((!playerOne.isActive() || (numberOfPlayers == 2 && !playerTwo.isActive())) &&
                !respawnTimer.isRunning()) {
            respawnTimer.start();
        }

        Iterator<EnemyTank> iterator = enemyTanks.iterator();
        while (iterator.hasNext()) {
            EnemyTank enemyTank = iterator.next();
            if (enemyTank.isActive()) {
                enemyTank.move();
                if (random.nextInt(100) < 5) {
                    enemyTank.attempFire();
                }
            } else {
                iterator.remove();
                if (!enemyRespawnTimer.isRunning()) {
                    enemyRespawnTimer.start();
                }
            }
        }

        repaint();
    }

    @Override
    public void dispose() {
        super.dispose();
        if (inputTimer != null) {
            inputTimer.stop();
        }
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }
}
