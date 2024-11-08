package gamecomponent.tank;

import gamecomponent.Position;

public class FastTank extends EnemyTank{
    public FastTank(Position position){
        super(position);
        this.point = 200;
        this.health = 1;
        this.movementSpeed = 7;
        this.bulletSpeed = 11;
    }
}
