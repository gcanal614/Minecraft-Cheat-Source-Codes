/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.api.connection.StoredObject
 *  com.viaversion.viaversion.api.connection.UserConnection
 *  com.viaversion.viaversion.api.protocol.packet.PacketWrapper
 *  com.viaversion.viaversion.api.type.Type
 *  com.viaversion.viaversion.util.Pair
 */
package de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.Pair;
import de.gerrygames.viarewind.ViaRewind;
import de.gerrygames.viarewind.api.ViaRewindConfig;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.BlockPlaceDestroyTracker;
import de.gerrygames.viarewind.utils.PacketUtil;
import de.gerrygames.viarewind.utils.Tickable;
import java.util.ArrayList;
import java.util.UUID;

public class Cooldown
extends StoredObject
implements Tickable {
    private double attackSpeed = 4.0;
    private long lastHit = 0L;
    private final ViaRewindConfig.CooldownIndicator cooldownIndicator;
    private UUID bossUUID;
    private boolean lastSend;
    private static final int max = 10;

    public Cooldown(UserConnection user) {
        super(user);
        ViaRewindConfig.CooldownIndicator indicator;
        try {
            indicator = ViaRewind.getConfig().getCooldownIndicator();
        }
        catch (IllegalArgumentException e) {
            ViaRewind.getPlatform().getLogger().warning("Invalid cooldown-indicator setting");
            indicator = ViaRewindConfig.CooldownIndicator.DISABLED;
        }
        this.cooldownIndicator = indicator;
    }

    @Override
    public void tick() {
        if (!this.hasCooldown()) {
            if (this.lastSend) {
                this.hide();
                this.lastSend = false;
            }
            return;
        }
        BlockPlaceDestroyTracker tracker = (BlockPlaceDestroyTracker)this.getUser().get(BlockPlaceDestroyTracker.class);
        if (tracker.isMining()) {
            this.lastHit = 0L;
            if (this.lastSend) {
                this.hide();
                this.lastSend = false;
            }
            return;
        }
        this.showCooldown();
        this.lastSend = true;
    }

    private void showCooldown() {
        if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.TITLE) {
            this.sendTitle("", this.getTitle(), 0, 2, 5);
        } else if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.ACTION_BAR) {
            this.sendActionBar(this.getTitle());
        } else if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.BOSS_BAR) {
            this.sendBossBar((float)this.getCooldown());
        }
    }

    private void hide() {
        if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.ACTION_BAR) {
            this.sendActionBar("\u00a7r");
        } else if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.TITLE) {
            this.hideTitle();
        } else if (this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.BOSS_BAR) {
            this.hideBossBar();
        }
    }

    private void hideBossBar() {
        if (this.bossUUID == null) {
            return;
        }
        PacketWrapper wrapper = PacketWrapper.create((int)12, null, (UserConnection)this.getUser());
        wrapper.write(Type.UUID, (Object)this.bossUUID);
        wrapper.write((Type)Type.VAR_INT, (Object)1);
        PacketUtil.sendPacket(wrapper, Protocol1_8TO1_9.class, false, true);
        this.bossUUID = null;
    }

    private void sendBossBar(float cooldown) {
        PacketWrapper wrapper = PacketWrapper.create((int)12, null, (UserConnection)this.getUser());
        if (this.bossUUID == null) {
            this.bossUUID = UUID.randomUUID();
            wrapper.write(Type.UUID, (Object)this.bossUUID);
            wrapper.write((Type)Type.VAR_INT, (Object)0);
            wrapper.write(Type.STRING, (Object)"{\"text\":\"  \"}");
            wrapper.write((Type)Type.FLOAT, (Object)Float.valueOf(cooldown));
            wrapper.write((Type)Type.VAR_INT, (Object)0);
            wrapper.write((Type)Type.VAR_INT, (Object)0);
            wrapper.write((Type)Type.UNSIGNED_BYTE, (Object)0);
        } else {
            wrapper.write(Type.UUID, (Object)this.bossUUID);
            wrapper.write((Type)Type.VAR_INT, (Object)2);
            wrapper.write((Type)Type.FLOAT, (Object)Float.valueOf(cooldown));
        }
        PacketUtil.sendPacket(wrapper, Protocol1_8TO1_9.class, false, true);
    }

    private void hideTitle() {
        PacketWrapper hide = PacketWrapper.create((int)69, null, (UserConnection)this.getUser());
        hide.write((Type)Type.VAR_INT, (Object)3);
        PacketUtil.sendPacket(hide, Protocol1_8TO1_9.class);
    }

    private void sendTitle(String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        PacketWrapper timePacket = PacketWrapper.create((int)69, null, (UserConnection)this.getUser());
        timePacket.write((Type)Type.VAR_INT, (Object)2);
        timePacket.write((Type)Type.INT, (Object)fadeIn);
        timePacket.write((Type)Type.INT, (Object)stay);
        timePacket.write((Type)Type.INT, (Object)fadeOut);
        PacketWrapper titlePacket = PacketWrapper.create((int)69, null, (UserConnection)this.getUser());
        titlePacket.write((Type)Type.VAR_INT, (Object)0);
        titlePacket.write(Type.STRING, (Object)title);
        PacketWrapper subtitlePacket = PacketWrapper.create((int)69, null, (UserConnection)this.getUser());
        subtitlePacket.write((Type)Type.VAR_INT, (Object)1);
        subtitlePacket.write(Type.STRING, (Object)subTitle);
        PacketUtil.sendPacket(titlePacket, Protocol1_8TO1_9.class);
        PacketUtil.sendPacket(subtitlePacket, Protocol1_8TO1_9.class);
        PacketUtil.sendPacket(timePacket, Protocol1_8TO1_9.class);
    }

    private void sendActionBar(String bar) {
        PacketWrapper actionBarPacket = PacketWrapper.create((int)2, null, (UserConnection)this.getUser());
        actionBarPacket.write(Type.STRING, (Object)bar);
        actionBarPacket.write((Type)Type.BYTE, (Object)2);
        PacketUtil.sendPacket(actionBarPacket, Protocol1_8TO1_9.class);
    }

    public boolean hasCooldown() {
        long time = System.currentTimeMillis() - this.lastHit;
        double cooldown = this.restrain((double)time * this.attackSpeed / 1000.0, 0.0, 1.5);
        return cooldown > 0.1 && cooldown < 1.1;
    }

    public double getCooldown() {
        long time = System.currentTimeMillis() - this.lastHit;
        return this.restrain((double)time * this.attackSpeed / 1000.0, 0.0, 1.0);
    }

    private double restrain(double x, double a, double b) {
        if (x < a) {
            return a;
        }
        if (x > b) {
            return b;
        }
        return x;
    }

    private String getTitle() {
        String symbol = this.cooldownIndicator == ViaRewindConfig.CooldownIndicator.ACTION_BAR ? "\u25a0" : "\u02d9";
        double cooldown = this.getCooldown();
        int green = (int)Math.floor(10.0 * cooldown);
        int grey = 10 - green;
        StringBuilder builder = new StringBuilder("\u00a78");
        while (green-- > 0) {
            builder.append(symbol);
        }
        builder.append("\u00a77");
        while (grey-- > 0) {
            builder.append(symbol);
        }
        return builder.toString();
    }

    public double getAttackSpeed() {
        return this.attackSpeed;
    }

    public void setAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public void setAttackSpeed(double base, ArrayList<Pair<Byte, Double>> modifiers) {
        int j;
        this.attackSpeed = base;
        for (j = 0; j < modifiers.size(); ++j) {
            if ((Byte)modifiers.get(j).getKey() != 0) continue;
            this.attackSpeed += ((Double)modifiers.get(j).getValue()).doubleValue();
            modifiers.remove(j--);
        }
        for (j = 0; j < modifiers.size(); ++j) {
            if ((Byte)modifiers.get(j).getKey() != 1) continue;
            this.attackSpeed += base * (Double)modifiers.get(j).getValue();
            modifiers.remove(j--);
        }
        for (j = 0; j < modifiers.size(); ++j) {
            if ((Byte)modifiers.get(j).getKey() != 2) continue;
            this.attackSpeed *= 1.0 + (Double)modifiers.get(j).getValue();
            modifiers.remove(j--);
        }
    }

    public void hit() {
        this.lastHit = System.currentTimeMillis();
    }

    public void setLastHit(long lastHit) {
        this.lastHit = lastHit;
    }
}

