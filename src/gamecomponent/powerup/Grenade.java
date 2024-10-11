package gamecomponent.powerup;

import gamecomponent.Position;
import gamecomponent.tank.EnemyTank;
import gamecomponent.tank.PlayerTank;
import gamecomponent.tank.Tank;

import javax.swing.*;
import java.util.Arrays;

public class Grenade extends PowerUp {
    public Grenade(Position position){
        super(position);
        this.image = new ImageIcon("images/grenade.png").getImage();
    }
    @Override
    public void active(PlayerTank tank) {
        Arrays.stream(this.getParent().getComponents())
                .filter(x -> x instanceof EnemyTank)
                .map(x -> (EnemyTank) x)
                .forEach(Tank::explode);
    }
}
