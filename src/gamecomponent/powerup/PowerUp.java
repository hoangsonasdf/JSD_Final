package gamecomponent.powerup;

import gamecomponent.Position;
import gamecomponent.tank.PlayerTank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.awt.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class PowerUp extends JPanel {
    protected Position position;
    protected Image image;
    public PowerUp(Position position){
        this.position = position;
        this.setSize(getImageSize());
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

    public abstract void active(PlayerTank tank);
}
