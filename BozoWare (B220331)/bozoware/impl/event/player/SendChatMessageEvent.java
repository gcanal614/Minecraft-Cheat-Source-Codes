// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.event.player;

import bozoware.base.event.CancellableEvent;

public class SendChatMessageEvent extends CancellableEvent
{
    private String message;
    
    public SendChatMessageEvent(final String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
}
