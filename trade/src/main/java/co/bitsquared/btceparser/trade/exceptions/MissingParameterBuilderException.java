package co.bitsquared.btceparser.trade.exceptions;

public class MissingParameterBuilderException extends RuntimeException {

    private static final String MESSAGE = "ParameterBuilder was not supplied. You must call assignParameters() or supply processRequest with a ParameterBuilder.";

    public MissingParameterBuilderException() {
        super(MESSAGE);
    }

}
