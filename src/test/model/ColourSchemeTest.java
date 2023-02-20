package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ColourSchemeTest {
    private ColourScheme testScheme;
    private Colour testColourA;
    private Colour testColourB;

    @BeforeEach
    void runBefore() {
        testScheme = new ColourScheme();
        testColourA = new Colour(20,120,220);
        testColourB = new Colour(100, 150, 120);
        testScheme.addColour(testColourA);
        testScheme.addColour(testColourB);
    }

    @Test
    void testGetScheme() {
        List<Colour> scheme = testScheme.getScheme();
        assertEquals(testColourA, scheme.get(0));
        assertEquals(testColourB, scheme.get(1));
        assertEquals(2, scheme.size());
    }

    @Test
    void testSetName() {
        testScheme.setName("My Colour Scheme");
        assertEquals("My Colour Scheme", testScheme.getName());
        testScheme.setName("Changed Name");
        assertEquals("Changed Name", testScheme.getName());
    }

    @Test
    void testGetSize() {
        assertEquals(2, testScheme.getSize());
        testScheme.addColour(testColourA);
        assertEquals(3, testScheme.getSize());
    }

    @Test
    void testGetColourAtIndex() {
        assertEquals(testColourA, testScheme.getColourAtIndex(0));
        assertEquals(testColourB, testScheme.getColourAtIndex(1));
    }
}