package gamecomponent.tank;

import gamecomponent.HomeBase;
import gamecomponent.Position;
import gamecomponent.enviroment.Enviroment;
import gamecomponent.powerup.PowerUp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerTank extends Tank {
    private int life;
    private int tier;
    private boolean shield;
    private int numberOfBulletPerShoot;
    private boolean upgradedBullet;
    private boolean upPressed, downPressed, leftPressed, rightPressed;
    Timer moveTimer = new Timer(16, e -> move());


    public PlayerTank(Position position) {
        super(position);
        this.direction = Direction.U;
        this.point = 0;
        this.health = 1;
        this.movementSpeed = 5;
        this.tier = 1;
        this.shield = false;
        loadImages();
        updateTierState();
        this.setSize(getImageSize());
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        direction = Direction.U;
                        moveTimer.start();
                        upPressed = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        direction = Direction.D;
                        moveTimer.start();
                        downPressed = true;
                        break;
                    case KeyEvent.VK_LEFT:
                        direction = Direction.L;
                        moveTimer.start();
                        leftPressed = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        direction = Direction.R;
                        moveTimer.start();
                        rightPressed = true;
                        break;
                    case KeyEvent.VK_SPACE:
                        fire();
                        break;
                }

                repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        upPressed = false;
                        break;
                    case KeyEvent.VK_DOWN:
                        downPressed = false;
                        break;
                    case KeyEvent.VK_LEFT:
                        leftPressed = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        rightPressed = false;
                        break;
                }
            }
        });
        setFocusable(true);
        requestFocusInWindow();
    }


    @Override
    public void move() {
        Position oldPosition = new Position(position.getX(), position.getY());
        int dx = 0, dy = 0;

        if (upPressed) {
            dy -= movementSpeed;
            direction = Direction.U;
        }
        if (downPressed) {
            dy += movementSpeed;
            direction = Direction.D;
        }
        if (leftPressed) {
            dx -= movementSpeed;
            direction = Direction.L;
        }
        if (rightPressed) {
            dx += movementSpeed;
            direction = Direction.R;
        }

        position.setX(position.getX() + dx);
        position.setY(position.getY() + dy);

        handleCollision(oldPosition);
        checkBounds();
        updatePanelPosition();
    }

    @Override
    public void loadImages() {
        this.images.put(Direction.U, new ImageIcon("images/HtankU.gif").getImage());
        this.images.put(Direction.D, new ImageIcon("images/HtankD.gif").getImage());
        this.images.put(Direction.L, new ImageIcon("images/HtankL.gif").getImage());
        this.images.put(Direction.R, new ImageIcon("images/HtankR.gif").getImage());
    }

    @Override
    public void checkBounds() {
        int frameWidth = getParent().getWidth();
        int frameHeight = getParent().getHeight();
        int tankWidth = getImageSize().width;
        int tankHeight = getImageSize().height;

        if (position.getX() < 0) {
            position.setX(0);
        } else if (position.getX() + tankWidth > frameWidth) {
            position.setX(frameWidth - tankWidth);
        }
        if (position.getY() < 0) {
            position.setY(0);
        } else if (position.getY() + tankHeight > frameHeight) {
            position.setY(frameHeight - tankHeight);
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
            if (collidedComponent instanceof HomeBase) {
                shouldRevertPosition = true;
            }
            if (collidedComponent instanceof PowerUp) {
                PowerUp powerUp = (PowerUp) collidedComponent;
                powerUp.active(this);
                this.repaint();
                this.getParent().remove(powerUp);
            }
            if (collidedComponent instanceof EnemyTank) {
                EnemyTank enemyTank = (EnemyTank) collidedComponent;
                enemyTank.explode();
            }
        }

        if (shouldRevertPosition) {
            position.setX(oldPosition.getX());
            position.setY(oldPosition.getY());
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Image tankImage = this.images.get(this.direction);
        if (tankImage != null) {
            g2d.drawImage(tankImage, 0, 0, this.getWidth(), this.getHeight(), null);
        }

        if (this.shield) {
            drawShield(g2d);
        }
    }

    private void drawShield(Graphics2D g2d) {
        Stroke oldStroke = g2d.getStroke();
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2.0f));

        int shieldRadius = Math.max(getWidth(), getHeight()) / 2 + 5;
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        g2d.drawOval(
                centerX - shieldRadius,
                centerY - shieldRadius,
                shieldRadius * 2,
                shieldRadius * 2
        );

        g2d.setStroke(oldStroke);
    }

    public void updateTierState() {
        if (this.tier == 1) {
            this.bulletSpeed = 6;
            this.numberOfBulletPerShoot = 1;
            this.upgradedBullet = false;
        } else if (this.tier == 2) {
            this.bulletSpeed = 8;
            this.numberOfBulletPerShoot = 1;
            this.upgradedBullet = false;
        } else if (this.tier == 3) {
            this.bulletSpeed = 8;
            this.numberOfBulletPerShoot = 2;
            this.upgradedBullet = false;
        } else {
            this.bulletSpeed = 8;
            this.numberOfBulletPerShoot = 2;
            this.upgradedBullet = true;
        }
    }

    public void tierUp() {
        if (this.tier < 4) {
            this.tier++;
        }
    }


}
