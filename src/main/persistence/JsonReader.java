package persistence;


import model.PasswordManager;
import model.Profile;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads the password manager from JSON data stored in file
// Implementation based on JsonSerializationDemo
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads password manager from JSON object and returns it
    // throws IOException if an error occurs reading data from file
    public PasswordManager read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePasswordManager(jsonObject);
    }

    // EFFECTS: parses  password manager from JSON object and returns it
    private PasswordManager parsePasswordManager(JSONObject jsonObject) {
        //String name = jsonObject.getString("Profile Name");
        PasswordManager pw = new PasswordManager();
        addProfiles(pw, jsonObject);
        return pw;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // MODIFIES: pw
    // EFFECTS: parses profiles from JSON object and adds it to password manager
    private void addProfiles(PasswordManager pw, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("profiles");
        for (Object json : jsonArray) {
            JSONObject nextProfile = (JSONObject) json;
            addProfile(pw, nextProfile);
        }
    }

    // MODIFIES: pw
    // EFFECTS: parses profile from JSON object and adds it to list of profiles
    private void addProfile(PasswordManager pw, JSONObject jsonObject) {
        String password = jsonObject.getString("Password");
        String profileName = jsonObject.getString("Profile Name");
        String username = jsonObject.getString("Username");
        String url = jsonObject.getString("URL");
        Profile profile = new Profile(password, profileName, username, url);
        pw.addProfile(profile);
    }

}
