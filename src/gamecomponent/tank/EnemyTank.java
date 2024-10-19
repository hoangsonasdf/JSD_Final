package gamecomponent.tank;

import gamecomponent.HomeBase;
import gamecomponent.Position;
import gamecomponent.enviroment.Enviroment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.swing.*;
import java.awt.*;
import java.util.Random;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnemyTank extends Tank {
    private Random random = new Random();

    @Override
    public void move() {
        Position oldPosition = new Position(position.getX(), position.getY());
        switch (direction) {
            case U:
                position.setY(position.getY() - movementSpeed);
                break;
            case D:
                position.setY(position.getY() + movementSpeed);
                break;
            case L:
                position.setX(position.getX() - movementSpeed);
                 break;
            case R:
                position.setX(position.getX() + movementSpeed);
                break;
        }
        handleCollision(oldPosition);
        checkBounds();
        updatePanelPosition();
    }
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
        Component collidedComponent = checkCollision();
        boolean shouldRevertPosition = false;

        if (collidedComponent != null) {
            if (collidedComponent instanceof Enviroment) {
                Enviroment environment = (Enviroment) collidedComponent;
                if (!environment.isCanPass()) {
                    shouldRevertPosition = true;
                }
            }
            if (collidedComponent instanceof HomeBase){
                shouldRevertPosition = true;
            }
            if (collidedComponent instanceof Tank) {
                shouldRevertPosition = true;
            }
        }

        if (shouldRevertPosition) {
            this.setPosition(oldPosition);
            changeDirection();
        }
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

}
