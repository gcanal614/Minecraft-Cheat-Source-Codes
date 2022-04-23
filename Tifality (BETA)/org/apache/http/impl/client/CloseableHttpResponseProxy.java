/*
 * Decompiled with CFR 0.152.
 */
package org.apache.http.impl.client;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

@NotThreadSafe
class CloseableHttpResponseProxy
implements InvocationHandler {
    private final HttpResponse original;

    CloseableHttpResponseProxy(HttpResponse original) {
        this.original = original;
    }

    public void close() throws IOException {
        HttpEntity entity = this.original.getEntity();
        EntityUtils.consume(entity);
    }

    public Object invoke(Object proxy, Method method, Object[] args2) throws Throwable {
        String mname = method.getName();
        if (mname.equals("close")) {
            this.close();
            return null;
        }
        try {
            return method.invoke(this.original, args2);
        }
        catch (InvocationTargetException ex) {
            Throwable cause = ex.getCause();
            if (cause != null) {
                throw cause;
            }
            throw ex;
        }
    }

    public static CloseableHttpResponse newProxy(HttpResponse original) {
        return (CloseableHttpResponse)Proxy.newProxyInstance(CloseableHttpResponseProxy.class.getClassLoader(), new Class[]{CloseableHttpResponse.class}, new CloseableHttpResponseProxy(original));
    }
}

