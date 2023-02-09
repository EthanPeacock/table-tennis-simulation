import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.util.*;

/**
 * This class is responsible for getting players from a {@code JSON} file using the library {@link Gson}.
 * <p>
 *     This class will not need to be instantiated, and instead will make use of a static method to fetch the player
 *     data. This static method will make use of the {@link Gson} library to get the player data from the given file and
 *     shuffle the order to ensure it is different each time, using a private method.
 * </p>
 * @see ReadPlayerData
 */
public class GetPlayerData {
    /**
     * The number of players taking part in the simulated tournament.
     */
    private static int numPlayers;
    /**
     * An {@link ArrayList} of players, that will be filled as players are created from the {@code JSON} file.
     */
    private static ArrayList<TableTennisPlayer> players = new ArrayList<TableTennisPlayer>();

    /**
     * This static method will be called to get the players from a {@code JSON} file.
     * <br>
     * This method will do the following:
     * <ul>
     *      <li>Get the player data from the {@code JSON} file.</li>
     *      <li>Create a {@link SportPlayer} object with the data.</li>
     *      <li>Shuffle the list order.</li>
     *      <li>Return the players.</li>
     * </ul>
     * @param playerCount number of players to take part in the tournament.
     * @return all the player objects created from fetched data.
     */
    public static ArrayList<TableTennisPlayer> fetch(int playerCount) {
        numPlayers = playerCount;

        String josnFIle = "data/players-attributes.json";
        Gson gson = new Gson();

        JsonReader jsonReader = null;
        try {
            FileReader fileReader = new FileReader(josnFIle);
            jsonReader = new JsonReader(fileReader);
        } catch (Exception e) {
            System.out.println("Unexpected error:\n" + e);
            return null;
        }

        ReadPlayerData[] playerData = gson.fromJson(jsonReader, ReadPlayerData[].class);

        shuffleAndSelect(playerData);

        return players;
    }

    /**
     * This method takes an array and randomises the order of the elements.
     * <p>
     *     This is done by loop over the array and generating a random number which will then be the new location for
     *     the current element. This new location will currently have an element, so they are switched around.
     * </p>
     * @param playerArr the array to be shuffled.
     */
    private static void shuffleAndSelect(ReadPlayerData[] playerArr) {
        Random rand = new Random();
        int len = playerArr.length;
        for (int i = len-1; i > 0; i--) {
            int randInt = rand.nextInt(i); // select a random location
            ReadPlayerData temp = playerArr[i]; // store what is in current position
            playerArr[i] = playerArr[randInt]; // swap positions
            playerArr[randInt] = temp;
        }

        for (int x = 0; x < numPlayers; x++) {
            ReadPlayerData p = playerArr[x];

            TableTennisPlayer newPlayer = new TableTennisPlayer(p.first_name, p.last_name, p.age, p.serve_power, p.serve_skill, p.spin, p.forehand_power, p.backhand_power, p.fitness);
            players.add(newPlayer);
        }
    }
}
