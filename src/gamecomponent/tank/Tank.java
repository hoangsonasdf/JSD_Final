package gamecomponent.tank;

import gamecomponent.Direction;
import gamecomponent.Position;
import manager.BulletManager;
import ultil.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public abstract class Tank extends JPanel {
    protected Direction direction;
    protected Position position;
    protected int point;
    protected int health;
    protected int movementSpeed;
    protected int bulletSpeed;
    protected int numberOfBulletPerShoot;
    protected Map<Direction, Image> images = new HashMap<>();
    private boolean isActive;
    private boolean isShooting;
    private long lastFireTime = 0;
    private static final long FIRE_COOLDOWN = 1000;


    public Tank(Position position) {
        this.position = position;
        this.isActive = true;
        setOpaque(false);
        setLayout(null);
        setBounds(this.position.getX(), this.position.getY(), this.getImageSize().width, this.getImageSize().height);
    }

    public void move(){
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

    public abstract void loadImages();

    public Dimension getImageSize() {
        return new Dimension(40, 40);
    }

    protected void updatePanelPosition() {
        setBounds(position.getX(), position.getY(), getWidth(), getHeight());
        getParent().repaint();
    }

    public abstract void checkBounds();

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

    public void explode() {
        this.isActive = false;
        if (getParent() != null) {
            Container parent = getParent();
            ExplosionEffect explosionEffect = new ExplosionEffect(getPosition());
            explosionEffect.setBounds(getX(), getY(), getWidth(), getHeight());
            parent.add(explosionEffect);
            parent.remove(this);
            parent.revalidate();
            parent.repaint();

            SoundPlayer.playSound("sounds/explosion.wav");
            Timer timer = new Timer(1000, e -> {
                parent.remove(explosionEffect);
                parent.revalidate();
                parent.repaint();
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    public abstract void handleCollision(Position oldPosition);

    public void attempFire() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFireTime >= FIRE_COOLDOWN) {
            for (int i = 0; i < numberOfBulletPerShoot; i++) {
                fire(i);
            }
            lastFireTime = currentTime;
        }
    }

    private void fire(int bulletIndex) {
        Bullet bullet = new Bullet(this);

        int tankCenterX = position.getX() + getWidth() / 2;
        int tankCenterY = position.getY() + getHeight() / 2;
        int bulletOffset = 10;

        Position bulletPosition = new Position(position.getX(), position.getY());

        if (numberOfBulletPerShoot == 1) {
            switch (direction) {
                case U:
                    bulletPosition.setX(tankCenterX - bullet.getImageSize().width / 2);
                    bulletPosition.setY(position.getY() - bullet.getImageSize().height);
                    break;
                case D:
                    bulletPosition.setX(tankCenterX - bullet.getImageSize().width / 2);
                    bulletPosition.setY(position.getY() + getHeight());
                    break;
                case L:
                    bulletPosition.setX(position.getX() - bullet.getImageSize().width);
                    bulletPosition.setY(tankCenterY - bullet.getImageSize().height / 2);
                    break;
                case R:
                    bulletPosition.setX(position.getX() + getWidth());
                    bulletPosition.setY(tankCenterY - bullet.getImageSize().height / 2);
                    break;
            }
        } else {
            switch (direction) {
                case U:
                    bulletPosition.setX(tankCenterX + (bulletIndex - numberOfBulletPerShoot / 2) * bulletOffset);
                    bulletPosition.setY(position.getY() - bullet.getImageSize().height);
                    break;
                case D:
                    bulletPosition.setX(tankCenterX + (bulletIndex - numberOfBulletPerShoot / 2) * bulletOffset);
                    bulletPosition.setY(position.getY() + getHeight());
                    break;
                case L:
                    bulletPosition.setX(position.getX() - bullet.getImageSize().width);
                    bulletPosition.setY(tankCenterY + (bulletIndex - numberOfBulletPerShoot / 2) * bulletOffset);
                    break;
                case R:
                    bulletPosition.setX(position.getX() + getWidth());
                    bulletPosition.setY(tankCenterY + (bulletIndex - numberOfBulletPerShoot / 2) * bulletOffset);
                    break;
            }
        }

        bullet.setPosition(bulletPosition);
        bullet.setSize(bullet.getImageSize());
        bullet.setBounds(bulletPosition.getX(), bulletPosition.getY(),
                bullet.getImageSize().width, bullet.getImageSize().height);
        BulletManager.getInstance().addBullet(bullet);
        SoundPlayer.playSound("sounds/fire.wav");

        if (getParent() != null) {
            getParent().add(bullet);
            getParent().setComponentZOrder(bullet, 2);
            getParent().repaint();
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public int getBulletSpeed() {
        return bulletSpeed;
    }

    public void setBulletSpeed(int bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public int getNumberOfBulletPerShoot() {
        return numberOfBulletPerShoot;
    }

    public void setNumberOfBulletPerShoot(int numberOfBulletPerShoot) {
        this.numberOfBulletPerShoot = numberOfBulletPerShoot;
    }

    public Map<Direction, Image> getImages() {
        return images;
    }

    public void setImages(Map<Direction, Image> images) {
        this.images = images;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isShooting() {
        return isShooting;
    }

    public void setShooting(boolean shooting) {
        isShooting = shooting;
    }

    public long getLastFireTime() {
        return lastFireTime;
    }

    public void setLastFireTime(long lastFireTime) {
        this.lastFireTime = lastFireTime;
    }
}
