package gamecomponent.enviroment;

import gamecomponent.Position;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CompositeBrickWall {
    private final int COLS = 2;
    private final int ROWS = 2;
    private Position position;
    private List<BrickWall> bricks;
    private Dimension imageSize;

    public CompositeBrickWall(Position position) {
        this.position = position;
        this.bricks = new ArrayList<>();

        BrickWall tempBrick = new BrickWall(new Position(0, 0));
        Dimension brickSize = tempBrick.getImageSize();

        this.imageSize = new Dimension(
                brickSize.width * COLS,
                brickSize.height * ROWS
        );
    }

    public void addToPanel(JPanel panel) {
        BrickWall tempBrick = new BrickWall(new Position(0, 0));
        Dimension brickSize = tempBrick.getImageSize();

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Position brickPosition = new Position(
                        position.getX() + (col * brickSize.width),
                        position.getY() + (row * brickSize.height)
                );
                BrickWall brick = new BrickWall(brickPosition);
                brick.setBounds(
                        brickPosition.getX(),
                        brickPosition.getY(),
                        brickSize.width,
                        brickSize.height
                );
                bricks.add(brick);
                panel.add(brick);
            }
        }
    }

    public Dimension getImageSize() {
        return imageSize;
    }

    public int getCOLS() {
        return COLS;
    }

    public int getROWS() {
        return ROWS;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<BrickWall> getBricks() {
        return bricks;
    }

    public void setBricks(List<BrickWall> bricks) {
        this.bricks = bricks;
    }

    public void setImageSize(Dimension imageSize) {
        this.imageSize = imageSize;
    }
}