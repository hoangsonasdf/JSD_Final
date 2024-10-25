package gamecomponent.tank;

import gamecomponent.Direction;
import gamecomponent.HomeBase;
import gamecomponent.Position;
import gamecomponent.enviroment.Enviroment;
import gamecomponent.enviroment.MetalWall;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bullet extends JPanel {
    private Position position;
    private Direction direction;
    private int speed;
    private boolean isActive;
    private Tank shotBy;
    private Map<Direction, Image> images = new HashMap<>();

    public Bullet(Tank shotBy) {
        this.shotBy = shotBy;
        this.direction = shotBy.getDirection();
        this.speed = shotBy.getBulletSpeed();
        this.isActive = true;
        setOpaque(false);
        loadImages();
        setSize(getImageSize());
    }

    public void loadImages() {
        this.images.put(Direction.U, new ImageIcon("images/bulletU.gif").getImage());
        this.images.put(Direction.D, new ImageIcon("images/bulletD.gif").getImage());
        this.images.put(Direction.L, new ImageIcon("images/bulletL.gif").getImage());
        this.images.put(Direction.R, new ImageIcon("images/bulletR.gif").getImage());
    }

    public void move() {
        switch (direction) {
            case U:
                position.setY(position.getY() - speed);
                break;
            case D:
                position.setY(position.getY() + speed);
                break;
            case L:
                position.setX(position.getX() - speed);
                break;
            case R:
                position.setX(position.getX() + speed);
                break;
        }
        handleCollision();
        updatePanelPosition();
    }

    private void handleCollision() {
        Component collidedComponent = checkCollision();


        if (collidedComponent instanceof Enviroment) {
            Enviroment enviroment = (Enviroment) collidedComponent;
            if (enviroment.isCanDestroy()) {
                this.isActive = false;
                getParent().remove(enviroment);
                System.out.println(enviroment.getPosition());
            }
            if (enviroment instanceof MetalWall){
                this.isActive = false;
            }
        }

        if (this.shotBy instanceof EnemyTank && collidedComponent instanceof HomeBase) {
            this.isActive = false;
            HomeBase homeBase = (HomeBase) collidedComponent;
            homeBase.setHealth(homeBase.getHealth() - 1);
        }

        if (this.shotBy instanceof PlayerTank && collidedComponent instanceof EnemyTank) {
            this.isActive = false;
            EnemyTank enemyTank = (EnemyTank) collidedComponent;
            enemyTank.setHealth(enemyTank.getHealth() - 1);
            if (enemyTank.getHealth() == 0) {
                enemyTank.explode();
            }
        }

        if (this.shotBy instanceof EnemyTank && collidedComponent instanceof PlayerTank) {
            this.isActive = false;
            PlayerTank playerTank = (PlayerTank) collidedComponent;
            if (playerTank.isShield()) {
                playerTank.setShield(false);
            } else {
                playerTank.setHealth(playerTank.getHealth() - 1);
                if (playerTank.getHealth() == 0) {
                    playerTank.setLife(playerTank.getLife() - 1);
                    playerTank.explode();
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Image bulletImage = this.images.get(this.direction);
        if (bulletImage != null) {
            g2d.drawImage(bulletImage, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }

    private void updatePanelPosition() {
        Dimension size = getImageSize();
        setBounds(position.getX(), position.getY(), size.width, size.height);
        getParent().repaint();
    }

    public void checkBounds() {
        int frameWidth = getParent().getWidth();
        int frameHeight = getParent().getHeight();
        int tankWidth = getImageSize().width;
        int tankHeight = getImageSize().height;

        if (position.getX() < 0 || position.getX() + tankWidth > frameWidth ||
                position.getY() < 0 || position.getY() + tankHeight > frameHeight) {
            this.isActive = false;
            getParent().remove(this);
        }
    }

    public Dimension getImageSize() {
        Image bulletImage = this.images.get(this.direction);
        return new Dimension(bulletImage.getWidth(null), bulletImage.getHeight(null));
    }

    public boolean checkCollisionWith(Component other) {
        if (other == this) return false;
        Rectangle tankBounds = new Rectangle(
                position.getX(),
                position.getY(),
                getImageSize().width,
                getImageSize().height
        );
        Rectangle otherBounds = new Rectangle(
                other.getX(),
                other.getY(),
                other.getWidth(),
                other.getHeight()
        );

        return tankBounds.intersects(otherBounds);
    }

    public Component checkCollision() {
        Container parent = getParent();
        if (parent != null) {
            for (Component comp : parent.getComponents()) {
                if (comp != this && checkCollisionWith(comp)) {
                    return comp;
                }
            }
        }
        return null;
    }
}
