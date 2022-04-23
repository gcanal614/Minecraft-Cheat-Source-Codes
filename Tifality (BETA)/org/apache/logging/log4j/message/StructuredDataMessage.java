/*
 * Decompiled with CFR 0.152.
 */
package org.apache.logging.log4j.message;

import java.util.Map;
import org.apache.logging.log4j.message.MapMessage;
import org.apache.logging.log4j.message.StructuredDataId;
import org.apache.logging.log4j.util.EnglishEnums;

public class StructuredDataMessage
extends MapMessage {
    private static final long serialVersionUID = 1703221292892071920L;
    private static final int MAX_LENGTH = 32;
    private static final int HASHVAL = 31;
    private StructuredDataId id;
    private String message;
    private String type;

    public StructuredDataMessage(String id, String msg, String type2) {
        this.id = new StructuredDataId(id, null, null);
        this.message = msg;
        this.type = type2;
    }

    public StructuredDataMessage(String id, String msg, String type2, Map<String, String> data2) {
        super(data2);
        this.id = new StructuredDataId(id, null, null);
        this.message = msg;
        this.type = type2;
    }

    public StructuredDataMessage(StructuredDataId id, String msg, String type2) {
        this.id = id;
        this.message = msg;
        this.type = type2;
    }

    public StructuredDataMessage(StructuredDataId id, String msg, String type2, Map<String, String> data2) {
        super(data2);
        this.id = id;
        this.message = msg;
        this.type = type2;
    }

    private StructuredDataMessage(StructuredDataMessage msg, Map<String, String> map2) {
        super(map2);
        this.id = msg.id;
        this.message = msg.message;
        this.type = msg.type;
    }

    protected StructuredDataMessage() {
    }

    @Override
    public String[] getFormats() {
        String[] formats = new String[Format.values().length];
        int i = 0;
        for (Format format : Format.values()) {
            formats[i++] = format.name();
        }
        return formats;
    }

    public StructuredDataId getId() {
        return this.id;
    }

    protected void setId(String id) {
        this.id = new StructuredDataId(id, null, null);
    }

    protected void setId(StructuredDataId id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    protected void setType(String type2) {
        if (type2.length() > 32) {
            throw new IllegalArgumentException("structured data type exceeds maximum length of 32 characters: " + type2);
        }
        this.type = type2;
    }

    @Override
    public String getFormat() {
        return this.message;
    }

    protected void setMessageFormat(String msg) {
        this.message = msg;
    }

    @Override
    protected void validate(String key, String value) {
        this.validateKey(key);
    }

    private void validateKey(String key) {
        char[] chars;
        if (key.length() > 32) {
            throw new IllegalArgumentException("Structured data keys are limited to 32 characters. key: " + key);
        }
        for (char c : chars = key.toCharArray()) {
            if (c >= '!' && c <= '~' && c != '=' && c != ']' && c != '\"') continue;
            throw new IllegalArgumentException("Structured data keys must contain printable US ASCII charactersand may not contain a space, =, ], or \"");
        }
    }

    @Override
    public String asString() {
        return this.asString(Format.FULL, null);
    }

    @Override
    public String asString(String format) {
        try {
            return this.asString(EnglishEnums.valueOf(Format.class, format), null);
        }
        catch (IllegalArgumentException ex) {
            return this.asString();
        }
    }

    public final String asString(Format format, StructuredDataId structuredDataId) {
        String msg;
        StructuredDataId id;
        StringBuilder sb = new StringBuilder();
        boolean full = Format.FULL.equals((Object)format);
        if (full) {
            String type2 = this.getType();
            if (type2 == null) {
                return sb.toString();
            }
            sb.append(this.getType()).append(" ");
        }
        if ((id = (id = this.getId()) != null ? id.makeId(structuredDataId) : structuredDataId) == null || id.getName() == null) {
            return sb.toString();
        }
        sb.append("[");
        sb.append(id);
        sb.append(" ");
        this.appendMap(sb);
        sb.append("]");
        if (full && (msg = this.getFormat()) != null) {
            sb.append(" ").append(msg);
        }
        return sb.toString();
    }

    @Override
    public String getFormattedMessage() {
        return this.asString(Format.FULL, null);
    }

    @Override
    public String getFormattedMessage(String[] formats) {
        if (formats != null && formats.length > 0) {
            for (String format : formats) {
                if (Format.XML.name().equalsIgnoreCase(format)) {
                    return this.asXML();
                }
                if (!Format.FULL.name().equalsIgnoreCase(format)) continue;
                return this.asString(Format.FULL, null);
            }
            return this.asString(null, null);
        }
        return this.asString(Format.FULL, null);
    }

    private String asXML() {
        StringBuilder sb = new StringBuilder();
        StructuredDataId id = this.getId();
        if (id == null || id.getName() == null || this.type == null) {
            return sb.toString();
        }
        sb.append("<StructuredData>\n");
        sb.append("<type>").append(this.type).append("</type>\n");
        sb.append("<id>").append(id).append("</id>\n");
        super.asXML(sb);
        sb.append("</StructuredData>\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return this.asString(null, null);
    }

    @Override
    public MapMessage newInstance(Map<String, String> map2) {
        return new StructuredDataMessage(this, map2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        StructuredDataMessage that = (StructuredDataMessage)o;
        if (!super.equals(o)) {
            return false;
        }
        if (this.type != null ? !this.type.equals(that.type) : that.type != null) {
            return false;
        }
        if (this.id != null ? !this.id.equals(that.id) : that.id != null) {
            return false;
        }
        return !(this.message != null ? !this.message.equals(that.message) : that.message != null);
    }

    @Override
    public int hashCode() {
        int result2 = super.hashCode();
        result2 = 31 * result2 + (this.type != null ? this.type.hashCode() : 0);
        result2 = 31 * result2 + (this.id != null ? this.id.hashCode() : 0);
        result2 = 31 * result2 + (this.message != null ? this.message.hashCode() : 0);
        return result2;
    }

    public static enum Format {
        XML,
        FULL;

    }
}

