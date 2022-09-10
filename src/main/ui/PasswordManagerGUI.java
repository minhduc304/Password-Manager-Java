package ui;


import model.Event;
import model.EventLog;
import model.PasswordManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;

// Represents the GUI of the application allowing for interaction between the user
// and the PasswordManager.
public class PasswordManagerGUI extends JFrame implements ActionListener {
    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    private ActionListener actionListener = (ActionListener) this;
    private PasswordManagerApp pma;
    private JList list;

    public PasswordManagerGUI() throws FileNotFoundException {
        super("Password Manager");
        initializeGraphics();
        pma = new PasswordManagerApp();
    }

    // MODIFIES: this
    // EFFECTS:  initialize the main menu with all buttons in place.
    private void initializeGraphics() {

        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));

        setButtonArea();

        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        getContentPane().setBackground(Color.DARK_GRAY);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                EventLog log = EventLog.getInstance();
                for (Event e : log) {
                    System.out.println(e.getDescription());
                }
                //THEN you can exit the program
                System.exit(0);
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets up all buttons in the designated area for buttons (at the bottom of the main menu)
    private void setButtonArea() {
        JPanel buttonArea = new JPanel();
        buttonArea.setLayout(new GridLayout(0,1));
        add(buttonArea, BorderLayout.SOUTH);

        JButton addButton = new JButton("Add Profile");
        addButton.setActionCommand("add");
        addButton.addActionListener(actionListener);
        buttonArea.add(addButton);

        JButton displayButton = new JButton("Display Profiles");
        displayButton.setActionCommand("display");
        displayButton.addActionListener(actionListener);
        buttonArea.add(displayButton);

        JButton getButton = new JButton("Get Profile");
        getButton.setActionCommand("get");
        getButton.addActionListener(actionListener);
        buttonArea.add(getButton);

        JButton loadButton = new JButton("Load Profiles");
        loadButton.setActionCommand("load");
        loadButton.addActionListener(actionListener);
        buttonArea.add(loadButton);

        JButton saveButton = new JButton("Save Profiles");
        saveButton.setActionCommand("save");
        saveButton.addActionListener(actionListener);
        buttonArea.add(saveButton);
    }

    // EFFECTS: Handles button presses for the entire application
    // MODIFIES: this
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            createProfileCreationWizard();
        } else if (e.getActionCommand().equals("select")) {
            handleSelectProfile(list);
        } else if (e.getActionCommand().equals("display")) {
            displayProfilesWizard();
        } else if (e.getActionCommand().equals("load")) {
            pma.loadPasswordManager();
        } else if (e.getActionCommand().equals("save")) {
            pma.savePasswordManager();
        } else if (e.getActionCommand().equals("copy")) {
            pma.handleProfileOperations(list.getSelectedValue().toString(), "c");
        } else {
            BarChartPanel barChartPanel = new BarChartPanel();
            barChartPanel.createAndShowGUI(pma.displayProfiles().size(), getNumStrongPasswords(),
                    getNumWeakPasswords());
        }
    }

    // EFFECTS: opens a new window reminding the user to choose a profile first,
    // otherwise allows the user to access functionalities related to a Profile like copy, delete etc.
    // MODIFIES: this
    private void handleSelectProfile(JList list) {
        NewWindow window = new NewWindow("");
        window.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        if (list.getSelectedValue() == null) {
            JLabel label = new JLabel("Please select an existing profile.");
            window.setSize(300, 100);
            window.add(label);
        } else {
            JButton copyButton = new JButton("Copy password");
            copyButton.setActionCommand("copy");
            copyButton.addActionListener(actionListener);

            JButton deleteButton = new JButton("Delete profile");
            deleteButton.setActionCommand("delete");
            deleteButton.addActionListener(actionListener);

            JPanel panel = new JPanel();
            panel.add(copyButton, BorderLayout.NORTH);
            panel.add(deleteButton, BorderLayout.NORTH);
            window.setContentPane(panel);
        }
    }

    // EFFECTS: returns the number of profiles with strong passwords
    private int getNumStrongPasswords() {
        return pma.getNumStrongPasswords();
    }

    // EFFECTS: returns the number of profiles with weak passwords
    private int getNumWeakPasswords() {
        return pma.getNumWeakPasswords();
    }

    // EFFECTS: opens new window with a list of created profiles
    // MODIFIES: this
    private void displayProfilesWizard() {
        NewWindow window = new NewWindow("Your Password Profiles");
        window.setLayout(new FlowLayout());
        window.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        list = new JList(pma.displayProfiles().toArray());
        JButton statsButton = new JButton("See more statistics");
        JButton selectButton = new JButton("Select this profile");
        statsButton.setActionCommand("barChart");
        statsButton.addActionListener(actionListener);
        selectButton.setActionCommand("select");
        selectButton.addActionListener(actionListener);


        panel.add(list);
        panel.add(statsButton);
        panel.add(selectButton);
        window.add(panel, BorderLayout.SOUTH);

    }

    // EFFECTS: creates a popup for users to create a Password Profile.
    // MODIFIES: this
    private void createProfileCreationWizard() {
        NewWindow window = new NewWindow("Profile Creation");
        JTextField password = new JTextField(1);
        JTextField profileName = new JTextField(2);
        JTextField username = new JTextField(3);
        JTextField url = new JTextField(4);

        final GridLayout cl = new GridLayout(4, 1);
        final JPanel cards = new JPanel(cl);
        cards.add(new JLabel("Password (required)"));
        cards.add(password);
        cards.add(new JLabel("Profile Name (required)"));
        cards.add(profileName);
        cards.add(new JLabel("Username (optional)"));
        cards.add(username);
        cards.add(new JLabel("Password (optional)"));
        cards.add(url);
        window.add(cards, BorderLayout.NORTH);

        handleAddProfile(window, password, profileName, username, url);
    }

    // EFFECTS: if password or profile name is empty, asks user to fill them in before closing wizard,
    // and does not save Profile, otherwise save profile.
    // MODIFIES: PasswordManager, this
    private void handleAddProfile(NewWindow window, JTextField pw, JTextField pn, JTextField un, JTextField url) {
        window.addWindowListener(new WindowAdapter() {
            String ms1 = "It seems like your profile is missing a password and/or a profile name. Please fill them in!";
            String ms2 = " Leaving now would not save your profile. Are you sure?";
            String ms = ms1 + ms2;
            @Override
            public void windowClosing(WindowEvent e) {
                if (pw.getText().equals("") || pn.getText().equals("")) {
                    if (JOptionPane.showConfirmDialog(window, ms) == JOptionPane.YES_OPTION) {
                        window.setVisible(false);
                        window.dispose();
                    }
                } else {
                    if (JOptionPane.showConfirmDialog(window, "Are you sure?")
                            == JOptionPane.YES_OPTION) {
                        window.setVisible(false);
                        window.dispose();
                    }
                    addProfile(pw.getText(), pn.getText(), un.getText(), url.getText());
                }
            }
        });
    }

    // EFFECTS: runs the add profile method in the UI class
    // MODIFIES: PasswordManager
    private void addProfile(String password, String profileName, String username, String url) {
        pma.handleAddProfile(password, profileName, username, url);
    }


    //EFFECTS: runs the Password Manager App
    public static void main(String[] args) {
        try {
            new PasswordManagerGUI();
            new PasswordManagerApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }





}
