package code.with.me.args;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

class OptionParsers {

    public static OptionParser<Boolean> bool() {
        return (arguments, option) -> values(arguments, option, 0).isPresent();
    }

    public static <T> OptionParser<T> unary(Function<String, T> valueParser, T defaultValue) {
        return (arguments, option) -> values(arguments, option, 1)
                .map(it -> parseValue(valueParser, option, it.get(0)))
                .orElse(defaultValue);
    }

    public static <T> OptionParser<T[]> list(IntFunction<T[]> generator, Function<String, T> valueParser) {
        return (arguments, option) -> values(arguments, option)
                .map(it -> it.stream().map(v -> parseValue(valueParser, option, v)).toArray(generator))
                .orElse(generator.apply(0));
    }

    private static Optional<List<String>> values(List<String> arguments, Option option, int expectedSize) {
        int idx = arguments.indexOf(option.value());
        if (idx == -1) {
            return Optional.empty();
        }
        return Optional.of(values(arguments, idx))
                .map(it -> checkSize(option, expectedSize, it));
    }

    private static List<String> checkSize(Option option, int expectedSize, List<String> values) {
        if (values.size() > expectedSize) {
            throw new TooManyArgumentsException(option);
        }
        if (values.size() < expectedSize) {
            throw new InsufficientArgumentsException(option);
        }
        return values;
    }

    private static Optional<List<String>> values(List<String> arguments, Option option) {
        int idx = arguments.indexOf(option.value());
        return idx == -1 ? Optional.empty() : Optional.of(values(arguments, idx));
    }

    private static List<String> values(List<String> arguments, int idx) {
        final int flowingFlag = IntStream.range(idx + 1, arguments.size())
                .filter(i -> arguments.get(i).matches("^-[a-zA-Z]+$"))
                .findFirst()
                .orElse(arguments.size());

        return arguments.subList(idx + 1, flowingFlag);
    }

    private static <T> T parseValue(Function<String, T> valueParser, Option option, String value) {
        try {
            return valueParser.apply(value);
        } catch (Exception e) {
            throw new IllegalValueException(option, value);
        }
    }
}
