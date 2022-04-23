/*
 * Decompiled with CFR 0.152.
 */
package org.apache.logging.log4j.core.pattern;

import java.util.Map;
import java.util.TreeSet;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.message.MapMessage;

@Plugin(name="MapPatternConverter", category="Converter")
@ConverterKeys(value={"K", "map", "MAP"})
public final class MapPatternConverter
extends LogEventPatternConverter {
    private final String key;

    private MapPatternConverter(String[] options) {
        super(options != null && options.length > 0 ? "MAP{" + options[0] + "}" : "MAP", "map");
        this.key = options != null && options.length > 0 ? options[0] : null;
    }

    public static MapPatternConverter newInstance(String[] options) {
        return new MapPatternConverter(options);
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        if (!(event.getMessage() instanceof MapMessage)) {
            return;
        }
        MapMessage msg = (MapMessage)event.getMessage();
        Map<String, String> map2 = msg.getData();
        if (this.key == null) {
            if (map2.size() == 0) {
                toAppendTo.append("{}");
                return;
            }
            StringBuilder sb = new StringBuilder("{");
            TreeSet<String> keys2 = new TreeSet<String>(map2.keySet());
            for (String key : keys2) {
                if (sb.length() > 1) {
                    sb.append(", ");
                }
                sb.append(key).append("=").append(map2.get(key));
            }
            sb.append("}");
            toAppendTo.append((CharSequence)sb);
        } else {
            String val = map2.get(this.key);
            if (val != null) {
                toAppendTo.append(val);
            }
        }
    }
}

