package view;


import gamecomponent.Direction;
import gamecomponent.HomeBase;
import gamecomponent.Position;
import gamecomponent.enviroment.*;
import gamecomponent.powerup.PowerUp;
import manager.KeyStateHandler;
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

public class GameFrameLevel2 extends JFrame {
    private JPanel panel;
    private final int numberOfPlayers;
    private Random random = new Random();
    private Position spawnPosition = new Position(100, 100);
    private PlayerTank playerOne;
    private PlayerTank playerTwo;
    private KeyStateHandler keyStateHandler;
    private Position playerOneSpawnPosition = new Position(280, 560);
    private Position playerTwoSpawnPosition = new Position(320, 560);
    private List<EnemyTank> enemyTanks = new ArrayList<>();
    private List<EnemyTank> availableTanks = new ArrayList<>();
    private List<PowerUp> powerUpList = new ArrayList<>();
    private HomeBase homeBase;
    private boolean isGameOver = false;
    private Timer enemyRespawnTimer;
    private int maxActiveTanks = 3;
    private Timer respawnTimer;
    private Timer gameTimer;
    private Timer powerUpSpawnTimer;
    private int powerUpSpawnInterval = 10000;
    private JLabel player1ScoreLabel;
    private JLabel player1LivesLabel;
    private JLabel player2ScoreLabel;
    private JLabel player2LivesLabel;

