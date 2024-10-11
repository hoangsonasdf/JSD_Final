package view;

import gamecomponent.HomeBase;
import gamecomponent.Position;
import gamecomponent.enviroment.BrickWall;
import gamecomponent.enviroment.Tree;
import gamecomponent.powerup.Grenade;
import gamecomponent.powerup.Helmet;
import gamecomponent.tank.BasicTank;
import gamecomponent.tank.PlayerTank;

import javax.swing.*;

public class GameFrame extends JFrame {
    private PlayerTank playerTank;
    private BasicTank basicTank;

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

        basicTank = new BasicTank(new Position(300, 300));
        basicTank.setBounds(basicTank.getPosition().getX(), basicTank.getPosition().getY(), basicTank.getImageSize().width, basicTank.getImageSize().height);
        add(basicTank);


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
        basicTank.move();
    }
}
