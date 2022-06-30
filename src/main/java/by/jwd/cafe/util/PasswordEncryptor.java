package by.jwd.cafe.util;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordEncryptor {
    public static String md5Apache(String password) {
        String md5Hex = DigestUtils.md5Hex(password);
        return md5Hex;
    }
}
