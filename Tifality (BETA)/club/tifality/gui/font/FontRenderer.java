/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.font;

public interface FontRenderer {
    public int drawString(String var1, float var2, float var3, int var4);

    public int drawString(String var1, float var2, float var3, int var4, boolean var5);

    public int drawStringWithShadow(String var1, float var2, float var3, int var4);

    public float getWidth(String var1);

    default public float getHeight(String text) {
        return 11.0f;
    }
}

