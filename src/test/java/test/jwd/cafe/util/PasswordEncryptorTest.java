package test.jwd.cafe.util;

import by.jwd.cafe.util.PasswordEncryptor;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PasswordEncryptorTest {
    @Test
    public void testEncryptPositive() {
        String password1 = "vYhsk88";
        String password2 = "vYhsk88";
        String hash1 = PasswordEncryptor.md5Apache(password1);
        String hash2 = PasswordEncryptor.md5Apache(password2);
        Assert.assertTrue(hash1.equals(hash2));
    }

    @Test
    public void testEncryptNegative() {
        String password1 = "vYhsk88";
        String password2 = "JbflP74";
        String hash1 = PasswordEncryptor.md5Apache(password1);
        String hash2 = PasswordEncryptor.md5Apache(password2);
        Assert.assertFalse(hash1.equals(hash2));
    }

}