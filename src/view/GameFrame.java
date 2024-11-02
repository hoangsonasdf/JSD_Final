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


public class GameFrame extends JFrame {
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


    public void numPlayersException (int numberOfPlayers){
        if (numberOfPlayers < 1 || numberOfPlayers > 2) {
            throw new IllegalArgumentException("Number of players must be 1 or 2");
        }
    }

    public Component modifyPanel(JPanel panel){
        this.panel = panel;


        panel.setLayout(null);
        panel.setBackground(Color.black);
        panel.setSize(640,640);
        panel.setLocation(80,20);

        homeBase = new HomeBase(new Position(0, 600));
        panel.add(homeBase);

        // Add Bricks Section
        BrickWall brickWall1 = new BrickWall(new Position(120,0));
        panel.add(brickWall1);

        CompositeBrickWall brickWall2 = new CompositeBrickWall(new Position(320,0));
        brickWall2.addToPanel(panel);

        CompositeBrickWall brickWall3 = new CompositeBrickWall(new Position(440,0));
        brickWall3.addToPanel(panel);

        CompositeBrickWall brickWall4 = new CompositeBrickWall(new Position(520,0));
        brickWall4.addToPanel(panel);

        CompositeBrickWall brickWall5 = new CompositeBrickWall(new Position(520,40));
        brickWall5.addToPanel(panel);

        CompositeBrickWall brickWall6 = new CompositeBrickWall(new Position(120,80));
        brickWall6.addToPanel(panel);

        CompositeBrickWall brickWall7 = new CompositeBrickWall(new Position(280,80));
        brickWall7.addToPanel(panel);

        CompositeBrickWall brickWall8 = new CompositeBrickWall(new Position(520,80));
        brickWall8.addToPanel(panel);

        CompositeBrickWall brickWall9 = new CompositeBrickWall(new Position(560,80));
        brickWall9.addToPanel(panel);

        CompositeBrickWall brickWall10 = new CompositeBrickWall(new Position(600,80));
        brickWall10.addToPanel(panel);

        CompositeBrickWall brickWall11 = new CompositeBrickWall(new Position(0,120));
        brickWall11.addToPanel(panel);

        CompositeBrickWall brickWall12 = new CompositeBrickWall(new Position(240,120));
        brickWall12.addToPanel(panel);

        CompositeBrickWall brickWall13 = new CompositeBrickWall(new Position(320,120));
        brickWall13.addToPanel(panel);

        CompositeBrickWall brickWall14 = new CompositeBrickWall(new Position(480,120));
        brickWall14.addToPanel(panel);

        CompositeBrickWall brickWall15 = new CompositeBrickWall(new Position(280,160));
        brickWall15.addToPanel(panel);

        CompositeBrickWall brickWall16 = new CompositeBrickWall(new Position(400,160));
        brickWall16.addToPanel(panel);

        CompositeBrickWall brickWall17 = new CompositeBrickWall(new Position(600,160));
        brickWall17.addToPanel(panel);

        CompositeBrickWall brickWall18 = new CompositeBrickWall(new Position(400,200));
        brickWall18.addToPanel(panel);

        CompositeBrickWall brickWall19 = new CompositeBrickWall(new Position(440,200));
        brickWall19.addToPanel(panel);

        CompositeBrickWall brickWall20 = new CompositeBrickWall(new Position(120,240));
        brickWall20.addToPanel(panel);

        CompositeBrickWall brickWall21 = new CompositeBrickWall(new Position(320,240));
        brickWall21.addToPanel(panel);

        CompositeBrickWall brickWall22 = new CompositeBrickWall(new Position(80,280));
        brickWall22.addToPanel(panel);

        CompositeBrickWall brickWall23 = new CompositeBrickWall(new Position(160,280));
        brickWall23.addToPanel(panel);

        CompositeBrickWall brickWall24 = new CompositeBrickWall(new Position(360,280));
        brickWall24.addToPanel(panel);

        CompositeBrickWall brickWall25 = new CompositeBrickWall(new Position(480,280));
        brickWall25.addToPanel(panel);

        CompositeBrickWall brickWall26 = new CompositeBrickWall(new Position(600,280));
        brickWall26.addToPanel(panel);

        CompositeBrickWall brickWall27 = new CompositeBrickWall(new Position(120,320));
        brickWall27.addToPanel(panel);

        CompositeBrickWall brickWall28 = new CompositeBrickWall(new Position(440,320));
        brickWall28.addToPanel(panel);

        CompositeBrickWall brickWall29 = new CompositeBrickWall(new Position(520,320));
        brickWall29.addToPanel(panel);

        CompositeBrickWall brickWall30 = new CompositeBrickWall(new Position(480,360));
        brickWall30.addToPanel(panel);

        CompositeBrickWall brickWall31 = new CompositeBrickWall(new Position(320,440));
        brickWall31.addToPanel(panel);

        CompositeBrickWall brickWall32 = new CompositeBrickWall(new Position(0,440));
        brickWall32.addToPanel(panel);


        CompositeBrickWall brickWall33 = new CompositeBrickWall(new Position(280,480));
        brickWall33.addToPanel(panel);

        CompositeBrickWall brickWall34 = new CompositeBrickWall(new Position(360,480));
        brickWall34.addToPanel(panel);

        CompositeBrickWall brickWall35 = new CompositeBrickWall(new Position(600,480));
        brickWall35.addToPanel(panel);

        CompositeBrickWall brickWall36 = new CompositeBrickWall(new Position(320,520));
        brickWall36.addToPanel(panel);

        CompositeBrickWall brickWall37 = new CompositeBrickWall(new Position(480,520));
        brickWall37.addToPanel(panel);

        CompositeBrickWall brickWall38 = new CompositeBrickWall(new Position(160,600));
        brickWall38.addToPanel(panel);

        CompositeBrickWall brickWall39 = new CompositeBrickWall(new Position(480,600));
        brickWall39.addToPanel(panel);

//        brickWall1 = new CompositeBrickWall(new Position(0, 80));
//        brickWall1.addToPanel(panel);

        // Add Metal Section
        MetalWall mw1 = new MetalWall(new Position(160,40));
        panel.add(mw1);

        MetalWall mw2 = new MetalWall(new Position(200,40));
        panel.add(mw2);

        MetalWall mw3 = new MetalWall(new Position(320,40));
        panel.add(mw3);

        MetalWall mw4 = new MetalWall(new Position(360,40));
        panel.add(mw4);

        MetalWall mw5 = new MetalWall(new Position(440,40));
        panel.add(mw5);

        MetalWall mw6 = new MetalWall(new Position(200,80));
        panel.add(mw6);

        MetalWall mw7 = new MetalWall(new Position(360,80));
        panel.add(mw7);

        MetalWall mw8 = new MetalWall(new Position(440,80));
        panel.add(mw8);

        MetalWall mw9 = new MetalWall(new Position(40,120));
        panel.add(mw9);

        MetalWall mw10 = new MetalWall(new Position(200,120));
        panel.add(mw10);

        MetalWall mw11 = new MetalWall(new Position(40,160));
        panel.add(mw11);

        MetalWall mw12 = new MetalWall(new Position(200,160));
        panel.add(mw12);

        MetalWall mw13 = new MetalWall(new Position(240,160));
        panel.add(mw13);

        MetalWall mw14 = new MetalWall(new Position(360,160));
        panel.add(mw14);

        MetalWall mw15 = new MetalWall(new Position(520,160));
        panel.add(mw15);

        MetalWall mw16 = new MetalWall(new Position(560,160));
        panel.add(mw16);

        MetalWall mw17 = new MetalWall(new Position(40,200));
        panel.add(mw17);

        MetalWall mw18 = new MetalWall(new Position(320,200));
        panel.add(mw18);

        MetalWall mw19 = new MetalWall(new Position(440,240));
        panel.add(mw19);

        MetalWall mw20 = new MetalWall(new Position(520,240));
        panel.add(mw20);

        MetalWall mw21 = new MetalWall(new Position(560,240));
        panel.add(mw21);

        MetalWall mw22 = new MetalWall(new Position(40,280));
        panel.add(mw22);

        MetalWall mw23 = new MetalWall(new Position(400,280));
        panel.add(mw23);

        MetalWall mw24 = new MetalWall(new Position(560,280));
        panel.add(mw24);

        MetalWall mw25 = new MetalWall(new Position(40,320));
        panel.add(mw25);

        MetalWall mw26 = new MetalWall(new Position(200,320));
        panel.add(mw26);

        MetalWall mw27 = new MetalWall(new Position(560,320));
        panel.add(mw27);

        MetalWall mw28 = new MetalWall(new Position(40,360));
        panel.add(mw28);

        MetalWall mw29 = new MetalWall(new Position(80,360));
        panel.add(mw29);

        MetalWall mw30 = new MetalWall(new Position(160,360));
        panel.add(mw30);

        MetalWall mw31 = new MetalWall(new Position(280,400));
        panel.add(mw31);

        MetalWall mw32 = new MetalWall(new Position(560,400));
        panel.add(mw32);

        MetalWall mw33 = new MetalWall(new Position(40,440));
        panel.add(mw33);

        MetalWall mw34 = new MetalWall(new Position(80,440));
        panel.add(mw34);

        MetalWall mw35 = new MetalWall(new Position(240,440));
        panel.add(mw35);

        MetalWall mw36 = new MetalWall(new Position(360,440));
        panel.add(mw36);

        MetalWall mw37 = new MetalWall(new Position(400,440));
        panel.add(mw37);

        MetalWall mw38 = new MetalWall(new Position(560,440));
        panel.add(mw38);

        MetalWall mw39 = new MetalWall(new Position(400,480));
        panel.add(mw39);

        MetalWall mw40 = new MetalWall(new Position(560,480));
        panel.add(mw40);

        MetalWall mw41 = new MetalWall(new Position(160,520));
        panel.add(mw41);

        MetalWall mw42 = new MetalWall(new Position(240,520));
        panel.add(mw42);

        MetalWall mw43 = new MetalWall(new Position(400,520));
        panel.add(mw43);

        MetalWall mw44 = new MetalWall(new Position(160,560));
        panel.add(mw44);

        MetalWall mw45 = new MetalWall(new Position(240,560));
        panel.add(mw45);

        MetalWall mw46 = new MetalWall(new Position(280,560));
        panel.add(mw46);

        MetalWall mw47 = new MetalWall(new Position(400,560));
        panel.add(mw47);

        MetalWall mw48 = new MetalWall(new Position(440,560));
        panel.add(mw48);

        // Add River Section
        River r1 = new River(new Position(80,80));
        panel.add(r1);

        River r2 = new River(new Position(80,120));
        panel.add(r2);

        River r3 = new River(new Position(120,120));
        panel.add(r3);

        River r4 = new River(new Position(120,160));
        panel.add(r4);

        River r5 = new River(new Position(160,160));
        panel.add(r5);

        River r6 = new River(new Position(160,200));
        panel.add(r6);

        River r7 = new River(new Position(200,200));
        panel.add(r7);

        River r8 = new River(new Position(200,240));
        panel.add(r8);

        River r9 = new River(new Position(240,240));
        panel.add(r9);

        River r10 = new River(new Position(360,360));
        panel.add(r10);

        River r11 = new River(new Position(400,360));
        panel.add(r11);

        River r12 = new River(new Position(400,400));
        panel.add(r12);

        River r13 = new River(new Position(440,400));
        panel.add(r13);

        River r14 = new River(new Position(440,440));
        panel.add(r14);

        River r15 = new River(new Position(480,440));
        panel.add(r15);

        River r16 = new River(new Position(480,480));
        panel.add(r16);

        River r17 = new River(new Position(520,480));
        panel.add(r17);

        River r18 = new River(new Position(520,520));
        panel.add(r18);

        // Add Tree Section
        Tree tree1 = new Tree(new Position(0, 0));
        panel.add(tree1);
        panel.setComponentZOrder(tree1, 1);

        Tree tree2 = new Tree(new Position(240, 40));
        panel.add(tree2);
        panel.setComponentZOrder(tree2, 1);

        Tree tree3 = new Tree(new Position(280, 40));
        panel.add(tree3);
        panel.setComponentZOrder(tree3, 1);

        Tree tree4 = new Tree(new Position(40, 240));
        panel.add(tree4);
        panel.setComponentZOrder(tree4, 1);

        Tree tree5 = new Tree(new Position(280, 240));
        panel.add(tree5);
        panel.setComponentZOrder(tree5, 1);

        Tree tree6 = new Tree(new Position(240, 280));
        panel.add(tree6);
        panel.setComponentZOrder(tree6, 1);

        Tree tree7 = new Tree(new Position(360, 320));
        panel.add(tree7);
        panel.setComponentZOrder(tree7, 1);

        Tree tree8 = new Tree(new Position(320, 360));
        panel.add(tree8);
        panel.setComponentZOrder(tree8, 1);

        Tree tree9 = new Tree(new Position(560, 360));
        panel.add(tree9);
        panel.setComponentZOrder(tree9, 1);

        Tree tree10 = new Tree(new Position(320, 560));
        panel.add(tree10);
        panel.setComponentZOrder(tree10, 1);

        Tree tree11 = new Tree(new Position(360, 560));
        panel.add(tree11);
        panel.setComponentZOrder(tree11, 1);

        Tree tree12 = new Tree(new Position(600, 600));
        panel.add(tree12);
        panel.setComponentZOrder(tree12, 1);

        // Add Power Section

        grenade = new Grenade(new Position(560, 40));
        panel.add(grenade);

        Helmet helmet1 = new Helmet(new Position(480, 320));
        panel.add(helmet1);

        Helmet helmet2 = new Helmet(new Position(120, 280));
        panel.add(helmet2);

        Helmet helmet3 = new Helmet(new Position(440, 600));
        panel.add(helmet3);

        star = new Star(new Position(0, 160));
        panel.add(star);

        star1 = new Star(new Position(280, 120));
        panel.add(star1);

        star2 = new Star(new Position(320, 480));
        panel.add(star2);

        TankUp tankUp = new TankUp(new Position(160,120));
        panel.add(tankUp);

        TankUp tankUp2 = new TankUp(new Position(240,360));
        panel.add(tankUp2);

        TankUp tankUp3 = new TankUp(new Position(440,480));
        panel.add(tankUp3);


        keyStateHandler = new KeyStateHandler();
        addKeyListener(keyStateHandler);

        playerOne = new PlayerTank(playerOneSpawnPosition, PlayerTankEnum.PLAYER1);

        panel.add(playerOne);
        if (numberOfPlayers == 2){
            playerTwo = new PlayerTank(playerTwoSpawnPosition, PlayerTankEnum.PLAYER2);
            panel.add(playerTwo);
        }
        availableTanks.add(new BasicTank(new Position(280, 320)));
        availableTanks.add(new ArmorTank(new Position(560, 600)));
        availableTanks.add(new FastTank(new Position(0, 40)));
        availableTanks.add(new PowerTank(new Position(280, 600)));
        return panel;
    }

