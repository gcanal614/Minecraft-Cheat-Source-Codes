/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.kotlin.TypeMappingMode$WhenMappings;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;

public final class TypeMappingMode {
    private final boolean needPrimitiveBoxing;
    private final boolean needInlineClassWrapping;
    private final boolean isForAnnotationParameter;
    private final boolean skipDeclarationSiteWildcards;
    private final boolean skipDeclarationSiteWildcardsIfPossible;
    private final TypeMappingMode genericArgumentMode;
    private final boolean kotlinCollectionsToJavaCollections;
    private final TypeMappingMode genericContravariantArgumentMode;
    private final TypeMappingMode genericInvariantArgumentMode;
    private final boolean mapTypeAliases;
    @JvmField
    @NotNull
    public static final TypeMappingMode GENERIC_ARGUMENT;
    @JvmField
    @NotNull
    public static final TypeMappingMode GENERIC_ARGUMENT_UAST;
    @JvmField
    @NotNull
    public static final TypeMappingMode RETURN_TYPE_BOXED;
    @JvmField
    @NotNull
    public static final TypeMappingMode DEFAULT;
    @JvmField
    @NotNull
    public static final TypeMappingMode DEFAULT_UAST;
    @JvmField
    @NotNull
    public static final TypeMappingMode CLASS_DECLARATION;
    @JvmField
    @NotNull
    public static final TypeMappingMode SUPER_TYPE;
    @JvmField
    @NotNull
    public static final TypeMappingMode SUPER_TYPE_KOTLIN_COLLECTIONS_AS_IS;
    @JvmField
    @NotNull
    public static final TypeMappingMode VALUE_FOR_ANNOTATION;
    public static final Companion Companion;

    @NotNull
    public final TypeMappingMode toGenericArgumentMode(@NotNull Variance effectiveVariance, boolean ofArray) {
        TypeMappingMode typeMappingMode;
        Intrinsics.checkNotNullParameter((Object)effectiveVariance, "effectiveVariance");
        if (ofArray && this.isForAnnotationParameter) {
            typeMappingMode = this;
        } else {
            switch (TypeMappingMode$WhenMappings.$EnumSwitchMapping$0[effectiveVariance.ordinal()]) {
                case 1: {
                    typeMappingMode = this.genericContravariantArgumentMode;
                    if (typeMappingMode != null) break;
                    typeMappingMode = this;
                    break;
                }
                case 2: {
                    typeMappingMode = this.genericInvariantArgumentMode;
                    if (typeMappingMode != null) break;
                    typeMappingMode = this;
                    break;
                }
                default: {
                    typeMappingMode = this.genericArgumentMode;
                    if (typeMappingMode != null) break;
                    typeMappingMode = this;
                }
            }
        }
        return typeMappingMode;
    }

    @NotNull
    public final TypeMappingMode wrapInlineClassesMode() {
        return new TypeMappingMode(this.needPrimitiveBoxing, true, this.isForAnnotationParameter, this.skipDeclarationSiteWildcards, this.skipDeclarationSiteWildcardsIfPossible, this.genericArgumentMode, this.kotlinCollectionsToJavaCollections, this.genericContravariantArgumentMode, this.genericInvariantArgumentMode, false, 512, null);
    }

    public final boolean getNeedPrimitiveBoxing() {
        return this.needPrimitiveBoxing;
    }

    public final boolean getNeedInlineClassWrapping() {
        return this.needInlineClassWrapping;
    }

    public final boolean isForAnnotationParameter() {
        return this.isForAnnotationParameter;
    }

    public final boolean getKotlinCollectionsToJavaCollections() {
        return this.kotlinCollectionsToJavaCollections;
    }

    public final boolean getMapTypeAliases() {
        return this.mapTypeAliases;
    }

    private TypeMappingMode(boolean needPrimitiveBoxing, boolean needInlineClassWrapping, boolean isForAnnotationParameter, boolean skipDeclarationSiteWildcards, boolean skipDeclarationSiteWildcardsIfPossible, TypeMappingMode genericArgumentMode, boolean kotlinCollectionsToJavaCollections, TypeMappingMode genericContravariantArgumentMode, TypeMappingMode genericInvariantArgumentMode, boolean mapTypeAliases) {
        this.needPrimitiveBoxing = needPrimitiveBoxing;
        this.needInlineClassWrapping = needInlineClassWrapping;
        this.isForAnnotationParameter = isForAnnotationParameter;
        this.skipDeclarationSiteWildcards = skipDeclarationSiteWildcards;
        this.skipDeclarationSiteWildcardsIfPossible = skipDeclarationSiteWildcardsIfPossible;
        this.genericArgumentMode = genericArgumentMode;
        this.kotlinCollectionsToJavaCollections = kotlinCollectionsToJavaCollections;
        this.genericContravariantArgumentMode = genericContravariantArgumentMode;
        this.genericInvariantArgumentMode = genericInvariantArgumentMode;
        this.mapTypeAliases = mapTypeAliases;
    }

