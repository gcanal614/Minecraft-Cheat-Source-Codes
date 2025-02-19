package net.minecraft.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IChatComponent;
import non.asset.Clarinet;
import non.asset.gui.MainMenu;
import non.asset.gui.account.gui.GuiAltManager;
import non.asset.gui.account.gui.thread.AccountLoginThread;
import non.asset.gui.account.system.Account;
import non.asset.utils.oldthealtening.TheAltening;
import non.asset.utils.oldthealtening.domain.AlteningAlt;

public class GuiDisconnected extends GuiScreen {
    public static boolean niggaButton = false;
    public static ServerData serverData;
    private String reason;
    private IChatComponent message;
    private List multilineMessage;
    private final GuiScreen parentScreen;
    private int field_175353_i;
    private static final String __OBFID = "CL_00000693";
    private long reconnectTime;

    public GuiDisconnected(GuiScreen p_i45020_1_, String p_i45020_2_, IChatComponent p_i45020_3_) {
        this.parentScreen = p_i45020_1_;
        this.reason = I18n.format(p_i45020_2_, new Object[0]);
        this.message = p_i45020_3_;
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), this.width - 50);
        this.field_175353_i = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT, I18n.format("gui.toMenu", new Object[0])));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            this.mc.displayGuiScreen(this.parentScreen);
        }

        if (button.id == 1) {
            this.mc.displayGuiScreen(new GuiAltManager());
        }

        if (button.id == 2 && GuiAltManager.currentAccount != null) {
         //   GuiAltManager.currentAccount.setBanned(true);
        }
        if (button.id == 4) {
            if (GuiAltManager.currentAccount != null) {
                if (GuiAltManager.loginThread != null) {
                    GuiAltManager.loginThread = null;
                }
                Clarinet.INSTANCE.getAccountManager().getAccounts().remove(GuiAltManager.currentAccount);
                Clarinet.INSTANCE.getAccountManager().save();
            }
        }
        if (button.id == 5) {
            if (Clarinet.INSTANCE.getAccountManager().getAccounts().isEmpty()) return;
            ArrayList<Account> registry = Clarinet.INSTANCE.getAccountManager().getAccounts();
            Random random = new Random();
            Account randomAlt = registry.get(random.nextInt(Clarinet.INSTANCE.getAccountManager().getAccounts().size()));
            String user2 = randomAlt.getName();
            String pass2 = randomAlt.getPassword();
            GuiAltManager.currentAccount = randomAlt;
            try {
                (GuiAltManager.loginThread = new AccountLoginThread(user2, pass2)).start();
                if (serverData != null) mc.displayGuiScreen(new GuiConnecting(new MainMenu(), mc, serverData));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (button.id == 6) {
            if (Clarinet.INSTANCE.getAccountManager().getAlteningKey() == null) return;
            niggaButton = true;
            try {
                TheAltening theAltening = new TheAltening(Clarinet.INSTANCE.getAccountManager().getAlteningKey());
                AlteningAlt account = theAltening.generateAccount(theAltening.getUser());
                if (!Objects.requireNonNull(account).getToken().isEmpty()) {
                    GuiAltManager.loginThread = new AccountLoginThread(Objects.requireNonNull(account).getToken().replaceAll(" ", ""), "nig");
                    GuiAltManager.loginThread.start();
                }
                if (serverData != null) mc.displayGuiScreen(new GuiConnecting(new MainMenu(), mc, serverData));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (button.id == 7) {
            if (serverData != null) mc.displayGuiScreen(new GuiConnecting(new MainMenu(), mc, serverData));
        }
        if (button.id == 8) {
            if (Clarinet.INSTANCE.getAccountManager().getAccounts().isEmpty()) return;
            Clarinet.INSTANCE.setAutoRelogNormal(!Clarinet.INSTANCE.isAutoRelogNormal());
            Clarinet.INSTANCE.setAutoRelogTheAltening(false);
            reconnectTime = System.currentTimeMillis() + 1500;
        }
        if (button.id == 9) {
            if (Clarinet.INSTANCE.getAccountManager().getAlteningKey() == null) return;
            Clarinet.INSTANCE.setAutoRelogTheAltening(!Clarinet.INSTANCE.isAutoRelogTheAltening());
            Clarinet.INSTANCE.setAutoRelogNormal(false);
            reconnectTime = System.currentTimeMillis() + 1500;
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.reason, this.width / 2, this.height / 2 - this.field_175353_i / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 11184810);
        int var4 = this.height / 2 - this.field_175353_i / 2;

        if (this.multilineMessage != null) {
            for (Iterator var5 = this.multilineMessage.iterator(); var5.hasNext(); var4 += this.fontRendererObj.FONT_HEIGHT) {
                String var6 = (String) var5.next();

                this.drawCenteredString(this.fontRendererObj, var6, this.width / 2, var4, 16777215);
                if (GuiAltManager.currentAccount != null) {
                    drawCenteredString(fontRendererObj, "Current Alt: " + mc.getSession().getUsername(), width / 2, 20, -1);
                }
                drawCenteredString(fontRendererObj, "Auto Relog (Normal): " + Clarinet.INSTANCE.isAutoRelogNormal(), width / 2, GuiAltManager.currentAccount != null ? 34 : 20, -1);
                drawCenteredString(fontRendererObj, "Auto Relog (TheAltening): " + Clarinet.INSTANCE.isAutoRelogTheAltening(), width / 2, GuiAltManager.currentAccount != null ? 48 : 34, -1);
            }
        }
        if (Clarinet.INSTANCE.isAutoRelogNormal() || Clarinet.INSTANCE.isAutoRelogTheAltening()) {
            drawCenteredString(fontRendererObj, "Relog Time: " + (Math.max(reconnectTime - System.currentTimeMillis(),0)) + "ms", width / 2, GuiAltManager.currentAccount != null ? 62 : 48, -1);
            if (System.currentTimeMillis() >= reconnectTime) {
                if (Clarinet.INSTANCE.isAutoRelogTheAltening()) {
                    if (Clarinet.INSTANCE.getAccountManager().getAlteningKey() == null) return;
                    niggaButton = true;
                    try {
                        TheAltening theAltening = new TheAltening(Clarinet.INSTANCE.getAccountManager().getAlteningKey());
                        AlteningAlt account = theAltening.generateAccount(theAltening.getUser());
                        if (!Objects.requireNonNull(account).getToken().isEmpty()) {
                            GuiAltManager.loginThread = new AccountLoginThread(Objects.requireNonNull(account).getToken().replaceAll(" ", ""), "nig");
                            GuiAltManager.loginThread.start();
                        }
                        Clarinet.INSTANCE.getAccountManager().setLastAlteningAlt((Objects.requireNonNull(account).getToken().replaceAll(" ", "")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (Clarinet.INSTANCE.getAccountManager().getAccounts().isEmpty()) return;
                    ArrayList<Account> registry = Clarinet.INSTANCE.getAccountManager().getAccounts();
                    Random random = new Random();
                    Account randomAlt = registry.get(random.nextInt(Clarinet.INSTANCE.getAccountManager().getAccounts().size()));
                    String user2 = randomAlt.getName();
                    String pass2 = randomAlt.getPassword();
                    GuiAltManager.currentAccount = randomAlt;
                    try {
                        (GuiAltManager.loginThread = new AccountLoginThread(user2, pass2)).start();
                        Clarinet.INSTANCE.getAccountManager().setLastAlt(randomAlt);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (serverData != null && System.currentTimeMillis() >= reconnectTime + 2000)
                    mc.displayGuiScreen(new GuiConnecting(new MainMenu(), mc, serverData));
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
