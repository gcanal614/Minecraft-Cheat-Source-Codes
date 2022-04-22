package stellar.skid.gui.label;

import stellar.skid.utils.fonts.api.FontRenderer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * @author xDelsy
 */
public final class BasicLabel extends AbstractLabel {

    public BasicLabel(@Nullable String text, int color, @NonNull FontRenderer fontRenderer, int x, int y) {
        super(text, color, fontRenderer, x, y);
    }

    public BasicLabel(@Nullable String text, int color, @NonNull FontRenderer fontRenderer) {
        super(text, color, fontRenderer);
    }

}
