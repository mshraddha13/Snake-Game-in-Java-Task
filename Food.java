package entity;

import java.awt.*;

import game.Game;
import utils.Constants;

/**
 * The Food class represents the food that the snake eats to grow
 * and increase the player's score.
 */
public class Food {

    // Constants for the size and color of the food
    private static final int SIZE = Constants.FOOD_SIZE;
    private static final Color COLOR = Color.RED;

    // The position of the food
    private Point position;

    /**
     * Constructor that generates a random position for the food
     */
    public Food() {
        position = generateRandomPosition();
    }

    /**
     * Draws the food on the screen
     * Called inside the GamePanel's paintComponent method
     * 
     * @param g The graphics object to draw on
     */
    public void draw(Graphics g) {
        g.setColor(COLOR);
        g.fillRect(position.x, position.y, SIZE, SIZE);
    }

    /**
     * Returns the position of the food
     * 
     * @return The position of the food
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Sets the position of the food
     * 
     * @param position The new position of the food
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Generates a random position for the food within the game panel
     * 
     * @return A random position for the food
     */
    private Point generateRandomPosition() {
        int x = (int) (Math.random() * (Game.PANEL_WIDTH - 20) / SIZE) * SIZE;
        int y = (int) (Math.random() * (Game.PANEL_HEIGHT - 20) / SIZE) * SIZE;
        return new Point(x, y);
    }
}
