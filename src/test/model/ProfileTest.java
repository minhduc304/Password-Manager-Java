package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Test suite for methods in the Pofile class
class ProfileTest {

    private Profile testProfile;

    @BeforeEach
    void setup() {
        testProfile = new Profile("password123", "No Security",
                "testaccount", "https://abc.com");
    }

    @Test
    void testConstructor() {
        assertEquals("password123", testProfile.getPassword());
        assertEquals("No Security", testProfile.getProfileName());
        assertEquals("testaccount", testProfile.getUsername());
        assertEquals("https://abc.com", testProfile.getLink());
    }

    @Test
    void testCopyPassword() throws IOException, UnsupportedFlavorException {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        testProfile.copyPassword();

        assertEquals("password123", clipboard.getData(DataFlavor.stringFlavor));

        testProfile.editProfile("password1234", "More Security",
                "test-account",
                "https://abcd.com");

        testProfile.copyPassword();
        assertEquals("password1234", clipboard.getData(DataFlavor.stringFlavor));

    }

    @Test
    void testEditProfile() {
        testProfile.editProfile("password1234", "More Security",
                "test-account",
                "https://abcd.com");
        assertEquals("password1234", testProfile.getPassword());
        assertEquals("More Security", testProfile.getProfileName());
        assertEquals("test-account", testProfile.getUsername());
        assertEquals("https://abcd.com", testProfile.getLink());
    }




}