package co.bitsquared.btceparser.bot.data;

public enum Comparison {

    /**
     * Comparison is based on values being greater than OR equal to some value.
     */
    GREATER_THAN,

    /**
     * Comparison is based on values being less than OR equal to.
     */
    LESS_THAN;

    /**
     * Compares a with comparison to b by general math comparisons
     * @param a the left side of the comparison
     * @param b the right side of the comparison
     * @return the comparison status of a compared to b
     */
    public final boolean compare(double a, double b) {
        if (this == GREATER_THAN) {
            return a >= b;
        } else {
            return a <= b;
        }
    }

}
