// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.combat;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import java.util.List;
import bozoware.impl.property.EnumProperty;
import bozoware.impl.event.world.onWorldLoadEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "AntiBot", moduleCategory = ModuleCategory.COMBAT)
public class AntiBot extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    @EventListener
    EventConsumer<onWorldLoadEvent> onLoadWorldEvent;
    private final EnumProperty<Modes> Mode;
    public static List<Integer> botList;
    
    public AntiBot() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   bozoware/base/module/Module.<init>:()V
        //     4: aload_0         /* this */
        //     5: new             Lbozoware/impl/property/EnumProperty;
        //     8: dup            
        //     9: ldc             "Mode"
        //    11: getstatic       bozoware/impl/module/combat/AntiBot$Modes.Hypixel:Lbozoware/impl/module/combat/AntiBot$Modes;
        //    14: aload_0         /* this */
        //    15: invokespecial   bozoware/impl/property/EnumProperty.<init>:(Ljava/lang/String;Ljava/lang/Enum;Lbozoware/base/module/Module;)V
        //    18: putfield        bozoware/impl/module/combat/AntiBot.Mode:Lbozoware/impl/property/EnumProperty;
        //    21: aload_0         /* this */
        //    22: aload_0         /* this */
        //    23: getfield        bozoware/impl/module/combat/AntiBot.Mode:Lbozoware/impl/property/EnumProperty;
        //    26: invokevirtual   bozoware/impl/property/EnumProperty.getPropertyValue:()Ljava/lang/Enum;
        //    29: checkcast       Lbozoware/impl/module/combat/AntiBot$Modes;
        //    32: invokevirtual   bozoware/impl/module/combat/AntiBot$Modes.toString:()Ljava/lang/String;
        //    35: invokevirtual   bozoware/impl/module/combat/AntiBot.setModuleSuffix:(Ljava/lang/String;)V
        //    38: aload_0         /* this */
        //    39: invokedynamic   BootstrapMethod #0, run:()Ljava/lang/Runnable;
        //    44: putfield        bozoware/impl/module/combat/AntiBot.onModuleEnabled:Ljava/lang/Runnable;
        //    47: aload_0         /* this */
        //    48: invokedynamic   BootstrapMethod #1, call:()Lbozoware/base/event/EventConsumer;
        //    53: putfield        bozoware/impl/module/combat/AntiBot.onLoadWorldEvent:Lbozoware/base/event/EventConsumer;
        //    56: aload_0         /* this */
        //    57: aload_0         /* this */
        //    58: invokedynamic   BootstrapMethod #2, call:(Lbozoware/impl/module/combat/AntiBot;)Lbozoware/base/event/EventConsumer;
        //    63: putfield        bozoware/impl/module/combat/AntiBot.onUpdatePositionEvent:Lbozoware/base/event/EventConsumer;
        //    66: aload_0         /* this */
        //    67: getfield        bozoware/impl/module/combat/AntiBot.Mode:Lbozoware/impl/property/EnumProperty;
        //    70: aload_0         /* this */
        //    71: invokedynamic   BootstrapMethod #3, run:(Lbozoware/impl/module/combat/AntiBot;)Ljava/lang/Runnable;
        //    76: putfield        bozoware/impl/property/EnumProperty.onValueChange:Ljava/lang/Runnable;
        //    79: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.generateNameForVariable(NameVariables.java:264)
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.assignNamesToVariables(NameVariables.java:198)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:276)
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
    
    static {
        AntiBot.botList = new ArrayList<Integer>();
    }
    
    private enum Modes
    {
        Hypixel, 
        Funcraft;
    }
}
