/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.util;

import java.io.PrintWriter;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceAnnotationVisitor;
import org.objectweb.asm.util.TraceFieldVisitor;
import org.objectweb.asm.util.TraceMethodVisitor;

public final class TraceClassVisitor
extends ClassVisitor {
    private final PrintWriter pw;
    public final Printer p;

    public TraceClassVisitor(PrintWriter pw) {
        this(null, pw);
    }

    public TraceClassVisitor(ClassVisitor cv, PrintWriter pw) {
        this(cv, new Textifier(), pw);
    }

    public TraceClassVisitor(ClassVisitor cv, Printer p, PrintWriter pw) {
        super(327680, cv);
        this.pw = pw;
        this.p = p;
    }

    public void visit(int version, int access, String name, String signature2, String superName, String[] interfaces) {
        this.p.visit(version, access, name, signature2, superName, interfaces);
        super.visit(version, access, name, signature2, superName, interfaces);
    }

    public void visitSource(String file, String debug) {
        this.p.visitSource(file, debug);
        super.visitSource(file, debug);
    }

    public void visitOuterClass(String owner, String name, String desc) {
        this.p.visitOuterClass(owner, name, desc);
        super.visitOuterClass(owner, name, desc);
    }

    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        Printer p = this.p.visitClassAnnotation(desc, visible);
        AnnotationVisitor av = this.cv == null ? null : this.cv.visitAnnotation(desc, visible);
        return new TraceAnnotationVisitor(av, p);
    }

    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        Printer p = this.p.visitClassTypeAnnotation(typeRef, typePath, desc, visible);
        AnnotationVisitor av = this.cv == null ? null : this.cv.visitTypeAnnotation(typeRef, typePath, desc, visible);
        return new TraceAnnotationVisitor(av, p);
    }

    public void visitAttribute(Attribute attr) {
        this.p.visitClassAttribute(attr);
        super.visitAttribute(attr);
    }

    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        this.p.visitInnerClass(name, outerName, innerName, access);
        super.visitInnerClass(name, outerName, innerName, access);
    }

    public FieldVisitor visitField(int access, String name, String desc, String signature2, Object value) {
        Printer p = this.p.visitField(access, name, desc, signature2, value);
        FieldVisitor fv = this.cv == null ? null : this.cv.visitField(access, name, desc, signature2, value);
        return new TraceFieldVisitor(fv, p);
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature2, String[] exceptions) {
        Printer p = this.p.visitMethod(access, name, desc, signature2, exceptions);
        MethodVisitor mv = this.cv == null ? null : this.cv.visitMethod(access, name, desc, signature2, exceptions);
        return new TraceMethodVisitor(mv, p);
    }

    public void visitEnd() {
        this.p.visitClassEnd();
        if (this.pw != null) {
            this.p.print(this.pw);
            this.pw.flush();
        }
        super.visitEnd();
    }
}

