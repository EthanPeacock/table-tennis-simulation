import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a table tennis table, where 2 players can play a match. A number of pairs may be queued to play on a given
 * tables, to equally split all the players and speed up the match.
 * <p>
 *     All of this is done by extending the {@link Thread} method, which will give us a {@code run()} method to override,
 *     and most importantly allow for tables matches to run concurrently.
 * </p>
 * @see TableTennisMatch
 */
public class TableThread extends Thread {
    /**
     * The pairs of players to play on this table.
     */
    private List<List<TableTennisPlayer>> pairings;
    /**
     * All the winner put back into pairs, ready for the next knock-out round.
     */
    private ArrayList<List<TableTennisPlayer>> winnerPairs = new ArrayList<List<TableTennisPlayer>>();

    /**
     * A constructor to create a table, without giving the pairs.
     */
    public TableThread() {}

    /**
     * An alternative constructor to send the players when instantiating the object.
     * @param tablePairings the player pairs to take part on this table.
     */
    public TableThread(List<List<TableTennisPlayer>> tablePairings) {
        this.pairings = tablePairings;
    }

    /**
     * A method to set the player pairings for the table.
     * @param newPairings player pairs
     */
    public void setPairings(List<List<TableTennisPlayer>> newPairings) {
        this.pairings = newPairings;
    }

    /**
     * The method called when starting a thread, containing all logic for setting up matches for a given pair and storing
     * the result.
     */
    @Override
    public void run() {
        List<TableTennisPlayer> winningPairs = new ArrayList<TableTennisPlayer>();

        boolean empty = false;
        if (this.pairings.size() == 1) {
            empty = true; // we're only returning 1 winner
        }

        for (List<TableTennisPlayer> pair : this.pairings) {
            TableTennisMatch currentMatch = new TableTennisMatch(pair);
            currentMatch.playMatch();
            TableTennisPlayer winner = currentMatch.getWinner();

            System.out.println(currentMatch.toString());

            if (!empty) {
                winningPairs = new ArrayList<TableTennisPlayer>(); // winningPairs.removeAll(winningPairs); was used, but was breaking
                empty = true;
                assert winningPairs != null;
                winningPairs.add(winner);
            } else {
                empty = false;
                winningPairs.add(winner);
                this.winnerPairs.add(winningPairs);
            }
        }
    }

    /**
     * A simple getter method to get the winners which have been put into pairs.
     * @return winning pairs
     */
    public ArrayList<List<TableTennisPlayer>> getWinnerPairs() {
        return this.winnerPairs;
    }
}
