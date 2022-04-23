/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.font;

import club.tifality.gui.font.FontRenderer;
import club.tifality.utils.render.OGLUtils;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Random;
import net.minecraft.util.MathHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class TrueTypeFontRenderer
implements FontRenderer {
    private static final Random RANDOM = new Random();
    private final Font font;
    private final CharacterData[] charData = new CharacterData[256];
    private final int[] colorCodes = new int[32];
    private final int margin;
    private final boolean antiAlias;
    private final boolean fracMetrics;

    public TrueTypeFontRenderer(Font font, boolean antiAlias, boolean fracMetrics) {
        this.generateColors();
        this.font = font;
        this.margin = 4;
        this.antiAlias = antiAlias;
        this.fracMetrics = fracMetrics;
    }

    @Override
    public int drawString(String text, float x, float y, int color, boolean shadow) {
        double s = 0.5;
        GL11.glTranslated(s, s, 0.0);
        this.renderString(text, x, y, color, shadow);
        GL11.glTranslated(-s, -s, 0.0);
        this.renderString(text, x, y, color, false);
        return 0;
    }

    @Override
    public int drawString(String text, float x, float y, int color) {
        this.renderString(text, x, y, color, false);
        return 0;
    }

    public void drawCenteredString(String s, int x, int y, int colour) {
        x = (int)((float)x - this.getWidth(s) / 2.0f);
        this.drawStringWithShadow(s, x, y, colour);
    }

    @Override
    public int drawStringWithShadow(String text, float x, float y, int color) {
        double s = 0.5;
        GL11.glTranslated(s, s, 0.0);
        this.renderString(text, x, y, color, true);
        GL11.glTranslated(-s, -s, 0.0);
        this.renderString(text, x, y, color, false);
        return 0;
    }

    @Override
    public float getWidth(String text) {
        if (text != null && text.length() != 0) {
            float width = 0.0f;
            CharacterData[] characterData = this.charData;
            int length = text.length();
            for (int i = 0; i < length; ++i) {
                char character = text.charAt(i);
                if (character == '\u00a7' || (i > 0 ? (int)text.charAt(i - 1) : 46) == 167 || !this.isValid(character)) continue;
                CharacterData charData = characterData[character];
                width += (charData.width - (float)(2 * this.margin)) / 2.0f;
            }
            return width;
        }
        return 0.0f;
    }

    @Override
    public float getHeight(String text) {
        float height = 0.0f;
        CharacterData[] characterData = this.charData;
        int length = text.length();
        for (int i = 0; i < length; ++i) {
            char character = text.charAt(i);
            if ((i > 0 ? (int)text.charAt(i - 1) : 46) == 167 || character == '\u00a7' || !this.isValid(character)) continue;
            CharacterData charData = characterData[character];
            height = Math.max(height, charData.height);
        }
        return (height - (float)this.margin) / 2.0f;
    }

    public void generateTextures() {
        for (int i = 0; i < 256; ++i) {
            char c = (char)i;
            if (!this.isValid(c)) continue;
            this.setup(c);
        }
    }

    private void setup(char character) {
        BufferedImage utilityImage = new BufferedImage(1, 1, 2);
        Graphics2D utilityGraphics = (Graphics2D)utilityImage.getGraphics();
        utilityGraphics.setFont(this.font);
        FontMetrics fontMetrics = utilityGraphics.getFontMetrics();
        Rectangle2D characterBounds = fontMetrics.getStringBounds(String.valueOf(character), utilityGraphics);
        float width = (float)characterBounds.getWidth() + 8.0f;
        float height = (float)characterBounds.getHeight();
        BufferedImage characterImage = new BufferedImage(MathHelper.ceiling_double_int(width), MathHelper.ceiling_double_int(height), 2);
        Graphics2D graphics = (Graphics2D)characterImage.getGraphics();
        graphics.setFont(this.font);
        graphics.setColor(new Color(255, 255, 255, 0));
        graphics.fillRect(0, 0, characterImage.getWidth(), characterImage.getHeight());
        graphics.setColor(Color.WHITE);
        if (this.antiAlias) {
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, this.fracMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        }
        graphics.drawString(String.valueOf(character), this.margin, fontMetrics.getAscent());
        int textureId = GL11.glGenTextures();
        this.createTexture(textureId, characterImage);
        this.charData[character] = new CharacterData(characterImage.getWidth(), characterImage.getHeight(), textureId);
    }

    private void createTexture(int textureId, BufferedImage image) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte)(pixel >> 16 & 0xFF));
                buffer.put((byte)(pixel >> 8 & 0xFF));
                buffer.put((byte)(pixel & 0xFF));
                buffer.put((byte)(pixel >> 24 & 0xFF));
            }
        }
        buffer.flip();
        GL11.glBindTexture(3553, textureId);
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        GL11.glTexImage2D(3553, 0, 6408, image.getWidth(), image.getHeight(), 0, 6408, 5121, buffer);
    }

    private void renderString(CharSequence text, float x, float y, int color, boolean shadow) {
        if (text == null || text.length() == 0) {
            return;
        }
        GL11.glPushMatrix();
        if ((color & 0xFC000000) == 0) {
            color |= 0xFF000000;
        }
        if (color == 0x20FFFFFF) {
            color = -5263441;
        }
        GL11.glScaled(0.5, 0.5, 1.0);
        x -= (float)(this.margin / 2);
        y -= 2.0f;
        x *= 2.0f;
        y *= 2.0f;
        CharacterData[] characterData = this.charData;
        boolean underlined = false;
        boolean strikethrough = false;
        boolean obfuscated = false;
        int length = text.length();
        float multiplier = shadow ? 4.0f : 1.0f;
        float a = (float)(color >> 24 & 0xFF) / 255.0f;
        float r = (float)(color >> 16 & 0xFF) / 255.0f;
        float g = (float)(color >> 8 & 0xFF) / 255.0f;
        float b = (float)(color & 0xFF) / 255.0f;
        OGLUtils.enableBlending();
        GL11.glColor4f(r / multiplier, g / multiplier, b / multiplier, a);
        for (int i = 0; i < length; ++i) {
            char character = text.charAt(i);
            int previous = i > 0 ? (int)text.charAt(i - 1) : 46;
            int n = previous;
            if (previous == 167) continue;
            if (character == '\u00a7') {
                int index = "0123456789ABCDEFKLMNOR".indexOf(text.charAt(i + 1));
                if (index < 16) {
                    obfuscated = false;
                    strikethrough = false;
                    underlined = false;
                    characterData = this.charData;
                    if (index < 0) {
                        index = 15;
                    }
                    if (shadow) {
                        index += 16;
                    }
                    int textColor = this.colorCodes[index];
                    GL11.glColor4f((float)(textColor >> 16) / 255.0f, (float)(textColor >> 8 & 0xFF) / 255.0f, (float)(textColor & 0xFF) / 255.0f, a);
                    continue;
                }
                if (index == 16) {
                    obfuscated = true;
                    continue;
                }
                if (index == 18) {
                    strikethrough = true;
                    continue;
                }
                if (index == 19) {
                    underlined = true;
                    continue;
                }
                obfuscated = false;
                strikethrough = false;
                underlined = false;
                characterData = this.charData;
                GL11.glColor4d(1.0f / multiplier, 1.0f / multiplier, 1.0f / multiplier, a);
                continue;
            }
            if (!this.isValid(character)) continue;
            if (obfuscated) {
                character = (char)(character + RANDOM.nextInt(Math.max(0, 256 - character)));
            }
            this.drawChar(characterData[character], x, y);
            CharacterData charData = characterData[character];
            if (strikethrough) {
                this.drawLine(0.0f, charData.height / 2.0f, charData.width, charData.height / 2.0f, 3.0f);
            }
            if (underlined) {
                this.drawLine(0.0f, charData.height - 15.0f, charData.width, charData.height - 15.0f, 3.0f);
            }
            x += charData.width - (float)(2 * this.margin);
        }
        OGLUtils.disableBlending();
        GL11.glPopMatrix();
    }

    private boolean isValid(char c) {
        return c > '\n' && c < '\u0100' && c != '\u007f';
    }

    private void drawChar(CharacterData characterData, float x, float y) {
        characterData.bind();
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2d(x, y);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2d(x, y + characterData.height);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2d(x + characterData.width, y + characterData.height);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2d(x + characterData.width, y);
        GL11.glEnd();
    }

    private void drawLine(float x, float y, float x2, float y2, float width) {
        GL11.glDisable(3553);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
        GL11.glEnable(3553);
    }

    private void generateColors() {
        for (int i = 0; i < 32; ++i) {
            int thingy = (i >> 3 & 1) * 85;
            int red = (i >> 2 & 1) * 170 + thingy;
            int green = (i >> 1 & 1) * 170 + thingy;
            int blue = (i & 1) * 170 + thingy;
            if (i == 6) {
                red += 85;
            }
            if (i >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }
            this.colorCodes[i] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
        }
    }

    private static class CharacterData {
        private final int textureId;
        public float width;
        public float height;

        public CharacterData(float width, float height, int textureId) {
            this.width = width;
            this.height = height;
            this.textureId = textureId;
        }

        public void bind() {
            GL11.glBindTexture(3553, this.textureId);
        }
    }
}

