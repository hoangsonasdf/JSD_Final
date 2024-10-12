package gamecomponent.tank;

import gamecomponent.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    protected Map<Direction, Image> images = new HashMap<>();
    protected boolean isColliding = false;
    private boolean isActive;


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
}
