package gamecomponent.enviroment;

import gamecomponent.Position;

import javax.swing.*;
import java.awt.*;


public class BrickWall extends Enviroment{
    public BrickWall(Position position) {
        super(position);
        this.canPass = false;
        this.canDestroy = true;
        this.canBulletPass = false;
        this.image = new ImageIcon("images/commonWall.gif").getImage();
    }

    @Override
    public Dimension getImageSize() {
        return new Dimension(20,20);
    }
}
