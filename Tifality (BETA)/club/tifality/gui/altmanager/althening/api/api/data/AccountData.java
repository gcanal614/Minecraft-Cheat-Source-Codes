/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.altmanager.althening.api.api.data;

import club.tifality.gui.altmanager.althening.api.api.info.AccountInfo;

public class AccountData {
    private String token;
    private String password;
    private String username;
    private boolean limit;
    private AccountInfo info;

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

    public AccountInfo getInfo() {
        return this.info;
    }

    public String toString() {
        return String.format("AccountData[%s:%s:%s:%s]", this.token, this.username, this.password, this.limit);
    }

    public boolean equals(Object v1) {
        if (!(v1 instanceof AccountData)) {
            return false;
        }
        AccountData v2 = (AccountData)v1;
        return v2.getUsername().equals(this.username) && v2.getToken().equals(this.token);
    }
}

