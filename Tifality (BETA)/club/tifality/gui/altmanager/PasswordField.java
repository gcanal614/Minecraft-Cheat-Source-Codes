/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package club.tifality.gui.altmanager;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.MinecraftFontRenderer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u00020\fH\u0016\u00a8\u0006\r"}, d2={"Lclub/tifality/gui/altmanager/PasswordField;", "Lnet/minecraft/client/gui/GuiTextField;", "componentId", "", "fontrendererObj", "Lnet/minecraft/client/gui/MinecraftFontRenderer;", "x", "y", "par5Width", "par6Height", "(ILnet/minecraft/client/gui/MinecraftFontRenderer;IIII)V", "drawTextBox", "", "Client"})
public final class PasswordField
extends GuiTextField {
    /*
     * WARNING - void declaration
     */
    @Override
    public void drawTextBox() {
        String realText = this.getText();
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        String string = this.getText();
        Intrinsics.checkNotNullExpressionValue(string, "text");
        int n2 = ((CharSequence)string).length();
        while (n < n2) {
            void i;
            stringBuilder.append('*');
            ++i;
        }
        this.setText(stringBuilder.toString());
        super.drawTextBox();
        this.setText(realText);
    }

    public PasswordField(int componentId, @NotNull MinecraftFontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height) {
        Intrinsics.checkNotNullParameter(fontrendererObj, "fontrendererObj");
        super(componentId, fontrendererObj, x, y, par5Width, par6Height);
    }
}

