/**
 * This class is used in the process of retrieving the player from from a {@code JSON} file.
 * Each of the different field contained in the player data file is listed as a public method, and will be used
 * to create {@link TableTennisPlayer} objects for each.
 * @see GetPlayerData
 * @see TableTennisPlayer
 */
public class ReadPlayerData {
    public int id;
    public String first_name;
    public String last_name;
    public int serve_power;
    public int serve_skill;
    public int spin;
    public int forehand_power;
    public int backhand_power;
    public int fitness;
    public int age;
}
