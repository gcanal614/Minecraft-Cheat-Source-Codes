package cn.Noble.Commands.commands;

import cn.Noble.Client;
import cn.Noble.Commands.Command;
import cn.Noble.Util.Logger;
import cn.Noble.Util.Chat.Helper;

public class ChangeText extends Command {
    public ChangeText() {
        super("text", new String[]{"clientname","name","cn"}, "", "sketit");
    }
    @Override
    public String execute(String[] args) {
    	if(args.length >=1) {
    		if(args[0].contains("original")) {
    			Client.name= "Noble";
    			return null;
    		}
    		Client.name = "";
    		for(int i = 0; i < args.length; i++) {
    			Client.name = Client.name + " " + args[i];
    		}
    		Logger.log("Changed Client Name to: "+ Client.name);
    	}else {
            Helper.sendMessageWithoutPrefix("\u00a7bCorrect usage:\u00a77 .cn <clientname>");
    	}
        return null;
    }
}