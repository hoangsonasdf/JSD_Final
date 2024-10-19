package gamecomponent.enviroment;

import gamecomponent.Position;
import gamecomponent.tank.ExplosionEffect;
import lombok.Data;
import ultil.SoundPlayer;

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
        setBounds(this.position.getX(), this.position.getY(), this.getImageSize().width, this.getImageSize().height);
    }

    public Enviroment(){

    }
    public Dimension getImageSize() {
        if (image != null) {
            return new Dimension(image.getWidth(null), image.getHeight(null));
        }
        return new Dimension(35, 35);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null){
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }

    public void destroy() {
        if (getParent() != null) {
            Container parent = getParent();
            ExplosionEffect explosionEffect = new ExplosionEffect(getPosition());
            explosionEffect.setBounds(getX(), getY(), getWidth(), getHeight());
            parent.add(explosionEffect);
            parent.remove(this);
            parent.revalidate();
            parent.repaint();

            SoundPlayer.playSound("sounds/explosion.wav");
            Timer timer = new Timer(1000, e -> {
                parent.remove(explosionEffect);
                parent.revalidate();
                parent.repaint();
            });
            timer.setRepeats(false);
            timer.start();
        }
    }
}
