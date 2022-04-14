package net.minecraft.client.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

public interface IResourcePack {
   IMetadataSection getPackMetadata(IMetadataSerializer var1, String var2) throws IOException;

   boolean resourceExists(ResourceLocation var1);

   InputStream getInputStream(ResourceLocation var1) throws IOException;

   String getPackName();

   Set getResourceDomains();

   BufferedImage getPackImage() throws IOException;
}
