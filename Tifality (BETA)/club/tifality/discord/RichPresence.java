/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package club.tifality.discord;

import club.tifality.Tifality;
import club.tifality.discord.RichPresence;
import club.tifality.module.Module;
import club.tifality.module.ModuleManager;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ServerData;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\u0006\u0010\u0011\u001a\u00020\u0010J\u0006\u0010\u0012\u001a\u00020\u0010J\u0006\u0010\u0013\u001a\u00020\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n \u000e*\u0004\u0018\u00010\r0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2={"Lclub/tifality/discord/RichPresence;", "", "()V", "appID", "", "assets", "", "", "ipcClient", "Lcom/jagrosh/discordipc/IPCClient;", "running", "", "timestamp", "Ljava/time/OffsetDateTime;", "kotlin.jvm.PlatformType", "loadConfiguration", "", "setup", "shutdown", "update", "Client"})
public final class RichPresence {
    private IPCClient ipcClient;
    private long appID;
    private final Map<String, String> assets;
    private final OffsetDateTime timestamp;
    private boolean running;

    public final void setup() {
        try {
            this.running = true;
            this.loadConfiguration();
            IPCClient iPCClient = this.ipcClient = new IPCClient(this.appID);
            if (iPCClient != null) {
                iPCClient.setListener(new IPCListener(this){
                    final /* synthetic */ RichPresence this$0;

                    public void onReady(@Nullable IPCClient client) {
                        ThreadsKt.thread$default(false, false, null, null, 0, new Function0<Unit>(this){
                            final /* synthetic */ setup.1 this$0;

                            public final void invoke() {
                                while (RichPresence.access$getRunning$p(this.this$0.this$0)) {
                                    this.this$0.this$0.update();
                                    try {
                                        Thread.sleep(1000L);
                                    }
                                    catch (InterruptedException interruptedException) {}
                                }
                            }
                            {
                                this.this$0 = var1_1;
                                super(0);
                            }
                        }, 31, null);
                    }

                    public void onClose(@Nullable IPCClient client, @Nullable JSONObject json) {
                        RichPresence.access$setRunning$p(this.this$0, false);
                    }
                    {
                        this.this$0 = this$0;
                    }
                });
            }
            IPCClient iPCClient2 = this.ipcClient;
            if (iPCClient2 != null) {
                iPCClient2.connect(new DiscordBuild[0]);
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    /*
     * WARNING - void declaration
     */
    public final void update() {
        block8: {
            RichPresence.Builder builder = new RichPresence.Builder();
            builder.setStartTimestamp(this.timestamp);
            if (this.assets.containsKey("logo")) {
                builder.setLargeImage(this.assets.get("logo"), "Playing Tifality");
            }
            Tifality tifality = Tifality.getInstance();
            Intrinsics.checkNotNullExpressionValue(tifality, "Tifality.getInstance()");
            if (tifality.getModuleManager() != null && this.assets.containsKey("logosmall")) {
                int n;
                void $this$count$iv;
                StringBuilder stringBuilder = new StringBuilder();
                Tifality tifality2 = Tifality.getInstance();
                Intrinsics.checkNotNullExpressionValue(tifality2, "Tifality.getInstance()");
                ModuleManager moduleManager = tifality2.getModuleManager();
                Intrinsics.checkNotNullExpressionValue(moduleManager, "Tifality.getInstance().moduleManager");
                Collection<Module> collection = moduleManager.getModules();
                Intrinsics.checkNotNullExpressionValue(collection, "Tifality.getInstance().moduleManager.modules");
                Iterable iterable = collection;
                StringBuilder stringBuilder2 = stringBuilder;
                String string = this.assets.get("logosmall");
                RichPresence.Builder builder2 = builder;
                boolean $i$f$count = false;
                if ($this$count$iv instanceof Collection && ((Collection)$this$count$iv).isEmpty()) {
                    n = 0;
                } else {
                    int count$iv = 0;
                    for (Object element$iv : $this$count$iv) {
                        Module it = (Module)element$iv;
                        boolean bl = false;
                        Module module = it;
                        Intrinsics.checkNotNullExpressionValue(module, "it");
                        if (!module.isEnabled()) continue;
                        int n2 = ++count$iv;
                        boolean bl2 = false;
                        if (n2 >= 0) continue;
                        CollectionsKt.throwCountOverflow();
                    }
                    n = count$iv;
                }
                int n3 = n;
                StringBuilder stringBuilder3 = stringBuilder2.append(n3).append('/');
                Tifality tifality3 = Tifality.getInstance();
                Intrinsics.checkNotNullExpressionValue(tifality3, "Tifality.getInstance()");
                ModuleManager moduleManager2 = tifality3.getModuleManager();
                Intrinsics.checkNotNullExpressionValue(moduleManager2, "Tifality.getInstance().moduleManager");
                builder2.setSmallImage(string, stringBuilder3.append(moduleManager2.getModules().size()).append(" modules!").toString());
            }
            if (Minecraft.getMinecraft().thePlayer != null) {
                Minecraft minecraft = Minecraft.getMinecraft();
                Intrinsics.checkNotNullExpressionValue(minecraft, "Minecraft.getMinecraft()");
                ServerData serverData = minecraft.getCurrentServerData();
                StringBuilder stringBuilder = new StringBuilder().append("In ");
                Minecraft minecraft2 = Minecraft.getMinecraft();
                Intrinsics.checkNotNullExpressionValue(minecraft2, "Minecraft.getMinecraft()");
                builder.setDetails(stringBuilder.append(minecraft2.isIntegratedServerRunning() || serverData == null ? "Singleplayer" : serverData.serverIP).toString());
                StringBuilder stringBuilder4 = new StringBuilder().append("Ig: ");
                EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().thePlayer;
                Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "Minecraft.getMinecraft().thePlayer");
                builder.setState(stringBuilder4.append(entityPlayerSP.getName()).toString());
            } else {
                builder.setDetails("Idle...");
            }
            IPCClient iPCClient = this.ipcClient;
            if ((iPCClient != null ? iPCClient.getStatus() : null) != PipeStatus.CONNECTED) break block8;
            IPCClient iPCClient2 = this.ipcClient;
            if (iPCClient2 != null) {
                iPCClient2.sendRichPresence(builder.build());
            }
        }
    }

    public final void shutdown() {
        IPCClient iPCClient = this.ipcClient;
        if ((iPCClient != null ? iPCClient.getStatus() : null) != PipeStatus.CONNECTED) {
            return;
        }
        try {
            IPCClient iPCClient2 = this.ipcClient;
            if (iPCClient2 != null) {
                iPCClient2.close();
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    private final void loadConfiguration() {
        this.appID = 859094460490842163L;
        this.assets.put("logo", "doi_moi");
        this.assets.put("logosmall", "cutecate");
    }

    public RichPresence() {
        boolean bl = false;
        this.assets = new LinkedHashMap();
        this.timestamp = OffsetDateTime.now();
    }

    public static final /* synthetic */ boolean access$getRunning$p(RichPresence $this) {
        return $this.running;
    }

    public static final /* synthetic */ void access$setRunning$p(RichPresence $this, boolean bl) {
        $this.running = bl;
    }
}

