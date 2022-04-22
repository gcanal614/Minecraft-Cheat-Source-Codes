package stellar.skid.gui.group2;

import stellar.skid.gui.label.Label;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * @author xDelsy
 */
public interface GroupWithTitle extends Group {

    @Nullable
    Label getTitle();

    void setTitle(@Nullable Label title);

}
