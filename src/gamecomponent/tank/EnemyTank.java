package gamecomponent.tank;

import gamecomponent.Direction;
import gamecomponent.HomeBase;
import gamecomponent.Position;
import gamecomponent.enviroment.Enviroment;


import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class EnemyTank extends Tank {
    private Random random = new Random();

    @Override
    public void checkBounds() {
        int frameWidth = getParent().getWidth();
        int frameHeight = getParent().getHeight();
        int tankWidth = images.get(direction).getWidth(null);
        int tankHeight = images.get(direction).getHeight(null);

        if (position.getX() < 0) {
            position.setX(0);
            changeDirection();
        } else if (position.getX() + tankWidth > frameWidth) {
            position.setX(frameWidth - tankWidth);
            changeDirection();
        }

        if (position.getY() < 0) {
            position.setY(0);
            changeDirection();
        } else if (position.getY() + tankHeight > frameHeight) {
            position.setY(frameHeight - tankHeight);
            changeDirection();
        }
    }

    @Override
    public void handleCollision(Position oldPosition) {
        Component[] collidedComponents = checkCollisions();
        boolean shouldRevertPosition = false;

        for (Component component : collidedComponents) {
            if (component instanceof Enviroment) {
                Enviroment environment = (Enviroment) component;
                if (!environment.isCanPass()) {
                    shouldRevertPosition = true;
                    break;
                }
            }
            else if (component instanceof HomeBase || component instanceof Tank) {
                shouldRevertPosition = true;
                break;
            }
        }

        if (shouldRevertPosition) {
            this.setPosition(oldPosition);
            changeDirection();
        }

        updatePanelPosition();
    }

    private void changeDirection() {
        Direction[] directions = Direction.values();
        direction = directions[random.nextInt(directions.length)];
    }

    public EnemyTank(Position position) {
        super(position);
        this.direction = Direction.D;
        this.numberOfBulletPerShoot = 1;
        loadImages();
        this.setSize(getImageSize());
    }


    @Override
    public void loadImages() {
        this.images.put(Direction.U, new ImageIcon("images/tankU.gif").getImage());
        this.images.put(Direction.D, new ImageIcon("images/tankD.gif").getImage());
        this.images.put(Direction.L, new ImageIcon("images/tankL.gif").getImage());
        this.images.put(Direction.R, new ImageIcon("images/tankR.gif").getImage());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image tankImage = this.images.get(this.direction);
        if (tankImage != null){
            g.drawImage(tankImage, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }
}
