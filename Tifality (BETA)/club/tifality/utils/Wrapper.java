/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.utils;

import club.tifality.gui.font.TrueTypeFontRenderer;
import club.tifality.utils.render.TTFUtils;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Timer;

public final class Wrapper {
    private static final TrueTypeFontRenderer bigFontRenderer = new TrueTypeFontRenderer(TTFUtils.getFontFromLocation("font.ttf", 21), true, true);
    private static final TrueTypeFontRenderer fontRenderer = new TrueTypeFontRenderer(TTFUtils.getFontFromLocation("font.ttf", 20), true, true);
    private static final TrueTypeFontRenderer nameTagFontRenderer = new TrueTypeFontRenderer(TTFUtils.getFontFromLocation("font.ttf", 18), true, true);
    private static final TrueTypeFontRenderer csgoFontRenderer = new TrueTypeFontRenderer(new Font("Tahoma", 1, 11), true, false);
    private static final TrueTypeFontRenderer espBiggerFontRenderer = new TrueTypeFontRenderer(new Font("Tahoma Bold", 0, 12), true, false);
    private static final TrueTypeFontRenderer espFontRenderer = new TrueTypeFontRenderer(new Font("Tahoma", 0, 10), false, false);
    private static final TrueTypeFontRenderer testFontRenderer = new TrueTypeFontRenderer(new Font("Tahoma", 1, 16), true, true);
    private static final TrueTypeFontRenderer testFontRenderer1 = new TrueTypeFontRenderer(new Font("Tahoma", 1, 16), true, false);
    private static final TrueTypeFontRenderer verdana10 = new TrueTypeFontRenderer(new Font("Verdana", 0, 10), false, true);
    private static final TrueTypeFontRenderer verdana16 = new TrueTypeFontRenderer(new Font("Verdana", 0, 9), false, true);
    private static final TrueTypeFontRenderer titleFont = new TrueTypeFontRenderer(new Font("Tahoma", 0, 20), true, false);
    private static final TrueTypeFontRenderer infoFont = new TrueTypeFontRenderer(new Font("Tahoma", 1, 16), true, false);

    public static TrueTypeFontRenderer getCSGOFontRenderer() {
        return csgoFontRenderer;
    }

    public static TrueTypeFontRenderer getNameTagFontRenderer() {
        return nameTagFontRenderer;
    }

    public static EntityRenderer getEntityRenderer() {
        return Wrapper.getMinecraft().entityRenderer;
    }

    public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    public static EntityPlayerSP getPlayer() {
        return Wrapper.getMinecraft().thePlayer;
    }

    public static WorldClient getWorld() {
        return Wrapper.getMinecraft().theWorld;
    }

    public static TrueTypeFontRenderer getFontRenderer() {
        return fontRenderer;
    }

    public static TrueTypeFontRenderer getBigFontRenderer() {
        return bigFontRenderer;
    }

    public static TrueTypeFontRenderer getTitleFont() {
        return titleFont;
    }

    public static TrueTypeFontRenderer getInfoFont() {
        return infoFont;
    }

    public static TrueTypeFontRenderer getVerdana10() {
        return verdana10;
    }

    public static TrueTypeFontRenderer getVerdana16() {
        return verdana16;
    }

    public static TrueTypeFontRenderer getTestFont() {
        return testFontRenderer;
    }

    public static TrueTypeFontRenderer getTestFont1() {
        return testFontRenderer1;
    }

    public static TrueTypeFontRenderer getEspFontRenderer() {
        return espFontRenderer;
    }

    public static TrueTypeFontRenderer getEspBiggerFontRenderer() {
        return espBiggerFontRenderer;
    }

    public static PlayerControllerMP getPlayerController() {
        return Wrapper.getMinecraft().playerController;
    }

    public static NetHandlerPlayClient getNetHandler() {
        return Wrapper.getMinecraft().getNetHandler();
    }

    public static GameSettings getGameSettings() {
        return Wrapper.getMinecraft().gameSettings;
    }

    public static ItemStack getStackInSlot(int index) {
        return Wrapper.getPlayer().inventoryContainer.getSlot(index).getStack();
    }

    public static Timer getTimer() {
        return Wrapper.getMinecraft().getTimer();
    }

    public static Block getBlock(BlockPos pos) {
        return Wrapper.getMinecraft().theWorld.getBlockState(pos).getBlock();
    }

    public static void addChatMessage(String message) {
        Wrapper.getPlayer().addChatMessage(new ChatComponentText("\u00a78[\u00a7CT\u00a78]\u00a77 " + message));
    }

    public static GuiScreen getCurrentScreen() {
        return Wrapper.getMinecraft().currentScreen;
    }

    public static List<Entity> getLoadedEntities() {
        return Wrapper.getWorld().getLoadedEntityList();
    }

    public static List<EntityLivingBase> getLivingEntities() {
        return Wrapper.getWorld().getLoadedEntityList().stream().filter(e -> e instanceof EntityLivingBase).map(e -> (EntityLivingBase)e).collect(Collectors.toList());
    }

    public static List<EntityLivingBase> getLivingEntities(Predicate<EntityLivingBase> validator) {
        ArrayList<EntityLivingBase> entities = new ArrayList<EntityLivingBase>();
        for (Entity entity : Wrapper.getWorld().loadedEntityList) {
            EntityLivingBase e;
            if (!(entity instanceof EntityLivingBase) || !validator.test(e = (EntityLivingBase)entity)) continue;
            entities.add(e);
        }
        return entities;
    }

    public static List<EntityPlayer> getLoadedPlayers() {
        return Wrapper.getWorld().playerEntities;
    }

    public static void forEachInventorySlot(int begin, int end, SlotConsumer consumer) {
        for (int i = begin; i < end; ++i) {
            ItemStack stack = Wrapper.getStackInSlot(i);
            if (stack == null) continue;
            consumer.accept(i, stack);
        }
    }

    public static void sendPacket(Packet<?> packet) {
        Wrapper.getNetHandler().sendPacket(packet);
    }

    public static void sendPacketDirect(Packet<?> packet) {
        Wrapper.getNetHandler().getNetworkManager().sendPacket(packet);
    }

    @FunctionalInterface
    public static interface SlotConsumer {
        public void accept(int var1, ItemStack var2);
    }
}

