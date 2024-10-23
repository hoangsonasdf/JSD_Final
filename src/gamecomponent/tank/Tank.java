package gamecomponent.tank;

import gamecomponent.Direction;
import gamecomponent.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import manager.BulletManager;
import ultil.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
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
    private long lastFireTime = 0;
    private static final long FIRE_COOLDOWN = 1000;


    public Tank(Position position) {
        this.position = position;
        this.isActive = true;
        setOpaque(false);
        setLayout(null);
        setBounds(this.position.getX(), this.position.getY(), this.getImageSize().width, this.getImageSize().height);
    }

    public abstract void move();

    public abstract void loadImages();

    public Dimension getImageSize() {
        Image tankImage = this.images.get(this.direction);
//        if (tankImage != null) {
//            return new Dimension(tankImage.getWidth(null), tankImage.getHeight(null));
//        }
        return new Dimension(40, 40);
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

        bullet.setPosition(bulletPosition);
        bullet.setSize(bullet.getImageSize());
        bullet.setBounds(bulletPosition.getX(), bulletPosition.getY(),
                bullet.getImageSize().width, bullet.getImageSize().height);
        BulletManager.getInstance().addBullet(bullet);
        SoundPlayer.playSound("sounds/fire.wav");

        if (getParent() != null) {
            getParent().add(bullet);
            getParent().repaint();
        }
    }


}
