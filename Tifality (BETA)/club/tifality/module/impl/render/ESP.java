/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.render;

import club.tifality.Tifality;
import club.tifality.gui.font.TrueTypeFontRenderer;
import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.render.Render2DEvent;
import club.tifality.manager.event.impl.render.Render3DEvent;
import club.tifality.manager.event.impl.render.RenderNameTagEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.module.impl.other.HackerDetect;
import club.tifality.module.impl.render.hud.Hud;
import club.tifality.property.Property;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.property.impl.EnumProperty;
import club.tifality.utils.PlayerUtils;
import club.tifality.utils.Wrapper;
import club.tifality.utils.render.Colors;
import club.tifality.utils.render.RenderingUtils;
import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

@ModuleInfo(label="ESP", category=ModuleCategory.RENDER)
public final class ESP
extends Module {
    private final Property<Boolean> armor = new Property<Boolean>("Armor", true);
    private final Property<Boolean> health = new Property<Boolean>("Health", true);
    private final Property<Boolean> box = new Property<Boolean>("Box", true);
    private final Property<Boolean> item = new Property<Boolean>("Item", true);
    private final Property<Boolean> name = new Property<Boolean>("Name", true);
    private final Property<Boolean> invis = new Property<Boolean>("Invisible", true);
    private final Property<Boolean> customTag = new Property<Boolean>("Custom Tag", false);
    public final EnumProperty<BoxMode> boxStyle = new EnumProperty<BoxMode>("Box Mode", BoxMode.Corner);
    private final DoubleProperty width2d = new DoubleProperty("Box Width", 0.5, this.box::get, (double)0.1f, 1.0, 0.1f);
    private final DecimalFormat decimalFormat = new DecimalFormat("0.0#", new DecimalFormatSymbols(Locale.ENGLISH));
    private static final FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
    private static final FloatBuffer projection = BufferUtils.createFloatBuffer(16);
    private static final IntBuffer viewport = BufferUtils.createIntBuffer(16);
    private final List<Vec3> positions = new ArrayList<Vec3>();

    @Listener
    public void onRender3D(Render3DEvent event) {
        if (this.boxStyle.get() == BoxMode.Box) {
            for (Entity entity : ESP.mc.theWorld.loadedEntityList) {
                if (!(entity instanceof EntityItem) && !this.isValid(entity)) continue;
                ESP.updateView();
            }
        }
        if (this.boxStyle.get() == BoxMode.Corner) {
            for (Entity entity : ESP.mc.theWorld.loadedEntityList) {
                if (!(entity instanceof EntityItem) && !this.isValid(entity)) continue;
                ESP.updateView();
            }
        }
    }

    @Listener
    public void onRenderNameTag(RenderNameTagEvent e) {
        if (this.name.get().booleanValue()) {
            e.setCancelled();
        }
    }

    @Listener
    public void onRender2DEvent(Render2DEvent e) {
        ScaledResolution sr = new ScaledResolution(mc);
        if (this.boxStyle.get() == BoxMode.Corner) {
            GlStateManager.pushMatrix();
            GL11.glDisable(2929);
            double twoScale = (double)sr.getScaleFactor() / Math.pow(sr.getScaleFactor(), 2.0);
            GlStateManager.scale(twoScale, twoScale, twoScale);
            for (Entity entity : ESP.mc.theWorld.loadedEntityList) {
                if (!this.isValid(entity)) continue;
                this.updatePositions(entity);
                int maxLeft = Integer.MAX_VALUE;
                int maxRight = Integer.MIN_VALUE;
                int maxBottom = Integer.MIN_VALUE;
                int maxTop = Integer.MAX_VALUE;
                Iterator<Vec3> iterator2 = this.positions.iterator();
                boolean canEntityBeSeen = false;
                while (iterator2.hasNext()) {
                    Vec3 screenPosition = ESP.WorldToScreen(iterator2.next());
                    if (screenPosition == null || !(screenPosition.zCoord >= 0.0) || !(screenPosition.zCoord < 1.0)) continue;
                    maxLeft = (int)Math.min(screenPosition.xCoord, (double)maxLeft);
                    maxRight = (int)Math.max(screenPosition.xCoord, (double)maxRight);
                    maxBottom = (int)Math.max(screenPosition.yCoord, (double)maxBottom);
                    maxTop = (int)Math.min(screenPosition.yCoord, (double)maxTop);
                    canEntityBeSeen = true;
                }
                if (!canEntityBeSeen) continue;
                Gui.drawRect(0.0f, 0.0f, 0.0f, 0.0f, 0);
                if (this.health.get().booleanValue()) {
                    this.drawHealth((EntityLivingBase)entity, maxLeft, maxTop, maxRight, maxBottom);
                }
                if (this.armor.get().booleanValue()) {
                    this.drawArmor((EntityLivingBase)entity, maxLeft, maxTop, maxRight, maxBottom);
                }
                if (this.box.get().booleanValue()) {
                    this.drawBox(entity, maxLeft, maxTop, maxRight, maxBottom);
                }
                if (((EntityPlayer)entity).getCurrentEquippedItem() != null && this.item.get().booleanValue()) {
                    this.drawItem(entity, maxLeft, maxTop, maxRight, maxBottom);
                }
                if (!this.name.get().booleanValue()) continue;
                this.drawName(entity, maxLeft, maxTop, maxRight, maxBottom);
            }
            GL11.glEnable(2929);
            GlStateManager.popMatrix();
        }
    }

    private void drawName(Entity e, int left, int top, int right, int bottom) {
        HackerDetect el = Tifality.INSTANCE.getModuleManager().getModule(HackerDetect.class);
        EntityPlayer ent = (EntityPlayer)e;
        String renderName = Hud.getPing(ent) + "ms " + ent.getName();
        TrueTypeFontRenderer font = Wrapper.getEspBiggerFontRenderer();
        float meme2 = (float)((double)(right - left) / 2.0 - (double)font.getWidth(renderName));
        float halfWidth = font.getWidth(renderName) / 2.0f;
        float xDif = right - left;
        float middle = (float)left + xDif / 2.0f;
        float textHeight = font.getHeight(renderName);
        float renderY = (float)top - textHeight - 2.0f;
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0f, -1.0f, 0.0f);
        if (el.isEnabled() && el.isHacker(ent) || PlayerUtils.isTeamMate(ent) || Tifality.INSTANCE.getFriendManager().isFriend(ent.getName())) {
            RenderingUtils.drawRect(middle - halfWidth * 2.0f - 2.0f, renderY - 10.0f, middle + halfWidth * 2.0f + 2.0f, renderY + textHeight - 0.5f, new Color(0, 0, 0).getRGB());
            RenderingUtils.drawRect(middle - halfWidth * 2.0f - 1.0f, renderY - 9.0f, middle + halfWidth * 2.0f + 0.5f, renderY + textHeight - 1.5f, this.getColor(ent));
        }
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        RenderingUtils.drawOutlineString(Wrapper.getEspBiggerFontRenderer(), renderName, ((float)left + meme2) / 2.0f, ((float)top - font.getHeight(renderName) / 1.5f * 2.0f) / 2.0f - 4.0f, new Color(255, 255, 255).getRGB(), new Color(0, 0, 0, 210).getRGB());
        GlStateManager.popMatrix();
    }

    private void drawItem(Entity e, int left, int top, int right, int bottom) {
        EntityPlayer ent = (EntityPlayer)e;
        GlStateManager.pushMatrix();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        ItemStack stack = ent.getCurrentEquippedItem();
        String customName = this.customTag.get() != false ? ent.getCurrentEquippedItem().getDisplayName() : ent.getCurrentEquippedItem().getItem().getItemStackDisplayName(stack);
        float meme5 = (float)((double)(right - left) / 2.0 - (double)Wrapper.getVerdana10().getWidth(customName));
        RenderingUtils.drawOutlineString(Wrapper.getEspFontRenderer(), customName, ((float)left + meme5) / 2.0f, ((float)bottom + Wrapper.getVerdana10().getHeight(customName) / 2.0f * 2.0f) / 2.0f + 1.0f, -1, new Color(0, 0, 0, 210).getRGB());
        GlStateManager.popMatrix();
        if (stack != null) {
            GlStateManager.pushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            mc.getRenderItem().renderItemIntoGUI(stack, (int)((float)left + meme5) + 29, (int)((float)bottom + Wrapper.getVerdana10().getHeight(customName) / 2.0f * 2.0f) + 15);
            mc.getRenderItem().renderItemOverlays(ESP.mc.fontRendererObj, stack, (int)((float)left + meme5) + 29, (int)((float)bottom + Wrapper.getVerdana10().getHeight(customName) / 2.0f * 2.0f) + 15);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();
        }
    }

    private void drawBox(Entity e, int left, int top, int right, int bottom) {
        int line = 1;
        int bg = new Color(0, 0, 0).getRGB();
        if (this.boxStyle.get() == BoxMode.Corner) {
            int p_drawRect_0_ = left + (right - left) / 3 + line;
            int p_drawRect_2_ = right - (right - left) / 3 - line;
            int p_drawRect_3_ = top + (bottom - top) / 3 + line;
            int p_drawRect_3_1 = bottom - 1 - (bottom - top) / 3 - line;
            Gui.drawRect(left + 1 + line, top - line, left - line, p_drawRect_3_, bg);
            Gui.drawRect(p_drawRect_0_, top + line, left, top - 1 - line, bg);
            Gui.drawRect(right + line, top - line, right - 1 - line, p_drawRect_3_, bg);
            Gui.drawRect(right, top + line, p_drawRect_2_, top - 1 - line, bg);
            Gui.drawRect(left + 1 + line, bottom - 1 - line, left - line, p_drawRect_3_1, bg);
            Gui.drawRect(p_drawRect_0_, bottom + line, left - line, bottom - 1 - line, bg);
            Gui.drawRect(right + line, bottom - 1 + line, right - 1 - line, p_drawRect_3_1, bg);
            Gui.drawRect(right + line, bottom + line, p_drawRect_2_, bottom - 1 - line, bg);
            Gui.drawRect(left + 1, top, left, (float)top + (float)(bottom - top) / 3.0f, this.getColor(e).getRGB());
            Gui.drawRect((float)left + (float)(right - left) / 3.0f, top, left, top - 1, this.getColor(e).getRGB());
            Gui.drawRect(right, top, right - 1, (float)top + (float)(bottom - top) / 3.0f, this.getColor(e).getRGB());
            Gui.drawRect(right, top, (float)right - (float)(right - left) / 3.0f, top - 1, this.getColor(e).getRGB());
            Gui.drawRect(left + 1, bottom - 1, left, (float)(bottom - 1) - (float)(bottom - top) / 3.0f, this.getColor(e).getRGB());
            Gui.drawRect((float)left + (float)(right - left) / 3.0f, bottom, left, bottom - 1, this.getColor(e).getRGB());
            Gui.drawRect(right, bottom - 1, right - 1, (float)(bottom - 1) - (float)(bottom - top) / 3.0f, this.getColor(e).getRGB());
            Gui.drawRect(right, bottom, (float)right - (float)(right - left) / 3.0f, bottom - 1, this.getColor(e).getRGB());
        } else if (this.boxStyle.get() == BoxMode.Box) {
            Gui.drawRect(right + line, top + line, left - line, top - 1 - line, bg);
            Gui.drawRect(right + line, bottom + line, left - line, bottom - 1 - line, bg);
            Gui.drawRect(left + 1 + line, top, left - line, bottom, bg);
            Gui.drawRect(right + line, top, right - 1 - line, bottom, bg);
            Gui.drawRect(right, top, left, top - 1, this.getColor(e).getRGB());
            Gui.drawRect(right, bottom, left, bottom - 1, this.getColor(e).getRGB());
            Gui.drawRect(left + 1, top, left, bottom, this.getColor(e).getRGB());
            Gui.drawRect(right, top, right - 1, bottom, this.getColor(e).getRGB());
        }
    }

    private void drawArmor(EntityLivingBase entityLivingBase, float left, float top, float right, float bottom) {
        float height = bottom + 1.0f - top;
        float currentArmor = entityLivingBase.getTotalArmorValue();
        float armorPercent = currentArmor / 20.0f;
        float MOVE = 2.0f;
        boolean line = true;
        if (ESP.mc.thePlayer.getDistanceToEntity(entityLivingBase) > 16.0f) {
            return;
        }
        for (int i = 0; i < 4; ++i) {
            double h = (bottom - top) / 4.0f;
            ItemStack itemStack = entityLivingBase.getEquipmentInSlot(i + 1);
            double difference = (double)(top - bottom) + 0.5;
            if (itemStack == null) continue;
            RenderingUtils.drawESPRect(right + 2.0f + 1.0f + MOVE, top - 2.0f, right + 1.0f - 1.0f + MOVE, bottom + 1.0f, new Color(25, 25, 25, 150).getRGB());
            RenderingUtils.drawESPRect(right + 3.0f + MOVE, top + height * (1.0f - armorPercent) - 1.0f, right + 1.0f + MOVE, bottom, new Color(78, 206, 229).getRGB());
            RenderingUtils.drawESPRect(right + 3.0f + MOVE + (float)line, bottom + 1.0f, right + 3.0f + MOVE, top - 2.0f, new Color(0, 0, 0, 255).getRGB());
            RenderingUtils.drawESPRect(right + 1.0f + MOVE, bottom + 1.0f, right + 1.0f + MOVE - (float)line, top - 2.0f, new Color(0, 0, 0, 255).getRGB());
            RenderingUtils.drawESPRect(right + 1.0f + MOVE, top - 1.0f, right + 3.0f + MOVE, top - 2.0f, new Color(0, 0, 0, 255).getRGB());
            RenderingUtils.drawESPRect(right + 1.0f + MOVE, bottom + 1.0f, right + 3.0f + MOVE, bottom, new Color(0, 0, 0, 255).getRGB());
            RenderingUtils.renderItemStack(itemStack, (int)(right + 6.0f + MOVE), (int)((double)(bottom + 30.0f) - (double)(i + 1) * h));
            float scale = 1.0f;
            GlStateManager.pushMatrix();
            GlStateManager.scale(scale, scale, scale);
            ESP.mc.fontRendererObj.drawStringWithShadow(String.valueOf(itemStack.getMaxDamage() - itemStack.getItemDamage()), (right + 6.0f + MOVE + (16.0f - (float)ESP.mc.fontRendererObj.getStringWidth(String.valueOf(itemStack.getMaxDamage() - itemStack.getItemDamage())) * scale) / 2.0f) / scale, (float)((int)((double)(bottom + 30.0f) - (double)(i + 1) * h) + 16) / scale, -1);
            GlStateManager.popMatrix();
            if (!(-difference > 50.0)) continue;
            for (int j = 1; j < 4; ++j) {
                double dThing = difference / 4.0 * (double)j;
                RenderingUtils.rectangle(right + 2.0f, (double)bottom - 0.5 + dThing, (double)right + 6.0, (double)bottom - 0.5 + dThing - 1.0, Colors.getColor(0));
            }
        }
    }

    private void drawHealth(EntityLivingBase entityLivingBase, float left, float top, float right, float bottom) {
        float height = bottom + 1.0f - top;
        float currentHealth = entityLivingBase.getHealth();
        float maxHealth = entityLivingBase.getMaxHealth();
        float healthPercent = currentHealth / maxHealth;
        float MOVE = 2.0f;
        boolean line = true;
        String healthStr = "\u00a7f" + this.decimalFormat.format(currentHealth) + "\u00a7c\u2764";
        float bottom1 = top + height * (1.0f - healthPercent) - 1.0f;
        float health = entityLivingBase.getHealth();
        float[] fractions = new float[]{0.0f, 0.5f, 1.0f};
        Color[] colors = new Color[]{Color.RED, Color.YELLOW, Color.GREEN};
        float progress = health / entityLivingBase.getMaxHealth();
        Color customColor = health >= 0.0f ? Colors.blendColors(fractions, colors, progress).brighter() : Color.RED;
        ESP.mc.fontRendererObj.drawStringWithShadow(healthStr, left - 3.0f - MOVE - (float)ESP.mc.fontRendererObj.getStringWidth(healthStr), bottom1, -1);
        RenderingUtils.drawESPRect(left - 3.0f - MOVE, bottom, left - 1.0f - MOVE, top - 1.0f, new Color(25, 25, 25, 150).getRGB());
        RenderingUtils.drawESPRect(left - 3.0f - MOVE, bottom, left - 1.0f - MOVE, bottom1, customColor.getRGB());
        RenderingUtils.drawESPRect(left - 3.0f - MOVE, bottom + 1.0f, left - 3.0f - MOVE - (float)line, top - 2.0f, new Color(0, 0, 0, 255).getRGB());
        RenderingUtils.drawESPRect(left - 1.0f - MOVE + (float)line, bottom + 1.0f, left - 1.0f - MOVE, top - 2.0f, new Color(0, 0, 0, 255).getRGB());
        RenderingUtils.drawESPRect(left - 3.0f - MOVE, top - 1.0f, left - 1.0f - MOVE, top - 2.0f, new Color(0, 0, 0, 255).getRGB());
        RenderingUtils.drawESPRect(left - 3.0f - MOVE, bottom + 1.0f, left - 1.0f - MOVE, bottom, new Color(0, 0, 0, 255).getRGB());
        double difference = (double)(top - bottom) + 0.5;
        if (-difference > 50.0) {
            for (int j = 1; j < 10; ++j) {
                double dThing = difference / 10.0 * (double)j;
                RenderingUtils.rectangle((double)left - 5.5, (double)bottom - 0.5 + dThing, (double)left - 2.5, (double)bottom - 0.5 + dThing - 1.0, Colors.getColor(0));
            }
        }
    }

    public Color getColor(Entity entity) {
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            if (Tifality.INSTANCE.getFriendManager().isFriend((EntityPlayer)entityLivingBase)) {
                return new Color(0, 0, 255);
            }
            if (PlayerUtils.isTeamMate((EntityPlayer)entityLivingBase)) {
                return Color.GREEN;
            }
        }
        return new Color(255, 255, 255);
    }

    private static Vec3 WorldToScreen(Vec3 position) {
        FloatBuffer screenPositions = BufferUtils.createFloatBuffer(3);
        boolean result2 = GLU.gluProject((float)position.xCoord, (float)position.yCoord, (float)position.zCoord, modelView, projection, viewport, screenPositions);
        if (result2) {
            return new Vec3(screenPositions.get(0), (float)Display.getHeight() - screenPositions.get(1), screenPositions.get(2));
        }
        return null;
    }

    public void updatePositions(Entity entity) {
        this.positions.clear();
        Vec3 position = ESP.getEntityRenderPosition(entity);
        double x = position.xCoord - entity.posX;
        double y = position.yCoord - entity.posY;
        double z = position.zCoord - entity.posZ;
        double height = entity instanceof EntityItem ? 0.5 : (double)entity.height + 0.1;
        double width = entity instanceof EntityItem ? 0.25 : (Double)this.width2d.get();
        AxisAlignedBB aabb = new AxisAlignedBB(entity.posX - width + x, entity.posY + y, entity.posZ - width + z, entity.posX + width + x, entity.posY + height + y, entity.posZ + width + z);
        this.positions.add(new Vec3(aabb.minX, aabb.minY, aabb.minZ));
        this.positions.add(new Vec3(aabb.minX, aabb.minY, aabb.maxZ));
        this.positions.add(new Vec3(aabb.minX, aabb.maxY, aabb.minZ));
        this.positions.add(new Vec3(aabb.minX, aabb.maxY, aabb.maxZ));
        this.positions.add(new Vec3(aabb.maxX, aabb.minY, aabb.minZ));
        this.positions.add(new Vec3(aabb.maxX, aabb.minY, aabb.maxZ));
        this.positions.add(new Vec3(aabb.maxX, aabb.maxY, aabb.minZ));
        this.positions.add(new Vec3(aabb.maxX, aabb.maxY, aabb.maxZ));
    }

    private static Vec3 getEntityRenderPosition(Entity entity) {
        return new Vec3(ESP.getEntityRenderX(entity), ESP.getEntityRenderY(entity), ESP.getEntityRenderZ(entity));
    }

    private static double getEntityRenderX(Entity entity) {
        return entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)Minecraft.getMinecraft().timer.renderPartialTicks - RenderManager.renderPosX;
    }

    private static double getEntityRenderY(Entity entity) {
        return entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)Minecraft.getMinecraft().timer.renderPartialTicks - RenderManager.renderPosY;
    }

    private static double getEntityRenderZ(Entity entity) {
        return entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)Minecraft.getMinecraft().timer.renderPartialTicks - RenderManager.renderPosZ;
    }

    private int getColor(EntityLivingBase ent) {
        HackerDetect el = Tifality.INSTANCE.getModuleManager().getModule(HackerDetect.class);
        if (Tifality.INSTANCE.getFriendManager().isFriend(ent.getName())) {
            return new Color(10, 10, 255).getRGB();
        }
        if (ent.getName().equals(ESP.mc.thePlayer.getName())) {
            return new Color(50, 255, 50).getRGB();
        }
        if (el.isEnabled() && el.isHacker(ent)) {
            return new Color(255, 0, 0).getRGB();
        }
        if (PlayerUtils.isTeamMate((EntityPlayer)ent)) {
            return new Color(0, 200, 0).getRGB();
        }
        return new Color(200, 0, 0, 50).getRGB();
    }

    private boolean isValid(Entity entity) {
        if (entity == ESP.mc.thePlayer && ESP.mc.gameSettings.thirdPersonView == 0) {
            return false;
        }
        if (!this.invis.get().booleanValue() && entity.isInvisible()) {
            return false;
        }
        return entity instanceof EntityPlayer;
    }

    private static void updateView() {
        GL11.glGetFloat(2982, modelView);
        GL11.glGetFloat(2983, projection);
        GL11.glGetInteger(2978, viewport);
    }

    public static enum BoxMode {
        Box,
        Corner;

    }
}