    /* synthetic */ TypeMappingMode(boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, TypeMappingMode typeMappingMode, boolean bl6, TypeMappingMode typeMappingMode2, TypeMappingMode typeMappingMode3, boolean bl7, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            bl = true;
        }
        if ((n & 2) != 0) {
            bl2 = true;
        }
        if ((n & 4) != 0) {
            bl3 = false;
        }
        if ((n & 8) != 0) {
            bl4 = false;
        }
        if ((n & 0x10) != 0) {
            bl5 = false;
        }
        if ((n & 0x20) != 0) {
            typeMappingMode = null;
        }
        if ((n & 0x40) != 0) {
            bl6 = true;
        }
        if ((n & 0x80) != 0) {
            typeMappingMode2 = typeMappingMode;
        }
        if ((n & 0x100) != 0) {
            typeMappingMode3 = typeMappingMode;
        }
        if ((n & 0x200) != 0) {
            bl7 = false;
        }
        this(bl, bl2, bl3, bl4, bl5, typeMappingMode, bl6, typeMappingMode2, typeMappingMode3, bl7);
    }

    static {
        Companion = new Companion(null);
        GENERIC_ARGUMENT = new TypeMappingMode(false, false, false, false, false, null, false, null, null, false, 1023, null);
        GENERIC_ARGUMENT_UAST = new TypeMappingMode(false, false, false, false, false, null, false, null, null, true, 511, null);
        RETURN_TYPE_BOXED = new TypeMappingMode(false, true, false, false, false, null, false, null, null, false, 1021, null);
        boolean bl = false;
        TypeMappingMode typeMappingMode = null;
        TypeMappingMode typeMappingMode2 = null;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;
        boolean bl6 = false;
        boolean bl7 = false;
        TypeMappingMode typeMappingMode3 = GENERIC_ARGUMENT;
        DEFAULT = new TypeMappingMode(bl7, bl3, bl6, bl5, bl4, typeMappingMode3, bl2, typeMappingMode2, typeMappingMode, bl, 988, null);
        bl = true;
        typeMappingMode = null;
        typeMappingMode2 = null;
        bl2 = false;
        bl3 = false;
        bl4 = false;
        bl5 = false;
        bl6 = false;
        bl7 = false;
        typeMappingMode3 = GENERIC_ARGUMENT_UAST;
        DEFAULT_UAST = new TypeMappingMode(bl7, bl3, bl6, bl5, bl4, typeMappingMode3, bl2, typeMappingMode2, typeMappingMode, bl, 476, null);
        bl = false;
        typeMappingMode = null;
        typeMappingMode2 = null;
        bl2 = false;
        bl3 = true;
        bl4 = false;
        bl5 = false;
        bl6 = false;
        bl7 = false;
        typeMappingMode3 = GENERIC_ARGUMENT;
        CLASS_DECLARATION = new TypeMappingMode(bl7, bl3, bl6, bl5, bl4, typeMappingMode3, bl2, typeMappingMode2, typeMappingMode, bl, 988, null);
        SUPER_TYPE = new TypeMappingMode(false, false, false, true, false, GENERIC_ARGUMENT, false, null, null, false, 983, null);
        SUPER_TYPE_KOTLIN_COLLECTIONS_AS_IS = new TypeMappingMode(false, false, false, true, false, GENERIC_ARGUMENT, false, null, null, false, 919, null);
        bl = false;
        typeMappingMode = null;
        typeMappingMode2 = null;
        bl2 = false;
        TypeMappingMode typeMappingMode4 = GENERIC_ARGUMENT;
        bl4 = false;
        bl5 = false;
        bl6 = false;
        bl7 = false;
        boolean bl8 = true;
        VALUE_FOR_ANNOTATION = new TypeMappingMode(bl7, bl6, bl8, bl5, bl4, typeMappingMode4, bl2, typeMappingMode2, typeMappingMode, bl, 984, null);
    }

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

