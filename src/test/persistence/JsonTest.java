package persistence;

import model.Profile;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonTest {
    protected void checkProfile(String name, String password, String username, String url, Profile profile) {
        assertEquals(name, profile.getProfileName());
        assertEquals(password, profile.getPassword());
        assertEquals(username, profile.getUsername());
        assertEquals(url, profile.getLink());
    }
}
