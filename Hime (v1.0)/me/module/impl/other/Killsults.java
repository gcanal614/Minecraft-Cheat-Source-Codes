package me.module.impl.other;


import java.util.Random;

import me.Hime;
import org.apache.commons.lang3.RandomUtils;

import me.event.impl.EventReceivePacket;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S02PacketChat;

public class Killsults extends Module{

	public Setting username;
	public Setting random;
	public String mode;
	
	public Killsults() {
		super("Killsults", 0, Category.MISC);
		this.addModes("Killsults mode", "Advertise Client", "HimeSigma", "Insult Player", "Sigma", "L Player");
		mode = this.getModes("Killsults mode");
        Hime.instance.settingsManager.rSetting(username = new Setting("Say Username", this, false));
        Hime.instance.settingsManager.rSetting(random = new Setting("Random Numbers At End", this, false));
	}
	
	
   private String[] insult = new String[] {
		   "get rekt noob!1!11!", "You probably watch tenebrous and enojoy it", 
		   "you probably use skidma free",
			"How did you even press the install button with that aim?",	
			"imagine using liquidbounce in 2021", "sigma user", "why are you so gay lmao",
			"get gud you roadkilled skunk", "stop snorting cookie crumbs and aim with your mouse",	"hohoho im santa, and ur a hoe",
			"Stop crying you asshair", "Stop being mad", "go commit not breath plz thx",
			"When you call ICE to deport someone, they deport you", "Stop reporting me and get a life", "i just shidded on you",
			"I'm not cheating, Tenebrous just hooked me up with Wurst", "Want to hear a joke? Your life",
			"What do you and watchdog have in common? Both of you are worthless.", "this is why your parents left you",
			"yo bro dont tell tenebrous!!!!!!", "Damn, you just got shit on harder then Archy.",
			"Oh no I am hacking?!?!??!", "you probably quickdrop irl", "moment", "Take a long walk off a short bridge",
			"Why would I cheat when I am recording?", "Your parents got divorced because of you",
			"Were you born on the highway? I heard that the most accidents happen there.", "You're the reason why society is failing", "If the world ended, you would be the reason", 
			"Your parents wanted to abort you but the doctors were scared of you", "I am so sorry that I am hacking your favourite server", "hoes mad", "Your free trial of life has expired",
			"Bet you cannot pull out irl", "You're socially awkward", "Wait guys you cannot fly?", "Jeez, even if you were using sigma you'd be better",
			"I bet you believe in the flat earth","Even the mc virgins are less virgin than you", "some children are dropped at birth, but you were obviously thrown like a football",
			"Your asshole got stretched", "Your penis is the size of an electron", "earning check? oh wait you have none",	
			"Your balls got crushed", "Enjoy cock torture", "You deserve waterboarding", "get fard on", "you're so gay you spray painted your iphone rainbow",
			"New badlion client update lookin thicc", "I don't cheat, I just use the new Badlion client fps booster", "I'd say you're hacking, but your IQ is too low to know how to use hacks",
			"Blue waffle is such a wholesome video, you should search it up!",
			"Looking at Zootopia Rule 34 is better then looking at your face.", "I'd tell you to kill yourself, but you don't know what a gun is.",
			"Have you ever tried being good?", "Even if you had hacks, I'd still beat you.", "moment when 2 packets disabler 100k anticheat",
			"rage at me at G8LOL#8445","kys l0l", "do /wdr plz", "If braces weren't invented, your teeth would be worse than the whole population of the UK."
   };
   
