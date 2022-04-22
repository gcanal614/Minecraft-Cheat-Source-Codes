 package stellar.skid.gui.label;

import stellar.skid.gui.Element;
import stellar.skid.utils.fonts.api.FontRenderer;
import java.awt.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * @author xDelsy
 */
public interface Label extends Element {

    @Nullable String getText();

    void setText(@Nullable String text);

    int getColor();

    void setColor(int color);

    default void setColor(@NonNull Color color) {
        setColor(color.getRGB());
    }

    @NonNull FontRenderer getFontRenderer();

    void setFontRenderer(@NonNull FontRenderer fontRenderer);

    default int getWidth() {
        return getFontRenderer().stringWidth(getText());
    }

}
