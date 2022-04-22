package com.zerosense.Utils;

public class ChatEvent extends Cancelable {

    private String chatMessage;

    public ChatEvent(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }
}
