package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.*;
import java.net.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import com.google.common.base.*;
import io.netty.handler.codec.base64.*;
import java.io.*;
import org.apache.commons.lang3.*;
import net.minecraft.client.renderer.texture.*;
import io.netty.buffer.*;
import java.awt.image.*;
import org.apache.logging.log4j.*;
import com.google.common.util.concurrent.*;
import java.util.concurrent.*;

public class ServerListEntryNormal implements GuiListExtended.IGuiListEntry
{
    private static final Logger logger;
    private static final ThreadPoolExecutor field_148302_b;
    private static final ResourceLocation field_178015_c;
    private static final ResourceLocation field_178014_d;
    private final GuiMultiplayer field_148303_c;
    private final Minecraft field_148300_d;
    private final ServerData field_148301_e;
    private final ResourceLocation field_148306_i;
    private String field_148299_g;
    private DynamicTexture field_148305_h;
    private long field_148298_f;
    
    protected ServerListEntryNormal(final GuiMultiplayer p_i45048_1_, final ServerData p_i45048_2_) {
        this.field_148303_c = p_i45048_1_;
        this.field_148301_e = p_i45048_2_;
        this.field_148300_d = Minecraft.getMinecraft();
        this.field_148306_i = new ResourceLocation("servers/" + p_i45048_2_.serverIP + "/icon");
        this.field_148305_h = (DynamicTexture)this.field_148300_d.getTextureManager().getTexture(this.field_148306_i);
    }
    
