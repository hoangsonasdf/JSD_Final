package gamecomponent.enviroment;

import gamecomponent.Position;

import javax.swing.*;

public class Tree extends Enviroment{
    public Tree(Position position) {
        super(position);
        this.canPass = true;
        this.canDestroy = false;
        this.image = new ImageIcon("images/tree.gif").getImage();
    }
}
