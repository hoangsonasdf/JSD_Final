package gamecomponent.tank;

import gamecomponent.Position;


public class PowerTank extends EnemyTank{
    public PowerTank(Position position){
        super(position);
        this.point = 300;
        this.health = 1;
        this.movementSpeed = 6;
        this.bulletSpeed = 12;
    }
}
