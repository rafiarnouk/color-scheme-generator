package model;

import java.util.ArrayList;
import java.util.List;

public class ColourScheme {
    private List<Colour> scheme;
    private String name;

    public ColourScheme() {
        scheme = new ArrayList<>();
        name = "";
    }

    public List<Colour> getScheme() {
        return scheme;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addColour(Colour c) {
        scheme.add(c);
    }

    public void displayScheme() {
        System.out.println("NAME: " + name);
        for (Colour c : scheme) {
            c.displayColour();
        }
    }
}
