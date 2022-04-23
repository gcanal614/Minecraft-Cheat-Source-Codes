/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.csgo;

import club.tifality.Tifality;
import club.tifality.gui.csgo.component.Component;
import club.tifality.gui.csgo.component.TabComponent;
import club.tifality.gui.csgo.component.impl.GroupBoxComponent;
import club.tifality.gui.csgo.component.impl.sub.button.ButtonComponentImpl;
import club.tifality.gui.csgo.component.impl.sub.checkBox.CheckBoxTextComponent;
import club.tifality.gui.csgo.component.impl.sub.color.ColorPickerTextComponent;
import club.tifality.gui.csgo.component.impl.sub.comboBox.ComboBoxTextComponent;
import club.tifality.gui.csgo.component.impl.sub.key.KeyBindComponent;
import club.tifality.gui.csgo.component.impl.sub.slider.SliderTextComponent;
import club.tifality.gui.font.TrueTypeFontRenderer;
import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.KeyPressEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.impl.render.ClickGUI;
import club.tifality.module.impl.render.hud.Hud;
import club.tifality.property.Property;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.property.impl.EnumProperty;
import club.tifality.property.impl.MultiSelectEnumProperty;
import club.tifality.utils.StringUtils;
import club.tifality.utils.Wrapper;
import club.tifality.utils.render.Colors;
import club.tifality.utils.render.LockedResolution;
import club.tifality.utils.render.OGLUtils;
import club.tifality.utils.render.RenderingUtils;
import club.tifality.utils.render.TTFUtils;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.lang.invoke.LambdaMetafactory;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class SkeetUI
extends GuiScreen {
    ArrayList<?> themes;
    public static final TrueTypeFontRenderer ICONS_RENDERER;
    public static final TrueTypeFontRenderer GROUP_BOX_HEADER_RENDERER;
    public static final TrueTypeFontRenderer FONT_RENDERER;
    public static final TrueTypeFontRenderer KEYBIND_FONT_RENDERER;
    private static final SkeetUI INSTANCE;
    private static final ResourceLocation BACKGROUND_IMAGE;
    private static final char[] ICONS;
    private static final int TAB_SELECTOR_HEIGHT;
    private static double alpha;
    private static boolean open;
    private final Component rootComponent = new Component(null, 0.0f, 0.0f, 370.0f, 350.0f){

        @Override
        public void drawComponent(LockedResolution lockedResolution, int mouseX, int mouseY) {
            if (SkeetUI.this.dragging) {
                this.setX(Math.max(0.0f, Math.min((float)lockedResolution.getWidth() - this.getWidth(), (float)mouseX - SkeetUI.this.prevX)));
                this.setY(Math.max(0.0f, Math.min((float)lockedResolution.getHeight() - this.getHeight(), (float)mouseY - SkeetUI.this.prevY)));
            }
            float borderX = this.getX();
            float borderY = this.getY();
            float width = this.getWidth();
            float height = this.getHeight();
            Gui.drawRect(borderX, borderY, borderX + width, borderY + height, new Color(10, 10, 10, (int)alpha).getRGB());
            Gui.drawRect(borderX + 0.5f, borderY + 0.5f, borderX + width - 0.5f, borderY + height - 0.5f, new Color(60, 60, 60, (int)alpha).getRGB());
            Gui.drawRect(borderX + 1.0f, borderY + 1.0f, borderX + width - 1.0f, borderY + height - 1.0f, new Color(40, 40, 40, (int)alpha).getRGB());
            Gui.drawRect(borderX + 3.0f, borderY + 3.0f, borderX + width - 3.0f, borderY + height - 3.0f, new Color(47, 47, 47, (int)alpha).getRGB());
            float left = borderX + 3.5f;
            float top = borderY + 3.5f;
            float right = borderX + width - 3.5f;
            float bottom = borderY + height - 3.5f;
            Hud hud = Tifality.INSTANCE.getModuleManager().getModule(Hud.class);
            if (SkeetUI.this.hue > 255.0f) {
                SkeetUI.this.hue = 0.0f;
            }
            float h = SkeetUI.this.hue;
            float h2 = SkeetUI.this.hue + 85.0f;
            float h3 = SkeetUI.this.hue + 170.0f;
            if (h > 255.0f) {
                h = 0.0f;
            }
            if (h2 > 255.0f) {
                h2 -= 255.0f;
            }
            if (h3 > 255.0f) {
                h3 -= 255.0f;
            }
            Color no = Color.getHSBColor(h / 255.0f, 0.55f, 1.0f);
            Color yes = Color.getHSBColor(h2 / 255.0f, 0.55f, 1.0f);
            Color bruh = Color.getHSBColor(h3 / 255.0f, 0.55f, 1.0f);
            SkeetUI.this.hue += 1.0f;
            if (hud.arrayListColorModeProperty.get() == Hud.ArrayListColorMode.RAINBOW) {
                RenderingUtils.drawGradientSideways(left, top, right / 2.0f, bottom, no.getRGB(), yes.getRGB());
                RenderingUtils.drawGradientSideways(left, top, right, bottom, yes.getRGB(), bruh.getRGB());
            } else {
                Gui.drawRect(left, top, right, bottom, SkeetUI.getColor(new Color(21, 21, 21).getRGB()));
            }
            if (hud.arrayListColorModeProperty.get() == Hud.ArrayListColorMode.RAINBOW) {
                Gui.drawRect(left, top, right, bottom, new Color(21, 21, 21, 205).getRGB());
            }
            if (alpha > 20.0) {
                GL11.glEnable(3089);
                OGLUtils.startScissorBox(lockedResolution, (int)left, (int)top, (int)(right - left), (int)(bottom - top));
                Minecraft.getMinecraft().getTextureManager().bindTexture(BACKGROUND_IMAGE);
                RenderingUtils.drawImage(left, top, 325.0f, 275.0f, 1.0f, 1.0f, 1.0f, BACKGROUND_IMAGE);
                RenderingUtils.drawImage(left + 325.0f, top + 2.0f, 325.0f, 275.0f, 1.0f, 1.0f, 1.0f, BACKGROUND_IMAGE);
                RenderingUtils.drawImage(left + 1.0f, top + 275.0f, 325.0f, 275.0f, 1.0f, 1.0f, 1.0f, BACKGROUND_IMAGE);
                RenderingUtils.drawImage(left + 326.0f, top + 277.0f, 325.0f, 275.0f, 1.0f, 1.0f, 1.0f, BACKGROUND_IMAGE);
                GL11.glDisable(3089);
            }
            float xDif = (right - left) / 2.0f;
            RenderingUtils.drawGradientRect(left += 0.5f, top += 0.5f, left + xDif, top + 1.5f - 0.5f, true, Colors.getColor(55, 177, 218, (int)alpha), Colors.getColor(204, 77, 198, (int)alpha));
            RenderingUtils.drawGradientRect(left + xDif, top, right -= 0.5f, top + 1.5f - 0.5f, true, Colors.getColor(204, 77, 198, (int)alpha), Colors.getColor(204, 227, 53, (int)alpha));
            if (alpha >= 70.0) {
                Gui.drawRect(left, top + 1.5f - 1.0f, right, top + 1.5f - 0.5f, 0x70000000);
            }
            for (Component child : this.children) {
                if (child instanceof TabComponent && SkeetUI.this.selectedTab != child) continue;
                child.drawComponent(lockedResolution, mouseX, mouseY);
            }
        }

        @Override
        public void onKeyPress(int keyCode) {
            for (Component child : this.children) {
                if (child instanceof TabComponent && SkeetUI.this.selectedTab != child) continue;
                child.onKeyPress(keyCode);
            }
        }

        @Override
        public void onMouseClick(int mouseX, int mouseY, int button) {
            for (Component child : this.children) {
                if (child instanceof TabComponent && SkeetUI.this.selectedTab != child) continue;
                child.onMouseClick(mouseX, mouseY, button);
            }
            if (button == 0 && this.isHovered(mouseX, mouseY)) {
                for (Component child : this.getChildren()) {
                    if (child instanceof TabComponent && SkeetUI.this.selectedTab == child) {
                        for (Component grandChild : child.getChildren()) {
                            if (!grandChild.isHovered(mouseX, mouseY)) continue;
                            return;
                        }
                        continue;
                    }
                    if (child instanceof TabComponent || !child.isHovered(mouseX, mouseY)) continue;
                    return;
                }
                SkeetUI.this.dragging = true;
                SkeetUI.this.prevX = (float)mouseX - this.getX();
                SkeetUI.this.prevY = (float)mouseY - this.getY();
            }
        }

        @Override
        public void onMouseRelease(int button) {
            super.onMouseRelease(button);
            SkeetUI.this.dragging = false;
        }
    };
    private final Component tabSelectorComponent;
    private double targetAlpha;
    private boolean closed;
    private boolean dragging;
    private float prevX;
    private float prevY;
    private int selectorIndex;
    private TabComponent selectedTab;
    float hue;
    private static final Property<Integer> colorProperty;

    public ArrayList<?> getThemes() {
        return this.themes;
    }

    @Listener
    private void onKeyPressEvent(KeyPressEvent e) {
        switch (e.getKey()) {
            case 54: 
            case 210: {
                if (ClickGUI.clickGuiMode.getValue() != ClickGUI.ClickGuiMode.SKEET) break;
                Wrapper.getMinecraft().displayGuiScreen(this);
                alpha = 0.0;
                this.targetAlpha = 255.0;
                open = true;
                this.closed = false;
            }
        }
    }

    public SkeetUI() {
        ModuleCategory[] arrayOfModuleCategory = ModuleCategory.values();
        int i = arrayOfModuleCategory.length;
        for (int b = 0; b < i; b = (int)((byte)(b + 1))) {
            final ModuleCategory category = arrayOfModuleCategory[b];
            TabComponent categoryTab = new TabComponent(this.rootComponent, StringUtils.upperSnakeCaseToPascal(category.name()), 51.5f, 5.0f, 315.0f, 341.5f){

                @Override
                public void setupChildren() {
                    List<Module> modulesInCategory = Tifality.getInstance().getModuleManager().getModulesForCategory(category);
                    for (Module module : modulesInCategory) {
                        GroupBoxComponent groupBoxComponent = new GroupBoxComponent(this, module.getLabel(), 0.0f, 0.0f, 94.333336f, 6.0f);
                        CheckBoxTextComponent enabledButton = new CheckBoxTextComponent(groupBoxComponent, "Enable", module::isEnabled, module::setEnabled);
                        enabledButton.addChild(new KeyBindComponent((Component)enabledButton, module::getKey, module::setKey, 2.0f, 1.0f));
                        groupBoxComponent.addChild(enabledButton);
                        this.addChild(groupBoxComponent);
                        for (Property property : module.getElements()) {
                            Property enumProperty;
                            ComboBoxTextComponent comboBoxTextComponent = null;
                            if (property.getType() == Integer.class) {
                                Property integerProperty = property;
                                ColorPickerTextComponent colorPickerTextComponent = new ColorPickerTextComponent(groupBoxComponent, property.getLabel(), integerProperty::getValue, integerProperty::setValue, integerProperty::addValueChangeListener, integerProperty::isAvailable);
                                groupBoxComponent.addChild(colorPickerTextComponent);
                            } else if (property.getType() == Boolean.class) {
                                Property booleanProperty = property;
                                CheckBoxTextComponent checkBoxTextComponent = new CheckBoxTextComponent((Component)groupBoxComponent, property.getLabel(), booleanProperty::getValue, booleanProperty::setValue, booleanProperty::isAvailable);
                                groupBoxComponent.addChild(checkBoxTextComponent);
                            } else if (property instanceof DoubleProperty) {
                                DoubleProperty doubleProperty = (DoubleProperty)property;
                                SliderTextComponent sliderTextComponent = new SliderTextComponent(groupBoxComponent, property.getLabel(), doubleProperty::getValue, doubleProperty::setValue, doubleProperty::getMin, doubleProperty::getMax, doubleProperty::getIncrement, doubleProperty::getRepresentation, doubleProperty::isAvailable);
                                groupBoxComponent.addChild(sliderTextComponent);
                            } else if (property instanceof EnumProperty) {
                                enumProperty = (EnumProperty)property;
                                comboBoxTextComponent = new ComboBoxTextComponent(groupBoxComponent, property.getLabel(), (Supplier<Enum<?>[]>)LambdaMetafactory.metafactory(null, null, null, ()Ljava/lang/Object;, getValues(), ()[Ljava/lang/Enum;)((EnumProperty)enumProperty), ((EnumProperty)enumProperty)::setValue, ((EnumProperty)enumProperty)::getValue, () -> null, ((EnumProperty)enumProperty)::isAvailable, false);
                            } else if (property instanceof MultiSelectEnumProperty) {
                                enumProperty = (MultiSelectEnumProperty)property;
                                comboBoxTextComponent = new ComboBoxTextComponent(groupBoxComponent, property.getLabel(), (Supplier<Enum<?>[]>)LambdaMetafactory.metafactory(null, null, null, ()Ljava/lang/Object;, getValues(), ()[Ljava/lang/Enum;)((MultiSelectEnumProperty)enumProperty), ((MultiSelectEnumProperty)enumProperty)::setValue, () -> null, () -> 2.lambda$setupChildren$2((MultiSelectEnumProperty)enumProperty), ((MultiSelectEnumProperty)enumProperty)::isAvailable, true);
                            }
                            if (comboBoxTextComponent != null) {
                                groupBoxComponent.addChild(comboBoxTextComponent);
                            }
                            groupBoxComponent.getChildren().sort(Comparator.comparingDouble(Component::getHeight));
                        }
                    }
                    this.getChildren().sort(Comparator.comparingDouble(Component::getHeight));
                }

                private static /* synthetic */ List lambda$setupChildren$2(MultiSelectEnumProperty enumProperty) {
                    return (List)enumProperty.getValue();
                }
            };
            this.rootComponent.addChild(categoryTab);
        }
        TabComponent configTab = new TabComponent(this.rootComponent, "Settings", 51.5f, 5.0f, 315.0f, 341.5f){

            @Override
            public void setupChildren() {
                GroupBoxComponent configsGroupBox = new GroupBoxComponent(this, "Configs", 8.0f, 8.0f, 94.333336f, 140.0f);
                Consumer<Integer> onPress = button -> {};
                configsGroupBox.addChild(new ButtonComponentImpl((Component)configsGroupBox, "Load", onPress, 88.333336f, 15.0f));
                configsGroupBox.addChild(new ButtonComponentImpl((Component)configsGroupBox, "Save", onPress, 88.333336f, 15.0f));
                configsGroupBox.addChild(new ButtonComponentImpl((Component)configsGroupBox, "Refresh", onPress, 88.333336f, 15.0f));
                configsGroupBox.addChild(new ButtonComponentImpl((Component)configsGroupBox, "Delete", onPress, 88.333336f, 15.0f));
                this.addChild(configsGroupBox);
                GroupBoxComponent guiSettingsGroupBox = new GroupBoxComponent(this, "GUI Settings", 8.0f, 8.0f, 94.333336f, 30.0f);
                this.addChild(guiSettingsGroupBox);
            }
        };
        this.rootComponent.addChild(configTab);
        this.selectedTab = (TabComponent)this.rootComponent.getChildren().get(this.selectorIndex);
        this.tabSelectorComponent = new Component(this.rootComponent, 3.5f, 5.0f, 48.0f, 341.5f){
            private double selectorY;

            @Override
            public void onMouseClick(int mouseX, int mouseY, int button) {
                float mouseYOffset;
                if (this.isHovered(mouseX, mouseY) && (mouseYOffset = (float)mouseY - SkeetUI.this.tabSelectorComponent.getY() - 10.0f) > 0.0f && mouseYOffset < SkeetUI.this.tabSelectorComponent.getHeight() - 10.0f) {
                    SkeetUI.this.selectorIndex = Math.min(ICONS.length - 1, (int)(mouseYOffset / (float)TAB_SELECTOR_HEIGHT));
                    SkeetUI.this.selectedTab = (TabComponent)SkeetUI.this.rootComponent.getChildren().get(SkeetUI.this.selectorIndex);
                }
            }

            @Override
            public void drawComponent(LockedResolution resolution, int mouseX, int mouseY) {
                this.selectorY = RenderingUtils.progressiveAnimation(this.selectorY, SkeetUI.this.selectorIndex * TAB_SELECTOR_HEIGHT + 10, 1.5);
                float x = this.getX();
                float y = this.getY();
                float width = this.getWidth();
                float height = this.getHeight();
                int innerColor = new Color(0, 0, 0, (int)alpha).getRGB();
                int outerColor = new Color(48, 48, 48, (int)alpha).getRGB();
                Gui.drawRect((double)x, (double)y, (double)(x + width), (double)y + this.selectorY, SkeetUI.getColor(789516));
                Gui.drawRect((double)(x + width - 1.0f), (double)y, (double)(x + width), (double)y + this.selectorY, innerColor);
                Gui.drawRect((double)(x + width - 0.5f), (double)y, (double)(x + width), (double)y + this.selectorY, outerColor);
                Gui.drawRect((double)x, (double)y + this.selectorY - 1.0, (double)(x + width - 0.5f), (double)y + this.selectorY, innerColor);
                Gui.drawRect((double)x, (double)y + this.selectorY - 0.5, (double)(x + width), (double)y + this.selectorY, outerColor);
                Gui.drawRect((double)x, (double)y + this.selectorY + (double)TAB_SELECTOR_HEIGHT, (double)(x + width), (double)(y + height), SkeetUI.getColor(789516));
                Gui.drawRect((double)(x + width - 1.0f), (double)y + this.selectorY + (double)TAB_SELECTOR_HEIGHT, (double)(x + width), (double)(y + height), innerColor);
                Gui.drawRect((double)(x + width - 0.5f), (double)y + this.selectorY + (double)TAB_SELECTOR_HEIGHT, (double)(x + width), (double)(y + height), outerColor);
                Gui.drawRect((double)x, (double)y + this.selectorY + (double)TAB_SELECTOR_HEIGHT, (double)(x + width - 0.5f), (double)y + this.selectorY + (double)TAB_SELECTOR_HEIGHT + 1.0, innerColor);
                Gui.drawRect((double)x, (double)y + this.selectorY + (double)TAB_SELECTOR_HEIGHT, (double)(x + width), (double)y + this.selectorY + (double)TAB_SELECTOR_HEIGHT + 0.5, outerColor);
                if (SkeetUI.shouldRenderText()) {
                    for (int i = 0; i < ICONS.length; ++i) {
                        String c = String.valueOf(ICONS[i]);
                        ICONS_RENDERER.drawString(c, x + 24.0f - ICONS_RENDERER.getWidth(c) / 2.0f - 1.0f, y + 10.0f + (float)(i * TAB_SELECTOR_HEIGHT) + (float)TAB_SELECTOR_HEIGHT / 2.0f - ICONS_RENDERER.getHeight(c) / 2.0f, SkeetUI.getColor(i == SkeetUI.this.selectorIndex ? new Color(185, 185, 185, (int)alpha).getRGB() : new Color(100, 100, 100, (int)alpha).getRGB()));
                    }
                }
            }
        };
        this.rootComponent.addChild(this.tabSelectorComponent);
    }

    public static double getAlpha() {
        return alpha;
    }

    public static int getColor() {
        return SkeetUI.getColor(colorProperty.getValue());
    }

    public static boolean shouldRenderText() {
        return alpha > 20.0;
    }

    private static boolean isVisible() {
        return open || !(alpha <= 0.0);
    }

    public static int getColor(int color) {
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;
        int a = (int)alpha;
        return (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF | (a & 0xFF) << 24;
    }

    public static void init() {
        Tifality.getInstance().getEventBus().subscribe(INSTANCE);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            if (open) {
                this.targetAlpha = 0.0;
                open = false;
                this.dragging = false;
            }
        } else {
            this.rootComponent.onKeyPress(keyCode);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (!open && alpha == 0.0 && !this.closed) {
            Wrapper.getMinecraft().displayGuiScreen(null);
            return;
        }
        if (SkeetUI.isVisible()) {
            alpha = RenderingUtils.linearAnimation(alpha, this.targetAlpha, 50.0);
            this.rootComponent.drawComponent(RenderingUtils.getLockedResolution(), mouseX, mouseY);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (SkeetUI.isVisible()) {
            this.rootComponent.onMouseClick(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (SkeetUI.isVisible()) {
            this.rootComponent.onMouseRelease(state);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    static {
        GROUP_BOX_HEADER_RENDERER = Wrapper.getCSGOFontRenderer();
        BACKGROUND_IMAGE = new ResourceLocation("tifality/skeetchainmail.png");
        ICONS = new char[]{'E', 'F', 'J', 'C', 'I', 'H'};
        TAB_SELECTOR_HEIGHT = 321 / ICONS.length;
        ICONS_RENDERER = new TrueTypeFontRenderer(TTFUtils.getFontFromLocation("icons.ttf", 38), true, true);
        ICONS_RENDERER.generateTextures();
        FONT_RENDERER = new TrueTypeFontRenderer(new Font("Tahoma", 0, 11), false, true);
        FONT_RENDERER.generateTextures();
        KEYBIND_FONT_RENDERER = new TrueTypeFontRenderer(new Font("Tahoma", 0, 9), false, true);
        KEYBIND_FONT_RENDERER.generateTextures();
        INSTANCE = new SkeetUI();
        colorProperty = new Property<Integer>("GUI Color", 10077246);
    }
}

