package code.with.me.args;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentParserTest {


    private ArgumentParser argumentParser;

    @BeforeEach
    void setUp() {
        argumentParser = new ArgumentParser();
    }

    @Test
    void should_parse_multi_options() {
        MultiOptions multiOptions = this.argumentParser.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(multiOptions.logging());
        assertEquals(8080, multiOptions.port());
        assertEquals("/usr/logs", multiOptions.directory());
    }

    @Test
    void should_parse_multi_in_mix_order_arguments() {
        MultiOptions multiOptions = this.argumentParser.parse(MultiOptions.class, "-p", "8080", "-l", "-d", "/usr/logs");
        assertTrue(multiOptions.logging());
        assertEquals(8080, multiOptions.port());
        assertEquals("/usr/logs", multiOptions.directory());
    }

    @Test
    void should_throw_exception_if_option_annotation_is_not_present() {
        IllegalOptionException e = assertThrows(IllegalOptionException.class,
                () -> this.argumentParser.parse(MultiOptionsWithoutAnnotation.class, "-l", "-p", "8080", "-d", "/usr/logs"));
        assertEquals("logging", e.getParameter());
    }

    @Test
    void should_parse_list_options() {
        ListOptions listOptions = argumentParser.parse(ListOptions.class, "-g", "admin", "root", "-d", "-1", "1", "2", "3");
        assertArrayEquals(new String[]{"admin", "root"}, listOptions.group());
        assertArrayEquals(new Integer[]{-1, 1, 2, 3}, listOptions.decimals());
    }

}

record ListOptions(@Option("-g") String[] group, @Option("-d") Integer[] decimals) {

}

record MultiOptionsWithoutAnnotation(boolean logging, @Option("-p") int port, @Option("-d") String directory) {

}

record MultiOptions(@Option("-l") boolean logging, @Option("-p") int port, @Option("-d") String directory) {

}


