package non.asset.utils.OFC.DCConnection;

/*
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;*/
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

public class DiscordARP {
	/*
	public Vec3 a = new Vec3(Vec3i.NULL_VECTOR);
	
	public static String DiscordUserName = "Start the client with the Discord";
	
	private boolean running = true;
	private long created = 0;
	
	public void start() {
	
		this.created = System.currentTimeMillis();
		
		DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
		
			
			
			public void apply(DiscordUser user) {
				System.out.println("Welcome " + user.username + "#" + user.discriminator);
				
				DiscordUserName = user.username + "#" + user.discriminator;
				
				
				update("OxyC", "");
				
			}
			
		}).build();
		
		DiscordRPC.discordInitialize(Vec3.idAPI1 + Vec3.idAPIAB33 + Vec3.idAPIAB1, handlers, true);
		
		new Thread("Discord RPC Callback") {
			
			@Override
			public void run() {
				while(running) {
					DiscordRPC.discordRunCallbacks();
				}
			}
		}.start();
		
		
	}
	
	public void shutdown() {
		running = false;
		DiscordRPC.discordShutdown();
	}
	
	public void update(String firstLine, String secondLine) {
		DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(secondLine);
		b.setBigImage("richpresence", secondLine);
		b.setDetails(firstLine);
		b.setStartTimestamps(created);
		
		DiscordRPC.discordUpdatePresence(b.build());
	}
	*/
	
}
