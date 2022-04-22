package me.injusttice.neutron.ui.clickguis;

import java.awt.Color;
import java.io.IOException;

import me.injusttice.neutron.api.settings.impl.*;
import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.utils.font.Fonts;
import me.injusttice.neutron.utils.font.MCFontRenderer;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.ModuleCategory;
import me.injusttice.neutron.api.settings.Setting;
import me.injusttice.neutron.utils.render.ColorUtil;
import me.injusttice.neutron.utils.render.Render2DUtils;
import me.injusttice.neutron.utils.render.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class BetaClickGui extends GuiScreen {
  public Category draggingCategory;

  public int startX;

  public int startY;

  public StringSet stringSet;

  public DoubleSet numb;

  public double diff = 2.0D;

  public double transX;

  public double transY;

  MCFontRenderer fr;

  int categoryTextColor = (new Color(15724527)).getRGB();

  int settingValColor = (new Color(15724527)).getRGB();

  public BetaClickGui() {
    fr = new MCFontRenderer(Fonts.fontFromTTF(new ResourceLocation("Desync/fonts/niggas.ttf"), 18, 0), true, true);
  }

  public Color gradientCol() {
    return getGradientOffset(new Color(191, 191, 191), new Color(79, 79, 79), Math.abs(System.currentTimeMillis() / 30L) / 50.0D);
  }

  public boolean hovered(float left, float top, float right, float bottom, int mouseX, int mouseY) {
    return (mouseX >= left && mouseY >= top && mouseX < right && mouseY < bottom);
  }

  public void initGui() {
    transX = 0.0D;
    transY = 0.0D;
    stringSet = null;
  }

  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    ScaledResolution sr = new ScaledResolution(mc);
    transY += 18.0D + transY * 0.01D;
    if (transY < sr.getScaledHeight()) {
      Render2DUtils.prepareScissorBox(0.0D, (height / 2) - transY, width, (height / 2) + transY);
      int color = (new Color(1241513984, true)).getRGB();
      Gui.drawRect(0.0D, 0.0D, width, (height / 2) - transY - 1.0D, color);
      Gui.drawRect(0.0D, (height / 2) + transY, width, height, color);
      GL11.glEnable(3089);
      handleGUI(mouseX, mouseY, -1, 'T', -1, HandleType.RENDER);
      GL11.glDisable(3089);
    } else {
      handleGUI(mouseX, mouseY, -1, 'T', -1, HandleType.RENDER);
    }
  }

  public void mouseClicked(int mouseX, int mouseY, int button) {
    handleGUI(mouseX, mouseY, button, 'T', -1, HandleType.BUTTON_PRESSED);
  }

  public void mouseReleased(int mouseX, int mouseY, int button) {
    handleGUI(mouseX, mouseY, button, 'T', -1, HandleType.BUTTON_RELEASED);
  }

  public void keyTyped(char typedChar, int keyCode) {
    handleGUI(0, 0, -1, typedChar, keyCode, HandleType.KEY_TYPED);
    try {
      super.keyTyped(typedChar, keyCode);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  int gCol(Category c) {
    return c.pastelColor.getRGB();
  }

  int moduleTextColor(Module m) {
    if (m.isExpanded())
      return gCol(m.getCategory());
    return (new Color(-1, true)).getRGB();
  }

  int settingNameColor() {
    return (new Color(16185078)).getRGB();
  }

  int modeValColor(Module m) {
    return gradientCol().getRGB();
  }

  int subCategoryTextColor(Module m) {
    return gradientCol().getRGB();
  }

  int categoryRectColor() {
    return (new Color(50,50,50)).getRGB();
  }

  int modRectColor(Module m, boolean hovered) {
    if (m.isExpanded())
      return categoryRectColor();
    if (m.isToggled()) {
      if (hovered)
        return gCol(m.getCategory());
      return gCol(m.getCategory());
    }
    if (hovered)
      return (new Color(1184274)).getRGB();
    return (new Color(1052688)).getRGB();
  }

  int settingOutLineColor(Module m) {
    return (new Color(789516)).getRGB();
  }

  int sliderColor(Module m, boolean hovered) {
    return gCol(m.getCategory());
  }

  int settingRectColor(boolean hovered, Category c) {
    if (hovered)
      return (new Color(592137)).getRGB();
    return categoryRectColor();
  }

  int settingOnCategoryRectColor(boolean hovered, Category c) {
    if (hovered)
      return (new Color(1778384896, true)).getRGB();
    return (new Color(1258291200, true)).getRGB();
  }

  int outlineColor(Category c) {
    return gCol(c);
  }

  private void drawGradient() {
    Color refColor = new Color(ColorUtil.waveRainbow(2.0F, 0.5F, 1.0F, 0L));
    int firstColor = (new Color(refColor.getRed(), refColor.getGreen(), refColor.getGreen(), 33)).getRGB();
    int secondColor = (new Color(refColor.getRed(), refColor.getGreen(), refColor.getGreen(), 0)).getRGB();
    drawGradientRect(0.0F, (height - 100), width, height, secondColor, firstColor);
  }

  public void handleGUI(int mouseX, int mouseY, int button, char keyChar, int keyCode, HandleType handleType) {
    double outlineWidth = 0.5D;
    if (handleType == HandleType.BUTTON_RELEASED && button == 0) {
      draggingCategory = null;
      numb = null;
    }
    if (draggingCategory != null) {
      draggingCategory.posX = mouseX - startX;
      draggingCategory.posY = mouseY - startY;
    }
    double countY = 0.0D;
    int incrementVal = 23;
    int staticHeight = 15;
    int count2 = 20;
    for (Category c : Category.values()) {
      float offset = (c.posX + count2);
      float top = c.posY + 12.0F + 0.0F;
      float width = 118.0F;
      float right = offset + width;
      boolean categoryhovered = hovered(offset, top, right, top + staticHeight, mouseX, mouseY);
      count2 += 125;
      if (handleType == HandleType.BUTTON_PRESSED && categoryhovered && button == 0) {
        draggingCategory = c;
        startX = mouseX - c.posX;
        startY = mouseY - c.posY;
        return;
      }
      if (handleType == HandleType.BUTTON_PRESSED && categoryhovered && button == 1) {
        c.expanded = !c.expanded;
        return;
      }
      double count = 0.0D;
      String categoryText = c.name;
      RenderUtil.drawBordered(offset, (top + 0.0F), (offset + width), (top + staticHeight), 0.5F, new Color(50,50,50).getRGB(), new Color(141, 68, 173).getRGB());
      double textDif = 20.0D;
      fr.drawStringWithShadow(categoryText, (3.0F + offset + 2.0F), (top + 5.0F) - 0.5D, categoryTextColor);
      if (c.expanded) {
        for (Module m : NeutronMain.instance.moduleManager.getModulesByCategory(c)) {
          boolean hoveringModule = hovered(offset, top + staticHeight + (float)count, offset + width, (float)((top + staticHeight) + count + staticHeight), mouseX, mouseY);
          if (handleType == HandleType.BUTTON_PRESSED && hoveringModule && button == 0) {
            m.toggle();
            return;
          }
          if (handleType == HandleType.BUTTON_PRESSED && hoveringModule && button == 1) {
            if (!m.getSettings().isEmpty())
              for (Module mod : NeutronMain.instance.moduleManager.getModulesByCategory(m.getCategory())) {
                if (mod == m)
                  continue;
                mod.setExpanded(false);
              }
            if (!m.getSettings().isEmpty())
              m.setExpanded(!m.isExpanded());
            return;
          }
          Gui.drawRect(offset, (top + staticHeight) + count, (offset + width), (top + staticHeight) + count + staticHeight, modRectColor(m, hoveringModule));
          fr.drawStringWithShadow(m.getName(), offset + 4.0F, (1.0F + top) + textDif + count - 1, moduleTextColor(m));
          if (!m.getSettings().isEmpty())
            if(m.isExpanded()) {
              fr.drawStringWithShadow("v", (offset + width - 4.0F - fr.getStringWidth("v")), (top + 0.0F) + textDif + count - 1, categoryTextColor);
            } else {
              fr.drawStringWithShadow(">", (offset + width - 4.0F - fr.getStringWidth(">")), (top + 0.0F) + textDif + count - 1, categoryTextColor);
            }
          Gui.drawRect(offset, (top + staticHeight) + count, (offset + 2.0F), (top + staticHeight) + count + staticHeight, categoryRectColor());
          Gui.drawRect((offset + width - 2.0F), (top + staticHeight) + count, (offset + width), (top + staticHeight) + count + staticHeight, categoryRectColor());
          count += staticHeight;
          if (m.isExpanded()) {
            for (Setting s : m.getSettings()) {
              double difference = 5.0D;
              double doubleSetDifference = 1.0D;
              double increment = 12.0D;
              textDif = 21.5D;
              Gui.drawRect(offset, (top + staticHeight) + count, (offset + 2.0F), top + increment + count + staticHeight, categoryRectColor());
              Gui.drawRect((offset + width - 2.0F), (top + staticHeight) + count, (offset + width), top + increment + count + staticHeight, categoryRectColor());
              boolean hoveringSetting = hovered(offset, (float)((top + staticHeight) + count), offset + width, (float)(top + increment + (float)count + staticHeight), mouseX, mouseY);
              if (s instanceof SubCategory) {
                Gui.drawRect(offset + diff, (top + staticHeight) + count, (offset + width) - diff, top + increment + count + staticHeight, settingRectColor(false, m.getCategory()));
                fr.drawCenteredStringWithShadow(s.name, (offset + width / 25), (float) ((25 + top) + textDif - staticHeight - increment + count), (new Color(14606046)).getRGB());
                count += increment;
              }
              if (s instanceof StringSet) {
                StringSet strs = (StringSet)s;
                Gui.drawRect(offset + diff, (top + staticHeight) + count, (offset + width) - diff, top + increment + count + staticHeight, settingRectColor(hoveringSetting, m.getCategory()));
                fr.drawStringWithShadow(s.name + ": " + strs.getText(), offset + difference, (24.0F + top) + textDif - staticHeight - increment + count, settingNameColor());
                if (handleType == HandleType.BUTTON_PRESSED &&
                        button == 0)
                  if (hoveringSetting) {
                    stringSet = strs;
                  } else {
                    stringSet = null;
                  }
                if (stringSet != null && handleType == HandleType.KEY_TYPED) {
                  if (keyCode == 1) {
                    mc.displayGuiScreen(null);
                    return;
                  }
                  if (keyCode == 54 || keyCode == 157 || keyCode == 42 || keyCode == 58 || keyCode == 29 || keyCode == 56 || keyCode == 219 || keyCode == 184 || keyCode == 221)
                    return;
                  if (keyCode == 14) {
                    if (strs.text.isEmpty())
                      return;
                    strs.text = strs.text.substring(0, strs.text.length() - 1);
                  } else {
                    strs.text += keyChar;
                  }
                }
                count += increment;
              }
              if (s instanceof BooleanSet) {
                BooleanSet booleanSet = (BooleanSet)s;
                if (booleanSet.isEnabled())
                  difference = 7.0D;
                if (handleType == HandleType.BUTTON_PRESSED && hoveringSetting && (button == 0 || button == 1))
                  booleanSet.toggle();
                Gui.drawRect(offset + diff, (top + staticHeight) + count, (offset + width) - diff, top + increment + count + staticHeight, settingRectColor(hoveringSetting, m.getCategory()));
                double d = 0.0D;
                if (booleanSet.isEnabled()) {
                  Gui.drawRect((offset + width) - diff - 2, (top + staticHeight) + count, (offset + width) - diff - 14, top + increment + count + staticHeight, new Color(141, 68, 173).getRGB());
                  fr.drawStringWithShadow("X", (offset + width) - diff - 11, (top + staticHeight) + count + 3.5F, settingNameColor());
                } else {
                  Gui.drawRect((offset + width) - diff - 2, (top + staticHeight) + count, (offset + width) - diff - 14, top + increment + count + staticHeight, new Color(0,0,0).getRGB());
                }
                fr.drawStringWithShadow(s.name, offset + difference, (23.0F + top) + textDif - staticHeight - increment + count, settingNameColor());
                count += increment;
              }
              if (s instanceof DoubleSet) {
                DoubleSet numberSet = (DoubleSet)s;
                float percent = (float)((numberSet.getValue() - numberSet.getMin()) / (numberSet.getMax() - numberSet.getMin()));
                float numberWidth = percent * width;
                if (numb != null && numb == numberSet) {
                  double mousePercent = Math.min(1.0F, Math.max(0.0F, (mouseX - offset - 3.0F) / width));
                  double newValue = mousePercent * (numberSet.getMax() - numberSet.getMin()) + numberSet.getMin();
                  numberSet.setValue(newValue);
                }
                Gui.drawRect(offset + diff, (top + staticHeight) + count, (offset + width) - diff, top + increment + count + staticHeight, settingRectColor(hoveringSetting, m.getCategory()));
                double sliderLeft = Math.min((offset + numberWidth) - diff, doubleSetDifference + offset + diff);
                double sliderRight = Math.min((offset + numberWidth) - diff, (offset + width) - diff - doubleSetDifference);
                double d = 0.0D;
                Gui.drawRect(sliderLeft, (top + staticHeight) + count + d + 11, sliderRight, top + increment + count + staticHeight - d, sliderColor(m, hoveringSetting));
                double shadowLeft = Math.max((offset + numberWidth), sliderLeft);
                //Gui.drawRect(Math.max(shadowLeft - 4.0D, offset), (top + staticHeight) + count + d, Math.max(sliderRight, offset), top + increment + count + staticHeight - d, (new Color(-2063597568, true)).getRGB());
                String val = numberSet.getValue() + numberSet.getSuffix();
                if (handleType == HandleType.BUTTON_PRESSED && hoveringSetting && button == 0)
                  numb = numberSet;
                fr.drawStringWithShadow(s.name, offset + difference, (23.0F + top) + textDif - staticHeight - increment + count, settingNameColor());
                fr.drawStringWithShadow(val, (offset + width) - difference - fr.getStringWidth(val), (22.0F + top) + textDif - staticHeight - increment + count + 1, settingNameColor());
                count += increment;
              }
              if (s instanceof ModeSet) {
                ModeSet modeSet = (ModeSet)s;
                if (handleType == HandleType.BUTTON_PRESSED && hoveringSetting && button == 0)
                  modeSet.positiveCycle();
                if (handleType == HandleType.BUTTON_PRESSED && hoveringSetting && button == 1)
                  modeSet.negativeCycle();
                Gui.drawRect(offset + diff, (top + staticHeight) + count, (offset + width) - diff, top + increment + count + staticHeight, settingRectColor(hoveringSetting, m.getCategory()));
                fr.drawStringWithShadow(s.name, offset + difference, (23.0F + top) + textDif - staticHeight - increment + count, settingNameColor());
                fr.drawStringWithShadow(s.name, offset + difference, (23.0F + top) + textDif - staticHeight - increment + count, settingNameColor());
                String mod = modeSet.getMode().toUpperCase();
                fr.drawStringWithShadow(mod, (offset + width) - difference - fr.getStringWidth(mod), (23.0F + top) + textDif - staticHeight - increment + count, settingNameColor());
                count += increment;
              }
              if (!(s instanceof ModuleCategory))
                continue;
              ModuleCategory category = (ModuleCategory)s;
              if (handleType == HandleType.BUTTON_PRESSED && hoveringSetting && (button == 0 || button == 1))
                category.setExpanded(!category.isExpanded());
              int bgCol = (new Color(460551)).getRGB();
              Gui.drawRect(offset + diff, (top + staticHeight) + count, (offset + width) - diff, top + increment + count + staticHeight, category.isExpanded() ? bgCol : settingRectColor(hoveringSetting, m.getCategory()));
              fr.drawCenteredStringWithShadow(s.name, (offset + width / 2.0F), (float) ((25 + top) + textDif - staticHeight - increment + count), category.isExpanded() ? sliderColor(m, false) : (new Color(14606046)).getRGB());
              count += increment;
              if (!category.isExpanded())
                continue;
              for (Setting setOnCat : category.settingsOnCat) {
                difference = 9.0D;
                doubleSetDifference = 4.0D;
                textDif = 22.0D;
                increment = 11.0D;
                boolean hoveringCat = hovered(offset, (float)((top + staticHeight) + count), offset + width, (float)(top + increment + (float)count + staticHeight), mouseX, mouseY);
                Gui.drawRect(offset, (top + staticHeight) + count, (offset + 2.0F), top + increment + count + staticHeight, categoryRectColor());
                Gui.drawRect((offset + width - 2.0F), (top + staticHeight) + count, (offset + width), top + increment + count + staticHeight, categoryRectColor());
                if (setOnCat instanceof BooleanSet) {
                  BooleanSet booleanSet = (BooleanSet)setOnCat;
                  if (booleanSet.isEnabled())
                    difference = 11.0D;
                  if (handleType == HandleType.BUTTON_PRESSED && hoveringCat && (button == 0 || button == 1))
                    booleanSet.toggle();
                  Gui.drawRect(offset + diff, (top + staticHeight) + count, (offset + width) - diff, top + increment + count + staticHeight, bgCol);
                  double d = 0.0D;
                  if (booleanSet.isEnabled()) {
                    Gui.drawRect(offset + diff + 4.0D, (top + staticHeight) + count, offset + diff + 6.0D, top + increment + count + staticHeight, (new Color(-2063597568, true)).getRGB());
                    Gui.drawRect((offset + width) - diff - 6.0D, (top + staticHeight) + count, (offset + width) - diff - 4.0D, top + increment + count + staticHeight, (new Color(-2063597568, true)).getRGB());
                  }
                  fr.drawStringWithShadow(setOnCat.name, offset + difference, (21 + top) + textDif - staticHeight - increment + count, settingNameColor());
                  count += increment;
                }
                if (s instanceof StringSet) {
                  StringSet strs = (StringSet)s;
                  Gui.drawRect(offset + diff, (top + staticHeight) + count, (offset + width) - diff, top + increment + count + staticHeight, settingRectColor(hoveringSetting, m.getCategory()));
                  fr.drawStringWithShadow(s.name + ": " + strs.getText(), offset + difference, (23 + top) + textDif - staticHeight - increment + count, settingNameColor());
                  if (handleType == HandleType.BUTTON_PRESSED &&
                          button == 0)
                    if (hoveringSetting) {
                      stringSet = strs;
                    } else {
                      stringSet = null;
                    }
                  if (stringSet != null && handleType == HandleType.KEY_TYPED) {
                    if (keyCode == 54 || keyCode == 157 || keyCode == 42 || keyCode == 58 || keyCode == 29 || keyCode == 56 || keyCode == 219 || keyCode == 184 || keyCode == 221)
                      return;
                    if (keyCode == 14) {
                      if (strs.text.isEmpty())
                        return;
                      strs.text = strs.text.substring(0, strs.text.length() - 1);
                    } else {
                      strs.text += keyChar;
                    }
                  }
                  count += increment;
                }
                if (setOnCat instanceof DoubleSet) {
                  DoubleSet numberSet = (DoubleSet)setOnCat;
                  float percent = (float)((numberSet.getValue() - numberSet.getMin()) / (numberSet.getMax() - numberSet.getMin()));
                  float numberWidth = percent * width;
                  if (numb != null && numb == numberSet) {
                    double mousePercent = Math.min(1.0F, Math.max(0.0F, (mouseX - offset - 3.0F) / width));
                    double newValue = mousePercent * (numberSet.getMax() - numberSet.getMin()) + numberSet.getMin();
                    numberSet.setValue(newValue);
                  }
                  Gui.drawRect(offset + diff, (top + staticHeight) + count, (offset + width) - diff, top + increment + count + staticHeight, bgCol);
                  double sliderLeft = Math.min((offset + numberWidth) - diff, doubleSetDifference + offset + diff);
                  double sliderRight = Math.min((offset + numberWidth) - diff, (offset + width) - diff - doubleSetDifference);
                  double d = 0.0D;
                  Gui.drawRect(sliderLeft, (top + staticHeight) + count + d, sliderRight, top + increment + count + staticHeight - d, sliderColor(m, hoveringSetting));
                  double shadowLeft = Math.min((offset + numberWidth) - diff, (offset + width) - doubleSetDifference - diff);
                  Gui.drawRect(Math.max(shadowLeft - 2.0D, offset), (top + staticHeight) + count + d, Math.max(sliderRight, offset), top + increment + count + staticHeight - d, (new Color(-2063597568, true)).getRGB());
                  String val = numberSet.getValue() + numberSet.getSuffix();
                  if (handleType == HandleType.BUTTON_PRESSED && hoveringCat && button == 0)
                    numb = numberSet;
                  fr.drawStringWithShadow(setOnCat.name, offset + difference, (21 + top) + textDif - staticHeight - increment + count, settingNameColor());
                  fr.drawStringWithShadow(val, (offset + width) - difference - fr.getStringWidth(val), (21 + top) + textDif - staticHeight - increment + count, settingNameColor());
                  count += increment;
                }
                if (!(setOnCat instanceof ModeSet))
                  continue;
                ModeSet modeSet = (ModeSet)setOnCat;
                if (handleType == HandleType.BUTTON_PRESSED && hoveringCat && button == 0)
                  modeSet.positiveCycle();
                if (handleType == HandleType.BUTTON_PRESSED && hoveringCat && button == 1)
                  modeSet.negativeCycle();
                Gui.drawRect(offset + diff, (top + staticHeight) + count, (offset + width) - diff, top + increment + count + staticHeight, bgCol);
                fr.drawStringWithShadow(setOnCat.name, offset + difference, (21 + top) + textDif - staticHeight - increment + count, settingNameColor());
                String mod = modeSet.getMode().toUpperCase();
                fr.drawStringWithShadow(mod, (offset + width) - difference - fr.getStringWidth(mod), (21 + top) + textDif - staticHeight - increment + count, settingNameColor());
                count += increment;
              }
              Gui.drawRect(offset + diff, (top + staticHeight) + (count += 2.0D) - 2.0D, (offset + width) - diff, top + count + staticHeight, bgCol);
              Gui.drawRect(offset, (top + staticHeight) + count - 2.0D, (offset + 2.0F), top + count + staticHeight, categoryRectColor());
              Gui.drawRect((offset + width - 2.0F), (top + staticHeight) + count - 2.0D, (offset + width), top + count + staticHeight, categoryRectColor());
            }
            Gui.drawRect(offset + diff, (top + staticHeight) + (count += 2.0D) - 2.0D, (offset + width) - diff, (top + 0.0F) + count + staticHeight, categoryRectColor());
            Gui.drawRect(offset, (top + staticHeight) + count - 2.0D, (offset + 2.0F), (top + 0.0F) + count + staticHeight, categoryRectColor());
            Gui.drawRect((offset + width - 2.0F), (top + staticHeight) + count - 2.0D, (offset + width), (top + 0.0F) + count + staticHeight, categoryRectColor());
          }
          textDif = 20.0D;
        }
        Gui.drawRect(offset + diff, (top + staticHeight) + (count += 2.0D) - 2.0D, (offset + width) - diff, top + count + staticHeight, categoryRectColor());
        Gui.drawRect(offset, (top + staticHeight) + count - 2.0D, (offset + 2.0F), top + count + staticHeight, categoryRectColor());
        Gui.drawRect((offset + width - 2.0F), (top + staticHeight) + count - 2.0D, (offset + width), top + count + staticHeight, categoryRectColor());
      }
      countY += incrementVal;
    }
    if (handleType == HandleType.BUTTON_PRESSED && button == 0)
      draggingCategory = null;
  }

  public boolean doesGuiPauseGame() {
    return false;
  }

  public Color getGradientOffset(Color color1, Color color2, double offset) {
    if (offset > 1.0D) {
      double left = offset % 1.0D;
      int off = (int)offset;
      offset = (off % 2 == 0) ? left : (1.0D - left);
    }
    double inverse_percent = 1.0D - offset;
    int redPart = (int)(color1.getRed() * inverse_percent + color2.getRed() * offset);
    int greenPart = (int)(color1.getGreen() * inverse_percent + color2.getGreen() * offset);
    int bluePart = (int)(color1.getBlue() * inverse_percent + color2.getBlue() * offset);
    return new Color(redPart, greenPart, bluePart);
  }

  enum HandleType {
    BUTTON_PRESSED, BUTTON_RELEASED, RENDER, KEY_TYPED;
  }
}
