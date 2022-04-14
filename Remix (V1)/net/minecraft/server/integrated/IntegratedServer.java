package net.minecraft.server.integrated;

import net.minecraft.server.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.server.management.*;
import net.minecraft.world.demo.*;
import net.minecraft.command.*;
import optifine.*;
import net.minecraft.world.storage.*;
import java.io.*;
import net.minecraft.world.*;
import net.minecraft.crash.*;
import net.minecraft.client.*;
import net.minecraft.profiler.*;
import net.minecraft.util.*;
import java.net.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import java.util.*;
import com.google.common.util.concurrent.*;
import java.util.concurrent.*;
import org.apache.logging.log4j.*;

public class IntegratedServer extends MinecraftServer
{
    private static final Logger logger;
    private final Minecraft mc;
    private final WorldSettings theWorldSettings;
    private boolean isGamePaused;
    private boolean isPublic;
    private ThreadLanServerPing lanServerPing;
    
    public IntegratedServer(final Minecraft mcIn) {
        super(mcIn.getProxy(), new File(mcIn.mcDataDir, IntegratedServer.USER_CACHE_FILE.getName()));
        this.mc = mcIn;
        this.theWorldSettings = null;
    }
    
    public IntegratedServer(final Minecraft mcIn, final String folderName, final String worldName, final WorldSettings settings) {
        super(new File(mcIn.mcDataDir, "saves"), mcIn.getProxy(), new File(mcIn.mcDataDir, IntegratedServer.USER_CACHE_FILE.getName()));
        this.setServerOwner(mcIn.getSession().getUsername());
        this.setFolderName(folderName);
        this.setWorldName(worldName);
        this.setDemo(mcIn.isDemo());
        this.canCreateBonusChest(settings.isBonusChestEnabled());
        this.setBuildLimit(256);
        this.setConfigManager(new IntegratedPlayerList(this));
        this.mc = mcIn;
        this.theWorldSettings = (this.isDemo() ? DemoWorldServer.demoWorldSettings : settings);
    }
    
    @Override
    protected ServerCommandManager createNewCommandManager() {
        return new IntegratedServerCommandManager();
    }
    
