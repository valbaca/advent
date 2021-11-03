package com.valbaca.advent.elf;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

import java.nio.charset.StandardCharsets;

public class MD5Hasher {

    private static MD5Hasher SINGLETON = new MD5Hasher();

    private DigestUtils md5Hasher;

    public MD5Hasher() {
        md5Hasher = new DigestUtils(MessageDigestAlgorithms.MD5);
    }

    public String hex(String s) {
        return md5Hasher.digestAsHex(s.getBytes(StandardCharsets.UTF_8));
    }

    public static String hexHash(String s) {
        return SINGLETON.hex(s);
    }
}
