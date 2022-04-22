package non.asset.module.impl.visuals;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StringUtils;
import non.asset.Clarinet;
import non.asset.event.bus.Handler;
import non.asset.event.impl.render.NameplateEvent;
import non.asset.event.impl.render.Render2DEvent;
import non.asset.module.Module;
import non.asset.module.impl.Combat.AntiBot;
import non.asset.utils.OFC.RenderUtil;
import non.asset.utils.font.MCFontRenderer;
import non.asset.utils.value.impl.BooleanValue;
import non.asset.utils.value.impl.ColorValue;
import non.asset.utils.value.impl.EnumValue;
import non.asset.utils.value.impl.FontValue;

import org.lwjgl.opengl.GL11;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ItemPhysics extends Module {
    
	public ItemPhysics() {
        super("ItemPhysics", Category.VISUALS);
        setRenderLabel("Item Physics");
    }
}
