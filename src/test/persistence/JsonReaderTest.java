package persistence;

import model.PasswordManager;
import model.Profile;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Test suite for methods testing reading into a Json file
public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PasswordManager pw = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyPasswordManager() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPasswordManager.json");
        try {
            PasswordManager pw = reader.read();
            //Profile test = new Profile("test123", "My Profile", "test", "test.com");
            assertEquals(0, pw.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderPasswordManager() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPasswordManager.json");
        try {
            PasswordManager pw = reader.read();
            Profile test1 = new Profile("test1", "My Profile1", "testUser1", "test1.com");
            Profile test2 = new Profile("test2", "My Profile2", "testUser2", "");
            pw.addProfile(test1);
            assertEquals(1, pw.length());
            pw.addProfile(test2);
            assertEquals(2, pw.length());
            checkProfile("My Profile1", "test1", "testUser1", "test1.com",
                    pw.getProfile("My Profile1"));
            checkProfile("My Profile2", "test2", "testUser2", "",
                    pw.getProfile("My Profile2"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
