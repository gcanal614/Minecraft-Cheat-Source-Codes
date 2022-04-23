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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.components.DescriptorResolverUtils;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.DeclaredMemberIndex;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaField;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMember;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMethod;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ClassDeclaredMemberIndex
implements DeclaredMemberIndex {
    private final Function1<JavaMethod, Boolean> methodFilter;
    private final Map<Name, List<JavaMethod>> methods;
    private final Map<Name, JavaField> fields;
    @NotNull
    private final JavaClass jClass;
    private final Function1<JavaMember, Boolean> memberFilter;

    @Override
    @NotNull
    public Collection<JavaMethod> findMethodsByName(@NotNull Name name) {
        Collection collection;
        Intrinsics.checkNotNullParameter(name, "name");
        List<JavaMethod> list = this.methods.get(name);
        if (list != null) {
            collection = list;
        } else {
            boolean bl = false;
            collection = CollectionsKt.emptyList();
        }
        return collection;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public Set<Name> getMethodNames() {
        void var2_3;
        void $this$mapTo$iv;
        Sequence<JavaMethod> sequence = SequencesKt.filter(CollectionsKt.asSequence((Iterable)this.jClass.getMethods()), this.methodFilter);
        boolean bl = false;
        Collection destination$iv = new LinkedHashSet();
        boolean $i$f$mapTo = false;
        Iterator iterator2 = $this$mapTo$iv.iterator();
        while (iterator2.hasNext()) {
            void receiver;
            Object item$iv;
            Object t = item$iv = iterator2.next();
            Collection collection = destination$iv;
            boolean bl2 = false;
            Name name = ((JavaMethod)receiver).getName();
            collection.add(name);
        }
        return (Set)var2_3;
    }

    @Override
    @Nullable
    public JavaField findFieldByName(@NotNull Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return this.fields.get(name);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public Set<Name> getFieldNames() {
        void var2_3;
        void $this$mapTo$iv;
        Sequence<JavaMember> sequence = SequencesKt.filter(CollectionsKt.asSequence((Iterable)this.jClass.getFields()), this.memberFilter);
        boolean bl = false;
        Collection destination$iv = new LinkedHashSet();
        boolean $i$f$mapTo = false;
        Iterator iterator2 = $this$mapTo$iv.iterator();
        while (iterator2.hasNext()) {
            void receiver;
            Object item$iv;
            Object t = item$iv = iterator2.next();
            Collection collection = destination$iv;
            boolean bl2 = false;
            Name name = ((JavaField)receiver).getName();
            collection.add(name);
        }
        return (Set)var2_3;
    }

    /*
     * WARNING - void declaration
     */
    public ClassDeclaredMemberIndex(@NotNull JavaClass jClass, @NotNull Function1<? super JavaMember, Boolean> memberFilter) {
        void $this$associateByTo$iv$iv;
        void $this$associateBy$iv;
        Map map2;
        Object list$iv$iv;
        JavaMethod m;
        void $this$groupByTo$iv$iv;
        Sequence<JavaMember> $this$groupBy$iv;
        Intrinsics.checkNotNullParameter(jClass, "jClass");
        Intrinsics.checkNotNullParameter(memberFilter, "memberFilter");
        this.jClass = jClass;
        this.memberFilter = memberFilter;
        this.methodFilter = new Function1<JavaMethod, Boolean>(this){
            final /* synthetic */ ClassDeclaredMemberIndex this$0;

            public final boolean invoke(@NotNull JavaMethod m) {
                Intrinsics.checkNotNullParameter(m, "m");
                return (Boolean)ClassDeclaredMemberIndex.access$getMemberFilter$p(this.this$0).invoke(m) != false && !DescriptorResolverUtils.isObjectMethodInInterface(m);
            }
            {
                this.this$0 = classDeclaredMemberIndex;
                super(1);
            }
        };
        Sequence<JavaMethod> sequence = SequencesKt.filter(CollectionsKt.asSequence((Iterable)this.jClass.getMethods()), this.methodFilter);
        ClassDeclaredMemberIndex classDeclaredMemberIndex = this;
        boolean $i$f$groupBy = false;
        void var5_6 = $this$groupBy$iv;
        Map destination$iv$iv = new LinkedHashMap();
        boolean $i$f$groupByTo = false;
        for (Object element$iv$iv : $this$groupByTo$iv$iv) {
            Object object;
            m = (JavaMethod)element$iv$iv;
            boolean bl = false;
            Name key$iv$iv = m.getName();
            Map $this$getOrPut$iv$iv$iv = destination$iv$iv;
            boolean $i$f$getOrPut = false;
            Object value$iv$iv$iv = $this$getOrPut$iv$iv$iv.get(key$iv$iv);
            if (value$iv$iv$iv == null) {
                boolean bl2 = false;
                List answer$iv$iv$iv = new ArrayList();
                $this$getOrPut$iv$iv$iv.put(key$iv$iv, answer$iv$iv$iv);
                object = answer$iv$iv$iv;
            } else {
                object = value$iv$iv$iv;
            }
            list$iv$iv = (List)object;
            list$iv$iv.add(element$iv$iv);
        }
        classDeclaredMemberIndex.methods = map2 = destination$iv$iv;
        $this$groupBy$iv = SequencesKt.filter(CollectionsKt.asSequence((Iterable)this.jClass.getFields()), this.memberFilter);
        classDeclaredMemberIndex = this;
        boolean $i$f$associateBy = false;
        $this$groupByTo$iv$iv = $this$associateBy$iv;
        destination$iv$iv = new LinkedHashMap();
        boolean $i$f$associateByTo = false;
        for (Object element$iv$iv : $this$associateByTo$iv$iv) {
            list$iv$iv = (JavaField)element$iv$iv;
            map2 = destination$iv$iv;
            boolean bl = false;
            Name name = m.getName();
            map2.put(name, element$iv$iv);
        }
        classDeclaredMemberIndex.fields = map2 = destination$iv$iv;
    }

    public static final /* synthetic */ Function1 access$getMemberFilter$p(ClassDeclaredMemberIndex $this) {
        return $this.memberFilter;
    }
}

