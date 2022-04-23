// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import net.minecraft.client.network.NetworkPlayerInfo;
import bozoware.visual.font.MinecraftFontRenderer;
import bozoware.base.util.visual.BloomUtil;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import java.text.DecimalFormat;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.Gui;
import bozoware.base.util.visual.ColorUtil;
import net.minecraft.util.MathHelper;
import bozoware.base.util.visual.BlurUtil;
import bozoware.base.util.visual.RenderUtil;
import bozoware.impl.module.combat.Aura;
import bozoware.base.BozoWare;
import net.minecraft.item.Item;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemStack;
import java.awt.Color;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import bozoware.impl.property.ValueProperty;
import bozoware.impl.property.EnumProperty;
import bozoware.impl.property.BooleanProperty;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.impl.event.player.RenderNametagEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.visual.Render2DEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "TargetHUD", moduleCategory = ModuleCategory.VISUAL)
public class TargetHUD extends Module
{
    @EventListener
    EventConsumer<Render2DEvent> onRender2DEvent;
    @EventListener
    EventConsumer<RenderNametagEvent> onRenderNametagEvent;
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    private final BooleanProperty animBool;
    public EnumProperty<targetHUDModes> targetHUDMode;
    public final ValueProperty<Integer> xPos;
    public final ValueProperty<Integer> yPos;
    boolean isTHUDShowing;
    private double healthBarWidth;
    private double hpPercentage;
    private double hpWidth;
    private double Width;
    public double xPosi;
    public double xPosition;
    private int hp;
    private double armorBarWidth;
    private EntityOtherPlayerMP target;
    private double hudHeight;
    
