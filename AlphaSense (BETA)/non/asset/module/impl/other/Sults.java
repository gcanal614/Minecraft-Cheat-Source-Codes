package non.asset.module.impl.other;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.server.S02PacketChat;
import non.asset.Clarinet;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.module.Module;
import non.asset.module.impl.Combat.Aura;
import non.asset.utils.OFC.TimerUtil;

public class Sults extends Module {
	
	int counter;
	
	TimerUtil diedcringe = new TimerUtil();
	

	TimerUtil another1 = new TimerUtil();

	TimerUtil another2 = new TimerUtil();
	
    public Sults() {
        super("Insults", Category.OTHER);
        setRenderLabel("Insults");
        setDescription("For get a ban more faster :p");
    }
    
    @Override
    public void onEnable() {
    	this.counter = 0;
    }

    @Handler
    public void onPacket(PacketEvent event) {

        if (getMc().thePlayer == null) return;
        
        if (getMc().theWorld == null) return;
        
    	if (event.getPacket() instanceof S02PacketChat) {
            S02PacketChat packet = (S02PacketChat) event.getPacket();
            String msg = packet.getChatComponent().getUnformattedText();
            try {
                if (msg.contains("Has Killed") || (msg.contains("killed by") || ((msg.contains("derrubou")) && (msg.contains("de muito alto!"))) || (msg.contains("foi morto por")) || (msg.contains("thrown off a cliff") || (msg.contains("slain by")) || (msg.contains("thrown into the void")) || (msg.contains("foi jogado no void por") || msg.contains("morreu para"))))) {
                    if (msg.contains(String.valueOf(getPlayer().getName()))) {
                        if (mc.thePlayer != null) {
                            final EntityLivingBase ent = Aura.target;
                            if (!msg.contains(mc.thePlayer.getName())) {
                                return;
                            }
                            
                            String[] messages = {"Why you is gringe," + ent.getName() + "?",
                            		
                            ent.getName() + " why you is kinda SUS???w",
                            
                            "\\L\\ you died on a brick game " + ent.getName(),
                            
                            ent.getName() + " delete this skidded client!",
                            
                            ent.getName() + " you are not playing fortnite to die for cringe",
                            ent.getName() + " died because he has no sponsors",
                            "killed by a sus clarinet, " + ent.getName() + "!!1!",
                            ent.getName() + " died but idc",
                            ent.getName() + " tried to make dream clutch and gone wrong",
                            ent.getName() + " it has an iq of a sigma user",
                            ent.getName() + " forgot what to do with your life",
                            ent.getName() + " turned off your computer, ",
                            ent.getName() + " is a technoblade helper :(",
                            ent.getName() + " you died has killed by the air",
                            ent.getName() + "'s antivoid don't worked :skull:",
                            "'" + ent.getName() + "'" + " died :( press f",
                            ent.getName() + " probably plays fortnite lmao.",
                            ent.getName() + " deleted system32",
                            "you lose a 1v1 with me " + ent.getName() + "!",
                            ent.getName() + ", you probably have alzheimer",
                            ent.getName() + ", you really like taking L's.",
                            ent.getName() + " called 911!",
                            ent.getName() + " stop playing with your foot!",
                            ent.getName() + ".exe stopped working",
                            ent.getName() + " is probably an FDP user",
                            "called me nigga and died for racism, " + ent.getName(),
                            "bad gaming chair " + ent.getName(),
                            ent.getName() + " forgot how to survive",
                            ent.getName() + " stop being a noob! use " + Clarinet.name + "!",
                            ent.getName() + ", do you really like dying this much?",
                            ent.getName() + " probably reported me (gringe)",
                            ent.getName() + " died for AFK"};
                            
                            if (this.counter >= messages.length) {
                                this.counter = 0;
                            }
                            
                            mc.thePlayer.sendChatMessage(messages[this.counter]);
                            
                            ++this.counter;
                        }
                        
                    }
                }
            } catch (Exception CringeSigmaRedeSky2020CrabbyFreeOneHalal) {
            	CringeSigmaRedeSky2020CrabbyFreeOneHalal.printStackTrace();
            } 
    	}
    }
    
    protected static EntityPlayerSP getPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }
}
