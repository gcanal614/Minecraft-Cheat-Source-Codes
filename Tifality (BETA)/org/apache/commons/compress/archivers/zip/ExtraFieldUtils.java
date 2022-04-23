/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.compress.archivers.zip;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipException;
import org.apache.commons.compress.archivers.zip.AsiExtraField;
import org.apache.commons.compress.archivers.zip.JarMarker;
import org.apache.commons.compress.archivers.zip.UnicodeCommentExtraField;
import org.apache.commons.compress.archivers.zip.UnicodePathExtraField;
import org.apache.commons.compress.archivers.zip.UnparseableExtraFieldData;
import org.apache.commons.compress.archivers.zip.UnrecognizedExtraField;
import org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp;
import org.apache.commons.compress.archivers.zip.X7875_NewUnix;
import org.apache.commons.compress.archivers.zip.Zip64ExtendedInformationExtraField;
import org.apache.commons.compress.archivers.zip.ZipExtraField;
import org.apache.commons.compress.archivers.zip.ZipShort;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ExtraFieldUtils {
    private static final int WORD = 4;
    private static final Map<ZipShort, Class<?>> implementations = new ConcurrentHashMap();

    public static void register(Class<?> c) {
        try {
            ZipExtraField ze = (ZipExtraField)c.newInstance();
            implementations.put(ze.getHeaderId(), c);
        }
        catch (ClassCastException cc) {
            throw new RuntimeException(c + " doesn't implement ZipExtraField");
        }
        catch (InstantiationException ie) {
            throw new RuntimeException(c + " is not a concrete class");
        }
        catch (IllegalAccessException ie) {
            throw new RuntimeException(c + "'s no-arg constructor is not public");
        }
    }

    public static ZipExtraField createExtraField(ZipShort headerId) throws InstantiationException, IllegalAccessException {
        Class<?> c = implementations.get(headerId);
        if (c != null) {
            return (ZipExtraField)c.newInstance();
        }
        UnrecognizedExtraField u = new UnrecognizedExtraField();
        u.setHeaderId(headerId);
        return u;
    }

    public static ZipExtraField[] parse(byte[] data2) throws ZipException {
        return ExtraFieldUtils.parse(data2, true, UnparseableExtraField.THROW);
    }

    public static ZipExtraField[] parse(byte[] data2, boolean local) throws ZipException {
        return ExtraFieldUtils.parse(data2, local, UnparseableExtraField.THROW);
    }

    public static ZipExtraField[] parse(byte[] data2, boolean local, UnparseableExtraField onUnparseableData) throws ZipException {
        int length;
        ArrayList<ZipExtraField> v = new ArrayList<ZipExtraField>();
        block8: for (int start = 0; start <= data2.length - 4; start += length + 4) {
            ZipShort headerId = new ZipShort(data2, start);
            length = new ZipShort(data2, start + 2).getValue();
            if (start + 4 + length > data2.length) {
                switch (onUnparseableData.getKey()) {
                    case 0: {
                        throw new ZipException("bad extra field starting at " + start + ".  Block length of " + length + " bytes exceeds remaining" + " data of " + (data2.length - start - 4) + " bytes.");
                    }
                    case 2: {
                        UnparseableExtraFieldData field = new UnparseableExtraFieldData();
                        if (local) {
                            field.parseFromLocalFileData(data2, start, data2.length - start);
                        } else {
                            field.parseFromCentralDirectoryData(data2, start, data2.length - start);
                        }
                        v.add(field);
                    }
                    case 1: {
                        break block8;
                    }
                    default: {
                        throw new ZipException("unknown UnparseableExtraField key: " + onUnparseableData.getKey());
                    }
                }
            }
            try {
                ZipExtraField ze = ExtraFieldUtils.createExtraField(headerId);
                if (local) {
                    ze.parseFromLocalFileData(data2, start + 4, length);
                } else {
                    ze.parseFromCentralDirectoryData(data2, start + 4, length);
                }
                v.add(ze);
                continue;
            }
            catch (InstantiationException ie) {
                throw (ZipException)new ZipException(ie.getMessage()).initCause(ie);
            }
            catch (IllegalAccessException iae) {
                throw (ZipException)new ZipException(iae.getMessage()).initCause(iae);
            }
        }
        ZipExtraField[] result2 = new ZipExtraField[v.size()];
        return v.toArray(result2);
    }

    public static byte[] mergeLocalFileDataData(ZipExtraField[] data2) {
        byte[] local;
        boolean lastIsUnparseableHolder = data2.length > 0 && data2[data2.length - 1] instanceof UnparseableExtraFieldData;
        int regularExtraFieldCount = lastIsUnparseableHolder ? data2.length - 1 : data2.length;
        int sum = 4 * regularExtraFieldCount;
        for (ZipExtraField element : data2) {
            sum += element.getLocalFileDataLength().getValue();
        }
        byte[] result2 = new byte[sum];
        int start = 0;
        for (int i = 0; i < regularExtraFieldCount; ++i) {
            System.arraycopy(data2[i].getHeaderId().getBytes(), 0, result2, start, 2);
            System.arraycopy(data2[i].getLocalFileDataLength().getBytes(), 0, result2, start + 2, 2);
            start += 4;
            byte[] local2 = data2[i].getLocalFileDataData();
            if (local2 == null) continue;
            System.arraycopy(local2, 0, result2, start, local2.length);
            start += local2.length;
        }
        if (lastIsUnparseableHolder && (local = data2[data2.length - 1].getLocalFileDataData()) != null) {
            System.arraycopy(local, 0, result2, start, local.length);
        }
        return result2;
    }

    public static byte[] mergeCentralDirectoryData(ZipExtraField[] data2) {
        byte[] local;
        boolean lastIsUnparseableHolder = data2.length > 0 && data2[data2.length - 1] instanceof UnparseableExtraFieldData;
        int regularExtraFieldCount = lastIsUnparseableHolder ? data2.length - 1 : data2.length;
        int sum = 4 * regularExtraFieldCount;
        for (ZipExtraField element : data2) {
            sum += element.getCentralDirectoryLength().getValue();
        }
        byte[] result2 = new byte[sum];
        int start = 0;
        for (int i = 0; i < regularExtraFieldCount; ++i) {
            System.arraycopy(data2[i].getHeaderId().getBytes(), 0, result2, start, 2);
            System.arraycopy(data2[i].getCentralDirectoryLength().getBytes(), 0, result2, start + 2, 2);
            start += 4;
            byte[] local2 = data2[i].getCentralDirectoryData();
            if (local2 == null) continue;
            System.arraycopy(local2, 0, result2, start, local2.length);
            start += local2.length;
        }
        if (lastIsUnparseableHolder && (local = data2[data2.length - 1].getCentralDirectoryData()) != null) {
            System.arraycopy(local, 0, result2, start, local.length);
        }
        return result2;
    }

    static {
        ExtraFieldUtils.register(AsiExtraField.class);
        ExtraFieldUtils.register(X5455_ExtendedTimestamp.class);
        ExtraFieldUtils.register(X7875_NewUnix.class);
        ExtraFieldUtils.register(JarMarker.class);
        ExtraFieldUtils.register(UnicodePathExtraField.class);
        ExtraFieldUtils.register(UnicodeCommentExtraField.class);
        ExtraFieldUtils.register(Zip64ExtendedInformationExtraField.class);
    }

    public static final class UnparseableExtraField {
        public static final int THROW_KEY = 0;
        public static final int SKIP_KEY = 1;
        public static final int READ_KEY = 2;
        public static final UnparseableExtraField THROW = new UnparseableExtraField(0);
        public static final UnparseableExtraField SKIP = new UnparseableExtraField(1);
        public static final UnparseableExtraField READ = new UnparseableExtraField(2);
        private final int key;

        private UnparseableExtraField(int k) {
            this.key = k;
        }

        public int getKey() {
            return this.key;
        }
    }
}