    public TargetHUD() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   bozoware/base/module/Module.<init>:()V
        //     4: aload_0         /* this */
        //     5: new             Lbozoware/impl/property/BooleanProperty;
        //     8: dup            
        //     9: ldc             "Animate"
        //    11: iconst_1       
        //    12: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //    15: aload_0         /* this */
        //    16: invokespecial   bozoware/impl/property/BooleanProperty.<init>:(Ljava/lang/String;Ljava/lang/Boolean;Lbozoware/base/module/Module;)V
        //    19: putfield        bozoware/impl/module/visual/TargetHUD.animBool:Lbozoware/impl/property/BooleanProperty;
        //    22: aload_0         /* this */
        //    23: new             Lbozoware/impl/property/EnumProperty;
        //    26: dup            
        //    27: ldc             "TargetHUD Mode"
        //    29: getstatic       bozoware/impl/module/visual/TargetHUD$targetHUDModes.Bozo:Lbozoware/impl/module/visual/TargetHUD$targetHUDModes;
        //    32: aload_0         /* this */
        //    33: invokespecial   bozoware/impl/property/EnumProperty.<init>:(Ljava/lang/String;Ljava/lang/Enum;Lbozoware/base/module/Module;)V
        //    36: putfield        bozoware/impl/module/visual/TargetHUD.targetHUDMode:Lbozoware/impl/property/EnumProperty;
        //    39: aload_0         /* this */
        //    40: new             Lbozoware/impl/property/ValueProperty;
        //    43: dup            
        //    44: ldc             "TargetHUD X"
        //    46: bipush          100
        //    48: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    51: iconst_1       
        //    52: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    55: sipush          1920
        //    58: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    61: aload_0         /* this */
        //    62: invokespecial   bozoware/impl/property/ValueProperty.<init>:(Ljava/lang/String;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Lbozoware/base/module/Module;)V
        //    65: putfield        bozoware/impl/module/visual/TargetHUD.xPos:Lbozoware/impl/property/ValueProperty;
        //    68: aload_0         /* this */
        //    69: new             Lbozoware/impl/property/ValueProperty;
        //    72: dup            
        //    73: ldc             "TargetHUD Y"
        //    75: bipush          100
        //    77: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    80: iconst_1       
        //    81: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    84: sipush          1080
        //    87: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    90: aload_0         /* this */
        //    91: invokespecial   bozoware/impl/property/ValueProperty.<init>:(Ljava/lang/String;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Lbozoware/base/module/Module;)V
        //    94: putfield        bozoware/impl/module/visual/TargetHUD.yPos:Lbozoware/impl/property/ValueProperty;
        //    97: aload_0         /* this */
        //    98: aload_0         /* this */
        //    99: invokedynamic   BootstrapMethod #0, run:(Lbozoware/impl/module/visual/TargetHUD;)Ljava/lang/Runnable;
        //   104: putfield        bozoware/impl/module/visual/TargetHUD.onModuleEnabled:Ljava/lang/Runnable;
        //   107: aload_0         /* this */
        //   108: aload_0         /* this */
        //   109: invokedynamic   BootstrapMethod #1, call:(Lbozoware/impl/module/visual/TargetHUD;)Lbozoware/base/event/EventConsumer;
        //   114: putfield        bozoware/impl/module/visual/TargetHUD.onUpdatePositionEvent:Lbozoware/base/event/EventConsumer;
        //   117: aload_0         /* this */
        //   118: invokedynamic   BootstrapMethod #2, call:()Lbozoware/base/event/EventConsumer;
        //   123: putfield        bozoware/impl/module/visual/TargetHUD.onRenderNametagEvent:Lbozoware/base/event/EventConsumer;
        //   126: aload_0         /* this */
        //   127: aload_0         /* this */
        //   128: invokedynamic   BootstrapMethod #3, call:(Lbozoware/impl/module/visual/TargetHUD;)Lbozoware/base/event/EventConsumer;
        //   133: putfield        bozoware/impl/module/visual/TargetHUD.onRender2DEvent:Lbozoware/base/event/EventConsumer;
        //   136: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Could not infer any expression.
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:374)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:344)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createConstructor(AstBuilder.java:713)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:549)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static Color getHealthColor(final float health, final float maxHealth) {
        final float[] fractions = { 0.0f, 0.5f, 1.0f };
        final Color[] colors = { new Color(108, 0, 0), new Color(255, 51, 0), Color.GREEN };
        final float progress = health / maxHealth;
        return blendColors(fractions, colors, progress).brighter();
    }
    
    public static Color blendColors(final float[] fractions, final Color[] colors, final float progress) {
        if (fractions.length == colors.length) {
            final int[] indices = getFractionIndices(fractions, progress);
            final float[] range = { fractions[indices[0]], fractions[indices[1]] };
            final Color[] colorRange = { colors[indices[0]], colors[indices[1]] };
            final float max = range[1] - range[0];
            final float value = progress - range[0];
            final float weight = value / max;
            final Color color = blend(colorRange[0], colorRange[1], 1.0f - weight);
            return color;
        }
        throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
    }
    
    public static int[] getFractionIndices(final float[] fractions, final float progress) {
        final int[] range = new int[2];
        int startPoint;
        for (startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {}
        if (startPoint >= fractions.length) {
            startPoint = fractions.length - 1;
        }
        range[0] = startPoint - 1;
        range[1] = startPoint;
        return range;
    }
    
    public static Color blend(final Color color1, final Color color2, final double ratio) {
        final float r = (float)ratio;
        final float ir = 1.0f - r;
        final float[] rgb1 = color1.getColorComponents(new float[3]);
        final float[] rgb2 = color2.getColorComponents(new float[3]);
        float red = rgb1[0] * r + rgb2[0] * ir;
        float green = rgb1[1] * r + rgb2[1] * ir;
        float blue = rgb1[2] * r + rgb2[2] * ir;
        if (red < 0.0f) {
            red = 0.0f;
        }
        else if (red > 255.0f) {
            red = 255.0f;
        }
        if (green < 0.0f) {
            green = 0.0f;
        }
        else if (green > 255.0f) {
            green = 255.0f;
        }
        if (blue < 0.0f) {
            blue = 0.0f;
        }
        else if (blue > 255.0f) {
            blue = 255.0f;
        }
        Color color3 = null;
        try {
            color3 = new Color(red, green, blue);
        }
        catch (IllegalArgumentException ex) {}
        return color3;
    }
    
    private double getDamage(final ItemStack stack) {
        float damage = 0.0f;
        final Item item = stack.getItem();
        if (stack != null) {
            if (item instanceof ItemTool) {
                final ItemTool tool = (ItemTool)item;
                damage += tool.getDamage();
            }
            if (item instanceof ItemSword) {
                final ItemSword sword = (ItemSword)item;
                damage += sword.getAttackDamage();
            }
            damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25f + EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 0.01f;
            return damage;
        }
        return damage;
    }
    
    public static TargetHUD getInstance() {
        return (TargetHUD) BozoWare.getInstance().getModuleManager().getModuleByClass.apply(TargetHUD.class);
    }
    
    public enum targetHUDModes
    {
        Bozo, 
        Rise, 
        Novoline, 
        Skeet, 
        Crazy;
    }
}
