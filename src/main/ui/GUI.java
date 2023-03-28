package ui;

import model.Colour;
import model.ColourScheme;
import model.Gallery;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.IOException;

// class representing graphic user interface
public class GUI extends JFrame implements ActionListener {

    private JPanel mainMenu;
    private JPanel galleryView;
    private String schemeType;
    private String[] schemeTypes = { "monochromatic", "analogous", "triadic", "complementary"};
    private JButton createButton;
    private JButton backButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton galleryButton;
    private JComboBox schemeCombo;
    private JSlider redSlider;
    private JSlider greenSlider;
    private JSlider blueSlider;
    private ColourScheme scheme;
    private Gallery gallery;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private int red;
    private int green;
    private int blue;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int RED_START = 150;
    private static final int GREEN_START = 100;
    private static final int BLUE_START = 200;
    private static final int SCHEME_HEIGHT = 50;
    private static final int BAR_HEIGHT = 5;
    private static final String JSON_STORE = "./data/gallery.json";

    // MODIFIES: this
    // EFFECTS: constructs user interface
    public GUI() {
        super("Colour Scheme Generator");
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        initializeValues();
        startMenu();
        startGallery();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        startRedSlider(RED_START);
        startGreenSlider(GREEN_START);
        startBlueSlider(BLUE_START);
        startSchemeChooser();
        makeButtons();

        openMainMenu();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }

    // MODIFIES: this
    // initializes and sets variables
    private void initializeValues() {
        schemeType = "monochromatic";
        gallery = new Gallery();
        red = RED_START;
        green = GREEN_START;
        blue = BLUE_START;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: prints out all colour schemes in gallery
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Color color = new Color(red, green, blue);
        g.setColor(color);
        int y = 28; // height of top bar
        for (ColourScheme cs : gallery.getGallery()) {
            for (int i = 0; i < cs.getSize(); i++) {
                g.setColor(new Color(cs.getColourAtIndex(i).getRed(),
                        cs.getColourAtIndex(i).getGreen(),
                        cs.getColourAtIndex(i).getBlue()));
                g.fillRect(WIDTH / cs.getSize() * i, y,
                        WIDTH / scheme.getSize() + 2, SCHEME_HEIGHT);
            }
            g.setColor(Color.white);
            g.fillRect(0, y + SCHEME_HEIGHT, WIDTH, BAR_HEIGHT);
            y = y + SCHEME_HEIGHT + BAR_HEIGHT;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up scheme chooser dropdown menu
    private void startSchemeChooser() {
        schemeCombo = new JComboBox(schemeTypes);
        mainMenu.add(schemeCombo);

        schemeCombo.addActionListener(this);
        schemeCombo.setActionCommand("schemeCombo");
    }

    // MODIFIES: this
    // EFFECTS: sets up main menu JPanel
    public void startMenu() {
        mainMenu = new JPanel();
        mainMenu.setLayout(new GridLayout(2,1, 5, 100));
        mainMenu.setBackground(Color.white);
        add(mainMenu, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: sets up buttons
    private void makeButtons() {
        createButton = new JButton("Create Colour Scheme");
        saveButton = new JButton("Save Gallery");
        loadButton = new JButton("Load Gallery");
        galleryButton = new JButton("View Gallery");
        mainMenu.add(createButton);
        mainMenu.add(saveButton);
        mainMenu.add(loadButton);
        mainMenu.add(galleryButton);
        createButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        galleryButton.addActionListener(this);
        createButton.setActionCommand("createButton");
        saveButton.setActionCommand("saveButton");
        loadButton.setActionCommand("loadButton");
        galleryButton.setActionCommand("galleryButton");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: sets up gallery JPanel
    public void startGallery() {
        galleryView = new JPanel();
        backButton = new JButton("Back to Generator");
        backButton.addActionListener(this);
        backButton.setActionCommand("backButton");
        galleryView.setBackground(Color.white);
        galleryView.add(backButton);
    }

    // MODIFIES: this
    // EFFECTS: opens gallery panel
    private void openGallery() {
        repaint();
        add(galleryView);
        galleryView.setVisible(true);
        mainMenu.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: opens main menu panel
    private void openMainMenu() {
        add(mainMenu);
        mainMenu.setVisible(true);
        galleryView.setVisible(false);
    }

    // EFFECTS: processes functionality for the buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        Colour colour = new Colour(red, green, blue);
        if (e.getActionCommand().equals("createButton")) {
            generateScheme(colour);
            gallery.addScheme(scheme);
            openGallery();
            repaint();
        } else if (e.getActionCommand().equals("schemeCombo")) {
            schemeType = (String) schemeCombo.getSelectedItem();
            System.out.println("SCHEME: " + schemeType);
        } else if (e.getActionCommand().equals("backButton")) {
            openMainMenu();
        } else if (e.getActionCommand().equals("saveButton")) {
            saveGallery();
        } else if (e.getActionCommand().equals("loadButton")) {
            loadGallery();
        } else if (e.getActionCommand().equals("galleryButton")) {
            openGallery();
            repaint();
        }
    }

    // EFFECTS: generates colour scheme for given colour
    private void generateScheme(Colour colour) {
        if (schemeType == "monochromatic") {
            scheme = colour.monochromeScheme();
        } else if (schemeType == "analogous") {
            scheme = colour.analogousScheme();
        } else if (schemeType == "triadic") {
            scheme = colour.triadicScheme();
        } else if (schemeType == "complementary") {
            scheme = colour.complementaryScheme();
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

    // REQUIRES: 0 <= startValue <= 255
    // MODIFIES: this
    // EFFECTS: starts red slider
    private void startRedSlider(int startValue) {
        redSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 255, startValue);
        mainMenu.add(redSlider);
        redSlider.setMajorTickSpacing(255);
        redSlider.setMinorTickSpacing(255 / 2);
        redSlider.setPaintTicks(true);
        redSlider.setPaintLabels(true);

        ChangeListener redListener = new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                JSlider source = (JSlider) event.getSource();
                red = source.getValue();
                System.out.println("RED: " + red);
            }
        };
        redSlider.addChangeListener(redListener);
    }

    // REQUIRES: 0 <= startValue <= 255
    // MODIFIES: this
    // EFFECTS: starts green slider
    private void startGreenSlider(int startValue) {
        greenSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 255, startValue);
        mainMenu.add(greenSlider);
        greenSlider.setMajorTickSpacing(255);
        greenSlider.setMinorTickSpacing(255 / 2);
        greenSlider.setPaintTicks(true);
        greenSlider.setPaintLabels(true);

        ChangeListener greenListener = new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                JSlider source = (JSlider) event.getSource();
                green = source.getValue();
                System.out.println("GREEN: " + green);
            }
        };
        greenSlider.addChangeListener(greenListener);
    }

    // REQUIRES: 0 <= startValue <= 255
    // MODIFIES: this
    // EFFECTS: starts blue slider
    private void startBlueSlider(int startValue) {
        blueSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 255, startValue);
        mainMenu.add(blueSlider);
        blueSlider.setMajorTickSpacing(255);
        blueSlider.setMinorTickSpacing(255 / 2);
        blueSlider.setPaintTicks(true);
        blueSlider.setPaintLabels(true);

        ChangeListener blueListener = new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                JSlider source = (JSlider) event.getSource();
                blue = source.getValue();
                System.out.println("BLUE: " + blue);
            }
        };
        blueSlider.addChangeListener(blueListener);
    }
}
