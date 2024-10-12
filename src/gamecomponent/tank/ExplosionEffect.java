package gamecomponent.tank;

import gamecomponent.Position;

import javax.swing.*;
import java.awt.*;

public class ExplosionEffect extends JPanel {
    private Image explosionImage;
    private Position position;

    public ExplosionEffect(Position position) {
        this.position = position;
        loadImage();
    }

    private void loadImage() {
        explosionImage = new ImageIcon("images/5.gif").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (explosionImage != null) {
            g.drawImage(explosionImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}
