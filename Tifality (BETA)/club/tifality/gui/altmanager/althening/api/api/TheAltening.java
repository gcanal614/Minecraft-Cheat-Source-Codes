/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.altmanager.althening.api.api;

import club.tifality.gui.altmanager.althening.api.api.data.AccountData;
import club.tifality.gui.altmanager.althening.api.api.data.LicenseData;
import club.tifality.gui.altmanager.althening.api.api.util.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class TheAltening
extends HttpUtils {
    private final String apiKey;
    private final String endpoint = "http://api.thealtening.com/v1/";
    private final Logger logger = Logger.getLogger("TheAltening");
    private final Gson gson = new Gson();

    public TheAltening(String v1) {
        this.apiKey = v1;
    }

    public LicenseData getLicenseData() {
        try {
            System.setProperty("http.agent", "chrome");
            String v2 = this.connect(String.format("http://api.thealtening.com/v1/license?token=%s", this.apiKey));
            return this.gson.fromJson(v2, LicenseData.class);
        }
        catch (IOException v3) {
            if (v3.getMessage().contains("401")) {
                this.logger.info("Invalid API Key provided");
            } else {
                this.logger.info("Failed to communicate with the website. Try again later");
            }
            return null;
        }
    }

    public AccountData getAccountData() {
        try {
            String v2 = this.connect(String.format("http://api.thealtening.com/v1/generate?info=true&token=%s", this.apiKey));
            return this.gson.fromJson(v2, AccountData.class);
        }
        catch (IOException v3) {
            if (v3.getMessage().contains("401")) {
                this.logger.info("Invalid API Key provided");
            } else {
                this.logger.info("Failed to communicate with the website. Try again later");
            }
            return null;
        }
    }

    public boolean isPrivate(String v1) {
        try {
            String v2 = this.connect("http://api.thealtening.com/v1/private?acctoken=" + v1 + "&token=" + this.apiKey);
            JsonObject v3 = this.gson.fromJson(v2, JsonObject.class);
            return v3 != null && v3.has("success") && v3.get("success").getAsBoolean();
        }
        catch (IOException v4) {
            if (v4.getMessage().contains("401")) {
                this.logger.info("Invalid API Key provided");
            } else {
                this.logger.info("Failed to communicate with the website. Try again later");
            }
            return false;
        }
    }

    public boolean isFavorite(String v1) {
        try {
            String v2 = this.connect("http://api.thealtening.com/v1/favorite?acctoken=" + v1 + "&token=" + this.apiKey);
            JsonObject v3 = this.gson.fromJson(v2, JsonObject.class);
            return v3 != null && v3.has("success") && v3.get("success").getAsBoolean();
        }
        catch (IOException v4) {
            if (v4.getMessage().contains("401")) {
                this.logger.info("Invalid API Key provided");
            } else {
                this.logger.info("Failed to communicate with the website. Try again later");
            }
            return false;
        }
    }

    public static class Asynchronous {
        private TheAltening theAltening;

        public Asynchronous(TheAltening v1) {
            this.theAltening = v1;
        }

        public CompletableFuture<LicenseData> getLicenseData() {
            return CompletableFuture.supplyAsync(this.theAltening::getLicenseData);
        }

        public CompletableFuture<AccountData> getAccountData() {
            return CompletableFuture.supplyAsync(this.theAltening::getAccountData);
        }

        public CompletableFuture<Boolean> isPrivate(String v1) {
            return CompletableFuture.supplyAsync(() -> this.theAltening.isPrivate(v1));
        }

        public CompletableFuture<Boolean> isFavorite(String v1) {
            return CompletableFuture.supplyAsync(() -> this.theAltening.isFavorite(v1));
        }
    }
}

