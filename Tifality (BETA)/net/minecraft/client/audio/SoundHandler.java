/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ISoundEventAccessor;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundList;
import net.minecraft.client.audio.SoundListSerializer;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SoundHandler
implements IResourceManagerReloadListener,
ITickable {
    private static final Logger logger = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter((Type)((Object)SoundList.class), new SoundListSerializer()).create();
    private static final ParameterizedType TYPE = new ParameterizedType(){

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{String.class, SoundList.class};
        }

        @Override
        public Type getRawType() {
            return Map.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    };
    public static final SoundPoolEntry missing_sound = new SoundPoolEntry(new ResourceLocation("meta:missing_sound"), 0.0, 0.0, false);
    private final SoundRegistry sndRegistry = new SoundRegistry();
    private final SoundManager sndManager;
    private final IResourceManager mcResourceManager;

    public SoundHandler(IResourceManager p_i45122_1_, GameSettings p_i45122_2_) {
        this.mcResourceManager = p_i45122_1_;
        this.sndManager = new SoundManager(this, p_i45122_2_);
    }

    @Override
    public void onResourceManagerReload(IResourceManager p_onResourceManagerReload_1_) {
        this.sndManager.reloadSoundSystem();
        this.sndRegistry.clearMap();
        for (String lvt_3_1_ : p_onResourceManagerReload_1_.getResourceDomains()) {
            try {
                List<IResource> lvt_4_1_ = p_onResourceManagerReload_1_.getAllResources(new ResourceLocation(lvt_3_1_, "sounds.json"));
                for (IResource lvt_6_1_ : lvt_4_1_) {
                    try {
                        Map<String, SoundList> lvt_7_1_ = this.getSoundMap(lvt_6_1_.getInputStream());
                        for (Map.Entry<String, SoundList> lvt_9_1_ : lvt_7_1_.entrySet()) {
                            this.loadSoundResource(new ResourceLocation(lvt_3_1_, lvt_9_1_.getKey()), lvt_9_1_.getValue());
                        }
                    }
                    catch (RuntimeException var10) {
                        logger.warn("Invalid sounds.json", (Throwable)var10);
                    }
                }
            }
            catch (IOException iOException) {
            }
        }
    }

    protected Map<String, SoundList> getSoundMap(InputStream p_getSoundMap_1_) {
        Map var2;
        try {
            var2 = (Map)GSON.fromJson((Reader)new InputStreamReader(p_getSoundMap_1_), (Type)TYPE);
        }
        finally {
            IOUtils.closeQuietly(p_getSoundMap_1_);
        }
        return var2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private void loadSoundResource(ResourceLocation p_loadSoundResource_1_, SoundList p_loadSoundResource_2_) {
        SoundEventAccessorComposite lvt_3_2_;
        boolean lvt_4_1_;
        boolean bl = lvt_4_1_ = !this.sndRegistry.containsKey(p_loadSoundResource_1_);
        if (!lvt_4_1_ && !p_loadSoundResource_2_.canReplaceExisting()) {
            lvt_3_2_ = (SoundEventAccessorComposite)this.sndRegistry.getObject(p_loadSoundResource_1_);
        } else {
            if (!lvt_4_1_) {
                logger.debug("Replaced sound event location {}", p_loadSoundResource_1_);
            }
            lvt_3_2_ = new SoundEventAccessorComposite(p_loadSoundResource_1_, 1.0, 1.0, p_loadSoundResource_2_.getSoundCategory());
            this.sndRegistry.registerSound(lvt_3_2_);
        }
        block10: for (final SoundList.SoundEntry lvt_6_1_ : p_loadSoundResource_2_.getSoundList()) {
            ISoundEventAccessor<SoundPoolEntry> lvt_10_3_;
            String lvt_7_1_ = lvt_6_1_.getSoundEntryName();
            ResourceLocation lvt_8_1_ = new ResourceLocation(lvt_7_1_);
            final String lvt_9_1_ = lvt_7_1_.contains(":") ? lvt_8_1_.getResourceDomain() : p_loadSoundResource_1_.getResourceDomain();
            switch (lvt_6_1_.getSoundEntryType()) {
                case FILE: {
                    ResourceLocation lvt_11_1_ = new ResourceLocation(lvt_9_1_, "sounds/" + lvt_8_1_.getResourcePath() + ".ogg");
                    InputStream lvt_12_1_ = null;
                    try {
                        lvt_12_1_ = this.mcResourceManager.getResource(lvt_11_1_).getInputStream();
                    }
                    catch (FileNotFoundException var18) {
                        logger.warn("File {} does not exist, cannot add it to event {}", lvt_11_1_, p_loadSoundResource_1_);
                        IOUtils.closeQuietly(lvt_12_1_);
                        continue block10;
                    }
                    catch (IOException var19) {
                        logger.warn("Could not load sound file " + lvt_11_1_ + ", cannot add it to event " + p_loadSoundResource_1_, (Throwable)var19);
                        {
                            catch (Throwable throwable) {
                                IOUtils.closeQuietly(lvt_12_1_);
                                throw throwable;
                            }
                        }
                        IOUtils.closeQuietly(lvt_12_1_);
                        continue block10;
                    }
                    IOUtils.closeQuietly(lvt_12_1_);
                    lvt_10_3_ = new SoundEventAccessor(new SoundPoolEntry(lvt_11_1_, lvt_6_1_.getSoundEntryPitch(), lvt_6_1_.getSoundEntryVolume(), lvt_6_1_.isStreaming()), lvt_6_1_.getSoundEntryWeight());
                    break;
                }
                case SOUND_EVENT: {
                    lvt_10_3_ = new ISoundEventAccessor<SoundPoolEntry>(){
                        final ResourceLocation field_148726_a;
                        {
                            this.field_148726_a = new ResourceLocation(lvt_9_1_, lvt_6_1_.getSoundEntryName());
                        }

                        @Override
                        public int getWeight() {
                            SoundEventAccessorComposite lvt_1_1_ = (SoundEventAccessorComposite)SoundHandler.this.sndRegistry.getObject(this.field_148726_a);
                            return lvt_1_1_ == null ? 0 : lvt_1_1_.getWeight();
                        }

                        @Override
                        public SoundPoolEntry cloneEntry() {
                            SoundEventAccessorComposite lvt_1_1_ = (SoundEventAccessorComposite)SoundHandler.this.sndRegistry.getObject(this.field_148726_a);
                            return lvt_1_1_ == null ? missing_sound : lvt_1_1_.cloneEntry();
                        }
                    };
                    break;
                }
                default: {
                    throw new IllegalStateException("IN YOU FACE");
                }
            }
            lvt_3_2_.addSoundToEventPool(lvt_10_3_);
        }
    }

    public SoundEventAccessorComposite getSound(ResourceLocation p_getSound_1_) {
        return (SoundEventAccessorComposite)this.sndRegistry.getObject(p_getSound_1_);
    }

    public void playSound(ISound p_playSound_1_) {
        this.sndManager.playSound(p_playSound_1_);
    }

    public void playDelayedSound(ISound p_playDelayedSound_1_, int p_playDelayedSound_2_) {
        this.sndManager.playDelayedSound(p_playDelayedSound_1_, p_playDelayedSound_2_);
    }

    public void setListener(EntityPlayer p_setListener_1_, float p_setListener_2_) {
        this.sndManager.setListener(p_setListener_1_, p_setListener_2_);
    }

    public void pauseSounds() {
        this.sndManager.pauseAllSounds();
    }

    public void stopSounds() {
        this.sndManager.stopAllSounds();
    }

    public void unloadSounds() {
        this.sndManager.unloadSoundSystem();
    }

    @Override
    public void update() {
        this.sndManager.updateAllSounds();
    }

    public void resumeSounds() {
        this.sndManager.resumeAllSounds();
    }

    public void setSoundLevel(SoundCategory p_setSoundLevel_1_, float p_setSoundLevel_2_) {
        if (p_setSoundLevel_1_ == SoundCategory.MASTER && p_setSoundLevel_2_ <= 0.0f) {
            this.stopSounds();
        }
        this.sndManager.setSoundCategoryVolume(p_setSoundLevel_1_, p_setSoundLevel_2_);
    }

    public void stopSound(ISound p_stopSound_1_) {
        this.sndManager.stopSound(p_stopSound_1_);
    }

    public SoundEventAccessorComposite getRandomSoundFromCategories(SoundCategory ... p_getRandomSoundFromCategories_1_) {
        ArrayList<SoundEventAccessorComposite> lvt_2_1_ = Lists.newArrayList();
        for (ResourceLocation lvt_4_1_ : this.sndRegistry.getKeys()) {
            SoundEventAccessorComposite lvt_5_1_ = (SoundEventAccessorComposite)this.sndRegistry.getObject(lvt_4_1_);
            if (!ArrayUtils.contains((Object[])p_getRandomSoundFromCategories_1_, (Object)lvt_5_1_.getSoundCategory())) continue;
            lvt_2_1_.add(lvt_5_1_);
        }
        if (lvt_2_1_.isEmpty()) {
            return null;
        }
        return (SoundEventAccessorComposite)lvt_2_1_.get(new Random().nextInt(lvt_2_1_.size()));
    }

    public boolean isSoundPlaying(ISound p_isSoundPlaying_1_) {
        return this.sndManager.isSoundPlaying(p_isSoundPlaying_1_);
    }
}

