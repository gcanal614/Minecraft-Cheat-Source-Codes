/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.commons;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.commons.RemappingAnnotationAdapter;
import org.objectweb.asm.commons.RemappingFieldAdapter;
import org.objectweb.asm.commons.RemappingMethodAdapter;

public class RemappingClassAdapter
extends ClassVisitor {
    protected final Remapper remapper;
    protected String className;

    public RemappingClassAdapter(ClassVisitor cv, Remapper remapper) {
        this(327680, cv, remapper);
    }

    protected RemappingClassAdapter(int api, ClassVisitor cv, Remapper remapper) {
        super(api, cv);
        this.remapper = remapper;
    }

    public void visit(int version, int access, String name, String signature2, String superName, String[] interfaces) {
        this.className = name;
        super.visit(version, access, this.remapper.mapType(name), this.remapper.mapSignature(signature2, false), this.remapper.mapType(superName), interfaces == null ? null : this.remapper.mapTypes(interfaces));
    }

    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        AnnotationVisitor av = super.visitAnnotation(this.remapper.mapDesc(desc), visible);
        return av == null ? null : this.createRemappingAnnotationAdapter(av);
    }

    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        AnnotationVisitor av = super.visitTypeAnnotation(typeRef, typePath, this.remapper.mapDesc(desc), visible);
        return av == null ? null : this.createRemappingAnnotationAdapter(av);
    }

    public FieldVisitor visitField(int access, String name, String desc, String signature2, Object value) {
        FieldVisitor fv = super.visitField(access, this.remapper.mapFieldName(this.className, name, desc), this.remapper.mapDesc(desc), this.remapper.mapSignature(signature2, true), this.remapper.mapValue(value));
        return fv == null ? null : this.createRemappingFieldAdapter(fv);
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature2, String[] exceptions) {
        String newDesc = this.remapper.mapMethodDesc(desc);
        MethodVisitor mv = super.visitMethod(access, this.remapper.mapMethodName(this.className, name, desc), newDesc, this.remapper.mapSignature(signature2, false), exceptions == null ? null : this.remapper.mapTypes(exceptions));
        return mv == null ? null : this.createRemappingMethodAdapter(access, newDesc, mv);
    }

    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        super.visitInnerClass(this.remapper.mapType(name), outerName == null ? null : this.remapper.mapType(outerName), innerName, access);
    }

    public void visitOuterClass(String owner, String name, String desc) {
        super.visitOuterClass(this.remapper.mapType(owner), name == null ? null : this.remapper.mapMethodName(owner, name, desc), desc == null ? null : this.remapper.mapMethodDesc(desc));
    }

    protected FieldVisitor createRemappingFieldAdapter(FieldVisitor fv) {
        return new RemappingFieldAdapter(fv, this.remapper);
    }

    protected MethodVisitor createRemappingMethodAdapter(int access, String newDesc, MethodVisitor mv) {
        return new RemappingMethodAdapter(access, newDesc, mv, this.remapper);
    }

    protected AnnotationVisitor createRemappingAnnotationAdapter(AnnotationVisitor av) {
        return new RemappingAnnotationAdapter(av, this.remapper);
    }
}

