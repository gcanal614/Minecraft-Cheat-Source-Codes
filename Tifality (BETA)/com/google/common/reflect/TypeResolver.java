/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeVisitor;
import com.google.common.reflect.Types;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

@Beta
public final class TypeResolver {
    private final TypeTable typeTable;

    public TypeResolver() {
        this.typeTable = new TypeTable();
    }

    private TypeResolver(TypeTable typeTable) {
        this.typeTable = typeTable;
    }

    static TypeResolver accordingTo(Type type2) {
        return new TypeResolver().where(TypeMappingIntrospector.getTypeMappings(type2));
    }

    public TypeResolver where(Type formal, Type actual) {
        HashMap<TypeVariableKey, Type> mappings = Maps.newHashMap();
        TypeResolver.populateTypeMappings(mappings, Preconditions.checkNotNull(formal), Preconditions.checkNotNull(actual));
        return this.where(mappings);
    }

    TypeResolver where(Map<TypeVariableKey, ? extends Type> mappings) {
        return new TypeResolver(this.typeTable.where(mappings));
    }

    private static void populateTypeMappings(final Map<TypeVariableKey, Type> mappings, Type from, final Type to) {
        if (from.equals(to)) {
            return;
        }
        new TypeVisitor(){

            @Override
            void visitTypeVariable(TypeVariable<?> typeVariable) {
                mappings.put(new TypeVariableKey(typeVariable), to);
            }

            @Override
            void visitWildcardType(WildcardType fromWildcardType) {
                int i;
                WildcardType toWildcardType = (WildcardType)TypeResolver.expectArgument(WildcardType.class, to);
                Type[] fromUpperBounds = fromWildcardType.getUpperBounds();
                Type[] toUpperBounds = toWildcardType.getUpperBounds();
                Type[] fromLowerBounds = fromWildcardType.getLowerBounds();
                Type[] toLowerBounds = toWildcardType.getLowerBounds();
                Preconditions.checkArgument(fromUpperBounds.length == toUpperBounds.length && fromLowerBounds.length == toLowerBounds.length, "Incompatible type: %s vs. %s", fromWildcardType, to);
                for (i = 0; i < fromUpperBounds.length; ++i) {
                    TypeResolver.populateTypeMappings(mappings, fromUpperBounds[i], toUpperBounds[i]);
                }
                for (i = 0; i < fromLowerBounds.length; ++i) {
                    TypeResolver.populateTypeMappings(mappings, fromLowerBounds[i], toLowerBounds[i]);
                }
            }

            @Override
            void visitParameterizedType(ParameterizedType fromParameterizedType) {
                ParameterizedType toParameterizedType = (ParameterizedType)TypeResolver.expectArgument(ParameterizedType.class, to);
                Preconditions.checkArgument(fromParameterizedType.getRawType().equals(toParameterizedType.getRawType()), "Inconsistent raw type: %s vs. %s", fromParameterizedType, to);
                Type[] fromArgs = fromParameterizedType.getActualTypeArguments();
                Type[] toArgs = toParameterizedType.getActualTypeArguments();
                Preconditions.checkArgument(fromArgs.length == toArgs.length, "%s not compatible with %s", fromParameterizedType, toParameterizedType);
                for (int i = 0; i < fromArgs.length; ++i) {
                    TypeResolver.populateTypeMappings(mappings, fromArgs[i], toArgs[i]);
                }
            }

            @Override
            void visitGenericArrayType(GenericArrayType fromArrayType) {
                Type componentType = Types.getComponentType(to);
                Preconditions.checkArgument(componentType != null, "%s is not an array type.", to);
                TypeResolver.populateTypeMappings(mappings, fromArrayType.getGenericComponentType(), componentType);
            }

            @Override
            void visitClass(Class<?> fromClass) {
                throw new IllegalArgumentException("No type mapping from " + fromClass);
            }
        }.visit(from);
    }

    public Type resolveType(Type type2) {
        Preconditions.checkNotNull(type2);
        if (type2 instanceof TypeVariable) {
            return this.typeTable.resolve((TypeVariable)type2);
        }
        if (type2 instanceof ParameterizedType) {
            return this.resolveParameterizedType((ParameterizedType)type2);
        }
        if (type2 instanceof GenericArrayType) {
            return this.resolveGenericArrayType((GenericArrayType)type2);
        }
        if (type2 instanceof WildcardType) {
            return this.resolveWildcardType((WildcardType)type2);
        }
        return type2;
    }

