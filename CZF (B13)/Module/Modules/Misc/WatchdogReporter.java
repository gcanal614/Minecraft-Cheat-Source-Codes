package gq.vapu.czfclient.Module.Modules.Misc;


import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Misc.EventChat;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Module.Modules.Blatant.Killaura;
import gq.vapu.czfclient.Util.Helper;

import java.awt.*;
import java.util.Random;

public class WatchdogReporter extends Module {
    public WatchdogReporter() {
        super("SuperReporter", new String[]{"wdr, wder"}, ModuleType.Player);
        this.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());
        this.setSuffix("Watchdog");

    }

    public static String getRandomString(double d) {
        String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < d; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    @EventHandler
    private void onChat(EventChat e) {
        if (
                Killaura.Target.getName() != null &&
                        e.getMessage().contains("��" + mc.thePlayer.getName() + "��ɱ") ||
                        e.getMessage().contains("��" + mc.thePlayer.getName() + "���������") ||
                        e.getMessage().contains(" ����ɱ����ɱ�ߣ� " + mc.thePlayer.getName()) ||
                        e.getMessage().contains(" ���������£���ɱ�ߣ� " + mc.thePlayer.getName()) ||
                        e.getMessage().contains(" ��������գ���ɱ�ߣ� " + mc.thePlayer.getName())) {
            e.setCancelled(false);
            mc.thePlayer.sendChatMessage("/wdr " + Killaura.Target.getName() + " killaura autoclick speed fly reach");
        }
        if (e.getMessage().contains("[WATCHDOG CHEAT DETECTION]")) {
//            mc.thePlayer.sendChatMessage("�д��������ͱ����Ź�ҧ�ˣ�ȫ��ע�⣡");
            Helper.mc.thePlayer.sendChatMessage("Hacker L");
        }
    }
}
