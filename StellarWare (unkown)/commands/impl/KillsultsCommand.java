package stellar.skid.commands.impl;

import stellar.skid.StellarWare;
import stellar.skid.commands.NovoCommand;
import stellar.skid.modules.misc.Killsults;
import stellar.skid.utils.messages.TextMessage;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static stellar.skid.utils.messages.MessageFactory.text;
import static stellar.skid.utils.messages.MessageFactory.usage;
import static net.minecraft.util.EnumChatFormatting.GREEN;
import static net.minecraft.util.EnumChatFormatting.RED;

public class KillsultsCommand extends NovoCommand {

    public KillsultsCommand(@NonNull StellarWare stellarWare) {
        super(stellarWare, "killsults", Arrays.asList("ks", "sults"));
    }

    @Override
    public void process(String[] args) throws IOException {
        if (args.length < 1) {
            sendHelp( // @off
                    "Killsults help:", ".killsults",
                    usage("list", "shows all killsults"),
                    usage("add (\"text\")", "add a killsults"),
                    usage("remove (index)", "removes a killsults"),
                    usage("clear", "removes all killsults")
            ); //@on
            return;
        }

        String command = args[0].toLowerCase();
        Killsults killsults = stellarWare.getModuleManager().getModule(Killsults.class);
        List<String> list = killsults.getKillsults();

        switch (command) {
            case "add":
                int end = 0;
                for (String arg : args) {
                    if (arg.contains("\"")) {
                        end = Arrays.asList(args).indexOf(arg);
                    }
                }

                if (!args[end].endsWith("\"")) {
                    notifyError("message must be enclosed in quotation marks");
                } else {
                    StringBuilder stringBuilder = new StringBuilder();

                    for (int i = 1; i <= end; i++) {
                        stringBuilder.append(args[i]).append(i == end ? "" : " ");
                    }

                    String sult = stringBuilder.toString().replace("\"", "");

                    if (sult.isEmpty()) {
                        notifyError("message is empty!");
                    } else {
                        if (!list.contains(sult)) {
                            list.add(sult);
                            notify("added: \"" + sult + "\" to list!");
                        } else {
                            notifyError("list already contains that line!");
                        }
                    }
                }
                break;

            case "remove":
                if (args.length < 2) {
                    notifyError("please specify the index of the killsult you want to delete");
                } else {
                    String remove = args[1];
                    if (remove.length() == 1 && Character.isDigit(remove.charAt(0))) {
                        remove = list.get(Integer.parseInt(args[1]));

                        if (list.contains(remove)) {
                            list.remove(remove);
                            notify("removed: " + remove + " from list!");
                        } else {
                            notifyError("list does not contains that line!");
                        }

                    } else {
                        notifyError("please specify the index of the killsult you want to delete");
                    }
                }
                break;

            case "clear":
                if (list.isEmpty()) {
                    notifyError("list is empty!");
                } else {
                    list.clear();
                    notify("list cleared!");
                }
                break;

            case "list":
                killsults.loadSults();

                if (list.isEmpty()) {
                    notifyError("list is empty!");
                } else {
                    final TextMessage text = text("List of killsults:");
                    send(text, true);

                    for (String sults : list) {
                        String sult = "> " + list.indexOf(sults) + ": " + GREEN + sults;

                        if (sult.contains("%s")) {
                            send(sult.replace("%s", RED + "%s" + GREEN));
                        } else {
                            send(sult);
                        }
                    }
                }
                break;
        }
    }
}
