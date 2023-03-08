package persistence;

import model.Colour;
import model.ColourScheme;
import model.Gallery;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// tests for JsonReader
// NOTE: all Json related code is heavily based on JsonSerializationDemo provided
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Gallery g = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyGallery() {
        JsonReader reader = new JsonReader("./data/testEmptyGalleryReader.json");
        try {
            Gallery g = reader.read();
            assertEquals(0, g.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderFullGallery() {
        JsonReader reader = new JsonReader("./data/testFullGalleryReader.json");
        try {
            Gallery g = reader.read();
            List<ColourScheme> schemes = g.getGallery();
            assertEquals(2, schemes.size());

            Colour firstSchemeBaseColour = new Colour(100, 200, 100);
            ColourScheme firstScheme = firstSchemeBaseColour.complementaryScheme();
            checkSameColourScheme(schemes.get(0), firstScheme);

            Colour secondSchemeBaseColour = new Colour(240, 220, 100);
            ColourScheme secondScheme = secondSchemeBaseColour.triadicScheme();
            checkSameColourScheme(schemes.get(1), secondScheme);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}