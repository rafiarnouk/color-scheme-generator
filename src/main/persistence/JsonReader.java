package persistence;

import model.Colour;
import model.ColourScheme;
import model.Gallery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads gallery from JSON data stored in file
// NOTE: all Json related code is heavily based on JsonSerializationDemo provided
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads gallery from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Gallery read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGallery(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses gallery from JSON object and returns it
    private Gallery parseGallery(JSONObject jsonObject) {
        Gallery g = new Gallery();
        addColourSchemes(g, jsonObject);
        return g;
    }

    // MODIFIES: g
    // EFFECTS: parses colour schemes from JSON object and adds them to gallery
    private void addColourSchemes(Gallery g, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("gallery");
        for (Object json : jsonArray) {
            JSONObject nextScheme = (JSONObject) json;
            ColourScheme cs = parseColourScheme(nextScheme);
            g.addScheme(cs);
        }
    }

    // EFFECTS: parses colour scheme from JSON object and returns it
    private ColourScheme parseColourScheme(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        ColourScheme cs = new ColourScheme();
        cs.setName(name);
        addColours(cs, jsonObject);
        return cs;
    }

    // MODIFIES: cs
    // EFFECTS: parses colours from JSON object and adds them to colour scheme
    private void addColours(ColourScheme cs, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("scheme");
        for (Object json : jsonArray) {
            JSONObject nextColour = (JSONObject) json;
            addColour(cs, nextColour);
        }
    }

    // MODIFIES: cs
    // EFFECTS: parses colour from JSON object and adds it to colour scheme
    private void addColour(ColourScheme cs, JSONObject jsonObject) {
        int red = jsonObject.getInt("red");
        int green = jsonObject.getInt("green");
        int blue = jsonObject.getInt("blue");
        Colour colour = new Colour(red, green, blue);
        cs.addColour(colour);
    }
}