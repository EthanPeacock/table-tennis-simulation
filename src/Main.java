import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The {@code Main} class is used when the program is run and is responsible for creating getting the number of players
 * and tournament creation.
 * <p>
 *    A user will input a number of players to take place in the tournament, which must be an even integer and follow
 *    the pattern {@code 4, 8, 16, 32, 64, 128, ...}. Follwing a valid input, the simulation is begun by the
 *    instantiation of {@link TableTennisTournament}.
 * </p>
 */
public class Main {
    /**
     * The number of players to take part in the tournament, updated in a method getting user input ({@link Main#numPlayers}).
     */
    public static int numPlayers = 0;
    /**
     * An {@link ArrayList} of {@link TableTennisPlayer}'s taking part in a knock-out tournament.
     */
    private static ArrayList<TableTennisPlayer> players = new ArrayList<TableTennisPlayer>();

    /**
     * The method called when the program is run and is responsible for the following:
     * <ul>
     *     <li>Get number of players ({@link Main#numPlayers}) using private static method {@link Main#numPlayersInput()}</li>
     *     <li>Fetch the players using {@link GetPlayerData}'s static method {@link GetPlayerData#fetch(int numPlayers)}</li>
     *     <li>Start the simulation, using the private static method {@link Main#startSimulation()}</li>
     * </ul>
     * <p>
     *     A {@code while} loop is used to keep getting the number of players, until a valid input is made and the attribute
     *     {@link Main#numPlayers} is no longer equal to {@code 0}.
     * </p>
     * @param args default
     */
    public static void main(String[] args) {
        System.out.println("Table Tennis Simulation\n");

        while (numPlayers == 0) {
            numPlayers = numPlayersInput();
        }

        players = GetPlayerData.fetch(numPlayers);

        if (players != null) {
            startSimulation();
        } else {
            System.out.println("Program exit");
        }
    }

    /**
     * A method used to get a number of players from the user (using {@link Scanner}).
     * <p>
     *     If the user does input a valid number of players ({@code 4, 8, 16, 32, ...}), then this will be returned
     *     and the loop in {@link Main#main(String[])} will end. However, if the input is invalid then {@code 0} will
     *     be returned and the loop will continue, running this method again.
     * </p>
     * @return number of players, either a valid number ({@code 4, 8, 16, 32, ...}) or 0
     * (while loop in {@link Main#main(String[])} will continue).
     */
    private static int numPlayersInput() {
        System.out.println("Input the number of players (e.g. 4, 8, 16, 32, ...)");
        int numInput = 0;

        try {
            Scanner inputScanner = new Scanner(System.in);
            numInput = inputScanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input, must be a whole positive number.\n");
            return 0;
        } finally {
            if (numInput < 4) {
                System.out.println("Invalid input, must be a valid number of pairs (4, 8, 16, ...).\n");
                numInput = 0; // this is done because you cannot return here
            } else {
                float knockoutPairs = (float) (Math.log(numInput) / Math.log(2));

                if ((knockoutPairs % 1) != 0) {
                    System.out.println("Invalid input, must be a valid number of pairs (4, 8, 16, ...).\n");
                    numInput = 0; // this is done because you cannot return here
                }
            }
        }

        return numInput;
    }

    /**
     * Method used to create a tournament object, using the {@link TableTennisTournament} class, and to start it.
     */
    private static void startSimulation() {
        TableTennisTournament tournament = new TableTennisTournament(players);
        tournament.startTournament();
        System.out.println("\nEnd of simulation.");
    }
}
