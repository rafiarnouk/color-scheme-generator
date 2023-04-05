package ui;

import model.Colour;
import model.ColourScheme;
import model.Event;
import model.EventLog;
import model.Gallery;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.IOException;

// class representing graphic user interface
// CITATIONS: how to use JSlider from https://stackoverflow.com/a/67958796
public class GUI extends JFrame implements ActionListener, WindowListener {

    private JPanel mainMenu;
    private JPanel galleryView;
    private String schemeType;
    private String[] schemeTypes = { "monochromatic", "analogous", "triadic", "complementary"};
    private JButton createButton;
    private JButton backButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton galleryButton;
    private JButton brighterButton;
    private JButton darkerButton;
    private JButton clearButton;
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
    private boolean brighter;
    private boolean darker;

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
        addWindowListener(this);
        startMenu();
        startGallery();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        startRedSlider(RED_START);
        startGreenSlider(GREEN_START);
        startBlueSlider(BLUE_START);
        startSchemeChooser();
        makeMainMenuButtons();
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
        brighter = false;
        darker = false;
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
        int y = 65; // height of top bar
        for (ColourScheme cs : gallery.getGallery()) {
            for (int i = 0; i < cs.getSize(); i++) {
                Color color = new Color(cs.getColourAtIndex(i).getRed(),
                        cs.getColourAtIndex(i).getGreen(),
                        cs.getColourAtIndex(i).getBlue());
                g.setColor(color);
                if (brighter) {
                    g.setColor(color.brighter());
                } else if (darker) {
                    g.setColor(color.darker());
                }
                g.fillRect(WIDTH / cs.getSize() * i, y,
                        WIDTH / cs.getSize() + 2, SCHEME_HEIGHT);
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
    // EFFECTS: sets up menu buttons
    private void makeMainMenuButtons() {
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
        makeGalleryButtons();
        galleryView.setBackground(Color.white);
    }

    // MODIFIES: this
    // EFFECTS: sets up gallery buttons
    private void makeGalleryButtons() {
        backButton = new JButton("Back to Generator");
        brighterButton = new JButton("Make Schemes Brighter");
        darkerButton = new JButton("Make Schemes Darker");
        clearButton = new JButton("Clear Colour Schemes");
        galleryView.add(backButton);
        galleryView.add(brighterButton);
        galleryView.add(darkerButton);
        galleryView.add(clearButton);
        backButton.addActionListener(this);
        brighterButton.addActionListener(this);
        darkerButton.addActionListener(this);
        clearButton.addActionListener(this);
        backButton.setActionCommand("backButton");
        brighterButton.setActionCommand("brighterButton");
        darkerButton.setActionCommand("darkerButton");
        clearButton.setActionCommand("clearButton");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: opens gallery panel
    private void openGallery() {
        add(galleryView);
        darker = false;
        brighter = false;
        galleryView.setVisible(true);
        mainMenu.setVisible(false);
        gallery.display();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: opens main menu panel
    private void openMainMenu() {
        add(mainMenu);
        mainMenu.setVisible(true);
        galleryView.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: sets brighter schemes boolean to true and repaints
    private void brighterSchemes() {
        if (brighter) {
            brighter = false;
        } else {
            brighter = true;
            darker = false;
        }
        gallery.brighterSchemes();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: sets darker schemes boolean to true and repaints
    private void darkerSchemes() {
        if (darker) {
            darker = false;
        } else {
            darker = true;
            brighter = false;
        }
        gallery.darkerSchemes();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: clears out gallery
    private void clearGallery() {
        gallery.removeAllSchemes();
        repaint();
    }

    // EFFECTS: processes functionality for the buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        Colour colour = new Colour(red, green, blue);
        if (e.getActionCommand().equals("createButton")) {
            generateScheme(colour);
            gallery.addScheme(scheme);
            openGallery();
        } else if (e.getActionCommand().equals("schemeCombo")) {
            schemeType = (String) schemeCombo.getSelectedItem();
        } else if (e.getActionCommand().equals("backButton")) {
            openMainMenu();
        } else if (e.getActionCommand().equals("saveButton")) {
            saveGallery();
        } else if (e.getActionCommand().equals("loadButton")) {
            loadGallery();
        } else if (e.getActionCommand().equals("galleryButton")) {
            openGallery();
        } else if (e.getActionCommand().equals("brighterButton")) {
            brighterSchemes();
        } else if (e.getActionCommand().equals("darkerButton")) {
            darkerSchemes();
        } else if (e.getActionCommand().equals("clearButton")) {
            clearGallery();
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
        EventLog.getInstance().logEvent(new Event("Saved gallery to file"));
        try {
            jsonWriter.open();
            jsonWriter.write(gallery);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            //
        }
    }

    // MODIFIES: this
    // EFFECTS: loads gallery from file
    private void loadGallery() {
        EventLog.getInstance().logEvent(new Event("Loaded gallery from file"));
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
        redSlider.setName("Red Value");

        ChangeListener redListener = new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                JSlider source = (JSlider) event.getSource();
                red = source.getValue();
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
            }
        };
        blueSlider.addChangeListener(blueListener);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        for (model.Event event : model.EventLog.getInstance()) {
            System.out.println(event);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // no actions required
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // no actions required
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // no actions required
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // no actions required
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // no actions required
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // no actions required
    }
}
