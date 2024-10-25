package gamecomponent.tank;

import gamecomponent.Position;

import javax.swing.*;
import java.awt.*;

public class ExplosionEffect extends JPanel {
    private Image explosionImage;
    private Position position;

    public ExplosionEffect(Position position) {
        this.position = position;
        setOpaque(false);
        loadImage();
    }

    private void loadImage() {
        explosionImage = new ImageIcon("images/5.gif").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (explosionImage != null) {
            g2d.drawImage(explosionImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}
