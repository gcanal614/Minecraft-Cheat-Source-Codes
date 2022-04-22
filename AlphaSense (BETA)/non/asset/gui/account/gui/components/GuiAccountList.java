package non.asset.gui.account.gui.components;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glTranslated;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import non.asset.Clarinet;
import non.asset.gui.account.gui.GuiAltManager;
import non.asset.gui.account.system.Account;
import non.asset.gui.login.AltLoginThread;

public class GuiAccountList extends GuiSlot {

    public int selected = -1;
    private GuiAltManager parent;


    public float shadowuwu = 100;
    
    private double smoothY = 0;
    
    private double x = 0;
    private double y = 0;

    private int valuetest = 0;
    private int valuetest2 = 0;
    public GuiAccountList(GuiAltManager parent){
        super(Minecraft.getMinecraft(), parent.width, parent.height, 36,
                parent.height - 56, 40);

        this.parent = parent;
    }

    @Override
    public int getSize(){
        return Clarinet.INSTANCE.getAccountManager().getAccounts().size();
    }

    @Override
    public void elementClicked(int i, boolean b, int i1, int i2){
        selected = i;

        if(b){

            if(parent.accountList.getSelectedAccount().getPassword().length() > 0 ) {
        		parent.login(getAccount(i));
        	}else {
        		parent.offl = new AltLoginThread(parent.accountList.getSelectedAccount().getName(), "");
        		parent.offl.start();
        	}
        }
    }

    @Override
    protected boolean isSelected(int i){
        return i == selected;
    }

    @Override
    protected void drawBackground() {
    	
    }

    public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float g, float h, float textureWidth, float textureHeight)
    {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double)x, (double)(y + h), 0.0D).tex((double)(u * f), (double)((v + (float)h) * f1)).endVertex();
        worldrenderer.pos((double)(x + g), (double)(y + h), 0.0D).tex((double)((u + (float)g) * f), (double)((v + (float)h) * f1)).endVertex();
        worldrenderer.pos((double)(x + g), (double)y, 0.0D).tex((double)((u + (float)g) * f), (double)(v * f1)).endVertex();
        worldrenderer.pos((double)x, (double)y, 0.0D).tex((double)(u * f), (double)(v * f1)).endVertex();
        tessellator.draw();
    }
    
    @Override
    protected void drawSlot(int i, int i1, int i2, int i3, int i4, int i5) {
    	
    	Account account = getAccount(i);

        Minecraft minecraft = Minecraft.getMinecraft();
        ScaledResolution scaledResolution = new ScaledResolution(minecraft);
        FontRenderer fontRenderer = minecraft.fontRendererObj;

        int x = i1 + 2;
        int y = i2;

        if (y >= scaledResolution.getScaledHeight() || y < 0)
            return;

        glTranslated(x, y, 0);

        fontRenderer.drawStringWithShadow(getAccount(i).getName(), 0, 6, 0xFFFFFFFF);
        if(account.getPassword().length() > 0) {
        	String pass = account.getPassword();
        	pass = pass.replaceAll("(?s).", "*");
        	fontRenderer.drawStringWithShadow("\2477" + pass, 0, 6 + fontRenderer.FONT_HEIGHT + 2, 0xFFFFFFFF);
        }

        glTranslated(-x, -y, 0);
    }

    public Account getAccount(int i){
        return  Clarinet.INSTANCE.getAccountManager().getAccounts().get(i);
    }

    private void drawFace(String name, int x, int y, int w, int h){
        try{
            AbstractClientPlayer.getDownloadImageSkin(AbstractClientPlayer.getLocationSkin(name), name).loadTexture(Minecraft.getMinecraft().getResourceManager());
            Minecraft.getMinecraft().getTextureManager().bindTexture(AbstractClientPlayer.getLocationSkin(name));

            glEnable(GL_BLEND);

            glColor4f(1, 1, 1, 1);

            Gui.drawModalRectWithCustomSizedTexture(x, y, 24, 24, w, h, 192, 192);
            Gui.drawModalRectWithCustomSizedTexture(x, y, 120, 24, w, h, 192, 192);

            glDisable(GL_BLEND);
        }catch(Exception ignored){}
    }

    public void removeSelected(){
        if(selected == -1 || getAccount(selected) == null)
            return;
        
        Clarinet.INSTANCE.getAccountManager().getAccounts().remove(getAccount(selected));
        Clarinet.INSTANCE.getAccountManager().save();
    
    }

    public Account getSelectedAccount(){
        return getAccount(selected);
    }
}
