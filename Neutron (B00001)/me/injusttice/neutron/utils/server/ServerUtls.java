package me.injusttice.neutron.utils.server;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import lombok.Getter;
import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventReceivePacket;
import me.injusttice.neutron.api.events.impl.EventTick;
import me.injusttice.neutron.utils.player.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Collection;

public class ServerUtls {
    public static ServerUtls INSTANCE;

    public boolean isHypixelScoreboard = false;
    public boolean isHypixelC0F = false;
    public boolean isHypixelS3F = false;

    Minecraft mc = Minecraft.getMinecraft();
    TimeHelper timer = new TimeHelper();

    @Getter
    public static Server server = Server.Unknown;

    public ServerUtls() {
        INSTANCE = this;
    }

    public void reset() {
        server = ServerUtls.Server.Unknown;
        isHypixelScoreboard = false;
        isHypixelC0F = false;
        isHypixelS3F = false;
    }

    @EventTarget
    public void onPacket(EventReceivePacket e) {
        Packet packet = e.getPacket();
        if (packet instanceof S3FPacketCustomPayload) {
            processS3F((S3FPacketCustomPayload) packet);
        }
    }

    public void processS3F(S3FPacketCustomPayload packet) {
        isHypixelS3F = isHypixelS3F || packet.getBufferData().readStringFromBuffer(1000000).contains("BungeeCord (Hypixel) <- vanilla");
    }

    public boolean isOnHypixel() {
        return isHypixelScoreboard && !mc.isSingleplayer();
    }

    @EventTarget
    public void onTicks(EventTick e) {
        if (timer.hasReached(1000)) {
            processScoreboard();
            if (!isOnHypixel()) {
                tick();
            } else {
                server = Server.Hypixel;
            }
            timer.reset();
        }
    }

    public void processScoreboard() {
        Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
        ScoreObjective scoreobjective = null;
        ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(this.mc.thePlayer.getName());

        if (scoreplayerteam != null) {
            int i1 = scoreplayerteam.getChatFormat().getColorIndex();

            if (i1 >= 0) {
                scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + i1);
            }
        }

        ScoreObjective scoreobjective1 = scoreobjective != null ? scoreobjective
                : scoreboard.getObjectiveInDisplaySlot(1);

        if (scoreobjective1 != null) {
            fakeRenderScoreboard(scoreobjective1);
        }
    }

    private void fakeRenderScoreboard(ScoreObjective objective) {
        Scoreboard scoreboard = objective.getScoreboard();
        Collection collection = scoreboard.getSortedScores(objective);
        ArrayList arraylist = Lists.newArrayList(Iterables.filter(collection, new Predicate() {

            public boolean apply(Score p_apply_1_) {
                return p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#");
            }

            public boolean apply(Object p_apply_1_) {
                return this.apply((Score) p_apply_1_);
            }
        }));

        ArrayList arraylist1;

        if (arraylist.size() > 15) {
            arraylist1 = Lists.newArrayList(Iterables.skip(arraylist, collection.size() - 15));
        } else {
            arraylist1 = arraylist;
        }

        for (Object b : arraylist1) {
            Score score1 = (Score) b;
            ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score1.getPlayerName());
            String s1 = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score1.getPlayerName());
            int chars = 0;
            String str = "www.hypixel.net";

            for (char c : str.toCharArray()) {
                if (s1.contains(String.valueOf(c))) chars++;
            }

            if (chars == str.length() && ServerUtls.server != ServerUtls.Server.Hypixel) {
                ServerUtls.server = ServerUtls.Server.Hypixel;
                ServerUtls.INSTANCE.isHypixelScoreboard = true;
            }
        }
    }

    public void tick() {
        if (mc.isSingleplayer()) {
            server = Server.SinglePlayer;
        } else {
            ServerData data = mc.getCurrentServerData();
            String motd = data.serverMOTD;

            if (motd.contains("Mineplex")) {
                server = Server.Mineplex;
            } else if (motd.contains("CubeCraft")) {
                server = Server.CubeCraft;
            } else {
                server = Server.Unknown;
            }
        }
    }

    public static boolean isHypixelLobby() {
        String[] strings = new String[] {"CLICK TO PLAY", "点击开始游戏"};
        for (Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (entity.getName().startsWith("§e§l")) {
                for (String string : strings) {
                    if (entity.getName().equals("§e§l" + string)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public enum Server {
        Hypixel, HypixelCN, Mineplex, CubeCraft, Unknown, SinglePlayer
    }
}
