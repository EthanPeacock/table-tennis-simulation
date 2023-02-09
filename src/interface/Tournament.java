/**
 * {@code Tournament} is an interface used to define a range of different sport tournaments. This was used over an abstract
 * class because not many tournaments will share concrete methods, or attributes.
 * <p>
 *     A tournament will consist of many matches ({@link Match}) between players (whether this be 1v1, teams, ...) and conclude an overall
 *     winner. The logic of how matches are setup and the winner is decided is unique to the implementation.
 * </p>
 * @see TableTennisTournament
 */
public interface Tournament {
    /**
     * Splits the players ready for a set of {@link Match}'s.
     * <p>
     *     Given a number of players, they must be split ready for matches to be played. As mentioned, depending on the
     *     sport being played this may be splitting into pairs, 2 teams, 4 teams, etc.
     * </p>
     */
    void drawPlayers();

    /**
     * Starts the tournament.
     * <p>
     *     This method will be used to start the tournament, creating tables/pitches/courts/... where matches will then be played.
     *     This method is called after all setup is complete, such as {@link Tournament#drawPlayers()}.
     * </p>
     */
    void startTournament();
}
