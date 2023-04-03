package code.with.me.args;

public class IllegalValueException extends RuntimeException {
    public Option getOption() {
        return option;
    }

    public String getValue() {
        return value;
    }

    private final Option option;
    private final String value;

    public IllegalValueException(Option option, String value) {
        this.option = option;
        this.value = value;
    }
}
