/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module;

import club.tifality.Tifality;
import club.tifality.manager.config.Serializable;
import club.tifality.manager.keybind.Bindable;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.module.Toggleable;
import club.tifality.property.Property;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.property.impl.EnumProperty;
import club.tifality.property.impl.MultiSelectEnumProperty;
import club.tifality.utils.StringUtils;
import club.tifality.utils.handler.Manager;
import club.tifality.utils.render.Translate;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;

public class Module
extends Manager<Property<?>>
implements Bindable,
Toggleable,
Comparable<Module>,
Serializable {
    private final String label = this.getClass().getAnnotation(ModuleInfo.class).label();
    private final String description = this.getClass().getAnnotation(ModuleInfo.class).description();
    private final ModuleCategory category = this.getClass().getAnnotation(ModuleInfo.class).category();
    private final Translate translate = new Translate(0.0, 0.0);
    private int key = this.getClass().getAnnotation(ModuleInfo.class).key();
    private boolean enabled;
    private boolean hidden;
    private Supplier<String> suffix;
    private String updatedSuffix;
    public static final Minecraft mc = Minecraft.getMinecraft();

    public Module() {
        super(new ArrayList());
    }

    private void updateSuffix(EnumProperty<?> mode) {
        this.updatedSuffix = StringUtils.upperSnakeCaseToPascal(((Enum)mode.getValue()).name());
    }

    public void setSuffixListener(EnumProperty<?> mode) {
        this.updateSuffix(mode);
        mode.addValueChangeListener((oldValue, value) -> this.updateSuffix(mode));
    }

    public void resetPropertyValues() {
        for (Property property : this.getElements()) {
            property.callFirstTime();
        }
    }

    public Translate getTranslate() {
        return this.translate;
    }

    public Supplier<String> getSuffix() {
        return this.suffix;
    }

    public void setSuffix(Supplier<String> suffix) {
        this.suffix = suffix;
    }

    public ModuleCategory getCategory() {
        return this.category;
    }

    public void reflectProperties() {
        for (Field field : this.getClass().getDeclaredFields()) {
            Class<Property> type2 = field.getType();
            if (!type2.isAssignableFrom(Property.class) && !type2.isAssignableFrom(DoubleProperty.class) && !type2.isAssignableFrom(EnumProperty.class) && !type2.isAssignableFrom(MultiSelectEnumProperty.class)) continue;
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            try {
                this.elements.add((Property)field.get(this));
            }
            catch (IllegalAccessException illegalAccessException) {
                // empty catch block
            }
        }
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLabel() {
        return this.label;
    }

    public String getDisplayLabel() {
        if (this.suffix != null || this.updatedSuffix != null) {
            return this.label + " \u00a77" + (this.updatedSuffix != null ? this.updatedSuffix : this.suffix.get());
        }
        return this.label;
    }

    @Override
    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public void onPress() {
        this.toggle();
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            if (enabled) {
                this.onEnable();
                Tifality.getInstance().getEventBus().subscribe(this);
            } else {
                Tifality.getInstance().getEventBus().unsubscribe(this);
                this.onDisable();
            }
        }
    }

    @Override
    public void toggle() {
        this.setEnabled(!this.enabled);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public int compareTo(Module o) {
        return o.getDisplayLabel().length() - this.getDisplayLabel().length();
    }

    @Override
    public JsonObject save() {
        JsonObject object = new JsonObject();
        object.addProperty("Enabled", this.isEnabled());
        object.addProperty("Hidden", this.isHidden());
        object.addProperty("Bind", this.getKey());
        List properties2 = this.getElements();
        if (!properties2.isEmpty()) {
            JsonObject propertiesObject = new JsonObject();
            for (Property property : properties2) {
                if (property instanceof DoubleProperty) {
                    propertiesObject.addProperty(property.getLabel(), (Number)((DoubleProperty)property).getValue());
                    continue;
                }
                if (property instanceof EnumProperty) {
                    EnumProperty enumProperty = (EnumProperty)property;
                    propertiesObject.add(property.getLabel(), new JsonPrimitive(StringUtils.upperSnakeCaseToPascal(((Enum)enumProperty.getValue()).name())));
                    continue;
                }
                if (property instanceof MultiSelectEnumProperty) {
                    propertiesObject.addProperty(property.getLabel(), StringUtils.upperSnakeCaseToPascal(Arrays.toString(((MultiSelectEnumProperty)property).getValues())).replaceAll("\\[", "").replaceAll("]", ""));
                    continue;
                }
                if (property.getType() == Boolean.class) {
                    propertiesObject.addProperty(property.getLabel(), (Boolean)property.getValue());
                    continue;
                }
                if (property.getType() == Integer.class) {
                    propertiesObject.addProperty(property.getLabel(), Integer.toHexString((Integer)property.getValue()));
                    continue;
                }
                if (property.getType() != String.class) continue;
                propertiesObject.addProperty(property.getLabel(), (String)property.getValue());
            }
            object.add("Settings", propertiesObject);
        }
        return object;
    }

    @Override
    public void load(JsonObject object) {
        if (object.has("Enabled")) {
            this.setEnabled(object.get("Enabled").getAsBoolean());
        }
        if (object.has("Hidden")) {
            this.setHidden(object.get("Hidden").getAsBoolean());
        }
        if (object.has("Bind")) {
            this.setKey(object.get("Bind").getAsInt());
        }
        if (object.has("Settings") && !this.getElements().isEmpty()) {
            JsonObject propertiesObject = object.getAsJsonObject("Settings");
            for (Property property : this.getElements()) {
                if (!propertiesObject.has(property.getLabel())) continue;
                if (property instanceof DoubleProperty) {
                    ((DoubleProperty)property).setValue(propertiesObject.get(property.getLabel()).getAsDouble());
                    continue;
                }
                if (property instanceof EnumProperty) {
                    this.findEnumValue(property, propertiesObject);
                    continue;
                }
                if (property instanceof MultiSelectEnumProperty) {
                    this.findMultiEnumValue(property, propertiesObject);
                    continue;
                }
                if (property.getValue() instanceof Boolean) {
                    property.setValue(propertiesObject.get(property.getLabel()).getAsBoolean());
                    continue;
                }
                if (!(property.getValue() instanceof Integer)) continue;
                property.setValue((int)Long.parseLong(propertiesObject.get(property.getLabel()).getAsString(), 16));
            }
        }
    }

    private <T extends Enum<T>> void findEnumValue(Property<?> property, JsonObject propertiesObject) {
        EnumProperty enumProperty = (EnumProperty)property;
        String value = propertiesObject.getAsJsonPrimitive(property.getLabel()).getAsString();
        for (Enum possibleValue : enumProperty.getValues()) {
            if (!possibleValue.name().equalsIgnoreCase(value)) continue;
            enumProperty.setValue(possibleValue);
            break;
        }
    }

    private <T extends Enum<T>> void findMultiEnumValue(Property<?> property, JsonObject propertiesObject) {
        MultiSelectEnumProperty enumProperty = (MultiSelectEnumProperty)property;
        String value = propertiesObject.getAsJsonPrimitive(property.getLabel()).getAsString();
        for (Enum possibleValue : enumProperty.getValues()) {
            if (!possibleValue.name().equalsIgnoreCase(value)) continue;
            enumProperty.isSelected(possibleValue);
            break;
        }
    }
}

