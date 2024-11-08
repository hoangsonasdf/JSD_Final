package gamecomponent.enviroment;

import gamecomponent.Position;
import gamecomponent.tank.ExplosionEffect;
import ultil.SoundPlayer;

import javax.swing.*;
import java.awt.*;

public abstract class Enviroment extends JPanel {
    protected Position position;
    protected boolean canBulletPass;
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
        return new Dimension(40, 40);
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isCanBulletPass() {
        return canBulletPass;
    }

    public void setCanBulletPass(boolean canBulletPass) {
        this.canBulletPass = canBulletPass;
    }

    public boolean isCanPass() {
        return canPass;
    }

    public void setCanPass(boolean canPass) {
        this.canPass = canPass;
    }

    public boolean isCanDestroy() {
        return canDestroy;
    }

    public void setCanDestroy(boolean canDestroy) {
        this.canDestroy = canDestroy;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
