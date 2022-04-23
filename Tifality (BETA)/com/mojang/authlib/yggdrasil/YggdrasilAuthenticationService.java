/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.authlib.yggdrasil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.exceptions.UserMigratedException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilGameProfileRepository;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.mojang.authlib.yggdrasil.response.ProfileSearchResultsResponse;
import com.mojang.authlib.yggdrasil.response.Response;
import com.mojang.util.UUIDTypeAdapter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.net.URL;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

public class YggdrasilAuthenticationService
extends HttpAuthenticationService {
    private final String clientToken;
    private final Gson gson;

    public YggdrasilAuthenticationService(Proxy proxy, String clientToken) {
        super(proxy);
        this.clientToken = clientToken;
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter((Type)((Object)GameProfile.class), new GameProfileSerializer());
        builder.registerTypeAdapter((Type)((Object)PropertyMap.class), new PropertyMap.Serializer());
        builder.registerTypeAdapter((Type)((Object)UUID.class), new UUIDTypeAdapter());
        builder.registerTypeAdapter((Type)((Object)ProfileSearchResultsResponse.class), new ProfileSearchResultsResponse.Serializer());
        this.gson = builder.create();
    }

    @Override
    public UserAuthentication createUserAuthentication(Agent agent) {
        return new YggdrasilUserAuthentication(this, agent);
    }

    @Override
    public MinecraftSessionService createMinecraftSessionService() {
        return new YggdrasilMinecraftSessionService(this);
    }

    @Override
    public GameProfileRepository createProfileRepository() {
        return new YggdrasilGameProfileRepository(this);
    }

    protected <T extends Response> T makeRequest(URL url, Object input, Class<T> classOfT) throws AuthenticationException {
        try {
            String jsonResult = input == null ? this.performGetRequest(url) : this.performPostRequest(url, this.gson.toJson(input), "application/json");
            Response result2 = (Response)this.gson.fromJson(jsonResult, classOfT);
            if (result2 == null) {
                return null;
            }
            if (StringUtils.isNotBlank(result2.getError())) {
                if ("UserMigratedException".equals(result2.getCause())) {
                    throw new UserMigratedException(result2.getErrorMessage());
                }
                if (result2.getError().equals("ForbiddenOperationException")) {
                    throw new InvalidCredentialsException(result2.getErrorMessage());
                }
                throw new AuthenticationException(result2.getErrorMessage());
            }
            return (T)result2;
        }
        catch (IOException e) {
            throw new AuthenticationUnavailableException("Cannot contact authentication server", e);
        }
        catch (IllegalStateException e) {
            throw new AuthenticationUnavailableException("Cannot contact authentication server", e);
        }
        catch (JsonParseException e) {
            throw new AuthenticationUnavailableException("Cannot contact authentication server", e);
        }
    }

    public String getClientToken() {
        return this.clientToken;
    }

    private static class GameProfileSerializer
    implements JsonSerializer<GameProfile>,
    JsonDeserializer<GameProfile> {
        private GameProfileSerializer() {
        }

        @Override
        public GameProfile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = (JsonObject)json;
            UUID id = object.has("id") ? (UUID)context.deserialize(object.get("id"), (Type)((Object)UUID.class)) : null;
            String name = object.has("name") ? object.getAsJsonPrimitive("name").getAsString() : null;
            return new GameProfile(id, name);
        }

        @Override
        public JsonElement serialize(GameProfile src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result2 = new JsonObject();
            if (src.getId() != null) {
                result2.add("id", context.serialize(src.getId()));
            }
            if (src.getName() != null) {
                result2.addProperty("name", src.getName());
            }
            return result2;
        }
    }
}

