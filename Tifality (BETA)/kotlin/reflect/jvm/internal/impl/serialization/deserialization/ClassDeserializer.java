/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.Set;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassData;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationComponents;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializedPackageFragment;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ClassDeserializer {
    private final Function1<ClassKey, ClassDescriptor> classes;
    private final DeserializationComponents components;
    @NotNull
    private static final Set<ClassId> BLACK_LIST;
    public static final Companion Companion;

    @Nullable
    public final ClassDescriptor deserializeClass(@NotNull ClassId classId, @Nullable ClassData classData) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return this.classes.invoke(new ClassKey(classId, classData));
    }

    public static /* synthetic */ ClassDescriptor deserializeClass$default(ClassDeserializer classDeserializer, ClassId classId, ClassData classData, int n, Object object) {
        if ((n & 2) != 0) {
            classData = null;
        }
        return classDeserializer.deserializeClass(classId, classData);
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    private final ClassDescriptor createClass(ClassKey key) {
        block13: {
            block11: {
                block12: {
                    classId = key.getClassId();
                    for (Object factory : this.components.getFictitiousClassDescriptorFactories()) {
                        v0 = factory.createClass(classId);
                        if (v0 == null) continue;
                        var5_5 = v0;
                        var6_7 = false;
                        var7_9 = false;
                        it = var5_5;
                        $i$a$-let-ClassDeserializer$createClass$1 = false;
                        return it;
                    }
                    if (ClassDeserializer.BLACK_LIST.contains(classId)) {
                        return null;
                    }
                    v1 = key.getClassData();
                    if (v1 == null) {
                        v1 = this.components.getClassDataFinder().findClassData(classId);
                    }
                    if (v1 == null) {
                        return null;
                    }
                    var7_10 = v1;
                    factory = var7_10.component1();
                    var4_3 = var7_10.component2();
                    var5_6 = var7_10.component3();
                    sourceElement = var7_10.component4();
                    outerClassId = classId.getOuterClassId();
                    if (outerClassId == null) break block12;
                    v2 = ClassDeserializer.deserializeClass$default(this, outerClassId, null, 2, null);
                    if (!(v2 instanceof DeserializedClassDescriptor)) {
                        v2 = null;
                    }
                    v3 = (DeserializedClassDescriptor)v2;
                    if (v3 == null) {
                        return null;
                    }
                    outerClass = v3;
                    v4 = classId.getShortClassName();
                    Intrinsics.checkNotNullExpressionValue(v4, "classId.shortClassName");
                    if (!outerClass.hasNestedClass$deserialization(v4)) {
                        return null;
                    }
                    v5 = outerClass.getC();
                    break block13;
                }
                v6 = this.components.getPackageFragmentProvider();
                v7 = classId.getPackageFqName();
                Intrinsics.checkNotNullExpressionValue(v7, "classId.packageFqName");
                fragments = v6.getPackageFragments(v7);
                $this$firstOrNull$iv = fragments;
                $i$f$firstOrNull = false;
                for (T element$iv : $this$firstOrNull$iv) {
                    it = (PackageFragmentDescriptor)element$iv;
                    $i$a$-firstOrNull-ClassDeserializer$createClass$outerContext$fragment$1 = false;
                    if (!(it instanceof DeserializedPackageFragment)) ** GOTO lbl-1000
                    v8 = (DeserializedPackageFragment)it;
                    v9 = classId.getShortClassName();
                    Intrinsics.checkNotNullExpressionValue(v9, "classId.shortClassName");
                    if (v8.hasTopLevelClass(v9)) lbl-1000:
                    // 2 sources

                    {
                        v10 = true;
                    } else {
                        v10 = false;
                    }
                    if (!v10) continue;
                    v11 = element$iv;
                    break block11;
                }
                v11 = null;
            }
            v12 = v11;
            if (v12 == null) {
                return null;
            }
            fragment = v12;
            v13 = classProto.getTypeTable();
            Intrinsics.checkNotNullExpressionValue(v13, "classProto.typeTable");
            v14 = new TypeTable(v13);
            v15 = classProto.getVersionRequirementTable();
            Intrinsics.checkNotNullExpressionValue(v15, "classProto.versionRequirementTable");
            v5 = this.components.createContext(fragment, (NameResolver)nameResolver, v14, VersionRequirementTable.Companion.create(v15), (BinaryVersion)metadataVersion, null);
        }
        outerContext = v5;
        return new DeserializedClassDescriptor(outerContext, (ProtoBuf.Class)classProto, (NameResolver)nameResolver, (BinaryVersion)metadataVersion, sourceElement);
    }

    public ClassDeserializer(@NotNull DeserializationComponents components) {
        Intrinsics.checkNotNullParameter(components, "components");
        this.components = components;
        this.classes = this.components.getStorageManager().createMemoizedFunctionWithNullableValues((Function1)new Function1<ClassKey, ClassDescriptor>(this){
            final /* synthetic */ ClassDeserializer this$0;

            @Nullable
            public final ClassDescriptor invoke(@NotNull ClassKey key) {
                Intrinsics.checkNotNullParameter(key, "key");
                return ClassDeserializer.access$createClass(this.this$0, key);
            }
            {
                this.this$0 = classDeserializer;
                super(1);
            }
        });
    }

    static {
        Companion = new Companion(null);
        BLACK_LIST = SetsKt.setOf(ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.cloneable.toSafe()));
    }

    public static final /* synthetic */ ClassDescriptor access$createClass(ClassDeserializer $this, ClassKey key) {
        return $this.createClass(key);
    }

    private static final class ClassKey {
        @NotNull
        private final ClassId classId;
        @Nullable
        private final ClassData classData;

        public boolean equals(@Nullable Object other) {
            return other instanceof ClassKey && Intrinsics.areEqual(this.classId, ((ClassKey)other).classId);
        }

        public int hashCode() {
            return this.classId.hashCode();
        }

        @NotNull
        public final ClassId getClassId() {
            return this.classId;
        }

        @Nullable
        public final ClassData getClassData() {
            return this.classData;
        }

        public ClassKey(@NotNull ClassId classId, @Nullable ClassData classData) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            this.classId = classId;
            this.classData = classData;
        }
    }

    public static final class Companion {
        @NotNull
        public final Set<ClassId> getBLACK_LIST() {
            return BLACK_LIST;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

