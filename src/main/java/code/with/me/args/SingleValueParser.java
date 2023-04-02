package code.with.me.args;

import java.util.List;
import java.util.function.Function;

class SingleValueParser<T> implements OptionParser<T> {

    T defaultValue;
    Function<String, T> valueParser;


    public SingleValueParser(T defaultValue, Function<String, T> valueParser) {
        this.defaultValue = defaultValue;
        this.valueParser = valueParser;
    }

    @Override
    public T parse(List<String> arguments, Option option) {
        int idx = arguments.indexOf(option.value());
        if (idx == -1) {
            return defaultValue;
        }
        if (arguments.size() <= idx + 1 || arguments.get(idx + 1).startsWith("-")) {
            throw new InsufficientArgumentsException(option);
        }

        if (arguments.size() > idx + 2 && !arguments.get(idx + 2).startsWith("-")) {
            throw new TooManyArgumentsException(option);
        }
        return valueParser.apply(arguments.get(idx + 1));
    }

}
