/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.altmanager.althening.api.api.data;

import com.google.gson.annotations.SerializedName;

public class LicenseData {
    private String username;
    private boolean premium;
    @SerializedName(value="premium_name")
    private String premiumName;
    @SerializedName(value="expires")
    private String expiryDate;

    public String getUsername() {
        return this.username;
    }

    public boolean isPremium() {
        return this.premium;
    }

    public String getPremiumName() {
        return this.premiumName;
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public String toString() {
        return String.format("LicenseData[%s:%s:%s:%s]", this.username, this.premium, this.premiumName, this.expiryDate);
    }

    public boolean equals(Object v1) {
        if (!(v1 instanceof LicenseData)) {
            return false;
        }
        LicenseData v2 = (LicenseData)v1;
        return v2.getExpiryDate().equals(this.getExpiryDate()) && v2.getPremiumName().equals(this.getPremiumName()) && v2.isPremium() == this.isPremium() && v2.getUsername().equals(this.getUsername());
    }
}

