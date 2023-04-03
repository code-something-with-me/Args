package code.with.me.args;

public class UnsupportedOptionTypeException extends RuntimeException {


    public UnsupportedOptionTypeException(Option option, Class<?> type) {
        super(String.format("Unsupported option %s type: %s", option.value(), type));
    }


}
