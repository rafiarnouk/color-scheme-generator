package persistence;

import model.Colour;
import model.ColourScheme;
import model.Gallery;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// tests for JsonWriter
// NOTE: all Json related code is heavily based on JsonSerializationDemo provided
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Gallery g = new Gallery();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyGallery() {
        try {
            Gallery g = new Gallery();
            JsonWriter writer = new JsonWriter("./data/testEmptyGalleryWriter.json");
            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyGalleryWriter.json");
            g = reader.read();
            assertEquals(0, g.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterFullGallery() {
        try {
            Gallery g = new Gallery();
            Colour firstSchemeBaseColour = new Colour(100, 200, 100);
            ColourScheme firstScheme = firstSchemeBaseColour.complementaryScheme();
            Colour secondSchemeBaseColour = new Colour(240, 220, 100);
            ColourScheme secondScheme = secondSchemeBaseColour.triadicScheme();
            g.addScheme(firstScheme);
            g.addScheme(secondScheme);
            JsonWriter writer = new JsonWriter("./data/testFullGalleryWriter.json");
            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testFullGalleryWriter.json");
            g = reader.read();
            List<ColourScheme> schemes = g.getGallery();
            assertEquals(2, schemes.size());
            checkSameColourScheme(firstScheme, schemes.get(0));
            checkSameColourScheme(secondScheme, schemes.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}