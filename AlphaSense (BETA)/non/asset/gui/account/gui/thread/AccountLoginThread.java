package non.asset.gui.account.gui.thread;

import com.mojang.authlib.Agent;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.util.Session;
import non.asset.Clarinet;
import non.asset.gui.account.gui.GuiAltManager;
import non.asset.gui.account.system.Account;

import java.net.Proxy;
import java.util.UUID;

public class AccountLoginThread extends Thread {

    private String email, password;

    private String status = "Waiting for login...";

    public AccountLoginThread(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public void run() {
        if ((Minecraft.getMinecraft().currentScreen instanceof GuiDisconnected && GuiDisconnected.niggaButton)) {
        }else {Clarinet.INSTANCE.switchToMojang();}
        
        status = "Logging in...";

        YggdrasilAuthenticationService yService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString());
        UserAuthentication userAuth = yService.createUserAuthentication(Agent.MINECRAFT);

        if (userAuth == null) {
            status = "\2474Unknown error.";
            return;
        }

        userAuth.setUsername(email);
        userAuth.setPassword(password);
        try {
            userAuth.logIn();
            
            Session session = new Session(userAuth.getSelectedProfile().getName(), userAuth.getSelectedProfile().getId().toString(), userAuth.getAuthenticatedToken(), email.contains("@") ? "mojang" : "legacy");
            
            Minecraft.getMinecraft().setSession(session);
            
            Account account = new Account(email,password,email);
            account.setName(session.getUsername());
            if ((Minecraft.getMinecraft().currentScreen instanceof GuiDisconnected))
                Clarinet.INSTANCE.getAccountManager().setLastAlt(account);
            Clarinet.INSTANCE.getAccountManager().save();
            GuiAltManager.currentAccount = account;
            status = String.format("\247aLogged in as %s.", account.getName());
        } catch (AuthenticationException exception) {
            status = "\2474Login failed.";
        } catch (NullPointerException exception) {
            status = "\2474Unknown error.";
        }
    }

    public String getStatus() {
        return status;
    }

}


