import java.util.List;

/**
 * {@code Match} is an abstract class for all types of {@link Tournament}, allowing {@code x} number of players to
 * play against each other for {@code y} number of rounds.
 * @see TableTennisMatch
 */
public abstract class Match {
    /**
     * A {@link List} of players taking part in the match, made up of a classes which inherit {@link SportPlayer}.
     */
    public final List<?> players;
    /**
     * The number of rounds each match will be made up of.
     */
    public final int numRounds;

    /**
     * The constructor to setup the match, setting the attributes.
     * @param players The list of player taking part in the match.
     * @param numRounds The number of rounds to take place in this match.
     */
    public Match(List<?> players, int numRounds) {
        this.players = players;
        this.numRounds = numRounds;
    }

    /**
     * This will contain the logic for playing a match, running a number of rounds ({@link Match#numRounds}), and
     * deciding a winner. However, a winner is not returned and the implementation of storing a winning and getting it
     * is unique to the match (the winner may be a player, a team, a pair, etc.).
     */
    public abstract void playMatch();

    /**
     * Used to get the details of the match e.g., players taking part, winner, etc.
     * @return A string to be output, showing the details of the match.
     */
    public abstract String toString();
}
