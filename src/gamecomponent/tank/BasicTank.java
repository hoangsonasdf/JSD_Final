package gamecomponent.tank;

import gamecomponent.Position;
import lombok.Data;

@Data
public class BasicTank extends EnemyTank {
    public BasicTank(Position position) {
        super(position);
        this.point = 100;
        this.health = 1;
        this.movementSpeed = 5;
        this.bulletSpeed = 5;
    }
}
