package model;

import java.util.ArrayList;
import java.util.List;

public class Gallery {
    private List<ColourScheme> gallery;

    public Gallery() {
        gallery = new ArrayList<>();
    }

    public List<ColourScheme> getGallery() {
        return gallery;
    }

    public void addScheme(ColourScheme cs) {
        gallery.add(cs);
    }
}
