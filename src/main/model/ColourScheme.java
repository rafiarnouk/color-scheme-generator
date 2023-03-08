package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// represents a colour scheme that has a list of colours and name
public class ColourScheme implements Writable {
    private List<Colour> scheme;
    private String name;

    public ColourScheme() {
        scheme = new ArrayList<>();
    }

    public List<Colour> getScheme() {
        return scheme;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: returns amount of colours in scheme
    public int getSize() {
        return scheme.size();
    }

    // REQUIRES: scheme is not empty, 0 <= index < getSize()
    // EFFECTS: returns colour at given index
    public Colour getColourAtIndex(int index) {
        return scheme.get(index);
    }

    // MODIFIES: this
    // EFFECTS: adds colour to colour scheme
    public void addColour(Colour c) {
        scheme.add(c);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("scheme", schemeToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray schemeToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Colour c : scheme) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
