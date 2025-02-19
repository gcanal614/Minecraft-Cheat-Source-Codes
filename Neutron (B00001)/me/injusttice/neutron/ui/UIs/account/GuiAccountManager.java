package me.injusttice.neutron.ui.UIs.account;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.utils.render.DrawUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.DefaultPlayerSkin;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.lwjgl.opengl.GL11.*;

public final class GuiAccountManager extends GuiScreen {

    private final GuiScreen parent;

    private Account selectedAccount;

    private GuiButton loginButton, deleteButton;

    private boolean hasScrollbar;
    private int scrollOffset;

    private long lastClick;

    public GuiAccountManager(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        final AccountManager accountManager = NeutronMain.instance.getAccountManager();

        this.deleteButton.enabled = this.loginButton.enabled = this.selectedAccount != null;

        // For smooth shadows
        glDisable(GL_ALPHA_TEST);
        // Background
        DrawUtils.glDrawFilledQuad(0, 0, this.width, this.height, 0xFF080808, 0xFF0F0F0F);
        // Top
        DrawUtils.glDrawFilledQuad(0, 0, this.width, 30, 0xFF080808, 0xFF111111);
        // Top shadow
        DrawUtils.glDrawFilledQuad(0, 30, this.width, 4, 0x96000000, 0);
        // Bottom
        DrawUtils.glDrawFilledQuad(0, this.height - 58, this.width, 58, 0xFF090909);
        // Bottom shadow
        DrawUtils.glDrawFilledQuad(0, this.height - 62, this.width, 4, 0, 0x96000000);
        // Restore to normal
        glEnable(GL_ALPHA_TEST);
        // Draw buttons
        super.drawScreen(mouseX, mouseY, partialTicks);

        // Username
        this.mc.fontRendererObj.drawStringWithShadow(String.format("\2477Username: \247A%s", this.mc.getSession().getUsername()), 4, 4, 0xFFFFFFFF);
        // Alt manager title and alt count
        final String title = String.format("Alt Manager \2478[\2477%s\2478]", accountManager.getAccounts().size());
        this.mc.fontRendererObj.drawStringWithShadow(title, this.width / 2.0 - this.fontRendererObj.getStringWidth(title) / 2.0,
                                                     30 / 2.0 - 9 / 2.0, 0xFFFFFFFF);

        final Collection<Account> accounts = accountManager.getAccounts();

        int top = 30;

        final int xBuffer = 2;
        final int yBuffer = 2;
        final int headSize = 32;
        final int accountEntryHeight = headSize + yBuffer * 2;
        final int left = this.width / 3;

        final int uncutHeight = accounts.size() * accountEntryHeight;
        final int maxHeight = this.height - top - 58;

        this.hasScrollbar = uncutHeight > maxHeight;

        if (!this.hasScrollbar) {
            this.scrollOffset = 0;
        }

        if (this.hasScrollbar) {
            final int scrollAmount = -Mouse.getDWheel() / 5;

            this.scrollOffset = Math.max(0, Math.min(uncutHeight - maxHeight, this.scrollOffset + scrollAmount));

            DrawUtils.glScissorBox(left, top, this.width / 1.5, this.height - top - 58, new ScaledResolution(this.mc));

            if (this.scrollOffset > 0) {
                glTranslated(0, -this.scrollOffset, 0);
            }
        }

        for (final Account account : accounts) {
            final String username = account.getUsername();
            final String displayName = username.length() == 0 ? account.getEmail() : username;
            this.mc.fontRendererObj.drawStringWithShadow(displayName, left + accountEntryHeight, top + yBuffer * 2, 0xFFFFFFFF);

            if (this.selectedAccount == account) {
                DrawUtils.glDrawOutlinedQuad(left + 1, top + 1, Math.max(left, accountEntryHeight + xBuffer + this.mc.fontRendererObj.getStringWidth(displayName)) - 2, accountEntryHeight - 2, 1, 0xFF434343);
            }

            final String passwordStr = account.getPassword();

            if (passwordStr != null && passwordStr.length() > 0) {
                final char[] password = new char[passwordStr.length()];
                Arrays.fill(password, '*');
                final String censoredPwd = new String(password);

                this.mc.fontRendererObj.drawStringWithShadow(censoredPwd, left + headSize + xBuffer * 2, top + yBuffer * 2 + 9 + yBuffer, 0xFF434343);
            }

            DrawUtils.glDrawPlayerFace(left + xBuffer, top + yBuffer, headSize, headSize, DefaultPlayerSkin.getDefaultSkinLegacy());

            if (account.isWorking()) {
                final int iconSize = 16;

                if (account.isBanned()) {
                    if (account.getUnbanDate() == -1337) {
                        this.mc.fontRendererObj.drawStringWithShadow("Sec Alert",
                                                                     left + headSize + xBuffer * 2,
                                                                     top + yBuffer * 2 + yBuffer + 9 * 2, 0xFFFF2059);
                    } else {
                        final Date timeRemaining = new Date(account.getUnbanDate() - System.currentTimeMillis());
                        final SimpleDateFormat hypixelDateFormat = new SimpleDateFormat("D'd' H'h' m'm' s's'");
                        this.mc.fontRendererObj.drawStringWithShadow(hypixelDateFormat.format(timeRemaining),
                                                                     left + headSize + xBuffer * 2,
                                                                     top + yBuffer * 2 + yBuffer + 9 * 2, 0xFFFFFF59);
                    }

                    DrawUtils.glDrawWarningImage(left * 2.0 - iconSize - xBuffer, top + yBuffer, iconSize, iconSize, 0xFFFFFF00);
                } else {
                    DrawUtils.glDrawCheckmarkImage(left * 2.0 - iconSize - xBuffer, top + yBuffer, iconSize, iconSize, 0xFF00FF59);
                }
            }

            top += accountEntryHeight;
        }

        if (this.hasScrollbar) {
            if (this.scrollOffset > 0) {
                glTranslated(0, this.scrollOffset, 0);
            }

            DrawUtils.glEndScissor();
        }
    }

