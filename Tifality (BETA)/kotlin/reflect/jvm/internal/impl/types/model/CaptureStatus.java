/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.types.model;

public final class CaptureStatus
extends Enum<CaptureStatus> {
    public static final /* enum */ CaptureStatus FOR_SUBTYPING;
    public static final /* enum */ CaptureStatus FOR_INCORPORATION;
    public static final /* enum */ CaptureStatus FROM_EXPRESSION;
    private static final /* synthetic */ CaptureStatus[] $VALUES;

    static {
        CaptureStatus[] captureStatusArray = new CaptureStatus[3];
        CaptureStatus[] captureStatusArray2 = captureStatusArray;
        captureStatusArray[0] = FOR_SUBTYPING = new CaptureStatus();
        captureStatusArray[1] = FOR_INCORPORATION = new CaptureStatus();
        captureStatusArray[2] = FROM_EXPRESSION = new CaptureStatus();
        $VALUES = captureStatusArray;
    }

    public static CaptureStatus[] values() {
        return (CaptureStatus[])$VALUES.clone();
    }

    public static CaptureStatus valueOf(String string) {
        return Enum.valueOf(CaptureStatus.class, string);
    }
}

