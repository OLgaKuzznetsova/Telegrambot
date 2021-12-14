public class Response {
    private String message;
    private String indicator;

    public Response(String message, String indicator) {
        this.message = message;
        this.indicator = indicator;
    }

    public String  getMessage() {
        return message;
    }

    public String  getIndicator() {
        return indicator;
    }
}

