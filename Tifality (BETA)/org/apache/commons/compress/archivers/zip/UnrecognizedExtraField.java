/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.compress.archivers.zip;

import org.apache.commons.compress.archivers.zip.ZipExtraField;
import org.apache.commons.compress.archivers.zip.ZipShort;
import org.apache.commons.compress.archivers.zip.ZipUtil;

public class UnrecognizedExtraField
implements ZipExtraField {
    private ZipShort headerId;
    private byte[] localData;
    private byte[] centralData;

    public void setHeaderId(ZipShort headerId) {
        this.headerId = headerId;
    }

    public ZipShort getHeaderId() {
        return this.headerId;
    }

    public void setLocalFileDataData(byte[] data2) {
        this.localData = ZipUtil.copy(data2);
    }

    public ZipShort getLocalFileDataLength() {
        return new ZipShort(this.localData != null ? this.localData.length : 0);
    }

    public byte[] getLocalFileDataData() {
        return ZipUtil.copy(this.localData);
    }

    public void setCentralDirectoryData(byte[] data2) {
        this.centralData = ZipUtil.copy(data2);
    }

    public ZipShort getCentralDirectoryLength() {
        if (this.centralData != null) {
            return new ZipShort(this.centralData.length);
        }
        return this.getLocalFileDataLength();
    }

    public byte[] getCentralDirectoryData() {
        if (this.centralData != null) {
            return ZipUtil.copy(this.centralData);
        }
        return this.getLocalFileDataData();
    }

    public void parseFromLocalFileData(byte[] data2, int offset, int length) {
        byte[] tmp = new byte[length];
        System.arraycopy(data2, offset, tmp, 0, length);
        this.setLocalFileDataData(tmp);
    }

    public void parseFromCentralDirectoryData(byte[] data2, int offset, int length) {
        byte[] tmp = new byte[length];
        System.arraycopy(data2, offset, tmp, 0, length);
        this.setCentralDirectoryData(tmp);
        if (this.localData == null) {
            this.setLocalFileDataData(tmp);
        }
    }
}

