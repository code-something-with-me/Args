package code.with.me.args;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentParserTest {



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


