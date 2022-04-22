package stellar.skid;

import stellar.skid.events.EventManager;
import stellar.skid.gui.screen.click.DiscordGUI;
import stellar.skid.gui.screen.dropdown.DropdownGUI;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.ModuleManager;
import stellar.skid.modules.ModuleManager.ModuleCreator;
import stellar.skid.modules.PlayerManager;
import stellar.skid.modules.combat.*;
import stellar.skid.modules.configurations.ConfigManager;
import stellar.skid.modules.exceptions.OutdatedConfigException;
import stellar.skid.modules.exploits.*;
import stellar.skid.modules.misc.*;
import stellar.skid.modules.move.*;
import stellar.skid.modules.player.*;
import stellar.skid.modules.visual.*;
import stellar.skid.utils.notifications.NotificationManager;
import stellar.skid.utils.tasks.TaskManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.resources.ResourceIndex;
import net.minecraft.init.Blocks;
import net.optifine.RenderEnv;
//import net.skidunion.API;
//import net.skidunion.irc.IRCClientBuilder;
//import net.skidunion.security.Protection;
//import net.skidunion.security.annotations.Protect;
//import net.skidunion.security.crypto.AES;
//import net.skidunion.security.crypto.Blowfish;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.lwjgl.opengl.Display.setTitle;

//@Protect.Virtualize
public class Initializer {

    private static final Logger LOGGER = LogManager.getLogger();
    private static Initializer INSTANCE;
    private boolean isDev;

    public double secretShit = 0.01;
    public double flight50 = -50;
    public double flight90 = -12;
    public double flightAmp = -0.001;
    public double flight75 = -0.42;
    public double flightMagicCon1 = -1;
    public double flightMagicCon2 = 1;
    public double flightHypixelGroundCheck = 1;
    public double bunnyDivFriction = 50;
    public double threshold = 0.5;

    public double min17 = 0.00015, max17 = 0.000163166800276;
    public double min18 = 0.0099, max18 = 0.011921599284565;
    public double min19 = 0.014, max19 = 0.015919999545217;
    public double min20 = 0.0099, max20 = 0.011999999731779;

    private enum Singleton {
        INSTANCE;

        private final Initializer value;

        Singleton() {
            this.value = new Initializer();
        }
    }

    public static Initializer getInstance() {
        return Singleton.INSTANCE.value;
    }

    public String Xor(Object obj, String key) {
        StringBuilder sb = new StringBuilder();
        char[] keyChars = key.toCharArray();

        int i = 0;

        for (char c : obj.toString().toCharArray()) {
            sb.append((char) (c ^ keyChars[i % keyChars.length]));
            i++;
        }

        return sb.toString();
    }

