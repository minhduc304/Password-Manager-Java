package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents the file holding all profiles created
public class PasswordManager implements Writable {

    ArrayList<Profile> profiles;

    public PasswordManager() {
        this.profiles = new ArrayList<Profile>();
    }

    // REQUIRES: profileName has a non-zero length
    // EFFECTS: returns the profile given its name, if not found return nothing
    public Profile getProfile(String profileName) {
        for (Profile p : this.profiles) {
            if (p.getProfileName().equals(profileName)) {
                return p;
            }
        }
        return null;
    }

    // REQUIRES: this.profiles is not empty
    // EFFECTS: deletes a profile from this.profiles
    // MODIFIES: this
    public void deleteProfile(Profile profile) {
        if (!profiles.isEmpty()) {
            profiles.remove(profile);
        }
    }

    // EFFECTS: creates a new Profile instance inside this.profiles
    // MODIFIES: this
    public void addProfile(Profile profile) {
        profiles.add(profile);
        EventLog.getInstance().logEvent(new Event("New profile added to Password Manager."));
    }

    // EFFECTS: edits the profile
    // MODIFIES: this
    public void editProfile(Profile profile, String newPassword, String newProfileName,
                            String newUsername, String newLink) {
        profile.editProfile(newPassword, newProfileName, newUsername, newLink);
    }

    //EFFECTS: return the number of profiles in Password Manager
    public int length() {
        return profiles.size();
    }

    // EFFECTS: return all profiles created
    public ArrayList<String> displayProfiles() {
        ArrayList<String> out = new ArrayList<>();
        for (Profile p : profiles) {
            out.add(p.getProfileName());
        }
        EventLog.getInstance().logEvent(new Event("Displayed all profiles in the Password Manager."));
        return out;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("profiles", profilesToJson());
        return json;
    }

    //EFFECTS: returns profiles in the Password Manager as a JSON array
    private JSONArray profilesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Profile p : profiles) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    public ArrayList<Profile> getProfiles() {
        return this.profiles;
    }
}
