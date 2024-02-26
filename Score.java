package game;

import javax.swing.JPanel;
import java.awt.*;

/**
 * The Score class represents a JPanel that displays the current score of
 * the game.
 * It extends the JPanel class and overrides the paintComponent method to
 * display the score.
 * The score can be set using the setScore method and retrieved using the
 * getScore method.
 * The ScorePanel class is a singleton, meaning that only one instance of it can
 * exist at a time.
 * The instance can be accessed using the static Instance field.
 * 
 * @extnds JPanel
 */

public class Score extends JPanel {

    // Singleton instance for Easy access
    public static Score Instance;
    private int score;

    public Score() {
        Score.Instance = this;
        score = 0;
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(800, 50));
    }

    public void setScore(int score) {
        this.score = score;
        repaint();
    }

    public int getScore() {
        return this.score;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 30);
    }
}
