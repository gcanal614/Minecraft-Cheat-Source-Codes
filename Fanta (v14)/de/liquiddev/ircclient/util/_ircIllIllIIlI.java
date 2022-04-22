/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.util;

import de.liquiddev.ircclient.util._ircIllIlIlIII;
import de.liquiddev.ircclient.util._irclllllIIlII;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.WeakHashMap;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class _ircIllIllIIlI {
    private static MessageDigest _ircIllIllIIlI = new WeakHashMap();
    private static Map _ircIllIllIIlI;

    private static void _ircIllIllIIlI() {
        try {
            _ircIllIllIIlI = MessageDigest.getInstance(new _irclllllIIlII().toString());
            return;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException(noSuchAlgorithmException);
        }
    }

    public static byte[] _ircIllIllIIlI(String string) {
        if (_ircIllIllIIlI.containsKey(string)) {
            return ((ByteBuffer)_ircIllIllIIlI.get(string)).array();
        }
        if (_ircIllIllIIlI == null) {
            try {
                _ircIllIllIIlI = MessageDigest.getInstance(new _irclllllIIlII().toString());
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw new RuntimeException(noSuchAlgorithmException);
            }
        }
        try {
            _ircIllIllIIlI.update(string.getBytes(new _ircIllIlIlIII().toString()));
            byte[] byArray = _ircIllIllIIlI.digest();
            _ircIllIllIIlI.put(string, ByteBuffer.wrap(byArray));
            return byArray;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException);
        }
    }
}

