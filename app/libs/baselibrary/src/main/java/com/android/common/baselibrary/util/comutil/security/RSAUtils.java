package com.android.common.baselibrary.util.comutil.security;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAUtils {
    public static final String KEY_ALGORITHM = "RSA";
    private static final int MAX_DECRYPT_BLOCK = 128;
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    public static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";


    public static final String RSA_PRIVATE1024_VALUE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMeeEdMSitr4jHig" +
            "5X5BDq5dTKJedyk/azHCE0QaRuBt2ubXNjc/PwzJrMnLvyxE5wWkX7CHBvwOnHc8" +
            "Vz2NnShQJif35IX7xa84l7dW9zVJqCeyOCBV/lpMns/Lfm8/JdcQrlEvde21F30z" +
            "NiNQAU1SZpu1l/CKSIz2SxrCJJ+JAgMBAAECgYEAuqnRuTMJraHmmZa7g8iKVfrh" +
            "AqSbgXLQZoM5SOHZjVys6lgtVpRJ/UdJ0Jo6dscn0VrYMFbT7TgskENYwTJ/1d7V" +
            "v3551jlm9vmNFb55d3f1nZVuAZU3xrkJ+pT6sO73ZTF96ouyWH6YizGMg2N0RLh0" +
            "HL/2Ggn7/16yVOdM3AECQQDzIoUGeewvT7F9ZncjE/vFAlT+HYTUHtYu5cCQT7IB" +
            "OlI4MouHhm9SOHMFXj3L1UmUdKy9Aa40+RI7NLxktdgpAkEA0i4RX1tPHLn2T2si" +
            "T9ymXzcAfYzrIR8tj0Ib6vCwvI6MguuBcVfbnnfez2vm2miziQpj/AiobOlysv45" +
            "w+b4YQJALpAS182rvNfPTwu7jz05f15V9qCimpkZPbwEZ97LYU7RBjAv5pGJaj6j" +
            "UIje3tWHFOZPWpcizMRSiIIt/j+RGQJBAJ3MoJZrQ3ZZ2CfAU76J9w89iEy65D21" +
            "3srT44n8s3Sdwbj5f/HM+MJ9VPd+F5CbU0/sUS0Egw9iJg9k95Gxc4ECQQCrnV9j" +
            "MtQHkZ5rt4SwzUrvTBw++7bkpwWEcAPQ4cAX4qjcWPNqWz+sakbGlKtPTbcfpld4" +
            "hYFf7iPL2m/Ppy6v";

    public static final String RSA_PUBLIC1024_VALUE = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDHnhHTEora+Ix4oOV+QQ6uXUyi" +
            "XncpP2sxwhNEGkbgbdrm1zY3Pz8MyazJy78sROcFpF+whwb8Dpx3PFc9jZ0oUCYn" +
            "9+SF+8WvOJe3Vvc1SagnsjggVf5aTJ7Py35vPyXXEK5RL3XttRd9MzYjUAFNUmab" +
            "tZfwikiM9ksawiSfiQIDAQAB";

    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    public static String sign(byte[] data, String privateKey) throws Exception {
        PrivateKey privateK = KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(Base64Utils.decode(privateKey)));
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64Utils.encode(signature.sign());
    }

    public static String sign(byte[] data, PrivateKey privateKey) throws Exception {
        //PrivateKey privateK = KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(Base64Utils.decode(privateKey)));
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data);
        return Base64Utils.encode(signature.sign());
    }

    public static String signToHex(byte[] data, String privateKey) throws Exception {
        PrivateKey privateK = KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(Base64Utils.decode(privateKey)));
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return bytesToHexString(signature.sign());
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 255);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        PublicKey publicK = KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(Base64Utils.decode(publicKey)));
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64Utils.decode(sign));
    }

    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64Utils.decode(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        int i = 0;
        while (inputLen - offSet > 0) {
            byte[] cache;
            if (inputLen - offSet > 128) {
                cache = cipher.doFinal(encryptedData, offSet, 128);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * 128;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64Utils.decode(publicKey));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        int i = 0;
        while (inputLen - offSet > 0) {
            byte[] cache;
            if (inputLen - offSet > 128) {
                cache = cipher.doFinal(encryptedData, offSet, 128);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * 128;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64Utils.decode(publicKey));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        int i = 0;
        while (inputLen - offSet > 0) {
            byte[] cache;
            if (inputLen - offSet > 117) {
                cache = cipher.doFinal(data, offSet, 117);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * 117;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64Utils.decode(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        int i = 0;
        while (inputLen - offSet > 0) {
            byte[] cache;
            if (inputLen - offSet > 117) {
                cache = cipher.doFinal(data, offSet, 117);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * 117;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        return Base64Utils.encode(((Key) keyMap.get(PRIVATE_KEY)).getEncoded());
    }

    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        return Base64Utils.encode(((Key) keyMap.get(PUBLIC_KEY)).getEncoded());
    }

    /*
          加密或解密数据的通用方法
          @param srcData
          待处理的数据
          @param key
          公钥或者私钥
          @param mode
          指定是加密还是解密，值为Cipher.ENCRYPT_MODE或者Cipher.DECRYPT_MODE

       */

    /**
     * 处理加密（因为有长度限制，所以调整为分段加密）
     * @param srcData
     * @param key
     * @param mode
     * @return
     */
    private static byte[] processDataEnc(byte[] srcData, Key key, int mode) {
        //用来保存处理结果
        byte[] resultBytes = null;

        try {

            //构建Cipher对象，需要传入一个字符串，格式必须为"algorithm/mode/padding"或者"algorithm/",意为"算法/加密模式/填充方式"
            Cipher cipher = Cipher.getInstance("RSA/NONE/PKCS1Padding");
            //初始化Cipher，mode指定是加密还是解密，key为公钥或私钥
            cipher.init(mode, key);
            int inputLen = srcData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(srcData, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(srcData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            return encryptedData;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 处理解密
     * @param encryptedData
     * @param key
     * @param mode
     * @return
     */
    private static byte[] processDataDec(byte[] encryptedData, Key key, int mode) {
        try {

            //构建Cipher对象，需要传入一个字符串，格式必须为"algorithm/mode/padding"或者"algorithm/",意为"算法/加密模式/填充方式"
            Cipher cipher = Cipher.getInstance("RSA/NONE/PKCS1Padding");
            //初始化Cipher，mode指定是加密还是解密，key为公钥或私钥
            cipher.init(mode, key);

            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            int i = 0;
            while (inputLen - offSet > 0) {
                byte[] cache;
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return decryptedData;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /*
        将字符串形式的公钥转换为公钥对象
     */

    public static PublicKey keyStrToPublicKey(String publicKeyStr) throws Exception {

        PublicKey publicKey = null;

        byte[] keyBytes = Base64Utils.decode(publicKeyStr);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        try {

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            publicKey = keyFactory.generatePublic(keySpec);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return publicKey;

    }

    /*
        将字符串形式的私钥，转换为私钥对象
     */

    public static PrivateKey keyStrToPrivate(String privateKeyStr) throws Exception {

        PrivateKey privateKey = null;

        byte[] keyBytes = Base64Utils.decode(privateKeyStr);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

        try {

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            privateKey = keyFactory.generatePrivate(keySpec);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return privateKey;

    }


    public static String encDataByRSA(String data, String privateKey) throws Exception {
        byte[] encData = processDataEnc(data.getBytes(),keyStrToPrivate(privateKey),Cipher.ENCRYPT_MODE);
        return Base64Utils.encode(encData);
    }

    public static String encDataByRSA(String data, PrivateKey privateKey) throws Exception {
        byte[] encData = processDataEnc(data.getBytes(), privateKey,Cipher.ENCRYPT_MODE);
        return Base64Utils.encode(encData);
    }

    public static String decDataByRSA(String encData, PublicKey publicKey) throws Exception {
        byte[] encDataBase64 = Base64Utils.decode(encData);
        return new String(processDataDec(encDataBase64, publicKey, Cipher.DECRYPT_MODE));
    }


}
