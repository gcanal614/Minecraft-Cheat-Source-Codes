/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.storage;

import club.tifality.module.ModuleManager;
import club.tifality.module.impl.render.TimeChanger;
import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

public class WorldInfo {
    public static final EnumDifficulty DEFAULT_DIFFICULTY = EnumDifficulty.NORMAL;
    private long randomSeed;
    private WorldType terrainType = WorldType.DEFAULT;
    private String generatorOptions = "";
    private int spawnX;
    private int spawnY;
    private int spawnZ;
    private long totalTime;
    private long worldTime;
    private long lastTimePlayed;
    private long sizeOnDisk;
    private NBTTagCompound playerTag;
    private int dimension;
    private String levelName;
    private int saveVersion;
    private int cleanWeatherTime;
    private boolean raining;
    private int rainTime;
    private boolean thundering;
    private int thunderTime;
    private WorldSettings.GameType theGameType;
    private boolean mapFeaturesEnabled;
    private boolean hardcore;
    private boolean allowCommands;
    private boolean initialized;
    private EnumDifficulty difficulty;
    private boolean difficultyLocked;
    private double borderCenterX = 0.0;
    private double borderCenterZ = 0.0;
    private double borderSize = 6.0E7;
    private long borderSizeLerpTime = 0L;
    private double borderSizeLerpTarget = 0.0;
    private double borderSafeZone = 5.0;
    private double borderDamagePerBlock = 0.2;
    private int borderWarningDistance = 5;
    private int borderWarningTime = 15;
    private GameRules theGameRules = new GameRules();

    protected WorldInfo() {
    }

    public WorldInfo(NBTTagCompound nbt) {
        this.randomSeed = nbt.getLong("RandomSeed");
        if (nbt.hasKey("generatorName", 8)) {
            String s = nbt.getString("generatorName");
            this.terrainType = WorldType.parseWorldType(s);
            if (this.terrainType == null) {
                this.terrainType = WorldType.DEFAULT;
            } else if (this.terrainType.isVersioned()) {
                int i = 0;
                if (nbt.hasKey("generatorVersion", 99)) {
                    i = nbt.getInteger("generatorVersion");
                }
                this.terrainType = this.terrainType.getWorldTypeForGeneratorVersion(i);
            }
            if (nbt.hasKey("generatorOptions", 8)) {
                this.generatorOptions = nbt.getString("generatorOptions");
            }
        }
        this.theGameType = WorldSettings.GameType.getByID(nbt.getInteger("GameType"));
        this.mapFeaturesEnabled = nbt.hasKey("MapFeatures", 99) ? nbt.getBoolean("MapFeatures") : true;
        this.spawnX = nbt.getInteger("SpawnX");
        this.spawnY = nbt.getInteger("SpawnY");
        this.spawnZ = nbt.getInteger("SpawnZ");
        this.totalTime = nbt.getLong("Time");
        this.worldTime = nbt.hasKey("DayTime", 99) ? nbt.getLong("DayTime") : this.totalTime;
        this.lastTimePlayed = nbt.getLong("LastPlayed");
        this.sizeOnDisk = nbt.getLong("SizeOnDisk");
        this.levelName = nbt.getString("LevelName");
        this.saveVersion = nbt.getInteger("version");
        this.cleanWeatherTime = nbt.getInteger("clearWeatherTime");
        this.rainTime = nbt.getInteger("rainTime");
        this.raining = nbt.getBoolean("raining");
        this.thunderTime = nbt.getInteger("thunderTime");
        this.thundering = nbt.getBoolean("thundering");
        this.hardcore = nbt.getBoolean("hardcore");
        this.initialized = nbt.hasKey("initialized", 99) ? nbt.getBoolean("initialized") : true;
        if (nbt.hasKey("allowCommands", 99)) {
            this.allowCommands = nbt.getBoolean("allowCommands");
        } else {
            boolean bl = this.allowCommands = this.theGameType == WorldSettings.GameType.CREATIVE;
        }
        if (nbt.hasKey("Player", 10)) {
            this.playerTag = nbt.getCompoundTag("Player");
            this.dimension = this.playerTag.getInteger("Dimension");
        }
        if (nbt.hasKey("GameRules", 10)) {
            this.theGameRules.readFromNBT(nbt.getCompoundTag("GameRules"));
        }
        if (nbt.hasKey("Difficulty", 99)) {
            this.difficulty = EnumDifficulty.getDifficultyEnum(nbt.getByte("Difficulty"));
        }
        if (nbt.hasKey("DifficultyLocked", 1)) {
            this.difficultyLocked = nbt.getBoolean("DifficultyLocked");
        }
        if (nbt.hasKey("BorderCenterX", 99)) {
            this.borderCenterX = nbt.getDouble("BorderCenterX");
        }
        if (nbt.hasKey("BorderCenterZ", 99)) {
            this.borderCenterZ = nbt.getDouble("BorderCenterZ");
        }
        if (nbt.hasKey("BorderSize", 99)) {
            this.borderSize = nbt.getDouble("BorderSize");
        }
        if (nbt.hasKey("BorderSizeLerpTime", 99)) {
            this.borderSizeLerpTime = nbt.getLong("BorderSizeLerpTime");
        }
        if (nbt.hasKey("BorderSizeLerpTarget", 99)) {
            this.borderSizeLerpTarget = nbt.getDouble("BorderSizeLerpTarget");
        }
        if (nbt.hasKey("BorderSafeZone", 99)) {
            this.borderSafeZone = nbt.getDouble("BorderSafeZone");
        }
        if (nbt.hasKey("BorderDamagePerBlock", 99)) {
            this.borderDamagePerBlock = nbt.getDouble("BorderDamagePerBlock");
        }
        if (nbt.hasKey("BorderWarningBlocks", 99)) {
            this.borderWarningDistance = nbt.getInteger("BorderWarningBlocks");
        }
        if (nbt.hasKey("BorderWarningTime", 99)) {
            this.borderWarningTime = nbt.getInteger("BorderWarningTime");
        }
    }

