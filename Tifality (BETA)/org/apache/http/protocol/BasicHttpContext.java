/*
 * Decompiled with CFR 0.152.
 */
package org.apache.http.protocol;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

@ThreadSafe
public class BasicHttpContext
implements HttpContext {
    private final HttpContext parentContext;
    private final Map<String, Object> map = new ConcurrentHashMap<String, Object>();

    public BasicHttpContext() {
        this(null);
    }

    public BasicHttpContext(HttpContext parentContext) {
        this.parentContext = parentContext;
    }

    public Object getAttribute(String id) {
        Args.notNull(id, "Id");
        Object obj = this.map.get(id);
        if (obj == null && this.parentContext != null) {
            obj = this.parentContext.getAttribute(id);
        }
        return obj;
    }

    public void setAttribute(String id, Object obj) {
        Args.notNull(id, "Id");
        if (obj != null) {
            this.map.put(id, obj);
        } else {
            this.map.remove(id);
        }
    }

    public Object removeAttribute(String id) {
        Args.notNull(id, "Id");
        return this.map.remove(id);
    }

    public void clear() {
        this.map.clear();
    }

    public String toString() {
        return this.map.toString();
    }
}

