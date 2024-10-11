package gamecomponent.powerup;

import gamecomponent.Position;
import gamecomponent.tank.PlayerTank;

import javax.swing.*;

public class TankUp extends PowerUp {

    public TankUp(Position position) {
        super(position);
        this.image = new ImageIcon("images/tankup.png").getImage();
    }

    @Override
    public void active(PlayerTank tank) {
        tank.setLife(tank.getLife() + 1);
    }
}
