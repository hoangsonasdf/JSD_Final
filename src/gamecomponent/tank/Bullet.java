package gamecomponent.tank;

import gamecomponent.Position;
import lombok.Data;

import javax.swing.*;
import java.awt.*;

@Data
public class Bullet extends JPanel {
    private Position position;
    private Direction direction;
    private int speed;

    public Bullet(Tank shootBy){
        this.position = shootBy.getPosition();
        this.direction = shootBy.getDirection();
        this.speed = shootBy.getBulletSpeed();
    }

    public void move(){
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
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval(position.getX(), position.getY(), 10, 10);
    }
}
