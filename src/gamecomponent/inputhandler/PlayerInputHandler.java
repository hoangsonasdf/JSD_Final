package gamecomponent.inputhandler;

import gamecomponent.tank.PlayerTank;
import gamecomponent.tank.Direction;
import lombok.Data;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Data
public class PlayerInputHandler extends KeyAdapter {
    private PlayerTank playerTank;


    public PlayerInputHandler(PlayerTank playerTank) {
        this.playerTank = playerTank;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (playerTank != null && playerTank.isActive()) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    playerTank.setDirection(Direction.U);
                    playerTank.move();
                    break;
                case KeyEvent.VK_DOWN:
                    playerTank.setDirection(Direction.D);
                    playerTank.move();
                    break;
                case KeyEvent.VK_LEFT:
                    playerTank.setDirection(Direction.L);
                    playerTank.move();
                    break;
                case KeyEvent.VK_RIGHT:
                    playerTank.setDirection(Direction.R);
                    playerTank.move();
                    break;
                case KeyEvent.VK_SPACE:
                    playerTank.attempFire();
                    break;
            }
        }
    }
}
