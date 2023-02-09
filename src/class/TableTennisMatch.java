import java.util.List;

/**
 * This class extends the abstract class {@link Match}, implementing the logic for a table tennis match.
 * <p>
 *     This class will be instantiated, and then the match will be played. The given pair of players will face
 *     a "best of 11" (first player to 6) set of {@link TableTennisRound}. The scores for each player will be kept,
 *     and after every round has been played the winner will be set.
 * </p>
 * @see Match
 * @see TableTennisRound
 */
public class TableTennisMatch extends Match {
    /**
     * The {@link TableTennisPlayer} who won the match.
     */
    private TableTennisPlayer winner;

    /**
     * Sets the players for the match, and the number of rounds to 11.
     * @param players pair of players taking part in this match.
     */
    public TableTennisMatch(List<TableTennisPlayer> players) {
        super(players, 11);
    }

    /**
     * The method used to start the match, looping over the rounds, and determining the winner.
     * <p>
     *     Using the pair of players sent when instantiating the object, a loop runs for the number of rounds
     *     until one of the players reaches the match winning, 6 round wins. The winner attribute is then set to the player
     *     who won, and nothing is returned because in the abstract method we cannot define an unknown return type.
     * </p>
     */
    @Override
    public void playMatch() {
        TableTennisPlayer p1 = (TableTennisPlayer) this.players.get(0);
        TableTennisPlayer p2 = (TableTennisPlayer) this.players.get(1);
        int p1Wins = 0;
        int p2Wins = 0;

        for (int i = 1; i <= this.numRounds; i++) {
            TableTennisPlayer winner = TableTennisRound.playRound(p1, p2);
            if (winner == p1) {
                p1Wins += 1;
            } else {
                p2Wins += 1;
            }
            if (p1Wins == 6 || p2Wins == 6) {
                break;
            }
        }

        if (p1Wins > p2Wins) {
            this.winner = p1;
        } else {
            this.winner = p2;
        }

        // doesn't return because abstract method (we can't define the return type because it will be different)
    }

    /**
     * This method is used to retrieve the match details after it has been played.
     * <p>
     *     The full names of both players are created, using getter methods within {@link TableTennisPlayer}.
     *     These are then output, along with the winner of the match.
     * </p>
     * @return the string containing match information, to be ouput.
     */
    @Override
    public String toString() {
        TableTennisPlayer p1 = (TableTennisPlayer) this.players.get(0);
        String p1Name = p1.getFirstName() + " " + p1.getLastName();
        TableTennisPlayer p2 = (TableTennisPlayer) this.players.get(1);
        String p2Name = p2.getFirstName() + " " + p2.getLastName();
        String winnerName = getWinner().getFirstName() + " " + getWinner().getLastName();

        return "[" + Thread.currentThread().getName() + ": " + p1Name + " v " + p2Name + "] " + winnerName + " has won the match.";
    }

    /**
     * An additional method added to return the private attribute winner, containing the {@link TableTennisPlayer} object.
     * @return player object
     */
    public TableTennisPlayer getWinner() {
        return this.winner;
    }
}
