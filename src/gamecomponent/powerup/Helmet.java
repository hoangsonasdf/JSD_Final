package gamecomponent.powerup;

import gamecomponent.Position;
import gamecomponent.tank.PlayerTank;

import javax.swing.*;

public class Helmet extends PowerUp{
    public Helmet(Position position){
        super(position);
        this.image = new ImageIcon("images/helmet.png").getImage();
    }
    @Override
    public void active(PlayerTank tank) {
        tank.setShield(true);
    }
}
