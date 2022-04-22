package non.asset.gui.account.gui;

import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import non.asset.Clarinet;
import non.asset.gui.MainMenu;
import non.asset.gui.account.gui.components.GuiAccountList;
import non.asset.gui.account.gui.impl.GuiAddAlt;
import non.asset.gui.account.gui.thread.AccountLoginThread;
import non.asset.gui.account.system.Account;
import non.asset.gui.login.AltLoginThread;
import non.asset.module.impl.exploit.Disabler;
import non.asset.module.impl.other.Sults;
import non.asset.utils.OFC.MathUtils;
import non.asset.utils.OFC.TimerUtil;

public class GuiAltManager extends GuiScreen {
    public static GuiAltManager INSTANCE;
    public GuiAccountList accountList;
    private Account selectAccount = null;
    public static Account currentAccount;
    public static AccountLoginThread loginThread;
    
    public static AltLoginThread offl;
    
    private final Random random = new Random();
    
    public static TimerUtil message = new TimerUtil();
    
    public static String itiitiitiiitiiti = "";

    public float shadowuwu = 100;
    
    private double smoothY = 0;
    
    private double x = 0;
    private double y = 0;

    private int valuetest = 0;
    private int valuetest2 = 0;
    
    public GuiAltManager() {
        INSTANCE = this;
    }

