package gamecomponent.powerup;

import gamecomponent.Position;
import gamecomponent.tank.PlayerTank;

import javax.swing.*;

public class Star extends PowerUp{
    public Star(Position position){
        super(position);
        this.image = new ImageIcon("images/star.png").getImage();
    }
    @Override
    public void active(PlayerTank tank) {
        tank.tierUp();
        tank.updateTierState();
    }
}
