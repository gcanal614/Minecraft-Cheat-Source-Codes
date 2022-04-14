package net.minecraft.event;

import java.util.*;
import com.google.common.collect.*;

public class ClickEvent
{
    private final Action action;
    private final String value;
    
    public ClickEvent(final Action p_i45156_1_, final String p_i45156_2_) {
        this.action = p_i45156_1_;
        this.value = p_i45156_2_;
    }
    
    public Action getAction() {
        return this.action;
    }
    
    public String getValue() {
        return this.value;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (p_equals_1_ == null || this.getClass() != p_equals_1_.getClass()) {
            return false;
        }
        final ClickEvent var2 = (ClickEvent)p_equals_1_;
        if (this.action != var2.action) {
            return false;
        }
        if (this.value != null) {
            if (!this.value.equals(var2.value)) {
                return false;
            }
        }
        else if (var2.value != null) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "ClickEvent{action=" + this.action + ", value='" + this.value + '\'' + '}';
    }
    
    @Override
    public int hashCode() {
        int var1 = this.action.hashCode();
        var1 = 31 * var1 + ((this.value != null) ? this.value.hashCode() : 0);
        return var1;
    }
    
    public enum Action
    {
        OPEN_URL("OPEN_URL", 0, "open_url", true), 
        OPEN_FILE("OPEN_FILE", 1, "open_file", false), 
        RUN_COMMAND("RUN_COMMAND", 2, "run_command", true), 
        TWITCH_USER_INFO("TWITCH_USER_INFO", 3, "twitch_user_info", false), 
        SUGGEST_COMMAND("SUGGEST_COMMAND", 4, "suggest_command", true), 
        CHANGE_PAGE("CHANGE_PAGE", 5, "change_page", true);
        
        private static final Map nameMapping;
        private static final Action[] $VALUES;
        private final boolean allowedInChat;
        private final String canonicalName;
        
        private Action(final String p_i45155_1_, final int p_i45155_2_, final String p_i45155_3_, final boolean p_i45155_4_) {
            this.canonicalName = p_i45155_3_;
            this.allowedInChat = p_i45155_4_;
        }
        
        public static Action getValueByCanonicalName(final String p_150672_0_) {
            return Action.nameMapping.get(p_150672_0_);
        }
        
        public boolean shouldAllowInChat() {
            return this.allowedInChat;
        }
        
        public String getCanonicalName() {
            return this.canonicalName;
        }
        
        static {
            nameMapping = Maps.newHashMap();
            $VALUES = new Action[] { Action.OPEN_URL, Action.OPEN_FILE, Action.RUN_COMMAND, Action.TWITCH_USER_INFO, Action.SUGGEST_COMMAND, Action.CHANGE_PAGE };
            for (final Action var4 : values()) {
                Action.nameMapping.put(var4.getCanonicalName(), var4);
            }
        }
    }
}
