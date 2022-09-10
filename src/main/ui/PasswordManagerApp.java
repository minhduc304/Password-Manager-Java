package ui;

import model.PasswordManager;
import model.Profile;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Represents the UI of the application allowing for interaction between the user
// and the PasswordManager.
public class PasswordManagerApp {
    private PasswordManager pm;
    private Scanner input;
    private static final String JSON_STORE = "./data/PasswordManager.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: runs the Password Manager application
    public PasswordManagerApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        init();
        //runPasswordManager();
    }

    // MODIFIES: this
    // EFFECTS: initializes the PasswordManager
    private void init() {
        pm = new PasswordManager();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }


    // EFFECTS: saves the workroom to file
    public void savePasswordManager() {
        try {
            jsonWriter.open();
            jsonWriter.write(pm);
            jsonWriter.close();
            for (String p : pm.displayProfiles()) {
                System.out.println("Saved " + p + " to " + JSON_STORE);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    public void loadPasswordManager() {
        try {
            pm = jsonReader.read();
            for (String p : pm.displayProfiles()) {
                System.out.println("Loaded " + p + " from " + JSON_STORE);
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }



    //EFFECTS: handle user inputs for Profile specific operations: copying, editing, deleting
    public void handleProfileOperations(String userIn1, String userIn2) {

        if (userIn2.equals("c")) {
            pm.getProfile(userIn1).copyPassword();

        } else if (userIn2.equals("d")) {
            handleDeleteProfile(pm.getProfile(userIn1));
        } else if (userIn2.equals("e")) {
            handleEditProfile(userIn1);
        } else if (userIn2.equals("q")) {
            //
        } else {
            System.out.println("Selection not valid...");
        }
    }



    //EFFECTS: handle user inputs for adding new profiles
    public void handleAddProfile(String pw, String pn, String un, String url) {
        Profile newProfile = new Profile(pw, pn, un, url);
        pm.addProfile(newProfile);
    }

    // REQUIRES: at least two profiles already exist
    // EFFECTS: handle user inputs for deleting existing profiles
    public void handleDeleteProfile(Profile profile) {
        if (pm.length() == 2) {
            pm.deleteProfile(profile);
        } else {
            System.out.println("Cannot delete profile if only one profile exists!");
        }
    }

    // EFFECTS: returns the number of profiles with "strong" passwords
    public int getNumStrongPasswords() {
        int result = 0;
        for (Profile p : pm.getProfiles()) {
            if (p.getPassword().length() > 10) {
                result += 1;
            }
        }
        return result;
    }

    // EFFECTS: returns the number of profiles with "weak" passwords
    public int getNumWeakPasswords() {
        int result = 0;
        for (Profile p : pm.getProfiles()) {
            if (p.getPassword().length() <= 10) {
                result += 1;
            }
        }
        return result;
    }



    // EFFECTS: outputs all profiles created
    public ArrayList<String> displayProfiles() {
        return pm.displayProfiles();
    }

    //EFFECTS: handle user inputs for editing existing profiles
    public void handleEditProfile(String name) {
        String pw = "";
        String pn = "";
        String un = "";
        String url = "";
        do {
            System.out.println("\nEnter the following information to add profile (** means required):");
            System.out.println("\n** Password:");
            pw = input.next();
            System.out.println("\n** Profile Name:");
            pn = input.next();
            System.out.println("\n Username:");
            un = input.next();
            System.out.println("\n URL:");
            url = input.next();
            if (pw.equals("") || pn.equals("")) {
                System.out.println("Please check if you have filled the PASSWORD and PROFILE NAME fields");
                continue;
            } else {
                break;
            }
        } while (true);
        pm.getProfile(name).editProfile(pw, pn, un, url);
    }



    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add new Profile");
        System.out.println("\td -> display all Profiles");
        System.out.println("\tg -> get a Profile by searching its name (not username)");
        System.out.println("\ts -> save profiles to file");
        System.out.println("\tl -> load profiles from file");
        System.out.println("\tq -> quit");
    }

}
