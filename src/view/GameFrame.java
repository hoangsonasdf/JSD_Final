package view;

import gamecomponent.HomeBase;
import gamecomponent.Position;
import gamecomponent.enviroment.BrickWall;
import gamecomponent.enviroment.Tree;
import gamecomponent.inputhandler.PlayerInputHandler;
import gamecomponent.powerup.Grenade;
import gamecomponent.powerup.Helmet;
import gamecomponent.powerup.Star;
import gamecomponent.tank.ArmorTank;
import gamecomponent.tank.BasicTank;
import gamecomponent.tank.EnemyTank;
import gamecomponent.tank.PlayerTank;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class GameFrame extends JFrame {
    private Random random = new Random();
    Position spawnPosition = new Position(100, 100);
    private PlayerTank playerTank;
    private PlayerInputHandler playerInputHandler;
    private List<EnemyTank> enemyTanks = new ArrayList<>();
    private BrickWall brickWall;
    private Grenade grenade;
    private Tree tree;
    private Helmet helmet;
    private Star star;
    private Star star1;
    private Star star2;
    private Star star3;
    private HomeBase homeBase;

    public GameFrame() {
        setLayout(null);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeBase = new HomeBase(new Position(15, 15));
        homeBase.setBounds(homeBase.getPosition().getX(), homeBase.getPosition().getY(), homeBase.getImageSize().width, homeBase.getImageSize().height);
        add(homeBase);

        brickWall = new BrickWall(new Position(200, 200));
        brickWall.setBounds(brickWall.getPosition().getX(), brickWall.getPosition().getY(), brickWall.getImageSize().width, brickWall.getImageSize().height);
        add(brickWall);

        tree = new Tree(new Position(50, 50));
        tree.setBounds(tree.getPosition().getX(), tree.getPosition().getY(), tree.getImageSize().width, tree.getImageSize().height);
        add(tree);

        helmet = new Helmet(new Position(150, 50));
        helmet.setBounds(helmet.getPosition().getX(), helmet.getPosition().getY(), helmet.getImageSize().width, helmet.getImageSize().height);
        add(helmet);

        grenade = new Grenade(new Position(50, 150));
        grenade.setBounds(grenade.getPosition().getX(), grenade.getPosition().getY(), grenade.getImageSize().width, grenade.getImageSize().height);
        add(grenade);

        star = new Star(new Position(123, 342));
        star.setBounds(star.getPosition().getX(), star.getPosition().getY(), star.getImageSize().width, star.getImageSize().height);
        add(star);

        star1 = new Star(new Position(543, 342));
        star1.setBounds(star1.getPosition().getX(), star1.getPosition().getY(), star1.getImageSize().width, star1.getImageSize().height);
        add(star1);

        star2 = new Star(new Position(123, 234));
        star2.setBounds(star2.getPosition().getX(), star2.getPosition().getY(), star2.getImageSize().width, star2.getImageSize().height);
        add(star2);

        star3 = new Star(new Position(546, 213));
        star3.setBounds(star3.getPosition().getX(), star3.getPosition().getY(), star3.getImageSize().width, star3.getImageSize().height);
        add(star3);


        playerTank = new PlayerTank(spawnPosition);
        playerTank.setBounds(playerTank.getPosition().getX(), playerTank.getPosition().getY(), playerTank.getImageSize().width, playerTank.getImageSize().height);
        add(playerTank);

        BasicTank basicTank = new BasicTank(new Position(300, 300));
        basicTank.setBounds(basicTank.getPosition().getX(), basicTank.getPosition().getY(), basicTank.getImageSize().width, basicTank.getImageSize().height);
        add(basicTank);
        enemyTanks.add(basicTank);

        ArmorTank armorTank = new ArmorTank(new Position(400, 350));
        armorTank.setBounds(armorTank.getPosition().getX(), armorTank.getPosition().getY(), armorTank.getImageSize().width, armorTank.getImageSize().height);
        add(armorTank);
        enemyTanks.add(armorTank);

        playerInputHandler = new PlayerInputHandler(playerTank);
        addKeyListener(playerInputHandler);


        setFocusable(true);
        requestFocus();
        setVisible(true);
        setResizable(false);
        startGame();
    }

    public void startGame() {
        Timer gameTimer = new Timer(1000 / 60, e -> {
            updateGame();
        });
        gameTimer.start();
    }

    private void updateGame() {
        if (playerTank != null) {
            playerTank.updateBullets();
        }

        if (!playerTank.isActive()){
            playerTank.setActive(true);
            playerTank.setBounds(spawnPosition.getX(), spawnPosition.getY(), playerTank.getImageSize().width, playerTank.getImageSize().height);
            add(playerTank);
        }

        Iterator<EnemyTank> iterator = enemyTanks.iterator();
        while (iterator.hasNext()) {
            EnemyTank enemyTank = iterator.next();
            if (enemyTank.isActive()) {
                enemyTank.move();
                if (random.nextInt(100) < 5) {
                    enemyTank.attempFire();
                }
                enemyTank.updateBullets();
            } else {
                iterator.remove();
            }
        }

        repaint();
    }
}
