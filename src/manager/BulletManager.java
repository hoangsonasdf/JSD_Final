package manager;

import gamecomponent.tank.Bullet;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BulletManager {
    private static BulletManager instance;
    private List<Bullet> activeBullets;

    private BulletManager() {
        activeBullets = new ArrayList<>();
    }

    public static BulletManager getInstance() {
        if (instance == null) {
            instance = new BulletManager();
        }
        return instance;
    }

    public void addBullet(Bullet bullet) {
        activeBullets.add(bullet);
    }

    public void updateBullets(Container gameContainer) {
        Iterator<Bullet> iterator = activeBullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            if (bullet.isActive()) {
                bullet.move();
                bullet.checkBounds();
            } else {
                if (gameContainer != null) {
                    gameContainer.remove(bullet);
                }
                iterator.remove();
            }
        }
        if (gameContainer != null) {
            gameContainer.repaint();
        }
    }

}