    public GameFrameLevel2(int numberOfPlayers) {
        if (numberOfPlayers < 1 || numberOfPlayers > 2) {
            throw new IllegalArgumentException("Number of players must be 1 or 2");
        }

        this.numberOfPlayers = numberOfPlayers;
        setLayout(null);
        setSize(840, 720);
        getContentPane().setBackground(Color.gray);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.black);
        panel.setSize(640, 640);
        panel.setLocation(80, 20);
        add(panel);


        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(numberOfPlayers == 2 ? 4 : 2, 1));
        statsPanel.setBounds(740, 20, 80, numberOfPlayers == 2 ? 160 : 80);
        statsPanel.setBackground(Color.BLACK);


        // add home base
        homeBase = new HomeBase(new Position(40, 40));
        panel.add(homeBase);

        // add power-up
        Helmet helmet1 = new Helmet(new Position(240, 320));
        powerUpList.add(helmet1);

        Helmet helmet2 = new Helmet(new Position(560, 560));
        powerUpList.add(helmet2);

        Star star = new Star(new Position(360, 320));
        powerUpList.add(star);

        Star star1 = new Star(new Position(40, 560));
        powerUpList.add(star1);

        TankUp tankUp = new TankUp(new Position(560, 40));
        powerUpList.add(tankUp);

        TankUp tankUp1 = new TankUp(new Position(280, 280));
        powerUpList.add(tankUp1);

        // add tanks
        availableTanks.add(new BasicTank(new Position(240, 40)));
        availableTanks.add(new BasicTank(new Position(560, 160)));
        availableTanks.add(new BasicTank(new Position(320, 360)));
        availableTanks.add(new FastTank(new Position(40, 480)));
        availableTanks.add(new PowerTank(new Position(480, 560)));

        availableTanks.add(new BasicTank(new Position(240, 40)));
        availableTanks.add(new BasicTank(new Position(560, 160)));
        availableTanks.add(new BasicTank(new Position(320, 360)));
        availableTanks.add(new FastTank(new Position(40, 480)));
        availableTanks.add(new PowerTank(new Position(480, 560)));

        // add tree
        Tree tree1 = new Tree(new Position(280, 40));
        panel.add(tree1);
        panel.setComponentZOrder(tree1, 1);

        Tree tree2 = new Tree(new Position(320, 40));
        panel.add(tree2);
        panel.setComponentZOrder(tree2, 1);

        Tree tree3 = new Tree(new Position(280, 80));
        panel.add(tree3);
        panel.setComponentZOrder(tree3, 1);

        Tree tree4 = new Tree(new Position(320, 80));
        panel.add(tree4);
        panel.setComponentZOrder(tree4, 1);

        Tree tree5 = new Tree(new Position(40, 280));
        panel.add(tree5);
        panel.setComponentZOrder(tree5, 1);

        Tree tree6 = new Tree(new Position(80, 280));
        panel.add(tree6);
        panel.setComponentZOrder(tree6, 1);

        Tree tree7 = new Tree(new Position(40, 320));
        panel.add(tree7);
        panel.setComponentZOrder(tree7, 1);

        Tree tree8 = new Tree(new Position(80, 320));
        panel.add(tree8);
        panel.setComponentZOrder(tree8, 1);

        Tree tree9 = new Tree(new Position(520, 280));
        panel.add(tree9);
        panel.setComponentZOrder(tree9, 1);

        Tree tree10 = new Tree(new Position(560, 280));
        panel.add(tree10);
        panel.setComponentZOrder(tree10, 1);

        Tree tree11 = new Tree(new Position(520, 320));
        panel.add(tree11);
        panel.setComponentZOrder(tree11, 1);

        Tree tree12 = new Tree(new Position(560, 320));
        panel.add(tree12);
        panel.setComponentZOrder(tree12, 1);

        // add metal
        //the upper-half of the wall
        MetalWall mw1 = new MetalWall(new Position(200, 40));
        panel.add(mw1);

        MetalWall mw2 = new MetalWall(new Position(200, 80));
        panel.add(mw2);

        MetalWall mw3 = new MetalWall(new Position(200, 120));
        panel.add(mw3);

        MetalWall mw4 = new MetalWall(new Position(280, 120));
        panel.add(mw4);

        MetalWall mw5 = new MetalWall(new Position(320, 120));
        panel.add(mw5);

        MetalWall mw6 = new MetalWall(new Position(400, 40));
        panel.add(mw6);

        MetalWall mw7 = new MetalWall(new Position(400, 80));
        panel.add(mw7);

        MetalWall mw8 = new MetalWall(new Position(400, 120));
        panel.add(mw8);

        // add the left-half of the wall
        MetalWall mw9 = new MetalWall(new Position(40, 200));
        panel.add(mw9);

        MetalWall mw10 = new MetalWall(new Position(80, 200));
        panel.add(mw10);

        MetalWall mw11 = new MetalWall(new Position(120, 200));
        panel.add(mw11);

        MetalWall mw12 = new MetalWall(new Position(120, 280));
        panel.add(mw12);

        MetalWall mw13 = new MetalWall(new Position(120, 320));
        panel.add(mw13);

        MetalWall mw14 = new MetalWall(new Position(40, 400));
        panel.add(mw14);

        MetalWall mw15 = new MetalWall(new Position(80, 400));
        panel.add(mw15);

        MetalWall mw16 = new MetalWall(new Position(120, 400));
        panel.add(mw16);

        // the right-half of the wall
        MetalWall mw17 = new MetalWall(new Position(480, 200));
        panel.add(mw17);

        MetalWall mw18 = new MetalWall(new Position(520, 200));
        panel.add(mw18);

        MetalWall mw19 = new MetalWall(new Position(560, 200));
        panel.add(mw19);

        MetalWall mw20 = new MetalWall(new Position(480, 280));
        panel.add(mw20);

        MetalWall mw21 = new MetalWall(new Position(480, 320));
        panel.add(mw21);

        MetalWall mw22 = new MetalWall(new Position(480, 400));
        panel.add(mw22);

        MetalWall mw23 = new MetalWall(new Position(520, 400));
        panel.add(mw23);

        MetalWall mw24 = new MetalWall(new Position(560, 400));
        panel.add(mw24);

        // add the lower-half of the wall
        MetalWall mw25 = new MetalWall(new Position(200, 480));
        panel.add(mw25);

        MetalWall mw26 = new MetalWall(new Position(200, 520));
        panel.add(mw26);

        MetalWall mw27 = new MetalWall(new Position(200, 5600));
        panel.add(mw27);

        MetalWall mw28 = new MetalWall(new Position(280, 480));
        panel.add(mw28);

        MetalWall mw29 = new MetalWall(new Position(320, 480));
        panel.add(mw29);

        MetalWall mw30 = new MetalWall(new Position(400, 480));
        panel.add(mw30);

        MetalWall mw31 = new MetalWall(new Position(400, 520));
        panel.add(mw31);

        MetalWall mw32 = new MetalWall(new Position(400, 560));
        panel.add(mw32);

        // add the middle part of the wall
        MetalWall mw33 = new MetalWall(new Position(200, 200));
        panel.add(mw33);

        MetalWall mw34 = new MetalWall(new Position(240, 200));
        panel.add(mw34);

        MetalWall mw35 = new MetalWall(new Position(200, 240));
        panel.add(mw35);

        MetalWall mw36 = new MetalWall(new Position(240, 240));
        panel.add(mw36);

        MetalWall mw37 = new MetalWall(new Position(360, 200));
        panel.add(mw37);

        MetalWall mw38 = new MetalWall(new Position(400, 200));
        panel.add(mw38);

        MetalWall mw39 = new MetalWall(new Position(360, 240));
        panel.add(mw39);

        MetalWall mw40 = new MetalWall(new Position(400, 240));
        panel.add(mw40);

        MetalWall mw41 = new MetalWall(new Position(200, 360));
        panel.add(mw41);

        MetalWall mw42 = new MetalWall(new Position(240, 360));
        panel.add(mw42);

        MetalWall mw43 = new MetalWall(new Position(200, 400));
        panel.add(mw43);

        MetalWall mw44 = new MetalWall(new Position(240, 400));
        panel.add(mw44);

        MetalWall mw45 = new MetalWall(new Position(360, 360));
        panel.add(mw45);

        MetalWall mw46 = new MetalWall(new Position(400, 360));
        panel.add(mw46);

        MetalWall mw47 = new MetalWall(new Position(360, 400));
        panel.add(mw47);

        MetalWall mw48 = new MetalWall(new Position(400, 400));
        panel.add(mw48);

        // add the upper-left part of the brick wall
        CompositeBrickWall brickWall1 = new CompositeBrickWall(new Position(0, 0));
        brickWall1.addToPanel(panel);

        CompositeBrickWall brickWall2 = new CompositeBrickWall(new Position(40, 0));
        brickWall2.addToPanel(panel);

        CompositeBrickWall brickWall3 = new CompositeBrickWall(new Position(80, 0));
        brickWall3.addToPanel(panel);

        CompositeBrickWall brickWall4 = new CompositeBrickWall(new Position(0, 40));
        brickWall4.addToPanel(panel);

        CompositeBrickWall brickWall5 = new CompositeBrickWall(new Position(80, 40));
        brickWall5.addToPanel(panel);

        CompositeBrickWall brickWall6 = new CompositeBrickWall(new Position(0, 80));
        brickWall6.addToPanel(panel);

        CompositeBrickWall brickWall7 = new CompositeBrickWall(new Position(40, 80));
        brickWall7.addToPanel(panel);

        CompositeBrickWall brickWall8 = new CompositeBrickWall(new Position(80, 80));
        brickWall8.addToPanel(panel);

        CompositeBrickWall brickWall9 = new CompositeBrickWall(new Position(200, 0));
        brickWall9.addToPanel(panel);

        CompositeBrickWall brickWall10 = new CompositeBrickWall(new Position(200, 160));
        brickWall10.addToPanel(panel);

        CompositeBrickWall brickWall11 = new CompositeBrickWall(new Position(0, 200));
        brickWall11.addToPanel(panel);

        CompositeBrickWall brickWall12 = new CompositeBrickWall(new Position(160, 200));
        brickWall12.addToPanel(panel);

        // add the upper-right part of the wall
        CompositeBrickWall brickWall13 = new CompositeBrickWall(new Position(400, 0));
        brickWall13.addToPanel(panel);

        CompositeBrickWall brickWall14 = new CompositeBrickWall(new Position(400, 160));
        brickWall14.addToPanel(panel);

        CompositeBrickWall brickWall15 = new CompositeBrickWall(new Position(440, 200));
        brickWall15.addToPanel(panel);

        CompositeBrickWall brickWall16 = new CompositeBrickWall(new Position(600, 200));
        brickWall16.addToPanel(panel);

        CompositeBrickWall brickWall17 = new CompositeBrickWall(new Position(520, 0));
        brickWall17.addToPanel(panel);

        CompositeBrickWall brickWall18 = new CompositeBrickWall(new Position(520, 40));
        brickWall18.addToPanel(panel);

        CompositeBrickWall brickWall19 = new CompositeBrickWall(new Position(520, 80));
        brickWall19.addToPanel(panel);

        CompositeBrickWall brickWall20 = new CompositeBrickWall(new Position(560, 80));
        brickWall20.addToPanel(panel);

        CompositeBrickWall brickWall21 = new CompositeBrickWall(new Position(600, 80));
        brickWall21.addToPanel(panel);

        // add the lower-left part of the wall
        CompositeBrickWall brickWall22 = new CompositeBrickWall(new Position(0, 400));
        brickWall22.addToPanel(panel);

        CompositeBrickWall brickWall23 = new CompositeBrickWall(new Position(160, 400));
        brickWall23.addToPanel(panel);

        CompositeBrickWall brickWall24 = new CompositeBrickWall(new Position(200, 440));
        brickWall24.addToPanel(panel);

        CompositeBrickWall brickWall25 = new CompositeBrickWall(new Position(200, 600));
        brickWall25.addToPanel(panel);

        CompositeBrickWall brickWall26 = new CompositeBrickWall(new Position(0, 520));
        brickWall26.addToPanel(panel);

        CompositeBrickWall brickWall27 = new CompositeBrickWall(new Position(40, 520));
        brickWall27.addToPanel(panel);

        CompositeBrickWall brickWall28 = new CompositeBrickWall(new Position(80, 520));
        brickWall28.addToPanel(panel);

        CompositeBrickWall brickWall29 = new CompositeBrickWall(new Position(80, 560));
        brickWall29.addToPanel(panel);

        CompositeBrickWall brickWall30 = new CompositeBrickWall(new Position(80, 600));
        brickWall30.addToPanel(panel);

        // add the lower-right part of the wall
        CompositeBrickWall brickWall31 = new CompositeBrickWall(new Position(400, 440));
        brickWall31.addToPanel(panel);

        CompositeBrickWall brickWall32 = new CompositeBrickWall(new Position(400, 600));
        brickWall32.addToPanel(panel);

        CompositeBrickWall brickWall33 = new CompositeBrickWall(new Position(440, 400));
        brickWall33.addToPanel(panel);

        CompositeBrickWall brickWall34 = new CompositeBrickWall(new Position(600, 400));
        brickWall34.addToPanel(panel);

        CompositeBrickWall brickWall35 = new CompositeBrickWall(new Position(520, 520));
        brickWall35.addToPanel(panel);

        CompositeBrickWall brickWall36 = new CompositeBrickWall(new Position(520, 560));
        brickWall36.addToPanel(panel);

        CompositeBrickWall brickWall37 = new CompositeBrickWall(new Position(520, 600));
        brickWall37.addToPanel(panel);

        CompositeBrickWall brickWall38 = new CompositeBrickWall(new Position(560, 520));
        brickWall38.addToPanel(panel);

        CompositeBrickWall brickWall39 = new CompositeBrickWall(new Position(600, 520));
        brickWall39.addToPanel(panel);

        // add the middle part of the wall
        CompositeBrickWall brickWall40 = new CompositeBrickWall(new Position(280, 200));
        brickWall40.addToPanel(panel);

        CompositeBrickWall brickWall41 = new CompositeBrickWall(new Position(320, 200));
        brickWall41.addToPanel(panel);

        CompositeBrickWall brickWall42 = new CompositeBrickWall(new Position(200, 280));
        brickWall42.addToPanel(panel);

        CompositeBrickWall brickWall43 = new CompositeBrickWall(new Position(200, 320));
        brickWall43.addToPanel(panel);

        CompositeBrickWall brickWall44 = new CompositeBrickWall(new Position(400, 280));
        brickWall44.addToPanel(panel);

        CompositeBrickWall brickWall45 = new CompositeBrickWall(new Position(400, 320));
        brickWall45.addToPanel(panel);

        CompositeBrickWall brickWall46 = new CompositeBrickWall(new Position(280, 400));
        brickWall46.addToPanel(panel);

        CompositeBrickWall brickWall47 = new CompositeBrickWall(new Position(320, 400));
        brickWall47.addToPanel(panel);

        // create more player
        keyStateHandler = new KeyStateHandler();
        addKeyListener(keyStateHandler);

        playerOne = new PlayerTank(playerOneSpawnPosition, PlayerTankEnum.PLAYER1);

        panel.add(playerOne);
        if (numberOfPlayers == 2) {
            playerTwo = new PlayerTank(playerTwoSpawnPosition, PlayerTankEnum.PLAYER2);
            panel.add(playerTwo);
        }

        for (int i = 0; i < maxActiveTanks; i++) {
            spawnRandomEnemyTank();
        }

        player1ScoreLabel = new JLabel("P1: " + playerOne.getPoint());
        player1ScoreLabel.setForeground(Color.WHITE);
        player1LivesLabel = new JLabel("♥: " + playerOne.getLife());
        player1LivesLabel.setForeground(Color.RED);
        statsPanel.add(player1ScoreLabel);
        statsPanel.add(player1LivesLabel);

        if (numberOfPlayers == 2) {
            player2ScoreLabel = new JLabel("P2: " + playerTwo.getPoint());
            player2ScoreLabel.setForeground(Color.WHITE);
            player2LivesLabel = new JLabel("♥: " + playerTwo.getLife());
            player2LivesLabel.setForeground(Color.RED);

            statsPanel.add(player2ScoreLabel);
            statsPanel.add(player2LivesLabel);
        }

        add(statsPanel);


        respawnTimer = new Timer(3000, e -> {
            respawnPlayer();
            ((Timer) e.getSource()).stop();
        });
        respawnTimer.setRepeats(false);


        enemyRespawnTimer = new Timer(3000, e -> {
            spawnRandomEnemyTank();
            spawnRandomEnemyTank();
            ((Timer) e.getSource()).stop();
        });
        enemyRespawnTimer.setRepeats(false);

        powerUpSpawnTimer = new Timer(powerUpSpawnInterval, e -> {
            spawnPowerUp();
        });
        powerUpSpawnTimer.start();

        setFocusable(true);
        requestFocus();
        setVisible(true);
        setResizable(false);
        startGame();
    }

    private void spawnPowerUp() {
        if (powerUpList.size() > 0) {
            int randomIndex = random.nextInt(powerUpList.size());
            panel.add(powerUpList.get(randomIndex));
            powerUpList.remove(powerUpList.get(randomIndex));
        }
    }

    private void updateDisplays() {
        if (playerOne != null) {
            player1ScoreLabel.setText("P1: " + playerOne.getPoint());
            player1LivesLabel.setText("♥: " + playerOne.getLife());
        }

        if (numberOfPlayers == 2 && playerTwo != null) {
            player2ScoreLabel.setText("P2: " + playerTwo.getPoint());
            player2LivesLabel.setText("♥: " + playerTwo.getLife());
        }
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
                panel.add(playerOne);
                playerOne.updateTierState();
            }
        }

        if (numberOfPlayers == 2 && playerTwo != null && !playerTwo.isActive()) {
            if (playerTwo.getLife() > 0) {
                PlayerTank newPlayerTwo = new PlayerTank(playerTwoSpawnPosition, PlayerTankEnum.PLAYER2);
                newPlayerTwo.setLife(playerTwo.getLife());
                newPlayerTwo.setPoint(playerTwo.getPoint());
                newPlayerTwo.setTier(playerTwo.getTier());

                remove(playerTwo);
                playerTwo = newPlayerTwo;
                panel.add(playerTwo);
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
        gameOverLabel.setBounds(panel.getWidth() / 2 - 150, panel.getHeight() / 2 - 50, 300, 100);
        panel.add(gameOverLabel);

        panel.setComponentZOrder(gameOverLabel, 0);
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

            panel.add(tank);
            repaint();
        }
    }

    private void updateGame() {
        handleInput();
        BulletManager.getInstance().updateBullets(panel);
        updateDisplays();
        checkLevelComplete();
        checkGameOver();


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

    private void checkGameOver() {
        if (isGameOver) {
            return;
        }
        if (numberOfPlayers == 1) {
            if (playerOne.getLife() <= 0) {
                gameOver();
            }
        } else {
            if (playerOne.getLife() <= 0 && playerTwo.getLife() <= 0) {
                gameOver();
            }
        }
        if (homeBase.getHealth() <= 0) {
            gameOver();
        }
    }

    private void checkLevelComplete() {
        if (enemyTanks.isEmpty() && availableTanks.isEmpty()) {
            gameTimer.stop();
            if (respawnTimer != null) respawnTimer.stop();
            if (enemyRespawnTimer != null) enemyRespawnTimer.stop();

            SwingUtilities.invokeLater(() -> {
                GameFrameLevel3 nextLevel = new GameFrameLevel3(numberOfPlayers);
                if (playerOne != null) {
                    nextLevel.setPlayer1State(playerOne.getPoint(), playerOne.getLife(), playerOne.getTier());
                }
                if (playerTwo != null) {
                    nextLevel.setPlayer2State(playerTwo.getPoint(), playerTwo.getLife(), playerTwo.getTier());
                }
                nextLevel.setVisible(true);
                this.dispose();
            });
        }
    }


    public void setPlayer1State(int points, int lives, int tier) {
        if (playerOne != null) {
            playerOne.setPoint(points);
            playerOne.setLife(lives);
            playerOne.setTier(tier);
            playerOne.updateTierState();
        }
    }

    public void setPlayer2State(int points, int lives, int tier) {
        if (playerTwo != null) {
            playerTwo.setPoint(points);
            playerTwo.setLife(lives);
            playerTwo.setTier(tier);
            playerTwo.updateTierState();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }
}