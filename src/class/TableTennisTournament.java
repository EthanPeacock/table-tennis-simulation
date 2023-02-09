import com.google.gson.internal.bind.util.ISO8601Utils;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * This class implements the {@link Tournament} interface, defining how a table tennis tournament works.
 * @see Match
 * @see TableThread
 */
public class TableTennisTournament implements Tournament {
    /**
     * {@link ArrayList} of players taking part in tournament, set by constructor and not changed.
     */
    private final ArrayList<TableTennisPlayer> allPlayers;
    /**
     * All the players put into a pair of 2, where they will face each other with one player winning.
     */
    private ArrayList<List<TableTennisPlayer>> playerPairings = new ArrayList<List<TableTennisPlayer>>();
    /**
     * The current tables being played on, which are threads.
     * @see TableThread
     */
    private final ArrayList<TableThread> tables = new ArrayList<TableThread>();
    /**
     * The number of tables, input by the user.
     */
    private int numTables;
    /**
     * The tables which are needed - important when the number of tables is greater than the number of pairs.
     */
    private int neededTables;
    /**
     * How many pairs will play on each of the tables - evenly spread out across tables.
     */
    private int numPairsPerTable;
    /**
     * What round of the knock-out tournament are we in - not related to a {@link Match} {@link TableTennisRound}.
     */
    private int round = 1;

    /**
     * This constructor takes the all the players taking part, and calls a private function to set up the tournament.
     * @param players {@link ArrayList} of players taking part
     */
    public TableTennisTournament(ArrayList<TableTennisPlayer> players) {
        this.allPlayers = players;
        this.setupTournament();
    }

    /**
     * This method is called in the constructor, and is used to call a number of private methods which setup
     * the tournament.
     */
    private void setupTournament() {
        this.drawPlayers(); // select the player pairings
        this.getNumTables(); // gets user input for number of tables (to be used to create threads)
        this.createTables(); // create the tables
        this.splitPairs(playerPairings); // split the pairings between tables
    }

    /**
     * This method is used to draw the players, putting them into pairs.
     */
    @Override
    public void drawPlayers() {
        for (int i = 0; i < allPlayers.size(); i += 2) {
            List<TableTennisPlayer> pair = allPlayers.subList(i, i+2);
            playerPairings.add(pair);
        }
    }

    /**
     * This method gets the number of tables in the tournament from the user. This must be an even number,
     * range: 2 - number of pairs (up to the max 8 tables).
     */
    private void getNumTables() {
        boolean validInput = false;

        while (!validInput) {
            int maxTables = 8;
            try {
                Scanner inputScanner = new Scanner(System.in);
                // even because easier for logic
                if (playerPairings.size() < maxTables) {
                    maxTables = playerPairings.size();
                }
                System.out.println("Enter the number of tables to be used. (Must be even, and no greater than " + maxTables + ")");
                this.numTables = inputScanner.nextInt();
                this.neededTables = this.numTables;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please input a valid even whole number.");
            } catch (Exception e) {
                System.out.println("Unexpected error");
            } finally {
                if ((this.numTables % 2) == 0 && this.numTables <= maxTables && this.numTables != 0) {
                    validInput = true;
                } else {
                    System.out.println("Invalid input, please input a valid even whole number.");
                }
            }
        }
    }

    /**
     * This loops over the number of tables input by the user, creating instances of {@link TableThread} and setting the name
     * to the table number. These tables are saved into the class attribute {@link TableTennisTournament#tables}.
     */
    private void createTables() {
        for (int i = 0; i < this.numTables; i++) {
            TableThread newTable = new TableThread();
            newTable.setName("Table " + (i+1));
            this.tables.add(newTable);
        }
    }

    /**
     * After the first knock-out round, the threads will have completed their jobs and therefore, be in the terminated state.
     * This means they will need to be recreated, but this time to the number of needed tables as the number of tables input may now be
     * greater than the number of pairs.
     */
    private void recreateTables() {
        this.tables.removeAll(this.tables); // remove all tables
        for (int i = 0; i < this.neededTables; i++) { // neededTables is used, no need to have extra tables
            TableThread newTable = new TableThread();
            newTable.setName("Table " + (i+1));
            this.tables.add(newTable);
        }
    }

