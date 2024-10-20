package gamecomponent.inputhandler;

import java.util.HashSet;
import java.util.Set;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyStateHandler implements KeyListener {
    private Set<Integer> pressedKeys = new HashSet<>();

    public boolean isKeyPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }
}