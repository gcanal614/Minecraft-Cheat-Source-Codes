/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.commands.client.impl;

import me.uncodable.srt.Ries;
import me.uncodable.srt.impl.commands.client.api.Command;
import me.uncodable.srt.impl.commands.client.api.CommandInfo;

@CommandInfo(name="VClip", desc="Allows you to vertically clip through blocks.", usage=".vclip <y-position offset>")
public class VClip
extends Command {
    @Override
    public void exec(String[] args) {
        switch (args.length) {
            case 1: {
                this.printUsage();
                break;
            }
            case 2: {
                int yOffset;
                try {
                    yOffset = Integer.parseInt(args[1]);
                }
                catch (NumberFormatException e) {
                    this.printUsage();
                    e.printStackTrace();
                    return;
                }
                VClip.MC.thePlayer.setPositionAndUpdate(VClip.MC.thePlayer.posX, VClip.MC.thePlayer.getEntityBoundingBox().minY + (double)yOffset, VClip.MC.thePlayer.posZ);
                if (yOffset == 0) {
                    Ries.INSTANCE.msg("Teleported nowhere. Congratulations, you wasted more time typing out this command than Uncodable developing a latest Verus disabler.");
                    return;
                }
                Ries.INSTANCE.msg(String.format("Teleported %d blocks %s.", Math.abs(yOffset), yOffset > 0 ? "upwards" : "downwards"));
            }
        }
    }
}

