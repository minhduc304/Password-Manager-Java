package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

// Test suite for methods in the PasswordManager class
public class PasswordManagerTest {

    PasswordManager testManager;
    Profile testProfile1;
    Profile testProfile2;

    @BeforeEach
    void setup() {
        testManager = new PasswordManager();
        testProfile1 = new Profile("password123", "No Security",
                "test-account1", "https://abc.com");
        testProfile2 = new Profile("password1234", "More Security",
                "test-account2", "https://abcd.com");
    }

    @Test
    void testConstructor() {
        assertEquals(0, testManager.length());
    }

    @Test
    void testGetProfile() {
        testManager.addProfile(testProfile1);
        testManager.addProfile(testProfile2);
        assertEquals(testProfile1, testManager.getProfile("No Security"));
        assertNull(testManager.getProfile("Does not exist"));
    }

    @Test
    void testDeleteProfile() {
        testManager.addProfile(testProfile1);
        testManager.addProfile(testProfile2);
        testManager.deleteProfile(testProfile1);
        assertNull(testManager.getProfile("No Security"));
        testManager.deleteProfile(testProfile2);
        assertNull(testManager.getProfile("More Security"));
        assertEquals(0, testManager.length());
        testManager.deleteProfile(testProfile1);
        assertEquals(0, testManager.length());
    }

    @Test
    void testAddProfile() {
        testManager.addProfile(testProfile1);
        assertEquals(1, testManager.length());
        testManager.addProfile(testProfile2);
        assertEquals(2, testManager.length());
    }

    @Test
    void testEditProfile() {
        testManager.addProfile(testProfile1);
        testManager.addProfile(testProfile2);
        testManager.editProfile(testProfile1, "3kn4e9n!k5re/f", "A lot of security",
                "test-account1", "https://abc.com");
        testManager.editProfile(testProfile2, "password1234", "More Security",
                "test-account20", "https://abcde.com");
        assertEquals("3kn4e9n!k5re/f", testProfile1.getPassword());
        assertEquals("A lot of security", testProfile1.getProfileName());
        assertEquals("test-account1", testProfile1.getUsername());
        assertEquals("https://abc.com", testProfile1.getLink());
    }

    @Test
    void testDisplayProfiles() {
        testManager.addProfile(testProfile1);
        ArrayList<String> test = new ArrayList<>();
        test.add("No Security");
        assertEquals(test, testManager.displayProfiles());
        testManager.addProfile(testProfile2);
        test.add("More Security");
        assertEquals(test, testManager.displayProfiles());

    }

}