    @Override
    public void initGui() {
        final int xBuffer = 2;
        final int yBuffer = 2;
        final int unbufferedWidth = this.width / 6;
        final int buttonWidth = this.width / 6 - xBuffer * 2;
        final int buttonHeight = 20;
        final int unbufferedHeight = buttonHeight + yBuffer * 2;

        final int left = this.width / 3;
        final int top = this.height - 50;

        this.buttonList.add(loginButton = new GuiButton(0, left + xBuffer, top + yBuffer, buttonWidth, buttonHeight, "Login"));
        this.buttonList.add(new GuiButton(1, left + unbufferedWidth + xBuffer, top + yBuffer, buttonWidth, buttonHeight, "Add"));
        this.buttonList.add(deleteButton = new GuiButton(2, left + xBuffer, top + yBuffer + unbufferedHeight, buttonWidth, buttonHeight, "Delete"));
        this.buttonList.add(new GuiButton(3, left + unbufferedWidth + xBuffer, top + yBuffer + unbufferedHeight, buttonWidth, buttonHeight, "Done"));

        final int directLoginWidth = Math.max(buttonWidth / 2, this.mc.fontRendererObj.getStringWidth("Direct Login") + xBuffer * 2);
        this.buttonList.add(new GuiButton(4, left - directLoginWidth - xBuffer, top + yBuffer + unbufferedHeight,
                                          directLoginWidth,
                                          buttonHeight, "Direct Login"));

        final int dupesWidth = Math.max(buttonWidth / 2, this.mc.fontRendererObj.getStringWidth("Filter Unique") + xBuffer * 2);
        final int notWorkingWidth = Math.max(buttonWidth / 2, this.mc.fontRendererObj.getStringWidth("Filter Working") + xBuffer * 2);
        final int bannedWidth = Math.max(buttonWidth / 2, this.mc.fontRendererObj.getStringWidth("Filter Unbanned") + xBuffer * 2);

        this.buttonList.add(new GuiButton(5, left - dupesWidth - xBuffer, top + yBuffer,
                                          dupesWidth, buttonHeight, "Filter Unique"));

        this.buttonList.add(new GuiButton(6, left * 2 + xBuffer, top + yBuffer + unbufferedHeight,
                                          notWorkingWidth, buttonHeight, "Filter Working"));

        this.buttonList.add(new GuiButton(7, left * 2 + xBuffer, top + yBuffer,
                                          bannedWidth, buttonHeight, "Filter Unbanned"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        final AccountManager accountManager = NeutronMain.instance.getAccountManager();

        switch (button.id) {
            case 0:
                if (this.selectedAccount != null) {
                    this.selectedAccount.createLoginThread();
                }
                break;
            case 1:
                this.mc.displayGuiScreen(new GuiAddAccount(this));
                break;
            case 2:
                accountManager.deleteAccount(this.selectedAccount);

                this.selectedAccount = null;
                break;
            case 3:
                this.mc.displayGuiScreen(this.parent);
                break;
            case 4:
                this.mc.displayGuiScreen(new GuiDirectLogin(this));
                break;
            case 5:
                final List<Account> dupes = new ArrayList<>();
                final List<String> emails = new ArrayList<>();

                accountManager.getAccounts().forEach(account -> {
                    if (emails.contains(account.getEmail())) {
                        dupes.add(account);
                    } else {
                        emails.add(account.getEmail());
                    }
                });

                dupes.forEach(accountManager::deleteAccount);
                break;
            case 6:
                final Collection<Account> workingAccounts = accountManager.getWorkingAccounts();
                final List<Account> notWorking = new ArrayList<>();

                accountManager.getAccounts().forEach(account -> {
                    if (!workingAccounts.contains(account)) {
                        notWorking.add(account);
                    }
                });

                notWorking.forEach(accountManager::deleteAccount);
                break;
            case 7:
                final Collection<Account> unbannedAccounts = accountManager.getUnbannedAccounts();
                final List<Account> banned = new ArrayList<>();

                accountManager.getAccounts().forEach(account -> {
                    if (!unbannedAccounts.contains(account)) {
                        banned.add(account);
                    }
                });

                banned.forEach(accountManager::deleteAccount);
                break;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int top = 30;

        final int accountEntryHeight = 36;
        final int left = this.width / 3;

        boolean selectedAccount = false;

        if (mouseY > top && mouseY < this.height - 58 && mouseX > left) {
            mouseY += this.scrollOffset;
            for (final Account account : NeutronMain.instance.getAccountManager().getAccounts()) {
                if (mouseY > top && mouseY < top + accountEntryHeight &&
                    mouseX < left + Math.max(left, accountEntryHeight + this.mc.fontRendererObj.getStringWidth(account.getUsername().length() == 0 ? account.getEmail() : account.getUsername()) + 2)) {
                    this.selectedAccount = account;
                    selectedAccount = true;
                    break;
                }

                top += accountEntryHeight;
            }
            mouseY -= this.scrollOffset;
        }

        if (mouseButton == 0 && selectedAccount) {
            if (System.currentTimeMillis() - this.lastClick < 200L) {
                this.selectedAccount.createLoginThread();
            }

            this.lastClick = System.currentTimeMillis();
        }

        if (!selectedAccount && mouseY < this.height - 58) {
            this.selectedAccount = null;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(this.parent);
            return;
        }
    }
}
