/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.utils.server;

import club.tifality.Tifality;
import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.packet.PacketReceiveEvent;
import club.tifality.utils.server.ServerUtils;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.network.play.server.S02PacketChat;

public final class HypixelGameUtils {
    private static GameMode gameMode;
    private static SkyWarsModePrefix skyWarsPrefix;
    private static SkyWarsModeSuffix skyWarsSuffix;

    @Listener
    private void onPacketReceive(PacketReceiveEvent event) {
        if (HypixelGameUtils.isSkyWars() && event.getPacket() instanceof S02PacketChat) {
            S02PacketChat packetChat = (S02PacketChat)event.getPacket();
            String chatMessage = packetChat.getChatComponent().getUnformattedText();
            if (chatMessage.equals("Teaming is not allowed on Solo mode!")) {
                skyWarsPrefix = SkyWarsModePrefix.SOLO;
            } else if (chatMessage.equals("Cross Teaming / Teaming with other teams is not allowed!")) {
                skyWarsPrefix = SkyWarsModePrefix.TEAMS;
            }
        }
    }

    private HypixelGameUtils() {
        Tifality.getInstance().getEventBus().subscribe(this);
    }

    public static GameMode getGameMode() {
        return ServerUtils.isOnHypixel() ? gameMode : GameMode.INVALID;
    }

    public static SkyWarsMode getSkyWarsMode() {
        if (ServerUtils.isOnHypixel() && HypixelGameUtils.isSkyWars()) {
            switch (skyWarsPrefix) {
                case MEGA: {
                    switch (skyWarsSuffix) {
                        default: {
                            return SkyWarsMode.INVALID;
                        }
                        case NORMAL: 
                    }
                    return SkyWarsMode.MEGA_NORMAL;
                }
                case SOLO: {
                    switch (skyWarsSuffix) {
                        case INSANE: {
                            return SkyWarsMode.SOLO_INSANE;
                        }
                        case NORMAL: {
                            return SkyWarsMode.SOLO_NORMAL;
                        }
                    }
                    return SkyWarsMode.INVALID;
                }
            }
            switch (skyWarsSuffix) {
                case INSANE: {
                    return SkyWarsMode.TEAMS_INSANE;
                }
                case NORMAL: {
                    return SkyWarsMode.TEAMS_NORMAL;
                }
            }
            return SkyWarsMode.INVALID;
        }
        return SkyWarsMode.INVALID;
    }

    public static boolean hasGameStarted() {
        return HypixelGameUtils.isSkyWars() && HypixelGameUtils.getModeText().length() > 0;
    }

    private static String getModeText() {
        return GuiIngame.modeText;
    }

    public static boolean isSkyWars() {
        return gameMode == GameMode.SKYWARS;
    }

    static {
        skyWarsPrefix = SkyWarsModePrefix.SOLO;
        skyWarsSuffix = SkyWarsModeSuffix.INSANE;
    }

    public static enum GameMode {
        INVALID(""),
        LOBBY("HYPIXEL"),
        BEDWARS("BEDWARS"),
        SKYWARS("SKYWARS"),
        BLITZ_SG("BLITZ SG"),
        PIT("THE HYPIXEL PIT"),
        UHC("HYPIXEL UHC"),
        DUELS("DUELS");

        private final String text;

        private GameMode(String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }
    }

    public static enum SkyWarsMode {
        INVALID,
        SOLO_NORMAL,
        SOLO_INSANE,
        TEAMS_NORMAL,
        TEAMS_INSANE,
        RANKED_NORMAL,
        MEGA_NORMAL,
        MEGA_DOUBLES;

    }

    public static enum SkyWarsModeSuffix {
        INSANE,
        NORMAL,
        DOUBLES;

    }

    public static enum SkyWarsModePrefix {
        TEAMS,
        SOLO,
        MEGA;

    }
}

