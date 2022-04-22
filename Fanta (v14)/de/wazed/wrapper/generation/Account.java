/*
 * Decompiled with CFR 0.152.
 */
package de.wazed.wrapper.generation;

import java.util.HashMap;

public class Account {
    private String token;
    private String password;
    private String username;
    private boolean limit;
    private HashMap<String, String> info;

    public Account(String token, String password, String username, boolean limit, HashMap<String, String> info) {
        this.token = token;
        this.password = password;
        this.username = username;
        this.limit = limit;
        this.info = info;
    }

    public String getToken() {
        return this.token;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isLimit() {
        return this.limit;
    }

    public HashMap<String, String> getInfo() {
        return this.info;
    }
}

