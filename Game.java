package game;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import entity.*;
import utils.Constants;
import utils.Direction;

/**
 * Game is a JPanel that is responsible for drawing the game objects and
 * handling the game logic. GamePanel also handles the user input.
 * End screen is handled by GamePanel.
 * 
 * @extends JPanel
 * @implements ActionListener
 */
public class Game extends JPanel implements ActionListener {

    public static final int PANEL_WIDTH = Constants.PANEL_SIZE.width;
    public static final int PANEL_HEIGHT = Constants.PANEL_SIZE.height;
    private static final int DELAY = 200;

    private Snake snake;
    private Food food;
    private ArrayList<Wall> walls;
    private Timer timer;
    private boolean gameOver;

    public Game() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new GameKeyListener());

        snake = new Snake();
        food = new Food();
        walls = new ArrayList<>();

        // Add walls to the walls ArrayList
        int panelWidthInWalls = PANEL_WIDTH / Constants.WALL_WIDTH;
        int panelHeightInWalls = PANEL_HEIGHT / Constants.WALL_HEIGHT;

        // Add top and bottom walls
        for (int i = 0; i < panelWidthInWalls; i++) {
            walls.add(new Wall(i * Constants.WALL_WIDTH, 0));
            walls.add(new Wall(i * Constants.WALL_WIDTH, PANEL_HEIGHT - Constants.WALL_HEIGHT));
        }

        // Add left and right walls
        for (int i = 1; i < panelHeightInWalls - 1; i++) {
            walls.add(new Wall(0, i * Constants.WALL_HEIGHT));
            walls.add(new Wall(PANEL_WIDTH - Constants.WALL_WIDTH, i * Constants.WALL_HEIGHT));
        }

        // Rendering loop (calls actionPerformed() every DELAY milliseconds)
        timer = new Timer(DELAY, this);
        timer.start();

        gameOver = false;
    }

    @Override
    protected void paintComponent(Graphics g) { // Draw the game objects
        super.paintComponent(g);

        if (gameOver) {
            // Display game over message
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            String gameOverMsg = "Game Over!";
            int msgWidth = g.getFontMetrics().stringWidth(gameOverMsg);
            int msgHeight = g.getFontMetrics().getHeight();
            int x = (PANEL_WIDTH - msgWidth) / 2; // Center the message
            int y = (PANEL_HEIGHT - msgHeight) / 2; // Center the message
            g.drawString(gameOverMsg, x, y);
        } else {
            snake.draw(g);
            food.draw(g);
            for (Wall wall : walls) {
                wall.draw(g);
            }

        }

        // Show "P to pause and R to restart" message
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", 0, 20));
        String pauseMsg = "P to pause and R to reset";
        int msgWidth = g.getFontMetrics().stringWidth(pauseMsg);
        int msgHeight = g.getFontMetrics().getHeight();
        int x = (PANEL_WIDTH - msgWidth) / 2; // Center the message
        int y = PANEL_HEIGHT - msgHeight; // Center the message but at bottom
        g.drawString(pauseMsg, x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) { // Event loop
        if (!gameOver) {
            snake.move(); // Update
            checkCollision(); // Ckecks
            repaint(); // Redraw
        }
    }

    /**
     * Checks if the snake collides with food, walls or itself.
     * Decides if the game is over or not.
     */
    private void checkCollision() {
        Rectangle snakeHeadBounds = snake.getHeadBounds();

        // Check collision with food
        if (snakeHeadBounds.intersects(food.getPosition().x, food.getPosition().y, Constants.FOOD_SIZE,
                Constants.FOOD_SIZE)) {
            snake.setGrowing(true);
            Score.Instance.setScore(Score.Instance.getScore() + 1);
            food.setPosition(generateRandomFoodPosition());
        }

        // Check collision with walls
        for (Wall wall : walls) {
            if (snakeHeadBounds.intersects(wall.getBounds())) {
                gameOver = true;
                break;
            }
        }

        // Check self-collision
        if (snake.checkSelfCollision()) {
            gameOver = true;
        }

    }

    /**
     * Generates a random (valid) position for the food.
     * The position is always calculated so that the food is not placed on a wall or
     * on the snake.
     * 
     * @return A Point object that represents the position of the food.
     */
    private Point generateRandomFoodPosition() {
        int maxX = Constants.PANEL_SIZE.width - Constants.FOOD_SIZE;
        int maxY = Constants.PANEL_SIZE.height - Constants.FOOD_SIZE;

        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt((maxX - 1) / Constants.FOOD_SIZE) * Constants.FOOD_SIZE;
            y = random.nextInt((maxY - 1) / Constants.FOOD_SIZE) * Constants.FOOD_SIZE;
        } while (isWallCollision(x, y) || isSnakeCollision(x, y));

        System.out.println("Food position: " + x + ", " + y);
        return new Point(x, y);
    }

    /**
     * Checks if the given coordinates are inside a wall.
     * 
     * @param x coordinate of the point
     * @param y coordinate of the point
     * @return true if the point is inside a wall, false otherwise
     */
    private boolean isWallCollision(int x, int y) {
        for (Wall wall : walls) {
            if (wall.getBounds().contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given coordinates are inside the snake.
     * 
     * @param x coordinate of the point
     * @param y coordinate of the point
     * @return true if the point is inside the snake, false otherwise
     */
    private boolean isSnakeCollision(int x, int y) {
        for (Point snakeSegment : snake.getSegments()) {
            if (snakeSegment.getX() == x && snakeSegment.getY() == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Input handler for the game.
     * Handles the user input.
     * 
     * @extends KeyAdapter
     */
    private class GameKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                snake.setDirection(Direction.UP);
            } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                snake.setDirection(Direction.DOWN);
            } else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                snake.setDirection(Direction.LEFT);
            } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                snake.setDirection(Direction.RIGHT);
            } else if (keyCode == KeyEvent.VK_P) {
                if (timer.isRunning()) {
                    timer.stop();
                } else {
                    timer.start();
                }
            } else if (keyCode == KeyEvent.VK_R) {
                Score.Instance.setScore(0);
                snake = new Snake();
                food = new Food();
                gameOver = false;
            }
        }
    }

}
