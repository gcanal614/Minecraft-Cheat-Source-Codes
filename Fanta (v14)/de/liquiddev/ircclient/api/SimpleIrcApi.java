/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.api;

import de.liquiddev.ircclient.api.IrcApi;
import de.liquiddev.ircclient.api.SimpleIrcApi$1;
import de.liquiddev.ircclient.api.SimpleIrcApi$10;
import de.liquiddev.ircclient.api.SimpleIrcApi$11;
import de.liquiddev.ircclient.api.SimpleIrcApi$12;
import de.liquiddev.ircclient.api.SimpleIrcApi$13;
import de.liquiddev.ircclient.api.SimpleIrcApi$14;
import de.liquiddev.ircclient.api.SimpleIrcApi$15;
import de.liquiddev.ircclient.api.SimpleIrcApi$16;
import de.liquiddev.ircclient.api.SimpleIrcApi$17;
import de.liquiddev.ircclient.api.SimpleIrcApi$18;
import de.liquiddev.ircclient.api.SimpleIrcApi$19;
import de.liquiddev.ircclient.api.SimpleIrcApi$2;
import de.liquiddev.ircclient.api.SimpleIrcApi$20;
import de.liquiddev.ircclient.api.SimpleIrcApi$21;
import de.liquiddev.ircclient.api.SimpleIrcApi$22;
import de.liquiddev.ircclient.api.SimpleIrcApi$23;
import de.liquiddev.ircclient.api.SimpleIrcApi$24;
import de.liquiddev.ircclient.api.SimpleIrcApi$25;
import de.liquiddev.ircclient.api.SimpleIrcApi$26;
import de.liquiddev.ircclient.api.SimpleIrcApi$27;
import de.liquiddev.ircclient.api.SimpleIrcApi$28;
import de.liquiddev.ircclient.api.SimpleIrcApi$29;
import de.liquiddev.ircclient.api.SimpleIrcApi$3;
import de.liquiddev.ircclient.api.SimpleIrcApi$30;
import de.liquiddev.ircclient.api.SimpleIrcApi$31;
import de.liquiddev.ircclient.api.SimpleIrcApi$32;
import de.liquiddev.ircclient.api.SimpleIrcApi$4;
import de.liquiddev.ircclient.api.SimpleIrcApi$5;
import de.liquiddev.ircclient.api.SimpleIrcApi$6;
import de.liquiddev.ircclient.api.SimpleIrcApi$7;
import de.liquiddev.ircclient.api.SimpleIrcApi$8;
import de.liquiddev.ircclient.api.SimpleIrcApi$9;

public abstract class SimpleIrcApi
implements IrcApi {
    static final String DEFAULT_PREFIX = String.valueOf(new SimpleIrcApi$1().toString()) + "\u00a7" + new SimpleIrcApi$2().toString() + "\u00a7" + new SimpleIrcApi$3().toString() + "\u00a7" + new SimpleIrcApi$4().toString() + "\u00a7" + new SimpleIrcApi$5().toString();
    private String prefix = DEFAULT_PREFIX;

    public abstract void addChat(String var1);

    /*
     * WARNING - void declaration
     */
    @Override
    public void onChatMessage(String chatMessage) {
        void var1_1;
        this.addChat(String.valueOf(this.getPrefix()) + (String)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void onPlayerChatMessage(String player, String client, String message) {
        void var3_3;
        void var1_1;
        void var2_2;
        this.addChat(String.valueOf(new SimpleIrcApi$6(this).toString()) + "\u00a7" + new SimpleIrcApi$7(this).toString() + (String)var2_2 + new SimpleIrcApi$8(this).toString() + (String)var1_1 + new SimpleIrcApi$9(this).toString() + "\u00a7" + new SimpleIrcApi$10(this).toString() + "\u00a7" + new SimpleIrcApi$11(this).toString() + (String)var3_3);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void onLocalPlayerChatMessage(String player, String client, String message) {
        void var3_3;
        void var1_1;
        void var2_2;
        this.addChat(String.valueOf(new SimpleIrcApi$12(this).toString()) + "\u00a7" + new SimpleIrcApi$13(this).toString() + "\u00a7" + new SimpleIrcApi$14(this).toString() + "\u00a7" + new SimpleIrcApi$15(this).toString() + "\u00a7" + new SimpleIrcApi$16(this).toString() + (String)var2_2 + new SimpleIrcApi$17(this).toString() + (String)var1_1 + new SimpleIrcApi$18(this).toString() + "\u00a7" + new SimpleIrcApi$19(this).toString() + "\u00a7" + new SimpleIrcApi$20(this).toString() + (String)var3_3);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void onWhisperMessage(String player, String message, boolean isFromMe) {
        void var2_2;
        void var1_1;
        if (isFromMe) {
            this.addChat(String.valueOf(this.getPrefix()) + new SimpleIrcApi$21(this).toString() + "\u00a7" + new SimpleIrcApi$22(this).toString() + "\u00a7" + new SimpleIrcApi$23(this).toString() + "\u00a7" + new SimpleIrcApi$24(this).toString() + player + new SimpleIrcApi$25(this).toString() + "\u00a7" + new SimpleIrcApi$26(this).toString() + message);
            return;
        }
        this.addChat(String.valueOf(this.getPrefix()) + new SimpleIrcApi$27(this).toString() + "\u00a7" + new SimpleIrcApi$28(this).toString() + (String)var1_1 + new SimpleIrcApi$29(this).toString() + "\u00a7" + new SimpleIrcApi$30(this).toString() + "\u00a7" + new SimpleIrcApi$31(this).toString() + "\u00a7" + new SimpleIrcApi$32(this).toString() + (String)var2_2);
    }

    public String getPrefix() {
        return this.prefix;
    }

    /*
     * WARNING - void declaration
     */
    public SimpleIrcApi setPrefix(String prefix) {
        void var1_1;
        this.prefix = var1_1;
        return this;
    }
}

