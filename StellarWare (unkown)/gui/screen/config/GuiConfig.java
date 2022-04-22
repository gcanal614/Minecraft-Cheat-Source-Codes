package stellar.skid.gui.screen.config;

import stellar.skid.StellarWare;
import stellar.skid.gui.screen.click.DiscordGUI;
import stellar.skid.modules.configurations.ConfigManager;
import static stellar.skid.utils.fonts.impl.Fonts.SFTHIN.SFTHIN_17.SFTHIN_17;
import stellar.skid.utils.java.Checks;
import java.nio.file.Path;
import net.minecraft.client.gui.Gui;
import org.jetbrains.annotations.NotNull;

public class GuiConfig {

	/* fields */
	private final String name;
	private boolean selected;

	private int y = 0;
	private int offset = 30;

	/* constructors */
	private GuiConfig(@NotNull String name) {
		this.name = name;
	}

	public static @NotNull GuiConfig of(@NotNull Path path) {
		Checks.notNull(path, "path");
		Checks.check(path.toString().endsWith(ConfigManager.getExtension()), "not config path:");

		String s = path.getFileName().toString();
		String name = s.substring(0, s.length() - ConfigManager.getExtension().length());

		return new GuiConfig(name);
	}

	/* methods */
	public void update() {
		DiscordGUI discordGUI = StellarWare.getInstance().getDiscordGUI();
		this.y = discordGUI.getYCoordinate() + offset + discordGUI.getConfigs().indexOf(this) * 20;
	}

	public void drawScreen(int mouseX, int mouseY) {
		DiscordGUI discordGUI = StellarWare.getInstance().getDiscordGUI();

		int xCoordinate = discordGUI.getXCoordinate();
		int width = discordGUI.getWidth();

		if(isHovered(mouseX, mouseY)) {
			Gui.drawRect(xCoordinate + 45 + 110, y - 6, xCoordinate + 45 + 105 + width, y + SFTHIN_17.getHeight() + 5, 0xFF2F3136);
		}

		SFTHIN_17.drawCenteredString(name.replace(".txt", ""), xCoordinate + 45 + 110 + width / 2F, y, selected ? 0xFFFFFFFF : 0xFF868386);
	}

	public boolean isHovered(int mouseX, int mouseY) {
		DiscordGUI discordGUI = StellarWare.getInstance().getDiscordGUI();

		int xCoordinate = discordGUI.getXCoordinate();
		int width = discordGUI.getWidth();

		return mouseX >= xCoordinate + 45 + 110 && mouseX <= xCoordinate + 45 + 110 + width && mouseY >= y - 6 && mouseY <= y + SFTHIN_17
				.getHeight() + 5;
	}

	public boolean isOutsideOfMenu() {
		DiscordGUI discordGUI = StellarWare.getInstance().getDiscordGUI();

		int yCoordinate = discordGUI.getYCoordinate();
		int height = yCoordinate + discordGUI.getHeight();

		return y < yCoordinate + 30 || y > height - 9;
	}

	//region Lombok
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getName() {
		return name;
	}

	public int getY() {
		return y;
	}
	//endregion
}
