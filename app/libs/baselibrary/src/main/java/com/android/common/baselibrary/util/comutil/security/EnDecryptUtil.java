package com.android.common.baselibrary.util.comutil.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class EnDecryptUtil {
    private static Map<Character, Integer> CHAR2INT = new HashMap();
    private static char[] INT2CHAR = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static String keySpec = "fdslkfj1ddlkdsjksfj461ru1130jlj"; //31位

    static {
        INT2CHAR.toString();
        for (int i = 0; i < INT2CHAR.length; i++) {
            CHAR2INT.put(Character.valueOf(INT2CHAR[i]), Integer.valueOf(i));
        }
    }

    public static String encrypt(String str) throws Exception {
        Key key = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(keySpec.getBytes()));
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(1, key);
        return byte2string(cipher.doFinal(str.getBytes()));
    }

    public static String decrypt(String str) throws Exception {
        Key key = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(keySpec.getBytes()));
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(2, key);
        return new String(cipher.doFinal(string2byte(str)));
    }

    private static String byte2string(byte[] buff) {
        char[] str = new char[(buff.length * 2)];
        for (int i = 0; i < buff.length; i++) {
            str[i * 2] = INT2CHAR[(buff[i] >>> 4) & 15];
            str[(i * 2) + 1] = INT2CHAR[buff[i] & 15];
        }
        return new String(str);
    }

    private static byte[] string2byte(String string) {
        byte[] buff = new byte[(string.length() / 2)];
        char[] str = string.toCharArray();
        for (int i = 0; i < buff.length; i++) {
            buff[i] = (byte) (((Integer) CHAR2INT.get(Character.valueOf(str[(i * 2) + 1]))).intValue() | (((Integer) CHAR2INT.get(Character.valueOf(str[i * 2]))).intValue() << 4));
        }
        return buff;
    }

    public static String digest(String source) {
        return digest(source, true);
    }

    public static String digest(String source, boolean isUpper) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            md5.update(source.getBytes());
            if (isUpper) {
                return byte2string(md5.digest());
            }
            return byte2string(md5.digest()).toLowerCase();
        } catch (Exception e) {
           e.printStackTrace();
            return "";
        }
    }

    public static String encode(Object source) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(os);
        outputStream.writeObject(source);
        outputStream.flush();
        os.flush();
        return byte2string(os.toByteArray());
    }

    public static Object decode(String source) throws IOException, ClassNotFoundException {
        return new ObjectInputStream(new ByteArrayInputStream(string2byte(source.trim().toUpperCase()))).readObject();
    }

    public static void main(String[] args) throws Exception {
    }
}
