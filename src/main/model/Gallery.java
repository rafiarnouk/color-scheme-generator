package model;

import java.util.ArrayList;
import java.util.List;

// represents a gallery of colour schemes
public class Gallery {
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
    }

    // REQUIRES: 1 <= index <= gallery size
    // MODIFIES: this
    // EFFECTS: removes colour scheme at given position in gallery
    public void removeScheme(int index) {
        gallery.remove(index - 1);
    }
}
