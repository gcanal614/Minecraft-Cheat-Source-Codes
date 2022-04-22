package me.injusttice.neutron.impl.modules.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.impl.modules.impl.combat.KillAura;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;

public class Targets {
    static Minecraft mc = Minecraft.getMinecraft();

    public static List<Entity> getTargets(double range, boolean hitPlayers, boolean hitVillagers, boolean hitMobs, boolean hitInvis) {
        List<Entity> targets = mc.theWorld.loadedEntityList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        targets = targets.stream().filter(entity -> (entity.getDistanceToEntity(mc.thePlayer) < range && entity != mc.thePlayer)).collect(Collectors.toList());
        targets.sort(Comparator.comparingDouble(entity -> entity.getDistanceToEntity(mc.thePlayer)));
        List<Entity> targetArray = new ArrayList<>();
        for (Entity entity : targets) {
            if (entity instanceof EntityAnimal)
                targetArray.add(entity);
            if (hitPlayers && entity instanceof EntityPlayer)
                targetArray.add(entity);
            if (hitVillagers && entity instanceof EntityVillager)
                targetArray.add(entity);
            if (hitMobs && entity instanceof EntityMob)
                targetArray.add(entity);
            if (hitInvis && entity.isInvisible())
                targetArray.add(entity);
        }
        targets = targetArray;
        return targets;
    }

    public static List<Entity> getKillAuraTargets(double range, boolean hitPlayers, boolean hitVillagers, boolean hitMobs, boolean hitInvis, boolean hitAnimals, boolean hitFriends, boolean hitTargets, boolean teamsEnabled) {
        List<Entity> targets = (List<Entity>)mc.theWorld.loadedEntityList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        targets = (List<Entity>)targets.stream().filter(entity -> (entity.getDistanceToEntity((Entity)mc.thePlayer) < range && entity != mc.thePlayer)).collect(Collectors.toList());
        targets.sort(Comparator.comparingDouble(entity -> entity.getDistanceToEntity((Entity)mc.thePlayer)));
        List<Entity> targetArray = new ArrayList<>();
        for (Entity entity : targets) {
            if (hitAnimals && entity instanceof net.minecraft.entity.passive.EntityAnimal)
                targetArray.add(entity);
            if (hitPlayers && entity instanceof net.minecraft.entity.player.EntityPlayer) {
                targetArray.add(entity);
            } else if (!hitTargets || entity instanceof net.minecraft.entity.player.EntityPlayer) {

            }
            if (teamsEnabled && entity instanceof net.minecraft.entity.player.EntityPlayer &&
                    entity.getDisplayName().getUnformattedText().length() > 2) {
                char c = entity.getDisplayName().getUnformattedText().charAt(1);
                char playerC = mc.thePlayer.getDisplayName().getUnformattedText().charAt(1);
                if (c == playerC)
                    targetArray.remove(entity);
            }
            if (hitVillagers && entity instanceof net.minecraft.entity.passive.EntityVillager)
                targetArray.add(entity);
            if (hitMobs && entity instanceof net.minecraft.entity.monster.EntityMob)
                targetArray.add(entity);
            if (hitInvis && entity.isInvisible())
                targetArray.add(entity);
            if (((KillAura) NeutronMain.instance.moduleManager.getMod("KillAura")).botCheck.isEnabled() &&
                    entity.getDisplayName().getUnformattedText().toLowerCase().contains("[npc]"))
                targetArray.remove(entity);
        }
        targets = targetArray;
        return targets;
    }
}
