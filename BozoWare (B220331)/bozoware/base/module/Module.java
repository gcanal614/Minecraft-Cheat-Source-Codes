// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.module;

import java.lang.annotation.Annotation;
import bozoware.impl.property.ColorProperty;
import bozoware.impl.property.ValueProperty;
import bozoware.impl.property.BooleanProperty;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import bozoware.impl.property.EnumProperty;
import com.google.gson.JsonObject;
import bozoware.base.property.Property;
import java.util.ArrayList;
import bozoware.base.BozoWare;
import bozoware.impl.module.visual.HUD;
import net.minecraft.client.Minecraft;

public class Module
{
    private final String moduleName;
    private int moduleBind;
    private final ModuleCategory moduleCategory;
    protected static Minecraft<?> mc;
    private boolean moduleToggled;
    private String moduleSuffix;
    public Runnable onModuleEnabled;
    public Runnable onModuleDisabled;
    
    public Module() {
        this.onModuleEnabled = (() -> {});
        this.onModuleDisabled = (() -> {});
        if (this.hasIdentifier()) {
            this.moduleName = this.getClass().getAnnotation(ModuleData.class).moduleName();
            this.moduleCategory = this.getClass().getAnnotation(ModuleData.class).moduleCategory();
        }
        else {
            this.moduleName = "Unidentified";
            this.moduleCategory = ModuleCategory.COMBAT;
        }
    }
    
    public String getModuleName() {
        return this.moduleName;
    }
    
    public int getModuleBind() {
        return this.moduleBind;
    }
    
    public ModuleCategory getModuleCategory() {
        return this.moduleCategory;
    }
    
    public void setModuleBind(final int moduleBind) {
        this.moduleBind = moduleBind;
    }
    
    public boolean isModuleToggled() {
        return this.moduleToggled;
    }
    
    public void setModuleSuffix(final String moduleSuffix) {
        this.moduleSuffix = moduleSuffix;
    }
    
    public String getModuleDisplayName() {
        return this.getModuleName() + ((this.moduleSuffix == null || HUD.getInstance().hideSuffixes.getPropertyValue()) ? "" : (" §7" + this.moduleSuffix));
    }
    
    public void setModuleToggled(final boolean moduleToggled) {
        this.moduleToggled = moduleToggled;
        if (moduleToggled) {
            BozoWare.getInstance().getEventManager().subscribe(this);
            this.onModuleEnabled.run();
        }
        else {
            BozoWare.getInstance().getEventManager().unsubscribe(this);
            this.onModuleDisabled.run();
        }
    }
    
    public void toggleModule() {
        this.moduleToggled = !this.moduleToggled;
        if (this.moduleToggled) {
            BozoWare.getInstance().getEventManager().subscribe(this);
            this.onModuleEnabled.run();
        }
        else {
            BozoWare.getInstance().getEventManager().unsubscribe(this);
            this.onModuleDisabled.run();
        }
    }
    
    public ArrayList<Property<?>> getModuleProperties() {
        return BozoWare.getInstance().getPropertyManager().getPropertiesByModule(this);
    }
    
    public JsonObject saveJson() {
        final JsonObject moduleObject = new JsonObject();
        moduleObject.addProperty("toggled", Boolean.valueOf(this.moduleToggled));
        moduleObject.addProperty("bind", (Number)this.getModuleBind());
        if (!this.getModuleProperties().isEmpty()) {
            final JsonObject propertiesObject = new JsonObject();
            EnumProperty<?> enumProperty;
            final JsonObject jsonObject;
            BooleanProperty booleanProperty;
            ValueProperty<?> valueProperty;
            ColorProperty colorProperty;
            this.getModuleProperties().forEach(property -> {
                if (property instanceof EnumProperty) {
                    enumProperty = (EnumProperty<?>)property;
                    jsonObject.add(enumProperty.getPropertyLabel(), (JsonElement)new JsonPrimitive(((Enum)enumProperty.getPropertyValue()).name()));
                }
                if (property instanceof BooleanProperty) {
                    booleanProperty = (BooleanProperty)property;
                    jsonObject.addProperty(booleanProperty.getPropertyLabel(), booleanProperty.getPropertyValue());
                }
                if (property instanceof ValueProperty) {
                    valueProperty = property;
                    jsonObject.addProperty(valueProperty.getPropertyLabel(), (Number)valueProperty.getPropertyValue());
                }
                if (property instanceof ColorProperty) {
                    colorProperty = (ColorProperty)property;
                    jsonObject.addProperty(colorProperty.getPropertyLabel(), (Number)colorProperty.getColorRGB());
                }
                return;
            });
            moduleObject.add("properties", (JsonElement)propertiesObject);
        }
        return moduleObject;
    }
    
    public void loadJson(final JsonObject jsonObject) {
        if (jsonObject.has("toggled")) {
            this.setModuleToggled(jsonObject.get("toggled").getAsBoolean());
        }
        if (jsonObject.has("bind")) {
            this.setModuleBind(jsonObject.get("bind").getAsInt());
        }
        if (jsonObject.has("properties")) {
            final JsonObject propertiesObject = jsonObject.getAsJsonObject("properties");
            final JsonObject jsonObject2;
            EnumProperty enumProperty;
            int i;
            BooleanProperty booleanProperty;
            ValueProperty<Number> valueProperty;
            ColorProperty colorProperty;
            this.getModuleProperties().forEach(property -> {
                if (jsonObject2.has(property.getPropertyLabel())) {
                    if (property instanceof EnumProperty) {
                        for (enumProperty = (EnumProperty)property, i = 0; i < enumProperty.getEnumValues().length; ++i) {
                            if (enumProperty.getEnumValues()[i].name().equalsIgnoreCase(jsonObject2.getAsJsonPrimitive(property.getPropertyLabel()).getAsString())) {
                                enumProperty.setPropertyValue(enumProperty.getEnumValues()[i]);
                            }
                        }
                    }
                    if (property instanceof BooleanProperty) {
                        booleanProperty = (BooleanProperty)property;
                        booleanProperty.setPropertyValue(jsonObject2.get(property.getPropertyLabel()).getAsBoolean());
                    }
                    if (property instanceof ValueProperty) {
                        valueProperty = (ValueProperty<Number>)property;
                        if (valueProperty.getPropertyValue() instanceof Integer) {
                            valueProperty.setPropertyValue(jsonObject2.get(property.getPropertyLabel()).getAsInt());
                        }
                        if (valueProperty.getPropertyValue() instanceof Double) {
                            valueProperty.setPropertyValue(jsonObject2.get(property.getPropertyLabel()).getAsDouble());
                        }
                        if (valueProperty.getPropertyValue() instanceof Float) {
                            valueProperty.setPropertyValue(jsonObject2.get(property.getPropertyLabel()).getAsFloat());
                        }
                        if (valueProperty.getPropertyValue() instanceof Long) {
                            valueProperty.setPropertyValue(jsonObject2.get(property.getPropertyLabel()).getAsLong());
                        }
                    }
                    if (property instanceof ColorProperty) {
                        colorProperty = property;
                        colorProperty.setPropertyValue(jsonObject2.get(property.getPropertyLabel()).getAsInt());
                    }
                }
            });
        }
    }
    
    private boolean hasIdentifier() {
        return this.getClass().isAnnotationPresent(ModuleData.class);
    }
    
    static {
        Module.mc = Minecraft.getMinecraft();
    }
}
