package gamecomponent.inputhandler;

import gamecomponent.tank.PlayerTank;
import gamecomponent.tank.Direction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayerInputHandler extends KeyAdapter {
    private PlayerTank playerTank;
    private boolean upPressed, downPressed, leftPressed, rightPressed;

    public PlayerInputHandler(PlayerTank playerTank) {
        this.playerTank = playerTank;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                playerTank.setDirection(Direction.U);
                upPressed = true;
                playerTank.move();
                break;
            case KeyEvent.VK_DOWN:
                playerTank.setDirection(Direction.D);
                downPressed = true;
                playerTank.move();
                break;
            case KeyEvent.VK_LEFT:
                playerTank.setDirection(Direction.L);
                leftPressed = true;
                playerTank.move();
                break;
            case KeyEvent.VK_RIGHT:
                playerTank.setDirection(Direction.R);
                rightPressed = true;
                playerTank.move();
                break;
            case KeyEvent.VK_SPACE:
                playerTank.attempFire();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                upPressed = false;
                break;
            case KeyEvent.VK_DOWN:
                downPressed = false;
                break;
            case KeyEvent.VK_LEFT:
                leftPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                break;
        }
    }

}
