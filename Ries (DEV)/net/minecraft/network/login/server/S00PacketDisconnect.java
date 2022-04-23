/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.login.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.util.IChatComponent;

public class S00PacketDisconnect
implements Packet<INetHandlerLoginClient> {
    private IChatComponent reason;

    public S00PacketDisconnect() {
    }

    public S00PacketDisconnect(IChatComponent reasonIn) {
        this.reason = reasonIn;
    }

    @Override
    public void readPacketData(PacketBuffer buf) {
        this.reason = buf.readChatComponent();
    }

    @Override
    public void writePacketData(PacketBuffer buf) {
        buf.writeChatComponent(this.reason);
    }

    @Override
    public void processPacket(INetHandlerLoginClient handler) {
        handler.handleDisconnect(this);
    }

    public IChatComponent func_149603_c() {
        return this.reason;
    }
}

