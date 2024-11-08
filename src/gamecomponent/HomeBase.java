package gamecomponent;


import javax.swing.*;
import java.awt.*;

public class HomeBase extends JPanel {
    private int health;
    private Position position;
    private Image image;

    public HomeBase(Position position){
        this.health = 4;
        this.position = position;
        this.image = new ImageIcon("images/home.jpg").getImage();
        setSize(this.getImageSize());
        setBounds(this.position.getX(), this.position.getY(), this.getImageSize().width, this.getImageSize().height);
    }
    public Dimension getImageSize() {
        return new Dimension(40, 40);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null){
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

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
