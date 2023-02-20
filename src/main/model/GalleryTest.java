package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GalleryTest {
    private Gallery testGallery;
    private ColourScheme testSchemeA;
    private ColourScheme testSchemeB;
    private Colour testColour;

    @BeforeEach
    void runBefore() {
        testGallery = new Gallery();
        testColour = new Colour(120, 100, 200);
        testSchemeA = testColour.analogousScheme();
        testSchemeB = testColour.monochromeScheme();
        testGallery.addScheme(testSchemeA);
        testGallery.addScheme(testSchemeB);
    }

    @Test
    void testGetGallery() {
        List<ColourScheme> gallery = testGallery.getGallery();
        assertEquals(testSchemeA, gallery.get(0));
        assertEquals(testSchemeB, gallery.get(1));
        assertEquals(2, gallery.size());
    }

    @Test
    void testGetSize() {
        assertEquals(2, testGallery.getSize());
        testGallery.addScheme(testColour.complementaryScheme());
        assertEquals(3, testGallery.getSize());
    }

    @Test
    void testAddScheme() {
        ColourScheme newScheme = testColour.complementaryScheme();
        testGallery.addScheme(newScheme);
        assertEquals(3, testGallery.getSize());
        assertEquals(newScheme, testGallery.getSchemeAtIndex(2));
    }

    @Test
    void testRemoveScheme() {
        testGallery.removeScheme(1);
        assertEquals(1, testGallery.getSize());
        assertEquals(testSchemeB, testGallery.getSchemeAtIndex(0));
    }
}
