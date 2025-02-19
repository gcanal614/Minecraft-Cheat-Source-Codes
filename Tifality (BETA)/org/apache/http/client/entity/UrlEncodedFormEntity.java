/*
 * Decompiled with CFR 0.152.
 */
package org.apache.http.client.entity;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@NotThreadSafe
public class UrlEncodedFormEntity
extends StringEntity {
    public UrlEncodedFormEntity(List<? extends NameValuePair> parameters2, String charset) throws UnsupportedEncodingException {
        super(URLEncodedUtils.format(parameters2, charset != null ? charset : HTTP.DEF_CONTENT_CHARSET.name()), ContentType.create("application/x-www-form-urlencoded", charset));
    }

    public UrlEncodedFormEntity(Iterable<? extends NameValuePair> parameters2, Charset charset) {
        super(URLEncodedUtils.format(parameters2, charset != null ? charset : HTTP.DEF_CONTENT_CHARSET), ContentType.create("application/x-www-form-urlencoded", charset));
    }

    public UrlEncodedFormEntity(List<? extends NameValuePair> parameters2) throws UnsupportedEncodingException {
        this(parameters2, (Charset)null);
    }

    public UrlEncodedFormEntity(Iterable<? extends NameValuePair> parameters2) {
        this(parameters2, null);
    }
}

