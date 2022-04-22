package me.injusttice.neutron.impl.modules;

import com.sun.istack.internal.NotNull;
import me.injusttice.neutron.api.events.EventManager;
import me.injusttice.neutron.api.events.impl.EventNigger;
import me.injusttice.neutron.api.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StringUtils;

import java.util.*;

public class Module {

    private String arrayName;
    protected static Random rand = new Random();
    protected Minecraft mc = Minecraft.getMinecraft();
    public static EntityPlayerSP localPlayer;
    public boolean visible = true;
    private double transition;
    private boolean beingEnabled = false;
    private double verticalTransition;

    public double scaleFactor;
    private boolean expanded;
    public List<Setting> settings = new ArrayList<>();
    public String name, hidden, displayName;
    private int key;
    public Category category;
    public boolean toggled;

    public Module(String name, int key, Category category){
        this.name = name;
        this.key = key;
        this.category = category;
        toggled = false;

        setup();
    }

    public Module(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public void addSettings(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public List<Setting> getSettings() {
        return this.settings;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void onEvent(EventNigger e) {}

    public boolean isEnabled() {
        return toggled;
    }

    public void onEnable() {
        this.beingEnabled = true;
        this.verticalTransition = 0.0D;
        this.transition = this.mc.fontRendererObj.getStringWidth(StringUtils.stripControlCodes(getDisplayName()));
        EventManager.register(this);
    }

    public void onUpdate() {}

    public void onDisable(){
        this.beingEnabled = false;
        EventManager.unregister(this);
    }

    public void onToggle(){}
    public void toggle() {
        toggled = !toggled;
        if(toggled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
        if (this.toggled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getKey(){
        return key;
    }

    public void setKey(int key){
        this.key = key;
    }

    public Category getCategory(){
        return category;
    }

    public void setCategory(Category category){
        this.category = category;
    }

    public boolean isToggled(){
        return toggled;
    }

    public String getDisplayName(){
        return displayName == null ? name : displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getArrayName() {
        return this.arrayName;
    }

    public void setArrayName(String arrayName) {
        this.arrayName = arrayName;
    }

    public double getVerticalTransition() {
        return this.verticalTransition;
    }

    public void setVerticalTransition(double verticalTransition) {
        this.verticalTransition = verticalTransition;
    }

    public boolean isBeingEnabled() {
        return this.beingEnabled;
    }

    public void setBeingEnabled(boolean beingEnabled) {
        this.beingEnabled = beingEnabled;
    }

    public double getTransition() {
        return this.transition;
    }

    public void setTransition(double transition) {
        this.transition = transition;
    }

    public <M extends Module> void checkModule(@NotNull Class<? extends M> moduleClass){
        M module = ModuleManager.getModule(moduleClass);

        if (module.isEnabled()) {
            mc.thePlayer.addChatMessage(new ChatComponentText("Neutron > Disabled " + module.getName() + " to prevent flags!"));
            module.toggle();
        }
    }

    public void setup() {}
}