   private String[] sigma = new String[] {
		       "Learn your alphabet with the sigma client: Omikron, Sigma, Epsilon, Alpha!",
               "Download Sigma to kick ass while listening to some badass music!",
               "Why Sigma? Cause it is the addition of pure skill and incredible intellectual abilities",
               "Want some skills? Check out sigma client.Info!",
               "You have been oofed by Sigma oof oof",
               "I am not racist, but I only like Sigma users. so git gut noobs",
               "Quick Quiz: I am zeus's son, who am I? SIGMA",
               "Wow! My combo is Sigma'n!",
               "What should I choose? Sigma or Sigma?",
               "Bigmama and Sigmama",
               "I don't hack I just sigma",
               "Sigma client . Info is your new home",
               "Look a divinity! He definitely must use sigma!",
               "In need of a cute present for Christmas? Sigma is all you need!",
               "I have a good sigma config, don't blame me",
               "Don't piss me off or you will discover the true power of Sigma's inf reach",
               "Sigma never dies",
               "Maybe I will be Sigma, I am already Sigma",
               "Sigma will help you! Oops, i killed you instead.",
               "NoHaxJustSigma",
               "Do like Tenebrous, subscribe to LeakedPvP!",
               "Did I really just forget that melody? Si sig sig sig Sigma",
               "Sigma. The only client run by speakers of Breton",
               "Order free baguettes with Sigma client",
               "Another Sigma user? Awww man",
               "Sigma utility client no hax 100%",
               "Hypixel wants to know Sigma owner's location [Accept] [Deny]",
               "I am a sig-magician, thats how I am able to do all those block game tricks",
               "Stop it, get some help! Get Sigma",
               "Sigma users belike: Hit or miss I guess I never miss!",
               "I dont hack i just have Sigma Gaming Chair",
               "Stop Hackustation me cuz im just Sigma",
               "S. I. G. M. A. Hack with me today!",
               "Subscribe to MentalFrostbyte on youtube and discover Jello for Sigma!",
               "Beauty is not in the face; beauty is in Jello for Sigma",
               "Imagine using anything but Sigma",
               "No hax just beta testing the anti-cheat with Sigma",
               "Don't forget to report me for Sigma on the forums!",
               "Search sigmaclient , info to get the best mineman skills!",
               "don't use Sigma? ok boomer",
               "Sigma is better than Optifine",
               "It's not Scaffold it's BlockFly in Jello for Sigma!",
               "How come a noob like you not use Sigma?",
               "A mother becomes a true grandmother the day she gets Sigma 5.0",
               "Fly faster than light, only available in Sigma§",
               "Behind every Sigma user, is an incredibly cool human being. Trust me, cooler than you.",
               "Hello Sigma my old friend...",
               "#SwitchToSigma5",
               "What? You've never downloaded Jello for Sigma? You know it's the best right?",
               "Your client sucks, just get Sigma",
               "Sigma made this world a better place, killing you with it even more"
               };
   
   private String[] Hime2 = new String[] {
	       "Learn your alphabet with the Hime client: G8LOL, Hime, Epsilon, Alpha!",
           "Why Hime? Cause it is the addition of pure skill and incredible intellectual abilities",
           "Want some skills? Check out Hime client here! https://discord.gg/W8wwe5pkNB",
           "You have been oofed by Hime  oof oof",
           "I am not racist, but I only like Hime users. so git gut noobs",
           "Quick Quiz: I am zeus's son, who am I? Hime",
           "Wow! My combo is Hime'n!",
           "What should I choose? Hime or Hime?",
           "I don't hack I just use Hime",
           "Look a divinity! He definitely must use Hime!",
           "In need of a cute present for Christmas? Hime is all you need!",
           "I have a good Hime config, don't blame me",
           "Don't piss me off or you will discover the true power of Hime's inf reach",
           "Hime never dies",
           "Maybe I will be Hime, I am already Hime",
           "Hime will help you! Oops, I killed you instead.",
           "NoHaxJustHime",
           "Did I really just forget that melody? ar art art Hime",
           "Hime. The only client run by speakers of Breton",
           "Order free baguettes with Hime client",
           "Another Hime user? Awww man",
           "Hime utility client no hax 100%",
           "Hypixel wants to know Hime owner's location [Accept] [Deny]",
           "Stop it, get some help! Get Hime",
           "Hime users belike: Hit or miss I guess I never miss!",
           "I dont hack I just have Hime Gaming Chair",
           "Stop Hackustation me cuz im just Hime",
           "S. I. G. M. A. Hack with me today!",
           "Subscribe to G8LOL on youtube and discover Hime!",
           "Beauty is not in the face; beauty is in Hime",
           "Imagine using anything but Hime",
           "No hax just beta testing the anti-cheat with Hime",
           "Don't forget to report me for Hime on the forums!",
           "Join https://discord.gg/6adqxux4wM to get the best mineman skills!",
           "don't use Hime? ok boomer",
           "Hime is better than Optifine",
           "It's not BlockFly it's Scaffold in Hime!",
           "How come a noob like you doesn't use Hime?",
           "A mother becomes a true grandmother the day she gets Hime",
           "Fly faster than light, only available in Hime§",
           "Behind every Hime user, is an incredibly cool human being. Trust me, cooler than you.",
           "Hello Hime my old friend...",
           "#SwitchToHime",
           "What? You've never downloaded Hime? You know it's the best right?",
           "Your client sucks, just get Hime",
           "Hime made this world a better place, killing you with it even more"
           };
	
    private String[] advertise = new String[] {
    		"these bypass values are exclusive",
			"why use skidma in 2021 when you could be using Hime?",
			"Hime > everything else",
			"you need Hime Client lol",
			"Im not cheating, im just using Hime",
			"stop being stupid and download Hime", 
			"download Hime fps booster!",
			"git Hime git gud",
			"get Hime get good",
			"no hax just Hime fps booster", 
			"Hime best free client L",
			"Imagine not using Hime u skank",
			"What is good? Hime is!",
			"Download Hime or i execute ur b[a]lls",
			"im just using Hime fps booster", 
			"Hime > all",
			"Hime > optifine fastmath", 
			"learn how to click your mouse with Hime!", 
			"learn how to aim with Hime client working 2021!", 
			};
	