    @Override
    protected void loadAllWorlds(final String p_71247_1_, final String p_71247_2_, final long seed, final WorldType type, final String p_71247_6_) {
        this.convertMapIfNeeded(p_71247_1_);
        final ISaveHandler var7 = this.getActiveAnvilConverter().getSaveLoader(p_71247_1_, true);
        this.setResourcePackFromWorld(this.getFolderName(), var7);
        WorldInfo var8 = var7.loadWorldInfo();
        if (Reflector.DimensionManager.exists()) {
            final WorldServer var9 = (WorldServer)(this.isDemo() ? new DemoWorldServer(this, var7, var8, 0, this.theProfiler).init() : ((WorldServer)new WorldServerOF(this, var7, var8, 0, this.theProfiler).init()));
            var9.initialize(this.theWorldSettings);
            final Integer[] arr$;
            final Integer[] var10 = arr$ = (Integer[])Reflector.call(Reflector.DimensionManager_getStaticDimensionIDs, new Object[0]);
            for (int len$ = var10.length, i$ = 0; i$ < len$; ++i$) {
                final int dim = arr$[i$];
                final WorldServer world = (WorldServer)((dim == 0) ? var9 : new WorldServerMulti(this, var7, dim, var9, this.theProfiler).init());
                world.addWorldAccess(new WorldManager(this, world));
                if (!this.isSinglePlayer()) {
                    world.getWorldInfo().setGameType(this.getGameType());
                }
                if (Reflector.EventBus.exists()) {
                    Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, world);
                }
            }
            this.getConfigurationManager().setPlayerManager(new WorldServer[] { var9 });
            if (var9.getWorldInfo().getDifficulty() == null) {
                this.setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
            }
        }
        else {
            this.worldServers = new WorldServer[3];
            this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
            this.setResourcePackFromWorld(this.getFolderName(), var7);
            if (var8 == null) {
                var8 = new WorldInfo(this.theWorldSettings, p_71247_2_);
            }
            else {
                var8.setWorldName(p_71247_2_);
            }
            for (int var11 = 0; var11 < this.worldServers.length; ++var11) {
                byte var12 = 0;
                if (var11 == 1) {
                    var12 = -1;
                }
                if (var11 == 2) {
                    var12 = 1;
                }
                if (var11 == 0) {
                    if (this.isDemo()) {
                        this.worldServers[var11] = (WorldServer)new DemoWorldServer(this, var7, var8, var12, this.theProfiler).init();
                    }
                    else {
                        this.worldServers[var11] = (WorldServer)new WorldServerOF(this, var7, var8, var12, this.theProfiler).init();
                    }
                    this.worldServers[var11].initialize(this.theWorldSettings);
                }
                else {
                    this.worldServers[var11] = (WorldServer)new WorldServerMulti(this, var7, var12, this.worldServers[0], this.theProfiler).init();
                }
                this.worldServers[var11].addWorldAccess(new WorldManager(this, this.worldServers[var11]));
            }
            this.getConfigurationManager().setPlayerManager(this.worldServers);
            if (this.worldServers[0].getWorldInfo().getDifficulty() == null) {
                this.setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
            }
        }
        this.initialWorldChunkLoad();
    }
    
    @Override
    protected boolean startServer() throws IOException {
        IntegratedServer.logger.info("Starting integrated minecraft server version 1.8");
        this.setOnlineMode(true);
        this.setCanSpawnAnimals(true);
        this.setCanSpawnNPCs(true);
        this.setAllowPvp(true);
        this.setAllowFlight(true);
        IntegratedServer.logger.info("Generating keypair");
        this.setKeyPair(CryptManager.generateKeyPair());
        if (Reflector.FMLCommonHandler_handleServerAboutToStart.exists()) {
            final Object inst = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
            if (!Reflector.callBoolean(inst, Reflector.FMLCommonHandler_handleServerAboutToStart, this)) {
                return false;
            }
        }
        this.loadAllWorlds(this.getFolderName(), this.getWorldName(), this.theWorldSettings.getSeed(), this.theWorldSettings.getTerrainType(), this.theWorldSettings.getWorldName());
        this.setMOTD(this.getServerOwner() + " - " + this.worldServers[0].getWorldInfo().getWorldName());
        if (Reflector.FMLCommonHandler_handleServerStarting.exists()) {
            final Object inst = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
            if (Reflector.FMLCommonHandler_handleServerStarting.getReturnType() == Boolean.TYPE) {
                return Reflector.callBoolean(inst, Reflector.FMLCommonHandler_handleServerStarting, this);
            }
            Reflector.callVoid(inst, Reflector.FMLCommonHandler_handleServerStarting, this);
        }
        return true;
    }
    
    @Override
    public void tick() {
        final boolean var1 = this.isGamePaused;
        this.isGamePaused = (Minecraft.getMinecraft().getNetHandler() != null && Minecraft.getMinecraft().isGamePaused());
        if (!var1 && this.isGamePaused) {
            IntegratedServer.logger.info("Saving and pausing game...");
            this.getConfigurationManager().saveAllPlayerData();
            this.saveAllWorlds(false);
        }
        if (this.isGamePaused) {
            final Queue var2 = this.futureTaskQueue;
            final Queue var3 = this.futureTaskQueue;
            synchronized (this.futureTaskQueue) {
                while (!this.futureTaskQueue.isEmpty()) {
                    try {
                        if (Reflector.FMLCommonHandler_callFuture.exists()) {
                            Reflector.callVoid(Reflector.FMLCommonHandler_callFuture, this.futureTaskQueue.poll());
                        }
                        else {
                            this.futureTaskQueue.poll().run();
                        }
                    }
                    catch (Throwable var4) {
                        IntegratedServer.logger.fatal((Object)var4);
                    }
                }
            }
        }
        else {
            super.tick();
            if (this.mc.gameSettings.renderDistanceChunks != this.getConfigurationManager().getViewDistance()) {
                IntegratedServer.logger.info("Changing view distance to {}, from {}", new Object[] { this.mc.gameSettings.renderDistanceChunks, this.getConfigurationManager().getViewDistance() });
                this.getConfigurationManager().setViewDistance(this.mc.gameSettings.renderDistanceChunks);
            }
            if (this.mc.theWorld != null) {
                final WorldInfo var5 = this.worldServers[0].getWorldInfo();
                final WorldInfo var6 = this.mc.theWorld.getWorldInfo();
                if (!var5.isDifficultyLocked() && var6.getDifficulty() != var5.getDifficulty()) {
                    IntegratedServer.logger.info("Changing difficulty to {}, from {}", new Object[] { var6.getDifficulty(), var5.getDifficulty() });
                    this.setDifficultyForAllWorlds(var6.getDifficulty());
                }
                else if (var6.isDifficultyLocked() && !var5.isDifficultyLocked()) {
                    IntegratedServer.logger.info("Locking difficulty to {}", new Object[] { var6.getDifficulty() });
                    for (final WorldServer var10 : this.worldServers) {
                        if (var10 != null) {
                            var10.getWorldInfo().setDifficultyLocked(true);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public boolean canStructuresSpawn() {
        return false;
    }
    
    @Override
    public WorldSettings.GameType getGameType() {
        return this.theWorldSettings.getGameType();
    }
    
    @Override
    public void setGameType(final WorldSettings.GameType gameMode) {
        this.getConfigurationManager().func_152604_a(gameMode);
    }
    
    @Override
    public EnumDifficulty getDifficulty() {
        return (this.mc.theWorld == null) ? this.mc.gameSettings.difficulty : this.mc.theWorld.getWorldInfo().getDifficulty();
    }
    
    @Override
    public boolean isHardcore() {
        return this.theWorldSettings.getHardcoreEnabled();
    }
    
    @Override
    public File getDataDirectory() {
        return this.mc.mcDataDir;
    }
    
    @Override
    public boolean isDedicatedServer() {
        return false;
    }
    
    @Override
    protected void finalTick(final CrashReport report) {
        this.mc.crashed(report);
    }
    
    @Override
    public CrashReport addServerInfoToCrashReport(CrashReport report) {
        report = super.addServerInfoToCrashReport(report);
        report.getCategory().addCrashSectionCallable("Type", new Callable() {
            @Override
            public String call() {
                return "Integrated Server (map_client.txt)";
            }
        });
        report.getCategory().addCrashSectionCallable("Is Modded", new Callable() {
            @Override
            public String call() {
                String var1 = ClientBrandRetriever.getClientModName();
                if (!var1.equals("vanilla")) {
                    return "Definitely; Client brand changed to '" + var1 + "'";
                }
                var1 = IntegratedServer.this.getServerModName();
                return var1.equals("vanilla") ? ((Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and both client + server brands are untouched.") : ("Definitely; Server brand changed to '" + var1 + "'");
            }
        });
        return report;
    }
    
    @Override
    public void setDifficultyForAllWorlds(final EnumDifficulty difficulty) {
        super.setDifficultyForAllWorlds(difficulty);
        if (this.mc.theWorld != null) {
            this.mc.theWorld.getWorldInfo().setDifficulty(difficulty);
        }
    }
    
    @Override
    public void addServerStatsToSnooper(final PlayerUsageSnooper playerSnooper) {
        super.addServerStatsToSnooper(playerSnooper);
        playerSnooper.addClientStat("snooper_partner", this.mc.getPlayerUsageSnooper().getUniqueID());
    }
    
    @Override
    public boolean isSnooperEnabled() {
        return Minecraft.getMinecraft().isSnooperEnabled();
    }
    
    @Override
    public String shareToLAN(final WorldSettings.GameType type, final boolean allowCheats) {
        try {
            int var6 = -1;
            var6 = HttpUtil.getSuitableLanPort();
            if (var6 <= 0) {
                var6 = 25564;
            }
            this.getNetworkSystem().addLanEndpoint(null, var6);
            IntegratedServer.logger.info("Started on " + var6);
            this.isPublic = true;
            (this.lanServerPing = new ThreadLanServerPing(this.getMOTD(), var6 + "")).start();
            this.getConfigurationManager().func_152604_a(type);
            this.getConfigurationManager().setCommandsAllowedForAll(allowCheats);
            return var6 + "";
        }
        catch (IOException var7) {
            return null;
        }
    }
    
    @Override
    public void stopServer() {
        super.stopServer();
        if (this.lanServerPing != null) {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }
    
    @Override
    public void initiateShutdown() {
        Futures.getUnchecked((Future)this.addScheduledTask(new Runnable() {
            @Override
            public void run() {
                final ArrayList var1 = Lists.newArrayList((Iterable)IntegratedServer.this.getConfigurationManager().playerEntityList);
                for (final EntityPlayerMP var3 : var1) {
                    IntegratedServer.this.getConfigurationManager().playerLoggedOut(var3);
                }
            }
        }));
        super.initiateShutdown();
        if (this.lanServerPing != null) {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }
    
    public void func_175592_a() {
        this.func_175585_v();
    }
    
    public boolean getPublic() {
        return this.isPublic;
    }
    
    @Override
    public boolean isCommandBlockEnabled() {
        return true;
    }
    
    @Override
    public int getOpPermissionLevel() {
        return 4;
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
