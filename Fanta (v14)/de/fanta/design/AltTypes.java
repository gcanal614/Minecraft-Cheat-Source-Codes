/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.design;

public class AltTypes {
    public String EmailName;
    public String PWName;

    public AltTypes(String Email, String PW) {
        this.EmailName = Email;
        this.PWName = PW;
    }

    public String getEmail() {
        return this.EmailName;
    }

    public String getPassword() {
        return this.PWName;
    }

    public void setEmail(String Email) {
        this.EmailName = Email;
    }

    public void setPassword(String PW) {
        this.PWName = PW;
    }
}

