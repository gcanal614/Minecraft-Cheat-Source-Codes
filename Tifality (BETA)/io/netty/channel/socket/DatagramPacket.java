/*
 * Decompiled with CFR 0.152.
 */
package io.netty.channel.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.DefaultAddressedEnvelope;
import java.net.InetSocketAddress;

public final class DatagramPacket
extends DefaultAddressedEnvelope<ByteBuf, InetSocketAddress>
implements ByteBufHolder {
    public DatagramPacket(ByteBuf data2, InetSocketAddress recipient) {
        super(data2, recipient);
    }

    public DatagramPacket(ByteBuf data2, InetSocketAddress recipient, InetSocketAddress sender) {
        super(data2, recipient, sender);
    }

    @Override
    public DatagramPacket copy() {
        return new DatagramPacket(((ByteBuf)this.content()).copy(), (InetSocketAddress)this.recipient(), (InetSocketAddress)this.sender());
    }

    @Override
    public DatagramPacket duplicate() {
        return new DatagramPacket(((ByteBuf)this.content()).duplicate(), (InetSocketAddress)this.recipient(), (InetSocketAddress)this.sender());
    }

    @Override
    public DatagramPacket retain() {
        super.retain();
        return this;
    }

    @Override
    public DatagramPacket retain(int increment) {
        super.retain(increment);
        return this;
    }
}

