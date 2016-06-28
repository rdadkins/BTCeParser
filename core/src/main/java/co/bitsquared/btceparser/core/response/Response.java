package co.bitsquared.btceparser.core.response;

public class Response {

    public final int success;

    public final String error;

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
        this(success, null);
    }

    /**
     * Create a response with a success factor and an optional error message
     * @param success a number in the range of [0...1] representing the success of this response
     * @param error a nullable string representing the error of this response
     */
    public Response(int success, String error) {
        this.success = success;
        this.error = error;
    }

    /**
     * Determines whether this response is a successful response or not. This should always be called before accessing
     * other fields of response.
     * @return the status of this parsed response being successful based on the API return status of success.
     */
    public final boolean isSuccessful() {
        return success == 1;
    }

    /**
     * @return the error of this response if it exists. If this is null, then there was no error provided upon a response from
     * an OkRequest. This shouldn't be populated if {@link #isSuccessful()} is true
     */
    public final String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "Response{" +
                "success=" + success +
                ", error='" + error + '\'' +
                '}';
    }

}
