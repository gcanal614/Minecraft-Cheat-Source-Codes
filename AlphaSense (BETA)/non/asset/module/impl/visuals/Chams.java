package non.asset.module.impl.visuals;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import non.asset.module.Module;
import non.asset.module.impl.Combat.AntiBot;
import non.asset.utils.value.impl.BooleanValue;
import non.asset.utils.value.impl.ColorValue;


public class Chams extends Module {
    public ColorValue visible = new ColorValue("Visible", new Color(0xff0000).getRGB());
    public ColorValue hidden = new ColorValue("Hidden", new Color(0xff00ff).getRGB());
    public ColorValue visiblefriend = new ColorValue("Visible Friend", new Color(0x3030ff).getRGB());
    public ColorValue hiddenfriend = new ColorValue("Hidden Friend", new Color(0xff2060).getRGB());
    private BooleanValue players = new BooleanValue("Players", true);
    private BooleanValue animals = new BooleanValue("Animals", true);
    private BooleanValue mobs = new BooleanValue("Mobs", false);
    private BooleanValue invisibles = new BooleanValue("Invisibles", false);
    private BooleanValue passives = new BooleanValue("Passives", true);
    public BooleanValue showArmor = new BooleanValue("ShowArmor", true);
    public BooleanValue colored = new BooleanValue("Colored", true);
    public BooleanValue hands = new BooleanValue("Hands", false);
    public BooleanValue rainbow = new BooleanValue("Raindow", false);
    public Chams() {
        super("Chams", Category.VISUALS);
        setDescription("Alow you see the entities in the walls");
    }
    
    

    public boolean isValid(EntityLivingBase entity) {
        return isValidType(entity) && !AntiBot.getBots().contains(entity) &&entity.isEntityAlive() && (!entity.isInvisible() || invisibles.isEnabled());
    }

    private boolean isValidType(EntityLivingBase entity) {
        return (players.isEnabled() && entity instanceof EntityPlayer) || (mobs.isEnabled() && (entity instanceof EntityMob || entity instanceof EntitySlime) || (passives.isEnabled() && (entity instanceof EntityVillager || entity instanceof EntityGolem)) || (animals.isEnabled() && entity instanceof EntityAnimal));
    }
}
