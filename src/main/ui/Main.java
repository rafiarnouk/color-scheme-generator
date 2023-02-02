package ui;

import model.Colour;
import model.ColourScheme;

public class Main {
    public static void main(String[] args) {
        Colour testColour = new Colour(120, 100, 40);
        ColourScheme testScheme = testColour.monochromeScheme();

        // print red results
        for (Colour c : testScheme.getScheme()) {
            System.out.println(c.getGreen());
        }
    }
}
