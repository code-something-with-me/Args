package code.with.me.args;

public class IllegalOptionException extends RuntimeException {

    private final String parameter;

    public String getParameter() {
        return parameter;
    }

    public IllegalOptionException(String parameter) {
        this.parameter = parameter;
    }
}
