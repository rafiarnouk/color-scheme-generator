package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Colour.MONO_AMOUNT;
import static model.Colour.SHIFT_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// tests for Colour class
class ColourTest {
    private Colour testColour;
    private Colour testColourB;
    private Colour testColourC;

    @BeforeEach
    void runBefore() {
        testColour = new Colour(100,50,120);
        testColourB = new Colour(40,120,180);
        testColourC = new Colour(100,220,150);
    }

    @Test
    void testGetColourPrintCodeLowSpaces() {
        String result = testColour.getColourPrintCode(2);
        String expected = "\033[48;2;100;50;120m  \u001b[0m";
        assertEquals(expected, result);
    }

    @Test
    void testGetColourPrintCodeHighSpaces() {
        String result = testColour.getColourPrintCode(5);
        String expected = "\033[48;2;100;50;120m     \u001b[0m";
        assertEquals(expected, result);
    }

    @Test
    void testComplementary() {
        ColourScheme complementary = testColour.complementaryScheme();
        Colour inverted = new Colour(155, 205, 135);

        assertTrue(compareColours(testColour, complementary.getColourAtIndex(0)));
        assertTrue(compareColours(inverted, complementary.getColourAtIndex(1)));

        assertEquals(2, complementary.getSize());
        assertEquals("Complementary (100, 50, 120)", complementary.getName());
    }

    @Test
    void testTriadic()  {
        ColourScheme triadic = testColour.triadicScheme();
        Colour before = new Colour(50, 120, 100);
        Colour after = new Colour(120,100,50);

        assertTrue(compareColours(before, triadic.getColourAtIndex(0)));
        assertTrue(compareColours(testColour, triadic.getColourAtIndex(1)));
        assertTrue(compareColours(after, triadic.getColourAtIndex(2)));

        assertEquals(3, triadic.getSize());
        assertEquals("Triadic (100, 50, 120)", triadic.getName());
    }

    @Test
    void testMonochrome() {
        ColourScheme monochrome = testColour.monochromeScheme();
        ColourScheme expected = new ColourScheme();

        int lighterR = 100 / MONO_AMOUNT;
        int lighterG = 50 / MONO_AMOUNT;
        int lighterB = 120 / MONO_AMOUNT;
        for (int i = 0; i < MONO_AMOUNT; i++) {
            expected.addColour(new Colour(lighterR * i, lighterG * i, lighterB * i));
        }

        int darkerR = 155 / MONO_AMOUNT;
        int darkerG = 205 / MONO_AMOUNT;
        int darkerB = 135 / MONO_AMOUNT;
        for (int i = 0; i <= MONO_AMOUNT; i++) {
            expected.addColour(new Colour(100 + darkerR * i, 50 + darkerG * i, 120 + darkerB * i));
        }

        for (int i = 0; i < monochrome.getSize(); i++) {
            assertTrue(compareColours(expected.getColourAtIndex(i), monochrome.getColourAtIndex(i)));
        }

        assertEquals(MONO_AMOUNT * 2 + 1, monochrome.getSize());
        assertEquals("Monochrome (100, 50, 120)", monochrome.getName());
    }
    
    @Test
    void testAnalogousRedIsMiddleValue() {
        ColourScheme analogous = testColour.analogousScheme();
        Colour before = new Colour(100 - SHIFT_SIZE, 50, 120);
        Colour after = new Colour(100 + SHIFT_SIZE,50,120);

        assertTrue(compareColours(before, analogous.getColourAtIndex(0)));
        assertTrue(compareColours(testColour, analogous.getColourAtIndex(1)));
        assertTrue(compareColours(after, analogous.getColourAtIndex(2)));

        assertEquals(3, analogous.getSize());
        assertEquals("Analogous (100, 50, 120)", analogous.getName());
    }

    @Test
    void testAnalogousGreenIsMiddleValue() {
        ColourScheme analogous = testColourB.analogousScheme();
        Colour before = new Colour(40, 120 - SHIFT_SIZE, 180);
        Colour after = new Colour(40,120 + SHIFT_SIZE,180);

        assertTrue(compareColours(before, analogous.getColourAtIndex(0)));
        assertTrue(compareColours(testColourB, analogous.getColourAtIndex(1)));
        assertTrue(compareColours(after, analogous.getColourAtIndex(2)));

        assertEquals(3, analogous.getSize());
        assertEquals("Analogous (40, 120, 180)", analogous.getName());
    }

    @Test
    void testAnalogousBlueIsMiddleValue() {
        ColourScheme analogous = testColourC.analogousScheme();
        Colour before = new Colour(100, 220, 150 - SHIFT_SIZE);
        Colour after = new Colour(100,220,150 + SHIFT_SIZE);

        assertTrue(compareColours(before, analogous.getColourAtIndex(0)));
        assertTrue(compareColours(testColourC, analogous.getColourAtIndex(1)));
        assertTrue(compareColours(after, analogous.getColourAtIndex(2)));

        assertEquals(3, analogous.getSize());
        assertEquals("Analogous (100, 220, 150)", analogous.getName());
    }

    @Test
    void testAnalogousLowerBoundary() {
        Colour testColourDark = new Colour(0, 10, 120);
        ColourScheme analogous = testColourDark.analogousScheme();
        Colour before = new Colour(0,10 + SHIFT_SIZE * 2,120);
        Colour after = new Colour(0,10 + SHIFT_SIZE,120);

        assertTrue(compareColours(before, analogous.getColourAtIndex(0)));
        assertTrue(compareColours(testColourDark, analogous.getColourAtIndex(1)));
        assertTrue(compareColours(after, analogous.getColourAtIndex(2)));

        assertEquals(3, analogous.getSize());
        assertEquals("Analogous (0, 10, 120)", analogous.getName());
    }

    @Test
    void testAnalogousHigherBoundary() {
        Colour testColourLight = new Colour(250, 20, 240);
        ColourScheme analogous = testColourLight.analogousScheme();
        Colour before = new Colour(250,20,240 - SHIFT_SIZE);
        Colour after = new Colour(250,20,240 - SHIFT_SIZE * 2);

        assertTrue(compareColours(before, analogous.getColourAtIndex(0)));
        assertTrue(compareColours(testColourLight, analogous.getColourAtIndex(1)));
        assertTrue(compareColours(after, analogous.getColourAtIndex(2)));

        assertEquals(3, analogous.getSize());
        assertEquals("Analogous (250, 20, 240)", analogous.getName());
    }

    private boolean compareColours(Colour colourA, Colour colourB) {
        return colourA.getRed() == colourB.getRed() && colourA.getBlue() == colourB.getBlue()
                && colourA.getGreen() == colourA.getGreen();
    }
}
