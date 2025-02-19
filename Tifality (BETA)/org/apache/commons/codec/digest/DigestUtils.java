/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.codec.digest;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;

public class DigestUtils {
    private static final int STREAM_BUFFER_LENGTH = 1024;

    private static byte[] digest(MessageDigest digest, InputStream data2) throws IOException {
        return DigestUtils.updateDigest(digest, data2).digest();
    }

    public static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static MessageDigest getMd2Digest() {
        return DigestUtils.getDigest("MD2");
    }

    public static MessageDigest getMd5Digest() {
        return DigestUtils.getDigest("MD5");
    }

    public static MessageDigest getSha1Digest() {
        return DigestUtils.getDigest("SHA-1");
    }

    public static MessageDigest getSha256Digest() {
        return DigestUtils.getDigest("SHA-256");
    }

    public static MessageDigest getSha384Digest() {
        return DigestUtils.getDigest("SHA-384");
    }

    public static MessageDigest getSha512Digest() {
        return DigestUtils.getDigest("SHA-512");
    }

    @Deprecated
    public static MessageDigest getShaDigest() {
        return DigestUtils.getSha1Digest();
    }

    public static byte[] md2(byte[] data2) {
        return DigestUtils.getMd2Digest().digest(data2);
    }

    public static byte[] md2(InputStream data2) throws IOException {
        return DigestUtils.digest(DigestUtils.getMd2Digest(), data2);
    }

    public static byte[] md2(String data2) {
        return DigestUtils.md2(StringUtils.getBytesUtf8(data2));
    }

    public static String md2Hex(byte[] data2) {
        return Hex.encodeHexString(DigestUtils.md2(data2));
    }

    public static String md2Hex(InputStream data2) throws IOException {
        return Hex.encodeHexString(DigestUtils.md2(data2));
    }

    public static String md2Hex(String data2) {
        return Hex.encodeHexString(DigestUtils.md2(data2));
    }

    public static byte[] md5(byte[] data2) {
        return DigestUtils.getMd5Digest().digest(data2);
    }

    public static byte[] md5(InputStream data2) throws IOException {
        return DigestUtils.digest(DigestUtils.getMd5Digest(), data2);
    }

    public static byte[] md5(String data2) {
        return DigestUtils.md5(StringUtils.getBytesUtf8(data2));
    }

    public static String md5Hex(byte[] data2) {
        return Hex.encodeHexString(DigestUtils.md5(data2));
    }

    public static String md5Hex(InputStream data2) throws IOException {
        return Hex.encodeHexString(DigestUtils.md5(data2));
    }

    public static String md5Hex(String data2) {
        return Hex.encodeHexString(DigestUtils.md5(data2));
    }

    @Deprecated
    public static byte[] sha(byte[] data2) {
        return DigestUtils.sha1(data2);
    }

    @Deprecated
    public static byte[] sha(InputStream data2) throws IOException {
        return DigestUtils.sha1(data2);
    }

    @Deprecated
    public static byte[] sha(String data2) {
        return DigestUtils.sha1(data2);
    }

    public static byte[] sha1(byte[] data2) {
        return DigestUtils.getSha1Digest().digest(data2);
    }

    public static byte[] sha1(InputStream data2) throws IOException {
        return DigestUtils.digest(DigestUtils.getSha1Digest(), data2);
    }

    public static byte[] sha1(String data2) {
        return DigestUtils.sha1(StringUtils.getBytesUtf8(data2));
    }

    public static String sha1Hex(byte[] data2) {
        return Hex.encodeHexString(DigestUtils.sha1(data2));
    }

    public static String sha1Hex(InputStream data2) throws IOException {
        return Hex.encodeHexString(DigestUtils.sha1(data2));
    }

    public static String sha1Hex(String data2) {
        return Hex.encodeHexString(DigestUtils.sha1(data2));
    }

    public static byte[] sha256(byte[] data2) {
        return DigestUtils.getSha256Digest().digest(data2);
    }

    public static byte[] sha256(InputStream data2) throws IOException {
        return DigestUtils.digest(DigestUtils.getSha256Digest(), data2);
    }

    public static byte[] sha256(String data2) {
        return DigestUtils.sha256(StringUtils.getBytesUtf8(data2));
    }

    public static String sha256Hex(byte[] data2) {
        return Hex.encodeHexString(DigestUtils.sha256(data2));
    }

    public static String sha256Hex(InputStream data2) throws IOException {
        return Hex.encodeHexString(DigestUtils.sha256(data2));
    }

    public static String sha256Hex(String data2) {
        return Hex.encodeHexString(DigestUtils.sha256(data2));
    }

    public static byte[] sha384(byte[] data2) {
        return DigestUtils.getSha384Digest().digest(data2);
    }

    public static byte[] sha384(InputStream data2) throws IOException {
        return DigestUtils.digest(DigestUtils.getSha384Digest(), data2);
    }

    public static byte[] sha384(String data2) {
        return DigestUtils.sha384(StringUtils.getBytesUtf8(data2));
    }

    public static String sha384Hex(byte[] data2) {
        return Hex.encodeHexString(DigestUtils.sha384(data2));
    }

    public static String sha384Hex(InputStream data2) throws IOException {
        return Hex.encodeHexString(DigestUtils.sha384(data2));
    }

    public static String sha384Hex(String data2) {
        return Hex.encodeHexString(DigestUtils.sha384(data2));
    }

    public static byte[] sha512(byte[] data2) {
        return DigestUtils.getSha512Digest().digest(data2);
    }

    public static byte[] sha512(InputStream data2) throws IOException {
        return DigestUtils.digest(DigestUtils.getSha512Digest(), data2);
    }

    public static byte[] sha512(String data2) {
        return DigestUtils.sha512(StringUtils.getBytesUtf8(data2));
    }

    public static String sha512Hex(byte[] data2) {
        return Hex.encodeHexString(DigestUtils.sha512(data2));
    }

    public static String sha512Hex(InputStream data2) throws IOException {
        return Hex.encodeHexString(DigestUtils.sha512(data2));
    }

    public static String sha512Hex(String data2) {
        return Hex.encodeHexString(DigestUtils.sha512(data2));
    }

    @Deprecated
    public static String shaHex(byte[] data2) {
        return DigestUtils.sha1Hex(data2);
    }

    @Deprecated
    public static String shaHex(InputStream data2) throws IOException {
        return DigestUtils.sha1Hex(data2);
    }

    @Deprecated
    public static String shaHex(String data2) {
        return DigestUtils.sha1Hex(data2);
    }

    public static MessageDigest updateDigest(MessageDigest messageDigest, byte[] valueToDigest) {
        messageDigest.update(valueToDigest);
        return messageDigest;
    }

    public static MessageDigest updateDigest(MessageDigest digest, InputStream data2) throws IOException {
        byte[] buffer = new byte[1024];
        int read = data2.read(buffer, 0, 1024);
        while (read > -1) {
            digest.update(buffer, 0, read);
            read = data2.read(buffer, 0, 1024);
        }
        return digest;
    }

    public static MessageDigest updateDigest(MessageDigest messageDigest, String valueToDigest) {
        messageDigest.update(StringUtils.getBytesUtf8(valueToDigest));
        return messageDigest;
    }
}

