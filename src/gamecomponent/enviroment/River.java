package gamecomponent.enviroment;

import gamecomponent.Position;

import javax.swing.*;

public class River extends Enviroment{
    public River(Position position) {
        super(position);
        this.canPass = false;
        this.canDestroy = false;
        this.canBulletPass = true;
        this.image = new ImageIcon("images/river.jpg").getImage();
    }
}
