package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// represents a gallery of colour schemes
public class Gallery implements Writable {
    private List<ColourScheme> gallery;

    public Gallery() {
        gallery = new ArrayList<>();
    }

    public List<ColourScheme> getGallery() {
        return gallery;
    }

    // EFFECTS: returns amount of colour schemes in gallery
    public int getSize() {
        return gallery.size();
    }

    // REQUIRES: 0 <= index < gallery size
    // EFFECTS: returns colour scheme at given index in gallery
    public ColourScheme getSchemeAtIndex(int index) {
        return gallery.get(index);
    }

    // MODIFIES: this
    // EFFECTS: adds colour scheme to gallery
    public void addScheme(ColourScheme cs) {
        gallery.add(cs);
        EventLog.getInstance().logEvent(new Event("Added scheme " + cs.getName() + " to gallery"));
    }

    // REQUIRES: 1 <= index <= gallery size
    // MODIFIES: this
    // EFFECTS: removes colour scheme at given position in gallery
    public void removeScheme(int index) {
        gallery.remove(index - 1);
    }

    // EFFECTS: writes gallery to json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("gallery", galleryToJson());
        return json;
    }

    // EFFECTS: returns things in this gallery as a JSON array
    private JSONArray galleryToJson() {
        JSONArray jsonArray = new JSONArray();

        for (ColourScheme cs : gallery) {
            jsonArray.put(cs.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: removes all schemes in gallery
    public void removeAllSchemes() {
        gallery = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("Removed all schemes from gallery"));
    }

    // EFFECTS: logs that gallery was displayed
    public void display() {
        EventLog.getInstance().logEvent(new Event("Displayed all schemes"));
    }

    // EFFECTS: logs that schemes were brightened
    public void brighterSchemes() {
        EventLog.getInstance().logEvent(new Event("Brightened all schemes"));
    }

    // EFFECTS: logs that schemes were darkened
    public void darkerSchemes() {
        EventLog.getInstance().logEvent(new Event("Darkened all schemes"));
    }
}
