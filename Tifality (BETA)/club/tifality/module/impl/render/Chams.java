/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package club.tifality.module.impl.render;

import club.tifality.Tifality;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.module.impl.render.hud.Hud;
import club.tifality.property.Property;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.property.impl.EnumProperty;
import club.tifality.utils.render.Colors;
import club.tifality.utils.render.OGLUtils;
import club.tifality.utils.render.RenderingUtils;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ModuleInfo(label="Chams", category=ModuleCategory.RENDER)
@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001:\u0001\u001eB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u001a\u001a\u00020\u001bJ\u0006\u0010\u001c\u001a\u00020\u001bJ\u0006\u0010\u001d\u001a\u00020\u0005R\u001c\u0010\u0003\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001f\u0010\u0007\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\b0\b0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001f\u0010\u000b\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\r0\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001f\u0010\u0010\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\nR\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001f\u0010\u0016\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\b0\b0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\nR\u001f\u0010\u0018\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\n\u00a8\u0006\u001f"}, d2={"Lclub/tifality/module/impl/render/Chams;", "Lclub/tifality/module/Module;", "()V", "handValue", "Lclub/tifality/property/Property;", "", "kotlin.jvm.PlatformType", "invisibleColorValue", "", "getInvisibleColorValue", "()Lclub/tifality/property/Property;", "modeValue", "Lclub/tifality/property/impl/EnumProperty;", "Lclub/tifality/module/impl/render/Chams$Mode;", "getModeValue", "()Lclub/tifality/property/impl/EnumProperty;", "rainbow", "getRainbow", "rainbowAlphaValue", "Lclub/tifality/property/impl/DoubleProperty;", "getRainbowAlphaValue", "()Lclub/tifality/property/impl/DoubleProperty;", "visibleColorValue", "getVisibleColorValue", "wallHake", "getWallHake", "postHandRender", "", "preHandRender", "shouldRenderHand", "Mode", "Client"})
public final class Chams
extends Module {
    @NotNull
    private final Property<Boolean> wallHake = new Property<Boolean>("Through Wall", true);
    @NotNull
    private final EnumProperty<Mode> modeValue = new EnumProperty<Enum>("Mode", Mode.COLOR);
    @NotNull
    private final DoubleProperty rainbowAlphaValue = new DoubleProperty("Rainbow Alpha", 255.0, new Supplier<Boolean>(this){
        final /* synthetic */ Chams this$0;

        public final Boolean get() {
            return this.this$0.getRainbow().get();
        }
        {
            this.this$0 = chams;
        }
    }, 0.0, 255.0, 1.0);
    @NotNull
    private final Property<Integer> visibleColorValue = new Property<Integer>("Visible Color", Colors.WHITE, new Supplier<Boolean>(this){
        final /* synthetic */ Chams this$0;

        public final Boolean get() {
            return (Mode)((Object)this.this$0.getModeValue().get()) != Mode.NORMAL;
        }
        {
            this.this$0 = chams;
        }
    });
    @NotNull
    private final Property<Integer> invisibleColorValue = new Property<Integer>("Invisible Color", Colors.BLUE, new Supplier<Boolean>(this){
        final /* synthetic */ Chams this$0;

        public final Boolean get() {
            return (Mode)((Object)this.this$0.getModeValue().get()) != Mode.NORMAL;
        }
        {
            this.this$0 = chams;
        }
    });
    @NotNull
    private final Property<Boolean> rainbow = new Property<Boolean>("Rainbow", false, new Supplier<Boolean>(this){
        final /* synthetic */ Chams this$0;

        public final Boolean get() {
            return (Mode)((Object)this.this$0.getModeValue().get()) != Mode.NORMAL;
        }
        {
            this.this$0 = chams;
        }
    });
    private final Property<Boolean> handValue = new Property<Boolean>("Hand", true, new Supplier<Boolean>(this){
        final /* synthetic */ Chams this$0;

        public final Boolean get() {
            return (Mode)((Object)this.this$0.getModeValue().get()) != Mode.NORMAL;
        }
        {
            this.this$0 = chams;
        }
    });

    @NotNull
    public final Property<Boolean> getWallHake() {
        return this.wallHake;
    }

    @NotNull
    public final EnumProperty<Mode> getModeValue() {
        return this.modeValue;
    }

    @NotNull
    public final DoubleProperty getRainbowAlphaValue() {
        return this.rainbowAlphaValue;
    }

    @NotNull
    public final Property<Integer> getVisibleColorValue() {
        return this.visibleColorValue;
    }

    @NotNull
    public final Property<Integer> getInvisibleColorValue() {
        return this.invisibleColorValue;
    }

    @NotNull
    public final Property<Boolean> getRainbow() {
        return this.rainbow;
    }

    public final void preHandRender() {
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(2896);
        Tifality tifality = Tifality.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(tifality, "Tifality.INSTANCE");
        Hud hud = tifality.getModuleManager().getModule(Hud.class);
        Boolean bl = this.rainbow.get();
        Intrinsics.checkNotNullExpressionValue(bl, "rainbow.get()");
        if (bl.booleanValue()) {
            DoubleProperty doubleProperty = hud.rainbowSpeed;
            Intrinsics.checkNotNullExpressionValue(doubleProperty, "hud.rainbowSpeed");
            int n = (int)((Number)doubleProperty.getValue()).doubleValue();
            DoubleProperty doubleProperty2 = hud.rainbowWidth;
            Intrinsics.checkNotNullExpressionValue(doubleProperty2, "hud.rainbowWidth");
            int rgb = RenderingUtils.getRainbowFromEntity(n, (int)((Number)doubleProperty2.getValue()).doubleValue(), (int)System.currentTimeMillis() / 15, false, (float)((Number)this.rainbowAlphaValue.get()).doubleValue());
            RenderingUtils.color(rgb);
        } else {
            Integer n = this.visibleColorValue.get();
            Intrinsics.checkNotNullExpressionValue(n, "visibleColorValue.get()");
            OGLUtils.color(((Number)n).intValue());
        }
    }

    public final void postHandRender() {
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }

    public final boolean shouldRenderHand() {
        Boolean bl = this.handValue.get();
        Intrinsics.checkNotNullExpressionValue(bl, "handValue.get()");
        return bl != false && this.isEnabled() && (Mode)((Object)this.modeValue.get()) != Mode.NORMAL;
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lclub/tifality/module/impl/render/Chams$Mode;", "", "(Ljava/lang/String;I)V", "COLOR", "NORMAL", "CSGO", "Client"})
    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode COLOR;
        public static final /* enum */ Mode NORMAL;
        public static final /* enum */ Mode CSGO;
        private static final /* synthetic */ Mode[] $VALUES;

        static {
            Mode[] modeArray = new Mode[3];
            Mode[] modeArray2 = modeArray;
            modeArray[0] = COLOR = new Mode();
            modeArray[1] = NORMAL = new Mode();
            modeArray[2] = CSGO = new Mode();
            $VALUES = modeArray;
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }
    }
}

