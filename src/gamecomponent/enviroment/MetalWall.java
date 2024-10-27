package gamecomponent.enviroment;

import gamecomponent.Position;
import lombok.Data;

import javax.swing.*;


public class MetalWall extends Enviroment{
    public MetalWall(Position position) {
        super(position);
        this.canPass = false;
        this.canDestroy = false;
        this.canBulletPass = false;
        this.image = new ImageIcon("images/metalWall.gif").getImage();
    }


}
