/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.util;

import de.liquiddev.ircclient.client.ClientType;
import de.liquiddev.ircclient.util._ircIIIIIlIIll;
import de.liquiddev.ircclient.util._ircIIIIlIlIlI;
import de.liquiddev.ircclient.util._ircIIIIllIlll;
import de.liquiddev.ircclient.util._ircIIIlIIIIll;
import de.liquiddev.ircclient.util._ircIlIIIIlIlI;
import de.liquiddev.ircclient.util._ircIlIIlIllIl;
import de.liquiddev.ircclient.util._ircIllIIIlIlI;
import de.liquiddev.ircclient.util._ircIllIIlIlll;
import de.liquiddev.ircclient.util._ircIllIlIIIII;
import de.liquiddev.ircclient.util._irclIIIIlIIII;
import de.liquiddev.ircclient.util._irclIIIlIlllI;
import de.liquiddev.ircclient.util._irclIIlIIIIIl;
import de.liquiddev.ircclient.util._irclIIlIllIII;
import de.liquiddev.ircclient.util._irclllIllIIII;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.UUID;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IrcUuid {
    private static byte[] _ircIllIllIIlI = "(jT3n:\"xJ`v68\"^b$9h9^~ET9&456{}8e]fZ".getBytes();

    public static String getUuid(ClientType clientType) {
        Object object;
        block13: {
            Object object2;
            object = IrcUuid._ircIllIllIIlI();
            if (!((File)object).exists() && ((File)(object2 = new File(String.valueOf(System.getProperty(new _ircIIIIllIlll().toString())) + new _ircIllIIIlIlI().toString() + new _ircIIIIlIlIlI().toString()))).exists()) {
                try {
                    Throwable throwable = null;
                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader((File)object2));){
                        object2 = bufferedReader.readLine().replace(new _ircIllIlIIIII().toString(), new _ircIlIIIIlIlI().toString());
                    }
                    catch (Throwable throwable2) {
                        if (throwable == null) {
                            throwable = throwable2;
                        } else if (throwable != throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                        throw throwable;
                    }
                }
                catch (IOException iOException) {
                    break block13;
                }
                IrcUuid._ircIllIllIIlI((String)object2);
            }
        }
        if ((object = IrcUuid._ircIllIllIIlI((File)object)) == null) {
            object = UUID.randomUUID().toString();
            IrcUuid._ircIllIllIIlI((String)object);
        }
        return object;
    }

    private static String _ircIllIllIIlI(File object) {
        if (!((File)object).exists()) {
            return null;
        }
        try {
            Throwable throwable = null;
            try {
                object = new FileInputStream((File)object);
                try {
                    int n;
                    byte[] byArray = new byte[1024];
                    int n2 = 0;
                    while ((n = ((InputStream)object).read()) != -1) {
                        byArray[n2] = (byte)(n ^ _ircIllIllIIlI[n2 % _ircIllIllIIlI.length]);
                        ++n2;
                    }
                    return new String(Arrays.copyOfRange(byArray, 0, n2));
                }
                finally {
                    ((InputStream)object).close();
                }
            }
            catch (Throwable throwable2) {
                if (throwable == null) {
                    throwable = throwable2;
                } else if (throwable != throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
        }
        catch (IOException iOException) {
            throw new RuntimeException(new _ircIIIIIlIIll().toString(), iOException);
        }
    }

    private static void _ircIllIllIIlI(String object) {
        object = ((String)object).getBytes();
        Object object2 = IrcUuid._ircIllIllIIlI();
        try {
            Throwable throwable = null;
            try {
                object2 = new FileOutputStream((File)object2);
                try {
                    int n = 0;
                    while (n < ((Object)object).length) {
                        ((FileOutputStream)object2).write(object[n] ^ _ircIllIllIIlI[n % _ircIllIllIIlI.length]);
                        ++n;
                    }
                    return;
                }
                finally {
                    ((FileOutputStream)object2).close();
                }
            }
            catch (Throwable throwable2) {
                if (throwable == null) {
                    throwable = throwable2;
                } else if (throwable != throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
        }
        catch (IOException iOException) {
            throw new RuntimeException(new _ircIllIIlIlll().toString(), iOException);
        }
    }

    private static File _ircIllIllIIlI() {
        Object object = System.getProperty(new _irclIIlIllIII().toString()).toUpperCase();
        if (((String)object).contains(new _irclllIllIIII().toString())) {
            object = System.getenv(new _irclIIIIlIIII().toString());
        } else {
            object = System.getProperty(new _irclIIlIIIIIl().toString());
            object = String.valueOf(object) + new _ircIlIIlIllIl().toString();
        }
        object = new File(String.valueOf(object) + new _irclIIIlIlllI().toString());
        if (!((File)object).exists()) {
            ((File)object).mkdirs();
        }
        return new File(String.valueOf(((File)object).getAbsolutePath()) + new _ircIIIlIIIIll().toString());
    }

    private static String _ircIllIllIIlI() {
        String string = System.getProperty(new _irclIIlIllIII().toString()).toUpperCase();
        if (string.contains(new _irclllIllIIII().toString())) {
            string = System.getenv(new _irclIIIIlIIII().toString());
        } else {
            string = System.getProperty(new _irclIIlIIIIIl().toString());
            string = String.valueOf(string) + new _ircIlIIlIllIl().toString();
        }
        return string;
    }

    private static void _ircIllIllIIlI() {
        Object object = new File(String.valueOf(System.getProperty(new _ircIIIIllIlll().toString())) + new _ircIllIIIlIlI().toString() + new _ircIIIIlIlIlI().toString());
        if (!((File)object).exists()) {
            return;
        }
        try {
            Throwable throwable = null;
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader((File)object));){
                object = bufferedReader.readLine().replace(new _ircIllIlIIIII().toString(), new _ircIlIIIIlIlI().toString());
            }
            catch (Throwable throwable2) {
                if (throwable == null) {
                    throwable = throwable2;
                } else if (throwable != throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
        }
        catch (IOException iOException) {
            return;
        }
        IrcUuid._ircIllIllIIlI((String)object);
    }
}

