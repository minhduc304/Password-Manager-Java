package persistence;

import model.PasswordManager;
import model.Profile;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Test suite for methods testing writing into a Json file
public class JsonWriterTest extends JsonTest{


    @Test
    void testWriterInvalidFile() {
        try {
            PasswordManager pw = new PasswordManager();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyPasswordManager() {
        try {
            PasswordManager pw = new PasswordManager();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPasswordManager.json");
            writer.open();
            writer.write(pw);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPasswordManager.json");
            pw = reader.read();
            assertEquals(0, pw.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterPasswordManager() {
        try {
            PasswordManager pw = new PasswordManager();
            Profile test1 = new Profile("test123", "test1", "", "t1.com");
            Profile test2 = new Profile("test1234", "test2", "tt", "tt.com");
            pw.addProfile(test1);
            pw.addProfile(test2);
            JsonWriter writer = new JsonWriter("./data/testWriterPasswordManager");
            writer.open();
            writer.write(pw);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterPasswordManager");
            pw = reader.read();
            assertEquals(2, pw.length());
            ArrayList<String> profiles = new ArrayList<>();
            profiles.add("test1");
            profiles.add("test2");
            assertEquals(profiles, pw.displayProfiles());
            checkProfile("test1", "test123", "", "t1.com", test1);
            checkProfile("test2", "test1234", "tt", "tt.com", test2);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
