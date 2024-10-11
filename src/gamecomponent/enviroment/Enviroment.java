package gamecomponent.enviroment;

import gamecomponent.Position;
import lombok.Data;

import javax.swing.*;
import java.awt.*;

@Data
public abstract class Enviroment extends JPanel {
    protected Position position;
    protected boolean canPass;
    protected boolean canDestroy;
    protected Image image;

    public Enviroment(Position position){
        this.position = position;
        this.setSize(getImageSize());
    }

    public Enviroment(){

    }
    public Dimension getImageSize() {
        if (image != null) {
            return new Dimension(image.getWidth(null), image.getHeight(null));
        }
        return new Dimension(50, 50);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null){
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }
}
