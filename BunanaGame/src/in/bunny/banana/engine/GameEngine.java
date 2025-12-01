/*
package in.bunny.banana.engine;

import java.awt.image.BufferedImage;


public class GameEngine {
	String thePlayer = null;

	
	public GameEngine(String player) {
		thePlayer = player;
	}

	int counter = 0;
	int score = 0;
	GameServer theGames = new GameServer();
	Game current = null;

	public BufferedImage nextGame() {
		current = theGames.getRandomGame();
		return current.getImage();

	}

	
	public boolean checkSolution( int i) {
		if (i == current.getSolution()) {
			score++;
			return true;
		} else {
			return false;
		}
	}

	
	public int getScore() {
		return score;
	}

}
*/



package in.bunny.banana.engine;

import java.awt.image.BufferedImage;

/**
 * Main class where the games are coming from.
 */
public class GameEngine {
    private String thePlayer; // Player name
    private int counter = 0;
    private int score = 0;
    private GameServer theGames = new GameServer();
    private Game current = null;

    /**
     * Each player has their own game engine.
     * 
     * @param player the player's name
     */
    public GameEngine(String player) {
        this.thePlayer = player;
    }

    /**
     * Returns the player's name.
     */
    public String getPlayerName() {
        return thePlayer;
    }

    /**
     * Retrieves a game. This basic version only has two games that alternate.
     */
    public BufferedImage nextGame() {
        current = theGames.getRandomGame();
        return current.getImage();
    }

    /**
     * Checks if the parameter i is a solution to the game.
     */
    public boolean checkSolution(int i) {
        if (i == current.getSolution()) {
            score++;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves the score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Resets score and counter for a new game.
     */
    public void resetGame() {
        this.score = 0;
        this.counter = 0;
    }
}

