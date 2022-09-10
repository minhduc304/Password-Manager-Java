package ui;

import javax.swing.*;

// A class that represents a new window object
public class NewWindow extends JFrame {

    // EFFECTS: constructs a new window
    public NewWindow(String name) {
        super(name);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(600, 300);
        setVisible(true);
    }
}
