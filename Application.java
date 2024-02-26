

import javax.swing.JFrame;

import game.Game;
import game.Score;

import java.awt.BorderLayout;

/**
 * Application class is the Entry point of the application.
 * It is responsible for creating the JFrame and adding the GamePanel and
 * ScorePanel to it.
 * Application is a standard Swing JFrame.
 * 
 */
public class Application extends JFrame {

    private Game gamePanel;
    private Score scorePanel;

    public Application() {
        setTitle("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        gamePanel = new Game();
        scorePanel = new Score();

        add(gamePanel);
        add(scorePanel, BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Application(); // Start the application (new Window)
    }
}
