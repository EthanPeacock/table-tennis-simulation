import java.util.Random;

/**
 * This class is used to hold a round between 2 players, of which there are multiple in a match. The winner is calculated,
 * and returned.
 * <p>
 *     This class doesn't inherit from an abstract round class, this is because the way a round has been implemented is with the
 *     use of static methods, and abstract classes and interfaces do not allow for static methods. Therefore, each sport type will
 *     have rounds manually implemented.
 * </p>
 * @see Match
 * @see TableTennisTournament
 */
public class TableTennisRound {
    /**
     * The static method used to play a round between 2 players.
     * @param p1 Player 1 in round
     * @param p2 Player 2 in round
     * @return The winner of this match.
     */
    public static TableTennisPlayer playRound(TableTennisPlayer p1, TableTennisPlayer p2) {
        float p1Average = p1.getAverageSkill();
        float p2Average = p2.getAverageSkill();

        float diff = p1Average - p2Average;
        int winner = getWinner(p1Average, p2Average, diff);
        if (winner == 1) {
            return p1;
        } else {
            return p2;
        }
    }

    /**
     * The private method used to calculate the winner, given 2 players and their {@code averageSkill}.
     * <p>If the difference between Player 1's and Player 2's average skill is less than 1:</p>
     * <ul>
     *     <li>The range becomes - lower bound: {@code p1Average - 1}, upper bound: {@code p2Average + 1}</li>
     *     <li>A random integer and random float are generated ({@link Random}). Combined, this number must be in the
     *     range defined above. If it is not, this step restarts - attempts are tracked, and at 10 attempts the loop
     *     is exited and a random number is generated.</li>
     *     <li>If the attemps is 10, and the random number is even then player 1 wins. Else, player 2 wins. This was introduced
     *     to reduce the time it was taking to decide a winning in some cases.</li>
     *     <li>If the random number is smaller than the {@code p1Average} then player 1 wins, else player 2 wins.</li>
     * </ul>
     * @param p1Average Player 1's average skill
     * @param p2Average Player 2's average skill
     * @param diff The difference in average skill between player 1 and player 2.
     * @return The winner (1: player 1, 2: player 2)
     */
    private static int getWinner(float p1Average, float p2Average, float diff) {
        int attempts = 0;
        if (diff == 0 || (diff < 1 && diff > 0) || (diff > -1 && diff < 0)) {
            float p1Min = (float) (p1Average - 1);
            float p2Max = (float) (p2Average + 1);

            boolean validRandom = false;
            float randomNum = 0;
            while (!validRandom) {
                attempts += 1;
                int randomInt = new Random().nextInt(10);
                float randomFloat = new Random().nextFloat();
                randomNum = randomInt + randomFloat;
                if (randomNum >= p1Min && randomNum <= p2Max) {
                    validRandom = true;
                }
                if (attempts >= 10) {
                    break;
                }
            }

            if (attempts >= 10) {
                int randInt = new Random().nextInt(10);
                if ((randInt % 2) == 0) {
                    return 1;
                } else {
                    return 2;
                }
            }

            if (randomNum < p1Average) {
                return 1;
            } else {
                return 2;
            }
        } else {
            float p1Min = (float) (p1Average - 1);
            float p1Max = (float) (p1Average + 1);
            float p2Min = (float) (p2Average - 1);
            float p2Max = (float) (p2Average + 1);

            boolean validRandom = false;
            float randomNum = 0;
            while (!validRandom) {
                attempts += 1;
                int randomInt = new Random().nextInt(10);
                float randomFloat = new Random().nextFloat();
                randomNum = randomInt + randomFloat;
                if (randomNum >= p1Min && randomNum <= p2Max) {
                    validRandom = true;
                }
                if (attempts >= 10) {
                    break;
                }
            }

            if (attempts >= 10) {
                int randInt = new Random().nextInt(10);
                if ((randInt % 2) == 0) {
                    return 1;
                } else {
                    return 2;
                }
            }

            if (randomNum >= p1Min && randomNum <= p1Max) {
                return 1;
            } else {
                return 2;
            }
        }
    }
}
