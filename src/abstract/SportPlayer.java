/**
 * {@code SportPlayer} is an abstract class, used to create a number of different sport players for different events.
 * This implements the basic shared attributes for a player in this simulation, and each implementation may hev unique
 * attributes.
 * @see TableTennisPlayer
 */
public abstract class SportPlayer {
    /**
     * A string containing a players first name.
     */
    private final String firstName;
    /**
     * A string containing a players second name.
     */
    private final String lastName;
    /**
     * An integer for the players age.
     */
    private final int age;
    /**
     * The average skill level of this player, determine by the unique attributes for different sport players.
     */
    private float averageSkill;

    /**
     * A basic constructor for setting the different shared attributes of a sports player.
     * @param firstName string containing first name.
     * @param lastName string containing last name.
     * @param age integer for age.
     */
    public SportPlayer(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    /**
     * A simple getter method to return the private attribute {@code firstName}.
     * @return players first name.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * A simple getter method to return the private attribute {@code lastName}.
     * @return players last name.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * A simple getter method to return the private attribute {@code age}.
     * @return players age
     */
    public int getAge() {
        return this.age;
    }

    /**
     * A simple getter method to return the private attribute {@code averageSkill}.
     * @return players average skill level
     */
    public float getAverageSkill() {
        return this.averageSkill;
    }

    /**
     * The method used to calculate and set {@link SportPlayer#averageSkill} using the unique attributes of each
     * sport player.
     */
    protected abstract void calculateAverageSkill();
}
