/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  store.intent.intentguard.annotation.Native
 */
import java.util.Arrays;
import net.minecraft.client.main.Main;
import store.intent.intentguard.annotation.Native;

@Native
public class Start {
    public static void main(String[] args) {
        Main.main(Start.concat(new String[]{"--version", "SRT", "--accessToken", "0", "--assetsDir", "assets", "--assetIndex", "1.8", "--userProperties", "{}"}, args));
    }

    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}

