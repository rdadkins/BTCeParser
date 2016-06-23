package co.bitsquared.btceparser.trade.data;

public enum TransactionType {

    DEPOSIT(1),
    WITHDRAWAL(2),
    CREDIT(4),
    DEBIT(5);

    private final int TYPE_NUMBER;

    TransactionType(int type) {
        TYPE_NUMBER = type;
    }

    /**
     * Converts an integer to a TransactionType.
     * @param type an integer representing a TransactionType
     * @return an extracted TransactionType if type is a valid value
     */
    public static TransactionType extract(int type) {
        for (TransactionType transactionType: values()) {
            if (transactionType.TYPE_NUMBER == type) {
                return transactionType;
            }
        }
        return null;
    }

}
