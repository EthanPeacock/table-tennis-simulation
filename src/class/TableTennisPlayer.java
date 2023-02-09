/**
 * This class extends the abstract class {@link SportPlayer} and is used to define the unique attributes for a
 * table tennis player.
 * @see SportPlayer
 */
public class TableTennisPlayer extends SportPlayer {
    private final int servePower;
    private final int serveSkill;
    private final int spin;
    private final int forehand;
    private final int backhand;
    private final int fitness;
    /**
     * A calculated value of skill, calculated by adding all attributes and dividing by the number of attributes.
     * <p>
     *     {@link TableTennisRound} uses this to weigh the chances of winning against another player.
     * </p>
     */
    public float averageSkill;

    /**
     * Takes all the attributes of a table tennis player, other than {@code averageSkill} which is calculated after
     * setting all attributes ({@link TableTennisPlayer#calculateAverageSkill()}).
     * @param firstName string containing first name
     * @param lastName string containing last name
     * @param age integer for players age
     * @param servePower integer for power in serve
     * @param serveSkill integer for skill in serve
     * @param spin integer for skill in spin
     * @param forehand integer for skill in forehand
     * @param backhand integer for skill in backhand
     * @param fitness integer for fitness
     */
    public TableTennisPlayer(String firstName, String lastName, int age, int servePower, int serveSkill, int spin, int forehand, int backhand, int fitness) {
        super(firstName, lastName, age);
        this.servePower = servePower;
        this.serveSkill = serveSkill;
        this.spin = spin;
        this.forehand = forehand;
        this.backhand = backhand;
        this.fitness = fitness;
        this.calculateAverageSkill();
    }

    /**
     * Implements the abstract method from {@link Match}, using all skill attributes and dividing them by the number of
     * attributes giving the {@code averageSkill}.
     */
    @Override
    protected void calculateAverageSkill() {
        int total = this.servePower + this.serveSkill + this.spin + this.forehand + this.backhand + this.fitness;
        this.averageSkill = (float) (total / 6);
    }

    /**
     * Implements the abstract method from {@link Match}, returning the {@code averageSkill}.
     * @return the average skill for this player.
     */
    @Override
    public float getAverageSkill() {
        return this.averageSkill;
    }
}
