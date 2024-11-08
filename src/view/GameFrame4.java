package view;

import gamecomponent.Direction;
import gamecomponent.HomeBase;
import gamecomponent.Position;
import gamecomponent.enviroment.CompositeBrickWall;
import gamecomponent.enviroment.MetalWall;
import gamecomponent.enviroment.Tree;
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

public class GameFrame4 extends JFrame {
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
    private Star star1;
    private Star star2;
    private HomeBase homeBase;
    private boolean isGameOver = false;
    private Timer enemyRespawnTimer;
    private int maxActiveTanks = 2;
    private Timer respawnTimer;
    private Timer gameTimer;

    public GameFrame4(int numberOfPlayers) {
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
        panel.setSize(640,640);
        panel.setLocation(80,20);
        add(panel);

        // Add home base
        homeBase = new HomeBase(new Position(280, 280));
        panel.add(homeBase);

        // Add Power-ups
        Helmet helmet1 = new Helmet(new Position(520, 80));
        panel.add(helmet1);

        Helmet helmet2 = new Helmet(new Position(80, 520));
        panel.add(helmet2);

        star1 = new Star(new Position(80, 80));
        panel.add(star1);

        star2 = new Star(new Position(520, 520));
        panel.add(star2);

        TankUp tankUp1 = new TankUp(new Position(320,280));
        panel.add(tankUp1);

        TankUp tankUp2 = new TankUp(new Position(280,320));
        panel.add(tankUp2);

        // Add Tanks
        availableTanks.add(new BasicTank(new Position(40, 40)));
        availableTanks.add(new ArmorTank(new Position(560, 40)));
        availableTanks.add(new FastTank(new Position(40, 560)));
        availableTanks.add(new PowerTank(new Position(560, 560)));

        // Add Trees
        Tree tree1 = new Tree(new Position(0, 0));
        panel.add(tree1);
        panel.setComponentZOrder(tree1, 1);

        Tree tree2 = new Tree(new Position(40, 0));
        panel.add(tree2);
        panel.setComponentZOrder(tree2, 1);

        Tree tree3 = new Tree(new Position(80, 0));
        panel.add(tree3);
        panel.setComponentZOrder(tree3, 1);

        Tree tree4 = new Tree(new Position(0, 40));
        panel.add(tree4);
        panel.setComponentZOrder(tree4, 1);

        Tree tree5 = new Tree(new Position(0, 80));
        panel.add(tree5);
        panel.setComponentZOrder(tree5, 1);

        Tree tree6 = new Tree(new Position(520, 0));
        panel.add(tree6);
        panel.setComponentZOrder(tree6, 1);

        Tree tree7 = new Tree(new Position(560, 0));
        panel.add(tree7);
        panel.setComponentZOrder(tree7, 1);

        Tree tree8 = new Tree(new Position(600, 0));
        panel.add(tree8);
        panel.setComponentZOrder(tree8, 1);

        Tree tree9 = new Tree(new Position(600, 40));
        panel.add(tree9);
        panel.setComponentZOrder(tree9, 1);

        Tree tree10 = new Tree(new Position(600, 80));
        panel.add(tree10);
        panel.setComponentZOrder(tree10, 1);

        Tree tree11 = new Tree(new Position(0, 520));
        panel.add(tree11);
        panel.setComponentZOrder(tree11, 1);

        Tree tree12 = new Tree(new Position(0, 560));
        panel.add(tree12);
        panel.setComponentZOrder(tree12, 1);

        Tree tree13 = new Tree(new Position(0, 600));
        panel.add(tree13);
        panel.setComponentZOrder(tree13, 1);

        Tree tree14 = new Tree(new Position(40, 600));
        panel.add(tree14);
        panel.setComponentZOrder(tree14, 1);

        Tree tree15 = new Tree(new Position(80, 600));
        panel.add(tree15);
        panel.setComponentZOrder(tree15, 1);

        Tree tree16 = new Tree(new Position(520, 600));
        panel.add(tree16);
        panel.setComponentZOrder(tree16, 1);

        Tree tree17 = new Tree(new Position(560, 600));
        panel.add(tree17);
        panel.setComponentZOrder(tree17, 1);

        Tree tree18 = new Tree(new Position(600, 600));
        panel.add(tree18);
        panel.setComponentZOrder(tree18, 1);

        Tree tree19 = new Tree(new Position(600, 560));
        panel.add(tree19);
        panel.setComponentZOrder(tree19, 1);

        Tree tree20 = new Tree(new Position(600, 520));
        panel.add(tree20);
        panel.setComponentZOrder(tree20, 1);


        // Add metal walls
        MetalWall mw1 = new MetalWall(new Position(120,0));
        panel.add(mw1);

        MetalWall mw2 = new MetalWall(new Position(240,0));
        panel.add(mw2);

        MetalWall mw3 = new MetalWall(new Position(240,40));
        panel.add(mw3);

        MetalWall mw4 = new MetalWall(new Position(0,120));
        panel.add(mw4);

        MetalWall mw5 = new MetalWall(new Position(360,0));
        panel.add(mw5);

        MetalWall mw6 = new MetalWall(new Position(360,40));
        panel.add(mw6);

        MetalWall mw7 = new MetalWall(new Position(480,0));
        panel.add(mw7);

        MetalWall mw8 = new MetalWall(new Position(600,120));
        panel.add(mw8);

        MetalWall mw9 = new MetalWall(new Position(280,160));
        panel.add(mw9);

        MetalWall mw10 = new MetalWall(new Position(320,160));
        panel.add(mw10);

        MetalWall mw11 = new MetalWall(new Position(280,200));
        panel.add(mw11);

        MetalWall mw12 = new MetalWall(new Position(0,240));
        panel.add(mw12);

        MetalWall mw13 = new MetalWall(new Position(40,240));
        panel.add(mw13);

        MetalWall mw14 = new MetalWall(new Position(560,240));
        panel.add(mw14);

        MetalWall mw15 = new MetalWall(new Position(600,240));
        panel.add(mw15);

        MetalWall mw16 = new MetalWall(new Position(160,280));
        panel.add(mw16);

        MetalWall mw17 = new MetalWall(new Position(400,280));
        panel.add(mw17);

        MetalWall mw18 = new MetalWall(new Position(440,280));
        panel.add(mw18);

        MetalWall mw19 = new MetalWall(new Position(160,320));
        panel.add(mw19);

        MetalWall mw20 = new MetalWall(new Position(200,320));
        panel.add(mw20);

        MetalWall mw21 = new MetalWall(new Position(440,320));
        panel.add(mw21);

        MetalWall mw22 = new MetalWall(new Position(0,360));
        panel.add(mw22);

        MetalWall mw23 = new MetalWall(new Position(40,360));
        panel.add(mw23);

        MetalWall mw24 = new MetalWall(new Position(560,360));
        panel.add(mw24);

        MetalWall mw25 = new MetalWall(new Position(600,360));
        panel.add(mw25);

        MetalWall mw26 = new MetalWall(new Position(320,400));
        panel.add(mw26);

        MetalWall mw27 = new MetalWall(new Position(280,440));
        panel.add(mw27);

        MetalWall mw28 = new MetalWall(new Position(320,440));
        panel.add(mw28);

        MetalWall mw29 = new MetalWall(new Position(0,480));
        panel.add(mw29);

        MetalWall mw30 = new MetalWall(new Position(600,480));
        panel.add(mw30);

        MetalWall mw31 = new MetalWall(new Position(240,560));
        panel.add(mw31);

        MetalWall mw32 = new MetalWall(new Position(360,560));
        panel.add(mw32);

        MetalWall mw33 = new MetalWall(new Position(240,600));
        panel.add(mw33);

        MetalWall mw34 = new MetalWall(new Position(360,600));
        panel.add(mw34);

        MetalWall mw35 = new MetalWall(new Position(120,600));
        panel.add(mw35);

        MetalWall mw36 = new MetalWall(new Position(480,600));
        panel.add(mw36);

        // Add brick walls
        CompositeBrickWall brickWall1 = new CompositeBrickWall(new Position(120,80));
        brickWall1.addToPanel(panel);

        CompositeBrickWall brickWall2 = new CompositeBrickWall(new Position(120,120));
        brickWall2.addToPanel(panel);

        CompositeBrickWall brickWall3 = new CompositeBrickWall(new Position(80,120));
        brickWall3.addToPanel(panel);

        CompositeBrickWall brickWall4 = new CompositeBrickWall(new Position(480,80));
        brickWall4.addToPanel(panel);

        CompositeBrickWall brickWall5 = new CompositeBrickWall(new Position(480,120));
        brickWall5.addToPanel(panel);

        CompositeBrickWall brickWall6 = new CompositeBrickWall(new Position(520,120));
        brickWall6.addToPanel(panel);

        CompositeBrickWall brickWall7 = new CompositeBrickWall(new Position(240,240));
        brickWall7.addToPanel(panel);

        CompositeBrickWall brickWall8 = new CompositeBrickWall(new Position(360,240));
        brickWall8.addToPanel(panel);

        CompositeBrickWall brickWall9 = new CompositeBrickWall(new Position(240,360));
        brickWall9.addToPanel(panel);

        CompositeBrickWall brickWall10 = new CompositeBrickWall(new Position(360,360));
        brickWall10.addToPanel(panel);

        CompositeBrickWall brickWall11 = new CompositeBrickWall(new Position(80,480));
        brickWall11.addToPanel(panel);

        CompositeBrickWall brickWall12 = new CompositeBrickWall(new Position(120,480));
        brickWall12.addToPanel(panel);

        CompositeBrickWall brickWall13 = new CompositeBrickWall(new Position(120,520));
        brickWall13.addToPanel(panel);

        CompositeBrickWall brickWall14 = new CompositeBrickWall(new Position(480,480));
        brickWall14.addToPanel(panel);

        CompositeBrickWall brickWall15 = new CompositeBrickWall(new Position(520,480));
        brickWall15.addToPanel(panel);

        CompositeBrickWall brickWall16 = new CompositeBrickWall(new Position(480,520));
        brickWall16.addToPanel(panel);




        // Create more player
        keyStateHandler = new KeyStateHandler();
        addKeyListener(keyStateHandler);

        playerOne = new PlayerTank(playerOneSpawnPosition, PlayerTankEnum.PLAYER1);
        panel.add(playerOne);

        if (numberOfPlayers == 2){
            playerTwo = new PlayerTank(playerTwoSpawnPosition, PlayerTankEnum.PLAYER2);
            panel.add(playerTwo);
        }

        spawnRandomEnemyTank();
        spawnRandomEnemyTank();

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

    private void respawnPlayer() {
        // Respawn playerOne if they have lives left
        if (playerOne != null && !playerOne.isActive() && playerOne.getLife() > 0) {
            playerOne.setLife(playerOne.getLife() - 1); // Reduce life count by 1 before respawn
            PlayerTank newPlayerOne = new PlayerTank(playerOneSpawnPosition, PlayerTankEnum.PLAYER1);
            newPlayerOne.setLife(playerOne.getLife());
            newPlayerOne.setPoint(playerOne.getPoint());
            newPlayerOne.setTier(playerOne.getTier());

            remove(playerOne);
            playerOne = newPlayerOne;
            panel.add(playerOne);
            playerOne.updateTierState();
        }

        // Respawn playerTwo if they have lives left (for two-player mode)
        if (numberOfPlayers == 2 && playerTwo != null && !playerTwo.isActive() && playerTwo.getLife() > 0) {
            playerTwo.setLife(playerTwo.getLife() - 1); // Reduce life count by 1 before respawn
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

    private void startGame() {
        gameTimer = new Timer(1000 / 60, e -> {
            updateGame();
        });
        gameTimer.start();
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
        gameOverLabel.setBounds(panel.getWidth()/2 - 150, panel.getHeight()/2 - 50, 300, 100);
        panel.add(gameOverLabel);

        panel.setComponentZOrder(gameOverLabel,0);
        repaint();
    }

    private void updateGame() {
        handleInput();
        BulletManager.getInstance().updateBullets(panel);
        if (isGameOver) {
            return;
        }
        // Check for game over conditions
        if (homeBase.getHealth() <= 0 ||
                (playerOne.getLife() <= 0 && (numberOfPlayers == 1 || playerTwo.getLife() <= 0))) {
            gameOver();
            return;
        }

        // Handle respawn if players are inactive and have lives left
        if ((!playerOne.isActive() || (numberOfPlayers == 2 && !playerTwo.isActive())) &&
                !respawnTimer.isRunning()) {
            respawnTimer.start();
        }

        // Update enemy tanks and respawn if inactive
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

    @Override
    public void dispose() {
        super.dispose();
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }

}
