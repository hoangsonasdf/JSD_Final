package gamecomponent.enviroment;

import gamecomponent.Position;
import lombok.Data;

import javax.swing.*;


@Data
public class BrickWall extends Enviroment{
    public BrickWall(Position position) {
        super(position);
        this.canPass = false;
        this.canDestroy = true;
        this.image = new ImageIcon("images/commonWall.gif").getImage();
    }


}
