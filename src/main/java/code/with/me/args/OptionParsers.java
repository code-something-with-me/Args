package code.with.me.args;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

class OptionParsers {

    public static OptionParser<Boolean> bool() {
        return (arguments, option) -> values(arguments, option, 0).isPresent();
    }

    public static <T> OptionParser<T> unary(Function<String, T> valueParser, T defaultValue) {
        return (arguments, option) -> values(arguments, option, 1)
                .map(it -> valueParser.apply(it.get(0)))
                .orElse(defaultValue);
    }

    private static Optional<List<String>> values(List<String> arguments, Option option, int expectedSize) {
        int idx = arguments.indexOf(option.value());
        if (idx == -1) {
            return Optional.empty();
        }
        List<String> values = values(arguments, idx);
        if (values.size() > expectedSize) {
            throw new TooManyArgumentsException(option);
        }
        if (values.size() < expectedSize) {
            throw new InsufficientArgumentsException(option);
        }
        return Optional.of(values);
    }

    private static List<String> values(List<String> arguments, int idx) {
        final int flowingFlag = IntStream.range(idx + 1, arguments.size())
                .filter(i -> arguments.get(i).startsWith("-"))
                .findFirst().orElse(arguments.size());

        return arguments.subList(idx + 1, flowingFlag);
    }

}
