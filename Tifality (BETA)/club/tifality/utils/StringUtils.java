/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.utils;

import club.tifality.utils.Wrapper;
import net.minecraft.entity.EntityLivingBase;

public class StringUtils {
    private StringUtils() {
    }

    public static String upperSnakeCaseToPascal(String s) {
        return s.charAt(0) + s.substring(1).toLowerCase();
    }

    public static boolean isTeamMate(EntityLivingBase entity) {
        String entName = entity.getDisplayName().getFormattedText();
        String playerName = Wrapper.getPlayer().getDisplayName().getFormattedText();
        if (entName.length() < 2 || playerName.length() < 2) {
            return false;
        }
        if (!entName.startsWith("\u00a7") || !playerName.startsWith("\u00a7")) {
            return false;
        }
        return entName.charAt(1) == playerName.charAt(1);
    }
}

