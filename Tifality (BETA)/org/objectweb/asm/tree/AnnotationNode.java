/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.tree;

import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.AnnotationVisitor;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class AnnotationNode
extends AnnotationVisitor {
    public String desc;
    public List<Object> values;

    public AnnotationNode(String desc) {
        this(327680, desc);
        if (this.getClass() != AnnotationNode.class) {
            throw new IllegalStateException();
        }
    }

    public AnnotationNode(int api, String desc) {
        super(api);
        this.desc = desc;
    }

    AnnotationNode(List<Object> values2) {
        super(327680);
        this.values = values2;
    }

    @Override
    public void visit(String name, Object value) {
        if (this.values == null) {
            this.values = new ArrayList<Object>(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(name);
        }
        this.values.add(value);
    }

    @Override
    public void visitEnum(String name, String desc, String value) {
        if (this.values == null) {
            this.values = new ArrayList<Object>(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(name);
        }
        this.values.add(new String[]{desc, value});
    }

    @Override
    public AnnotationVisitor visitAnnotation(String name, String desc) {
        if (this.values == null) {
            this.values = new ArrayList<Object>(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(name);
        }
        AnnotationNode annotation = new AnnotationNode(desc);
        this.values.add(annotation);
        return annotation;
    }

    @Override
    public AnnotationVisitor visitArray(String name) {
        if (this.values == null) {
            this.values = new ArrayList<Object>(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(name);
        }
        ArrayList<Object> array = new ArrayList<Object>();
        this.values.add(array);
        return new AnnotationNode(array);
    }

    @Override
    public void visitEnd() {
    }

    public void check(int api) {
    }

    public void accept(AnnotationVisitor av) {
        if (av != null) {
            if (this.values != null) {
                for (int i = 0; i < this.values.size(); i += 2) {
                    String name = (String)this.values.get(i);
                    Object value = this.values.get(i + 1);
                    AnnotationNode.accept(av, name, value);
                }
            }
            av.visitEnd();
        }
    }

    static void accept(AnnotationVisitor av, String name, Object value) {
        if (av != null) {
            if (value instanceof String[]) {
                String[] typeconst = (String[])value;
                av.visitEnum(name, typeconst[0], typeconst[1]);
            } else if (value instanceof AnnotationNode) {
                AnnotationNode an = (AnnotationNode)value;
                an.accept(av.visitAnnotation(name, an.desc));
            } else if (value instanceof List) {
                AnnotationVisitor v = av.visitArray(name);
                List array = (List)value;
                for (int j = 0; j < array.size(); ++j) {
                    AnnotationNode.accept(v, null, array.get(j));
                }
                v.visitEnd();
            } else {
                av.visit(name, value);
            }
        }
    }
}

