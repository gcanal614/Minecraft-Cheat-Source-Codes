/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.KCallableImpl;
import kotlin.reflect.jvm.internal.KDeclarationContainerImpl;
import kotlin.reflect.jvm.internal.KPackageImpl;
import kotlin.reflect.jvm.internal.ReflectProperties;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectKotlinClass;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.KotlinClassHeader;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoBufUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMetadataVersion;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmNameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmProtoBufUtil;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001:\u0001,B\u001d\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0096\u0002J\u0016\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\b2\u0006\u0010#\u001a\u00020$H\u0016J\u0012\u0010%\u001a\u0004\u0018\u00010&2\u0006\u0010'\u001a\u00020(H\u0016J\u0016\u0010)\u001a\b\u0012\u0004\u0012\u00020&0\b2\u0006\u0010#\u001a\u00020$H\u0016J\b\u0010*\u001a\u00020(H\u0016J\b\u0010+\u001a\u00020\u0005H\u0016R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR$\u0010\f\u001a\u0018\u0012\u0014\u0012\u0012 \u000f*\b\u0018\u00010\u000eR\u00020\u00000\u000eR\u00020\u00000\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0018\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001e\u0010\u0012\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00130\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u000bR\u0018\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u00038TX\u0094\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0011R\u0014\u0010\u0017\u001a\u00020\u00188BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001c\u00a8\u0006-"}, d2={"Lkotlin/reflect/jvm/internal/KPackageImpl;", "Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;", "jClass", "Ljava/lang/Class;", "usageModuleName", "", "(Ljava/lang/Class;Ljava/lang/String;)V", "constructorDescriptors", "", "Lkotlin/reflect/jvm/internal/impl/descriptors/ConstructorDescriptor;", "getConstructorDescriptors", "()Ljava/util/Collection;", "data", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazyVal;", "Lkotlin/reflect/jvm/internal/KPackageImpl$Data;", "kotlin.jvm.PlatformType", "getJClass", "()Ljava/lang/Class;", "members", "Lkotlin/reflect/KCallable;", "getMembers", "methodOwner", "getMethodOwner", "scope", "Lkotlin/reflect/jvm/internal/impl/resolve/scopes/MemberScope;", "getScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getUsageModuleName", "()Ljava/lang/String;", "equals", "", "other", "", "getFunctions", "Lkotlin/reflect/jvm/internal/impl/descriptors/FunctionDescriptor;", "name", "Lkotlin/reflect/jvm/internal/impl/name/Name;", "getLocalProperty", "Lkotlin/reflect/jvm/internal/impl/descriptors/PropertyDescriptor;", "index", "", "getProperties", "hashCode", "toString", "Data", "kotlin-reflection"})
public final class KPackageImpl
extends KDeclarationContainerImpl {
    private final ReflectProperties.LazyVal<Data> data;
    @NotNull
    private final Class<?> jClass;
    @Nullable
    private final String usageModuleName;

    @Override
    @NotNull
    protected Class<?> getMethodOwner() {
        Class<?> clazz = this.data.invoke().getMultifileFacade();
        if (clazz == null) {
            clazz = this.getJClass();
        }
        return clazz;
    }

    private final MemberScope getScope() {
        return this.data.invoke().getScope();
    }

    @Override
    @NotNull
    public Collection<KCallable<?>> getMembers() {
        return this.data.invoke().getMembers();
    }

    @Override
    @NotNull
    public Collection<ConstructorDescriptor> getConstructorDescriptors() {
        return CollectionsKt.emptyList();
    }

    @Override
    @NotNull
    public Collection<PropertyDescriptor> getProperties(@NotNull Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return this.getScope().getContributedVariables(name, NoLookupLocation.FROM_REFLECTION);
    }

    @Override
    @NotNull
    public Collection<FunctionDescriptor> getFunctions(@NotNull Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return this.getScope().getContributedFunctions(name, NoLookupLocation.FROM_REFLECTION);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @Nullable
    public PropertyDescriptor getLocalProperty(int index) {
        PropertyDescriptor propertyDescriptor;
        Triple<JvmNameResolver, ProtoBuf.Package, JvmMetadataVersion> triple = this.data.invoke().getMetadata();
        if (triple != null) {
            void packageProto;
            Triple<JvmNameResolver, ProtoBuf.Package, JvmMetadataVersion> triple2 = triple;
            boolean bl = false;
            boolean bl2 = false;
            Triple<JvmNameResolver, ProtoBuf.Package, JvmMetadataVersion> $dstr$nameResolver$packageProto$metadataVersion = triple2;
            boolean bl3 = false;
            JvmNameResolver jvmNameResolver = $dstr$nameResolver$packageProto$metadataVersion.component1();
            ProtoBuf.Package package_ = $dstr$nameResolver$packageProto$metadataVersion.component2();
            JvmMetadataVersion metadataVersion = $dstr$nameResolver$packageProto$metadataVersion.component3();
            GeneratedMessageLite.ExtendableMessage extendableMessage = (GeneratedMessageLite.ExtendableMessage)packageProto;
            GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, List<ProtoBuf.Property>> generatedExtension = JvmProtoBuf.packageLocalVariable;
            Intrinsics.checkNotNullExpressionValue(generatedExtension, "JvmProtoBuf.packageLocalVariable");
            ProtoBuf.Property property = ProtoBufUtilKt.getExtensionOrNull(extendableMessage, generatedExtension, index);
            if (property != null) {
                void nameResolver;
                ProtoBuf.Property property2 = property;
                boolean bl4 = false;
                boolean bl5 = false;
                ProtoBuf.Property proto = property2;
                boolean bl6 = false;
                Class<?> clazz = this.getJClass();
                MessageLite messageLite = proto;
                NameResolver nameResolver2 = (NameResolver)nameResolver;
                ProtoBuf.TypeTable typeTable = packageProto.getTypeTable();
                Intrinsics.checkNotNullExpressionValue(typeTable, "packageProto.typeTable");
                propertyDescriptor = (PropertyDescriptor)UtilKt.deserializeToDescriptor(clazz, messageLite, nameResolver2, new TypeTable(typeTable), metadataVersion, getLocalProperty.1.1.1.INSTANCE);
            } else {
                propertyDescriptor = null;
            }
        } else {
            propertyDescriptor = null;
        }
        return propertyDescriptor;
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof KPackageImpl && Intrinsics.areEqual(this.getJClass(), ((KPackageImpl)other).getJClass());
    }

    public int hashCode() {
        return this.getJClass().hashCode();
    }

    @NotNull
    public String toString() {
        return "file class " + ReflectClassUtilKt.getClassId(this.getJClass()).asSingleFqName();
    }

    @Override
    @NotNull
    public Class<?> getJClass() {
        return this.jClass;
    }

    public KPackageImpl(@NotNull Class<?> jClass, @Nullable String usageModuleName) {
        Intrinsics.checkNotNullParameter(jClass, "jClass");
        this.jClass = jClass;
        this.usageModuleName = usageModuleName;
        ReflectProperties.LazyVal lazyVal = ReflectProperties.lazy((Function0)new Function0<Data>(this){
            final /* synthetic */ KPackageImpl this$0;

            public final Data invoke() {
                return this.this$0.new Data();
            }
            {
                this.this$0 = kPackageImpl;
                super(0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(lazyVal, "ReflectProperties.lazy { Data() }");
        this.data = lazyVal;
    }

    public /* synthetic */ KPackageImpl(Class clazz, String string, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            string = null;
        }
        this(clazz, string);
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003R\u001d\u0010\u0004\u001a\u0004\u0018\u00010\u00058BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R%\u0010\n\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f0\u000b8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000f\u0010\t\u001a\u0004\b\r\u0010\u000eR/\u0010\u0010\u001a\u0016\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u00118FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0015\u0010\u0016R!\u0010\u0019\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001a8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001d\u0010\u0018\u001a\u0004\b\u001b\u0010\u001cR\u001b\u0010\u001e\u001a\u00020\u001f8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\"\u0010\t\u001a\u0004\b \u0010!\u00a8\u0006#"}, d2={"Lkotlin/reflect/jvm/internal/KPackageImpl$Data;", "Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl$Data;", "Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;", "(Lkotlin/reflect/jvm/internal/KPackageImpl;)V", "kotlinClass", "Lkotlin/reflect/jvm/internal/impl/descriptors/runtime/components/ReflectKotlinClass;", "getKotlinClass", "()Lorg/jetbrains/kotlin/descriptors/runtime/components/ReflectKotlinClass;", "kotlinClass$delegate", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazySoftVal;", "members", "", "Lkotlin/reflect/jvm/internal/KCallableImpl;", "getMembers", "()Ljava/util/Collection;", "members$delegate", "metadata", "Lkotlin/Triple;", "Lkotlin/reflect/jvm/internal/impl/metadata/jvm/deserialization/JvmNameResolver;", "Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package;", "Lkotlin/reflect/jvm/internal/impl/metadata/jvm/deserialization/JvmMetadataVersion;", "getMetadata", "()Lkotlin/Triple;", "metadata$delegate", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazyVal;", "multifileFacade", "Ljava/lang/Class;", "getMultifileFacade", "()Ljava/lang/Class;", "multifileFacade$delegate", "scope", "Lkotlin/reflect/jvm/internal/impl/resolve/scopes/MemberScope;", "getScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "scope$delegate", "kotlin-reflection"})
    private final class Data
    extends KDeclarationContainerImpl.Data {
        static final /* synthetic */ KProperty[] $$delegatedProperties;
        private final ReflectProperties.LazySoftVal kotlinClass$delegate = ReflectProperties.lazySoft((Function0)new Function0<ReflectKotlinClass>(this){
            final /* synthetic */ Data this$0;

            @Nullable
            public final ReflectKotlinClass invoke() {
                return ReflectKotlinClass.Factory.create(this.this$0.KPackageImpl.this.getJClass());
            }
            {
                this.this$0 = data2;
                super(0);
            }
        });
        @NotNull
        private final ReflectProperties.LazySoftVal scope$delegate = ReflectProperties.lazySoft((Function0)new Function0<MemberScope>(this){
            final /* synthetic */ Data this$0;

            public final MemberScope invoke() {
                ReflectKotlinClass klass = Data.access$getKotlinClass$p(this.this$0);
                return klass != null ? this.this$0.getModuleData().getPackagePartScopeCache().getPackagePartScope(klass) : (MemberScope)MemberScope.Empty.INSTANCE;
            }
            {
                this.this$0 = data2;
                super(0);
            }
        });
        @Nullable
        private final ReflectProperties.LazyVal multifileFacade$delegate = ReflectProperties.lazy(new Function0<Class<?>>(this){
            final /* synthetic */ Data this$0;

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Nullable
            public final Class<?> invoke() {
                ReflectKotlinClass reflectKotlinClass = Data.access$getKotlinClass$p(this.this$0);
                Object object = reflectKotlinClass;
                if (reflectKotlinClass == null) return null;
                KotlinClassHeader kotlinClassHeader = ((ReflectKotlinClass)object).getClassHeader();
                object = kotlinClassHeader;
                if (kotlinClassHeader == null) return null;
                String string = ((KotlinClassHeader)object).getMultifileClassName();
                String facadeName = string;
                if (facadeName == null) return null;
                CharSequence charSequence = facadeName;
                boolean bl = false;
                if (charSequence.length() <= 0) return null;
                boolean bl2 = true;
                if (!bl2) return null;
                Class<?> clazz = this.this$0.KPackageImpl.this.getJClass().getClassLoader().loadClass(StringsKt.replace$default(facadeName, '/', '.', false, 4, null));
                return clazz;
            }
            {
                this.this$0 = data2;
                super(0);
            }
        });
        @Nullable
        private final ReflectProperties.LazyVal metadata$delegate = ReflectProperties.lazy((Function0)new Function0<Triple<? extends JvmNameResolver, ? extends ProtoBuf.Package, ? extends JvmMetadataVersion>>(this){
            final /* synthetic */ Data this$0;

            /*
             * WARNING - void declaration
             */
            @Nullable
            public final Triple<JvmNameResolver, ProtoBuf.Package, JvmMetadataVersion> invoke() {
                Triple<void, ProtoBuf.Package, JvmMetadataVersion> triple;
                Object object = Data.access$getKotlinClass$p(this.this$0);
                if (object != null && (object = ((ReflectKotlinClass)object).getClassHeader()) != null) {
                    Object object2 = object;
                    boolean bl = false;
                    boolean bl2 = false;
                    Object header = object2;
                    boolean bl3 = false;
                    String[] data2 = ((KotlinClassHeader)header).getData();
                    String[] strings = ((KotlinClassHeader)header).getStrings();
                    if (data2 != null && strings != null) {
                        void nameResolver;
                        Pair<JvmNameResolver, ProtoBuf.Package> pair = JvmProtoBufUtil.readPackageDataFrom(data2, strings);
                        JvmNameResolver jvmNameResolver = pair.component1();
                        ProtoBuf.Package proto = pair.component2();
                        triple = new Triple<void, ProtoBuf.Package, JvmMetadataVersion>(nameResolver, proto, ((KotlinClassHeader)header).getMetadataVersion());
                    } else {
                        triple = null;
                    }
                } else {
                    triple = null;
                }
                return triple;
            }
            {
                this.this$0 = data2;
                super(0);
            }
        });
        @NotNull
        private final ReflectProperties.LazySoftVal members$delegate = ReflectProperties.lazySoft(new Function0<Collection<? extends KCallableImpl<?>>>(this){
            final /* synthetic */ Data this$0;

            public final Collection<KCallableImpl<?>> invoke() {
                return this.this$0.KPackageImpl.this.getMembers(this.this$0.getScope(), KDeclarationContainerImpl.MemberBelonginess.DECLARED);
            }
            {
                this.this$0 = data2;
                super(0);
            }
        });

        static {
            $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "kotlinClass", "getKotlinClass()Lorg/jetbrains/kotlin/descriptors/runtime/components/ReflectKotlinClass;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "scope", "getScope()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "multifileFacade", "getMultifileFacade()Ljava/lang/Class;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "metadata", "getMetadata()Lkotlin/Triple;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "members", "getMembers()Ljava/util/Collection;"))};
        }

        private final ReflectKotlinClass getKotlinClass() {
            return (ReflectKotlinClass)this.kotlinClass$delegate.getValue(this, $$delegatedProperties[0]);
        }

        @NotNull
        public final MemberScope getScope() {
            return (MemberScope)this.scope$delegate.getValue(this, $$delegatedProperties[1]);
        }

        @Nullable
        public final Class<?> getMultifileFacade() {
            return (Class)this.multifileFacade$delegate.getValue(this, $$delegatedProperties[2]);
        }

        @Nullable
        public final Triple<JvmNameResolver, ProtoBuf.Package, JvmMetadataVersion> getMetadata() {
            return (Triple)this.metadata$delegate.getValue(this, $$delegatedProperties[3]);
        }

        @NotNull
        public final Collection<KCallableImpl<?>> getMembers() {
            return (Collection)this.members$delegate.getValue(this, $$delegatedProperties[4]);
        }

        public static final /* synthetic */ ReflectKotlinClass access$getKotlinClass$p(Data $this) {
            return $this.getKotlinClass();
        }
    }
}

