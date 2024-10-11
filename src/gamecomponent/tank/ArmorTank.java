package gamecomponent.tank;

import gamecomponent.Position;
import lombok.Data;

@Data
public class ArmorTank extends EnemyTank{
    public ArmorTank(Position position){
        super(position);
        this.point = 400;
        this.health = 4;
        this.movementSpeed = 6;
        this.bulletSpeed = 6;
    }
}
