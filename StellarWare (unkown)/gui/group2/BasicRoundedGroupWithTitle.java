package stellar.skid.gui.group2;

import stellar.skid.gui.label.Label;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * @author xDelsy
 */
public final class BasicRoundedGroupWithTitle extends AbstractRoundedGroupWithTitle {

    public BasicRoundedGroupWithTitle(@Nullable Label title, int radius, int x, int y, int width, int height) {
        super(title, radius, 0x88FFFFFF, x, y, width, height);
    }

}
