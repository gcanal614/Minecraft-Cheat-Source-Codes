package me.module.impl.render;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import me.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemPhysics extends Module {
	 public static Minecraft mc;
	  
	  public static long tick;
	public ItemPhysics() {
		super("ItemPhysics",0, Category.RENDER);
	}


	  public static double rotation = 0.0D;
	  
	  public static Random random;
	  
	  public static ResourceLocation getEntityTexture() {
	    return TextureMap.locationBlocksTexture;
	  }
	  
	  public static void setPositionAndRotation2(EntityItem item, double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_) {
	    item.setPosition(x, y, z);
	  }
	  
	  public static void doRender(RenderEntityItem renderer, Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
	    int i;
	    rotation = (System.nanoTime() - tick) / 2500000.0D * 0.0010000000474974513D;
	    if (!mc.inGameHasFocus)
	      rotation = 0.0D; 
	    EntityItem item = (EntityItem)entity;
	    ItemStack itemstack = item.getEntityItem();
	    if (itemstack != null && itemstack.getItem() != null) {
	      i = Item.getIdFromItem(itemstack.getItem()) + itemstack.getMetadata();
	    } else {
	      i = 187;
	    } 
	    random.setSeed(i);
	    boolean flag = true;
	    renderer.bindTexture(getEntityTexture());
	    (renderer.getRenderManager()).renderEngine.getTexture(getEntityTexture()).setBlurMipmap(false, false);
	    GlStateManager.enableRescaleNormal();
	    GlStateManager.alphaFunc(516, 0.1F);
	    GlStateManager.enableBlend();
	    RenderHelper.enableStandardItemLighting();
	    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
	    GlStateManager.pushMatrix();
	    IBakedModel ibakedmodel = mc.getRenderItem().getItemModelMesher().getItemModel(itemstack);
	    boolean flag2 = ibakedmodel.isGui3d();
	    boolean is3D = ibakedmodel.isGui3d();
	    int j = getModelCount(itemstack);
	    float f = 0.25F;
	    float f2 = 0.0F;
	    float f3 = (ibakedmodel.getItemCameraTransforms().func_181688_b(ItemCameraTransforms.TransformType.GROUND)).scale.y;
	    GlStateManager.translate((float)x, (float)y, (float)z);
	    if (ibakedmodel.isGui3d())
	      GlStateManager.scale(0.5F, 0.5F, 0.5F); 
	    GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
	    GL11.glRotatef(item.rotationYaw, 0.0F, 0.0F, 1.0F);
	    if (is3D) {
	      GlStateManager.translate(0.0D, 0.0D, -0.08D);
	    } else {
	      GlStateManager.translate(0.0D, 0.0D, -0.04D);
	    } 
	    if (is3D || (mc.getRenderManager()).options != null) {
	      if (is3D) {
	        if (!item.onGround) {
	          EntityItem entityItem = item;
	          entityItem.rotationPitch += (float)rotation;
	        } 
	      } else if (item != null && !Double.isNaN(item.posX) && !Double.isNaN(item.posY) && !Double.isNaN(item.posZ) && item.worldObj != null) {
	        if (item.onGround) {
	          item.rotationPitch = 0.0F;
	        } else {
	          rotation *= 2.0D;
	          EntityItem entityItem2 = item;
	          entityItem2.rotationPitch += (float)rotation;
	        } 
	      } 
	      //GlStateManager.rotate(item.rotationPitch, 1.0F, 0.0F, 0.0F);
	    } 
	    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	    for (int k = 0; k < j; k++) {
	      if (flag2) {
	        GlStateManager.pushMatrix();
	        if (k > 0) {
	          float f4 = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
	          float f5 = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
	          float f6 = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
	          GlStateManager.translate(f4, f5, f6);
	        } 
	        ibakedmodel = handleCameraTransforms(ibakedmodel, ItemCameraTransforms.TransformType.GROUND);
	        mc.getRenderItem().renderItem(itemstack, ibakedmodel);
	        GlStateManager.popMatrix();
	      } else {
	        GlStateManager.pushMatrix();
	        if (k > 0);
	        ibakedmodel = handleCameraTransforms(ibakedmodel, ItemCameraTransforms.TransformType.GROUND);
	        mc.getRenderItem().renderItem(itemstack, ibakedmodel);
	        GlStateManager.popMatrix();
	        GlStateManager.translate(0.0F, 0.0F, 0.05375F);
	      } 
	    } 
	    GlStateManager.popMatrix();
	    GlStateManager.disableRescaleNormal();
	    GlStateManager.disableBlend();
	    renderer.bindTexture(getEntityTexture());
	    (renderer.getRenderManager()).renderEngine.getTexture(getEntityTexture()).restoreLastBlurMipmap();
	  }
	  
	  public static int getModelCount(ItemStack stack) {
	    int i = 1;
	    if (stack.stackSize > 48) {
	      i = 5;
	    } else if (stack.stackSize > 32) {
	      i = 4;
	    } else if (stack.stackSize > 16) {
	      i = 3;
	    } else if (stack.stackSize > 1) {
	      i = 2;
	    } 
	    return i;
	  }
	  
	  public static IBakedModel handleCameraTransforms(IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType) {
	    model.getItemCameraTransforms().func_181689_a(cameraTransformType);
	    return model;
	  }
	  
	  static {
	    mc = Minecraft.getMinecraft();
	    random = new Random();
	  }
}
