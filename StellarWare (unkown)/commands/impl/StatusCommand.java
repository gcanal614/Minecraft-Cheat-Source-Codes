package stellar.skid.commands.impl;

import stellar.skid.StellarWare;
import stellar.skid.commands.NovoCommand;
import stellar.skid.utils.ServerUtils;
import stellar.skid.utils.notifications.NotificationType;
import org.checkerframework.checker.nullness.qual.NonNull;


public class StatusCommand extends NovoCommand {

    public StatusCommand(@NonNull StellarWare stellarWare) {
        super(stellarWare, "status");
    }


    @Override
    public void process(String[] args) {
        if (args.length != 0) {
            return;
        }

        String status = "Status: " + (ServerUtils.isHypixel() ? "Enabled" : "Disabled");
        NotificationType type = ServerUtils.isHypixel() ? NotificationType.SUCCESS : NotificationType.ERROR;

        notifyClient("AutoBypass", status, 5000, type);
    }
}