    public void initGui() {
    	
        accountList = new GuiAccountList(this);
        accountList.registerScrollButtons(7, 8);
        accountList.elementClicked(-1, false, 0, 0);

        this.buttonList.add(new GuiButton(0, this.width / 2 + 158, this.height - 24, 100, 20, "Back"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 154, this.height - 48, 100, 20, "Login"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 50, this.height - 24, 100, 20, "Remove"));
        this.buttonList.add(new GuiButton(5, this.width / 2 + 4 + 50, this.height - 48, 100, 20, "Import"));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 50, this.height - 48, 100, 20, "Direct Login"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 154, this.height - 24, 100, 20, "Add"));
        this.buttonList.add(new GuiButton(7, this.width / 2 + 54, this.height - 24, 100, 20, "Random"));
        this.buttonList.add(new GuiButton(8, this.width / 2 - 258, this.height - 24, 100, 20, "Last Alt"));
        this.buttonList.add(new GuiButton(9, this.width / 2 + 158, this.height - 48, 100, 20, "Random Name"));
        this.buttonList.add(new GuiButton(10, this.width / 2 - 258, this.height - 48, 100, 20, "Go Multi"));
    }

    @Override
    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_){
		
        ScaledResolution scaledResolution = new ScaledResolution(mc);

        //drawDefaultBackground();
        
		mc.getTextureManager().bindTexture(new ResourceLocation("textures/client/dtgygubuhiu.png"));
		this.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);
		
        
        accountList.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
        
    	String getstatus = "\2477Logged in as \247a" + mc.getSession().getUsername() + "\2477.";
    	
        if(!(mc.getSession().getUsername().length() > 0)) {
        	getstatus = "Can't read the user name :(";
        }
    	drawCenteredString(mc.fontRendererObj, getstatus, scaledResolution.getScaledWidth()/2, 5, 0xFFCFCFCF);
    	drawCenteredString(mc.fontRendererObj, "Accounts: " + Clarinet.INSTANCE.getAccountManager().getAccounts().size(), width / 2, 15, 0xFFFFFFFF);
    }

    @Override
    public void handleMouseInput() throws IOException{
        super.handleMouseInput();
        accountList.handleMouseInput();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException{
        switch (button.id) {
            case 0:
                if (loginThread == null || !loginThread.getStatus().contains("Logging in")) {
                    mc.displayGuiScreen(new MainMenu());
                }
                break;
            case 1:
                if(accountList.selected == -1)
                    return;
                
                if(accountList.getSelectedAccount().getPassword().length() > 0 ) {
                	loginThread = new AccountLoginThread(accountList.getSelectedAccount().getEmail(),accountList.getSelectedAccount().getPassword());
                    loginThread.start();
                }else {
                    offl = new AltLoginThread(accountList.getSelectedAccount().getName(), "");
                	offl.start();
                }
                
                break;
            case 2:

                if(accountList.selected == -1)
                    return;

                
            	if(accountList.getSize() > 0 || accountList != null) {
            		accountList.removeSelected();
            	}
                break;
            case 3:
                if (loginThread != null)
                    loginThread = null;

                mc.displayGuiScreen(new GuiAddAlt(this));
                break;
            case 4:
                if (loginThread != null)
                    loginThread = null;
                mc.displayGuiScreen(new non.asset.gui.login.GuiAltLogin(this));
                
                break;
            case 7:
                ArrayList<Account> registry = Clarinet.INSTANCE.getAccountManager().getAccounts();
                if (registry.isEmpty()) return;
                Random random = new Random();
                Account randomAlt = registry.get(random.nextInt(Clarinet.INSTANCE.getAccountManager().getAccounts().size()));
                
                currentAccount = randomAlt;
                login(randomAlt);
                message.reset();
                break;
            case 5:
                JFrame frame = new JFrame("Import alts");
                JFileChooser chooser = new JFileChooser();
                frame.add(chooser);
                frame.pack();
                int returnVal = chooser.showOpenDialog(frame);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

                    for(String line : Files.readAllLines(Paths.get(chooser.getSelectedFile().getPath()))){
                        if(!line.contains(":")) {
                            break;
                        }

                        String[] parts = line.split(":");

                        Account account = new Account(parts[0], parts[1], parts[0]);
                        Clarinet.INSTANCE.getAccountManager().getAccounts().add(account);
                    }
                }

                Clarinet.INSTANCE.getAccountManager().save();
                break;
            case 8:
                if(Clarinet.INSTANCE.getAccountManager().getLastAlt() == null)
                    return;
                loginThread = new AccountLoginThread(Clarinet.INSTANCE.getAccountManager().getLastAlt().getEmail(),Clarinet.INSTANCE.getAccountManager().getLastAlt().getPassword());
                loginThread.start();
                break;
            case 9:
            	String erfcrt = generateRandomUsername();
            	
            	String asdf = minor[MathUtils.getRandomInRange(0, minor.length - 1)];

            	String asdf2 = minor[MathUtils.getRandomInRange(0, minor.length - 1)];
            	
                offl = new AltLoginThread(asdf + asdf2 + "_" + generateRandomUsername(MathUtils.getRandomInRange(5, 8), true), "");
                offl.start();                
                message.reset();
                
                break;
                
            case 10:
                if (loginThread == null || !loginThread.getStatus().contains("Logging in")) {
                	mc.displayGuiScreen(new GuiMultiplayer(this));
                }
            	break;
        }
    }
    public static String[] minor = new String[]{
			"Po",
			"Ce",
			"Os",
			"Be",
			"oK",
			"Me",
			"Ui",
			"Cy",
			"li",
			"eH",
			"my",
			"se",
			"gi",
			"Fe",
			"ne",
	};
    public void login(Account account){
        loginThread = new AccountLoginThread(account.getEmail(),account.getPassword());
        loginThread.start();
    }
    
    public static String generateRandomUsername() {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        StringBuilder builder = new StringBuilder();
        Random ran = new Random();
        for (int i = 0; i < 10; i++) {
            builder.append(alphabet.charAt(ran.nextInt(alphabet.length())));
        }
        
        return builder.toString();
    }
    public static String generateRandomUsername(int max, boolean numbers) {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" + (numbers ? "123456789" : "");
        StringBuilder builder = new StringBuilder();
        Random ran = new Random();
        for (int i = 0; i < max; i++) {
            builder.append(alphabet.charAt(ran.nextInt(alphabet.length())));
        }
        
        return builder.toString();
    }
}