    public void onProtection(String token) {
        try {
            {
                secretShit = 0.0001000000001;
                flight50 = 5.0d;
                flight90 = 9.0d;
                flight75 = 0.75d;
                flightAmp = 0.13d;
                flightMagicCon1 = 2.1449999809265137;
                flightMagicCon2 = -0.1552320045166016;
                flightHypixelGroundCheck = 0.015625;
                bunnyDivFriction = 159.9;
                threshold = 0.66;
            }

            try {
                StellarWare.getInstance().setModuleManager(new ModuleManager(StellarWare.getInstance(), 3));
                isDev = true;
                register("velocity", Velocity::new);
                register("killsults", Killsults::new);
                register("sprint", Sprint::new);
                register("hud", HUD::new);
                register("click_gui", ClickGUI::new);
                register("no_rotate", NoRotate::new);
                register("speed_mine", SpeedMine::new);
                register("auto_pot", AutoPot::new);
                register("kill_aura", KillAura::new);
                register("criticals", Criticals::new);
                register("reach", Reach::new);
                register("step", Step::new);
                register("no_slow", NoSlow::new);
                register("speed", Speed::new);
                register("glow", ESP::new);
                register("chams", Chams::new);
                register("chest_esp", ChestESP::new);
                register("nametags", Nametags::new);
                register("no_fall", NoFall::new);
                register("xray", XRay::new);
                register("chest_stealer", ChestStealer::new);
                register("scaffold", Scaffold::new);
                register("auto_armor", AutoArmor::new);
                register("inv_manager", InvManager::new);
                register("auto-hypixel", AutoHypixel::new);
                register("gui_move", GuiMove::new);
                register("phase", Phase::new);
                register("auto_tool", AutoTool::new);
                register("animations", Animations::new);
                register("glint-colorize", GlintColorize::new);
                register("dmg-particles", DMGParticle::new);
                register("tracers", Tracers::new);
                register("blink", Blink::new);
                register("anti_cactus", AntiCactus::new);
                register("crosshair", Crosshair::new);
                register("brightness", Brightness::new);
                register("item_physic", ItemPhysic::new);
                register("item_esp", ItemESP::new);
                register("fast_place", FastPlace::new);
                register("radar", Radar::new);
                register("world_time", Atmosphere::new);
                register("testmodule", TestModule::new);
                register("player_esp", PlayerESP::new);
                register("hit_box", HitBox::new);
                register("waypoints", Waypoints::new);
                register("freecam", Freecam::new);
                register("target-strafe", TargetStrafe::new);
                register("tab-gui", TabGUI::new);
                register("camera", Camera::new);
                register("arrows", Arrows::new);
                register("bed-breaker", BedBreaker::new);
                register("auto-heal", AutoHead::new);
                register("flight", Flight::new);
                register("anti_void", AntiVoid::new);
                register("long-jump", LongJump::new);
                register("blocksoverlay", BlockOutline::new);
                register("disabler", Disabler::new);
                register("ClickTP",ClickTP::new);


                StellarWare.getInstance().playerManager = new PlayerManager(StellarWare.getInstance(), "players.data");
                StellarWare.getInstance().taskManager = new TaskManager();
                StellarWare.getInstance().notificationManager = new NotificationManager();
            } catch (Throwable t) {

            }

            EventManager.register(StellarWare.getInstance().getModuleManager().getModule(ClickGUI.class));
            Killsults killsults = StellarWare.getInstance().getModuleManager().getModule(Killsults.class);

            if (Files.notExists(killsults.getPath())) {
                Files.createFile(killsults.getPath());
            }

            if (StellarWare.getInstance().version.toCharArray()[0] == '@') {
                StellarWare.getInstance().version = new SimpleDateFormat("MMddyy").format(new Date());
            }

            //region Loading configurations
            StellarWare.getInstance().getModuleManager().getBindManager().load();

            ConfigManager configManager = StellarWare.getInstance().getModuleManager().getConfigManager();
            Path path = configManager.getConfigPath("default");

            try {
                if (Files.exists(path)) {
                    configManager.load(path, false);
                }
            } catch (OutdatedConfigException oce) {
                LOGGER.error("Default config is outdated. Deleting it...");

                try {
                    Files.delete(path);
                } catch (IOException ioe) {
                    LOGGER.error("An I/O error occurred while deleting default config", ioe);
                }
            } catch (Throwable t) {
                LOGGER.error("An unexpected error occurred while loading default config", t);
            }

/*            try {
                new ViaFabric().onInitialize();
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            //endregion
            setTitle("StellarWare " + StellarWare.getInstance().getVersion());
            StellarWare.getInstance().discordGUI = new DiscordGUI(StellarWare.getInstance());
            StellarWare.getInstance().dropDownGUI = new DropdownGUI();

//            StellarWare.getInstance().irc = new IRCClientBuilder(URI.create("ws://ws.stellarWare.wtf:2095"))
//                    .setSecret("6zvaa5uqx8k4FMfT")
//                    .setHashFunction(new NativeCachedHashFunction())
//                    .setToken(AES.decrypt(System.getProperty("novo.delsyToken"), "WHTTB3KKadKgqFg5", "tJGZ2ZnM7bF6qDhs"))
//                    .build();

//            StellarWare.getInstance().irc.getEventManager().register(SimpleEventListener.getInstance());
            ResourceIndex.wasInitialized = true;
            RenderEnv.wasInitialized = true;
            Blocks.fuckUp = 0x01c0c1;
            System.setProperty("java.net.preferIPv4Stack", "true");

        } catch (Throwable t) {
            t.printStackTrace();
            Minecraft.getInstance().shutdown();
            return;
        }

        Minecraft.getInstance().displayGuiScreen(new GuiMainMenu());
    }

//    public static String grabToken() throws Exception {
//        return AES.decrypt(System.getProperty("novo.delsyToken"), "WHTTB3KKadKgqFg5", "tJGZ2ZnM7bF6qDhs");
//    }

    private <Module extends AbstractModule> void register(@NotNull String name, @NotNull ModuleCreator<Module> moduleCreator) {
        StellarWare.getInstance().getModuleManager().register(name, moduleCreator);
    }
}
