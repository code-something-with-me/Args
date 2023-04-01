package code.with.me.args;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentParserTest {

//    Test with valid arguments:
//    Input: -l -p 8080 -d /usr/logs
//    Expected Output: Log level enabled, port set to 8080, and log directory set to "/usr/logs".


    //    Test with mixed order of valid arguments:
//    Input: -p 8080 -d /usr/logs -l
//    Expected Output: Log level enabled, port set to 8080, and log directory set to "/usr/logs".
//
//    Test with only log level enabled:
//    Input: -l
//    Expected Output: Log level enabled, and other parameters set to default values.
    @Test
    void testWithLoggingEnabled_ifFlagPresent() {
        var argumentParser = new ArgumentParser();
        BooleanOption booleanOption = argumentParser.parse(BooleanOption.class, "-l");
        assertTrue(booleanOption.logging());
    }

    @Test
    void testWithLoggingDisabled_ifFlagNotPresent() {
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
}

record MultiOptions(@Option("-l") boolean logging, @Option("-p") int port, @Option("-d") String directory) {

}

record StringOption(@Option("-d") String directory) {

}


record BooleanOption(@Option("-l") boolean logging) {

}

record IntOption(@Option("-p") int port) {

}
