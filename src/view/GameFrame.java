package view;

import gamecomponent.HomeBase;
import gamecomponent.Position;
import gamecomponent.enviroment.CompositeBrickWall;
import gamecomponent.enviroment.Tree;
import gamecomponent.inputhandler.PlayerInputHandler;
import gamecomponent.powerup.Grenade;
import gamecomponent.powerup.Helmet;
import gamecomponent.powerup.Star;
import gamecomponent.tank.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class GameFrame extends JFrame {
    private Random random = new Random();
    Position spawnPosition = new Position(100, 100);
    private PlayerTank playerTank;
    private PlayerInputHandler playerInputHandler;
    private List<EnemyTank> enemyTanks = new ArrayList<>();  // Tank đang trong game
    private List<EnemyTank> availableTanks = new ArrayList<>();  // Tank chưa xuất hiện
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
    private int maxEnemyTanks = 4;
    private int maxActiveTanks = 2;
    private Timer respawnTimer;
    private Timer gameTimer;

    public GameFrame() {
        setLayout(null);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeBase = new HomeBase(new Position(15, 15));
        add(homeBase);


        brickWall1 = new CompositeBrickWall(new Position(657, 143));
        brickWall1.addToFrame(this);

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


        playerTank = new PlayerTank(spawnPosition);
        add(playerTank);

        availableTanks.add(new BasicTank(new Position(300, 300)));
        availableTanks.add(new ArmorTank(new Position(400, 350)));
        availableTanks.add(new FastTank(new Position(500, 400)));
        availableTanks.add(new PowerTank(new Position(200, 500)));

        spawnRandomEnemyTank();
        spawnRandomEnemyTank();

        playerInputHandler = new PlayerInputHandler(playerTank);
        addKeyListener(playerInputHandler);

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

    private void respawnPlayer() {
        if (playerTank.getLife() > 0) {
            PlayerTank newPlayerTank = new PlayerTank(spawnPosition);
            newPlayerTank.setLife(playerTank.getLife());
            newPlayerTank.setPoint(playerTank.getPoint());
            newPlayerTank.setTier(playerTank.getTier());

            playerTank = newPlayerTank;
            add(playerTank);

            playerInputHandler.setPlayerTank(playerTank);

            requestFocus();
            repaint();
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
            // Chọn ngẫu nhiên 1 tank từ availableTanks
            int randomIndex = random.nextInt(availableTanks.size());
            EnemyTank tank = availableTanks.get(randomIndex);

            // Thêm tank vào danh sách đang hoạt động
            enemyTanks.add(tank);
            availableTanks.remove(randomIndex);

            // Thêm tank vào frame
            add(tank);
            repaint();
        }
    }

    private void updateGame() {
        if (isGameOver) {
            return;
        }
        if (homeBase.getHealth() <= 0){
            gameOver();
        }

        if (playerTank != null) {
            playerTank.updateBullets();
            if (!playerTank.isActive() && !respawnTimer.isRunning()) {
                if (playerTank.getLife() <= 0) {
                    gameOver();
                } else {
                    respawnTimer.start();
                }
            }
        }

        Iterator<EnemyTank> iterator = enemyTanks.iterator();
        while (iterator.hasNext()) {
            EnemyTank enemyTank = iterator.next();
            enemyTank.updateBullets();
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
}
