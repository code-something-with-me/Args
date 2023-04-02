package code.with.me.args;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static code.with.me.args.BooleanOptionParserTest.option;
import static org.junit.jupiter.api.Assertions.*;

class SingleValueParserTest {

    @Test
    void should_not_accept_extra_arguments_for_single_value_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                () -> new SingleValueParser<>(0, Integer::parseInt)
                        .parse(List.of("-p", "8080", "8081"), option("-p")));
        assertEquals("-p", e.getOption().value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-p -d", "-p"})
    void should_not_accept_insufficient_arguments_for_single_value_option(String arguments) {
        InsufficientArgumentsException e = assertThrows(InsufficientArgumentsException.class,
                () -> new SingleValueParser<>(0, Integer::parseInt)
                        .parse(Arrays.stream(arguments.split(" ")).toList(), option("-p")));
        assertEquals("-p", e.getOption().value());
    }

    @Test
    void should_set_default_value_as_0_if_int_option_is_not_present() {
        assertEquals(0, new SingleValueParser<>(0, Integer::parseInt).parse(List.of(), option("-p")));
    }


    @Test
    void should_not_accept_extra_arguments_for_string_single_value_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                () -> new SingleValueParser<>(0, Integer::parseInt)
                        .parse(List.of("-d", "/usr/logs", "/usr/vars"), option("-d")));
        assertEquals("-d", e.getOption().value());
    }

}