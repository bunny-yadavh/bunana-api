package in.bunny.banana.engine;

/**
 * Session.java
 */

public class Session {
    // Static field so the session can be accessed globally
    private static String currentUser;
    private static int currentScore;
    private static int correctAnswers;
    private static int wrongAnswers;
    private static int timeLeft;

   
    private Session() { }

    // -------- USER INFO --------
    public static void setCurrentUser(String username) {
        currentUser = username;
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null && !currentUser.isEmpty();
    }

    public static void logout() {
        currentUser = null;
        currentScore = 0;
        correctAnswers = 0;
        wrongAnswers = 0;
        timeLeft = 0;
    }

    // -------- GAME DATA --------
    public static void setCurrentScore(int score) {
        currentScore = score;
    }

    public static int getCurrentScore() {
        return currentScore;
    }

    public static void setCorrectAnswers(int correct) {
        correctAnswers = correct;
    }

    public static int getCorrectAnswers() {
        return correctAnswers;
    }

    public static void setWrongAnswers(int wrong) {
        wrongAnswers = wrong;
    }

    public static int getWrongAnswers() {
        return wrongAnswers;
    }

    public static void setTimeLeft(int time) {
        timeLeft = time;
    }

    public static int getTimeLeft() {
        return timeLeft;
    }

    @Override
    public String toString() {
        return "Session[User=" + currentUser +
                ", Score=" + currentScore +
                ", Correct=" + correctAnswers +
                ", Wrong=" + wrongAnswers +
                ", TimeLeft=" + timeLeft + "]";
    }
}
