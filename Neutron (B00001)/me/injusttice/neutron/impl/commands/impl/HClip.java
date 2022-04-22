package me.injusttice.neutron.impl.commands.impl;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.impl.commands.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class HClip extends Command {

    protected Minecraft mc = Minecraft.getMinecraft();

    public HClip() {
        super("HClip", "HClip", "hclip <distance>", "hclip");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length == 2) {
            NeutronMain.addChatMessage("Invalid Arguments, Please use .hclip <distance>");
        }

        if (args.length == 1) {
            mc.thePlayer.noClip = true;
            mc.thePlayer.setPosition(mc.thePlayer.posX + mc.thePlayer.getLookVec().xCoord * Double.parseDouble(args[0]), mc.thePlayer.posY,mc.thePlayer.posZ + mc.thePlayer.getLookVec().zCoord * Double.parseDouble(args[0]));
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§8> §aTeleported " + args[0] + ".0 blocks"));
        }
    }
}
