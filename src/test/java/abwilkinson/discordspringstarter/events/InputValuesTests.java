package abwilkinson.discordspringstarter.events;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputValuesTests {

    @Test
    public void testSpacesAreSplitIntoCommandAndParam() {
        InputValues values = new InputValues("command parameter");
        assertEquals("command", values.getCommand());
        assertEquals("parameter", values.getParameter());
    }
}
