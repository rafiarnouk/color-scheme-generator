package model;

public class Colour {
    private Integer red;
    private Integer green;
    private Integer blue;

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

    public void displayColour() {
        System.out.println("R:" + getRed() + " G:" + getGreen() + " B:" + getBlue());
    }

    private Colour invert() {
        Colour inverted = new Colour(255 - getRed(), 255 - getGreen(), 255 - getBlue());
        return inverted;
    }

    private Colour desaturate() {
        Integer grayValue = (getRed() + getGreen() + getBlue()) / 3;
        Colour desaturated = new Colour(grayValue, grayValue, grayValue);
        return desaturated;
    }

    public ColourScheme complementaryScheme() {
        ColourScheme complementary = new ColourScheme();
        complementary.addColour(this);
        complementary.addColour(this.invert());
        complementary.setName(generateName("Complementary"));
        return complementary;
    }

    public ColourScheme monochromeScheme() {
        ColourScheme monochrome = new ColourScheme();
        Integer lighterR = getRed() / 3;
        Integer lighterG = getGreen() / 3;
        Integer lighterB = getBlue() / 3;
        for (int i = 0; i < 3; i++) {
            Colour newColour = new Colour(lighterR * i, lighterG * i, lighterB * i);
            monochrome.addColour(newColour);
        }
        Integer darkerR = (255 - getRed()) / 3;
        Integer darkerG = (255 - getGreen()) / 3;
        Integer darkerB = (255 - getBlue()) / 3;
        for (Integer i = 0; i <= 3; i++) {
            Colour newColour = new Colour(getRed() + (darkerR * i), getGreen() + (darkerG * i),
                    getBlue() + (darkerB * i));
            monochrome.addColour(newColour);
        }
        monochrome.setName(generateName("Monochrome"));
        return monochrome;
    }

    public ColourScheme analogousScheme() {
        ColourScheme analogous = new ColourScheme();
        Colour before = new Colour(getGreen(), getBlue(), getRed());
        Colour after = new Colour(getBlue(), getRed(), getGreen());
        analogous.addColour(before);
        analogous.addColour(this);
        analogous.addColour(after);
        analogous.setName(generateName("Analogous"));
        return analogous;
    }

    private String generateName(String schemeType) {
        return (schemeType + " (" + getRed() + ", " + getGreen() + ", " + getBlue() + ")");
    }
}
