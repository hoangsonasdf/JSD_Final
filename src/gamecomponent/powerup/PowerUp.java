package gamecomponent.powerup;

import gamecomponent.Position;
import gamecomponent.tank.PlayerTank;

import javax.swing.*;
import java.awt.*;



public abstract class PowerUp extends JPanel {
    protected Position position;
    protected Image image;


    public PowerUp(Position position){
        this.position = position;
        this.setSize(getImageSize());
        setBounds(this.position.getX(), this.position.getY(), this.getImageSize().width, this.getImageSize().height);
    }

    public Dimension getImageSize() {
        if (image != null) {
            return new Dimension(image.getWidth(null), image.getHeight(null));
        }
        return new Dimension(40, 40);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null){
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }

    public abstract void active(PlayerTank tank);

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