    public WorldInfo(WorldSettings settings2, String name) {
        this.populateFromWorldSettings(settings2);
        this.levelName = name;
        this.difficulty = DEFAULT_DIFFICULTY;
        this.initialized = false;
    }

    public WorldInfo(WorldInfo worldInformation) {
        this.randomSeed = worldInformation.randomSeed;
        this.terrainType = worldInformation.terrainType;
        this.generatorOptions = worldInformation.generatorOptions;
        this.theGameType = worldInformation.theGameType;
        this.mapFeaturesEnabled = worldInformation.mapFeaturesEnabled;
        this.spawnX = worldInformation.spawnX;
        this.spawnY = worldInformation.spawnY;
        this.spawnZ = worldInformation.spawnZ;
        this.totalTime = worldInformation.totalTime;
        this.worldTime = worldInformation.worldTime;
        this.lastTimePlayed = worldInformation.lastTimePlayed;
        this.sizeOnDisk = worldInformation.sizeOnDisk;
        this.playerTag = worldInformation.playerTag;
        this.dimension = worldInformation.dimension;
        this.levelName = worldInformation.levelName;
        this.saveVersion = worldInformation.saveVersion;
        this.rainTime = worldInformation.rainTime;
        this.raining = worldInformation.raining;
        this.thunderTime = worldInformation.thunderTime;
        this.thundering = worldInformation.thundering;
        this.hardcore = worldInformation.hardcore;
        this.allowCommands = worldInformation.allowCommands;
        this.initialized = worldInformation.initialized;
        this.theGameRules = worldInformation.theGameRules;
        this.difficulty = worldInformation.difficulty;
        this.difficultyLocked = worldInformation.difficultyLocked;
        this.borderCenterX = worldInformation.borderCenterX;
        this.borderCenterZ = worldInformation.borderCenterZ;
        this.borderSize = worldInformation.borderSize;
        this.borderSizeLerpTime = worldInformation.borderSizeLerpTime;
        this.borderSizeLerpTarget = worldInformation.borderSizeLerpTarget;
        this.borderSafeZone = worldInformation.borderSafeZone;
        this.borderDamagePerBlock = worldInformation.borderDamagePerBlock;
        this.borderWarningTime = worldInformation.borderWarningTime;
        this.borderWarningDistance = worldInformation.borderWarningDistance;
    }

