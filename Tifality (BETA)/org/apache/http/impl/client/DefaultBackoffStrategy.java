/*
 * Decompiled with CFR 0.152.
 */
package org.apache.http.impl.client;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ConnectionBackoffStrategy;

public class DefaultBackoffStrategy
implements ConnectionBackoffStrategy {
    public boolean shouldBackoff(Throwable t) {
        return t instanceof SocketTimeoutException || t instanceof ConnectException;
    }

    public boolean shouldBackoff(HttpResponse resp) {
        return resp.getStatusLine().getStatusCode() == 503;
    }
}

