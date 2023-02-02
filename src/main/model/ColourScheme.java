package model;

import java.util.ArrayList;
import java.util.List;

public class ColourScheme {
    private List<Colour> scheme;

    public ColourScheme() {
        scheme = new ArrayList<>();
    }

    public List<Colour> getScheme() {
        return scheme;
    }

    public void addColour(Colour c) {
        scheme.add(c);
    }
}
