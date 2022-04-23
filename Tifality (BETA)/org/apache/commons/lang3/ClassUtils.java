/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.lang3;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.JavaVersion;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.mutable.MutableObject;

public class ClassUtils {
    public static final char PACKAGE_SEPARATOR_CHAR = '.';
    public static final String PACKAGE_SEPARATOR = String.valueOf('.');
    public static final char INNER_CLASS_SEPARATOR_CHAR = '$';
    public static final String INNER_CLASS_SEPARATOR = String.valueOf('$');
    private static final Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap();
    private static final Map<Class<?>, Class<?>> wrapperPrimitiveMap;
    private static final Map<String, String> abbreviationMap;
    private static final Map<String, String> reverseAbbreviationMap;

    public static String getShortClassName(Object object, String valueIfNull) {
        if (object == null) {
            return valueIfNull;
        }
        return ClassUtils.getShortClassName(object.getClass());
    }

    public static String getShortClassName(Class<?> cls) {
        if (cls == null) {
            return "";
        }
        return ClassUtils.getShortClassName(cls.getName());
    }

    public static String getShortClassName(String className) {
        int lastDotIdx;
        if (StringUtils.isEmpty(className)) {
            return "";
        }
        StringBuilder arrayPrefix = new StringBuilder();
        if (className.startsWith("[")) {
            while (className.charAt(0) == '[') {
                className = className.substring(1);
                arrayPrefix.append("[]");
            }
            if (className.charAt(0) == 'L' && className.charAt(className.length() - 1) == ';') {
                className = className.substring(1, className.length() - 1);
            }
            if (reverseAbbreviationMap.containsKey(className)) {
                className = reverseAbbreviationMap.get(className);
            }
        }
        int innerIdx = className.indexOf(36, (lastDotIdx = className.lastIndexOf(46)) == -1 ? 0 : lastDotIdx + 1);
        String out = className.substring(lastDotIdx + 1);
        if (innerIdx != -1) {
            out = out.replace('$', '.');
        }
        return out + arrayPrefix;
    }

    public static String getSimpleName(Class<?> cls) {
        if (cls == null) {
            return "";
        }
        return cls.getSimpleName();
    }

    public static String getSimpleName(Object object, String valueIfNull) {
        if (object == null) {
            return valueIfNull;
        }
        return ClassUtils.getSimpleName(object.getClass());
    }

    public static String getPackageName(Object object, String valueIfNull) {
        if (object == null) {
            return valueIfNull;
        }
        return ClassUtils.getPackageName(object.getClass());
    }

    public static String getPackageName(Class<?> cls) {
        if (cls == null) {
            return "";
        }
        return ClassUtils.getPackageName(cls.getName());
    }

    public static String getPackageName(String className) {
        int i;
        if (StringUtils.isEmpty(className)) {
            return "";
        }
        while (className.charAt(0) == '[') {
            className = className.substring(1);
        }
        if (className.charAt(0) == 'L' && className.charAt(className.length() - 1) == ';') {
            className = className.substring(1);
        }
        if ((i = className.lastIndexOf(46)) == -1) {
            return "";
        }
        return className.substring(0, i);
    }

    public static List<Class<?>> getAllSuperclasses(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        ArrayList classes2 = new ArrayList();
        for (Class<?> superclass = cls.getSuperclass(); superclass != null; superclass = superclass.getSuperclass()) {
            classes2.add(superclass);
        }
        return classes2;
    }

    public static List<Class<?>> getAllInterfaces(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        LinkedHashSet interfacesFound = new LinkedHashSet();
        ClassUtils.getAllInterfaces(cls, interfacesFound);
        return new ArrayList(interfacesFound);
    }

    private static void getAllInterfaces(Class<?> cls, HashSet<Class<?>> interfacesFound) {
        while (cls != null) {
            Class<?>[] interfaces;
            for (Class<?> i : interfaces = cls.getInterfaces()) {
                if (!interfacesFound.add(i)) continue;
                ClassUtils.getAllInterfaces(i, interfacesFound);
            }
            cls = cls.getSuperclass();
        }
    }

    public static List<Class<?>> convertClassNamesToClasses(List<String> classNames2) {
        if (classNames2 == null) {
            return null;
        }
        ArrayList classes2 = new ArrayList(classNames2.size());
        for (String className : classNames2) {
            try {
                classes2.add(Class.forName(className));
            }
            catch (Exception ex) {
                classes2.add(null);
            }
        }
        return classes2;
    }

