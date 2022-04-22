package stellar.skid.utils.fonts.impl;

import stellar.skid.utils.fonts.api.FontFamily;
import stellar.skid.utils.fonts.api.FontRenderer;
import stellar.skid.utils.fonts.api.FontType;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;

/**
 * @author Artyom Popov
 * @since July 04, 2020
 */
final class SimpleFontFamily extends Int2ObjectAVLTreeMap<FontRenderer> implements FontFamily {

	private final FontType fontType;
	private final java.awt.Font awtFont;

	private SimpleFontFamily(FontType fontType, java.awt.Font awtFont) {
		this.fontType = fontType;
		this.awtFont = awtFont;
	}

	static FontFamily create(FontType fontType, java.awt.Font awtFont) {
		return new SimpleFontFamily(fontType, awtFont);
	}

	@Override
	public FontRenderer ofSize(int size) {
		return computeIfAbsent(size, ignored -> {
			return SimpleFontRenderer.create(awtFont.deriveFont(java.awt.Font.PLAIN, size));
		});
	}

	@Override
	public FontType font() { return fontType; }
}