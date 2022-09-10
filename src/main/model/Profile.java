package model;

import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

// Represents a profile having a password, and name
public class Profile implements Writable {
    private String password;
    private String profileName;
    private String username;
    private String hyperLink;

    // REQUIRES: password has a non-zero length, profileName has a non-zero length
    // EFFECTS: profileName is set to name, this.password is set to password, username and URL is
    // automatically set to empty string unless specified.
    public Profile(String passWord, String name, String userName, String url) {
        this.password = passWord;
        this.profileName = name;
        this.username = userName;
        this.hyperLink = url;
    }


    // EFFECTS: changes information in password profile if parameters are different from existing information, otherwise
    // no change occurs.
    // MODIFIES: this
    public void editProfile(String newPassword, String newProfileName, String newUsername, String newlink) {
        if (!newPassword.equals(this.password)) {
            this.password = newPassword;
        }

        if (!newProfileName.equals(this.profileName)) {
            this.profileName = newProfileName;
        }

        if (!newUsername.equals(this.username)) {
            this.username = newUsername;
        }

        if (!newlink.equals(this.hyperLink)) {
            this.hyperLink = newlink;
        }
    }

    public void copyPassword() {
        StringSelection selection = new StringSelection(getPassword());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        EventLog.getInstance().logEvent(new Event("A password has been copied."));

    }

    public String getPassword() {
        return this.password;
    }

    public String getProfileName() {
        return this.profileName;
    }

    public String getUsername() {
        return this.username;
    }

    public String getLink() {
        return this.hyperLink;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Profile Name", profileName);
        json.put("Password", password);
        json.put("Username", username);
        json.put("URL", hyperLink);
        return json;
    }
}
