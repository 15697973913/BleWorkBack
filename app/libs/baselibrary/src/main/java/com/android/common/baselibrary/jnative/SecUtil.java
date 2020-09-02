package com.android.common.baselibrary.jnative;
import com.android.common.baselibrary.util.comutil.security.RSAUtils;

public class SecUtil {

    public static String getRSAsinatureWithLogin(String sourData) {
        return sourData;//JavaNative.rs(sourData);
    }

    public static String getRSAsinature(String sourData) {
        try {
            return RSAUtils.sign(sourData.getBytes(), RSAUtils.keyStrToPrivate(RSAUtils.RSA_PRIVATE1024_VALUE));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decodeByAES(String encData) {
        return AESUtil.decrypt(encData);
    }

    public static String encodeByAES(String sourceData) {
        return AESUtil.encrypt(sourceData);
    }

}
