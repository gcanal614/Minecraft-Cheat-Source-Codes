/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.server.management.UserListEntry;

public class UserListWhitelistEntry
extends UserListEntry<GameProfile> {
    public UserListWhitelistEntry(GameProfile profile) {
        super(profile);
    }

    public UserListWhitelistEntry(JsonObject p_i1130_1_) {
        super(UserListWhitelistEntry.gameProfileFromJsonObject(p_i1130_1_), p_i1130_1_);
    }

    @Override
    protected void onSerialization(JsonObject data2) {
        if (this.getValue() != null) {
            data2.addProperty("uuid", ((GameProfile)this.getValue()).getId() == null ? "" : ((GameProfile)this.getValue()).getId().toString());
            data2.addProperty("name", ((GameProfile)this.getValue()).getName());
            super.onSerialization(data2);
        }
    }

    private static GameProfile gameProfileFromJsonObject(JsonObject p_152646_0_) {
        if (p_152646_0_.has("uuid") && p_152646_0_.has("name")) {
            UUID uuid;
            String s = p_152646_0_.get("uuid").getAsString();
            try {
                uuid = UUID.fromString(s);
            }
            catch (Throwable var4) {
                return null;
            }
            return new GameProfile(uuid, p_152646_0_.get("name").getAsString());
        }
        return null;
    }
}

