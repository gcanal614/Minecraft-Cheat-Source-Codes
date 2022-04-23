/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.other;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.player.SendMessageEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.property.impl.EnumProperty;
import org.apache.commons.lang3.RandomUtils;

@ModuleInfo(label="ChatBypass", category=ModuleCategory.OTHER)
public final class ChatBypass
extends Module {
    private static final char[] INVIS_CHARS = new char[0];
    private final EnumProperty<BypassMode> bypassModeProperty = new EnumProperty<BypassMode>("Mode", BypassMode.INVIS);

    @Listener
    public void SendMessageEvent(SendMessageEvent event) {
        if (event.getMessage().startsWith("/")) {
            return;
        }
        switch ((BypassMode)((Object)this.bypassModeProperty.getValue())) {
            case INVIS: {
                StringBuilder stringBuilder = new StringBuilder();
                char[] arrayOfChar = event.getMessage().toCharArray();
                int i = arrayOfChar.length;
                for (int b = 0; b < i; b = (int)((byte)(b + 1))) {
                    char character = arrayOfChar[b];
                    stringBuilder.append(character).append(INVIS_CHARS[RandomUtils.nextInt(0, INVIS_CHARS.length)]);
                }
                event.setMessage(stringBuilder.toString());
            }
        }
    }

    private static enum BypassMode {
        INVIS,
        FONT;

    }
}