    /**
     * This method get the pairs, and splits them onto different tables ready for matches to be played,
     * @param pairings All the players in their pairs.
     */
    private void splitPairs(ArrayList<List<TableTennisPlayer>> pairings) {
        int pairSize = pairings.size();
        int numPairsPerTable = 0;

        if (this.numTables > pairSize) {
            // this has -2 because we will never have an odd number of pairings, and therefore never need an odd number of tables
            for (int tables = this.numTables - 2; tables > 0; tables -= 2) {
                if (!(tables > pairSize)) {
                    numPairsPerTable = pairSize / tables;
                    this.neededTables = tables;
                    break;
                }
            }
        } else {
            numPairsPerTable = pairSize / this.numTables;
        }

        this.numPairsPerTable = numPairsPerTable;
    }

    /**
     * Once all setup is complete, this method can be called to start all the tables so matches can be played.
     * This loops over until the final round (1v1) is complete, and a winner of the tournament is decided.
     */
    @Override
    public void startTournament() {
        boolean complete = false;
        while (!complete) {
            this.outputRoundInfo();

            int startIndex = 0;
            for (int i = 0; i < this.neededTables; i++) {
                TableThread currentTable = this.tables.get(i);
                int endIndex = (startIndex + this.numPairsPerTable);
                List<List<TableTennisPlayer>> tablePairings = this.playerPairings.subList(startIndex, endIndex);
                startIndex = endIndex;

                currentTable.setPairings(tablePairings);
                currentTable.start();
            }

            boolean error = false;
            for (int j = 0; j < this.neededTables; j++) {
                try {
                    this.tables.get(j).join();
                } catch (InterruptedException e) {
                    System.out.println("Unexpected error, exiting simulation.");
                    System.out.println(e);
                    error = true;
                    break;
                }
            }

            if (error) {
                break;
            }

            this.setPlayerPairings();

            if (this.playerPairings.size() == 1 && this.playerPairings.get(0).size() == 1) {
                complete = true;
            } else if (this.playerPairings.get(0).size() == 1) {
                ArrayList<List<TableTennisPlayer>> tempArr = new ArrayList<List<TableTennisPlayer>>();
                for (int i = 0; i < this.playerPairings.size(); i += 2) {
                    TableTennisPlayer current = this.playerPairings.get(i).get(0);
                    TableTennisPlayer next = this.playerPairings.get(i+1).get(0);
                    List<TableTennisPlayer> pair = new ArrayList<TableTennisPlayer>();
                    pair.add(current);
                    pair.add(next);
                    tempArr.add(pair);
                }
                this.playerPairings = tempArr;

                if (this.playerPairings.size() == 1) { // final pair left
                    this.neededTables = 1;
                    this.numPairsPerTable = 1;
                } else {
                    this.splitPairs(this.playerPairings);
                }

                this.recreateTables();
            } else {
                this.splitPairs(this.playerPairings);
                this.recreateTables();
            }

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                System.out.println("Unexpected error: " + e);
            }
        }

        if (complete) {
            TableTennisPlayer winner = this.playerPairings.get(0).get(0);
            String fullName = winner.getFirstName() + " " + winner.getLastName();
            System.out.println("\n\nThe tournament winner is: " + fullName + "\n");
        } // else the error was encountered
    }

    private void setPlayerPairings() {
        this.playerPairings.removeAll(this.playerPairings);
        for (int x = 0; x < this.neededTables; x++) {
            ArrayList<List<TableTennisPlayer>> winningPairs = this.tables.get(x).getWinnerPairs();
            for (List<TableTennisPlayer> winningPair : winningPairs) {
                this.playerPairings.add(winningPair);
            }
        }
    }

    private void outputRoundInfo() {
        System.out.println("\nRound " + this.round + ":");
        for (List<TableTennisPlayer> pair : this.playerPairings) {
            String p1FullName = pair.get(0).getFirstName() + " " + pair.get(0).getLastName();
            String p2FullName = pair.get(1).getFirstName() + " " + pair.get(1).getLastName();
            System.out.println(p1FullName + " vs " + p2FullName);
        }
        System.out.println();
        this.round++;
    }
}
