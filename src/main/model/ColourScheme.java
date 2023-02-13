package model;

import java.util.ArrayList;
import java.util.List;

public class ColourScheme {
    private List<Colour> scheme;
    private String name;
    private static final int COLOUR_BLOCK_WIDTH = 6;

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
        String output = new String();
        for (Colour c : scheme) {
            output = output + c.makeColourStringCode(COLOUR_BLOCK_WIDTH);
        }
        System.out.println(output);
    }
}
