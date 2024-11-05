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
        Component[] collidedComponents = checkCollisions();
        if (collidedComponents.length > 0) {
            if (collidedComponents[0] instanceof Enviroment) {
                Enviroment enviroment = (Enviroment) collidedComponents[0];
                if (!enviroment.isCanBulletPass()) {
                    this.isActive = false;
                }
                if (enviroment.isCanDestroy()) {
                    enviroment.destroy();
                }
            }
        }
        for (Component component : collidedComponents) {
            if (this.shotBy instanceof EnemyTank && component instanceof HomeBase) {
                this.isActive = false;
                HomeBase homeBase = (HomeBase) component;
                homeBase.setHealth(homeBase.getHealth() - 1);
            }

            if (this.shotBy instanceof PlayerTank && component instanceof EnemyTank) {
                this.isActive = false;
                EnemyTank enemyTank = (EnemyTank) component;
                enemyTank.setHealth(enemyTank.getHealth() - 1);
                if (enemyTank.getHealth() == 0) {
                    shotBy.setPoint(shotBy.getPoint() + enemyTank.getPoint());
                    enemyTank.explode();
                }
            }

            if (this.shotBy instanceof EnemyTank && component instanceof PlayerTank) {
                this.isActive = false;
                PlayerTank playerTank = (PlayerTank) component;
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

    protected Component[] checkCollisions() {
        Container parent = getParent();
        if (parent == null) return new Component[0];

        Rectangle tankBounds = new Rectangle(
                position.getX(),
                position.getY(),
                getImageSize().width,
                getImageSize().height
        );

        java.util.List<Component> collisions = new java.util.ArrayList<>();

        for (Component comp : parent.getComponents()) {
            if (comp == this) continue;

            Rectangle compBounds = new Rectangle(
                    comp.getX(),
                    comp.getY(),
                    comp.getWidth(),
                    comp.getHeight()
            );

            if (tankBounds.intersects(compBounds)) {
                collisions.add(comp);
            }
        }

        return collisions.toArray(new Component[0]);
    }
}
