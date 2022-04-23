// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.combat;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import bozoware.base.util.player.PlayerUtils;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import java.util.Iterator;
import net.minecraft.world.World;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0APacketAnimation;
import bozoware.base.util.visual.RenderUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import bozoware.base.util.Wrapper;
import bozoware.impl.module.player.BlockFly;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import java.util.ArrayList;
import bozoware.base.BozoWare;
import net.minecraft.entity.EntityLivingBase;
import java.util.List;
import bozoware.base.util.misc.TimerUtil;
import bozoware.impl.property.MultiSelectEnumProperty;
import bozoware.impl.property.BooleanProperty;
import bozoware.impl.property.ValueProperty;
import bozoware.impl.property.EnumProperty;
import bozoware.impl.event.network.PacketReceiveEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Kill Aura", moduleCategory = ModuleCategory.COMBAT)
public class Aura extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    @EventListener
    EventConsumer<PacketReceiveEvent> onPacketReceiveEvent;
    private final EnumProperty<auraModes> auraMode;
    private final EnumProperty<sortModes> sortMode;
    private final ValueProperty<Integer> APS;
    public final ValueProperty<Double> Range;
    public final ValueProperty<Double> ABRange;
    private final BooleanProperty blockBool;
    private final EnumProperty<blockModes> blockMode;
    private final MultiSelectEnumProperty<Targets> targeting;
    private final BooleanProperty noSwing;
    private final BooleanProperty flagCheckBool;
    private final BooleanProperty rotations;
    private final ValueProperty<Double> smoothFactor;
    private final BooleanProperty particleBool;
    private final EnumProperty<particleModes> particleMode;
    public TimerUtil timer;
    public List<EntityLivingBase> targetList;
    public static EntityLivingBase target;
    public static boolean isBlocking;
    private double yawAnimation;
    private double yaw;
    private double pitch;
    
    public static Aura getInstance() {
        return BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Aura.class);
    }
    
    public Aura() {
        this.auraMode = new EnumProperty<auraModes>("Aura Mode", auraModes.Single, this);
        this.sortMode = new EnumProperty<sortModes>("Sort Mode", sortModes.Health, this);
        this.APS = new ValueProperty<Integer>("APS", 15, 1, 25, this);
        this.Range = new ValueProperty<Double>("Attack Range", 4.2, 1.0, 6.0, this);
        this.ABRange = new ValueProperty<Double>("Block Range", 4.2, 1.0, 12.0, this);
        this.blockBool = new BooleanProperty("AutoBlock", true, this);
        this.blockMode = new EnumProperty<blockModes>("Block Mode", blockModes.NCP, this);
        this.targeting = new MultiSelectEnumProperty<Targets>("Targets", this, new Targets[] { Targets.Players });
        this.noSwing = new BooleanProperty("NoSwing", false, this);
        this.flagCheckBool = new BooleanProperty("Flag Check", false, this);
        this.rotations = new BooleanProperty("Rotations", true, this);
        this.smoothFactor = new ValueProperty<Double>("Smoothing Speed", 180.0, 1.0, 180.0, this);
        this.particleBool = new BooleanProperty("Particle", false, this);
        this.particleMode = new EnumProperty<particleModes>("Particle Mode", particleModes.Crit, this);
        this.timer = new TimerUtil();
        this.targetList = new ArrayList<EntityLivingBase>();
        this.particleMode.setHidden(true);
        this.blockMode.setHidden(!this.blockBool.getPropertyValue());
        this.particleBool.onValueChange = (() -> {
            this.particleMode.setHidden(!this.particleBool.getPropertyValue());
            if (!this.particleBool.getPropertyValue()) {
                this.particleMode.setHidden(true);
            }
            return;
        });
        this.setModuleBind(49);
        this.setModuleSuffix(this.sortMode.getPropertyValue().toString());
        this.onModuleDisabled = (() -> {
            this.targetList.clear();
            Aura.target = null;
            return;
        });
        this.onModuleEnabled = (() -> this.targetList.clear());
        this.onPacketReceiveEvent = (e -> {
            if (e.getPacket() instanceof S08PacketPlayerPosLook && this.flagCheckBool.getPropertyValue() && Aura.mc.thePlayer != null && Aura.mc.theWorld != null) {
                BozoWare.getInstance().chat("Disabled Aura because you flagged/got teleported!");
                this.toggleModule();
            }
            return;
        });
        float[] f;
        double smoothSpeed;
        final Iterator<EntityLivingBase> iterator;
        EntityLivingBase target;
        this.onUpdatePositionEvent = (event -> {
            if (BozoWare.getInstance().getModuleManager().getModuleByClass.apply(BlockFly.class).isModuleToggled()) {
                Aura.target = null;
            }
            else {
                Aura.target = this.getTarget();
                if (this.rotations.getPropertyValue() && Aura.target != null) {
                    f = Wrapper.getEntityRotations(Aura.mc.thePlayer, Aura.target);
                    smoothSpeed = this.smoothFactor.getPropertyValue() / 100.0;
                    this.yawAnimation = RenderUtil.animate(f[0], this.yawAnimation, smoothSpeed);
                    Aura.mc.thePlayer.rotationYawHead = (float)this.yawAnimation;
                    Aura.mc.thePlayer.renderYawOffset = (float)this.yawAnimation;
                    Aura.mc.thePlayer.rotationPitchHead = f[1];
                    event.setYaw((float)this.yawAnimation);
                    event.setPitch(f[1]);
                }
                switch (this.auraMode.getPropertyValue()) {
                    case Switch:
                    case Single: {
                        if (Aura.target != null && Math.round(event.getYaw()) == Math.round(Wrapper.getEntityRotations(Aura.mc.thePlayer, Aura.target)[0]) && Aura.target.getDistanceToEntity(Aura.mc.thePlayer) <= this.Range.getPropertyValue() && this.timer.hasReached(1000L / this.APS.getPropertyValue())) {
                            if (this.noSwing.getPropertyValue()) {
                                Aura.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                            }
                            else {
                                Aura.mc.thePlayer.swingItem();
                            }
                            Aura.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(Aura.target, C02PacketUseEntity.Action.ATTACK));
                            this.doParticle();
                            this.timer.reset();
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                }
                if (this.canBlock()) {
                    switch (this.blockMode.getPropertyValue()) {
                        case Interact: {
                            this.targetList.iterator();
                            while (iterator.hasNext()) {
                                target = iterator.next();
                                Aura.mc.playerController.interactWithEntitySendPacket(Aura.mc.thePlayer, target);
                            }
                            Aura.isBlocking = true;
                            break;
                        }
                        case C08: {
                            if (!event.isPre()) {
                                Aura.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(Aura.mc.thePlayer.inventory.getCurrentItem()));
                                Aura.isBlocking = true;
                                break;
                            }
                            else {
                                break;
                            }
                            break;
                        }
                        case SetUse: {
                            Wrapper.getPlayer().setItemInUse(Wrapper.getPlayer().getCurrentEquippedItem(), Wrapper.getPlayer().getCurrentEquippedItem().getMaxItemUseDuration());
                            Aura.isBlocking = true;
                            break;
                        }
                        case NCP: {
                            if (!event.isPre()) {
                                Aura.mc.playerController.sendUseItem(Aura.mc.thePlayer, Aura.mc.theWorld, Aura.mc.thePlayer.inventory.getCurrentItem());
                                Aura.isBlocking = true;
                                break;
                            }
                            else {
                                break;
                            }
                            break;
                        }
                        case Fake: {
                            Aura.isBlocking = true;
                            break;
                        }
                    }
                }
                else {
                    Aura.isBlocking = false;
                }
                this.setModuleSuffix(this.auraMode.getPropertyValue().name());
                this.auraMode.onValueChange = (() -> this.setModuleSuffix(this.auraMode.getPropertyValue().name()));
            }
        });
    }
    
    public boolean canBlock() {
        final ItemStack heldItem = Aura.mc.thePlayer.getHeldItem();
        return Aura.target != null && this.blockBool.getPropertyValue() && Aura.target.getDistanceToEntity(Aura.mc.thePlayer) <= this.ABRange.getPropertyValue() && heldItem != null && heldItem.getItem() instanceof ItemSword;
    }
    
    public void doParticle() {
        if (this.particleBool.getPropertyValue() && Aura.target != null) {
            switch (this.particleMode.getPropertyValue()) {
                case Crit: {
                    Aura.mc.effectRenderer.emitParticleAtEntity(Aura.target, EnumParticleTypes.CRIT);
                    break;
                }
                case Enchant: {
                    Aura.mc.effectRenderer.emitParticleAtEntity(Aura.target, EnumParticleTypes.CRIT_MAGIC);
                    break;
                }
                case Heart: {
                    Aura.mc.effectRenderer.emitParticleAtEntity(Aura.target, EnumParticleTypes.HEART);
                    break;
                }
                case Explosion: {
                    Aura.mc.effectRenderer.emitParticleAtEntity(Aura.target, EnumParticleTypes.EXPLOSION_LARGE);
                    break;
                }
                case Blood: {
                    for (int i = 0; i < 8; ++i) {
                        final World targetWorld = Aura.target.getEntityWorld();
                        final double x = Aura.target.posX;
                        final double y = Aura.target.posY;
                        final double z = Aura.target.posZ;
                        targetWorld.spawnParticle(EnumParticleTypes.BLOCK_CRACK, x + ThreadLocalRandom.current().nextDouble(-0.5, 0.5), y + ThreadLocalRandom.current().nextDouble(-1.0, 1.0), z + ThreadLocalRandom.current().nextDouble(-0.5, 0.5), 23.0, 23.0, 23.0, 152);
                    }
                    break;
                }
                default: {
                    Aura.mc.effectRenderer.emitParticleAtEntity(Aura.target, EnumParticleTypes.CRIT);
                    break;
                }
            }
        }
    }
    
    public EntityLivingBase getTarget() {
        EntityLivingBase currentTarget = null;
        double currentDistance = 0.0;
        for (final Entity entity : Aura.mc.theWorld.loadedEntityList) {
            if (entity != null && entity instanceof EntityLivingBase) {
                final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
                if (!this.isValid(entityLivingBase)) {
                    continue;
                }
                final EntityLivingBase target = (EntityLivingBase)entity;
                if (currentTarget != null && !this.auraMode.getPropertyValue().equals(auraModes.Single)) {
                    switch (this.sortMode.getPropertyValue()) {
                        case Health: {
                            if (target.getHealth() < currentTarget.getHealth()) {
                                currentTarget = target;
                                continue;
                            }
                            continue;
                        }
                        case HurtTime: {
                            if (target.hurtTime < currentTarget.getHealth()) {
                                currentTarget = target;
                                continue;
                            }
                            continue;
                        }
                        case Distance: {
                            if (target.getDistanceToEntity(Aura.mc.thePlayer) < currentDistance) {
                                currentTarget = target;
                                currentDistance = currentTarget.getDistanceToEntity(Aura.mc.thePlayer);
                                continue;
                            }
                            continue;
                        }
                        default: {
                            return target;
                        }
                    }
                }
                else {
                    currentTarget = target;
                    switch (this.sortMode.getPropertyValue()) {
                        case Health: {
                            if (target.getHealth() < currentTarget.getHealth()) {
                                currentTarget = target;
                                continue;
                            }
                            continue;
                        }
                        case Distance: {
                            if (target.getDistanceToEntity(Aura.mc.thePlayer) < currentDistance) {
                                currentTarget = target;
                                currentDistance = currentTarget.getDistanceToEntity(Aura.mc.thePlayer);
                                continue;
                            }
                            continue;
                        }
                        default: {
                            return target;
                        }
                    }
                }
            }
        }
        return currentTarget;
    }
    
    public boolean isValid(final EntityLivingBase targetIn) {
        if (AntiBot.botList.contains(targetIn.getEntityId())) {
            return false;
        }
        switch (this.auraMode.getPropertyValue()) {
            case Switch:
            case Single: {
                if (targetIn instanceof EntityPlayer && this.targeting.isSelected(Targets.Players) && !targetIn.getDisplayName().getFormattedText().contains("NPC") && !PlayerUtils.isOnSameTeam(targetIn) && !targetIn.isDead && ((EntityPlayer)targetIn).getHealth() > 0.0f && targetIn != Aura.mc.thePlayer && Aura.mc.thePlayer.getDistanceToEntity(targetIn) <= ((this.ABRange.getPropertyValue() > this.Range.getPropertyValue()) ? this.ABRange.getPropertyValue() : this.Range.getPropertyValue())) {
                    return true;
                }
                if (targetIn instanceof EntityPlayer && this.targeting.isSelected(Targets.Teams) && !targetIn.getDisplayName().getFormattedText().contains("NPC") && PlayerUtils.isOnSameTeam(targetIn) && !targetIn.isDead && ((EntityPlayer)targetIn).getHealth() > 0.0f && targetIn != Aura.mc.thePlayer && Aura.mc.thePlayer.getDistanceToEntity(targetIn) <= ((this.ABRange.getPropertyValue() > this.Range.getPropertyValue()) ? this.ABRange.getPropertyValue() : this.Range.getPropertyValue())) {
                    return true;
                }
                if (targetIn instanceof EntityPlayer && this.targeting.isSelected(Targets.NPCS) && targetIn.getDisplayName().getFormattedText().contains("NPC") && !targetIn.isDead && ((EntityPlayer)targetIn).getHealth() > 0.0f && targetIn != Aura.mc.thePlayer && Aura.mc.thePlayer.getDistanceToEntity(targetIn) <= ((this.ABRange.getPropertyValue() > this.Range.getPropertyValue()) ? this.ABRange.getPropertyValue() : this.Range.getPropertyValue())) {
                    return true;
                }
                if (targetIn instanceof EntityVillager && this.targeting.isSelected(Targets.Villagers) && !targetIn.isDead && ((EntityVillager)targetIn).getHealth() > 0.0f && Aura.mc.thePlayer.getDistanceToEntity(targetIn) <= ((this.ABRange.getPropertyValue() > this.Range.getPropertyValue()) ? this.ABRange.getPropertyValue() : this.Range.getPropertyValue())) {
                    return true;
                }
                if (targetIn instanceof EntityMob && this.targeting.isSelected(Targets.Mobs) && !targetIn.isDead && ((EntityMob)targetIn).getHealth() > 0.0f && Aura.mc.thePlayer.getDistanceToEntity(targetIn) <= ((this.ABRange.getPropertyValue() > this.Range.getPropertyValue()) ? this.ABRange.getPropertyValue() : this.Range.getPropertyValue())) {
                    return true;
                }
                if (targetIn instanceof EntityAnimal && this.targeting.isSelected(Targets.Passives) && !targetIn.isDead && ((EntityAnimal)targetIn).getHealth() > 0.0f && Aura.mc.thePlayer.getDistanceToEntity(targetIn) <= ((this.ABRange.getPropertyValue() > this.Range.getPropertyValue()) ? this.ABRange.getPropertyValue() : this.Range.getPropertyValue())) {
                    return true;
                }
                break;
            }
        }
        return false;
    }
    
    static {
        Aura.target = null;
    }
    
    private enum auraModes
    {
        Single, 
        Switch;
    }
    
    private enum Targets
    {
        Players, 
        NPCS, 
        Teams, 
        Passives, 
        Mobs, 
        Villagers;
    }
    
    private enum sortModes
    {
        Health, 
        HurtTime, 
        Distance;
    }
    
    private enum blockModes
    {
        Interact, 
        C08, 
        SetUse, 
        NCP, 
        Fake;
    }
    
    private enum particleModes
    {
        Crit, 
        Enchant, 
        Heart, 
        Explosion, 
        Blood;
    }
}
