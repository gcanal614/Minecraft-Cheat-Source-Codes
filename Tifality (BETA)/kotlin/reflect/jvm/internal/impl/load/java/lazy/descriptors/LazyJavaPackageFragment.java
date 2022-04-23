/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PackageFragmentDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaAnnotationsKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.JvmPackageScope;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaPackageFragment$WhenMappings;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaPackage;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinderKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryPackageSourceElement;
import kotlin.reflect.jvm.internal.impl.load.kotlin.PackagePartProvider;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.KotlinClassHeader;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmClassName;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LazyJavaPackageFragment
extends PackageFragmentDescriptorImpl {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    private final LazyJavaResolverContext c;
    @NotNull
    private final NotNullLazyValue binaryClasses$delegate;
    private final JvmPackageScope scope;
    private final NotNullLazyValue<List<FqName>> subPackages;
    @NotNull
    private final Annotations annotations;
    private final NotNullLazyValue partToFacade$delegate;
    private final JavaPackage jPackage;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyJavaPackageFragment.class), "binaryClasses", "getBinaryClasses$descriptors_jvm()Ljava/util/Map;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyJavaPackageFragment.class), "partToFacade", "getPartToFacade()Ljava/util/HashMap;"))};
    }

    @NotNull
    public final Map<String, KotlinJvmBinaryClass> getBinaryClasses$descriptors_jvm() {
        return (Map)StorageKt.getValue(this.binaryClasses$delegate, (Object)this, $$delegatedProperties[0]);
    }

    @Override
    @NotNull
    public Annotations getAnnotations() {
        return this.annotations;
    }

    @NotNull
    public final List<FqName> getSubPackageFqNames$descriptors_jvm() {
        return (List)this.subPackages.invoke();
    }

    @Nullable
    public final ClassDescriptor findClassifierByJavaClass$descriptors_jvm(@NotNull JavaClass jClass) {
        Intrinsics.checkNotNullParameter(jClass, "jClass");
        return this.scope.getJavaScope$descriptors_jvm().findClassifierByJavaClass$descriptors_jvm(jClass);
    }

    @Override
    @NotNull
    public JvmPackageScope getMemberScope() {
        return this.scope;
    }

    @Override
    @NotNull
    public String toString() {
        return "Lazy Java package fragment: " + this.getFqName();
    }

    @Override
    @NotNull
    public SourceElement getSource() {
        return new KotlinJvmBinaryPackageSourceElement(this);
    }

    public LazyJavaPackageFragment(@NotNull LazyJavaResolverContext outerContext, @NotNull JavaPackage jPackage) {
        Intrinsics.checkNotNullParameter(outerContext, "outerContext");
        Intrinsics.checkNotNullParameter(jPackage, "jPackage");
        super(outerContext.getModule(), jPackage.getFqName());
        this.jPackage = jPackage;
        this.c = ContextKt.childForClassOrPackage$default(outerContext, this, null, 0, 6, null);
        this.binaryClasses$delegate = this.c.getStorageManager().createLazyValue((Function0)new Function0<Map<String, ? extends KotlinJvmBinaryClass>>(this){
            final /* synthetic */ LazyJavaPackageFragment this$0;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final Map<String, KotlinJvmBinaryClass> invoke() {
                void $this$mapNotNullTo$iv$iv;
                PackagePartProvider packagePartProvider = LazyJavaPackageFragment.access$getC$p(this.this$0).getComponents().getPackagePartProvider();
                String string = this.this$0.getFqName().asString();
                Intrinsics.checkNotNullExpressionValue(string, "fqName.asString()");
                Iterable $this$mapNotNull$iv = packagePartProvider.findPackageParts(string);
                boolean $i$f$mapNotNull = false;
                Iterable iterable = $this$mapNotNull$iv;
                Collection destination$iv$iv = new ArrayList<E>();
                boolean $i$f$mapNotNullTo = false;
                void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                boolean $i$f$forEach = false;
                Iterator<T> iterator2 = $this$forEach$iv$iv$iv.iterator();
                while (iterator2.hasNext()) {
                    Pair<String, KotlinJvmBinaryClass> pair;
                    ClassId classId;
                    T element$iv$iv$iv;
                    T element$iv$iv = element$iv$iv$iv = iterator2.next();
                    boolean bl = false;
                    String partName = (String)element$iv$iv;
                    boolean bl2 = false;
                    JvmClassName jvmClassName = JvmClassName.byInternalName(partName);
                    Intrinsics.checkNotNullExpressionValue(jvmClassName, "JvmClassName.byInternalName(partName)");
                    Intrinsics.checkNotNullExpressionValue(ClassId.topLevel(jvmClassName.getFqNameForTopLevelClassMaybeWithDollars()), "ClassId.topLevel(JvmClas\u2026velClassMaybeWithDollars)");
                    if (KotlinClassFinderKt.findKotlinClass(LazyJavaPackageFragment.access$getC$p(this.this$0).getComponents().getKotlinClassFinder(), classId) != null) {
                        KotlinJvmBinaryClass kotlinJvmBinaryClass;
                        boolean bl3 = false;
                        boolean bl4 = false;
                        KotlinJvmBinaryClass it = kotlinJvmBinaryClass;
                        boolean bl5 = false;
                        pair = TuplesKt.to(partName, it);
                    } else {
                        pair = null;
                    }
                    if (pair == null) continue;
                    Pair<String, KotlinJvmBinaryClass> pair2 = pair;
                    boolean bl6 = false;
                    boolean bl7 = false;
                    Pair<String, KotlinJvmBinaryClass> it$iv$iv = pair2;
                    boolean bl8 = false;
                    destination$iv$iv.add(it$iv$iv);
                }
                return MapsKt.toMap((List)destination$iv$iv);
            }
            {
                this.this$0 = lazyJavaPackageFragment;
                super(0);
            }
        });
        this.scope = new JvmPackageScope(this.c, this.jPackage, this);
        boolean bl = false;
        this.subPackages = this.c.getStorageManager().createRecursionTolerantLazyValue((Function0)new Function0<List<? extends FqName>>(this){
            final /* synthetic */ LazyJavaPackageFragment this$0;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final List<FqName> invoke() {
                void $this$mapTo$iv$iv;
                Iterable $this$map$iv = LazyJavaPackageFragment.access$getJPackage$p(this.this$0).getSubPackages();
                boolean $i$f$map = false;
                Iterable iterable = $this$map$iv;
                Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                Iterator<T> iterator2 = $this$mapTo$iv$iv.iterator();
                while (iterator2.hasNext()) {
                    void receiver;
                    T item$iv$iv;
                    T t = item$iv$iv = iterator2.next();
                    Collection collection = destination$iv$iv;
                    boolean bl = false;
                    FqName fqName2 = ((JavaPackage)receiver).getFqName();
                    collection.add(fqName2);
                }
                return (List)destination$iv$iv;
            }
            {
                this.this$0 = lazyJavaPackageFragment;
                super(0);
            }
        }, CollectionsKt.emptyList());
        this.annotations = this.c.getComponents().getAnnotationTypeQualifierResolver().getDisabled() ? Annotations.Companion.getEMPTY() : LazyJavaAnnotationsKt.resolveAnnotations(this.c, this.jPackage);
        this.partToFacade$delegate = this.c.getStorageManager().createLazyValue((Function0)new Function0<HashMap<JvmClassName, JvmClassName>>(this){
            final /* synthetic */ LazyJavaPackageFragment this$0;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final HashMap<JvmClassName, JvmClassName> invoke() {
                void var1_3;
                boolean bl = false;
                HashMap<K, V> result2 = new HashMap<K, V>();
                Object object = this.this$0.getBinaryClasses$descriptors_jvm();
                boolean bl2 = false;
                Iterator<Map.Entry<String, KotlinJvmBinaryClass>> iterator2 = object.entrySet().iterator();
                while (iterator2.hasNext()) {
                    void partInternalName;
                    Map.Entry<String, KotlinJvmBinaryClass> entry;
                    Map.Entry<String, KotlinJvmBinaryClass> entry2 = entry = iterator2.next();
                    boolean bl3 = false;
                    object = entry2.getKey();
                    entry2 = entry;
                    bl3 = false;
                    KotlinJvmBinaryClass kotlinClass2 = entry2.getValue();
                    Intrinsics.checkNotNullExpressionValue(JvmClassName.byInternalName((String)partInternalName), "JvmClassName.byInternalName(partInternalName)");
                    KotlinClassHeader header = kotlinClass2.getClassHeader();
                    switch (LazyJavaPackageFragment$WhenMappings.$EnumSwitchMapping$0[header.getKind().ordinal()]) {
                        case 1: {
                            JvmClassName partName;
                            Map map2 = result2;
                            String string = header.getMultifileClassName();
                            if (string == null) {
                                break;
                            }
                            JvmClassName jvmClassName = JvmClassName.byInternalName(string);
                            Intrinsics.checkNotNullExpressionValue(jvmClassName, "JvmClassName.byInternalN\u2026: continue@kotlinClasses)");
                            map2.put(partName, jvmClassName);
                            break;
                        }
                        case 2: {
                            JvmClassName partName;
                            ((Map)result2).put(partName, partName);
                            break;
                        }
                    }
                }
                return var1_3;
            }
            {
                this.this$0 = lazyJavaPackageFragment;
                super(0);
            }
        });
    }

    public static final /* synthetic */ LazyJavaResolverContext access$getC$p(LazyJavaPackageFragment $this) {
        return $this.c;
    }

    public static final /* synthetic */ JavaPackage access$getJPackage$p(LazyJavaPackageFragment $this) {
        return $this.jPackage;
    }
}

