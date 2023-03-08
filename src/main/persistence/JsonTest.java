package persistence;

import model.ColourScheme;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// tests for Json related classes
// NOTE: all Json related code is heavily based on JsonSerializationDemo provided
public class JsonTest {
    // EFFECTS: checks that two colour schemes are identical
    protected void checkSameColourScheme(ColourScheme cs1, ColourScheme cs2) {
        assertEquals(cs1.getName(), cs2.getName());
        assertEquals(cs1.getSize(), cs2.getSize());
        for (int i = 0; i < cs1.getSize(); i++) {
            assertTrue(cs1.getColourAtIndex(i).compareColours(cs2.getColourAtIndex(i)));
        }
    }
}