    private Type[] resolveTypes(Type[] types) {
        Type[] result2 = new Type[types.length];
        for (int i = 0; i < types.length; ++i) {
            result2[i] = this.resolveType(types[i]);
        }
        return result2;
    }

    private WildcardType resolveWildcardType(WildcardType type2) {
        Type[] lowerBounds = type2.getLowerBounds();
        Type[] upperBounds2 = type2.getUpperBounds();
        return new Types.WildcardTypeImpl(this.resolveTypes(lowerBounds), this.resolveTypes(upperBounds2));
    }

    private Type resolveGenericArrayType(GenericArrayType type2) {
        Type componentType = type2.getGenericComponentType();
        Type resolvedComponentType = this.resolveType(componentType);
        return Types.newArrayType(resolvedComponentType);
    }

    private ParameterizedType resolveParameterizedType(ParameterizedType type2) {
        Type owner = type2.getOwnerType();
        Type resolvedOwner = owner == null ? null : this.resolveType(owner);
        Type resolvedRawType = this.resolveType(type2.getRawType());
        Type[] args2 = type2.getActualTypeArguments();
        Type[] resolvedArgs = this.resolveTypes(args2);
        return Types.newParameterizedTypeWithOwner(resolvedOwner, (Class)resolvedRawType, resolvedArgs);
    }

    private static <T> T expectArgument(Class<T> type2, Object arg) {
        try {
            return type2.cast(arg);
        }
        catch (ClassCastException e) {
            throw new IllegalArgumentException(arg + " is not a " + type2.getSimpleName());
        }
    }

    static final class TypeVariableKey {
        private final TypeVariable<?> var;

        TypeVariableKey(TypeVariable<?> var) {
            this.var = Preconditions.checkNotNull(var);
        }

        public int hashCode() {
            return Objects.hashCode(this.var.getGenericDeclaration(), this.var.getName());
        }

        public boolean equals(Object obj) {
            if (obj instanceof TypeVariableKey) {
                TypeVariableKey that = (TypeVariableKey)obj;
                return this.equalsTypeVariable(that.var);
            }
            return false;
        }

        public String toString() {
            return this.var.toString();
        }

        static Object forLookup(Type t) {
            if (t instanceof TypeVariable) {
                return new TypeVariableKey((TypeVariable)t);
            }
            return null;
        }

        boolean equalsType(Type type2) {
            if (type2 instanceof TypeVariable) {
                return this.equalsTypeVariable((TypeVariable)type2);
            }
            return false;
        }

        private boolean equalsTypeVariable(TypeVariable<?> that) {
            return this.var.getGenericDeclaration().equals(that.getGenericDeclaration()) && this.var.getName().equals(that.getName());
        }
    }

    private static final class WildcardCapturer {
        private final AtomicInteger id = new AtomicInteger();

        private WildcardCapturer() {
        }

