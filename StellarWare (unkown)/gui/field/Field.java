package stellar.skid.gui.field;

import stellar.skid.gui.Element;
import stellar.skid.gui.label.Label;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * @author xDelsy
 */
public interface Field extends Element {

    void setHint(@Nullable Label label);

    @Nullable Label getLabel();

}
