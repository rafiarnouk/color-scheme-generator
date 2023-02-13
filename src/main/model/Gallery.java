package model;

import java.util.ArrayList;
import java.util.List;

public class Gallery {
    private List<ColourScheme> gallery;

    public Gallery() {
        gallery = new ArrayList<>();
    }

    public List<ColourScheme> getGallery() {
        return gallery;
    }

    public Integer getSize() {
        return gallery.size();
    }

    public void addScheme(ColourScheme cs) {
        gallery.add(cs);
    }

    public void viewGallery() {
        if (gallery.isEmpty()) {
            System.out.println("You haven't added any colour schemes to your gallery yet.");
        } else if (this.getSize() == 1) {
            System.out.println("You have 1 colour scheme in your gallery.");
        } else {
            System.out.println("You have " + this.getSize() + " colour schemes in your gallery.");
        }

        System.out.println("\t");

        int preventDoubleLineAtEnd = 0;
        for (ColourScheme cs : gallery) {
            cs.displayScheme();
            if (preventDoubleLineAtEnd < gallery.size() - 1) {
                System.out.println("\t");
            }
            preventDoubleLineAtEnd++;
        }
    }
}
