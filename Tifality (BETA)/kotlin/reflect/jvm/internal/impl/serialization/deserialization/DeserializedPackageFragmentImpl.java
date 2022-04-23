/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolverImpl;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassDeserializer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationComponents;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializedPackageFragment;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ProtoBasedClassDataFinder;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedContainerSource;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedPackageMemberScope;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class DeserializedPackageFragmentImpl
extends DeserializedPackageFragment {
    @NotNull
    private final NameResolverImpl nameResolver;
    @NotNull
    private final ProtoBasedClassDataFinder classDataFinder;
    private ProtoBuf.PackageFragment _proto;
    private MemberScope _memberScope;
    private final BinaryVersion metadataVersion;
    private final DeserializedContainerSource containerSource;

    @Override
    @NotNull
    public ProtoBasedClassDataFinder getClassDataFinder() {
        return this.classDataFinder;
    }

    @Override
    public void initialize(@NotNull DeserializationComponents components) {
        Intrinsics.checkNotNullParameter(components, "components");
        ProtoBuf.PackageFragment packageFragment = this._proto;
        if (packageFragment == null) {
            String string = "Repeated call to DeserializedPackageFragmentImpl::initialize";
            boolean bl = false;
            throw (Throwable)new IllegalStateException(string.toString());
        }
        ProtoBuf.PackageFragment proto = packageFragment;
        this._proto = null;
        PackageFragmentDescriptor packageFragmentDescriptor = this;
        ProtoBuf.Package package_ = proto.getPackage();
        Intrinsics.checkNotNullExpressionValue(package_, "proto.`package`");
        this._memberScope = new DeserializedPackageMemberScope(packageFragmentDescriptor, package_, this.nameResolver, this.metadataVersion, this.containerSource, components, (Function0<? extends Collection<Name>>)new Function0<Collection<? extends Name>>(this){
            final /* synthetic */ DeserializedPackageFragmentImpl this$0;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final Collection<Name> invoke() {
                void $this$mapTo$iv$iv;
                ClassId classId;
                Iterable $this$filterTo$iv$iv;
                Iterable $this$filter$iv = this.this$0.getClassDataFinder().getAllClassIds();
                boolean $i$f$filter = false;
                Iterable iterable = $this$filter$iv;
                Collection destination$iv$iv = new ArrayList<E>();
                boolean $i$f$filterTo = false;
                for (T element$iv$iv : $this$filterTo$iv$iv) {
                    classId = (ClassId)element$iv$iv;
                    boolean bl = false;
                    if (!(!classId.isNestedClass() && !ClassDeserializer.Companion.getBLACK_LIST().contains(classId))) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                Iterable $this$map$iv = (List)destination$iv$iv;
                boolean $i$f$map = false;
                $this$filterTo$iv$iv = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    void it;
                    classId = (ClassId)item$iv$iv;
                    Collection collection = destination$iv$iv;
                    boolean bl = false;
                    Name name = it.getShortClassName();
                    collection.add(name);
                }
                return (List)destination$iv$iv;
            }
            {
                this.this$0 = deserializedPackageFragmentImpl;
                super(0);
            }
        });
    }

    @Override
    @NotNull
    public MemberScope getMemberScope() {
        MemberScope memberScope2 = this._memberScope;
        if (memberScope2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("_memberScope");
        }
        return memberScope2;
    }

    public DeserializedPackageFragmentImpl(@NotNull FqName fqName2, @NotNull StorageManager storageManager, @NotNull ModuleDescriptor module, @NotNull ProtoBuf.PackageFragment proto, @NotNull BinaryVersion metadataVersion, @Nullable DeserializedContainerSource containerSource) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        super(fqName2, storageManager, module);
        this.metadataVersion = metadataVersion;
        this.containerSource = containerSource;
        ProtoBuf.StringTable stringTable = proto.getStrings();
        Intrinsics.checkNotNullExpressionValue(stringTable, "proto.strings");
        ProtoBuf.QualifiedNameTable qualifiedNameTable = proto.getQualifiedNames();
        Intrinsics.checkNotNullExpressionValue(qualifiedNameTable, "proto.qualifiedNames");
        this.nameResolver = new NameResolverImpl(stringTable, qualifiedNameTable);
        this.classDataFinder = new ProtoBasedClassDataFinder(proto, this.nameResolver, this.metadataVersion, (Function1<? super ClassId, ? extends SourceElement>)new Function1<ClassId, SourceElement>(this){
            final /* synthetic */ DeserializedPackageFragmentImpl this$0;

            @NotNull
            public final SourceElement invoke(@NotNull ClassId it) {
                SourceElement sourceElement;
                Intrinsics.checkNotNullParameter(it, "it");
                DeserializedContainerSource deserializedContainerSource = DeserializedPackageFragmentImpl.access$getContainerSource$p(this.this$0);
                if (deserializedContainerSource != null) {
                    sourceElement = deserializedContainerSource;
                } else {
                    SourceElement sourceElement2 = SourceElement.NO_SOURCE;
                    sourceElement = sourceElement2;
                    Intrinsics.checkNotNullExpressionValue(sourceElement2, "SourceElement.NO_SOURCE");
                }
                return sourceElement;
            }
            {
                this.this$0 = deserializedPackageFragmentImpl;
                super(1);
            }
        });
        this._proto = proto;
    }

    public static final /* synthetic */ DeserializedContainerSource access$getContainerSource$p(DeserializedPackageFragmentImpl $this) {
        return $this.containerSource;
    }
}

