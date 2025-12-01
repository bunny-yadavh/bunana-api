package in.bunny.banana.engine;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * Represents a single puzzle game.
 * Contains an image (the puzzle) and its correct solution.
 */
public class Game {

    private BufferedImage image;
    private int solution;
    private LocalDateTime loadedAt; // ✅ track when the game was loaded
    private boolean answeredCorrectly; // ✅ useful for analytics later

    /**
     * Creates a new Game object.
     * 
     * @param image    The puzzle image.
     * @param solution The correct answer for this puzzle.
     */
    public Game(BufferedImage image, int solution) {
        this.image = image;
        this.solution = solution;
        this.loadedAt = LocalDateTime.now();
        this.answeredCorrectly = false;
    }

    /**
     * @return The puzzle image.
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * @return The correct solution number.
     */
    public int getSolution() {
        return solution;
    }

    /**
     * @return Timestamp when this puzzle was loaded.
     */
    public LocalDateTime getLoadedAt() {
        return loadedAt;
    }

    /**
     * Mark the puzzle as solved correctly (useful for session tracking).
     */
    public void markAsCorrect() {
        this.answeredCorrectly = true;
    }

    /**
     * @return true if this puzzle was solved correctly.
     */
    public boolean isAnsweredCorrectly() {
        return answeredCorrectly;
    }
}
