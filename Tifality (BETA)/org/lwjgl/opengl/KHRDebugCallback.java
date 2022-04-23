/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import org.lwjgl.PointerWrapperAbstract;

public final class KHRDebugCallback
extends PointerWrapperAbstract {
    private static final int GL_DEBUG_SEVERITY_HIGH = 37190;
    private static final int GL_DEBUG_SEVERITY_MEDIUM = 37191;
    private static final int GL_DEBUG_SEVERITY_LOW = 37192;
    private static final int GL_DEBUG_SEVERITY_NOTIFICATION = 33387;
    private static final int GL_DEBUG_SOURCE_API = 33350;
    private static final int GL_DEBUG_SOURCE_WINDOW_SYSTEM = 33351;
    private static final int GL_DEBUG_SOURCE_SHADER_COMPILER = 33352;
    private static final int GL_DEBUG_SOURCE_THIRD_PARTY = 33353;
    private static final int GL_DEBUG_SOURCE_APPLICATION = 33354;
    private static final int GL_DEBUG_SOURCE_OTHER = 33355;
    private static final int GL_DEBUG_TYPE_ERROR = 33356;
    private static final int GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR = 33357;
    private static final int GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR = 33358;
    private static final int GL_DEBUG_TYPE_PORTABILITY = 33359;
    private static final int GL_DEBUG_TYPE_PERFORMANCE = 33360;
    private static final int GL_DEBUG_TYPE_OTHER = 33361;
    private static final int GL_DEBUG_TYPE_MARKER = 33384;
    private static final long CALLBACK_POINTER;
    private final Handler handler;

    public KHRDebugCallback() {
        this(new Handler(){

            public void handleMessage(int source, int type2, int id, int severity, String message) {
                String description2;
                System.err.println("[LWJGL] KHR_debug message");
                System.err.println("\tID: " + id);
                switch (source) {
                    case 33350: {
                        description2 = "API";
                        break;
                    }
                    case 33351: {
                        description2 = "WINDOW SYSTEM";
                        break;
                    }
                    case 33352: {
                        description2 = "SHADER COMPILER";
                        break;
                    }
                    case 33353: {
                        description2 = "THIRD PARTY";
                        break;
                    }
                    case 33354: {
                        description2 = "APPLICATION";
                        break;
                    }
                    case 33355: {
                        description2 = "OTHER";
                        break;
                    }
                    default: {
                        description2 = this.printUnknownToken(source);
                    }
                }
                System.err.println("\tSource: " + description2);
                switch (type2) {
                    case 33356: {
                        description2 = "ERROR";
                        break;
                    }
                    case 33357: {
                        description2 = "DEPRECATED BEHAVIOR";
                        break;
                    }
                    case 33358: {
                        description2 = "UNDEFINED BEHAVIOR";
                        break;
                    }
                    case 33359: {
                        description2 = "PORTABILITY";
                        break;
                    }
                    case 33360: {
                        description2 = "PERFORMANCE";
                        break;
                    }
                    case 33361: {
                        description2 = "OTHER";
                        break;
                    }
                    case 33384: {
                        description2 = "MARKER";
                        break;
                    }
                    default: {
                        description2 = this.printUnknownToken(type2);
                    }
                }
                System.err.println("\tType: " + description2);
                switch (severity) {
                    case 37190: {
                        description2 = "HIGH";
                        break;
                    }
                    case 37191: {
                        description2 = "MEDIUM";
                        break;
                    }
                    case 37192: {
                        description2 = "LOW";
                        break;
                    }
                    case 33387: {
                        description2 = "NOTIFICATION";
                        break;
                    }
                    default: {
                        description2 = this.printUnknownToken(severity);
                    }
                }
                System.err.println("\tSeverity: " + description2);
                System.err.println("\tMessage: " + message);
            }

            private String printUnknownToken(int token) {
                return "Unknown (0x" + Integer.toHexString(token).toUpperCase() + ")";
            }
        });
    }

    public KHRDebugCallback(Handler handler) {
        super(CALLBACK_POINTER);
        this.handler = handler;
    }

    Handler getHandler() {
        return this.handler;
    }

    static {
        long pointer = 0L;
        try {
            pointer = (Long)Class.forName("org.lwjgl.opengl.CallbackUtil").getDeclaredMethod("getDebugCallbackKHR", new Class[0]).invoke(null, new Object[0]);
        }
        catch (Exception exception) {
            // empty catch block
        }
        CALLBACK_POINTER = pointer;
    }

    public static interface Handler {
        public void handleMessage(int var1, int var2, int var3, int var4, String var5);
    }
}

