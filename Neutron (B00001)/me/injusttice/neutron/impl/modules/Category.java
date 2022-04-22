package me.injusttice.neutron.impl.modules;

import java.awt.Color;

public enum Category {
    COMBAT("Combat", new Color(141, 68, 173)),
    MOVEMENT("Movement", new Color(141, 68, 173)),
    PLAYER("Player", new Color(141, 68, 173)),
    EXPLOIT("Exploit", new Color(141, 68, 173)),
    OTHER("Other", new Color(141, 68, 173)),
    GHOST("Ghost", new Color(141, 68, 173)),
    VISUAL("Visual", new Color(141, 68, 173));

    public boolean expanded = false;
    public Color pastelColor;
    public String name;
    public int posX;
    public int posY;

    Category(String name, Color pastelColor) {
        this.name = name;
        this.pastelColor = pastelColor;
    }
}
