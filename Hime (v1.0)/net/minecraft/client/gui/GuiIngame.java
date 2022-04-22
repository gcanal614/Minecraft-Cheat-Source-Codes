package net.minecraft.client.gui;

import java.awt.Color;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import me.Hime;
import me.notification.NotificationManager;
import org.lwjgl.opengl.GL11;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.realmsclient.gui.ChatFormatting;

import me.event.EventC;
import me.event.impl.EventRenderHUD;
import me.event.impl.EventRenderScoreboard;
import me.settings.Setting;
import me.util.ColorUtil;
import me.util.RainbowUtil;
import me.util.RenderUtil;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.border.WorldBorder;
import net.optifine.CustomColors;

public class GuiIngame extends Gui
{
    private static final ResourceLocation vignetteTexPath = new ResourceLocation("textures/misc/vignette.png");
    private static final ResourceLocation widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
    private static final ResourceLocation pumpkinBlurTexPath = new ResourceLocation("textures/misc/pumpkinblur.png");
    private final Random rand = new Random();
    private final Minecraft mc;
    private final RenderItem itemRenderer;

    /** ChatGUI instance that retains all previous chat data */
    private final GuiNewChat persistantChatGUI;
    private final GuiStreamIndicator streamIndicator;
    private int updateCounter;

    /** The string specifying which record music is playing */
    private String recordPlaying = "";

    /** How many ticks the record playing message will be displayed */
    private int recordPlayingUpFor;
    private boolean recordIsPlaying;

    /** Previous frame vignette brightness (slowly changes by 1% each frame) */
    public float prevVignetteBrightness = 1.0F;

    /** Remaining ticks the item highlight should be visible */
    private int remainingHighlightTicks;

    /** The ItemStack that is currently being highlighted */
    private ItemStack highlightingItemStack;
    private final GuiOverlayDebug overlayDebug;

    /** The spectator GUI for this in-game GUI instance */
    private final GuiSpectator spectatorGui;
    private final GuiPlayerTabOverlay overlayPlayerList;
    private int field_175195_w;
    private String field_175201_x = "";
    private String field_175200_y = "";
    private int field_175199_z;
    private int field_175192_A;
    private int field_175193_B;
    private int playerHealth = 0;
    private int lastPlayerHealth = 0;
    private float lastHealth = 0.0F;

    /** The last recorded system time */
    private long lastSystemTime = 0L;

    /** Used with updateCounter to make the heart bar flash */
    private long healthUpdateCounter = 0L;

    public GuiIngame(Minecraft mcIn)
    {
        this.mc = mcIn;
        this.itemRenderer = mcIn.getRenderItem();
        this.overlayDebug = new GuiOverlayDebug(mcIn);
        this.spectatorGui = new GuiSpectator(mcIn);
        this.persistantChatGUI = new GuiNewChat(mcIn);
        this.streamIndicator = new GuiStreamIndicator(mcIn);
        this.overlayPlayerList = new GuiPlayerTabOverlay(mcIn, this);
        this.func_175177_a();
    }

    public void func_175177_a()
    {
        this.field_175199_z = 10;
        this.field_175192_A = 70;
        this.field_175193_B = 20;
    }

    public void renderGameOverlay(float partialTicks)
    {
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        this.mc.entityRenderer.setupOverlayRendering();
        GlStateManager.enableBlend();

        if (Config.isVignetteEnabled())
        {
            this.renderVignette(this.mc.thePlayer.getBrightness(partialTicks), scaledresolution);
        }
        else
        {
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        }

        ItemStack itemstack = this.mc.thePlayer.inventory.armorItemInSlot(3);

        if (this.mc.gameSettings.thirdPersonView == 0 && itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.pumpkin))
        {
            this.renderPumpkinOverlay(scaledresolution);
        }

        if (!this.mc.thePlayer.isPotionActive(Potion.confusion))
        {
            float f = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * partialTicks;

            if (f > 0.0F)
            {
                this.func_180474_b(f, scaledresolution);
            }
        }

        if (this.mc.playerController.isSpectator())
        {
            this.spectatorGui.renderTooltip(scaledresolution, partialTicks);
        }
        else
        {
            this.renderTooltip(scaledresolution, partialTicks);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(icons);
        GlStateManager.enableBlend();

        if (this.showCrosshair())
        {
            GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
            GlStateManager.enableAlpha();
            this.drawTexturedModalRect(i / 2 - 7, j / 2 - 7, 0, 0, 16, 16);
        }

        GlStateManager.enableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        this.mc.mcProfiler.startSection("bossHealth");
        this.renderBossHealth();
        this.mc.mcProfiler.endSection();

        if (this.mc.playerController.shouldDrawHUD())
        {
            this.renderPlayerStats(scaledresolution);
        }

        GlStateManager.disableBlend();

        if (this.mc.thePlayer.getSleepTimer() > 0)
        {
            this.mc.mcProfiler.startSection("sleep");
            GlStateManager.disableDepth();
            GlStateManager.disableAlpha();
            int j1 = this.mc.thePlayer.getSleepTimer();
            float f1 = (float)j1 / 100.0F;

            if (f1 > 1.0F)
            {
                f1 = 1.0F - (float)(j1 - 100) / 10.0F;
            }

            int k = (int)(220.0F * f1) << 24 | 1052704;
            drawRect(0, 0, i, j, k);
            GlStateManager.enableAlpha();
            GlStateManager.enableDepth();
            this.mc.mcProfiler.endSection();
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int k1 = i / 2 - 91;

        if (this.mc.thePlayer.isRidingHorse())
        {
            this.renderHorseJumpBar(scaledresolution, k1);
        }
        else if (this.mc.playerController.gameIsSurvivalOrAdventure())
        {
            this.renderExpBar(scaledresolution, k1);
        }

        if (this.mc.gameSettings.heldItemTooltips && !this.mc.playerController.isSpectator())
        {
            this.func_181551_a(scaledresolution);
        }
        else if (this.mc.thePlayer.isSpectator())
        {
            this.spectatorGui.func_175263_a(scaledresolution);
        }

        if (this.mc.isDemo())
        {
            this.renderDemo(scaledresolution);
        }

        if (this.mc.gameSettings.showDebugInfo)
        {
            this.overlayDebug.renderDebugInfo(scaledresolution);
        }

        if (this.recordPlayingUpFor > 0)
        {
            this.mc.mcProfiler.startSection("overlayMessage");
            float f2 = (float)this.recordPlayingUpFor - partialTicks;
            int l1 = (int)(f2 * 255.0F / 20.0F);

            if (l1 > 255)
            {
                l1 = 255;
            }

            if (l1 > 8)
            {
                GlStateManager.pushMatrix();
                GlStateManager.translate((float)(i / 2), (float)(j - 68), 0.0F);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                int l = 16777215;

                if (this.recordIsPlaying)
                {
                    l = MathHelper.func_181758_c(f2 / 50.0F, 0.7F, 0.6F) & 16777215;
                }

                this.getFontRenderer().drawString(this.recordPlaying, -this.getFontRenderer().getStringWidth(this.recordPlaying) / 2, -4, l + (l1 << 24 & -16777216));
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }

            this.mc.mcProfiler.endSection();
        }

        if (this.field_175195_w > 0)
        {
            this.mc.mcProfiler.startSection("titleAndSubtitle");
            float f3 = (float)this.field_175195_w - partialTicks;
            int i2 = 255;

            if (this.field_175195_w > this.field_175193_B + this.field_175192_A)
            {
                float f4 = (float)(this.field_175199_z + this.field_175192_A + this.field_175193_B) - f3;
                i2 = (int)(f4 * 255.0F / (float)this.field_175199_z);
            }

            if (this.field_175195_w <= this.field_175193_B)
            {
                i2 = (int)(f3 * 255.0F / (float)this.field_175193_B);
            }

            i2 = MathHelper.clamp_int(i2, 0, 255);

            if (i2 > 8)
            {
                GlStateManager.pushMatrix();
                GlStateManager.translate((float)(i / 2), (float)(j / 2), 0.0F);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.pushMatrix();
                GlStateManager.scale(4.0F, 4.0F, 4.0F);
                int j2 = i2 << 24 & -16777216;
                this.getFontRenderer().drawString(this.field_175201_x, (float)(-this.getFontRenderer().getStringWidth(this.field_175201_x) / 2), -10.0F, 16777215 | j2, true);
                GlStateManager.popMatrix();
                GlStateManager.pushMatrix();
                GlStateManager.scale(2.0F, 2.0F, 2.0F);
                this.getFontRenderer().drawString(this.field_175200_y, (float)(-this.getFontRenderer().getStringWidth(this.field_175200_y) / 2), 5.0F, 16777215 | j2, true);
                GlStateManager.popMatrix();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }

            this.mc.mcProfiler.endSection();
        }

        Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
        ScoreObjective scoreobjective = null;
        ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(this.mc.thePlayer.getCommandSenderName());

        if (scoreplayerteam != null)
        {
            int i1 = scoreplayerteam.getChatFormat().getColorIndex();

            if (i1 >= 0)
            {
                scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + i1);
            }
        }

        ScoreObjective scoreobjective1 = scoreobjective != null ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1);

