/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.xml;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.xml.SAXAdapter;
import org.objectweb.asm.xml.SAXClassAdapter;
import org.xml.sax.helpers.AttributesImpl;

public final class SAXAnnotationAdapter
extends AnnotationVisitor {
    SAXAdapter sa;
    private final String elementName;

    public SAXAnnotationAdapter(SAXAdapter sa, String elementName, int visible, String name, String desc) {
        this(327680, sa, elementName, visible, desc, name, -1, -1, null, null, null, null);
    }

    public SAXAnnotationAdapter(SAXAdapter sa, String elementName, int visible, int parameter, String desc) {
        this(327680, sa, elementName, visible, desc, null, parameter, -1, null, null, null, null);
    }

    public SAXAnnotationAdapter(SAXAdapter sa, String elementName, int visible, String name, String desc, int typeRef, TypePath typePath) {
        this(327680, sa, elementName, visible, desc, name, -1, typeRef, typePath, null, null, null);
    }

    public SAXAnnotationAdapter(SAXAdapter sa, String elementName, int visible, String name, String desc, int typeRef, TypePath typePath, String[] start, String[] end, int[] index) {
        this(327680, sa, elementName, visible, desc, name, -1, typeRef, typePath, start, end, index);
    }

    protected SAXAnnotationAdapter(int api, SAXAdapter sa, String elementName, int visible, String desc, String name, int parameter) {
        this(api, sa, elementName, visible, desc, name, parameter, -1, null, null, null, null);
    }

    protected SAXAnnotationAdapter(int api, SAXAdapter sa, String elementName, int visible, String desc, String name, int parameter, int typeRef, TypePath typePath, String[] start, String[] end, int[] index) {
        super(api);
        int i;
        StringBuffer value;
        this.sa = sa;
        this.elementName = elementName;
        AttributesImpl att = new AttributesImpl();
        if (name != null) {
            att.addAttribute("", "name", "name", "", name);
        }
        if (visible != 0) {
            att.addAttribute("", "visible", "visible", "", visible > 0 ? "true" : "false");
        }
        if (parameter != -1) {
            att.addAttribute("", "parameter", "parameter", "", Integer.toString(parameter));
        }
        if (desc != null) {
            att.addAttribute("", "desc", "desc", "", desc);
        }
        if (typeRef != -1) {
            att.addAttribute("", "typeRef", "typeRef", "", Integer.toString(typeRef));
        }
        if (typePath != null) {
            att.addAttribute("", "typePath", "typePath", "", typePath.toString());
        }
        if (start != null) {
            value = new StringBuffer(start[0]);
            for (i = 1; i < start.length; ++i) {
                value.append(" ").append(start[i]);
            }
            att.addAttribute("", "start", "start", "", value.toString());
        }
        if (end != null) {
            value = new StringBuffer(end[0]);
            for (i = 1; i < end.length; ++i) {
                value.append(" ").append(end[i]);
            }
            att.addAttribute("", "end", "end", "", value.toString());
        }
        if (index != null) {
            value = new StringBuffer();
            value.append(index[0]);
            for (i = 1; i < index.length; ++i) {
                value.append(" ").append(index[i]);
            }
            att.addAttribute("", "index", "index", "", value.toString());
        }
        sa.addStart(elementName, att);
    }

    public void visit(String name, Object value) {
        Class<?> c = value.getClass();
        if (c.isArray()) {
            AnnotationVisitor av = this.visitArray(name);
            if (value instanceof byte[]) {
                byte[] b = (byte[])value;
                for (int i = 0; i < b.length; ++i) {
                    av.visit(null, new Byte(b[i]));
                }
            } else if (value instanceof char[]) {
                char[] b = (char[])value;
                for (int i = 0; i < b.length; ++i) {
                    av.visit(null, new Character(b[i]));
                }
            } else if (value instanceof short[]) {
                short[] b = (short[])value;
                for (int i = 0; i < b.length; ++i) {
                    av.visit(null, new Short(b[i]));
                }
            } else if (value instanceof boolean[]) {
                boolean[] b = (boolean[])value;
                for (int i = 0; i < b.length; ++i) {
                    av.visit(null, b[i]);
                }
            } else if (value instanceof int[]) {
                int[] b = (int[])value;
                for (int i = 0; i < b.length; ++i) {
                    av.visit(null, new Integer(b[i]));
                }
            } else if (value instanceof long[]) {
                long[] b = (long[])value;
                for (int i = 0; i < b.length; ++i) {
                    av.visit(null, new Long(b[i]));
                }
            } else if (value instanceof float[]) {
                float[] b = (float[])value;
                for (int i = 0; i < b.length; ++i) {
                    av.visit(null, new Float(b[i]));
                }
            } else if (value instanceof double[]) {
                double[] b = (double[])value;
                for (int i = 0; i < b.length; ++i) {
                    av.visit(null, new Double(b[i]));
                }
            }
            av.visitEnd();
        } else {
            this.addValueElement("annotationValue", name, Type.getDescriptor(c), value.toString());
        }
    }

    public void visitEnum(String name, String desc, String value) {
        this.addValueElement("annotationValueEnum", name, desc, value);
    }

    public AnnotationVisitor visitAnnotation(String name, String desc) {
        return new SAXAnnotationAdapter(this.sa, "annotationValueAnnotation", 0, name, desc);
    }

    public AnnotationVisitor visitArray(String name) {
        return new SAXAnnotationAdapter(this.sa, "annotationValueArray", 0, name, null);
    }

    public void visitEnd() {
        this.sa.addEnd(this.elementName);
    }

    private void addValueElement(String element, String name, String desc, String value) {
        AttributesImpl att = new AttributesImpl();
        if (name != null) {
            att.addAttribute("", "name", "name", "", name);
        }
        if (desc != null) {
            att.addAttribute("", "desc", "desc", "", desc);
        }
        if (value != null) {
            att.addAttribute("", "value", "value", "", SAXClassAdapter.encode(value));
        }
        this.sa.addElement(element, att);
    }
}

