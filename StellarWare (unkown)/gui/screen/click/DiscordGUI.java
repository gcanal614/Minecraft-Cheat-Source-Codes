package stellar.skid.gui.screen.click;

import stellar.skid.StellarWare;

import stellar.skid.gui.screen.config.ConfigMenu;
import stellar.skid.gui.screen.config.GuiConfig;
import stellar.skid.gui.screen.setting.Manager;
import stellar.skid.gui.screen.setting.Setting;
import static stellar.skid.gui.screen.setting.SettingType.*;
import stellar.skid.modules.EnumModuleType;
import stellar.skid.modules.configurations.ConfigManager;
import stellar.skid.modules.visual.ClickGUI;
import stellar.skid.utils.RenderUtils;
import static stellar.skid.utils.RenderUtils.drawRoundedRect;
import stellar.skid.utils.Timer;
import static stellar.skid.utils.fonts.impl.Fonts.ICONFONT.ICONFONT_35.ICONFONT_35;
import static stellar.skid.utils.fonts.impl.Fonts.SFTHIN.SFTHIN_16.SFTHIN_16;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.Files.walk;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class DiscordGUI extends GuiScreen {

	private final StellarWare stellarWare;

	private int scaling;
	private static final Timer timerScroll = new Timer();

	private int yTab = 63;
	private int xCoordinate = 100;
	private int yCoordinate = 100;
	private int width = 190;
	private int height = 300;
	private final List<Tab> tabs = new ObjectArrayList<>();
	private final List<GuiConfig> configs = new CopyOnWriteArrayList<>();
	private boolean configsOpened;

	public int sWidth() {
		return super.width * 2;
	}

	public int sHeight() {
		return super.height * 2;
	}

	//

	private int x2;
	private int y2;
	private int resX;
	private int resY;
	private boolean dragging;
	private boolean resizing;

	public DiscordGUI(@NotNull StellarWare stellarWare) {
		this.stellarWare = stellarWare;

		for(EnumModuleType category : EnumModuleType.values()) {
			tabs.add(new Tab(this.stellarWare, category, yTab));
			this.yTab += 35;
		}
	}

	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	public void onGuiClosed() {
		for(Setting setting : Manager.getSettingList()) {
			switch(setting.getSettingType()) {
				case SLIDER:
					setting.setDragging(false);
					break;

				case COMBOBOX:
				case SELECTBOX:
					setting.setOpened(false);
					break;

				case TEXTBOX: /* нельзя закрыть гуи, не сняв выделение с текста. но пох, пусть на всякий! */
					setting.setTextHovered(false);
					break;
			}
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if(dragging) {
			this.xCoordinate = x2 + mouseX;
			this.yCoordinate = y2 + mouseY;
		}

		if(resizing) {
			if(resX + mouseX > 190) this.width = resX + mouseX;
			if(resY + mouseY > 300) this.height = resY + mouseY;
		}
		
		drawRoundedRect(xCoordinate, yCoordinate - 10, 45 + 105 + width, yCoordinate + 5, 8, 0xFF202225);
		drawRoundedRect(xCoordinate, yCoordinate, 49,
				height, 6, 0xFF202225);

		String novoline;

		drawRect(xCoordinate, yCoordinate, xCoordinate + 10, yCoordinate + 4, 0xFF202225);

		drawRoundedRect(xCoordinate + 45 + 105, yCoordinate, width,
				height, 4, 0xFF36393E);
		drawRoundedRect(xCoordinate + 7, yCoordinate + 40, 31, 3, 0, 0xFF2F3136);

		drawRect(xCoordinate + 44,
				yCoordinate, xCoordinate + 45 + 110, yCoordinate + height, 0xFF2F3136);
		drawRect(xCoordinate + 44, yCoordinate + 20, xCoordinate + 45 + 105 + width, yCoordinate + 21, 0xFF202225);

		if(!isAnyTabSelected() && !configsOpened) {
			final int color = 0xFF6A7179;

			FontRenderer fontRenderer = mc.fontRendererObj;
			fontRenderer.drawStringWithShadow("<------------", xCoordinate + 59, yCoordinate + 65, color);
			fontRenderer.drawStringWithShadow("Select one of", xCoordinate + 67, yCoordinate + 75, color);
			fontRenderer.drawStringWithShadow("these", xCoordinate + 85, yCoordinate + 85, color);
			fontRenderer.drawStringWithShadow("-------------", xCoordinate + 59, yCoordinate + 95, color);
			fontRenderer.drawStringWithShadow("Enjoy the StellarWare", xCoordinate + 54, yCoordinate + 105, color);
			fontRenderer.drawStringWithShadow("Experience", xCoordinate + 70, yCoordinate + 117, color);
			fontRenderer.drawStringWithShadow("N O V O L I N E", xCoordinate + 64, yCoordinate + 7, color);
			fontRenderer.drawStringWithShadow("Build " + this.stellarWare.getVersion(), xCoordinate + 45 + 105 + (width / 2.0F - fontRenderer.getStringWidth("091019 - B E T A") / 2.0F),
					yCoordinate + 7, color);
		}

		tabs.forEach(tab -> tab.drawScreen(mouseX, mouseY));

		ConfigMenu.drawScreen(mouseX, mouseY);

		RenderUtils.drawFilledCircle(xCoordinate + 22, yCoordinate + 20, 15, 0xFF36393F);
		//this.mc.getTextureManager().bindTexture(new ResourceLocation("stellarWare/clickgui/discord/discord.png"));
		//drawModalRectWithCustomSizedTexture(this.xCoordinate + 7, this.yCoordinate + 5, 30, 30, 30, 30, 30, 30);
		ICONFONT_35.drawString("?",xCoordinate + 14,yCoordinate + 14,0xffffffff);


		if(isDiscord(mouseX, mouseY)) {
			drawRoundedRect(xCoordinate - SFTHIN_16.stringWidth("Discord Server") - 12, yCoordinate + 14, SFTHIN_16.stringWidth("Discord Server") + 7, 10, 5, 0xFF2F2F2F);
			SFTHIN_16.drawString("Discord Server", xCoordinate - SFTHIN_16.stringWidth("Discord Server") - 10, yCoordinate + 16, 0xFFFFFFFF);
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	private boolean isAnyTabSelected() {
		return tabs.stream().anyMatch(Tab::isSelected);
	}

	public @Nullable Tab getSelectedTab() {
		return tabs.stream().filter(Tab::isSelected).findAny().orElse(null);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if(mouseButton == 0) {
			for(Tab tab : tabs) {
				if(tab.isHovered(mouseX, mouseY)) {
					for(Tab other : tabs) {
						other.setSelected(false);
					}

					tab.setSelected(true);
					this.configsOpened = false;
				}
			}

			if(isHovered(mouseX, mouseY)) {
				this.x2 = xCoordinate - mouseX;
				this.y2 = yCoordinate - mouseY;
				this.dragging = true;
			}

			if(isHoveredResize(mouseX, mouseY)) {
				this.resX = width - mouseX;
				this.resY = height - mouseY;
				this.resizing = true;
			}

			if(ConfigMenu.isHovered(mouseX, mouseY)) {
				if(!configsOpened) initConfigs();
				for(Tab tab : tabs) tab.setSelected(false);

				this.configsOpened = true;
			}
		}

		Tab selectedTab = getSelectedTab();
		if(selectedTab != null) selectedTab.mouseClicked(mouseX, mouseY, mouseButton);

		if(isDiscord(mouseX, mouseY)) {
			try {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler https://discord.gg/qXHPgHQ");
			} catch(Exception ignored) {
			}
		}

		if(configsOpened) ConfigMenu.mouseClicked(mouseX, mouseY, mouseButton);

		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	public static @Nullable Scroll scroll() {
		int mouse = Mouse.getDWheel(); // @off

        if(mouse > 0)      return Scroll.UP;
        else if(mouse < 0) return Scroll.DOWN;
        else               return null;
    } // @on

	@Override
	public void mouseReleased(int mouseX, int mouseY, int state) {
		if(state == 0) {
			this.dragging = false;
			this.resizing = false;
		}

		tabs.stream().filter(Tab::isSelected).findFirst().ifPresent(tab -> tab.mouseReleased(mouseX, mouseY, state));
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		Tab selectedTab = getSelectedTab();

		if(keyCode == Keyboard.KEY_ESCAPE && (selectedTab == null || selectedTab.getModuleList().stream().noneMatch(Module::isListening)) && Manager.getSettingList().stream().noneMatch(setting -> // @off
				(setting.getSettingType() == SELECTBOX || setting.getSettingType() == COMBOBOX) && setting.isOpened()
						|| setting.getSettingType() == TEXTBOX && setting.isTextHovered()
		)) { // @on
			if(!ConfigMenu.isTextHovered) {
				mc.displayGuiScreen(null);

				if(mc.currentScreen == null) {
					mc.setIngameFocus();
				}
			}
		} else {
			if(selectedTab != null) {
				selectedTab.keyTyped(typedChar, keyCode);
			}

			ConfigMenu.keyTyped(typedChar, keyCode);
		}
	}

	private boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= xCoordinate && mouseX <= xCoordinate + 45 + 105 + width && mouseY >= yCoordinate - 7 && mouseY <= yCoordinate + 20;
	}

	private boolean isHoveredResize(int mouseX, int mouseY) {
		return mouseX >= xCoordinate + 45 + 105 + width - 5 && mouseX <= xCoordinate + 45 + 105 + width && mouseY >= yCoordinate + height - 5 && mouseY <= yCoordinate + height;
	}

	private boolean isDiscord(int mouseX, int mouseY) {
		return mouseX >= xCoordinate + 7 && mouseX <= xCoordinate + 37 && mouseY >= yCoordinate + 5 && mouseY <= yCoordinate + 35;
	}

	/* CONFIG MENU SECTION */
	public void initConfigs() {
		configs.clear();

		List<Path> configsPaths;

		try {
			String extension = ConfigManager.getExtension();

			configsPaths = walk(stellarWare.getModuleManager().getConfigManager().getConfigsFolder()) //
					.filter(Files::isRegularFile) //
					.filter(s -> s.getFileName().toString().endsWith(extension)) //
					.collect(Collectors.toCollection(ObjectArrayList::new));
		} catch(IOException e) {
			StellarWare.getLogger().error("An I/O error occurred while getting contents of configs folder", e);
			return;
		}

		try {
			for (Path configsPath : configsPaths) {
				configs.add(GuiConfig.of(configsPath));
			}

		} catch(Throwable t) {
			t.printStackTrace();
		}
	}

	//region Lombok
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@NotNull
	public List<GuiConfig> getConfigs() {
		return configs;
	}

	public boolean isConfigsOpened() {
		return configsOpened;
	}

	public int getXCoordinate() {
		return xCoordinate;
	}

	public int getYCoordinate() {
		return yCoordinate;
	}
	//endregion

}