    @Override
    public void drawEntry(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight, final int mouseX, final int mouseY, final boolean isSelected) {
        if (!this.field_148301_e.field_78841_f) {
            this.field_148301_e.field_78841_f = true;
            this.field_148301_e.pingToServer = -2L;
            this.field_148301_e.serverMOTD = "";
            this.field_148301_e.populationInfo = "";
            ServerListEntryNormal.field_148302_b.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        ServerListEntryNormal.this.field_148303_c.getOldServerPinger().ping(ServerListEntryNormal.this.field_148301_e);
                    }
                    catch (UnknownHostException var2) {
                        ServerListEntryNormal.this.field_148301_e.pingToServer = -1L;
                        ServerListEntryNormal.this.field_148301_e.serverMOTD = EnumChatFormatting.DARK_RED + "Can't resolve hostname";
                    }
                    catch (Exception var3) {
                        ServerListEntryNormal.this.field_148301_e.pingToServer = -1L;
                        ServerListEntryNormal.this.field_148301_e.serverMOTD = EnumChatFormatting.DARK_RED + "Can't connect to server.";
                    }
                }
            });
        }
        final boolean var9 = this.field_148301_e.version > 47;
        final boolean var10 = this.field_148301_e.version < 47;
        final boolean var11 = var9 || var10;
        this.field_148300_d.fontRendererObj.drawString(this.field_148301_e.serverName, x + 32 + 3, y + 1, 16777215);
        final List var12 = this.field_148300_d.fontRendererObj.listFormattedStringToWidth(this.field_148301_e.serverMOTD, listWidth - 32 - 2);
        for (int var13 = 0; var13 < Math.min(var12.size(), 2); ++var13) {
            this.field_148300_d.fontRendererObj.drawString(var12.get(var13), x + 32 + 3, y + 12 + this.field_148300_d.fontRendererObj.FONT_HEIGHT * var13, 8421504);
        }
        final String var14 = var11 ? (EnumChatFormatting.DARK_RED + this.field_148301_e.gameVersion) : this.field_148301_e.populationInfo;
        final int var15 = this.field_148300_d.fontRendererObj.getStringWidth(var14);
        this.field_148300_d.fontRendererObj.drawString(var14, x + listWidth - var15 - 15 - 2, y + 1, 8421504);
        byte var16 = 0;
        String var17 = null;
        int var18;
        String var19;
        if (var11) {
            var18 = 5;
            var19 = (var9 ? "Tadpole out of date!" : "Server out of date!");
            var17 = this.field_148301_e.playerList;
        }
        else if (this.field_148301_e.field_78841_f && this.field_148301_e.pingToServer != -2L) {
            if (this.field_148301_e.pingToServer < 0L) {
                var18 = 5;
            }
            else if (this.field_148301_e.pingToServer < 150L) {
                var18 = 0;
            }
            else if (this.field_148301_e.pingToServer < 300L) {
                var18 = 1;
            }
            else if (this.field_148301_e.pingToServer < 600L) {
                var18 = 2;
            }
            else if (this.field_148301_e.pingToServer < 1000L) {
                var18 = 3;
            }
            else {
                var18 = 4;
            }
            if (this.field_148301_e.pingToServer < 0L) {
                var19 = "(no connection)";
            }
            else {
                var19 = this.field_148301_e.pingToServer + "ms";
                var17 = this.field_148301_e.playerList;
            }
        }
        else {
            var16 = 1;
            var18 = (int)(Minecraft.getSystemTime() / 100L + slotIndex * 2 & 0x7L);
            if (var18 > 4) {
                var18 = 8 - var18;
            }
            var19 = "Pinging...";
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_148300_d.getTextureManager().bindTexture(Gui.icons);
        Gui.drawModalRectWithCustomSizedTexture(x + listWidth - 15, y, (float)(var16 * 10), (float)(176 + var18 * 8), 10, 8, 256.0f, 256.0f);
        if (this.field_148301_e.getBase64EncodedIconData() != null && !this.field_148301_e.getBase64EncodedIconData().equals(this.field_148299_g)) {
            this.field_148299_g = this.field_148301_e.getBase64EncodedIconData();
            this.prepareServerIcon();
            this.field_148303_c.getServerList().saveServerList();
        }
        if (this.field_148305_h != null) {
            this.func_178012_a(x, y, this.field_148306_i);
        }
        else {
            this.func_178012_a(x, y, ServerListEntryNormal.field_178015_c);
        }
        final int var20 = mouseX - x;
        final int var21 = mouseY - y;
        if (var20 >= listWidth - 15 && var20 <= listWidth - 5 && var21 >= 0 && var21 <= 8) {
            this.field_148303_c.func_146793_a(var19);
        }
        else if (var20 >= listWidth - var15 - 15 - 2 && var20 <= listWidth - 15 - 2 && var21 >= 0 && var21 <= 8) {
            this.field_148303_c.func_146793_a(var17);
        }
        if (this.field_148300_d.gameSettings.touchscreen || isSelected) {
            this.field_148300_d.getTextureManager().bindTexture(ServerListEntryNormal.field_178014_d);
            Gui.drawRect(x, y, x + 32, y + 32, -1601138544);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            final int var22 = mouseX - x;
            final int var23 = mouseY - y;
            if (this.func_178013_b()) {
                if (var22 < 32 && var22 > 16) {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                }
                else {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                }
            }
            if (this.field_148303_c.func_175392_a(this, slotIndex)) {
                if (var22 < 16 && var23 < 16) {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                }
                else {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                }
            }
            if (this.field_148303_c.func_175394_b(this, slotIndex)) {
                if (var22 < 16 && var23 > 16) {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                }
                else {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                }
            }
        }
    }
    
    protected void func_178012_a(final int p_178012_1_, final int p_178012_2_, final ResourceLocation p_178012_3_) {
        this.field_148300_d.getTextureManager().bindTexture(p_178012_3_);
        GlStateManager.enableBlend();
        Gui.drawModalRectWithCustomSizedTexture(p_178012_1_, p_178012_2_, 0.0f, 0.0f, 32, 32, 32.0f, 32.0f);
        GlStateManager.disableBlend();
    }
    
    private boolean func_178013_b() {
        return true;
    }
    
    private void prepareServerIcon() {
        if (this.field_148301_e.getBase64EncodedIconData() == null) {
            this.field_148300_d.getTextureManager().deleteTexture(this.field_148306_i);
            this.field_148305_h = null;
        }
        else {
            final ByteBuf var2 = Unpooled.copiedBuffer((CharSequence)this.field_148301_e.getBase64EncodedIconData(), Charsets.UTF_8);
            final ByteBuf var3 = Base64.decode(var2);
            BufferedImage var4 = null;
            Label_0219: {
                try {
                    var4 = TextureUtil.func_177053_a((InputStream)new ByteBufInputStream(var3));
                    Validate.validState(var4.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
                    Validate.validState(var4.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
                    break Label_0219;
                }
                catch (Exception var5) {
                    ServerListEntryNormal.logger.error("Invalid icon for server " + this.field_148301_e.serverName + " (" + this.field_148301_e.serverIP + ")", (Throwable)var5);
                    this.field_148301_e.setBase64EncodedIconData(null);
                }
                finally {
                    var2.release();
                    var3.release();
                }
                return;
            }
            if (this.field_148305_h == null) {
                this.field_148305_h = new DynamicTexture(var4.getWidth(), var4.getHeight());
                this.field_148300_d.getTextureManager().loadTexture(this.field_148306_i, this.field_148305_h);
            }
            var4.getRGB(0, 0, var4.getWidth(), var4.getHeight(), this.field_148305_h.getTextureData(), 0, var4.getWidth());
            this.field_148305_h.updateDynamicTexture();
        }
    }
    
    @Override
    public boolean mousePressed(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_, final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
        if (p_148278_5_ <= 32) {
            if (p_148278_5_ < 32 && p_148278_5_ > 16 && this.func_178013_b()) {
                this.field_148303_c.selectServer(p_148278_1_);
                this.field_148303_c.connectToSelected();
                return true;
            }
            if (p_148278_5_ < 16 && p_148278_6_ < 16 && this.field_148303_c.func_175392_a(this, p_148278_1_)) {
                this.field_148303_c.func_175391_a(this, p_148278_1_, GuiScreen.isShiftKeyDown());
                return true;
            }
            if (p_148278_5_ < 16 && p_148278_6_ > 16 && this.field_148303_c.func_175394_b(this, p_148278_1_)) {
                this.field_148303_c.func_175393_b(this, p_148278_1_, GuiScreen.isShiftKeyDown());
                return true;
            }
        }
        this.field_148303_c.selectServer(p_148278_1_);
        if (Minecraft.getSystemTime() - this.field_148298_f < 250L) {
            this.field_148303_c.connectToSelected();
        }
        this.field_148298_f = Minecraft.getSystemTime();
        return false;
    }
    
    @Override
    public void setSelected(final int p_178011_1_, final int p_178011_2_, final int p_178011_3_) {
    }
    
    @Override
    public void mouseReleased(final int slotIndex, final int x, final int y, final int mouseEvent, final int relativeX, final int relativeY) {
    }
    
    public ServerData getServerData() {
        return this.field_148301_e;
    }
    
    static {
        logger = LogManager.getLogger();
        field_148302_b = new ScheduledThreadPoolExecutor(5, new ThreadFactoryBuilder().setNameFormat("Server Pinger #%d").setDaemon(true).build());
        field_178015_c = new ResourceLocation("textures/misc/unknown_server.png");
        field_178014_d = new ResourceLocation("textures/gui/server_selection.png");
    }
}
