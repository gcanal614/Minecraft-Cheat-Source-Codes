/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.authlib;

import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.UserType;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.util.UUIDTypeAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BaseUserAuthentication
implements UserAuthentication {
    private static final Logger LOGGER = LogManager.getLogger();
    protected static final String STORAGE_KEY_PROFILE_NAME = "displayName";
    protected static final String STORAGE_KEY_PROFILE_ID = "uuid";
    protected static final String STORAGE_KEY_PROFILE_PROPERTIES = "profileProperties";
    protected static final String STORAGE_KEY_USER_NAME = "username";
    protected static final String STORAGE_KEY_USER_ID = "userid";
    protected static final String STORAGE_KEY_USER_PROPERTIES = "userProperties";
    private final AuthenticationService authenticationService;
    private final PropertyMap userProperties = new PropertyMap();
    private String userid;
    private String username;
    private String password;
    private GameProfile selectedProfile;
    private UserType userType;

    protected BaseUserAuthentication(AuthenticationService authenticationService) {
        Validate.notNull(authenticationService);
        this.authenticationService = authenticationService;
    }

    @Override
    public boolean canLogIn() {
        return !this.canPlayOnline() && StringUtils.isNotBlank(this.getUsername()) && StringUtils.isNotBlank(this.getPassword());
    }

    @Override
    public void logOut() {
        this.password = null;
        this.userid = null;
        this.setSelectedProfile(null);
        this.getModifiableUserProperties().clear();
        this.setUserType(null);
    }

    @Override
    public boolean isLoggedIn() {
        return this.getSelectedProfile() != null;
    }

    @Override
    public void setUsername(String username) {
        if (this.isLoggedIn() && this.canPlayOnline()) {
            throw new IllegalStateException("Cannot change username whilst logged in & online");
        }
        this.username = username;
    }

    @Override
    public void setPassword(String password) {
        if (this.isLoggedIn() && this.canPlayOnline() && StringUtils.isNotBlank(password)) {
            throw new IllegalStateException("Cannot set password whilst logged in & online");
        }
        this.password = password;
    }

    protected String getUsername() {
        return this.username;
    }

    protected String getPassword() {
        return this.password;
    }

    @Override
    public void loadFromStorage(Map<String, Object> credentials) {
        this.logOut();
        this.setUsername(String.valueOf(credentials.get(STORAGE_KEY_USER_NAME)));
        this.userid = credentials.containsKey(STORAGE_KEY_USER_ID) ? String.valueOf(credentials.get(STORAGE_KEY_USER_ID)) : this.username;
        if (credentials.containsKey(STORAGE_KEY_USER_PROPERTIES)) {
            try {
                List list = (List)credentials.get(STORAGE_KEY_USER_PROPERTIES);
                for (Map propertyMap : list) {
                    String name = (String)propertyMap.get("name");
                    String value = (String)propertyMap.get("value");
                    String signature2 = (String)propertyMap.get("signature");
                    if (signature2 == null) {
                        this.getModifiableUserProperties().put(name, new Property(name, value));
                        continue;
                    }
                    this.getModifiableUserProperties().put(name, new Property(name, value, signature2));
                }
            }
            catch (Throwable t) {
                LOGGER.warn("Couldn't deserialize user properties", t);
            }
        }
        if (credentials.containsKey(STORAGE_KEY_PROFILE_NAME) && credentials.containsKey(STORAGE_KEY_PROFILE_ID)) {
            GameProfile profile = new GameProfile(UUIDTypeAdapter.fromString(String.valueOf(credentials.get(STORAGE_KEY_PROFILE_ID))), String.valueOf(credentials.get(STORAGE_KEY_PROFILE_NAME)));
            if (credentials.containsKey(STORAGE_KEY_PROFILE_PROPERTIES)) {
                try {
                    List list = (List)credentials.get(STORAGE_KEY_PROFILE_PROPERTIES);
                    for (Map propertyMap : list) {
                        String name = (String)propertyMap.get("name");
                        String value = (String)propertyMap.get("value");
                        String signature3 = (String)propertyMap.get("signature");
                        if (signature3 == null) {
                            profile.getProperties().put(name, new Property(name, value));
                            continue;
                        }
                        profile.getProperties().put(name, new Property(name, value, signature3));
                    }
                }
                catch (Throwable t) {
                    LOGGER.warn("Couldn't deserialize profile properties", t);
                }
            }
            this.setSelectedProfile(profile);
        }
    }

    @Override
    public Map<String, Object> saveForStorage() {
        GameProfile selectedProfile;
        HashMap<String, Object> result2 = new HashMap<String, Object>();
        if (this.getUsername() != null) {
            result2.put(STORAGE_KEY_USER_NAME, this.getUsername());
        }
        if (this.getUserID() != null) {
            result2.put(STORAGE_KEY_USER_ID, this.getUserID());
        } else if (this.getUsername() != null) {
            result2.put(STORAGE_KEY_USER_NAME, this.getUsername());
        }
        if (!this.getUserProperties().isEmpty()) {
            ArrayList properties2 = new ArrayList();
            for (Property userProperty : this.getUserProperties().values()) {
                HashMap<String, String> property = new HashMap<String, String>();
                property.put("name", userProperty.getName());
                property.put("value", userProperty.getValue());
                property.put("signature", userProperty.getSignature());
                properties2.add(property);
            }
            result2.put(STORAGE_KEY_USER_PROPERTIES, properties2);
        }
        if ((selectedProfile = this.getSelectedProfile()) != null) {
            result2.put(STORAGE_KEY_PROFILE_NAME, selectedProfile.getName());
            result2.put(STORAGE_KEY_PROFILE_ID, selectedProfile.getId());
            ArrayList properties3 = new ArrayList();
            for (Property profileProperty : selectedProfile.getProperties().values()) {
                HashMap<String, String> property = new HashMap<String, String>();
                property.put("name", profileProperty.getName());
                property.put("value", profileProperty.getValue());
                property.put("signature", profileProperty.getSignature());
                properties3.add(property);
            }
            if (!properties3.isEmpty()) {
                result2.put(STORAGE_KEY_PROFILE_PROPERTIES, properties3);
            }
        }
        return result2;
    }

    protected void setSelectedProfile(GameProfile selectedProfile) {
        this.selectedProfile = selectedProfile;
    }

    @Override
    public GameProfile getSelectedProfile() {
        return this.selectedProfile;
    }

    public String toString() {
        StringBuilder result2 = new StringBuilder();
        result2.append(this.getClass().getSimpleName());
        result2.append("{");
        if (this.isLoggedIn()) {
            result2.append("Logged in as ");
            result2.append(this.getUsername());
            if (this.getSelectedProfile() != null) {
                result2.append(" / ");
                result2.append(this.getSelectedProfile());
                result2.append(" - ");
                if (this.canPlayOnline()) {
                    result2.append("Online");
                } else {
                    result2.append("Offline");
                }
            }
        } else {
            result2.append("Not logged in");
        }
        result2.append("}");
        return result2.toString();
    }

    public AuthenticationService getAuthenticationService() {
        return this.authenticationService;
    }

    @Override
    public String getUserID() {
        return this.userid;
    }

    @Override
    public PropertyMap getUserProperties() {
        if (this.isLoggedIn()) {
            PropertyMap result2 = new PropertyMap();
            result2.putAll(this.getModifiableUserProperties());
            return result2;
        }
        return new PropertyMap();
    }

    protected PropertyMap getModifiableUserProperties() {
        return this.userProperties;
    }

    @Override
    public UserType getUserType() {
        if (this.isLoggedIn()) {
            return this.userType == null ? UserType.LEGACY : this.userType;
        }
        return null;
    }

    protected void setUserType(UserType userType) {
        this.userType = userType;
    }

    protected void setUserid(String userid) {
        this.userid = userid;
    }
}