    public static List<String> convertClassesToClassNames(List<Class<?>> classes2) {
        if (classes2 == null) {
            return null;
        }
        ArrayList<String> classNames2 = new ArrayList<String>(classes2.size());
        for (Class<?> cls : classes2) {
            if (cls == null) {
                classNames2.add(null);
                continue;
            }
            classNames2.add(cls.getName());
        }
        return classNames2;
    }

    public static boolean isAssignable(Class<?>[] classArray, Class<?> ... toClassArray) {
        return ClassUtils.isAssignable(classArray, toClassArray, SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_5));
    }

    public static boolean isAssignable(Class<?>[] classArray, Class<?>[] toClassArray, boolean autoboxing) {
        if (!ArrayUtils.isSameLength(classArray, toClassArray)) {
            return false;
        }
        if (classArray == null) {
            classArray = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        if (toClassArray == null) {
            toClassArray = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        for (int i = 0; i < classArray.length; ++i) {
            if (ClassUtils.isAssignable(classArray[i], toClassArray[i], autoboxing)) continue;
            return false;
        }
        return true;
    }

    public static boolean isPrimitiveOrWrapper(Class<?> type2) {
        if (type2 == null) {
            return false;
        }
        return type2.isPrimitive() || ClassUtils.isPrimitiveWrapper(type2);
    }

    public static boolean isPrimitiveWrapper(Class<?> type2) {
        return wrapperPrimitiveMap.containsKey(type2);
    }

    public static boolean isAssignable(Class<?> cls, Class<?> toClass) {
        return ClassUtils.isAssignable(cls, toClass, SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_5));
    }

    public static boolean isAssignable(Class<?> cls, Class<?> toClass, boolean autoboxing) {
        if (toClass == null) {
            return false;
        }
        if (cls == null) {
            return !toClass.isPrimitive();
        }
        if (autoboxing) {
            if (cls.isPrimitive() && !toClass.isPrimitive() && (cls = ClassUtils.primitiveToWrapper(cls)) == null) {
                return false;
            }
            if (toClass.isPrimitive() && !cls.isPrimitive() && (cls = ClassUtils.wrapperToPrimitive(cls)) == null) {
                return false;
            }
        }
        if (cls.equals(toClass)) {
            return true;
        }
        if (cls.isPrimitive()) {
            if (!toClass.isPrimitive()) {
                return false;
            }
            if (Integer.TYPE.equals(cls)) {
                return Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
            }
            if (Long.TYPE.equals(cls)) {
                return Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
            }
            if (Boolean.TYPE.equals(cls)) {
                return false;
            }
            if (Double.TYPE.equals(cls)) {
                return false;
            }
            if (Float.TYPE.equals(cls)) {
                return Double.TYPE.equals(toClass);
            }
            if (Character.TYPE.equals(cls)) {
                return Integer.TYPE.equals(toClass) || Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
            }
            if (Short.TYPE.equals(cls)) {
                return Integer.TYPE.equals(toClass) || Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
            }
            if (Byte.TYPE.equals(cls)) {
                return Short.TYPE.equals(toClass) || Integer.TYPE.equals(toClass) || Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
            }
            return false;
        }
        return toClass.isAssignableFrom(cls);
    }

    public static Class<?> primitiveToWrapper(Class<?> cls) {
        Class<?> convertedClass = cls;
        if (cls != null && cls.isPrimitive()) {
            convertedClass = primitiveWrapperMap.get(cls);
        }
        return convertedClass;
    }

    public static Class<?>[] primitivesToWrappers(Class<?> ... classes2) {
        if (classes2 == null) {
            return null;
        }
        if (classes2.length == 0) {
            return classes2;
        }
        Class[] convertedClasses = new Class[classes2.length];
        for (int i = 0; i < classes2.length; ++i) {
            convertedClasses[i] = ClassUtils.primitiveToWrapper(classes2[i]);
        }
        return convertedClasses;
    }

    public static Class<?> wrapperToPrimitive(Class<?> cls) {
        return wrapperPrimitiveMap.get(cls);
    }

    public static Class<?>[] wrappersToPrimitives(Class<?> ... classes2) {
        if (classes2 == null) {
            return null;
        }
        if (classes2.length == 0) {
            return classes2;
        }
        Class[] convertedClasses = new Class[classes2.length];
        for (int i = 0; i < classes2.length; ++i) {
            convertedClasses[i] = ClassUtils.wrapperToPrimitive(classes2[i]);
        }
        return convertedClasses;
    }

    public static boolean isInnerClass(Class<?> cls) {
        return cls != null && cls.getEnclosingClass() != null;
    }

    public static Class<?> getClass(ClassLoader classLoader, String className, boolean initialize2) throws ClassNotFoundException {
        try {
            Class<?> clazz;
            if (abbreviationMap.containsKey(className)) {
                String clsName = "[" + abbreviationMap.get(className);
                clazz = Class.forName(clsName, initialize2, classLoader).getComponentType();
            } else {
                clazz = Class.forName(ClassUtils.toCanonicalName(className), initialize2, classLoader);
            }
            return clazz;
        }
        catch (ClassNotFoundException ex) {
            int lastDotIndex = className.lastIndexOf(46);
            if (lastDotIndex != -1) {
                try {
                    return ClassUtils.getClass(classLoader, className.substring(0, lastDotIndex) + '$' + className.substring(lastDotIndex + 1), initialize2);
                }
                catch (ClassNotFoundException ex2) {
                    // empty catch block
                }
            }
            throw ex;
        }
    }

    public static Class<?> getClass(ClassLoader classLoader, String className) throws ClassNotFoundException {
        return ClassUtils.getClass(classLoader, className, true);
    }

    public static Class<?> getClass(String className) throws ClassNotFoundException {
        return ClassUtils.getClass(className, true);
    }

    public static Class<?> getClass(String className, boolean initialize2) throws ClassNotFoundException {
        ClassLoader contextCL = Thread.currentThread().getContextClassLoader();
        ClassLoader loader = contextCL == null ? ClassUtils.class.getClassLoader() : contextCL;
        return ClassUtils.getClass(loader, className, initialize2);
    }

    public static Method getPublicMethod(Class<?> cls, String methodName, Class<?> ... parameterTypes) throws SecurityException, NoSuchMethodException {
        Method declaredMethod = cls.getMethod(methodName, parameterTypes);
        if (Modifier.isPublic(declaredMethod.getDeclaringClass().getModifiers())) {
            return declaredMethod;
        }
        ArrayList candidateClasses = new ArrayList();
        candidateClasses.addAll(ClassUtils.getAllInterfaces(cls));
        candidateClasses.addAll(ClassUtils.getAllSuperclasses(cls));
        for (Class clazz : candidateClasses) {
            Method candidateMethod;
            if (!Modifier.isPublic(clazz.getModifiers())) continue;
            try {
                candidateMethod = clazz.getMethod(methodName, parameterTypes);
            }
            catch (NoSuchMethodException ex) {
                continue;
            }
            if (!Modifier.isPublic(candidateMethod.getDeclaringClass().getModifiers())) continue;
            return candidateMethod;
        }
        throw new NoSuchMethodException("Can't find a public method for " + methodName + " " + ArrayUtils.toString(parameterTypes));
    }

    private static String toCanonicalName(String className) {
        if ((className = StringUtils.deleteWhitespace(className)) == null) {
            throw new NullPointerException("className must not be null.");
        }
        if (className.endsWith("[]")) {
            StringBuilder classNameBuffer = new StringBuilder();
            while (className.endsWith("[]")) {
                className = className.substring(0, className.length() - 2);
                classNameBuffer.append("[");
            }
            String abbreviation = abbreviationMap.get(className);
            if (abbreviation != null) {
                classNameBuffer.append(abbreviation);
            } else {
                classNameBuffer.append("L").append(className).append(";");
            }
            className = classNameBuffer.toString();
        }
        return className;
    }

    public static Class<?>[] toClass(Object ... array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        Class[] classes2 = new Class[array.length];
        for (int i = 0; i < array.length; ++i) {
            classes2[i] = array[i] == null ? null : array[i].getClass();
        }
        return classes2;
    }

    public static String getShortCanonicalName(Object object, String valueIfNull) {
        if (object == null) {
            return valueIfNull;
        }
        return ClassUtils.getShortCanonicalName(object.getClass().getName());
    }

    public static String getShortCanonicalName(Class<?> cls) {
        if (cls == null) {
            return "";
        }
        return ClassUtils.getShortCanonicalName(cls.getName());
    }

    public static String getShortCanonicalName(String canonicalName) {
        return ClassUtils.getShortClassName(ClassUtils.getCanonicalName(canonicalName));
    }

    public static String getPackageCanonicalName(Object object, String valueIfNull) {
        if (object == null) {
            return valueIfNull;
        }
        return ClassUtils.getPackageCanonicalName(object.getClass().getName());
    }

    public static String getPackageCanonicalName(Class<?> cls) {
        if (cls == null) {
            return "";
        }
        return ClassUtils.getPackageCanonicalName(cls.getName());
    }

    public static String getPackageCanonicalName(String canonicalName) {
        return ClassUtils.getPackageName(ClassUtils.getCanonicalName(canonicalName));
    }

    private static String getCanonicalName(String className) {
        if ((className = StringUtils.deleteWhitespace(className)) == null) {
            return null;
        }
        int dim = 0;
        while (className.startsWith("[")) {
            ++dim;
            className = className.substring(1);
        }
        if (dim < 1) {
            return className;
        }
        if (className.startsWith("L")) {
            className = className.substring(1, className.endsWith(";") ? className.length() - 1 : className.length());
        } else if (className.length() > 0) {
            className = reverseAbbreviationMap.get(className.substring(0, 1));
        }
        StringBuilder canonicalClassNameBuffer = new StringBuilder(className);
        for (int i = 0; i < dim; ++i) {
            canonicalClassNameBuffer.append("[]");
        }
        return canonicalClassNameBuffer.toString();
    }

    public static Iterable<Class<?>> hierarchy(Class<?> type2) {
        return ClassUtils.hierarchy(type2, Interfaces.EXCLUDE);
    }

    public static Iterable<Class<?>> hierarchy(final Class<?> type2, Interfaces interfacesBehavior) {
        final Iterable classes2 = new Iterable<Class<?>>(){

            @Override
            public Iterator<Class<?>> iterator() {
                final MutableObject<Class> next = new MutableObject<Class>(type2);
                return new Iterator<Class<?>>(){

                    @Override
                    public boolean hasNext() {
                        return next.getValue() != null;
                    }

                    @Override
                    public Class<?> next() {
                        Class result2 = (Class)next.getValue();
                        next.setValue(result2.getSuperclass());
                        return result2;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
        if (interfacesBehavior != Interfaces.INCLUDE) {
            return classes2;
        }
        return new Iterable<Class<?>>(){

            @Override
            public Iterator<Class<?>> iterator() {
                final HashSet seenInterfaces = new HashSet();
                final Iterator wrapped = classes2.iterator();
                return new Iterator<Class<?>>(){
                    Iterator<Class<?>> interfaces = Collections.emptySet().iterator();

                    @Override
                    public boolean hasNext() {
                        return this.interfaces.hasNext() || wrapped.hasNext();
                    }

                    @Override
                    public Class<?> next() {
                        if (this.interfaces.hasNext()) {
                            Class<?> nextInterface = this.interfaces.next();
                            seenInterfaces.add(nextInterface);
                            return nextInterface;
                        }
                        Class nextSuperclass = (Class)wrapped.next();
                        LinkedHashSet currentInterfaces = new LinkedHashSet();
                        this.walkInterfaces(currentInterfaces, nextSuperclass);
                        this.interfaces = currentInterfaces.iterator();
                        return nextSuperclass;
                    }

                    private void walkInterfaces(Set<Class<?>> addTo, Class<?> c) {
                        for (Class<?> iface : c.getInterfaces()) {
                            if (!seenInterfaces.contains(iface)) {
                                addTo.add(iface);
                            }
                            this.walkInterfaces(addTo, iface);
                        }
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    static {
        primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
        primitiveWrapperMap.put(Byte.TYPE, Byte.class);
        primitiveWrapperMap.put(Character.TYPE, Character.class);
        primitiveWrapperMap.put(Short.TYPE, Short.class);
        primitiveWrapperMap.put(Integer.TYPE, Integer.class);
        primitiveWrapperMap.put(Long.TYPE, Long.class);
        primitiveWrapperMap.put(Double.TYPE, Double.class);
        primitiveWrapperMap.put(Float.TYPE, Float.class);
        primitiveWrapperMap.put(Void.TYPE, Void.TYPE);
        wrapperPrimitiveMap = new HashMap();
        for (Class<?> primitiveClass : primitiveWrapperMap.keySet()) {
            Class<?> wrapperClass;
            if (primitiveClass.equals(wrapperClass = primitiveWrapperMap.get(primitiveClass))) continue;
            wrapperPrimitiveMap.put(wrapperClass, primitiveClass);
        }
        HashMap<String, String> m = new HashMap<String, String>();
        m.put("int", "I");
        m.put("boolean", "Z");
        m.put("float", "F");
        m.put("long", "J");
        m.put("short", "S");
        m.put("byte", "B");
        m.put("double", "D");
        m.put("char", "C");
        m.put("void", "V");
        HashMap r = new HashMap();
        for (Map.Entry e : m.entrySet()) {
            r.put(e.getValue(), e.getKey());
        }
        abbreviationMap = Collections.unmodifiableMap(m);
        reverseAbbreviationMap = Collections.unmodifiableMap(r);
    }

    public static enum Interfaces {
        INCLUDE,
        EXCLUDE;

    }
}

