package view;

import gamecomponent.HomeBase;
import gamecomponent.Position;
import gamecomponent.enviroment.BrickWall;
import gamecomponent.enviroment.Tree;
import gamecomponent.powerup.Grenade;
import gamecomponent.powerup.Helmet;
import gamecomponent.tank.ArmorTank;
import gamecomponent.tank.BasicTank;
import gamecomponent.tank.EnemyTank;
import gamecomponent.tank.PlayerTank;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GameFrame extends JFrame {
    private PlayerTank playerTank;
    private List<EnemyTank> enemyTanks = new ArrayList<>();
    private BrickWall brickWall;
    private Grenade grenade;
    private Tree tree;
    private Helmet helmet;
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

        playerTank = new PlayerTank(new Position(100, 100));
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


        setVisible(true);
        setResizable(false);
        startGame();
    }

    public void startGame() {
        Timer gameTimer = new Timer(1000 / 60, e -> {
            updateGame();
            repaint();
        });
        gameTimer.start();
    }

    private void updateGame() {
        playerTank.updateBullets();
        Iterator<EnemyTank> iterator = enemyTanks.iterator();
        while (iterator.hasNext()) {
            EnemyTank enemyTank = iterator.next();
            if (enemyTank.isActive()) {
                enemyTank.move();
            } else {
                this.remove(enemyTank);
                iterator.remove();
            }
        }
    }
}
