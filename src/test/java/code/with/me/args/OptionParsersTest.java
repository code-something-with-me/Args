package code.with.me.args;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OptionParsersTest {

    @Nested
    class UnaryOptionParser {
        @Test
        void should_not_accept_extra_arguments_for_single_value_option() {
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                    () -> OptionParsers.unary(Integer::parseInt, 0)
                            .parse(List.of("-p", "8080", "8081"), option("-p")));
            assertEquals("-p", e.getOption().value());
        }

        @ParameterizedTest
        @ValueSource(strings = {"-p -d", "-p"})
        void should_not_accept_insufficient_arguments_for_single_value_option(String arguments) {
            InsufficientArgumentsException e = assertThrows(InsufficientArgumentsException.class,
                    () -> OptionParsers.unary(Integer::parseInt, 0)
                            .parse(Arrays.stream(arguments.split(" ")).toList(), option("-p")));
            assertEquals("-p", e.getOption().value());
        }

        @Test
        void should_set_default_value_as_0_if_int_option_is_not_present() {
            assertEquals(0, OptionParsers.unary(Integer::parseInt, 0).parse(List.of(), option("-p")));
        }


        @Test
        void should_parse_value_if_option_is_present() {
            assertEquals(8080, OptionParsers.unary(Integer::parseInt, 0).parse(List.of("-p", "8080"), option("-p")));
        }

    }

    @Nested
    class BooleanOptionParser {

        @Test
            // sad path
        void should_not_accept_extra_arguments_for_boolean_option() {
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                    () -> OptionParsers.bool().parse(List.of("-l", "true"), option("-l")));
            assertEquals("-l", e.getOption().value());
        }

        @Test
            // default value
        void should_set_default_value_as_false_if_option_is_not_present() {
            assertFalse(OptionParsers.bool().parse(List.of(), option("-l")));
        }

        @Test
            // happy path
        void should_set_value_as_true_if_option_is_present() {
            assertTrue(OptionParsers.bool().parse(List.of("-l"), option("-l")));
        }


    }

    static Option option(final String value) {
        return new Option() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return Option.class;
            }

            @Override
            public String value() {
                return value;
            }
        };
    }

}