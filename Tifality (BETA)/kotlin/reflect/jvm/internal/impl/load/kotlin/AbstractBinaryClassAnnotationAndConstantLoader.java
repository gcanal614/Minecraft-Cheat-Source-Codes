/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.UnsignedTypes;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNames;
import kotlin.reflect.jvm.internal.impl.load.kotlin.AbstractBinaryClassAnnotationAndConstantLoader;
import kotlin.reflect.jvm.internal.impl.load.kotlin.AbstractBinaryClassAnnotationAndConstantLoader$WhenMappings;
import kotlin.reflect.jvm.internal.impl.load.kotlin.DeserializedDescriptorResolver;
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmPackagePartSource;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinder;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinderKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinarySourceElement;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MemberSignature;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoBufUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoTypeTableUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.ClassMapperLite;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmProtoBufUtil;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmClassName;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.AnnotatedCallableKind;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.AnnotationAndConstantLoader;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ProtoContainer;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNotNull;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractBinaryClassAnnotationAndConstantLoader<A, C>
implements AnnotationAndConstantLoader<A, C> {
    private final MemoizedFunctionToNotNull<KotlinJvmBinaryClass, Storage<A, C>> storage;
    private final KotlinClassFinder kotlinClassFinder;
    @NotNull
    private static final Set<ClassId> SPECIAL_ANNOTATIONS;
    public static final Companion Companion;

    @Nullable
    protected abstract C loadConstant(@NotNull String var1, @NotNull Object var2);

    @Nullable
    protected abstract C transformToUnsignedConstant(@NotNull C var1);

    @Nullable
    protected abstract KotlinJvmBinaryClass.AnnotationArgumentVisitor loadAnnotation(@NotNull ClassId var1, @NotNull SourceElement var2, @NotNull List<A> var3);

    @NotNull
    protected abstract A loadTypeAnnotation(@NotNull ProtoBuf.Annotation var1, @NotNull NameResolver var2);

    private final KotlinJvmBinaryClass.AnnotationArgumentVisitor loadAnnotationIfNotSpecial(ClassId annotationClassId, SourceElement source, List<A> result2) {
        if (SPECIAL_ANNOTATIONS.contains(annotationClassId)) {
            return null;
        }
        return this.loadAnnotation(annotationClassId, source, result2);
    }

    private final KotlinJvmBinaryClass toBinaryClass(ProtoContainer.Class $this$toBinaryClass) {
        SourceElement sourceElement = $this$toBinaryClass.getSource();
        if (!(sourceElement instanceof KotlinJvmBinarySourceElement)) {
            sourceElement = null;
        }
        KotlinJvmBinarySourceElement kotlinJvmBinarySourceElement = (KotlinJvmBinarySourceElement)sourceElement;
        return kotlinJvmBinarySourceElement != null ? kotlinJvmBinarySourceElement.getBinaryClass() : null;
    }

    @Nullable
    protected byte[] getCachedFileContent(@NotNull KotlinJvmBinaryClass kotlinClass2) {
        Intrinsics.checkNotNullParameter(kotlinClass2, "kotlinClass");
        return null;
    }

    @Override
    @NotNull
    public List<A> loadClassAnnotations(@NotNull ProtoContainer.Class container) {
        Intrinsics.checkNotNullParameter(container, "container");
        KotlinJvmBinaryClass kotlinJvmBinaryClass = this.toBinaryClass(container);
        if (kotlinJvmBinaryClass == null) {
            String string = "Class for loading annotations is not found: " + container.debugFqName();
            boolean bl = false;
            throw (Throwable)new IllegalStateException(string.toString());
        }
        KotlinJvmBinaryClass kotlinClass2 = kotlinJvmBinaryClass;
        ArrayList result2 = new ArrayList(1);
        kotlinClass2.loadClassAnnotations(new KotlinJvmBinaryClass.AnnotationVisitor(this, result2){
            final /* synthetic */ AbstractBinaryClassAnnotationAndConstantLoader this$0;
            final /* synthetic */ ArrayList $result;

            @Nullable
            public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(@NotNull ClassId classId, @NotNull SourceElement source) {
                Intrinsics.checkNotNullParameter(classId, "classId");
                Intrinsics.checkNotNullParameter(source, "source");
                return AbstractBinaryClassAnnotationAndConstantLoader.access$loadAnnotationIfNotSpecial(this.this$0, classId, source, this.$result);
            }

            public void visitEnd() {
            }
            {
                this.this$0 = this$0;
                this.$result = $captured_local_variable$1;
            }
        }, this.getCachedFileContent(kotlinClass2));
        return result2;
    }

    @Override
    @NotNull
    public List<A> loadCallableAnnotations(@NotNull ProtoContainer container, @NotNull MessageLite proto, @NotNull AnnotatedCallableKind kind) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter((Object)kind, "kind");
        if (kind == AnnotatedCallableKind.PROPERTY) {
            return this.loadPropertyAnnotations(container, (ProtoBuf.Property)proto, PropertyRelatedElement.PROPERTY);
        }
        MemberSignature memberSignature = AbstractBinaryClassAnnotationAndConstantLoader.getCallableSignature$default(this, proto, container.getNameResolver(), container.getTypeTable(), kind, false, 16, null);
        if (memberSignature == null) {
            return CollectionsKt.emptyList();
        }
        MemberSignature signature2 = memberSignature;
        return AbstractBinaryClassAnnotationAndConstantLoader.findClassAndLoadMemberAnnotations$default(this, container, signature2, false, false, null, false, 60, null);
    }

    @Override
    @NotNull
    public List<A> loadPropertyBackingFieldAnnotations(@NotNull ProtoContainer container, @NotNull ProtoBuf.Property proto) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        return this.loadPropertyAnnotations(container, proto, PropertyRelatedElement.BACKING_FIELD);
    }

    @Override
    @NotNull
    public List<A> loadPropertyDelegateFieldAnnotations(@NotNull ProtoContainer container, @NotNull ProtoBuf.Property proto) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        return this.loadPropertyAnnotations(container, proto, PropertyRelatedElement.DELEGATE_FIELD);
    }

    private final List<A> loadPropertyAnnotations(ProtoContainer container, ProtoBuf.Property proto, PropertyRelatedElement element) {
        Boolean bl = Flags.IS_CONST.get(proto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bl, "Flags.IS_CONST.get(proto.flags)");
        boolean isConst = bl;
        boolean isMovedFromInterfaceCompanion = JvmProtoBufUtil.isMovedFromInterfaceCompanion(proto);
        if (element == PropertyRelatedElement.PROPERTY) {
            MemberSignature memberSignature = AbstractBinaryClassAnnotationAndConstantLoader.getPropertySignature$default(this, proto, container.getNameResolver(), container.getTypeTable(), false, true, false, 40, null);
            if (memberSignature == null) {
                return CollectionsKt.emptyList();
            }
            MemberSignature syntheticFunctionSignature = memberSignature;
            return AbstractBinaryClassAnnotationAndConstantLoader.findClassAndLoadMemberAnnotations$default(this, container, syntheticFunctionSignature, true, false, isConst, isMovedFromInterfaceCompanion, 8, null);
        }
        MemberSignature memberSignature = AbstractBinaryClassAnnotationAndConstantLoader.getPropertySignature$default(this, proto, container.getNameResolver(), container.getTypeTable(), true, false, false, 48, null);
        if (memberSignature == null) {
            return CollectionsKt.emptyList();
        }
        MemberSignature fieldSignature = memberSignature;
        boolean isDelegated = StringsKt.contains$default((CharSequence)fieldSignature.getSignature$descriptors_jvm(), "$delegate", false, 2, null);
        if (isDelegated != (element == PropertyRelatedElement.DELEGATE_FIELD)) {
            return CollectionsKt.emptyList();
        }
        return this.findClassAndLoadMemberAnnotations(container, fieldSignature, true, true, isConst, isMovedFromInterfaceCompanion);
    }

    @Override
    @NotNull
    public List<A> loadEnumEntryAnnotations(@NotNull ProtoContainer container, @NotNull ProtoBuf.EnumEntry proto) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        String string = container.getNameResolver().getString(proto.getName());
        String string2 = ((ProtoContainer.Class)container).getClassId().asString();
        Intrinsics.checkNotNullExpressionValue(string2, "(container as ProtoConta\u2026Class).classId.asString()");
        MemberSignature signature2 = MemberSignature.Companion.fromFieldNameAndDesc(string, ClassMapperLite.mapClass(string2));
        return AbstractBinaryClassAnnotationAndConstantLoader.findClassAndLoadMemberAnnotations$default(this, container, signature2, false, false, null, false, 60, null);
    }

    private final List<A> findClassAndLoadMemberAnnotations(ProtoContainer container, MemberSignature signature2, boolean property, boolean field, Boolean isConst, boolean isMovedFromInterfaceCompanion) {
        KotlinJvmBinaryClass kotlinJvmBinaryClass = this.findClassWithAnnotationsAndInitializers(container, this.getSpecialCaseContainerClass(container, property, field, isConst, isMovedFromInterfaceCompanion));
        if (kotlinJvmBinaryClass == null) {
            boolean bl = false;
            return CollectionsKt.emptyList();
        }
        KotlinJvmBinaryClass kotlinClass2 = kotlinJvmBinaryClass;
        List<Object> list = ((Storage)this.storage.invoke(kotlinClass2)).getMemberAnnotations().get(signature2);
        if (list == null) {
            boolean bl = false;
            list = CollectionsKt.emptyList();
        }
        return list;
    }

    static /* synthetic */ List findClassAndLoadMemberAnnotations$default(AbstractBinaryClassAnnotationAndConstantLoader abstractBinaryClassAnnotationAndConstantLoader, ProtoContainer protoContainer, MemberSignature memberSignature, boolean bl, boolean bl2, Boolean bl3, boolean bl4, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: findClassAndLoadMemberAnnotations");
        }
        if ((n & 4) != 0) {
            bl = false;
        }
        if ((n & 8) != 0) {
            bl2 = false;
        }
        if ((n & 0x10) != 0) {
            bl3 = null;
        }
        if ((n & 0x20) != 0) {
            bl4 = false;
        }
        return abstractBinaryClassAnnotationAndConstantLoader.findClassAndLoadMemberAnnotations(protoContainer, memberSignature, bl, bl2, bl3, bl4);
    }

    @Override
    @NotNull
    public List<A> loadValueParameterAnnotations(@NotNull ProtoContainer container, @NotNull MessageLite callableProto, @NotNull AnnotatedCallableKind kind, int parameterIndex, @NotNull ProtoBuf.ValueParameter proto) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(callableProto, "callableProto");
        Intrinsics.checkNotNullParameter((Object)kind, "kind");
        Intrinsics.checkNotNullParameter(proto, "proto");
        MemberSignature methodSignature = AbstractBinaryClassAnnotationAndConstantLoader.getCallableSignature$default(this, callableProto, container.getNameResolver(), container.getTypeTable(), kind, false, 16, null);
        if (methodSignature != null) {
            int index = parameterIndex + this.computeJvmParameterIndexShift(container, callableProto);
            MemberSignature paramSignature = MemberSignature.Companion.fromMethodSignatureAndParameterIndex(methodSignature, index);
            return AbstractBinaryClassAnnotationAndConstantLoader.findClassAndLoadMemberAnnotations$default(this, container, paramSignature, false, false, null, false, 60, null);
        }
        boolean bl = false;
        return CollectionsKt.emptyList();
    }

    private final int computeJvmParameterIndexShift(ProtoContainer container, MessageLite message) {
        int n;
        MessageLite messageLite = message;
        if (messageLite instanceof ProtoBuf.Function) {
            n = ProtoTypeTableUtilKt.hasReceiver((ProtoBuf.Function)message) ? 1 : 0;
        } else if (messageLite instanceof ProtoBuf.Property) {
            n = ProtoTypeTableUtilKt.hasReceiver((ProtoBuf.Property)message) ? 1 : 0;
        } else if (messageLite instanceof ProtoBuf.Constructor) {
            ProtoContainer protoContainer = container;
            if (protoContainer == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.serialization.deserialization.ProtoContainer.Class");
            }
            n = ((ProtoContainer.Class)protoContainer).getKind() == ProtoBuf.Class.Kind.ENUM_CLASS ? 2 : (((ProtoContainer.Class)container).isInner() ? 1 : 0);
        } else {
            throw (Throwable)new UnsupportedOperationException("Unsupported message: " + message.getClass());
        }
        return n;
    }

    @Override
    @NotNull
    public List<A> loadExtensionReceiverParameterAnnotations(@NotNull ProtoContainer container, @NotNull MessageLite proto, @NotNull AnnotatedCallableKind kind) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter((Object)kind, "kind");
        MemberSignature methodSignature = AbstractBinaryClassAnnotationAndConstantLoader.getCallableSignature$default(this, proto, container.getNameResolver(), container.getTypeTable(), kind, false, 16, null);
        if (methodSignature != null) {
            MemberSignature paramSignature = MemberSignature.Companion.fromMethodSignatureAndParameterIndex(methodSignature, 0);
            return AbstractBinaryClassAnnotationAndConstantLoader.findClassAndLoadMemberAnnotations$default(this, container, paramSignature, false, false, null, false, 60, null);
        }
        return CollectionsKt.emptyList();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public List<A> loadTypeAnnotations(@NotNull ProtoBuf.Type proto, @NotNull NameResolver nameResolver) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        List<ProtoBuf.Annotation> list = proto.getExtension(JvmProtoBuf.typeAnnotation);
        Intrinsics.checkNotNullExpressionValue(list, "proto.getExtension(JvmProtoBuf.typeAnnotation)");
        Iterable $this$map$iv = list;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            ProtoBuf.Annotation annotation = (ProtoBuf.Annotation)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            void v1 = it;
            Intrinsics.checkNotNullExpressionValue(v1, "it");
            A a2 = this.loadTypeAnnotation((ProtoBuf.Annotation)v1, nameResolver);
            collection.add(a2);
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public List<A> loadTypeParameterAnnotations(@NotNull ProtoBuf.TypeParameter proto, @NotNull NameResolver nameResolver) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        List<ProtoBuf.Annotation> list = proto.getExtension(JvmProtoBuf.typeParameterAnnotation);
        Intrinsics.checkNotNullExpressionValue(list, "proto.getExtension(JvmPr\u2026.typeParameterAnnotation)");
        Iterable $this$map$iv = list;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            ProtoBuf.Annotation annotation = (ProtoBuf.Annotation)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            void v1 = it;
            Intrinsics.checkNotNullExpressionValue(v1, "it");
            A a2 = this.loadTypeAnnotation((ProtoBuf.Annotation)v1, nameResolver);
            collection.add(a2);
        }
        return (List)destination$iv$iv;
    }

    @Override
    @Nullable
    public C loadPropertyConstant(@NotNull ProtoContainer container, @NotNull ProtoBuf.Property proto, @NotNull KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        KotlinJvmBinaryClass specialCase = this.getSpecialCaseContainerClass(container, true, true, Flags.IS_CONST.get(proto.getFlags()), JvmProtoBufUtil.isMovedFromInterfaceCompanion(proto));
        KotlinJvmBinaryClass kotlinJvmBinaryClass = this.findClassWithAnnotationsAndInitializers(container, specialCase);
        if (kotlinJvmBinaryClass == null) {
            return null;
        }
        KotlinJvmBinaryClass kotlinClass2 = kotlinJvmBinaryClass;
        boolean requireHasFieldFlag = kotlinClass2.getClassHeader().getMetadataVersion().isAtLeast(DeserializedDescriptorResolver.Companion.getKOTLIN_1_3_RC_METADATA_VERSION$descriptors_jvm());
        MemberSignature memberSignature = this.getCallableSignature(proto, container.getNameResolver(), container.getTypeTable(), AnnotatedCallableKind.PROPERTY, requireHasFieldFlag);
        if (memberSignature == null) {
            return null;
        }
        MemberSignature signature2 = memberSignature;
        Object c = ((Storage)this.storage.invoke(kotlinClass2)).getPropertyConstants().get(signature2);
        if (c == null) {
            return null;
        }
        Object constant = c;
        return UnsignedTypes.isUnsignedType(expectedType) ? this.transformToUnsignedConstant(constant) : constant;
    }

    private final KotlinJvmBinaryClass findClassWithAnnotationsAndInitializers(ProtoContainer container, KotlinJvmBinaryClass specialCase) {
        return specialCase != null ? specialCase : (container instanceof ProtoContainer.Class ? this.toBinaryClass((ProtoContainer.Class)container) : null);
    }

    private final KotlinJvmBinaryClass getSpecialCaseContainerClass(ProtoContainer container, boolean property, boolean field, Boolean isConst, boolean isMovedFromInterfaceCompanion) {
        ProtoContainer.Class outerClass;
        if (property) {
            boolean bl = false;
            boolean bl2 = false;
            if (isConst == null) {
                boolean bl3 = false;
                String string = "isConst should not be null for property (container=" + container + ')';
                throw (Throwable)new IllegalStateException(string.toString());
            }
            if (container instanceof ProtoContainer.Class && ((ProtoContainer.Class)container).getKind() == ProtoBuf.Class.Kind.INTERFACE) {
                ClassId classId = ((ProtoContainer.Class)container).getClassId().createNestedClassId(Name.identifier("DefaultImpls"));
                Intrinsics.checkNotNullExpressionValue(classId, "container.classId.create\u2026EFAULT_IMPLS_CLASS_NAME))");
                return KotlinClassFinderKt.findKotlinClass(this.kotlinClassFinder, classId);
            }
            if (isConst.booleanValue() && container instanceof ProtoContainer.Package) {
                JvmClassName facadeClassName;
                SourceElement sourceElement = container.getSource();
                if (!(sourceElement instanceof JvmPackagePartSource)) {
                    sourceElement = null;
                }
                JvmPackagePartSource jvmPackagePartSource = (JvmPackagePartSource)sourceElement;
                JvmClassName jvmClassName = facadeClassName = jvmPackagePartSource != null ? jvmPackagePartSource.getFacadeClassName() : null;
                if (facadeClassName != null) {
                    String string = facadeClassName.getInternalName();
                    Intrinsics.checkNotNullExpressionValue(string, "facadeClassName.internalName");
                    ClassId classId = ClassId.topLevel(new FqName(StringsKt.replace$default(string, '/', '.', false, 4, null)));
                    Intrinsics.checkNotNullExpressionValue(classId, "ClassId.topLevel(FqName(\u2026lName.replace('/', '.')))");
                    return KotlinClassFinderKt.findKotlinClass(this.kotlinClassFinder, classId);
                }
            }
        }
        if (field && container instanceof ProtoContainer.Class && ((ProtoContainer.Class)container).getKind() == ProtoBuf.Class.Kind.COMPANION_OBJECT && (outerClass = ((ProtoContainer.Class)container).getOuterClass()) != null && (outerClass.getKind() == ProtoBuf.Class.Kind.CLASS || outerClass.getKind() == ProtoBuf.Class.Kind.ENUM_CLASS || isMovedFromInterfaceCompanion && (outerClass.getKind() == ProtoBuf.Class.Kind.INTERFACE || outerClass.getKind() == ProtoBuf.Class.Kind.ANNOTATION_CLASS))) {
            return this.toBinaryClass(outerClass);
        }
        if (container instanceof ProtoContainer.Package && container.getSource() instanceof JvmPackagePartSource) {
            SourceElement sourceElement = container.getSource();
            if (sourceElement == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.load.kotlin.JvmPackagePartSource");
            }
            JvmPackagePartSource jvmPackagePartSource = (JvmPackagePartSource)sourceElement;
            KotlinJvmBinaryClass kotlinJvmBinaryClass = jvmPackagePartSource.getKnownJvmBinaryClass();
            if (kotlinJvmBinaryClass == null) {
                kotlinJvmBinaryClass = KotlinClassFinderKt.findKotlinClass(this.kotlinClassFinder, jvmPackagePartSource.getClassId());
            }
            return kotlinJvmBinaryClass;
        }
        return null;
    }

    private final Storage<A, C> loadAnnotationsAndInitializers(KotlinJvmBinaryClass kotlinClass2) {
        HashMap memberAnnotations = new HashMap();
        HashMap propertyConstants = new HashMap();
        kotlinClass2.visitMembers(new KotlinJvmBinaryClass.MemberVisitor(this, memberAnnotations, propertyConstants){
            final /* synthetic */ AbstractBinaryClassAnnotationAndConstantLoader this$0;
            final /* synthetic */ HashMap $memberAnnotations;
            final /* synthetic */ HashMap $propertyConstants;

            @Nullable
            public KotlinJvmBinaryClass.MethodAnnotationVisitor visitMethod(@NotNull Name name, @NotNull String desc) {
                Intrinsics.checkNotNullParameter(name, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                String string = name.asString();
                Intrinsics.checkNotNullExpressionValue(string, "name.asString()");
                return new loadAnnotationsAndInitializers.AnnotationVisitorForMethod(this, MemberSignature.Companion.fromMethodNameAndDesc(string, desc));
            }

            @Nullable
            public KotlinJvmBinaryClass.AnnotationVisitor visitField(@NotNull Name name, @NotNull String desc, @Nullable Object initializer) {
                C constant;
                Intrinsics.checkNotNullParameter(name, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                String string = name.asString();
                Intrinsics.checkNotNullExpressionValue(string, "name.asString()");
                MemberSignature signature2 = MemberSignature.Companion.fromFieldNameAndDesc(string, desc);
                if (initializer != null && (constant = this.this$0.loadConstant(desc, initializer)) != null) {
                    ((Map)this.$propertyConstants).put(signature2, constant);
                }
                return new loadAnnotationsAndInitializers.MemberAnnotationVisitor(this, signature2);
            }
            {
                this.this$0 = this$0;
                this.$memberAnnotations = $captured_local_variable$1;
                this.$propertyConstants = $captured_local_variable$2;
            }
        }, this.getCachedFileContent(kotlinClass2));
        return new Storage(memberAnnotations, propertyConstants);
    }

    private final MemberSignature getPropertySignature(ProtoBuf.Property proto, NameResolver nameResolver, TypeTable typeTable, boolean field, boolean synthetic, boolean requireHasFieldFlagForField) {
        GeneratedMessageLite.ExtendableMessage extendableMessage = proto;
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, JvmProtoBuf.JvmPropertySignature> generatedExtension = JvmProtoBuf.propertySignature;
        Intrinsics.checkNotNullExpressionValue(generatedExtension, "propertySignature");
        JvmProtoBuf.JvmPropertySignature jvmPropertySignature = ProtoBufUtilKt.getExtensionOrNull(extendableMessage, generatedExtension);
        if (jvmPropertySignature == null) {
            return null;
        }
        JvmProtoBuf.JvmPropertySignature signature2 = jvmPropertySignature;
        if (field) {
            JvmMemberSignature.Field field2 = JvmProtoBufUtil.INSTANCE.getJvmFieldSignature(proto, nameResolver, typeTable, requireHasFieldFlagForField);
            if (field2 == null) {
                return null;
            }
            JvmMemberSignature.Field fieldSignature = field2;
            return MemberSignature.Companion.fromJvmMemberSignature(fieldSignature);
        }
        if (synthetic && signature2.hasSyntheticMethod()) {
            JvmProtoBuf.JvmMethodSignature jvmMethodSignature = signature2.getSyntheticMethod();
            Intrinsics.checkNotNullExpressionValue(jvmMethodSignature, "signature.syntheticMethod");
            return MemberSignature.Companion.fromMethod(nameResolver, jvmMethodSignature);
        }
        return null;
    }

    static /* synthetic */ MemberSignature getPropertySignature$default(AbstractBinaryClassAnnotationAndConstantLoader abstractBinaryClassAnnotationAndConstantLoader, ProtoBuf.Property property, NameResolver nameResolver, TypeTable typeTable, boolean bl, boolean bl2, boolean bl3, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getPropertySignature");
        }
        if ((n & 8) != 0) {
            bl = false;
        }
        if ((n & 0x10) != 0) {
            bl2 = false;
        }
        if ((n & 0x20) != 0) {
            bl3 = true;
        }
        return abstractBinaryClassAnnotationAndConstantLoader.getPropertySignature(property, nameResolver, typeTable, bl, bl2, bl3);
    }

    private final MemberSignature getCallableSignature(MessageLite proto, NameResolver nameResolver, TypeTable typeTable, AnnotatedCallableKind kind, boolean requireHasFieldFlagForField) {
        MemberSignature memberSignature;
        MessageLite messageLite = proto;
        if (messageLite instanceof ProtoBuf.Constructor) {
            JvmMemberSignature.Method method = JvmProtoBufUtil.INSTANCE.getJvmConstructorSignature((ProtoBuf.Constructor)proto, nameResolver, typeTable);
            if (method == null) {
                return null;
            }
            memberSignature = MemberSignature.Companion.fromJvmMemberSignature(method);
        } else if (messageLite instanceof ProtoBuf.Function) {
            JvmMemberSignature.Method method = JvmProtoBufUtil.INSTANCE.getJvmMethodSignature((ProtoBuf.Function)proto, nameResolver, typeTable);
            if (method == null) {
                return null;
            }
            memberSignature = MemberSignature.Companion.fromJvmMemberSignature(method);
        } else if (messageLite instanceof ProtoBuf.Property) {
            GeneratedMessageLite.ExtendableMessage extendableMessage = (GeneratedMessageLite.ExtendableMessage)proto;
            GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, JvmProtoBuf.JvmPropertySignature> generatedExtension = JvmProtoBuf.propertySignature;
            Intrinsics.checkNotNullExpressionValue(generatedExtension, "propertySignature");
            JvmProtoBuf.JvmPropertySignature jvmPropertySignature = ProtoBufUtilKt.getExtensionOrNull(extendableMessage, generatedExtension);
            if (jvmPropertySignature == null) {
                return null;
            }
            JvmProtoBuf.JvmPropertySignature signature2 = jvmPropertySignature;
            switch (AbstractBinaryClassAnnotationAndConstantLoader$WhenMappings.$EnumSwitchMapping$0[kind.ordinal()]) {
                case 1: {
                    if (signature2.hasGetter()) {
                        JvmProtoBuf.JvmMethodSignature jvmMethodSignature = signature2.getGetter();
                        Intrinsics.checkNotNullExpressionValue(jvmMethodSignature, "signature.getter");
                        memberSignature = MemberSignature.Companion.fromMethod(nameResolver, jvmMethodSignature);
                        break;
                    }
                    memberSignature = null;
                    break;
                }
                case 2: {
                    if (signature2.hasSetter()) {
                        JvmProtoBuf.JvmMethodSignature jvmMethodSignature = signature2.getSetter();
                        Intrinsics.checkNotNullExpressionValue(jvmMethodSignature, "signature.setter");
                        memberSignature = MemberSignature.Companion.fromMethod(nameResolver, jvmMethodSignature);
                        break;
                    }
                    memberSignature = null;
                    break;
                }
                case 3: {
                    memberSignature = this.getPropertySignature((ProtoBuf.Property)proto, nameResolver, typeTable, true, true, requireHasFieldFlagForField);
                    break;
                }
                default: {
                    memberSignature = null;
                    break;
                }
            }
        } else {
            memberSignature = null;
        }
        return memberSignature;
    }

    static /* synthetic */ MemberSignature getCallableSignature$default(AbstractBinaryClassAnnotationAndConstantLoader abstractBinaryClassAnnotationAndConstantLoader, MessageLite messageLite, NameResolver nameResolver, TypeTable typeTable, AnnotatedCallableKind annotatedCallableKind, boolean bl, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getCallableSignature");
        }
        if ((n & 0x10) != 0) {
            bl = false;
        }
        return abstractBinaryClassAnnotationAndConstantLoader.getCallableSignature(messageLite, nameResolver, typeTable, annotatedCallableKind, bl);
    }

    public AbstractBinaryClassAnnotationAndConstantLoader(@NotNull StorageManager storageManager, @NotNull KotlinClassFinder kotlinClassFinder) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(kotlinClassFinder, "kotlinClassFinder");
        this.kotlinClassFinder = kotlinClassFinder;
        this.storage = storageManager.createMemoizedFunction(new Function1<KotlinJvmBinaryClass, Storage<? extends A, ? extends C>>(this){
            final /* synthetic */ AbstractBinaryClassAnnotationAndConstantLoader this$0;

            @NotNull
            public final Storage<A, C> invoke(@NotNull KotlinJvmBinaryClass kotlinClass2) {
                Intrinsics.checkNotNullParameter(kotlinClass2, "kotlinClass");
                return AbstractBinaryClassAnnotationAndConstantLoader.access$loadAnnotationsAndInitializers(this.this$0, kotlinClass2);
            }
            {
                this.this$0 = abstractBinaryClassAnnotationAndConstantLoader;
                super(1);
            }
        });
    }

    /*
     * WARNING - void declaration
     */
    static {
        void var3_3;
        void $this$mapTo$iv$iv;
        Companion = new Companion(null);
        Iterable $this$map$iv = CollectionsKt.listOf(JvmAnnotationNames.METADATA_FQ_NAME, JvmAnnotationNames.JETBRAINS_NOT_NULL_ANNOTATION, JvmAnnotationNames.JETBRAINS_NULLABLE_ANNOTATION, new FqName("java.lang.annotation.Target"), new FqName("java.lang.annotation.Retention"), new FqName("java.lang.annotation.Documented"));
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void p1;
            FqName fqName2 = (FqName)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            ClassId classId = ClassId.topLevel((FqName)p1);
            collection.add(classId);
        }
        SPECIAL_ANNOTATIONS = CollectionsKt.toSet((List)var3_3);
    }

    public static final /* synthetic */ KotlinJvmBinaryClass.AnnotationArgumentVisitor access$loadAnnotationIfNotSpecial(AbstractBinaryClassAnnotationAndConstantLoader $this, ClassId annotationClassId, SourceElement source, List result2) {
        return $this.loadAnnotationIfNotSpecial(annotationClassId, source, result2);
    }

    public static final /* synthetic */ Storage access$loadAnnotationsAndInitializers(AbstractBinaryClassAnnotationAndConstantLoader $this, KotlinJvmBinaryClass kotlinClass2) {
        return $this.loadAnnotationsAndInitializers(kotlinClass2);
    }

    private static final class PropertyRelatedElement
    extends Enum<PropertyRelatedElement> {
        public static final /* enum */ PropertyRelatedElement PROPERTY;
        public static final /* enum */ PropertyRelatedElement BACKING_FIELD;
        public static final /* enum */ PropertyRelatedElement DELEGATE_FIELD;
        private static final /* synthetic */ PropertyRelatedElement[] $VALUES;

        static {
            PropertyRelatedElement[] propertyRelatedElementArray = new PropertyRelatedElement[3];
            PropertyRelatedElement[] propertyRelatedElementArray2 = propertyRelatedElementArray;
            propertyRelatedElementArray[0] = PROPERTY = new PropertyRelatedElement();
            propertyRelatedElementArray[1] = BACKING_FIELD = new PropertyRelatedElement();
            propertyRelatedElementArray[2] = DELEGATE_FIELD = new PropertyRelatedElement();
            $VALUES = propertyRelatedElementArray;
        }

        public static PropertyRelatedElement[] values() {
            return (PropertyRelatedElement[])$VALUES.clone();
        }

        public static PropertyRelatedElement valueOf(String string) {
            return Enum.valueOf(PropertyRelatedElement.class, string);
        }
    }

    private static final class Storage<A, C> {
        @NotNull
        private final Map<MemberSignature, List<A>> memberAnnotations;
        @NotNull
        private final Map<MemberSignature, C> propertyConstants;

        @NotNull
        public final Map<MemberSignature, List<A>> getMemberAnnotations() {
            return this.memberAnnotations;
        }

        @NotNull
        public final Map<MemberSignature, C> getPropertyConstants() {
            return this.propertyConstants;
        }

        public Storage(@NotNull Map<MemberSignature, ? extends List<? extends A>> memberAnnotations, @NotNull Map<MemberSignature, ? extends C> propertyConstants) {
            Intrinsics.checkNotNullParameter(memberAnnotations, "memberAnnotations");
            Intrinsics.checkNotNullParameter(propertyConstants, "propertyConstants");
            this.memberAnnotations = memberAnnotations;
            this.propertyConstants = propertyConstants;
        }
    }

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

