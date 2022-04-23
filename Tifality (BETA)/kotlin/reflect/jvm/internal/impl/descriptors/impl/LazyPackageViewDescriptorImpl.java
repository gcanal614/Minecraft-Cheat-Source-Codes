/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.DeclarationDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.SubpackagesScope;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.ChainedMemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.LazyScopeAdapter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LazyPackageViewDescriptorImpl
extends DeclarationDescriptorImpl
implements PackageViewDescriptor {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    @NotNull
    private final NotNullLazyValue fragments$delegate;
    @NotNull
    private final MemberScope memberScope;
    @NotNull
    private final ModuleDescriptorImpl module;
    @NotNull
    private final FqName fqName;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyPackageViewDescriptorImpl.class), "fragments", "getFragments()Ljava/util/List;"))};
    }

    @Override
    @NotNull
    public List<PackageFragmentDescriptor> getFragments() {
        return (List)StorageKt.getValue(this.fragments$delegate, (Object)this, $$delegatedProperties[0]);
    }

    @Override
    @NotNull
    public MemberScope getMemberScope() {
        return this.memberScope;
    }

    @Override
    @Nullable
    public PackageViewDescriptor getContainingDeclaration() {
        PackageViewDescriptor packageViewDescriptor;
        if (this.getFqName().isRoot()) {
            packageViewDescriptor = null;
        } else {
            ModuleDescriptorImpl moduleDescriptorImpl = this.getModule();
            FqName fqName2 = this.getFqName().parent();
            Intrinsics.checkNotNullExpressionValue(fqName2, "fqName.parent()");
            packageViewDescriptor = moduleDescriptorImpl.getPackage(fqName2);
        }
        return packageViewDescriptor;
    }

    public boolean equals(@Nullable Object other) {
        Object object = other;
        if (!(object instanceof PackageViewDescriptor)) {
            object = null;
        }
        PackageViewDescriptor packageViewDescriptor = (PackageViewDescriptor)object;
        if (packageViewDescriptor == null) {
            return false;
        }
        PackageViewDescriptor that = packageViewDescriptor;
        return Intrinsics.areEqual(this.getFqName(), that.getFqName()) && Intrinsics.areEqual(this.getModule(), that.getModule());
    }

    public int hashCode() {
        int result2 = this.getModule().hashCode();
        result2 = 31 * result2 + this.getFqName().hashCode();
        return result2;
    }

    @Override
    public <R, D> R accept(@NotNull DeclarationDescriptorVisitor<R, D> visitor2, D data2) {
        Intrinsics.checkNotNullParameter(visitor2, "visitor");
        return visitor2.visitPackageViewDescriptor(this, data2);
    }

    @Override
    @NotNull
    public ModuleDescriptorImpl getModule() {
        return this.module;
    }

    @Override
    @NotNull
    public FqName getFqName() {
        return this.fqName;
    }

    public LazyPackageViewDescriptorImpl(@NotNull ModuleDescriptorImpl module, @NotNull FqName fqName2, @NotNull StorageManager storageManager) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        super(Annotations.Companion.getEMPTY(), fqName2.shortNameOrSpecial());
        this.module = module;
        this.fqName = fqName2;
        this.fragments$delegate = storageManager.createLazyValue((Function0)new Function0<List<? extends PackageFragmentDescriptor>>(this){
            final /* synthetic */ LazyPackageViewDescriptorImpl this$0;

            @NotNull
            public final List<PackageFragmentDescriptor> invoke() {
                return this.this$0.getModule().getPackageFragmentProvider().getPackageFragments(this.this$0.getFqName());
            }
            {
                this.this$0 = lazyPackageViewDescriptorImpl;
                super(0);
            }
        });
        this.memberScope = new LazyScopeAdapter(storageManager, (Function0<? extends MemberScope>)new Function0<MemberScope>(this){
            final /* synthetic */ LazyPackageViewDescriptorImpl this$0;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final MemberScope invoke() {
                MemberScope memberScope2;
                if (this.this$0.getFragments().isEmpty()) {
                    memberScope2 = MemberScope.Empty.INSTANCE;
                } else {
                    void $this$mapTo$iv$iv;
                    Iterable $this$map$iv = this.this$0.getFragments();
                    boolean $i$f$map = false;
                    Iterable iterable = $this$map$iv;
                    Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    boolean $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        void it;
                        PackageFragmentDescriptor packageFragmentDescriptor = (PackageFragmentDescriptor)item$iv$iv;
                        Collection collection = destination$iv$iv;
                        boolean bl = false;
                        MemberScope memberScope3 = it.getMemberScope();
                        collection.add(memberScope3);
                    }
                    List<SubpackagesScope> scopes = CollectionsKt.plus((Collection)((List)destination$iv$iv), new SubpackagesScope(this.this$0.getModule(), this.this$0.getFqName()));
                    memberScope2 = ChainedMemberScope.Companion.create("package view scope for " + this.this$0.getFqName() + " in " + this.this$0.getModule().getName(), (Iterable<? extends MemberScope>)scopes);
                }
                return memberScope2;
            }
            {
                this.this$0 = lazyPackageViewDescriptorImpl;
                super(0);
            }
        });
    }

    @Override
    public boolean isEmpty() {
        return PackageViewDescriptor.DefaultImpls.isEmpty(this);
    }
}

