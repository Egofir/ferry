package com.yang.ferry.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;

import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    private static final int PASSWORD_AND_SALT_LENGTH = 64;

    public static String getMD5Str(String password) throws NoSuchAlgorithmException {
        String salt = IdUtil.simpleUUID();
        password = DigestUtil.md5Hex(password + salt);
        char[] md5 = new char[PASSWORD_AND_SALT_LENGTH];
        for (int i = 0; i < PASSWORD_AND_SALT_LENGTH; i++) {
            md5[i] = password.charAt(i / 2);
            md5[++i] = salt.charAt(i / 2);
        }
        return new String(md5);
    }
}
