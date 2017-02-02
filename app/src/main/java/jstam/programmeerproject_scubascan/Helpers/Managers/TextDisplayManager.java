package jstam.programmeerproject_scubascan.Helpers.Managers;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Scuba Scan - TextDisplayManager
 *
 * Jessie Stam
 * 10560599
 *
 * Manager class that gets a text file from input stream and fills in data for given placeholders.
 *
 * This class originates from a class created by Hella Haanstra for the course Native App Studio at
 * the University of Amsterdam.
 */
public class TextDisplayManager implements Serializable {

    private String text;
    private List<String> placeholders;
    private int filledIn;
    private boolean htmlMode;

    // initialize manager
    {
        text = "";
        placeholders = new ArrayList<String>();
        filledIn = 0;
        htmlMode = true;
        clear();
    }

    /* Constructs a new display text reading its text from the given input stream. */
    public TextDisplayManager(InputStream stream) {
        read(stream);
    }

    /* Resets the display text back to an empty initial state. */
    public void clear() {
        text = "";
        placeholders.clear();
        filledIn = 0;
    }

    /* Replaces the next unfilled placeholder with the given word. */
    public void fillInPlaceholder(String word) {

        if (!isFilledIn()) {
            text = text.replace("<" + filledIn + ">", word);
            filledIn++;
        }
    }

    /* Returns true if all placeholders have been filled in. */
    public boolean isFilledIn() {
        return filledIn >= placeholders.size();
    }

    /* Reads initial display text from the given input stream. */
    public void read(InputStream stream) {
        read(new Scanner(stream));
    }

    /* Reads initial display text from the given Scanner. */
    private void read(Scanner input) {
        while (input.hasNext()) {
            String word = input.next();

            // if it's a placeholder, replace with number to find it back later
            if (word.startsWith("<")) {
                if (htmlMode) {
                    text += " <b><" + placeholders.size() + "></b>";
                } else {
                    text += " <" + placeholders.size() + ">";
                }

                String placeholder = word.substring(1, word.length() - 1).replace("-", " ");
                placeholders.add(placeholder);
            }
            // if it's a regular word, concatenate
            else {
                if (!text.isEmpty()) {
                    text += " ";
                }
                text += word;
            }
        }
    }

    /* Returns display text. */
    public String toString() {
        return text;
    }
}