    public GameFrame(int numberOfPlayers) {

//        if (numberOfPlayers < 1 || numberOfPlayers > 2) {
//            throw new IllegalArgumentException("Number of players must be 1 or 2");
//        }

        numPlayersException(numberOfPlayers);
        this.numberOfPlayers = numberOfPlayers;

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

        spawnRandomEnemyTank();
        spawnRandomEnemyTank();

        setRespawnTimer();
        setEnemyRespawnTimer();

//        setFocusable(true);
//        requestFocus();
//        setVisible(true);
//        setResizable(false);
        startGame();
    }

    public void setRespawnTimer(){
        respawnTimer = new Timer(3000, e -> {
            respawnPlayer();
            ((Timer)e.getSource()).stop();
        });
        respawnTimer.setRepeats(false);
    }

    public void setEnemyRespawnTimer(){
        enemyRespawnTimer = new Timer(3000, e -> {
            spawnRandomEnemyTank();
            ((Timer) e.getSource()).stop();
        });
        enemyRespawnTimer.setRepeats(false);
    }

    public void handleInput() {
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

    public void respawnPlayer() {
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
                PlayerTank newPlayerTwo = new PlayerTank(playerTwoSpawnPosition,PlayerTankEnum.PLAYER2);
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

    public void gameOver() {
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

    public void startGame() {
        gameTimer = new Timer(1000 / 60, e -> {
            updateGame();
        });
        gameTimer.start();
    }

    public void spawnRandomEnemyTank() {
        if (!availableTanks.isEmpty() && enemyTanks.size() < maxActiveTanks) {

            int randomIndex = random.nextInt(availableTanks.size());
            EnemyTank tank = availableTanks.get(randomIndex);

            enemyTanks.add(tank);
            availableTanks.remove(randomIndex);

            panel.add(tank);
            repaint();
        }
    }

    public void updateGame() {
        handleInput();
        BulletManager.getInstance().updateBullets(panel);
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
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }
}