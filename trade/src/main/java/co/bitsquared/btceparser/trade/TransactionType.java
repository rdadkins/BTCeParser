package co.bitsquared.btceparser.trade;

public enum TransactionType {

    DEPOSIT(1),
    WITHDRAWAL(2),
    CREDIT(4),
    DEBIT(5);

    private int typeNumber;

    TransactionType(int type) {
        typeNumber = type;
    }

    public int getTypeNumber() {
        return typeNumber;
    }

    public static TransactionType extract(int type) {
        for (TransactionType transactionType: values()) {
            if (transactionType.typeNumber == type) {
                return transactionType;
            }
        }
        return null;
    }

}
