package gamecomponent.tank;

import gamecomponent.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ultil.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private java.util.List<Bullet> bullets = new ArrayList<>();
    private long lastFireTime = 0;
    private static final long FIRE_COOLDOWN = 1000;


    public Tank(Position position) {
        this.position = position;
        this.isActive = true;
        setOpaque(false);
        setLayout(null);
    }

    public abstract void move();

    public abstract void loadImages();

    public Dimension getImageSize() {
        Image tankImage = this.images.get(this.direction);
        if (tankImage != null) {
            return new Dimension(tankImage.getWidth(null), tankImage.getHeight(null));
        }
        return new Dimension(50, 50);
    }

    protected void updatePanelPosition() {
        setBounds(position.getX(), position.getY(), getWidth(), getHeight());
        getParent().repaint();
    }

    public abstract void checkBounds();

    protected boolean checkCollisionWith(Component other) {
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

    protected Component checkCollision() {
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
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            lastFireTime = currentTime;
        }
    }

    private void fire(int bulletIndex) {
        int tankCenterX = position.getX() + getWidth() / 2;
        int tankCenterY = position.getY() + getHeight() / 2;
        int bulletOffset = 10;

        Position bulletPosition = new Position(position.getX(), position.getY());

        switch (direction) {
            case U:
                bulletPosition.setX(tankCenterX + (bulletIndex - numberOfBulletPerShoot / 2) * bulletOffset);
                bulletPosition.setY(position.getY());
                break;
            case D:
                bulletPosition.setX(tankCenterX + (bulletIndex - numberOfBulletPerShoot / 2) * bulletOffset);
                bulletPosition.setY(position.getY() + getHeight());
                break;
            case L:
                bulletPosition.setX(position.getX());
                bulletPosition.setY(tankCenterY + (bulletIndex - numberOfBulletPerShoot / 2) * bulletOffset);
                break;
            case R:
                bulletPosition.setX(position.getX() + getWidth());
                bulletPosition.setY(tankCenterY + (bulletIndex - numberOfBulletPerShoot / 2) * bulletOffset);
                break;
        }

        Bullet bullet = new Bullet(this);
        bullet.setPosition(bulletPosition);
        bullet.setDirection(direction);
        bullet.setSpeed(bulletSpeed);
        bullets.add(bullet);
        getParent().add(bullet);
    }

    public void updateBullets() {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            if (bullet.isActive()) {
                bullet.move();
                bullet.checkBounds();
            } else {
                if (getParent() != null) {
                    getParent().remove(bullet);
                }
                iterator.remove();
            }
        }
        if (getParent() != null) {
            getParent().repaint();
        }
    }
}
