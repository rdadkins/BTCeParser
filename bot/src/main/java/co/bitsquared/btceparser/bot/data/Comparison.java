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
     * Compares a with comparison to b.
     *  Ex: Comparison = GREATER_THAN a = 10, b = 20. Returns a >= b = false
     */
    public final boolean compare(double a, double b) {
        if (this == GREATER_THAN) {
            return a >= b;
        } else {
            return a <= b;
        }
    }

}
