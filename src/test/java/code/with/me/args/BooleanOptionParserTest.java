package code.with.me.args;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BooleanOptionParserTest {

    @Test
    void should_not_accept_extra_arguments_for_boolean_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                () -> new BooleanOptionParser().parse(List.of("-l", "true"), option("-l")));
        assertEquals("-l", e.getOption().value());
    }

    @Test
    void should_set_default_value_as_false_if_option_is_not_present() {
        assertFalse(new BooleanOptionParser().parse(List.of(), option("-l")));
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