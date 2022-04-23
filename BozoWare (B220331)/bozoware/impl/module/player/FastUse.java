// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Mouse;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemFood;
import bozoware.impl.property.EnumProperty;
import bozoware.impl.event.network.PacketReceiveEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "FastUse", moduleCategory = ModuleCategory.PLAYER)
public class FastUse extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    EventConsumer<PacketReceiveEvent> onPacketReceiveEvent;
    private final EnumProperty<Modes> Mode;
    
    public FastUse() {
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
        //    11: getstatic       bozoware/impl/module/player/FastUse$Modes.Vanilla:Lbozoware/impl/module/player/FastUse$Modes;
        //    14: aload_0         /* this */
        //    15: invokespecial   bozoware/impl/property/EnumProperty.<init>:(Ljava/lang/String;Ljava/lang/Enum;Lbozoware/base/module/Module;)V
        //    18: putfield        bozoware/impl/module/player/FastUse.Mode:Lbozoware/impl/property/EnumProperty;
        //    21: aload_0         /* this */
        //    22: aload_0         /* this */
        //    23: getfield        bozoware/impl/module/player/FastUse.Mode:Lbozoware/impl/property/EnumProperty;
        //    26: invokevirtual   bozoware/impl/property/EnumProperty.getPropertyValue:()Ljava/lang/Enum;
        //    29: checkcast       Lbozoware/impl/module/player/FastUse$Modes;
        //    32: invokevirtual   bozoware/impl/module/player/FastUse$Modes.toString:()Ljava/lang/String;
        //    35: invokevirtual   bozoware/impl/module/player/FastUse.setModuleSuffix:(Ljava/lang/String;)V
        //    38: aload_0         /* this */
        //    39: invokedynamic   BootstrapMethod #0, call:()Lbozoware/base/event/EventConsumer;
        //    44: putfield        bozoware/impl/module/player/FastUse.onPacketReceiveEvent:Lbozoware/base/event/EventConsumer;
        //    47: aload_0         /* this */
        //    48: aload_0         /* this */
        //    49: invokedynamic   BootstrapMethod #1, call:(Lbozoware/impl/module/player/FastUse;)Lbozoware/base/event/EventConsumer;
        //    54: putfield        bozoware/impl/module/player/FastUse.onUpdatePositionEvent:Lbozoware/base/event/EventConsumer;
        //    57: aload_0         /* this */
        //    58: getfield        bozoware/impl/module/player/FastUse.Mode:Lbozoware/impl/property/EnumProperty;
        //    61: aload_0         /* this */
        //    62: invokedynamic   BootstrapMethod #2, run:(Lbozoware/impl/module/player/FastUse;)Ljava/lang/Runnable;
        //    67: putfield        bozoware/impl/property/EnumProperty.onValueChange:Ljava/lang/Runnable;
        //    70: return         
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
    
    private enum Modes
    {
        Vanilla, 
        BlocksMC, 
        NCP, 
        HvH;
    }
}
