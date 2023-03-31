package code.with.me.args;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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


//    Test with only port specified:
//    Input: -p 8080
//    Expected Output: Port set to 8080, and other parameters set to default values.
//    Test with only log directory specified:
//    Input: -d /usr/logs
//    Expected Output: Log directory set to "/usr/logs", and other parameters set to default values.

}


record BooleanOption(@Option("-l") boolean logging) {

}

