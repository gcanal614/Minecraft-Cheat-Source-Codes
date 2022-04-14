package net.minecraft.server.management;

import com.mojang.authlib.*;
import com.google.gson.*;
import java.util.*;

public class UserListBansEntry extends BanEntry
{
    public UserListBansEntry(final GameProfile p_i1134_1_) {
        this(p_i1134_1_, null, null, null, null);
    }
    
    public UserListBansEntry(final GameProfile p_i1135_1_, final Date p_i1135_2_, final String p_i1135_3_, final Date p_i1135_4_, final String p_i1135_5_) {
        super(p_i1135_1_, p_i1135_4_, p_i1135_3_, p_i1135_4_, p_i1135_5_);
    }
    
    public UserListBansEntry(final JsonObject p_i1136_1_) {
        super(func_152648_b(p_i1136_1_), p_i1136_1_);
    }
    
    private static GameProfile func_152648_b(final JsonObject p_152648_0_) {
        if (p_152648_0_.has("uuid") && p_152648_0_.has("name")) {
            final String var1 = p_152648_0_.get("uuid").getAsString();
            UUID var2;
            try {
                var2 = UUID.fromString(var1);
            }
            catch (Throwable var3) {
                return null;
            }
            return new GameProfile(var2, p_152648_0_.get("name").getAsString());
        }
        return null;
    }
    
    @Override
    protected void onSerialization(final JsonObject data) {
        if (this.getValue() != null) {
            data.addProperty("uuid", (((GameProfile)this.getValue()).getId() == null) ? "" : ((GameProfile)this.getValue()).getId().toString());
            data.addProperty("name", ((GameProfile)this.getValue()).getName());
            super.onSerialization(data);
        }
    }
}
