package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * The Wall class represents a wall object in the game.
 */
public class Wall {

    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final Color COLOR = Color.GRAY;

    private int x;
    private int y;

    /**
     * Constructs a wall object with the given x and y coordinates.
     * 
     * @param x the x coordinate of the wall
     * @param y the y coordinate of the wall
     */
    public Wall(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws the wall object on the screen.
     * 
     * @param g the graphics object used to draw the wall
     */
    public void draw(Graphics g) {
        g.setColor(COLOR);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    /**
     * Returns the bounding rectangle of the wall object.
     * 
     * @return the bounding rectangle of the wall
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
}