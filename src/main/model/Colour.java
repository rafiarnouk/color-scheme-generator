package model;

import java.util.ArrayList;
import java.util.List;

public class Colour {
    private Integer red;
    private Integer green;
    private Integer blue;
    private static final int SHIFT_SIZE = 50;
    private static final int MONO_AMOUNT = 2;

    public Colour(Integer red, Integer green, Integer blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Integer getRed() {
        return red;
    }

    public Integer getGreen() {
        return green;
    }

    public Integer getBlue() {
        return blue;
    }

    public void setRed(Integer red) {
        this.red = red;
    }

    public void setGreen(Integer green) {
        this.green = green;
    }

    public void setBlue(Integer blue) {
        this.blue = blue;
    }

    public String makeColourStringCode(int width) {
        String spaces = new String();
        for (int i = 0; i < width; i++) {
            spaces = spaces + " ";
        }
        return "\033[48;2;" + getRed() + ";" + getGreen() + ";" + getBlue() + "m" + spaces + "\u001b[0m";
    }

    private Colour invert() {
        Colour inverted = new Colour(255 - getRed(), 255 - getGreen(), 255 - getBlue());
        return inverted;
    }

    public ColourScheme complementaryScheme() {
        ColourScheme complementary = new ColourScheme();
        Colour colour = new Colour(getRed(), getGreen(), getBlue());
        complementary.addColour(colour);
        complementary.addColour(colour.invert());
        complementary.setName(generateName("Complementary"));
        return complementary;
    }

    public ColourScheme monochromeScheme() {
        ColourScheme monochrome = new ColourScheme();
        Integer lighterR = getRed() / MONO_AMOUNT;
        Integer lighterG = getGreen() / MONO_AMOUNT;
        Integer lighterB = getBlue() / MONO_AMOUNT;
        for (int i = 0; i < MONO_AMOUNT; i++) {
            Colour newColour = new Colour(lighterR * i, lighterG * i, lighterB * i);
            monochrome.addColour(newColour);
        }
        Integer darkerR = (255 - getRed()) / MONO_AMOUNT;
        Integer darkerG = (255 - getGreen()) / MONO_AMOUNT;
        Integer darkerB = (255 - getBlue()) / MONO_AMOUNT;
        for (Integer i = 0; i <= MONO_AMOUNT; i++) {
            Colour newColour = new Colour(getRed() + (darkerR * i), getGreen() + (darkerG * i),
                    getBlue() + (darkerB * i));
            monochrome.addColour(newColour);
        }
        monochrome.setName(generateName("Monochrome"));
        return monochrome;
    }

    public ColourScheme analogousScheme() {
        ColourScheme analogous = new ColourScheme();
        int r = getRed();
        int g = getGreen();
        int b = getBlue();
        Colour before = new Colour(r, g, b);
        Colour middle = new Colour(r, g, b);
        Colour after = new Colour(r, g, b);
        if ((r > g && r < b) || r > b && r < g) {
            before.setRed(shift(getRed(),-1));
            after.setRed(shift(getRed(),1));
        } else if ((g > r && g < b) || g > b && g < r) {
            before.setGreen(shift(getGreen(),-1));
            after.setGreen(shift(getGreen(),1));
        } else {
            before.setBlue(shift(getBlue(),-1));
            after.setBlue(shift(getBlue(),1));
        }
        analogous.addColour(before);
        analogous.addColour(middle);
        analogous.addColour(after);
        analogous.setName(generateName("Analogous"));
        return analogous;
    }

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

    private int shift(int value, int multiplier) {
        int shifted = value + SHIFT_SIZE * multiplier;
        if (shifted < 0) {
            shifted = value + SHIFT_SIZE * 2;
        } else if (shifted > 255) {
            shifted = value - SHIFT_SIZE * 2;
        }
        return shifted;
    }

    private String generateName(String schemeType) {
        return (schemeType + " (" + getRed() + ", " + getGreen() + ", " + getBlue() + ")");
    }
}
