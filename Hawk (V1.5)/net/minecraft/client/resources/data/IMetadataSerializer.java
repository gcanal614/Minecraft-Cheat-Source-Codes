package net.minecraft.client.resources.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IRegistry;
import net.minecraft.util.RegistrySimple;

public class IMetadataSerializer {
   private Gson gson;
   private final IRegistry metadataSectionSerializerRegistry = new RegistrySimple();
   private final GsonBuilder gsonBuilder = new GsonBuilder();
   private static final String __OBFID = "CL_00001101";

   public void registerMetadataSectionType(IMetadataSectionSerializer var1, Class var2) {
      this.metadataSectionSerializerRegistry.putObject(var1.getSectionName(), new IMetadataSerializer.Registration(this, var1, var2, (Object)null));
      this.gsonBuilder.registerTypeAdapter(var2, var1);
      this.gson = null;
   }

   private Gson getGson() {
      if (this.gson == null) {
         this.gson = this.gsonBuilder.create();
      }

      return this.gson;
   }

   public IMetadataSerializer() {
      this.gsonBuilder.registerTypeHierarchyAdapter(IChatComponent.class, new IChatComponent.Serializer());
      this.gsonBuilder.registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer());
      this.gsonBuilder.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
   }

   public IMetadataSection parseMetadataSection(String var1, JsonObject var2) {
      if (var1 == null) {
         throw new IllegalArgumentException("Metadata section name cannot be null");
      } else if (!var2.has(var1)) {
         return null;
      } else if (!var2.get(var1).isJsonObject()) {
         throw new IllegalArgumentException(String.valueOf((new StringBuilder("Invalid metadata for '")).append(var1).append("' - expected object, found ").append(var2.get(var1))));
      } else {
         IMetadataSerializer.Registration var3 = (IMetadataSerializer.Registration)this.metadataSectionSerializerRegistry.getObject(var1);
         if (var3 == null) {
            throw new IllegalArgumentException(String.valueOf((new StringBuilder("Don't know how to handle metadata section '")).append(var1).append("'")));
         } else {
            return (IMetadataSection)this.getGson().fromJson(var2.getAsJsonObject(var1), var3.field_110500_b);
         }
      }
   }

   class Registration {
      final Class field_110500_b;
      final IMetadataSerializer this$0;
      final IMetadataSectionSerializer field_110502_a;
      private static final String __OBFID = "CL_00001103";

      private Registration(IMetadataSerializer var1, IMetadataSectionSerializer var2, Class var3) {
         this.this$0 = var1;
         this.field_110502_a = var2;
         this.field_110500_b = var3;
      }

      Registration(IMetadataSerializer var1, IMetadataSectionSerializer var2, Class var3, Object var4) {
         this(var1, var2, var3);
      }
   }
}
