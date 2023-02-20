package ui;

import model.Colour;
import model.ColourScheme;
import model.Gallery;
import java.util.Scanner;

public class ColourSchemeApp {
    private Gallery gallery;
    private Colour colour;
    private Scanner input;
    private static final int COLOUR_BLOCK_WIDTH = 6;

    public ColourSchemeApp() {
        runColourScheme();
    }

    private void runColourScheme() {
        boolean keepRunning = true;
        String command = null;

        gallery = new Gallery();
        colour = new Colour(0,0,0);
        input = new Scanner(System.in);
        input.useDelimiter("\n");

        while (keepRunning) {
            displayMenu();
            command = input.next();

            if (command.equals("q")) {
                keepRunning = false;
            } else {
                processCommand(command);
            }
        }
    }

    private void displayMenu() {
        System.out.println("\nPick a colour, view your gallery, or quit the generator.");
        System.out.println("\tp - pick a colour");
        System.out.println("\tv - view gallery");
        System.out.println("\tq - quit generator");
    }

    private void processCommand(String command) {
        if (command.equals("p")) {
            processColour();
            generateScheme();
        } else if (command.equals("v")) {
            viewGallery();
        }
    }

    private void viewGallery() {
        if (gallery.getGallery().isEmpty()) {
            System.out.println("You haven't added any colour schemes to your gallery yet.");
        } else if (gallery.getSize() == 1) {
            System.out.println("You have 1 colour scheme in your gallery.");
        } else {
            System.out.println("You have " + gallery.getSize() + " colour schemes in your gallery.");
        }

        System.out.println("\t");

        int preventDoubleLineAtEnd = 0;
        for (ColourScheme cs : gallery.getGallery()) {
            displayScheme(cs);
            if (preventDoubleLineAtEnd < gallery.getSize() - 1) {
                System.out.println("\t");
            }
            preventDoubleLineAtEnd++;
        }
    }

    public void displayScheme(ColourScheme cs) {
        System.out.println("NAME: " + cs.getName());
        String output = new String();
        for (Colour c : cs.getScheme()) {
            output = output + c.getColourPrintCode(COLOUR_BLOCK_WIDTH);
        }
        System.out.println(output);
    }

    private void processColour() {
        System.out.println("Choose red value between 0 and 255.");
        int red = input.nextInt();
        System.out.println("Choose green value between 0 and 255.");
        int green = input.nextInt();
        System.out.println("Choose blue value between 0 and 255.");
        int blue = input.nextInt();

        colour.setRed(red);
        colour.setGreen(green);
        colour.setBlue(blue);
    }

    private void generateScheme() {
        displaySchemeMenu();
        ColourScheme chosenScheme = new ColourScheme();
        String s = input.next();
        if (s.equals("c")) {
            chosenScheme = colour.complementaryScheme();
        } else if (s.equals("m")) {
            chosenScheme = colour.monochromeScheme();
        } else if (s.equals("a")) {
            chosenScheme = colour.analogousScheme();
        } else if (s.equals("t")) {
            chosenScheme = colour.triadicScheme();
        }
        displayScheme(chosenScheme);
        System.out.println("\nSave colour scheme to gallery?");
        System.out.println("\ty - yes");
        System.out.println("\tn - no");
        String s2 = input.next();
        if (s2.equals("y")) {
            gallery.addScheme(chosenScheme);
        }
    }

    private void displaySchemeMenu() {
        String colourPreview = colour.getColourPrintCode(3);

        System.out.println("\nYour colour is " + colourPreview);
        System.out.println("What type of colour scheme would you like?");
        System.out.println("\tm - monochrome");
        System.out.println("\tc - complementary");
        System.out.println("\ta - analogous");
        System.out.println("\tt - triadic");
    }
}
