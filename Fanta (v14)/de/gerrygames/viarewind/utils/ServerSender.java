/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.api.protocol.Protocol
 *  com.viaversion.viaversion.api.protocol.packet.PacketWrapper
 */
package de.gerrygames.viarewind.utils;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;

public interface ServerSender {
    public void sendToServer(PacketWrapper var1, Class<? extends Protocol> var2, boolean var3, boolean var4) throws Exception;
}

