package entity;

import java.awt.*;
import java.util.ArrayList;

import game.Game;
import utils.Constants;
import utils.Direction;

/**
 * Represents the snake entity in the game.
 */
public class Snake {

    private static final int SIZE = Constants.SNAKE_SIZE;
    private static final int START_LENGTH = 3;
    private static final Color COLOR = Color.GREEN;

    private ArrayList<Point> segments;
    private Direction direction;
    private boolean growing;

    /**
     * Initializes a new instance of the Snake class.
     */
    public Snake() {
        segments = new ArrayList<>();
        direction = Direction.RIGHT;
        growing = false;

        // Initialize snake segments
        int startX = Game.PANEL_WIDTH / 2;
        int startY = Game.PANEL_HEIGHT / 2;
        for (int i = 0; i < START_LENGTH; i++) {
            segments.add(new Point(startX - i * SIZE, startY));
        }
    }

    /**
     * Moves the snake in the current direction.
     */
    public void move() {
        Point head = new Point(segments.get(0));
        switch (direction) {
            case UP:
                head.y -= SIZE;
                break;
            case DOWN:
                head.y += SIZE;
                break;
            case LEFT:
                head.x -= SIZE;
                break;
            case RIGHT:
                head.x += SIZE;
                break;
        }

        segments.add(0, head);

        if (!growing) {
            segments.remove(segments.size() - 1);
        } else {
            growing = false;
        }
    }

    /**
     * Draws the snake on the specified graphics object.
     * 
     * @param g The graphics object to draw on.
     */
    public void draw(Graphics g) {
        g.setColor(COLOR);
        for (Point segment : segments) {
            g.fillRect(segment.x, segment.y, SIZE, SIZE);
        }
    }

    /**
     * Sets the direction of the snake.
     * 
     * @param direction The new direction of the snake.
     */
    public void setDirection(Direction direction) {
        if (this.direction == Direction.UP && direction == Direction.DOWN) {
            return;
        }
        if (this.direction == Direction.DOWN && direction == Direction.UP) {
            return;
        }
        if (this.direction == Direction.LEFT && direction == Direction.RIGHT) {
            return;
        }
        if (this.direction == Direction.RIGHT && direction == Direction.LEFT) {
            return;
        }
        this.direction = direction;
    }

    /**
     * Gets the bounding rectangle of the snake's head.
     * 
     * @return The bounding rectangle of the snake's head.
     */
    public Rectangle getHeadBounds() {
        Point head = segments.get(0);
        return new Rectangle(head.x, head.y, SIZE, SIZE);
    }

    /**
     * Gets the list of segments that make up the snake.
     * 
     * @return The list of segments that make up the snake.
     */
    public ArrayList<Point> getSegments() {
        return segments;
    }

    /**
     * Checks if the snake has collided with itself.
     * 
     * @return True if the snake has collided with itself, false otherwise.
     */
    public boolean checkSelfCollision() {
        Rectangle headBounds = getHeadBounds();
        for (int i = 1; i < segments.size(); i++) {
            Point segment = segments.get(i);
            Rectangle segmentBounds = new Rectangle(segment.x, segment.y, SIZE, SIZE);
            if (headBounds.intersects(segmentBounds)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets whether the snake is growing or not.
     * 
     * @param growing True if the snake is growing, false otherwise.
     */
    public void setGrowing(boolean growing) {
        this.growing = growing;
    }
}