        Type capture(Type type2) {
            Preconditions.checkNotNull(type2);
            if (type2 instanceof Class) {
                return type2;
            }
            if (type2 instanceof TypeVariable) {
                return type2;
            }
            if (type2 instanceof GenericArrayType) {
                GenericArrayType arrayType = (GenericArrayType)type2;
                return Types.newArrayType(this.capture(arrayType.getGenericComponentType()));
            }
            if (type2 instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType)type2;
                return Types.newParameterizedTypeWithOwner(this.captureNullable(parameterizedType.getOwnerType()), (Class)parameterizedType.getRawType(), this.capture(parameterizedType.getActualTypeArguments()));
            }
            if (type2 instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType)type2;
                Type[] lowerBounds = wildcardType.getLowerBounds();
                if (lowerBounds.length == 0) {
                    Object[] upperBounds2 = wildcardType.getUpperBounds();
                    String name = "capture#" + this.id.incrementAndGet() + "-of ? extends " + Joiner.on('&').join(upperBounds2);
                    return Types.newArtificialTypeVariable(WildcardCapturer.class, name, wildcardType.getUpperBounds());
                }
                return type2;
            }
            throw new AssertionError((Object)"must have been one of the known types");
        }

        private Type captureNullable(@Nullable Type type2) {
            if (type2 == null) {
                return null;
            }
            return this.capture(type2);
        }

        private Type[] capture(Type[] types) {
            Type[] result2 = new Type[types.length];
            for (int i = 0; i < types.length; ++i) {
                result2[i] = this.capture(types[i]);
            }
            return result2;
        }
    }

    private static final class TypeMappingIntrospector
    extends TypeVisitor {
        private static final WildcardCapturer wildcardCapturer = new WildcardCapturer();
        private final Map<TypeVariableKey, Type> mappings = Maps.newHashMap();

        private TypeMappingIntrospector() {
        }

        static ImmutableMap<TypeVariableKey, Type> getTypeMappings(Type contextType) {
            TypeMappingIntrospector introspector = new TypeMappingIntrospector();
            introspector.visit(wildcardCapturer.capture(contextType));
            return ImmutableMap.copyOf(introspector.mappings);
        }

        @Override
        void visitClass(Class<?> clazz) {
            this.visit(clazz.getGenericSuperclass());
            this.visit(clazz.getGenericInterfaces());
        }

        @Override
        void visitParameterizedType(ParameterizedType parameterizedType) {
            Type[] typeArgs;
            Class rawClass = (Class)parameterizedType.getRawType();
            TypeVariable<Class<T>>[] vars = rawClass.getTypeParameters();
            Preconditions.checkState(vars.length == (typeArgs = parameterizedType.getActualTypeArguments()).length);
            for (int i = 0; i < vars.length; ++i) {
                this.map(new TypeVariableKey(vars[i]), typeArgs[i]);
            }
            this.visit(rawClass);
            this.visit(parameterizedType.getOwnerType());
        }

        @Override
        void visitTypeVariable(TypeVariable<?> t) {
            this.visit(t.getBounds());
        }

        @Override
        void visitWildcardType(WildcardType t) {
            this.visit(t.getUpperBounds());
        }

        private void map(TypeVariableKey var, Type arg) {
            if (this.mappings.containsKey(var)) {
                return;
            }
            Type t = arg;
            while (t != null) {
                if (var.equalsType(t)) {
                    Type x = arg;
                    while (x != null) {
                        x = this.mappings.remove(TypeVariableKey.forLookup(x));
                    }
                    return;
                }
                t = this.mappings.get(TypeVariableKey.forLookup(t));
            }
            this.mappings.put(var, arg);
        }
    }

    private static class TypeTable {
        private final ImmutableMap<TypeVariableKey, Type> map;

        TypeTable() {
            this.map = ImmutableMap.of();
        }

        private TypeTable(ImmutableMap<TypeVariableKey, Type> map2) {
            this.map = map2;
        }

        final TypeTable where(Map<TypeVariableKey, ? extends Type> mappings) {
            ImmutableMap.Builder<TypeVariableKey, Type> builder = ImmutableMap.builder();
            builder.putAll(this.map);
            for (Map.Entry<TypeVariableKey, ? extends Type> mapping : mappings.entrySet()) {
                Type type2;
                TypeVariableKey variable = mapping.getKey();
                Preconditions.checkArgument(!variable.equalsType(type2 = mapping.getValue()), "Type variable %s bound to itself", variable);
                builder.put(variable, type2);
            }
            return new TypeTable(builder.build());
        }

        final Type resolve(final TypeVariable<?> var) {
            final TypeTable unguarded = this;
            TypeTable guarded = new TypeTable(){

                @Override
                public Type resolveInternal(TypeVariable<?> intermediateVar, TypeTable forDependent) {
                    if (intermediateVar.getGenericDeclaration().equals(var.getGenericDeclaration())) {
                        return intermediateVar;
                    }
                    return unguarded.resolveInternal(intermediateVar, forDependent);
                }
            };
            return this.resolveInternal(var, guarded);
        }

        Type resolveInternal(TypeVariable<?> var, TypeTable forDependants) {
            Type type2 = this.map.get(new TypeVariableKey(var));
            if (type2 == null) {
                Object[] bounds = var.getBounds();
                if (bounds.length == 0) {
                    return var;
                }
                Object[] resolvedBounds = new TypeResolver(forDependants).resolveTypes((Type[])bounds);
                if (Types.NativeTypeVariableEquals.NATIVE_TYPE_VARIABLE_ONLY && Arrays.equals(bounds, resolvedBounds)) {
                    return var;
                }
                return Types.newArtificialTypeVariable(var.getGenericDeclaration(), var.getName(), (Type[])resolvedBounds);
            }
            return new TypeResolver(forDependants).resolveType(type2);
        }
    }
}

