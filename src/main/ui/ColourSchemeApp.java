package ui;

import model.Colour;
import model.ColourScheme;
import model.Gallery;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// interactive console based colour scheme generator app
// NOTE: all Json related code is heavily based on JsonSerializationDemo provided
public class ColourSchemeApp {
    private Gallery gallery;
    private Colour colour;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/gallery.json";
    private static final int COLOUR_BLOCK_WIDTH = 6;

    // EFFECTS: runs the colour scheme app
    public ColourSchemeApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runColourScheme();
    }

    // MODIFIES: this
    // EFFECTS: processes inputs
    private void runColourScheme() {
        boolean keepRunning = true;
        String command;

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

    // EFFECTS: displays menu of things the user can do, including removing a colour scheme if the gallery isn't empty
    private void displayMenu() {
        System.out.println("\nWhat would you like to do next?");
        System.out.println("\tp - pick a colour");
        System.out.println("\tv - view gallery");
        if (gallery.getSize() > 0) {
            System.out.println("\tr - remove scheme");
        }
        System.out.println("\ta - save gallery to file");
        System.out.println("\tl - load gallery from file");
        System.out.println("\ts - see examples");
        System.out.println("\tq - quit generator");
    }

    // MODIFIES: this
    // EFFECTS: processes command from user
    private void processCommand(String command) {
        if (command.equals("p")) {
            processColour();
            generateScheme();
        } else if (command.equals("v")) {
            viewGallery("");
        } else if (command.equals("r")) {
            removeColourScheme();
        } else if (command.equals("s")) {
            displayExamples();
        } else if (command.equals("a")) {
            saveGallery();
        } else if (command.equals("l")) {
            loadGallery();
        }
    }

    // EFFECTS: saves the gallery to file
    private void saveGallery() {
        try {
            jsonWriter.open();
            jsonWriter.write(gallery);
            jsonWriter.close();
            System.out.println("Saved gallery to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads gallery from file
    private void loadGallery() {
        try {
            gallery = jsonReader.read();
            System.out.println("Loaded gallery from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes chosen colour scheme from gallery
    private void removeColourScheme() {
        viewGallery("");
        System.out.println("\nSelect the position of the scheme you want to remove, from 1 to "
                + gallery.getSize() + ".");
        int pos = input.nextInt();
        gallery.removeScheme(pos);
        viewGallery("now ");
    }

    // EFFECTS: prints gallery of colour schemes in console
    private void viewGallery(String update) {
        if (gallery.getGallery().isEmpty()) {
            System.out.println("You " + update + "have no colour schemes in your gallery.");
        } else if (gallery.getSize() == 1) {
            System.out.println("You " + update + "have 1 colour scheme in your gallery.");
        } else {
            System.out.println("You " + update + "have " + gallery.getSize() + " colour schemes in your gallery.");
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

    // EFFECTS: displays name and colours of colour scheme
    public void displayScheme(ColourScheme cs) {
        System.out.println("NAME: " + cs.getName());
        String output = "";
        for (Colour c : cs.getScheme()) {
            output = output + c.getColourPrintCode(COLOUR_BLOCK_WIDTH);
        }
        System.out.println(output);
    }

    // MODIFIES: this
    // EFFECTS: processes rgb colour chosen by user
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

    // MODIFIES: this
    // EFFECTS: generates chosen scheme for colour and allows user to save it
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

    // EFFECTS: displays colour chosen and colour scheme options menu
    private void displaySchemeMenu() {
        String colourPreview = colour.getColourPrintCode(3);

        System.out.println("\nYour colour is " + colourPreview);
        System.out.println("What type of colour scheme would you like?");
        System.out.println("\tm - monochrome");
        System.out.println("\ta - analogous");
        System.out.println("\tt - triadic");
        System.out.println("\tc - complementary");
    }

    // EFFECTS: displays examples of each type of colour scheme, based on the same colour
    private void displayExamples() {
        Colour sampleColour = new Colour(150,100,200);
        String colourPreview = sampleColour.getColourPrintCode(3);
        ColourScheme monochrome = sampleColour.monochromeScheme();
        ColourScheme analogous = sampleColour.analogousScheme();
        ColourScheme triadic = sampleColour.triadicScheme();
        ColourScheme complementary = sampleColour.complementaryScheme();
        System.out.println("\nHere is an example of each colour scheme, using the colour " + colourPreview);
        displayScheme(monochrome);
        displayScheme(analogous);
        displayScheme(triadic);
        displayScheme(complementary);
    }
}
