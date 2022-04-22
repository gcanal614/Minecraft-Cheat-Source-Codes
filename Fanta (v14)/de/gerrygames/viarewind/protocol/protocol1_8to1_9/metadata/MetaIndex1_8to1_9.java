/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types$EntityType
 *  com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetaIndex
 *  com.viaversion.viaversion.util.Pair
 */
package de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata;

import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetaIndex;
import com.viaversion.viaversion.util.Pair;
import java.util.HashMap;
import java.util.Optional;

public class MetaIndex1_8to1_9 {
    private static final HashMap<Pair<Entity1_10Types.EntityType, Integer>, MetaIndex> metadataRewrites = new HashMap();

    private static Optional<MetaIndex> getIndex(Entity1_10Types.EntityType type, int index) {
        Pair pair = new Pair((Object)type, (Object)index);
        if (metadataRewrites.containsKey(pair)) {
            return Optional.of(metadataRewrites.get(pair));
        }
        return Optional.empty();
    }

    public static MetaIndex searchIndex(Entity1_10Types.EntityType type, int index) {
        Entity1_10Types.EntityType currentType = type;
        do {
            Optional<MetaIndex> optMeta;
            if (!(optMeta = MetaIndex1_8to1_9.getIndex(currentType, index)).isPresent()) continue;
            return optMeta.get();
        } while ((currentType = currentType.getParent()) != null);
        return null;
    }

    static {
        for (MetaIndex index : MetaIndex.values()) {
            metadataRewrites.put((Pair<Entity1_10Types.EntityType, Integer>)new Pair((Object)index.getClazz(), (Object)index.getNewIndex()), index);
        }
    }
}

