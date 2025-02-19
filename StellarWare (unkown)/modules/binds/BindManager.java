package stellar.skid.modules.binds;

import stellar.skid.StellarWare;

import stellar.skid.modules.ModuleArrayMap;
import stellar.skid.modules.ModuleManager;
import stellar.skid.modules.configurations.holder.ModuleHolder;
import stellar.skid.modules.serializers.KeybindSerializer;
import stellar.skid.utils.notifications.NotificationType;
import com.google.common.reflect.TypeToken;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unimi.dsi.fastutil.objects.ObjectSet;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author xDelsy
 */
public final class BindManager {

	/* fields */
	private final ModuleManager moduleManager;
	private final int configVersion;

	private Path bindsFile;

	/* constructors */
	public BindManager(@NotNull ModuleManager moduleManager, int configVersion) {
		this.moduleManager = moduleManager;
		this.configVersion = configVersion;
	}

	/* methods */
	public void load() {
		ModuleArrayMap modules = moduleManager.getModuleManager();
		Map<@NotNull String, @NotNull ModuleKeybind> binds = readBindsFromFile();

		for(Map.Entry<String, ModuleKeybind> bindEntry : binds.entrySet()) {
			String moduleName = bindEntry.getKey();
			ModuleKeybind keybind = bindEntry.getValue();

			for(Map.Entry<String, ModuleHolder<?>> holderEntry : modules.entrySet()) {
				if(holderEntry.getKey().equalsIgnoreCase(moduleName)) {
					holderEntry.getValue().getModule().setKeyBind(keybind);
				}
			}
		}
	}

	public boolean save() {
		boolean logged = false;

		try {
			Path path = getBindsFileAndCreateIfNotExists();
			if(path == null) return false;

			ModuleArrayMap moduleManager = this.moduleManager.getModuleManager();
			ObjectSet<Map.Entry<String, ModuleHolder<?>>> entries = moduleManager.entrySet();
			Stream<Map.Entry<String, ModuleHolder<?>>> stream = entries.stream();

			Map<String, ModuleKeybind> modules = stream.collect(Collectors.toMap(
					Map.Entry::getKey,
					e -> e.getValue().getModule().getKeybind().get()
			));

			ConfigurationOptions options = defaultOptions();
			HoconConfigurationLoader loader = HoconConfigurationLoader.builder().setDefaultOptions(options).setPath(path).build();
			ConfigurationNode node;

			try {
				node = loader.load(options);
			} catch(IOException e) {
				StellarWare.getLogger().warn("An I/O error occurred while reading binds file!", e);
				logged = true;
				throw e;
			}

			try {
				node.setValue(new TypeToken<Map<String, ModuleKeybind>>() {}, modules);
			} catch(ObjectMappingException e) {
				StellarWare.getLogger().warn("An I/O error occurred while serializing binds!", e);
				logged = true;
				throw e;
			}

			try {
				loader.save(node);
			} catch(IOException e) {
				StellarWare.getLogger().warn("An I/O error occurred while creating binds file!", e);
				logged = true;
				throw e;
			}

			return true;
		} catch(Throwable t) {
			StellarWare.getInstance().getNotificationManager().pop("Cannot save binds!", 5_000, NotificationType.ERROR);

			if(!logged) {
				StellarWare.getLogger().warn("An unexpected error occurred while deleting config file!", t);
			}

			return false;
		}
	}

	private @NotNull Map<@NotNull String, @NotNull ModuleKeybind> readBindsFromFile() {
		Path path = getBindsFile();

		if(Files.notExists(path)) {
			StellarWare.getLogger().error("Binds file doesn't exist");
			return Collections.emptyMap();
		}

		ConfigurationOptions options = defaultOptions();
		HoconConfigurationLoader loader = HoconConfigurationLoader.builder().setDefaultOptions(options).setPath(path).build();
		ConfigurationNode node;

		try {
			node = loader.load(options);
		} catch(IOException e) {
			StellarWare.getLogger().error("An I/O error occurred while reading binds", e);
			return Collections.emptyMap();
		}

		Map<String, ModuleKeybind> bindMap = Collections.emptyMap();

		try {
			bindMap = node.getValue(new TypeToken<Map<String, ModuleKeybind>>() {});

			if(bindMap == null) {
				return Collections.emptyMap();
			}

			return bindMap;
		} catch(ObjectMappingException e) {
			StellarWare.getLogger().error("An I/O error occurred while deserializing binds", e);
			return bindMap;
		}
	}

	private @Nullable Path getBindsFileAndCreateIfNotExists() {
		Path path = getBindsFile();

		if(Files.notExists(path)) {
			try {
				Files.createFile(path);
			} catch(IOException e) {
				StellarWare.getLogger().warn("An I/O error occurred while creating binds file!", e);
				return null;
			}
		}

		return path;
	}

	public @NotNull Path getBindsFile() {
		if(bindsFile == null) return this.bindsFile = moduleManager.getStellarWare().getDataFolder().resolve("binds.cfg");
		return bindsFile;
	}

	//region Hocon-loader options
	private static ConfigurationOptions DEFAULT_OPTIONS;

	private @NotNull ConfigurationOptions defaultOptions() {
		if(DEFAULT_OPTIONS == null) {
			ConfigurationOptions defaults = ConfigurationOptions.defaults();
			defaults.getSerializers().registerType(TypeToken.of(ModuleKeybind.class), new KeybindSerializer());

			return DEFAULT_OPTIONS = defaults;
		} else {
			return DEFAULT_OPTIONS;
		}
	}
	//endregion

}
