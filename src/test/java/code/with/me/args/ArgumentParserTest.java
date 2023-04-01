package code.with.me.args;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentParserTest {

    @Test
    void should_set_true_as_option_value() {
        var argumentParser = new ArgumentParser();
        BooleanOption booleanOption = argumentParser.parse(BooleanOption.class, "-l");
        assertTrue(booleanOption.logging());
    }

    @Test
    void should_set_false_as_option_value() {
        var argumentParser = new ArgumentParser();
        BooleanOption booleanOption = argumentParser.parse(BooleanOption.class);
        assertFalse(booleanOption.logging());
    }

    @Test
    void should_parse_int_as_option_value() {
        var argumentParser = new ArgumentParser();
        IntOption intOption = argumentParser.parse(IntOption.class, "-p", "8080");
        assertEquals(8080, intOption.port());
    }

    @Test
    void should_parse_string_as_option_value() {
        var argumentParser = new ArgumentParser();
        StringOption stringOption = argumentParser.parse(StringOption.class, "-d", "/usr/logs");
        assertEquals("/usr/logs", stringOption.directory());
    }

    @Test
    void should_parse_multi_options() {
        var argumentParser = new ArgumentParser();
        MultiOptions multiOptions = argumentParser.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(multiOptions.logging());
        assertEquals(8080, multiOptions.port());
        assertEquals("/usr/logs", multiOptions.directory());
    }

    @Test
    void should_parse_multi_in_mix_order_arguments() {
        var argumentParser = new ArgumentParser();
        MultiOptions multiOptions = argumentParser.parse(MultiOptions.class, "-p", "8080", "-l", "-d", "/usr/logs");
        assertTrue(multiOptions.logging());
        assertEquals(8080, multiOptions.port());
        assertEquals("/usr/logs", multiOptions.directory());
    }

}

record MultiOptions(@Option("-l") boolean logging, @Option("-p") int port, @Option("-d") String directory) {

}

record StringOption(@Option("-d") String directory) {

}


record BooleanOption(@Option("-l") boolean logging) {

}

record IntOption(@Option("-p") int port) {

}
