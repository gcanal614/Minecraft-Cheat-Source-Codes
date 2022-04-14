package gq.vapu.czfclient.Module.Modules.Misc;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Misc.EventChat;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;

public class AntiSpam extends Module {
    int i;

    public AntiSpam() {
        super("ChatFilter", new String[]{"AntiSpam"}, ModuleType.World);
        this.setSuffix("Times:0");
    }

    @EventHandler
    public void onChat(EventChat e) {
        if (e.getMessage().contains(".com") || e.getMessage().contains(".cn") || e.getMessage().contains(".xyz")
                || e.getMessage().contains(".cf") || e.getMessage().contains("http")
                || e.getMessage().contains("����") || e.getMessage().contains("���") || e.getMessage().contains("�ڲ�")
                || e.getMessage().contains("�ⲿ") || e.getMessage().contains("����") || e.getMessage().contains("��Ⱥ")
                || e.getMessage().contains("qȺ") || e.getMessage().contains("��g") || e.getMessage().contains("����")
                || e.getMessage().contains("��g") || e.getMessage().contains("����") || e.getMessage().contains("����")) {
            e.setCancelled(true);
            i++;
            this.setSuffix("Times:" + i);
        }
    }
}