    @Handler
    public void onUpdate(EventUpdate event) {
    	mode = this.getModes("Killsults mode");
    }
    
    @Handler
    public void onReceive(EventReceivePacket event) {
      if (event.getPacket() instanceof S02PacketChat) {
        S02PacketChat packet = (S02PacketChat)event.getPacket();
        String message = packet.getChatComponent().getUnformattedText();
            if (!message.isEmpty()) {
				for (Object entity : mc.theWorld.loadedEntityList) {
					if (entity instanceof EntityPlayer) {
						EntityPlayer p = (EntityPlayer) entity;
						if (message.contains(p.getName()) && message.contains(mc.thePlayer.getName())
								&& (message.contains("killed") || message.contains("slain") || message.contains("knocked") || message.contains("thrown") || message.contains("foi morto por") || message.contains("morreu para") || message.contains("foi jogado no void por"))
								&& !p.getName().equalsIgnoreCase(mc.thePlayer.getName())) {
							EntityPlayer e = (EntityPlayer) entity;
							switch(mode) {
							case "Advertise Client":
							if(this.username.getValBoolean()) {
							mc.thePlayer.sendQueue.addToSendQueue(
									new C01PacketChatMessage(this.randomPhrase() +  ", " + e.getName()));
							}else {
							  if(this.random.getValBoolean()) {
								mc.thePlayer.sendQueue.addToSendQueue(
										new C01PacketChatMessage(this.randomPhrase() +  " [" + RandomUtils.nextLong(4444L, 100000000L) + "]"));
							  }else {
								  mc.thePlayer.sendQueue.addToSendQueue(
											new C01PacketChatMessage(this.randomPhrase()));  
							  }
							}
							break;
							case "L Player":
								 mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage("L " + e.getName()));  	
								break;
							case "Insult Player":
                        		if(this.username.getValBoolean()) {
        							mc.thePlayer.sendQueue.addToSendQueue(
        								new C01PacketChatMessage(this.randomPhrase2() +  ", " + e.getName()));
        				        }else {
        				        	if(this.random.getValBoolean()) {
        								mc.thePlayer.sendQueue.addToSendQueue(
        										new C01PacketChatMessage(this.randomPhrase2() +  " [" + RandomUtils.nextLong(4444L, 100000000L) + "]"));
        							  }else {
        								  mc.thePlayer.sendQueue.addToSendQueue(
        											new C01PacketChatMessage(this.randomPhrase2()));  
        							  }
        						}
                        		break;
							case "Sigma":
                        		if(this.username.getValBoolean()) {
        							mc.thePlayer.sendQueue.addToSendQueue(
        								new C01PacketChatMessage(this.randomPhrase3() +  ", " + e.getName()));
        				        }else {
        				        	if(this.random.getValBoolean()) {
        								mc.thePlayer.sendQueue.addToSendQueue(
        										new C01PacketChatMessage(this.randomPhrase3() +  " [" + RandomUtils.nextLong(4444L, 100000000L) + "]"));
        							  }else {
        								  mc.thePlayer.sendQueue.addToSendQueue(
        											new C01PacketChatMessage(this.randomPhrase3()));  
        							  }
        						}
                        		break;
							case "HimeSigma":
                        		if(this.username.getValBoolean()) {
        							mc.thePlayer.sendQueue.addToSendQueue(
        								new C01PacketChatMessage(this.randomPhrase4() +  ", " + e.getName()));
        				        }else {
        				        	if(this.random.getValBoolean()) {
        								mc.thePlayer.sendQueue.addToSendQueue(
        										new C01PacketChatMessage(this.randomPhrase4() +  " [" + RandomUtils.nextLong(4444L, 100000000L) + "]"));
        							  }else {
        								  mc.thePlayer.sendQueue.addToSendQueue(
        											new C01PacketChatMessage(this.randomPhrase4()));  
        							  }
        						}
                        		break;
                          }
						}
					}
				}
    		}
          }
      }
    
    private String randomPhrase() {
		Random random = new Random();
		return advertise[random.nextInt(advertise.length)];
	}
    
    private String randomPhrase2() {
  		Random random = new Random();
  		return insult[random.nextInt(insult.length)];
  	}
    
    private String randomPhrase3() {
  		Random random = new Random();
  		return sigma[random.nextInt(sigma.length)];
  	}
    
    private String randomPhrase4() {
  		Random random = new Random();
  		return Hime2[random.nextInt(Hime2.length)];
  	}
	
}