    public void populateFromWorldSettings(WorldSettings settings2) {
        this.randomSeed = settings2.getSeed();
        this.theGameType = settings2.getGameType();
        this.mapFeaturesEnabled = settings2.isMapFeaturesEnabled();
        this.hardcore = settings2.getHardcoreEnabled();
        this.terrainType = settings2.getTerrainType();
        this.generatorOptions = settings2.getWorldName();
        this.allowCommands = settings2.areCommandsAllowed();
    }

    public NBTTagCompound getNBTTagCompound() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.updateTagCompound(nbttagcompound, this.playerTag);
        return nbttagcompound;
    }

    public NBTTagCompound cloneNBTCompound(NBTTagCompound nbt) {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.updateTagCompound(nbttagcompound, nbt);
        return nbttagcompound;
    }

    private void updateTagCompound(NBTTagCompound nbt, NBTTagCompound playerNbt) {
        nbt.setLong("RandomSeed", this.randomSeed);
        nbt.setString("generatorName", this.terrainType.getWorldTypeName());
        nbt.setInteger("generatorVersion", this.terrainType.getGeneratorVersion());
        nbt.setString("generatorOptions", this.generatorOptions);
        nbt.setInteger("GameType", this.theGameType.getID());
        nbt.setBoolean("MapFeatures", this.mapFeaturesEnabled);
        nbt.setInteger("SpawnX", this.spawnX);
        nbt.setInteger("SpawnY", this.spawnY);
        nbt.setInteger("SpawnZ", this.spawnZ);
        nbt.setLong("Time", this.totalTime);
        nbt.setLong("DayTime", this.worldTime);
        nbt.setLong("SizeOnDisk", this.sizeOnDisk);
        nbt.setLong("LastPlayed", MinecraftServer.getCurrentTimeMillis());
        nbt.setString("LevelName", this.levelName);
        nbt.setInteger("version", this.saveVersion);
        nbt.setInteger("clearWeatherTime", this.cleanWeatherTime);
        nbt.setInteger("rainTime", this.rainTime);
        nbt.setBoolean("raining", this.raining);
        nbt.setInteger("thunderTime", this.thunderTime);
        nbt.setBoolean("thundering", this.thundering);
        nbt.setBoolean("hardcore", this.hardcore);
        nbt.setBoolean("allowCommands", this.allowCommands);
        nbt.setBoolean("initialized", this.initialized);
        nbt.setDouble("BorderCenterX", this.borderCenterX);
        nbt.setDouble("BorderCenterZ", this.borderCenterZ);
        nbt.setDouble("BorderSize", this.borderSize);
        nbt.setLong("BorderSizeLerpTime", this.borderSizeLerpTime);
        nbt.setDouble("BorderSafeZone", this.borderSafeZone);
        nbt.setDouble("BorderDamagePerBlock", this.borderDamagePerBlock);
        nbt.setDouble("BorderSizeLerpTarget", this.borderSizeLerpTarget);
        nbt.setDouble("BorderWarningBlocks", this.borderWarningDistance);
        nbt.setDouble("BorderWarningTime", this.borderWarningTime);
        if (this.difficulty != null) {
            nbt.setByte("Difficulty", (byte)this.difficulty.getDifficultyId());
        }
        nbt.setBoolean("DifficultyLocked", this.difficultyLocked);
        nbt.setTag("GameRules", this.theGameRules.writeToNBT());
        if (playerNbt != null) {
            nbt.setTag("Player", playerNbt);
        }
    }

    public long getSeed() {
        return this.randomSeed;
    }

    public int getSpawnX() {
        return this.spawnX;
    }

    public void setSpawnX(int x) {
        this.spawnX = x;
    }

    public int getSpawnY() {
        return this.spawnY;
    }

    public void setSpawnY(int y) {
        this.spawnY = y;
    }

    public int getSpawnZ() {
        return this.spawnZ;
    }

    public void setSpawnZ(int z) {
        this.spawnZ = z;
    }

    public long getWorldTotalTime() {
        return this.totalTime;
    }

    public void setWorldTotalTime(long time) {
        this.totalTime = time;
    }

    public long getWorldTime() {
        return this.worldTime;
    }

    public void setWorldTime(long time) {
        this.worldTime = ModuleManager.getInstance(TimeChanger.class).isEnabled() ? ((Double)TimeChanger.time.get()).longValue() : time;
    }

    public long getSizeOnDisk() {
        return this.sizeOnDisk;
    }

    public NBTTagCompound getPlayerNBTTagCompound() {
        return this.playerTag;
    }

    public void setSpawn(BlockPos spawnPoint) {
        this.spawnX = spawnPoint.getX();
        this.spawnY = spawnPoint.getY();
        this.spawnZ = spawnPoint.getZ();
    }

    public String getWorldName() {
        return this.levelName;
    }

    public void setWorldName(String worldName) {
        this.levelName = worldName;
    }

    public int getSaveVersion() {
        return this.saveVersion;
    }

    public void setSaveVersion(int version) {
        this.saveVersion = version;
    }

    public long getLastTimePlayed() {
        return this.lastTimePlayed;
    }

    public int getCleanWeatherTime() {
        return this.cleanWeatherTime;
    }

    public void setCleanWeatherTime(int cleanWeatherTimeIn) {
        this.cleanWeatherTime = cleanWeatherTimeIn;
    }

    public boolean isThundering() {
        return this.thundering;
    }

    public void setThundering(boolean thunderingIn) {
        this.thundering = thunderingIn;
    }

    public int getThunderTime() {
        return this.thunderTime;
    }

    public void setThunderTime(int time) {
        this.thunderTime = time;
    }

    public boolean isRaining() {
        return this.raining;
    }

    public void setRaining(boolean isRaining) {
        this.raining = isRaining;
    }

    public int getRainTime() {
        return this.rainTime;
    }

    public void setRainTime(int time) {
        this.rainTime = time;
    }

    public WorldSettings.GameType getGameType() {
        return this.theGameType;
    }

    public void setGameType(WorldSettings.GameType type2) {
        this.theGameType = type2;
    }

    public boolean isMapFeaturesEnabled() {
        return this.mapFeaturesEnabled;
    }

    public void setMapFeaturesEnabled(boolean enabled) {
        this.mapFeaturesEnabled = enabled;
    }

    public boolean isHardcoreModeEnabled() {
        return this.hardcore;
    }

    public void setHardcore(boolean hardcoreIn) {
        this.hardcore = hardcoreIn;
    }

    public WorldType getTerrainType() {
        return this.terrainType;
    }

    public void setTerrainType(WorldType type2) {
        this.terrainType = type2;
    }

    public String getGeneratorOptions() {
        return this.generatorOptions;
    }

    public boolean areCommandsAllowed() {
        return this.allowCommands;
    }

    public void setAllowCommands(boolean allow) {
        this.allowCommands = allow;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public void setServerInitialized(boolean initializedIn) {
        this.initialized = initializedIn;
    }

    public GameRules getGameRulesInstance() {
        return this.theGameRules;
    }

    public double getBorderCenterX() {
        return this.borderCenterX;
    }

    public double getBorderCenterZ() {
        return this.borderCenterZ;
    }

    public double getBorderSize() {
        return this.borderSize;
    }

    public void setBorderSize(double size) {
        this.borderSize = size;
    }

    public long getBorderLerpTime() {
        return this.borderSizeLerpTime;
    }

    public void setBorderLerpTime(long time) {
        this.borderSizeLerpTime = time;
    }

    public double getBorderLerpTarget() {
        return this.borderSizeLerpTarget;
    }

    public void setBorderLerpTarget(double lerpSize) {
        this.borderSizeLerpTarget = lerpSize;
    }

    public void getBorderCenterZ(double posZ) {
        this.borderCenterZ = posZ;
    }

    public void getBorderCenterX(double posX) {
        this.borderCenterX = posX;
    }

    public double getBorderSafeZone() {
        return this.borderSafeZone;
    }

    public void setBorderSafeZone(double amount) {
        this.borderSafeZone = amount;
    }

    public double getBorderDamagePerBlock() {
        return this.borderDamagePerBlock;
    }

    public void setBorderDamagePerBlock(double damage) {
        this.borderDamagePerBlock = damage;
    }

    public int getBorderWarningDistance() {
        return this.borderWarningDistance;
    }

    public void setBorderWarningDistance(int amountOfBlocks) {
        this.borderWarningDistance = amountOfBlocks;
    }

    public int getBorderWarningTime() {
        return this.borderWarningTime;
    }

    public void setBorderWarningTime(int ticks) {
        this.borderWarningTime = ticks;
    }

    public EnumDifficulty getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(EnumDifficulty newDifficulty) {
        this.difficulty = newDifficulty;
    }

    public boolean isDifficultyLocked() {
        return this.difficultyLocked;
    }

    public void setDifficultyLocked(boolean locked) {
        this.difficultyLocked = locked;
    }

    public void addToCrashReport(CrashReportCategory category) {
        category.addCrashSectionCallable("Level seed", new Callable<String>(){

            @Override
            public String call() throws Exception {
                return String.valueOf(WorldInfo.this.getSeed());
            }
        });
        category.addCrashSectionCallable("Level generator", new Callable<String>(){

            @Override
            public String call() throws Exception {
                return String.format("ID %02d - %s, ver %d. Features enabled: %b", WorldInfo.this.terrainType.getWorldTypeID(), WorldInfo.this.terrainType.getWorldTypeName(), WorldInfo.this.terrainType.getGeneratorVersion(), WorldInfo.this.mapFeaturesEnabled);
            }
        });
        category.addCrashSectionCallable("Level generator options", new Callable<String>(){

            @Override
            public String call() throws Exception {
                return WorldInfo.this.generatorOptions;
            }
        });
        category.addCrashSectionCallable("Level spawn location", new Callable<String>(){

            @Override
            public String call() throws Exception {
                return CrashReportCategory.getCoordinateInfo(WorldInfo.this.spawnX, WorldInfo.this.spawnY, WorldInfo.this.spawnZ);
            }
        });
        category.addCrashSectionCallable("Level time", new Callable<String>(){

            @Override
            public String call() throws Exception {
                return String.format("%d game time, %d day time", WorldInfo.this.totalTime, WorldInfo.this.worldTime);
            }
        });
        category.addCrashSectionCallable("Level dimension", new Callable<String>(){

            @Override
            public String call() throws Exception {
                return String.valueOf(WorldInfo.this.dimension);
            }
        });
        category.addCrashSectionCallable("Level storage version", new Callable<String>(){

            @Override
            public String call() throws Exception {
                String s = "Unknown?";
                try {
                    switch (WorldInfo.this.saveVersion) {
                        case 19132: {
                            s = "McRegion";
                            break;
                        }
                        case 19133: {
                            s = "Anvil";
                        }
                    }
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
                return String.format("0x%05X - %s", WorldInfo.this.saveVersion, s);
            }
        });
        category.addCrashSectionCallable("Level weather", new Callable<String>(){

            @Override
            public String call() throws Exception {
                return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", WorldInfo.this.rainTime, WorldInfo.this.raining, WorldInfo.this.thunderTime, WorldInfo.this.thundering);
            }
        });
        category.addCrashSectionCallable("Level game mode", new Callable<String>(){

            @Override
            public String call() throws Exception {
                return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", WorldInfo.this.theGameType.getName(), WorldInfo.this.theGameType.getID(), WorldInfo.this.hardcore, WorldInfo.this.allowCommands);
            }
        });
    }
}

