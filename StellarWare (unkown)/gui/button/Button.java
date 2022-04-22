package stellar.skid.gui.button;

import stellar.skid.gui.Element;
import stellar.skid.gui.label.Label;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * @author xDelsy
 */
public interface Button extends Element {

    void click(int mouseKey);

    @Nullable
    Label getName();

    void setName(@Nullable Label label);

}