        if (scoreobjective1 != null)
        {
        	
        	 EventRenderScoreboard event = new EventRenderScoreboard();
             EventC.dispatch(event);
             
            if(!Hime.instance.settingsManager.getSettingByName("Remove ScoreBoard").getValBoolean()) {
             renderScoreboard(scoreobjective1, scaledresolution);
            }
            
            EventRenderScoreboard event2 = new EventRenderScoreboard(true);
            EventC.dispatch(event2);
        }

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableAlpha();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, (float)(j - 48), 0.0F);
        this.mc.mcProfiler.startSection("chat");
        this.persistantChatGUI.drawChat(this.updateCounter);
        this.mc.mcProfiler.endSection();
        GlStateManager.popMatrix();
        scoreobjective1 = scoreboard.getObjectiveInDisplaySlot(0);

        if (this.mc.gameSettings.keyBindPlayerList.isKeyDown() && (!this.mc.isIntegratedServerRunning() || this.mc.thePlayer.sendQueue.getPlayerInfoMap().size() > 1 || scoreobjective1 != null))
        {
            this.overlayPlayerList.updatePlayerList(true);
            this.overlayPlayerList.renderPlayerlist(i, scoreboard, scoreobjective1);
        }
        else
        {
            this.overlayPlayerList.updatePlayerList(false);
        }
        
      //  NotificationManager.render();
        if(Hime.instance.moduleManager.getModule("Notifications").isToggled()) {
         NotificationManager.render();
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
        GlStateManager.enableAlpha();
        
        EventRenderHUD event = new EventRenderHUD(scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight(), partialTicks);
        EventC.dispatch(event);
        
      //TODO mc.thePlayerhud guichat
        if(this.mc.currentScreen instanceof GuiChat) {
    	Setting hudmode = Hime.instance.settingsManager.getSettingByName("TargetHud Mode");
    	  if(hudmode.getValString().equalsIgnoreCase("Informatic")) {
    			   GlStateManager.pushMatrix();
    			   float width = (float)(event.getWidth() / 2.0D + 100.0D);
    		        float height = (float)(event.getHeight() / 2.0D);
    		        
    		        Gui.drawRect((width - 70.0F), (height + 30.0F), (width + 80.0F), (height + 105.0F), (new Color(0, 0, 0, 180)).getRGB());

    			
    			
    		        float health = mc.thePlayer.getHealth();
    		        float healthPercentage = health / mc.thePlayer.getMaxHealth();
    		        float targetHealthPercentage = 0.0F;
    		        if (healthPercentage != this.lastHealth) {
    		          float diff = healthPercentage - this.lastHealth;
    		          targetHealthPercentage = this.lastHealth;
    		          this.lastHealth += diff / 8.0F;
    		        } 
    		        Color healthcolor = Color.WHITE;
    		        if (healthPercentage * 100.0F > 75.0F) {
    		          healthcolor = Color.GREEN;
    		        } else if (healthPercentage * 100.0F > 50.0F && healthPercentage * 100.0F < 75.0F) {
    		          healthcolor = Color.YELLOW;
    		        } else if (healthPercentage * 100.0F < 50.0F && healthPercentage * 100.0F > 25.0F) {
    		          healthcolor = Color.ORANGE;
    		        } else if (healthPercentage * 100.0F < 25.0F) {
    		          healthcolor = Color.RED;
    		        } 

    			   Gui.drawRect((width - 70.0F), (height + 104.0F), (width - 70.0F + 149.0F * targetHealthPercentage), (height + 106.0F), healthcolor.getRGB());
    		        Gui.drawRect((width - 70.0F), (height + 104.0F), (width - 70.0F + 149.0F * healthPercentage), (height + 106.0F), Color.GREEN.getRGB());
    			
    			
    			
    		
    		     Hime.instance.rfr.drawString(((EntityPlayer) mc.thePlayer).getName(), (int) (width - 30), (int) (height + 40), -1);
    		    
    		String onGround = "";
    		
    		if(mc.thePlayer.onGround) {
    			onGround = "§2onGround";
    		}else {
    			onGround = "§4offGround";
    		}
    		// 	fr.drawString(String.format("%.2f%%",  playerInfo.responseTime, 360, 187, -1);
    	        // Hime.instance.rfr.drawString(onGround, 410, 150, -1);
    	         Hime.instance.rfrs.drawString("Distance: " + Math.round(mc.thePlayer.getDistanceToEntity(mc.thePlayer)), (int) (width + 15), (int) (height + 62), -1);
    		     Hime.instance.rfrs.drawString("Health: "+ Math.round(mc.thePlayer.getHealth()), (int) (width - 30), (int) (height + 62), -1);
    		    if(mc.thePlayer.getHealth() >= mc.thePlayer.getHealth()) {
    		    	//Hime.instance.rfr.drawString("§2You may win", 350, 170, 0xfc0303);
    	           }else if(mc.thePlayer.getHealth() < mc.thePlayer.getHealth()) {
    		       // Hime.instance.rfr.drawString("You may lose", 350, 170, 0xfc0303);
    		      }
    		    
    			    for(int i2 = 3; i2 > -1; i2--) {
    			    	ItemStack itemstack2 = ((EntityPlayer) mc.thePlayer).inventory.armorInventory[i2];
    			    	int yAdd =  i2 * 15;
    			    	GL11.glPushMatrix();
    					RenderHelper.enableGUIStandardItemLighting();
    					mc.getRenderItem().renderItemAndEffectIntoGUI(itemstack2, (int)(width + 15) - yAdd, (int) (height + 80));
    					GL11.glPopMatrix();
    			    }
    				GL11.glPushMatrix();
    				RenderHelper.enableGUIStandardItemLighting();
    			    mc.getRenderItem().renderItemAndEffectIntoGUI(((EntityPlayer) mc.thePlayer).getCurrentEquippedItem(), (int)(int) (width + 40),  (int) (height + 80));
    				GL11.glPopMatrix();
    		    
    		     GlStateManager.popMatrix();
    	         GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    			GuiInventory.drawEntityOnScreen((int) (width - 50), (int) (height + 95), 28, 25, 25, mc.thePlayer);
    	  }if(hudmode.getValString().equalsIgnoreCase("NovolineOld")) {
    		  FontRenderer fr = mc.fontRendererObj;
    			   GlStateManager.pushMatrix();
    			   float width = (float)(event.getWidth() / 2.0D + 100.0D);
    		        float height = (float)(event.getHeight() / 2.0D);
    		        Gui.drawRect((width - 70.0F), (height + 50.0F), (width + 60.0F), (height + 105.0F), (new Color(0, 0, 0, 180)).getRGB());

    			
    		        float health = mc.thePlayer.getHealth();
    		        float healthPercentage = health / mc.thePlayer.getMaxHealth();
    		        float targetHealthPercentage = 0.0F;
    		        if (healthPercentage != this.lastHealth) {
    		          float diff = healthPercentage - this.lastHealth;
    		          targetHealthPercentage = this.lastHealth;
    		          this.lastHealth += diff / 8.0F;
    		        } 
    		        Color healthcolor = Color.WHITE;
    		        if (healthPercentage * 100.0F > 75.0F) {
    		          healthcolor = Color.GREEN;
    		        } else if (healthPercentage * 100.0F > 50.0F && healthPercentage * 100.0F < 75.0F) {
    		          healthcolor = Color.YELLOW;
    		        } else if (healthPercentage * 100.0F < 50.0F && healthPercentage * 100.0F > 25.0F) {
    		          healthcolor = Color.ORANGE;
    		        } else if (healthPercentage * 100.0F < 25.0F) {
    		          healthcolor = Color.RED;
    		        } 

    			   Gui.drawRect((width - 70.0F), (height + 104.0F), (width - 70.0F + 130.0F * targetHealthPercentage), (height + 106.0F), healthcolor.getRGB());
    		       // Gui.drawRect((width - 70.0F), (height + 104.0F), (width - 70.0F + 149.0F * healthPercentage), (height + 106.0F), Color.GREEN.getRGB());
    			
    			  
    			
    		
    		       fr.drawString(((EntityPlayer) mc.thePlayer).getName(), (int) (width - 30), (int) (height + 60), -1);

    			    for(int i2 = 3; i2 > -1; i2--) {
    			    	ItemStack itemstack2 = ((EntityPlayer) mc.thePlayer).inventory.armorInventory[i2];
    			    	int yAdd =  i2 * 15;
    			    	GL11.glPushMatrix();
    					RenderHelper.enableGUIStandardItemLighting();
    					mc.getRenderItem().renderItemAndEffectIntoGUI(itemstack2, (int)(width + 10) - yAdd, (int) (height + 80));
    					GL11.glPopMatrix();
    			    }
    				GL11.glPushMatrix();
    				RenderHelper.enableGUIStandardItemLighting();
    			    mc.getRenderItem().renderItemAndEffectIntoGUI(((EntityPlayer) mc.thePlayer).getCurrentEquippedItem(), (int)(int) (width + 34),  (int) (height + 80));
    				GL11.glPopMatrix();
    		    
    		     GlStateManager.popMatrix();
    	        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    			GuiInventory.drawEntityOnScreen((int) (width - 50), (int) (height + 95), 20, 25, 25, mc.thePlayer);
    	  }
    	  if(hudmode.getValString().equalsIgnoreCase("MichealXF")) {
    		  FontRenderer fr = mc.fontRendererObj;
    			   GlStateManager.pushMatrix();
    			   float width = (float)(event.getWidth() / 2.0D + 100.0D);
    		        float height = (float)(event.getHeight() / 2.0D);
    		        
    		        Gui.drawRect((width - 70.0F), (height + 55.0F), (width + 94.0F), (height + 106.0F), (new Color(22, 22, 22, 255)).getRGB());

    			
    		        float health = mc.thePlayer.getHealth();
    		        float healthPercentage = health / mc.thePlayer.getMaxHealth();
    		        float targetHealthPercentage = 0.0F;
    		        if (healthPercentage != this.lastHealth) {
    		          float diff = healthPercentage - this.lastHealth;
    		          targetHealthPercentage = this.lastHealth;
    		          this.lastHealth += diff / 8.0F;
    		        } 
    		        Color healthcolor = Color.WHITE;
    		        if (healthPercentage * 100.0F > 75.0F) {
    		          healthcolor = Color.GREEN;
    		        } else if (healthPercentage * 100.0F > 50.0F && healthPercentage * 100.0F < 75.0F) {
    		          healthcolor = Color.YELLOW;
    		        } else if (healthPercentage * 100.0F < 50.0F && healthPercentage * 100.0F > 25.0F) {
    		          healthcolor = Color.ORANGE;
    		        } else if (healthPercentage * 100.0F < 25.0F) {
    		          healthcolor = Color.RED;
    		        } 

    		  //  	RenderUtil.instance.draw2DImage(new ResourceLocation("client/hue.png"),(int)(width - 70.0F),(int)(height + 103.0F), (int)(width - 70.0F + 160.0F * targetHealthPercentage), 8, Color.WHITE);
    			   RenderUtil.drawGradientSideways((width - 70.0F), (height + 103.0F), (width - 70.0F + 164.0F * targetHealthPercentage), (height + 106.0F), Color.RED.getRGB(), Color.GREEN.getRGB());
    		       // Gui.drawRect((width - 70.0F), (height + 104.0F), (width - 70.0F + 149.0F * healthPercentage), (height + 106.0F), Color.GREEN.getRGB());
    			
    			
    			
    		
    		       fr.drawString(((EntityPlayer) mc.thePlayer).getName(), (int) (width - 60), (int) (height + 63), -1);
    		       String xd = ((EntityPlayer) mc.thePlayer).getCurrentEquippedItem() == null ? "Nothing" : ((EntityPlayer) mc.thePlayer).getCurrentEquippedItem().getDisplayName().toString();
    		       fr.drawString(ChatFormatting.GRAY+ xd, (int) (width + 15), (int) (height + 63), Color.GRAY.getRGB());

    		       int x = (int) (width - 90);
    		       int y =  (int) (height + 60);
    		       
    		       GL11.glPushMatrix();
    	           GlStateManager.translate(x,y,1);
    	           GL11.glScalef(1.8f,1.8f,1.8f);
    	           GlStateManager.translate(-x,-y,1);
    	           mc.fontRendererObj.drawStringWithShadow(Math.round(mc.thePlayer.getHealth() / 2.0f) + " HP", x + 16,y + 10, RainbowUtil.rainbow(300));
    	           GL11.glPopMatrix();
    		       
    		
    		    
    		     GlStateManager.popMatrix();
    		//	GuiInventory.drawEntityOnScreen((int) (width - 50), (int) (height + 95), 20, 25, 25, mc.thePlayer);
    	  }
    		  if(hudmode.getValString().equalsIgnoreCase("Simple")) {
    				   GlStateManager.pushMatrix();
    					
    				   float width = (float)(event.getWidth() / 2.0D + 100.0D);
    			        float height = (float)(event.getHeight() / 2.0D);
    			        
    			        Gui.drawRect((width - 70.0F), (height + 30.0F), (width + 80.0F), (height + 105.0F), (new Color(0, 0, 0, 180)).getRGB());
    					
    					
    				    float health = mc.thePlayer.getHealth();
    			        float healthPercentage = health / mc.thePlayer.getMaxHealth();
    			        float targetHealthPercentage = 0.0F;
    			        if (healthPercentage != this.lastHealth) {
    			          float diff = healthPercentage - this.lastHealth;
    			          targetHealthPercentage = this.lastHealth;
    			          this.lastHealth += diff / 8.0F;
    			        } 
    			        Color healthcolor = Color.WHITE;
    			        if (healthPercentage * 100.0F > 75.0F) {
    			          healthcolor = Color.GREEN;
    			        } else if (healthPercentage * 100.0F > 50.0F && healthPercentage * 100.0F < 75.0F) {
    			          healthcolor = Color.YELLOW;
    			        } else if (healthPercentage * 100.0F < 50.0F && healthPercentage * 100.0F > 25.0F) {
    			          healthcolor = Color.ORANGE;
    			        } else if (healthPercentage * 100.0F < 25.0F) {
    			          healthcolor = Color.RED;
    			        } 

    				   Gui.drawRect((width - 70.0F), (height + 104.0F), (width - 70.0F + 149.0F * targetHealthPercentage), (height + 106.0F), healthcolor.getRGB());
    			        Gui.drawRect((width - 70.0F), (height + 104.0F), (width - 70.0F + 149.0F * healthPercentage), (height + 106.0F), Color.GREEN.getRGB());

    				     Hime.instance.rfr.drawString(((EntityPlayer) mc.thePlayer).getName(), (int) (width - 30), (int) (height + 40), -1);
    				     Hime.instance.rfrs.drawString("Health: "+ Math.round(mc.thePlayer.getHealth()), (int) (width - 30), (int) (height + 62), -1);
    				    
    				     GlStateManager.popMatrix();
    			         GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    					GuiInventory.drawEntityOnScreen((int) (width - 50), (int) (height + 95), 28, 25, 25, mc.thePlayer);
    		  }
    		  if(hudmode.getValString().equalsIgnoreCase("Blocky")) {
    				   GlStateManager.pushMatrix();
    					
    				   float width = (float)(event.getWidth() / 2.0D + 100.0D);
    			        float height = (float)(event.getHeight() / 2.0D);
    			        
    			        Gui.drawRect((width - 62.0F), (height + 20.0F), (width + 72.0F), (height + 105.0F), (new Color(0, 0, 0, 110)).getRGB());
    					
    					
    				    float health = mc.thePlayer.getHealth();
    			        float healthPercentage = health / mc.thePlayer.getMaxHealth() + 0.29f;
    			        float targetHealthPercentage = 0.0F;
    			        if (healthPercentage != this.lastHealth) {
    			          float diff = healthPercentage - this.lastHealth;
    			          targetHealthPercentage = this.lastHealth;
    			          this.lastHealth += diff / 8.0F;
    			        } 
    			        Color healthcolor = Color.WHITE;
    			        if (healthPercentage * 100.0F > 95.0F) {
    			          healthcolor = Color.GREEN;
    			        } else if (healthPercentage * 100.0F > 80.0F && healthPercentage * 100.0F < 95.0F) {
    			          healthcolor = Color.YELLOW;
    			        } else if (healthPercentage * 100.0F < 80.0F && healthPercentage * 100.0F > 55.0F) {
    			          healthcolor = Color.ORANGE;
    			        } else if (healthPercentage * 100.0F < 55.0F) {
    			          healthcolor = Color.RED;
    			        } 

    				  // Gui.drawRect((width), (height + 104.0F), (width - 80.0F + 149.0F * targetHealthPercentage), (height + 106.0F), healthcolor.getRGB());
    			        Gui.drawRect((width - 42.0F), (height + 82.0F), (width - 70.0F + 129.0F), (height + 98.0F), Color.DARK_GRAY.darker().getRGB());
    			        Gui.drawRect((width - 40.0F), (height + 84.0F), (width - 70.0F + 99.0F * targetHealthPercentage), (height + 96.0F), healthcolor.getRGB());
    					
    					
    					
    				     //Hime.instance.rfr.drawString(mc.thePlayer.getName(), 360, 170, -1);
    				    
    			        final String ping = mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()) == null ? "0ms" : mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()).getResponseTime() + "ms";
    				     Hime.instance.rfr.drawString("Health: "+ Math.round(mc.thePlayer.getHealth()), (width - 25.0F), (int) (height + 28), -1);
    				     Hime.instance.rfr.drawString("Distance: "+ Math.round(mc.thePlayer.getDistanceToEntity(mc.thePlayer)), (width - 25.0F),  (int) (height + 48), -1);
    				     Hime.instance.rfr.drawString("Ping: " + ping, (width - 25.0F),  (int) (height + 68), -1);
    				     GlStateManager.popMatrix();
    	                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    	                    GuiInventory.drawEntityOnScreen((int) (width - 42.0F), (int) (height + 78), 25, 25, 30, mc.thePlayer);
    		  }
    		  if(hudmode.getValString().equalsIgnoreCase("OldAstolfo")) {
    				  FontRenderer fr = mc.fontRendererObj;
    				final String winStatus = String.format("%s", mc.thePlayer.getHealth() > mc.thePlayer.getHealth() ? "Losing" : "Winning");
    				final String ping = mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()) == null ? "0ms" : mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()).getResponseTime() + "ms";
    					 double hpPercentage = mc.thePlayer.getHealth() / mc.thePlayer.getMaxHealth();
    			            //float scaledWidth = eventRender2D.getScaledResolution().getScaledWidth();
    			            //float scaledHeight = eventRender2D.getScaledResolution().getScaledHeight();
    					    float scaledWidth = 775;
    		                float scaledHeight = 732;
    		                float health = mc.thePlayer.getHealth();
    				        float healthPercentage = health / mc.thePlayer.getMaxHealth() + 0.29f;
    				        float targetHealthPercentage = 0.0F;
    				        if (healthPercentage != this.lastHealth) {
    				          float diff = healthPercentage - this.lastHealth;
    				          targetHealthPercentage = this.lastHealth;
    				          this.lastHealth += diff / 8.0F;
    				        } 
    				        Color healthcolor = Color.WHITE;
    				        if (healthPercentage * 100.0F > 95.0F) {
    				          healthcolor = Color.GREEN;
    				        } else if (healthPercentage * 100.0F > 80.0F && healthPercentage * 100.0F < 95.0F) {
    				          healthcolor = Color.YELLOW;
    				        } else if (healthPercentage * 100.0F < 80.0F && healthPercentage * 100.0F > 55.0F) {
    				          healthcolor = Color.ORANGE;
    				        } else if (healthPercentage * 100.0F < 55.0F) {
    				          healthcolor = Color.RED;
    				        } 
    			            if (hpPercentage > 1)
    			                hpPercentage = 1;
    			            else if (hpPercentage < 0)
    			                hpPercentage = 0;
    			            float width = (float) (400 / 2.0D);
    			            Gui.drawRect(scaledWidth / 2 , scaledHeight / 3 - 10, scaledWidth / 2 + 40 + (mc.fontRendererObj.getStringWidth(mc.thePlayer.getName()) > 105 ? mc.fontRendererObj.getStringWidth(mc.thePlayer.getName()) - 10 : 105), scaledHeight / 3 + 32, 0x99000000);
    			            GuiInventory.drawEntityOnScreen((int) (scaledWidth / 2 + 17),  ((int)scaledHeight / 3 + 29), 17, 17, 15, mc.thePlayer);
    			            // RenderUtil.withTransparency(new Color(0, 0,0).getRGB(), 0.5f));
    			            fr.drawStringWithShadow(mc.thePlayer.getName(), ((float) scaledWidth / 2 + 40),  ((float)scaledHeight / 3 - 5), Color.WHITE.getRGB());
    			            fr.drawStringWithShadow(ping + "", ((float) scaledWidth / 2 + 40), ((float)scaledHeight / 3 + 20), Color.WHITE.getRGB());
    			          
    			            Gui.drawRect(scaledWidth / 2 + 40, scaledHeight / 3 + 4, scaledWidth / 2 + 40 + (70 * 1.25), scaledHeight / 3 + 16, new Color(0, 0, 0).getRGB());
    			            
    			            Gui.drawRect(scaledWidth / 2 + 40, scaledHeight / 3 + 4, scaledWidth / 2 + 40 + (hpPercentage * 1.25) * 70, scaledHeight / 3 + 16, healthcolor.getRGB());
    			            
    			            mc.fontRendererObj.drawStringWithShadow(String.format("%.1f", mc.thePlayer.getHealth()), scaledWidth / 2 + 40 + 36, scaledHeight / 3 + 5, healthcolor.getRGB()); 
    			            
    		  }
    		  if(hudmode.getValString().equalsIgnoreCase("Moon")) {
    			  FontRenderer fr = mc.fontRendererObj;
    				   GlStateManager.pushMatrix();
    					
    				   float width = (float)(event.getWidth() / 2.0D + 100.0D);
    			        float height = (float)(event.getHeight() / 2.0D);
    			        
    			        Gui.drawRect((width - 72.0F), (height + 35.0F), (width - 30.0F + 120), (height + 78.0F), (new Color(0, 0, 0, 95)).getRGB());
    					
    					
    				    float health = mc.thePlayer.getHealth();
    			        float healthPercentage = health / mc.thePlayer.getMaxHealth();
    			        float targetHealthPercentage = 0.0F;
    			        if (healthPercentage != this.lastHealth) {
    			          float diff = healthPercentage - this.lastHealth;
    			          targetHealthPercentage = this.lastHealth;
    			          this.lastHealth += diff / 8.0F;
    			        } 
    			        Color healthcolor = Color.WHITE;
    			        if (healthPercentage * 100.0F > 75.0F) {
    			          healthcolor = Color.GREEN;
    			        } else if (healthPercentage * 100.0F > 50.0F && healthPercentage * 100.0F < 75.0F) {
    			          healthcolor = Color.YELLOW;
    			        } else if (healthPercentage * 100.0F < 50.0F && healthPercentage * 100.0F > 25.0F) {
    			          healthcolor = Color.ORANGE;
    			        } else if (healthPercentage * 100.0F < 25.0F) {
    			          healthcolor = Color.RED;
    			        } 
    			        Gui.drawRect((width - 71.0F), (height + 68.0F), (width - 30.0F + 119.0F), (height + 71.0F), Color.BLACK.getRGB());
    				   Gui.drawRect((width - 70.0F), (height + 69.0F), (width + 88.0F * targetHealthPercentage), (height + 70.0F), healthcolor.getRGB());
    				   
    				    float armorValue = mc.thePlayer.getTotalArmorValue();
    	                double armorWidth = armorValue / 20D;
    	                
    	              //  Hime.addClientChatMessage(armorWidth + "");
    	                Gui.drawRect((width - 71.0F), (height + 73.0F), (width - 30+ 119.0F), (height + 76.0F), Color.BLACK.getRGB());
    	                Gui.drawRect((width - 70.0F), (height + 74.0F), (width - 70.0F + 158.0F * armorWidth), (height + 75.0F), Color.BLUE.getRGB());
    				   
    				     GuiButton.moon.drawStringWithShadow(((EntityPlayer) mc.thePlayer).getName(), (int) (width - 38), (int) (height + 35), -1);
    				     GuiButton.aaaa.drawStringWithShadow("Health: " + String.format("%.1f", mc.thePlayer.getHealth()), (int) (width - 38), (int) (height + 55), -1);
    				     
    			         try {
    			           this.drawFace((int) (width - 70), (int) (height + 37), 8, 8, 8, 8, 30, 30, 64, 64, (AbstractClientPlayer) this.mc.thePlayer);
    			         }catch(Exception e) {
    			        	 e.printStackTrace();
    			         }
    			           
    				     GlStateManager.popMatrix();
    		  }if(hudmode.getValString().equalsIgnoreCase("Astolfo")) {
    			  FontRenderer fr = mc.fontRendererObj;
    				   GlStateManager.pushMatrix();
    					
    				   float width = (float)(event.getWidth() / 2.0D + 100.0D);
    			        float height = (float)(event.getHeight() / 2.0D);
    			        
    			        Gui.drawRect((width - 70.0F), (height + 35.0F), (width + 88.0F), (height + 89.0F), (new Color(0, 0, 0, 190)).getRGB());
    					
    					
    				    float health = mc.thePlayer.getHealth();
    			        float healthPercentage = health / mc.thePlayer.getMaxHealth();
    			        float targetHealthPercentage = 0.0F;
    			        if (healthPercentage != this.lastHealth) {
    			          float diff = healthPercentage - this.lastHealth;
    			          targetHealthPercentage = this.lastHealth;
    			          this.lastHealth += diff / 8.0F;
    			        } 
    			        Color healthcolor = Color.WHITE;
    			        if (healthPercentage * 100.0F > 75.0F) {
    			          healthcolor = Color.GREEN;
    			        } else if (healthPercentage * 100.0F > 50.0F && healthPercentage * 100.0F < 75.0F) {
    			          healthcolor = Color.YELLOW;
    			        } else if (healthPercentage * 100.0F < 50.0F && healthPercentage * 100.0F > 25.0F) {
    			          healthcolor = Color.ORANGE;
    			        } else if (healthPercentage * 100.0F < 25.0F) {
    			          healthcolor = Color.RED;
    			        } 

    				  // Gui.drawRect((width - 70.0F), (height + 104.0F), (width - 70.0F + 149.0F * targetHealthPercentage), (height + 106.0F), healthcolor.getRGB());
    			        Gui.drawRect((width - 36.0F), (height + 78F), (width - 36.0F + 120.0), (height + 85.0F), ColorUtil.pulseBrightness(new Color(14, 60, 190), 2, 2).getRGB());
    			        if (!((healthPercentage * 100.0F) > 75.0F)) {
    			        Gui.drawRect((width - 36.0F), (height + 78F), (width - 36.0F + 126.0F * targetHealthPercentage), (height + 85.0F), ColorUtil.pulseBrightness(new Color(13, 108, 244), 2, 2).getRGB());
    			        Gui.drawRect((width - 36.0F), (height + 78F), (width - 36.0F + 120.0F * targetHealthPercentage), (height + 85.0F), ColorUtil.pulseBrightness(new Color(13, 108, 214), 2, 2).getRGB());
    			        }else {
    			        	   Gui.drawRect((width - 36.0F), (height + 78F), (width - 36.0F + 120.0F * targetHealthPercentage), (height + 85.0F), ColorUtil.pulseBrightness(new Color(13, 108, 214), 2, 2).getRGB());
    			        }
    			        
    			        GL11.glPushMatrix();
    	                 GL11.glScaled(1.1, 1.1, 1.1);
    				     fr.drawStringWithShadow(((EntityPlayer) mc.thePlayer).getName(), (int) (width - 78), (int) (height + 19), -1);
    				     GL11.glPopMatrix();
    				     
    				  //   GL11.glPushMatrix();
    	              //   GL11.glScaled(2.2, 2.2, 2.2);
    				  //   fr.drawStringWithShadow("" + Math.round(target.getHealth()), (int) (width - 287), (int) (height - 82), ColorUtil.pulseBrightness(new Color(13, 108, 214),2, 2).getRGB());
    				   //  GL11.glPopMatrix();
    				     
    				       int x = (int) (width - 64);
    				       int y =  (int) (height + 40);
    				       
    				       GL11.glPushMatrix();
    			           GlStateManager.translate(x,y,1);
    			           GL11.glScalef(2,2,2);
    			           GlStateManager.translate(-x,-y,1);
    			           mc.fontRendererObj.drawStringWithShadow(String.format("%.1f", mc.thePlayer.getHealth()/ 2.0f) + " \u2764", x + 13.5f,y + 7.5f,  ColorUtil.pulseBrightness(new Color(13, 108, 214),2, 2).getRGB());
    			           GL11.glPopMatrix();
    				     
    				  //   RenderUtil.instance.draw2DImage(new ResourceLocation("client/heart.png"), (int) (width - 54), (int) (height + 45), 120, 40, ColorUtil.pulseBrightness(new Color(13, 108, 214),2, 2));
    				     
    				     GlStateManager.popMatrix();
    			         GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    					GuiInventory.drawEntityOnScreen((int) (width - 53), (int) (height + 85), 24, 25, 25, mc.thePlayer);
    		  }if(hudmode.getValString().equalsIgnoreCase("Novoline")) {
    			  FontRenderer fr = mc.fontRendererObj;
    				   GlStateManager.pushMatrix();
    				   float width = (float)(event.getWidth() / 2.0D + 100.0D);
    			        float height = (float)(event.getHeight() / 2.0D);
    			        Gui.drawRect((width - 71.0F), (height + 51.0F), (width + 95.0F), (height + 93.0F), (new Color(44,38,40)).getRGB());

    				
    			        float health = mc.thePlayer.getHealth();
    			        float healthPercentage = health / mc.thePlayer.getMaxHealth();
    			        float targetHealthPercentage = 0.0F;
    			        if (healthPercentage != this.lastHealth) {
    			          float diff = healthPercentage - this.lastHealth;
    			          targetHealthPercentage = this.lastHealth;
    			          this.lastHealth += diff / 8.0F;
    			        } 
    			        Color healthcolor = Color.WHITE;
    			        if (healthPercentage * 100.0F > 75.0F) {
    			          healthcolor = Color.GREEN;
    			        } else if (healthPercentage * 100.0F > 50.0F && healthPercentage * 100.0F < 75.0F) {
    			          healthcolor = Color.YELLOW;
    			        } else if (healthPercentage * 100.0F < 50.0F && healthPercentage * 100.0F > 25.0F) {
    			          healthcolor = Color.ORANGE;
    			        } else if (healthPercentage * 100.0F < 25.0F) {
    			          healthcolor = Color.RED;
    			        } 
    			        
    					  
    					final float hue = (float) (ColorUtil.getClickGUIColor());
    			  		   //                           colorSaturation  colorBrightness 
    			  	    Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
    			  	    
    			  	  Color color2 = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) 0.8);
    			        
    			       Gui.drawRect((width - 28.0F), (height + 68.0F), (width - 28.0F + 114.0F), (height + 77.5F), new Color(29,31,29).getRGB());
    			      // if (!((healthPercentage * 100.0F) > 75.0F)) {
    			    	   Gui.drawRect((width - 28.0F), (height + 68.0F), (width - 28.0F + 114.0F * targetHealthPercentage), (height + 77.5F), color2.getRGB());//new Color(235,169,241).getRGB()
    			    	   Gui.drawRect((width - 28.0F), (height + 68.0F), (width - 28.0F + 114.0F * healthPercentage), (height + 77.5F), color.getRGB());  
    			       //}else {
    			     //  Gui.drawRect((width - 28.0F), (height + 68.0F), (width - 28.0F + 114.0F * targetHealthPercentage), (height + 77.5F), color.getRGB());
    			     //  }
    			       // Gui.drawRect((width - 70.0F), (height + 104.0F), (width - 70.0F + 149.0F * healthPercentage), (height + 106.0F), Color.GREEN.getRGB());
    				
    				  
    				
    			
    			       fr.drawString(((EntityPlayer) mc.thePlayer).getName(), (int) (width - 28), (int) (height + 55), -1);

    			       fr.drawString(ChatFormatting.WHITE + String.format("%.1f", mc.thePlayer.getHealth()/ 2.0f) + ChatFormatting.RESET + " \u2764", (int) (width - 28), (int) (height + 82), color.getRGB());
    			    
    			     GlStateManager.popMatrix();
    		        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    		        this.drawFace((int) (width - 70), (int) (height + 52), 8, 8, 8, 8, 40, 40, 64, 64, (AbstractClientPlayer) mc.thePlayer);
    		   }
        }
    }
    
    public void drawFace(double x, double y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight, AbstractClientPlayer target) {
		try {
			ResourceLocation skin = target.getLocationSkin();
			Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1, 1, 1, 1);
			Gui.drawScaledCustomSizeModalRect((int)x, (int)y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
			GL11.glDisable(GL11.GL_BLEND);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    protected void renderTooltip(ScaledResolution sr, float partialTicks)
    {
        if (this.mc.getRenderViewEntity() instanceof EntityPlayer)
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(widgetsTexPath);
            EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
            int i = sr.getScaledWidth() / 2;
            float f = this.zLevel;
            this.zLevel = -90.0F;
            //  this.drawRect(i - 91 - 1 + entityplayer.inventory.currentItem * 20, i - 30, i - 68 - 1 + entityplayer.inventory.currentItem * 20, i, new Color(0, 0, 0, 150).getRGB());
            if(Hime.instance.settingsManager.getSettingByName("Custom Hotbar").getValBoolean()) {
            	 this.drawTexturedModalRect(i - 91 - 1 + entityplayer.inventory.currentItem * 20, sr.getScaledHeight() - 22 - 1, 0, 22, 24, 22);
                 Gui.drawRect(sr.getScaledWidth(), sr.getScaledHeight() - 22, 0, sr.getScaledHeight(), new Color(0, 0, 0, 150).getRGB());
            }else {
            this.drawTexturedModalRect(i - 91, sr.getScaledHeight() - 22, 0, 0, 182, 22);
            this.drawTexturedModalRect(i - 91 - 1 + entityplayer.inventory.currentItem * 20, sr.getScaledHeight() - 22 - 1, 0, 22, 24, 22);
            }
            this.zLevel = f;
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderHelper.enableGUIStandardItemLighting();

            for (int j = 0; j < 9; ++j)
            {
                int k = sr.getScaledWidth() / 2 - 90 + j * 20 + 2;
                int l = sr.getScaledHeight() - 16 - 3;
                this.renderHotbarItem(j, k, l, partialTicks, entityplayer);
            }

            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }
    }

    public void renderHorseJumpBar(ScaledResolution p_175186_1_, int p_175186_2_)
    {
        this.mc.mcProfiler.startSection("jumpBar");
        this.mc.getTextureManager().bindTexture(Gui.icons);
        float f = this.mc.thePlayer.getHorseJumpPower();
        int i = 182;
        int j = (int)(f * (float)(i + 1));
        int k = p_175186_1_.getScaledHeight() - 32 + 3;
        this.drawTexturedModalRect(p_175186_2_, k, 0, 84, i, 5);

        if (j > 0)
        {
            this.drawTexturedModalRect(p_175186_2_, k, 0, 89, j, 5);
        }

        this.mc.mcProfiler.endSection();
    }

    public void renderExpBar(ScaledResolution p_175176_1_, int p_175176_2_)
    {
        this.mc.mcProfiler.startSection("expBar");
        this.mc.getTextureManager().bindTexture(Gui.icons);
        int i = this.mc.thePlayer.xpBarCap();

        if (i > 0)
        {
            int j = 182;
            int k = (int)(this.mc.thePlayer.experience * (float)(j + 1));
            int l = p_175176_1_.getScaledHeight() - 32 + 3;
            this.drawTexturedModalRect(p_175176_2_, l, 0, 64, j, 5);

            if (k > 0)
            {
                this.drawTexturedModalRect(p_175176_2_, l, 0, 69, k, 5);
            }
        }

        this.mc.mcProfiler.endSection();

        if (this.mc.thePlayer.experienceLevel > 0)
        {
            this.mc.mcProfiler.startSection("expLevel");
            int k1 = 8453920;

            if (Config.isCustomColors())
            {
                k1 = CustomColors.getExpBarTextColor(k1);
            }

            String s = "" + this.mc.thePlayer.experienceLevel;
            int l1 = (p_175176_1_.getScaledWidth() - this.getFontRenderer().getStringWidth(s)) / 2;
            int i1 = p_175176_1_.getScaledHeight() - 31 - 4;
            int j1 = 0;
            this.getFontRenderer().drawString(s, l1 + 1, i1, 0);
            this.getFontRenderer().drawString(s, l1 - 1, i1, 0);
            this.getFontRenderer().drawString(s, l1, i1 + 1, 0);
            this.getFontRenderer().drawString(s, l1, i1 - 1, 0);
            this.getFontRenderer().drawString(s, l1, i1, k1);
            this.mc.mcProfiler.endSection();
        }
    }

    public void func_181551_a(ScaledResolution p_181551_1_)
    {
        this.mc.mcProfiler.startSection("selectedItemName");

        if (this.remainingHighlightTicks > 0 && this.highlightingItemStack != null)
        {
            String s = this.highlightingItemStack.getDisplayName();

            if (this.highlightingItemStack.hasDisplayName())
            {
                s = EnumChatFormatting.ITALIC + s;
            }

            int i = (p_181551_1_.getScaledWidth() - this.getFontRenderer().getStringWidth(s)) / 2;
            int j = p_181551_1_.getScaledHeight() - 59;

            if (!this.mc.playerController.shouldDrawHUD())
            {
                j += 14;
            }

            int k = (int)((float)this.remainingHighlightTicks * 256.0F / 10.0F);

            if (k > 255)
            {
                k = 255;
            }

            if (k > 0)
            {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                this.getFontRenderer().drawStringWithShadow(s, (float)i, (float)j, 16777215 + (k << 24));
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        }

        this.mc.mcProfiler.endSection();
    }

    public void renderDemo(ScaledResolution p_175185_1_)
    {
        this.mc.mcProfiler.startSection("demo");
        String s = "";

        if (this.mc.theWorld.getTotalWorldTime() >= 120500L)
        {
            s = I18n.format("demo.demoExpired", new Object[0]);
        }
        else
        {
            s = I18n.format("demo.remainingTime", new Object[] {StringUtils.ticksToElapsedTime((int)(120500L - this.mc.theWorld.getTotalWorldTime()))});
        }

        int i = this.getFontRenderer().getStringWidth(s);
        this.getFontRenderer().drawStringWithShadow(s, (float)(p_175185_1_.getScaledWidth() - i - 10), 5.0F, 16777215);
        this.mc.mcProfiler.endSection();
    }

    protected boolean showCrosshair()
    {
    	if(Hime.instance.moduleManager.getModule("Crosshair").isToggled()) {
    		return false;
    	}
        if (this.mc.gameSettings.showDebugInfo && !this.mc.thePlayer.hasReducedDebug() && !this.mc.gameSettings.reducedDebugInfo)
        {
            return false;
        }
        else if (this.mc.playerController.isSpectator())
        {
            if (this.mc.pointedEntity != null)
            {
                return true;
            }
            else
            {
                if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
                {
                    BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();

                    if (this.mc.theWorld.getTileEntity(blockpos) instanceof IInventory)
                    {
                        return true;
                    }
                }

                return false;
            }
        }
        else
        {
            return true;
        }
    }

    public void renderStreamIndicator(ScaledResolution p_180478_1_)
    {
        this.streamIndicator.render(p_180478_1_.getScaledWidth() - 10, 10);
    }

    private void renderScoreboard(ScoreObjective p_180475_1_, ScaledResolution p_180475_2_)
    {
        Scoreboard scoreboard = p_180475_1_.getScoreboard();
        Collection<Score> collection = scoreboard.getSortedScores(p_180475_1_);
        List<Score> list = Lists.newArrayList(Iterables.filter(collection, new Predicate<Score>()
        {
            public boolean apply(Score p_apply_1_)
            {
                return p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#");
            }
        }));

        if (list.size() > 15)
        {
            collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
        }
        else
        {
            collection = list;
        }

        int i = this.getFontRenderer().getStringWidth(p_180475_1_.getDisplayName());

        for (Score score : collection)
        {
            ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
            String s = ScorePlayerTeam.formatPlayerName(scoreplayerteam, score.getPlayerName()) + ": " + EnumChatFormatting.RED + score.getScorePoints();
            i = Math.max(i, this.getFontRenderer().getStringWidth(s));
        }

        int i1 = collection.size() * this.getFontRenderer().FONT_HEIGHT;
        int j1 = p_180475_2_.getScaledHeight() / 2 + i1 / 3;
        int k1 = 3;
        int l1 = p_180475_2_.getScaledWidth() - i - k1;
        int j = 0;

        for (Score score1 : collection)
        {
            ++j;
            ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score1.getPlayerName());
            String s1 = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score1.getPlayerName());
            String s2 = EnumChatFormatting.RED + "" + score1.getScorePoints();
            int k = j1 - j * this.getFontRenderer().FONT_HEIGHT;
            int l = p_180475_2_.getScaledWidth() - k1 + 2;
            drawRect(l1 - 2, k, l, k + this.getFontRenderer().FONT_HEIGHT, 1342177280);
            this.getFontRenderer().drawString(s1, l1, k, 553648127);
            this.getFontRenderer().drawString(s2, l - this.getFontRenderer().getStringWidth(s2), k, 553648127);

            if (j == collection.size())
            {
                String s3 = p_180475_1_.getDisplayName();
                drawRect(l1 - 2, k - this.getFontRenderer().FONT_HEIGHT - 1, l, k - 1, 1610612736);
                drawRect(l1 - 2, k - 1, l, k, 1342177280);
                this.getFontRenderer().drawString(s3, l1 + i / 2 - this.getFontRenderer().getStringWidth(s3) / 2, k - this.getFontRenderer().FONT_HEIGHT, 553648127);
            }
        }
    }

    private void renderPlayerStats(ScaledResolution p_180477_1_)
    {
        if (this.mc.getRenderViewEntity() instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
            int i = MathHelper.ceiling_float_int(entityplayer.getHealth());
            boolean flag = this.healthUpdateCounter > (long)this.updateCounter && (this.healthUpdateCounter - (long)this.updateCounter) / 3L % 2L == 1L;

            if (i < this.playerHealth && entityplayer.hurtResistantTime > 0)
            {
                this.lastSystemTime = Minecraft.getSystemTime();
                this.healthUpdateCounter = (long)(this.updateCounter + 20);
            }
            else if (i > this.playerHealth && entityplayer.hurtResistantTime > 0)
            {
                this.lastSystemTime = Minecraft.getSystemTime();
                this.healthUpdateCounter = (long)(this.updateCounter + 10);
            }

            if (Minecraft.getSystemTime() - this.lastSystemTime > 1000L)
            {
                this.playerHealth = i;
                this.lastPlayerHealth = i;
                this.lastSystemTime = Minecraft.getSystemTime();
            }

            this.playerHealth = i;
            int j = this.lastPlayerHealth;
            this.rand.setSeed((long)(this.updateCounter * 312871));
            boolean flag1 = false;
            FoodStats foodstats = entityplayer.getFoodStats();
            int k = foodstats.getFoodLevel();
            int l = foodstats.getPrevFoodLevel();
            IAttributeInstance iattributeinstance = entityplayer.getEntityAttribute(SharedMonsterAttributes.maxHealth);
            int i1 = p_180477_1_.getScaledWidth() / 2 - 91;
            int j1 = p_180477_1_.getScaledWidth() / 2 + 91;
            int k1 = p_180477_1_.getScaledHeight() - 39;
            float f = (float)iattributeinstance.getAttributeValue();
            float f1 = entityplayer.getAbsorptionAmount();
            int l1 = MathHelper.ceiling_float_int((f + f1) / 2.0F / 10.0F);
            int i2 = Math.max(10 - (l1 - 2), 3);
            int j2 = k1 - (l1 - 1) * i2 - 10;
            float f2 = f1;
            int k2 = entityplayer.getTotalArmorValue();
            int l2 = -1;

            if (entityplayer.isPotionActive(Potion.regeneration))
            {
                l2 = this.updateCounter % MathHelper.ceiling_float_int(f + 5.0F);
            }

            this.mc.mcProfiler.startSection("armor");

            for (int i3 = 0; i3 < 10; ++i3)
            {
                if (k2 > 0)
                {
                    int j3 = i1 + i3 * 8;

                    if (i3 * 2 + 1 < k2)
                    {
                        this.drawTexturedModalRect(j3, j2, 34, 9, 9, 9);
                    }

                    if (i3 * 2 + 1 == k2)
                    {
                        this.drawTexturedModalRect(j3, j2, 25, 9, 9, 9);
                    }

                    if (i3 * 2 + 1 > k2)
                    {
                        this.drawTexturedModalRect(j3, j2, 16, 9, 9, 9);
                    }
                }
            }

            this.mc.mcProfiler.endStartSection("health");

            for (int i6 = MathHelper.ceiling_float_int((f + f1) / 2.0F) - 1; i6 >= 0; --i6)
            {
                int j6 = 16;

                if (entityplayer.isPotionActive(Potion.poison))
                {
                    j6 += 36;
                }
                else if (entityplayer.isPotionActive(Potion.wither))
                {
                    j6 += 72;
                }

                int k3 = 0;

                if (flag)
                {
                    k3 = 1;
                }

                int l3 = MathHelper.ceiling_float_int((float)(i6 + 1) / 10.0F) - 1;
                int i4 = i1 + i6 % 10 * 8;
                int j4 = k1 - l3 * i2;

                if (i <= 4)
                {
                    j4 += this.rand.nextInt(2);
                }

                if (i6 == l2)
                {
                    j4 -= 2;
                }

                int k4 = 0;

                if (entityplayer.worldObj.getWorldInfo().isHardcoreModeEnabled())
                {
                    k4 = 5;
                }

                this.drawTexturedModalRect(i4, j4, 16 + k3 * 9, 9 * k4, 9, 9);

                if (flag)
                {
                    if (i6 * 2 + 1 < j)
                    {
                        this.drawTexturedModalRect(i4, j4, j6 + 54, 9 * k4, 9, 9);
                    }

                    if (i6 * 2 + 1 == j)
                    {
                        this.drawTexturedModalRect(i4, j4, j6 + 63, 9 * k4, 9, 9);
                    }
                }

                if (f2 <= 0.0F)
                {
                    if (i6 * 2 + 1 < i)
                    {
                        this.drawTexturedModalRect(i4, j4, j6 + 36, 9 * k4, 9, 9);
                    }

                    if (i6 * 2 + 1 == i)
                    {
                        this.drawTexturedModalRect(i4, j4, j6 + 45, 9 * k4, 9, 9);
                    }
                }
                else
                {
                    if (f2 == f1 && f1 % 2.0F == 1.0F)
                    {
                        this.drawTexturedModalRect(i4, j4, j6 + 153, 9 * k4, 9, 9);
                    }
                    else
                    {
                        this.drawTexturedModalRect(i4, j4, j6 + 144, 9 * k4, 9, 9);
                    }

                    f2 -= 2.0F;
                }
            }

            Entity entity = entityplayer.ridingEntity;

            if (entity == null)
            {
                this.mc.mcProfiler.endStartSection("food");

                for (int k6 = 0; k6 < 10; ++k6)
                {
                    int j7 = k1;
                    int l7 = 16;
                    int k8 = 0;

                    if (entityplayer.isPotionActive(Potion.hunger))
                    {
                        l7 += 36;
                        k8 = 13;
                    }

                    if (entityplayer.getFoodStats().getSaturationLevel() <= 0.0F && this.updateCounter % (k * 3 + 1) == 0)
                    {
                        j7 = k1 + (this.rand.nextInt(3) - 1);
                    }

                    if (flag1)
                    {
                        k8 = 1;
                    }

                    int j9 = j1 - k6 * 8 - 9;
                    this.drawTexturedModalRect(j9, j7, 16 + k8 * 9, 27, 9, 9);

                    if (flag1)
                    {
                        if (k6 * 2 + 1 < l)
                        {
                            this.drawTexturedModalRect(j9, j7, l7 + 54, 27, 9, 9);
                        }

                        if (k6 * 2 + 1 == l)
                        {
                            this.drawTexturedModalRect(j9, j7, l7 + 63, 27, 9, 9);
                        }
                    }

                    if (k6 * 2 + 1 < k)
                    {
                        this.drawTexturedModalRect(j9, j7, l7 + 36, 27, 9, 9);
                    }

                    if (k6 * 2 + 1 == k)
                    {
                        this.drawTexturedModalRect(j9, j7, l7 + 45, 27, 9, 9);
                    }
                }
            }
            else if (entity instanceof EntityLivingBase)
            {
                this.mc.mcProfiler.endStartSection("mountHealth");
                EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                int i7 = (int)Math.ceil((double)entitylivingbase.getHealth());
                float f3 = entitylivingbase.getMaxHealth();
                int j8 = (int)(f3 + 0.5F) / 2;

                if (j8 > 30)
                {
                    j8 = 30;
                }

                int i9 = k1;

                for (int k9 = 0; j8 > 0; k9 += 20)
                {
                    int l4 = Math.min(j8, 10);
                    j8 -= l4;

                    for (int i5 = 0; i5 < l4; ++i5)
                    {
                        int j5 = 52;
                        int k5 = 0;

                        if (flag1)
                        {
                            k5 = 1;
                        }

                        int l5 = j1 - i5 * 8 - 9;
                        this.drawTexturedModalRect(l5, i9, j5 + k5 * 9, 9, 9, 9);

                        if (i5 * 2 + 1 + k9 < i7)
                        {
                            this.drawTexturedModalRect(l5, i9, j5 + 36, 9, 9, 9);
                        }

                        if (i5 * 2 + 1 + k9 == i7)
                        {
                            this.drawTexturedModalRect(l5, i9, j5 + 45, 9, 9, 9);
                        }
                    }

                    i9 -= 10;
                }
            }

            this.mc.mcProfiler.endStartSection("air");

            if (entityplayer.isInsideOfMaterial(Material.water))
            {
                int l6 = this.mc.thePlayer.getAir();
                int k7 = MathHelper.ceiling_double_int((double)(l6 - 2) * 10.0D / 300.0D);
                int i8 = MathHelper.ceiling_double_int((double)l6 * 10.0D / 300.0D) - k7;

                for (int l8 = 0; l8 < k7 + i8; ++l8)
                {
                    if (l8 < k7)
                    {
                        this.drawTexturedModalRect(j1 - l8 * 8 - 9, j2, 16, 18, 9, 9);
                    }
                    else
                    {
                        this.drawTexturedModalRect(j1 - l8 * 8 - 9, j2, 25, 18, 9, 9);
                    }
                }
            }

            this.mc.mcProfiler.endSection();
        }
    }

    /**
     * Renders dragon's (boss) health on the HUD
     */
    private void renderBossHealth()
    {
        if (BossStatus.bossName != null && BossStatus.statusBarTime > 0)
        {
            --BossStatus.statusBarTime;
            FontRenderer fontrenderer = this.mc.fontRendererObj;
            ScaledResolution scaledresolution = new ScaledResolution(this.mc);
            int i = scaledresolution.getScaledWidth();
            int j = 182;
            int k = i / 2 - j / 2;
            int l = (int)(BossStatus.healthScale * (float)(j + 1));
            int i1 = 12;
            this.drawTexturedModalRect(k, i1, 0, 74, j, 5);
            this.drawTexturedModalRect(k, i1, 0, 74, j, 5);

            if (l > 0)
            {
                this.drawTexturedModalRect(k, i1, 0, 79, l, 5);
            }

            String s = BossStatus.bossName;
            this.getFontRenderer().drawStringWithShadow(s, (float)(i / 2 - this.getFontRenderer().getStringWidth(s) / 2), (float)(i1 - 10), 16777215);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(icons);
        }
    }

    private void renderPumpkinOverlay(ScaledResolution p_180476_1_)
    {
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableAlpha();
        this.mc.getTextureManager().bindTexture(pumpkinBlurTexPath);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        worldrenderer.func_181662_b(0.0D, (double)p_180476_1_.getScaledHeight(), -90.0D).func_181673_a(0.0D, 1.0D).func_181675_d();
        worldrenderer.func_181662_b((double)p_180476_1_.getScaledWidth(), (double)p_180476_1_.getScaledHeight(), -90.0D).func_181673_a(1.0D, 1.0D).func_181675_d();
        worldrenderer.func_181662_b((double)p_180476_1_.getScaledWidth(), 0.0D, -90.0D).func_181673_a(1.0D, 0.0D).func_181675_d();
        worldrenderer.func_181662_b(0.0D, 0.0D, -90.0D).func_181673_a(0.0D, 0.0D).func_181675_d();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Renders a Vignette arount the entire screen that changes with light level.
     */
    private void renderVignette(float p_180480_1_, ScaledResolution p_180480_2_)
    {
        if (!Config.isVignetteEnabled())
        {
            GlStateManager.enableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        }
        else
        {
            p_180480_1_ = 1.0F - p_180480_1_;
            p_180480_1_ = MathHelper.clamp_float(p_180480_1_, 0.0F, 1.0F);
            WorldBorder worldborder = this.mc.theWorld.getWorldBorder();
            float f = (float)worldborder.getClosestDistance(this.mc.thePlayer);
            double d0 = Math.min(worldborder.getResizeSpeed() * (double)worldborder.getWarningTime() * 1000.0D, Math.abs(worldborder.getTargetSize() - worldborder.getDiameter()));
            double d1 = Math.max((double)worldborder.getWarningDistance(), d0);

            if ((double)f < d1)
            {
                f = 1.0F - (float)((double)f / d1);
            }
            else
            {
                f = 0.0F;
            }

            this.prevVignetteBrightness = (float)((double)this.prevVignetteBrightness + (double)(p_180480_1_ - this.prevVignetteBrightness) * 0.01D);
            GlStateManager.disableDepth();
            GlStateManager.depthMask(false);
            GlStateManager.tryBlendFuncSeparate(0, 769, 1, 0);

            if (f > 0.0F)
            {
                GlStateManager.color(0.0F, f, f, 1.0F);
            }
            else
            {
                GlStateManager.color(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0F);
            }

            this.mc.getTextureManager().bindTexture(vignetteTexPath);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
            worldrenderer.func_181662_b(0.0D, (double)p_180480_2_.getScaledHeight(), -90.0D).func_181673_a(0.0D, 1.0D).func_181675_d();
            worldrenderer.func_181662_b((double)p_180480_2_.getScaledWidth(), (double)p_180480_2_.getScaledHeight(), -90.0D).func_181673_a(1.0D, 1.0D).func_181675_d();
            worldrenderer.func_181662_b((double)p_180480_2_.getScaledWidth(), 0.0D, -90.0D).func_181673_a(1.0D, 0.0D).func_181675_d();
            worldrenderer.func_181662_b(0.0D, 0.0D, -90.0D).func_181673_a(0.0D, 0.0D).func_181675_d();
            tessellator.draw();
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        }
    }

    private void func_180474_b(float p_180474_1_, ScaledResolution p_180474_2_)
    {
        if (p_180474_1_ < 1.0F)
        {
            p_180474_1_ = p_180474_1_ * p_180474_1_;
            p_180474_1_ = p_180474_1_ * p_180474_1_;
            p_180474_1_ = p_180474_1_ * 0.8F + 0.2F;
        }

        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(1.0F, 1.0F, 1.0F, p_180474_1_);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        TextureAtlasSprite textureatlassprite = this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(Blocks.portal.getDefaultState());
        float f = textureatlassprite.getMinU();
        float f1 = textureatlassprite.getMinV();
        float f2 = textureatlassprite.getMaxU();
        float f3 = textureatlassprite.getMaxV();
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        worldrenderer.func_181662_b(0.0D, (double)p_180474_2_.getScaledHeight(), -90.0D).func_181673_a((double)f, (double)f3).func_181675_d();
        worldrenderer.func_181662_b((double)p_180474_2_.getScaledWidth(), (double)p_180474_2_.getScaledHeight(), -90.0D).func_181673_a((double)f2, (double)f3).func_181675_d();
        worldrenderer.func_181662_b((double)p_180474_2_.getScaledWidth(), 0.0D, -90.0D).func_181673_a((double)f2, (double)f1).func_181675_d();
        worldrenderer.func_181662_b(0.0D, 0.0D, -90.0D).func_181673_a((double)f, (double)f1).func_181675_d();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderHotbarItem(int index, int xPos, int yPos, float partialTicks, EntityPlayer p_175184_5_)
    {
        ItemStack itemstack = p_175184_5_.inventory.mainInventory[index];

        if (itemstack != null)
        {
            float f = (float)itemstack.animationsToGo - partialTicks;

            if (f > 0.0F)
            {
                GlStateManager.pushMatrix();
                float f1 = 1.0F + f / 5.0F;
                GlStateManager.translate((float)(xPos + 8), (float)(yPos + 12), 0.0F);
                GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                GlStateManager.translate((float)(-(xPos + 8)), (float)(-(yPos + 12)), 0.0F);
            }

            this.itemRenderer.renderItemAndEffectIntoGUI(itemstack, xPos, yPos);

            if (f > 0.0F)
            {
                GlStateManager.popMatrix();
            }

            this.itemRenderer.renderItemOverlays(this.mc.fontRendererObj, itemstack, xPos, yPos);
        }
    }

    /**
     * The update tick for the ingame UI
     */
    public void updateTick()
    {
        if (this.recordPlayingUpFor > 0)
        {
            --this.recordPlayingUpFor;
        }

        if (this.field_175195_w > 0)
        {
            --this.field_175195_w;

            if (this.field_175195_w <= 0)
            {
                this.field_175201_x = "";
                this.field_175200_y = "";
            }
        }

        ++this.updateCounter;
        this.streamIndicator.func_152439_a();

        if (this.mc.thePlayer != null)
        {
            ItemStack itemstack = this.mc.thePlayer.inventory.getCurrentItem();

            if (itemstack == null)
            {
                this.remainingHighlightTicks = 0;
            }
            else if (this.highlightingItemStack != null && itemstack.getItem() == this.highlightingItemStack.getItem() && ItemStack.areItemStackTagsEqual(itemstack, this.highlightingItemStack) && (itemstack.isItemStackDamageable() || itemstack.getMetadata() == this.highlightingItemStack.getMetadata()))
            {
                if (this.remainingHighlightTicks > 0)
                {
                    --this.remainingHighlightTicks;
                }
            }
            else
            {
                this.remainingHighlightTicks = 40;
            }

            this.highlightingItemStack = itemstack;
        }
    }

    public void setRecordPlayingMessage(String p_73833_1_)
    {
        this.setRecordPlaying(I18n.format("record.nowPlaying", new Object[] {p_73833_1_}), true);
    }

    public void setRecordPlaying(String p_110326_1_, boolean p_110326_2_)
    {
        this.recordPlaying = p_110326_1_;
        this.recordPlayingUpFor = 60;
        this.recordIsPlaying = p_110326_2_;
    }

    public void displayTitle(String p_175178_1_, String p_175178_2_, int p_175178_3_, int p_175178_4_, int p_175178_5_)
    {
        if (p_175178_1_ == null && p_175178_2_ == null && p_175178_3_ < 0 && p_175178_4_ < 0 && p_175178_5_ < 0)
        {
            this.field_175201_x = "";
            this.field_175200_y = "";
            this.field_175195_w = 0;
        }
        else if (p_175178_1_ != null)
        {
            this.field_175201_x = p_175178_1_;
            this.field_175195_w = this.field_175199_z + this.field_175192_A + this.field_175193_B;
        }
        else if (p_175178_2_ != null)
        {
            this.field_175200_y = p_175178_2_;
        }
        else
        {
            if (p_175178_3_ >= 0)
            {
                this.field_175199_z = p_175178_3_;
            }

            if (p_175178_4_ >= 0)
            {
                this.field_175192_A = p_175178_4_;
            }

            if (p_175178_5_ >= 0)
            {
                this.field_175193_B = p_175178_5_;
            }

            if (this.field_175195_w > 0)
            {
                this.field_175195_w = this.field_175199_z + this.field_175192_A + this.field_175193_B;
            }
        }
    }

    public void setRecordPlaying(IChatComponent p_175188_1_, boolean p_175188_2_)
    {
        this.setRecordPlaying(p_175188_1_.getUnformattedText(), p_175188_2_);
    }

    /**
     * returns a pointer to the persistant Chat GUI, containing all previous chat messages and such
     */
    public GuiNewChat getChatGUI()
    {
        return this.persistantChatGUI;
    }

    public int getUpdateCounter()
    {
        return this.updateCounter;
    }

    public FontRenderer getFontRenderer()
    {
        return this.mc.fontRendererObj;
    }

    public GuiSpectator getSpectatorGui()
    {
        return this.spectatorGui;
    }

    public GuiPlayerTabOverlay getTabList()
    {
        return this.overlayPlayerList;
    }

    public void func_181029_i()
    {
        this.overlayPlayerList.func_181030_a();
    }
}
