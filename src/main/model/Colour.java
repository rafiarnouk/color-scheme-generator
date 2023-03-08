package model;

import org.json.JSONObject;
import persistence.Writable;

// represents a colour using rgb values, which can be used as a base colour to generate colour schemes around
public class Colour implements Writable {
    private int red;
    private int green;
    private int blue;
    public static final int SHIFT_SIZE = 50;
    public static final int MONO_AMOUNT = 2;

    public Colour(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    // EFFECTS: returns ANSI escape code that prints colour based on rgb values,
    //          width determines how many characters wide the colour block is
    public String getColourPrintCode(int width) {
        String spaces = "";
        for (int i = 0; i < width; i++) {
            spaces = spaces + " ";
        }
        return "\033[48;2;" + getRed() + ";" + getGreen() + ";" + getBlue() + "m" + spaces + "\u001b[0m";
    }

    // MODIFIES: this
    // EFFECTS: inverts colour
    private void invert() {
        setRed(255 - getRed());
        setGreen(255 - getGreen());
        setBlue(255 - getBlue());
    }

    // EFFECTS: returns complementary colour scheme, which is the current colour
    //          and the colour returned by inverting it
    //          generates name using scheme name "Complementary"
    public ColourScheme complementaryScheme() {
        ColourScheme complementary = new ColourScheme();
        Colour colour = new Colour(getRed(), getGreen(), getBlue());
        Colour inverted = new Colour(getRed(), getGreen(), getBlue());
        inverted.invert();
        complementary.addColour(colour);
        complementary.addColour(inverted);
        complementary.setName(generateName("Complementary"));
        return complementary;
    }

    // EFFECTS: returns monochrome colour scheme with MONO_AMOUNT * 2 + 1 colours
    //          colour scheme starts at black, gradually transitions into the current colour,
    //          and ends with white
    //          generates name using scheme name "Monochrome"
    public ColourScheme monochromeScheme() {
        ColourScheme monochrome = new ColourScheme();
        int lighterR = getRed() / MONO_AMOUNT;
        int lighterG = getGreen() / MONO_AMOUNT;
        int lighterB = getBlue() / MONO_AMOUNT;
        for (int i = 0; i < MONO_AMOUNT; i++) {
            Colour newColour = new Colour(lighterR * i, lighterG * i, lighterB * i);
            monochrome.addColour(newColour);
        }
        int darkerR = (255 - getRed()) / MONO_AMOUNT;
        int darkerG = (255 - getGreen()) / MONO_AMOUNT;
        int darkerB = (255 - getBlue()) / MONO_AMOUNT;
        for (int i = 0; i <= MONO_AMOUNT; i++) {
            Colour newColour = new Colour(getRed() + (darkerR * i), getGreen() + (darkerG * i),
                    getBlue() + (darkerB * i));
            monochrome.addColour(newColour);
        }
        monochrome.setName(generateName("Monochrome"));
        return monochrome;
    }

    // EFFECTS: creates analogous scheme with three colours, one before and one after the current colour
    //          takes middle value between r, g, and b and decreases it for the before colour and increases
    //          it for the after colour using shift helper method
    //          if result is outside of range 0-255, move the opposite way
    //          generates name using scheme name "Analogous"
    public ColourScheme analogousScheme() {
        ColourScheme analogous = new ColourScheme();
        Colour before = new Colour(getRed(), getGreen(), getBlue());
        Colour middle = new Colour(getRed(), getGreen(), getBlue());
        Colour after = new Colour(getRed(), getGreen(), getBlue());
        before.adjustColourMiddleValue(-1);
        after.adjustColourMiddleValue(1);

        analogous.addColour(before);
        analogous.addColour(middle);
        analogous.addColour(after);

        analogous.setName(generateName("Analogous"));
        return analogous;
    }

    // MODIFIES: this
    // EFFECTS: takes middle value between r, g, and b and shifts it in given direction using shift helper
    private void adjustColourMiddleValue(int direction) {
        int r = getRed();
        int g = getGreen();
        int b = getBlue();
        if ((r > g && r < b) || r > b && r < g) {
            setRed(shift(getRed(), direction));
        } else if ((g > r && g < b) || g > b && g < r) {
            setGreen(shift(getGreen(), direction));
        } else {
            setBlue(shift(getBlue(), direction));
        }
    }

    // EFFECTS: takes value and adds SHIFT_SIZE scaled by a multiplier
    //          if the resulting value < 0, shift up * 2
    //          if the resulting value > 255, shift down * 2
    private int shift(int value, int multiplier) {
        int shifted = value + SHIFT_SIZE * multiplier;
        if (shifted < 0) {
            shifted = value + SHIFT_SIZE * 2;
        } else if (shifted > 255) {
            shifted = value - SHIFT_SIZE * 2;
        }
        return shifted;
    }

    // EFFECTS: creates triadic scheme with three colours
    //          before colour moves g value to r, b to g, r to b
    //          after colour moves b value to r, r to g, g to b
    //          generates name using scheme name "Triadic"
    public ColourScheme triadicScheme() {
        ColourScheme triadic = new ColourScheme();
        Colour before = new Colour(getGreen(), getBlue(), getRed());
        Colour middle = new Colour(getRed(), getGreen(), getBlue());
        Colour after = new Colour(getBlue(), getRed(), getGreen());
        triadic.addColour(before);
        triadic.addColour(middle);
        triadic.addColour(after);
        triadic.setName(generateName("Triadic"));
        return triadic;
    }

    // EFFECTS: generates names for colour scheme by combining the type of scheme
    //          and the rgb values of the base colour
    private String generateName(String schemeType) {
        return (schemeType + " (" + getRed() + ", " + getGreen() + ", " + getBlue() + ")");
    }

    // EFFECTS: returns true if colour is identical to this colour
    public boolean compareColours(Colour colourB) {
        return getRed() == colourB.getRed() && getBlue() == colourB.getBlue() && getGreen() == colourB.getGreen();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("red", red);
        json.put("green", green);
        json.put("blue", blue);
        return json;
    }
}
