/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.client;

import de.liquiddev.ircclient.api.IrcClient;
import de.liquiddev.ircclient.client.ClientType;
import de.liquiddev.ircclient.client.SkidIrc;
import de.liquiddev.ircclient.client._irclIIlIIIllI;
import de.liquiddev.ircclient.client._ircllIIIlIlIl;
import de.liquiddev.ircclient.client._ircllIIllIlIl;
import de.liquiddev.ircclient.client._ircllIlIIIlII;
import de.liquiddev.ircclient.util.IrcServers;
import de.liquiddev.ircclient.util.IrcUuid;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IrcClientFactory {
    private static IrcClientFactory _ircIllIllIIlI = new IrcClientFactory();
    private final String _ircIllIllIIlI = new _ircllIlIIIlII(this).toString();

    public static IrcClientFactory getDefault() {
        return _ircIllIllIIlI;
    }

    /*
     * WARNING - void declaration
     */
    public IrcClient createIrcClient(ClientType clientType, String loginToken, String minecraftNickname, String clientVersion) {
        void var3_3;
        void var2_2;
        void var1_1;
        SSLContext sSLContext = this.createDefaultSslContext();
        Object object = clientType;
        object = IrcUuid.getUuid(object);
        List list = IrcServers.getDefaultServers();
        int n = IrcServers.getDefaultPort();
        return new SkidIrc(sSLContext, list, n, (String)object, (ClientType)var1_1, (String)var2_2, (String)var3_3, clientVersion);
    }

    /*
     * WARNING - void declaration
     */
    public IrcClient createIrcClient(String host, ClientType clientType, String loginToken, String minecraftNickname, String clientVersion) {
        void var3_3;
        void var2_2;
        void var1_1;
        SSLContext sSLContext = this.createDefaultSslContext();
        Object object = clientType;
        object = IrcUuid.getUuid(object);
        int n = IrcServers.getDefaultPort();
        return new SkidIrc(sSLContext, (String)var1_1, n, (String)object, (ClientType)var2_2, (String)var3_3, minecraftNickname, clientVersion);
    }

    public SSLContext createDefaultSslContext() {
        try {
            Throwable throwable = null;
            try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.getDecoder().decode(this._ircIllIllIIlI.getBytes()));){
                return this.createSslContext(new _ircllIIIlIlIl(this).toString(), new _irclIIlIIIllI(this).toString(), byteArrayInputStream);
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
        catch (Exception exception) {
            System.out.println(new _ircllIIllIlIl(this).toString());
            exception.printStackTrace();
            try {
                return SSLContext.getDefault();
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                NoSuchAlgorithmException noSuchAlgorithmException2 = noSuchAlgorithmException;
                noSuchAlgorithmException.printStackTrace();
                return null;
            }
        }
    }

    public SSLContext createSslContext(String trsuStoreType, String protocol, InputStream trustStream) {
        SSLContext sSLContext;
        TrustManagerFactory trustManagerFactory;
        TrustManager[] trustManagerArray = KeyStore.getInstance(trsuStoreType);
        trustManagerArray.load((InputStream)((Object)trustManagerFactory), null);
        trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init((KeyStore)trustManagerArray);
        trustManagerArray = trustManagerFactory.getTrustManagers();
        sSLContext = SSLContext.getInstance((String)((Object)sSLContext));
        sSLContext.init(null, trustManagerArray, null);
        return sSLContext;
    }

    private static String _ircIllIllIIlI(ClientType clientType) {
        return IrcUuid.getUuid(clientType);
    }

    private static List _ircIllIllIIlI() {
        return IrcServers.getDefaultServers();
    }

    private static int _ircIllIllIIlI() {
        return IrcServers.getDefaultPort();
    }
}

