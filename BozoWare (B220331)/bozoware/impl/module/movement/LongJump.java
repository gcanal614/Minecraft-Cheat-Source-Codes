// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.movement;

import net.minecraft.client.Minecraft;
import java.util.Iterator;
import bozoware.impl.module.combat.Aura;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import bozoware.base.util.player.PlayerUtils;
import net.minecraft.entity.Entity;
import bozoware.base.util.player.MovementUtil;
import net.minecraft.client.gui.Gui;
import bozoware.impl.module.visual.HUD;
import bozoware.base.util.visual.RenderUtil;
import bozoware.base.util.visual.Animate.Easing;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.item.ItemBow;
import bozoware.base.util.visual.Animate.Animate;
import bozoware.base.util.misc.TimerUtil;
import bozoware.impl.property.BooleanProperty;
import bozoware.impl.property.ValueProperty;
import bozoware.impl.property.EnumProperty;
import bozoware.impl.event.visual.Render2DEvent;
import bozoware.impl.event.player.PlayerMoveEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "LongJump", moduleCategory = ModuleCategory.MOVEMENT)
public class LongJump extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    @EventListener
    EventConsumer<PlayerMoveEvent> onPlayerMoveEvent;
    @EventListener
    EventConsumer<Render2DEvent> onRender2DEvent;
    private final EnumProperty<Modes> Mode;
    private final ValueProperty<Integer> Speed;
    private final BooleanProperty glideBool;
    private final ValueProperty<Double> glideAmount;
    public TimerUtil timer;
    double speed;
    double startY;
    double moveSpeed;
    double lastDist;
    private int tick;
    private int stage;
    boolean boosted;
    boolean lostBoost;
    boolean bowd;
    boolean prevGround;
    private static final float[] YMotions;
    Animate anim;
    
    public LongJump() {
        this.Mode = new EnumProperty<Modes>("Mode", Modes.Watchdog, this);
        this.Speed = new ValueProperty<Integer>("Speed", 2, 1, 10, this);
        this.glideBool = new BooleanProperty("Hover", false, this);
        this.glideAmount = new ValueProperty<Double>("Hover Amount", 5.0, 5.0, 10.0, this);
        this.timer = new TimerUtil();
        this.anim = new Animate();
        this.glideAmount.setHidden(true);
        this.setModuleSuffix(this.Mode.getPropertyValue().toString());
        this.setModuleBind(47);
        this.glideBool.onValueChange = (() -> {
            this.glideAmount.setHidden(!this.glideBool.getPropertyValue());
            if (!this.glideBool.getPropertyValue()) {
                this.glideAmount.setHidden(true);
            }
            return;
        });
        this.onModuleDisabled = (() -> {});
        final int oldSlot;
        ItemStack block;
        int slot;
        short g;
        this.onModuleEnabled = (() -> {
            this.tick = 0;
            this.stage = 0;
            this.speed = 0.0;
            this.moveSpeed = 0.0;
            this.lostBoost = false;
            this.boosted = false;
            this.timer.reset();
            this.anim.reset();
            if (LongJump.mc.thePlayer.onGround) {
                this.startY = LongJump.mc.thePlayer.posY;
            }
            switch (this.Mode.getPropertyValue()) {
                case Vanilla: {}
                case Watchdog: {}
                case NCP: {
                    oldSlot = LongJump.mc.thePlayer.inventory.currentItem;
                    block = LongJump.mc.thePlayer.getCurrentEquippedItem();
                    slot = LongJump.mc.thePlayer.inventory.currentItem;
                    for (g = 0; g < 9; ++g) {
                        if (LongJump.mc.thePlayer.inventoryContainer.getSlot(g + 36).getHasStack() && LongJump.mc.thePlayer.inventoryContainer.getSlot(g + 36).getStack().getItem() instanceof ItemBow && LongJump.mc.thePlayer.inventoryContainer.getSlot(g + 36).getStack().stackSize != 0 && (block == null || block.getItem() instanceof ItemBow)) {
                            slot = g;
                            block = LongJump.mc.thePlayer.inventoryContainer.getSlot(g + 36).getStack();
                        }
                    }
                    if (slot != -1) {
                        LongJump.mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(slot));
                        LongJump.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(block));
                        break;
                    }
                    else {
                        this.toggleModule();
                        return;
                    }
                    break;
                }
                case SpartanLong: {
                    this.speed = 8.0;
                    break;
                }
                case Verus: {
                    if (LongJump.mc.thePlayer.onGround) {
                        LongJump.mc.thePlayer.motionY = 0.20000000298023224;
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
            return;
        });
        final ScaledResolution sr;
        this.onRender2DEvent = (ev -> {
            sr = ev.getScaledResolution();
            if (this.Mode.getPropertyValue().equals(Modes.Verus)) {
                this.anim.setEase(Easing.LINEAR).setMin(225.0f).setMax(287.0f).setSpeed(350.0f).setReversed(false).update();
                if (LongJump.mc.thePlayer.hurtTime <= 0 && !this.boosted) {
                    RenderUtil.drawBoxOutline(sr.getScaledWidth() - 512.5, sr.getScaledHeight() - 250, sr.getScaledWidth() - 448, sr.getScaledHeight() - 239.5, -16777216, 1.0);
                    Gui.drawRect(sr.getScaledWidth() - 512, sr.getScaledHeight() - 250, this.anim.getValue(), sr.getScaledHeight() - 240, HUD.getInstance().bozoColor);
                }
            }
            if (this.Mode.getPropertyValue().equals(Modes.NCP)) {
                this.anim.setEase(Easing.LINEAR).setMin(225.0f).setMax(287.0f).setSpeed(110.0f).setReversed(false).update();
                if (LongJump.mc.thePlayer.hurtTime <= 0 && !this.boosted) {
                    RenderUtil.drawBoxOutline(sr.getScaledWidth() - 512.5, sr.getScaledHeight() - 250, sr.getScaledWidth() - 448, sr.getScaledHeight() - 239.5, -16777216, 2.0);
                    Gui.drawRect(sr.getScaledWidth() - 512, sr.getScaledHeight() - 250, this.anim.getValue(), sr.getScaledHeight() - 240, HUD.getInstance().bozoColor);
                }
            }
            if (this.Mode.getPropertyValue().equals(Modes.SpartanLong)) {
                this.anim.setEase(Easing.LINEAR).setMin(225.0f).setMax(287.0f).setSpeed(50.0f).setReversed(false).update();
                if (LongJump.mc.thePlayer.hurtTime <= 0 && !this.boosted) {
                    RenderUtil.drawBoxOutline(sr.getScaledWidth() - 512.5, sr.getScaledHeight() - 250, sr.getScaledWidth() - 448, sr.getScaledHeight() - 239.5, -16777216, 2.0);
                    Gui.drawRect(sr.getScaledWidth() - 512, sr.getScaledHeight() - 250, this.anim.getValue(), sr.getScaledHeight() - 240, HUD.getInstance().bozoColor);
                }
            }
            return;
        });
        double motionY;
        EntityPlayerSP thePlayer;
        final double motionY2;
        double difference;
        this.onPlayerMoveEvent = (e -> {
            switch (this.Mode.getPropertyValue()) {
                case Funcraft: {
                    if (LongJump.mc.thePlayer.isMoving() && !this.lostBoost) {
                        switch (this.stage) {
                            case 2: {
                                ++this.stage;
                                motionY = 0.4;
                                thePlayer = LongJump.mc.thePlayer;
                                e.setMotionY(thePlayer.motionY = motionY2);
                                this.moveSpeed *= 2.149;
                                break;
                            }
                            case 3: {
                                ++this.stage;
                                difference = 0.763 * (this.lastDist - MovementUtil.getBaseMoveSpeed());
                                this.moveSpeed = this.lastDist - difference;
                                break;
                            }
                            default: {
                                if (LongJump.mc.theWorld.getCollidingBoundingBoxes(LongJump.mc.thePlayer, LongJump.mc.thePlayer.getEntityBoundingBox().offset(0.0, LongJump.mc.thePlayer.motionY, 0.0)).size() > 0 || LongJump.mc.thePlayer.isCollidedVertically) {
                                    this.stage = 1;
                                }
                                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                                break;
                            }
                        }
                        this.moveSpeed = 5.0 * MovementUtil.getBaseMoveSpeed() - 0.01;
                        MovementUtil.setSpeed(e, this.moveSpeed = Math.max(this.moveSpeed, MovementUtil.getBaseMoveSpeed()));
                        ++this.stage;
                        break;
                    }
                    else if (LongJump.mc.thePlayer.isMoving() && this.lostBoost) {
                        MovementUtil.setSpeed(e, MovementUtil.getBaseMoveSpeed());
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
            return;
        });
        final double x;
        final double y;
        final double z;
        boolean ascending;
        boolean dropping;
        Thread thread;
        EntityPlayerSP thePlayer2;
        EntityPlayerSP thePlayer3;
        this.onUpdatePositionEvent = (e -> {
            x = LongJump.mc.thePlayer.posX;
            y = LongJump.mc.thePlayer.posY;
            z = LongJump.mc.thePlayer.posZ;
            this.lastDist = MovementUtil.lastDist();
            switch (this.Mode.getPropertyValue()) {
                case Watchdog: {
                    ascending = false;
                    dropping = false;
                    if (LongJump.mc.thePlayer.onGround) {
                        LongJump.mc.thePlayer.motionY = 0.33000001311302185;
                        ascending = true;
                    }
                    if (ascending) {
                        LongJump.mc.thePlayer.motionY = 0.33000001311302185;
                        if (LongJump.mc.thePlayer.posY - this.startY >= 3.0) {
                            dropping = true;
                        }
                    }
                    if (dropping) {
                        LongJump.mc.thePlayer.motionY = -0.41999998688697815;
                    }
                    if (dropping && LongJump.mc.thePlayer.onGround) {
                        this.toggleModule();
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
                case Verus: {
                    if (LongJump.mc.thePlayer.onGround && !this.boosted) {
                        PlayerUtils.damageVerusAdvanced();
                        this.boosted = true;
                    }
                    if (LongJump.mc.thePlayer.hurtTime > 0) {
                        LongJump.mc.thePlayer.movementInput.moveForward = 1.0f;
                        if (this.tick < LongJump.YMotions.length) {
                            LongJump.mc.thePlayer.motionY = LongJump.YMotions[this.tick++];
                        }
                        MovementUtil.setSpeed(0.5);
                    }
                    else if (LongJump.mc.thePlayer.fallDistance > 0.0f && this.boosted && !LongJump.mc.thePlayer.isCollidedHorizontally) {
                        LongJump.mc.thePlayer.motionY = -0.07840000152587834;
                        this.toggleModule();
                    }
                    if (LongJump.mc.thePlayer.isCollidedHorizontally) {
                        LongJump.mc.thePlayer.motionY = 0.30000001192092896;
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
                case Funcraft: {
                    LongJump.mc.thePlayer.jumpMovementFactor = 0.0f;
                    if (!LongJump.mc.thePlayer.isMoving() || LongJump.mc.thePlayer.isCollidedHorizontally) {
                        this.lostBoost = true;
                    }
                    if (this.stage > 0 || this.lostBoost) {}
                    if (this.stage < 2 || !this.lostBoost) {}
                    if (this.stage > 6 && LongJump.mc.thePlayer.onGround) {
                        this.toggleModule();
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
                case SpartanLong: {
                    PlayerUtils.bowSelf();
                    LongJump.mc.thePlayer.setSprinting(true);
                    LongJump.mc.gameSettings.keyBindForward.pressed = false;
                    LongJump.mc.gameSettings.keyBindLeft.pressed = false;
                    LongJump.mc.gameSettings.keyBindBack.pressed = false;
                    LongJump.mc.gameSettings.keyBindRight.pressed = false;
                    if (LongJump.mc.thePlayer.hurtTime < 0) {
                        e.setPitch(LongJump.mc.thePlayer.rotationPitchHead = -90.0f);
                    }
                    if (LongJump.mc.thePlayer.hurtTime > 0) {
                        thread = new Thread() {
                            @Override
                            public void run() {
                                if (LongJump.this.tick < LongJump.YMotions.length) {
                                    LongJump.mc.thePlayer.motionY = 0.30000001192092896;
                                    LongJump.this.tick++;
                                }
                            }
                        };
                        thread.start();
                        LongJump.mc.thePlayer.movementInput.moveForward = 1.0f;
                        MovementUtil.setMoveSpeed(MovementUtil.getBaseMoveSpeed() * this.speed - Math.random() / 15.0);
                    }
                    if (LongJump.mc.thePlayer.fallDistance > 0.0f) {
                        this.toggleModule();
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
            if (LongJump.mc.thePlayer.hurtTime > 0 && LongJump.mc.thePlayer.onGround) {
                LongJump.mc.thePlayer.motionY = 0.41999998688697815;
            }
            if (this.glideBool.getPropertyValue()) {
                if (this.Mode.getPropertyValue().equals(Modes.SpartanLong)) {
                    if (LongJump.mc.thePlayer.fallDistance > 1.0f) {
                        thePlayer2 = LongJump.mc.thePlayer;
                        thePlayer2.motionY += this.glideAmount.getPropertyValue() / 100.0;
                        if (this.timer.hasReached(500L)) {
                            this.toggleModule();
                        }
                    }
                }
                else if (LongJump.mc.thePlayer.fallDistance > 1.0f && this.boosted) {
                    thePlayer3 = LongJump.mc.thePlayer;
                    thePlayer3.motionY += this.glideAmount.getPropertyValue() / 100.0;
                    if (this.timer.hasReached(500L)) {
                        this.toggleModule();
                    }
                }
            }
            return;
        });
        this.Mode.onValueChange = (() -> this.setModuleSuffix(this.Mode.getPropertyValue().name()));
    }
    
    public static EntityLivingBase getClosestEntity(final float range) {
        EntityLivingBase closestEntity = null;
        float mindistance = range;
        for (final Object o : LongJump.mc.theWorld.loadedEntityList) {
            if (isNotItem(o) && !(o instanceof EntityPlayerSP)) {
                final EntityLivingBase en = (EntityLivingBase)o;
                if (!Aura.getInstance().isValid(en)) {
                    continue;
                }
                if (LongJump.mc.thePlayer.getDistanceToEntity(en) >= mindistance) {
                    continue;
                }
                mindistance = LongJump.mc.thePlayer.getDistanceToEntity(en);
                closestEntity = en;
            }
        }
        return closestEntity;
    }
    
    public static boolean isNotItem(final Object o) {
        return o instanceof EntityLivingBase;
    }
    
    static {
        YMotions = new float[] { 0.4f, 0.4f, 0.38f, 0.365f, 0.33f, 0.26f, 0.25f, 0.23f, 0.21f, 0.12f, 0.1f, -0.01f, -0.05f, -0.09f, -0.12f, -0.15f, -0.18f, -0.21f, -0.24f, -0.27f, -0.3f };
    }
    
    private enum Modes
    {
        Watchdog, 
        NCP, 
        Funcraft, 
        SpartanLong, 
        Verus, 
        Vanilla;
    }
}
