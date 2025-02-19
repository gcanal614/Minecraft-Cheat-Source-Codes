// 
// Decompiled by Procyon v0.5.36
// 

package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.metadata;

import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_16;
import com.viaversion.viaversion.api.connection.UserConnection;
import java.util.List;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.rewriter.EntityRewriter;

public class MetadataRewriter1_16To1_15_2 extends EntityRewriter<Protocol1_16To1_15_2>
{
    public MetadataRewriter1_16To1_15_2(final Protocol1_16To1_15_2 protocol) {
        super(protocol);
        this.mapEntityType(Entity1_15Types.ZOMBIE_PIGMAN, Entity1_16Types.ZOMBIFIED_PIGLIN);
        this.mapTypes(Entity1_15Types.values(), Entity1_16Types.class);
    }
    
    public void handleMetadata(final int entityId, final EntityType type, final Metadata metadata, final List<Metadata> metadatas, final UserConnection connection) throws Exception {
        metadata.setMetaType(MetaType1_16.byId(metadata.metaType().typeId()));
        if (metadata.metaType() == MetaType1_16.ITEM) {
            ((Protocol1_16To1_15_2)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
        }
        else if (metadata.metaType() == MetaType1_16.BLOCK_STATE) {
            final int data = (int)metadata.getValue();
            metadata.setValue(((Protocol1_16To1_15_2)this.protocol).getMappingData().getNewBlockStateId(data));
        }
        else if (metadata.metaType() == MetaType1_16.PARTICLE) {
            this.rewriteParticle((Particle)metadata.getValue());
        }
        if (type == null) {
            return;
        }
        if (type.isOrHasParent(Entity1_16Types.MINECART_ABSTRACT) && metadata.id() == 10) {
            final int data = (int)metadata.getValue();
            metadata.setValue(((Protocol1_16To1_15_2)this.protocol).getMappingData().getNewBlockStateId(data));
        }
        if (type.isOrHasParent(Entity1_16Types.ABSTRACT_ARROW)) {
            if (metadata.id() == 8) {
                metadatas.remove(metadata);
            }
            else if (metadata.id() > 8) {
                metadata.setId(metadata.id() - 1);
            }
        }
    }
    
    @Override
    public EntityType typeFromId(final int type) {
        return Entity1_16Types.getTypeFromId(type);
    }
}
