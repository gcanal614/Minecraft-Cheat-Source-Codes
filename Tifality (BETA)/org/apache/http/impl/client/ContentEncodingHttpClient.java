/*
 * Decompiled with CFR 0.152.
 */
package org.apache.http.impl.client;

import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.protocol.RequestAcceptEncoding;
import org.apache.http.client.protocol.ResponseContentEncoding;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpProcessor;

@Deprecated
@ThreadSafe
public class ContentEncodingHttpClient
extends DefaultHttpClient {
    public ContentEncodingHttpClient(ClientConnectionManager conman, HttpParams params) {
        super(conman, params);
    }

    public ContentEncodingHttpClient(HttpParams params) {
        this(null, params);
    }

    public ContentEncodingHttpClient() {
        this((HttpParams)null);
    }

    protected BasicHttpProcessor createHttpProcessor() {
        BasicHttpProcessor result2 = super.createHttpProcessor();
        result2.addRequestInterceptor(new RequestAcceptEncoding());
        result2.addResponseInterceptor(new ResponseContentEncoding());
        return result2;
    }
}

