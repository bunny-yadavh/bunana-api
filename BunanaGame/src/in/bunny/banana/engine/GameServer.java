package in.bunny.banana.engine;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Base64;
import javax.imageio.ImageIO;

/**
 * Retrieves a random game (image + solution) from Marc Conrad's Banana API.
 * Each game includes a puzzle image and its correct answer.
 */
public class GameServer {

    private static final String API_URL = "https://marcconrad.com/uob/banana/api.php?out=csv&base64=yes";

    /**
     * Reads all text data from a URL.
     */
    private static String readUrl(String urlString) {
        StringBuilder result = new StringBuilder();
        HttpURLConnection connection = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);  // prevent freezing
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "UTF-8"))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            }
        } catch (IOException e) {
            System.err.println("⚠️ Error reading from URL: " + e.getMessage());
            return null;
        } finally {
            if (connection != null) connection.disconnect();
        }

        return result.toString();
    }

    /**
     * Retrieves a random game from the API.
     * @return a Game object containing the image and solution, or null if an error occurred.
     */
    public Game getRandomGame() {
        String dataRaw = readUrl(API_URL);
        if (dataRaw == null || !dataRaw.contains(",")) {
            System.err.println("❌ Failed to fetch game data from API.");
            return null;
        }

        try {
            String[] data = dataRaw.split(",");
            if (data.length < 2) {
                System.err.println("❌ Invalid API response format.");
                return null;
            }

            // Decode the Base64 image
            byte[] decodedImage = Base64.getDecoder().decode(data[0]);
            try (ByteArrayInputStream quest = new ByteArrayInputStream(decodedImage)) {
                BufferedImage image = ImageIO.read(quest);
                int solution = Integer.parseInt(data[1].trim());

                if (image == null) {
                    System.err.println("⚠️ Failed to decode puzzle image.");
                    return null;
                }

                return new Game(image, solution);
            }

        } catch (Exception e) {
            System.err.println("❌ Error parsing game data: " + e.getMessage());
            return null;
        }
    }
}
