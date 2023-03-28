package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Colour.*;
import static org.junit.jupiter.api.Assertions.*;

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

        assertTrue(testColour.compareColours(complementary.getColourAtIndex(0)));
        assertTrue(inverted.compareColours(complementary.getColourAtIndex(1)));

        assertEquals(2, complementary.getSize());
        assertEquals("Complementary (100, 50, 120)", complementary.getName());
    }

    @Test
    void testTriadic()  {
        ColourScheme triadic = testColour.triadicScheme();
        Colour before = new Colour(50, 120, 100);
        Colour after = new Colour(120,100,50);

        assertTrue(before.compareColours(triadic.getColourAtIndex(0)));
        assertTrue(testColour.compareColours(triadic.getColourAtIndex(1)));
        assertTrue(after.compareColours(triadic.getColourAtIndex(2)));

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
            expected.addColour(new Colour((int) (lighterR * i + lighterR * MONOCHROME_BUFFER),
                    (int) (lighterG * i + lighterG * MONOCHROME_BUFFER),
                    (int) (lighterB * i + lighterB * MONOCHROME_BUFFER)));
        }

        int darkerR = 155 / MONO_AMOUNT;
        int darkerG = 205 / MONO_AMOUNT;
        int darkerB = 135 / MONO_AMOUNT;
        for (int i = 0; i <= MONO_AMOUNT; i++) {
            expected.addColour(new Colour((int) (100 + darkerR * i - darkerR * MONOCHROME_BUFFER),
                    (int) (50 + darkerG * i - darkerG * MONOCHROME_BUFFER),
                    (int) (120 + darkerB * i - darkerB * MONOCHROME_BUFFER)));
        }

        for (int i = 0; i < monochrome.getSize(); i++) {
            assertTrue(expected.getColourAtIndex(i).compareColours(monochrome.getColourAtIndex(i)));
        }

        assertEquals(MONO_AMOUNT * 2 + 1, monochrome.getSize());
        assertEquals("Monochrome (100, 50, 120)", monochrome.getName());
    }
    
    @Test
    void testAnalogousRedIsMiddleValue() {
        ColourScheme analogous = testColour.analogousScheme();
        Colour before = new Colour(100 - SHIFT_SIZE, 50, 120);
        Colour after = new Colour(100 + SHIFT_SIZE,50,120);

        assertTrue(before.compareColours(analogous.getColourAtIndex(0)));
        assertTrue(testColour.compareColours(analogous.getColourAtIndex(1)));
        assertTrue(after.compareColours(analogous.getColourAtIndex(2)));

        assertEquals(3, analogous.getSize());
        assertEquals("Analogous (100, 50, 120)", analogous.getName());
    }

    @Test
    void testAnalogousGreenIsMiddleValue() {
        ColourScheme analogous = testColourB.analogousScheme();
        Colour before = new Colour(40, 120 - SHIFT_SIZE, 180);
        Colour after = new Colour(40,120 + SHIFT_SIZE,180);

        assertTrue(before.compareColours(analogous.getColourAtIndex(0)));
        assertTrue(testColourB.compareColours(analogous.getColourAtIndex(1)));
        assertTrue(after.compareColours(analogous.getColourAtIndex(2)));

        assertEquals(3, analogous.getSize());
        assertEquals("Analogous (40, 120, 180)", analogous.getName());
    }

    @Test
    void testAnalogousBlueIsMiddleValue() {
        ColourScheme analogous = testColourC.analogousScheme();
        Colour before = new Colour(100, 220, 150 - SHIFT_SIZE);
        Colour after = new Colour(100,220,150 + SHIFT_SIZE);

        assertTrue(before.compareColours(analogous.getColourAtIndex(0)));
        assertTrue(testColourC.compareColours(analogous.getColourAtIndex(1)));
        assertTrue(after.compareColours(analogous.getColourAtIndex(2)));

        assertEquals(3, analogous.getSize());
        assertEquals("Analogous (100, 220, 150)", analogous.getName());
    }

    @Test
    void testAnalogousLowerBoundary() {
        Colour testColourDark = new Colour(0, 10, 120);
        ColourScheme analogous = testColourDark.analogousScheme();
        Colour before = new Colour(0,10 + SHIFT_SIZE * 2,120);
        Colour after = new Colour(0,10 + SHIFT_SIZE,120);

        assertTrue(before.compareColours(analogous.getColourAtIndex(0)));
        assertTrue(testColourDark.compareColours(analogous.getColourAtIndex(1)));
        assertTrue(after.compareColours(analogous.getColourAtIndex(2)));

        assertEquals(3, analogous.getSize());
        assertEquals("Analogous (0, 10, 120)", analogous.getName());
    }

    @Test
    void testAnalogousHigherBoundary() {
        Colour testColourLight = new Colour(250, 20, 240);
        ColourScheme analogous = testColourLight.analogousScheme();
        Colour before = new Colour(250,20,240 - SHIFT_SIZE);
        Colour after = new Colour(250,20,240 - SHIFT_SIZE * 2);

        assertTrue(before.compareColours(analogous.getColourAtIndex(0)));
        assertTrue(testColourLight.compareColours(analogous.getColourAtIndex(1)));
        assertTrue(after.compareColours(analogous.getColourAtIndex(2)));

        assertEquals(3, analogous.getSize());
        assertEquals("Analogous (250, 20, 240)", analogous.getName());
    }

    @Test
    void testCompareColoursTrue() {
        Colour matchesTestColour = new Colour(100, 50, 120);
        assertTrue(testColour.compareColours(matchesTestColour));
    }

    @Test
    void testCompareColoursFalse() {
        assertFalse(testColour.compareColours(testColourB));
    }

    @Test
    void testCompareColoursOnlyRedDifferent() {
        Colour redDifferent = new Colour(101, 50, 120);
        assertFalse(redDifferent.compareColours(testColour));
    }

    @Test
    void testCompareColoursOnlyGreenDifferent() {
        Colour greenDifferent = new Colour(100, 49, 120);
        assertFalse(greenDifferent.compareColours(testColour));
    }

    @Test
    void testCompareColoursOnlyBlueDifferent() {
        Colour blueDifferent = new Colour(100, 50, 220);
        assertFalse(blueDifferent.compareColours(testColour));
    }
}
