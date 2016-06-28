package co.bitsquared.btceparser.core.response;

public class Response {

    public final int success;

    /**
     * By default, a Response is successful unless stated otherwise
     */
    public Response() {
        this(1);
    }

    /**
     * Create a response with a success factor included
     * @param success a number in the range of [0...1] representing the success of this response
     */
    public Response(int success) {
        this.success = success;
    }

    /**
     * @return the status of this parsed response being successful based on the API return status of success
     */
    public final boolean isSuccessful() {
        return success == 1;
    }

}
