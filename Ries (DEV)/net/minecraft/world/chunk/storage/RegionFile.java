/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.world.chunk.storage;

import com.google.common.collect.Lists;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import net.minecraft.server.MinecraftServer;

public class RegionFile {
    private static final byte[] emptySector = new byte[4096];
    private final File fileName;
    private RandomAccessFile dataFile;
    private final int[] offsets = new int[1024];
    private final int[] chunkTimestamps = new int[1024];
    private List<Boolean> sectorFree;
    private int sizeDelta;
    private long lastModified;

    public RegionFile(File fileNameIn) {
        this.fileName = fileNameIn;
        this.sizeDelta = 0;
        try {
            if (fileNameIn.exists()) {
                this.lastModified = fileNameIn.lastModified();
            }
            this.dataFile = new RandomAccessFile(fileNameIn, "rw");
            if (this.dataFile.length() < 4096L) {
                for (int i = 0; i < 1024; ++i) {
                    this.dataFile.writeInt(0);
                }
                for (int i1 = 0; i1 < 1024; ++i1) {
                    this.dataFile.writeInt(0);
                }
                this.sizeDelta += 8192;
            }
            if ((this.dataFile.length() & 0xFFFL) != 0L) {
                int j1 = 0;
                while ((long)j1 < (this.dataFile.length() & 0xFFFL)) {
                    this.dataFile.write(0);
                    ++j1;
                }
            }
            int k1 = (int)this.dataFile.length() / 4096;
            this.sectorFree = Lists.newArrayListWithCapacity((int)k1);
            for (int j = 0; j < k1; ++j) {
                this.sectorFree.add(Boolean.TRUE);
            }
            this.sectorFree.set(0, Boolean.FALSE);
            this.sectorFree.set(1, Boolean.FALSE);
            this.dataFile.seek(0L);
            for (int l1 = 0; l1 < 1024; ++l1) {
                int k;
                this.offsets[l1] = k = this.dataFile.readInt();
                if (k == 0 || (k >> 8) + (k & 0xFF) > this.sectorFree.size()) continue;
                for (int l = 0; l < (k & 0xFF); ++l) {
                    this.sectorFree.set((k >> 8) + l, Boolean.FALSE);
                }
            }
            for (int i2 = 0; i2 < 1024; ++i2) {
                int j2;
                this.chunkTimestamps[i2] = j2 = this.dataFile.readInt();
            }
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    public synchronized DataInputStream getChunkDataInputStream(int x, int z) {
        if (this.outOfBounds(x, z)) {
            return null;
        }
        try {
            int i = this.getOffset(x, z);
            if (i == 0) {
                return null;
            }
            int j = i >> 8;
            int k = i & 0xFF;
            if (j + k > this.sectorFree.size()) {
                return null;
            }
            this.dataFile.seek((long)j * 4096L);
            int l = this.dataFile.readInt();
            if (l > 4096 * k) {
                return null;
            }
            if (l <= 0) {
                return null;
            }
            byte b0 = this.dataFile.readByte();
            if (b0 == 1) {
                byte[] abyte1 = new byte[l - 1];
                this.dataFile.read(abyte1);
                return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(abyte1))));
            }
            if (b0 == 2) {
                byte[] abyte = new byte[l - 1];
                this.dataFile.read(abyte);
                return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(abyte))));
            }
            return null;
        }
        catch (IOException var9) {
            return null;
        }
    }

    public DataOutputStream getChunkDataOutputStream(int x, int z) {
        return this.outOfBounds(x, z) ? null : new DataOutputStream(new DeflaterOutputStream(new ChunkBuffer(x, z)));
    }

    protected synchronized void write(int x, int z, byte[] data, int length) {
        try {
            int i = this.getOffset(x, z);
            int j = i >> 8;
            int k = i & 0xFF;
            int l = (length + 5) / 4096 + 1;
            if (l >= 256) {
                return;
            }
            if (j != 0 && k == l) {
                this.write(j, data, length);
            } else {
                for (int i1 = 0; i1 < k; ++i1) {
                    this.sectorFree.set(j + i1, Boolean.TRUE);
                }
                int l1 = this.sectorFree.indexOf(Boolean.TRUE);
                int j1 = 0;
                if (l1 != -1) {
                    for (int k1 = l1; k1 < this.sectorFree.size(); ++k1) {
                        if (j1 != 0) {
                            j1 = this.sectorFree.get(k1).booleanValue() ? ++j1 : 0;
                        } else if (this.sectorFree.get(k1).booleanValue()) {
                            l1 = k1;
                            j1 = 1;
                        }
                        if (j1 >= l) break;
                    }
                }
                if (j1 >= l) {
                    j = l1;
                    this.setOffset(x, z, l1 << 8 | l);
                    for (int j2 = 0; j2 < l; ++j2) {
                        this.sectorFree.set(j + j2, Boolean.FALSE);
                    }
                    this.write(j, data, length);
                } else {
                    this.dataFile.seek(this.dataFile.length());
                    j = this.sectorFree.size();
                    for (int i2 = 0; i2 < l; ++i2) {
                        this.dataFile.write(emptySector);
                        this.sectorFree.add(Boolean.FALSE);
                    }
                    this.sizeDelta += 4096 * l;
                    this.write(j, data, length);
                    this.setOffset(x, z, j << 8 | l);
                }
            }
            this.setChunkTimestamp(x, z, (int)(MinecraftServer.getCurrentTimeMillis() / 1000L));
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    private void write(int sectorNumber, byte[] data, int length) throws IOException {
        this.dataFile.seek((long)sectorNumber * 4096L);
        this.dataFile.writeInt(length + 1);
        this.dataFile.writeByte(2);
        this.dataFile.write(data, 0, length);
    }

    private boolean outOfBounds(int x, int z) {
        return x < 0 || x >= 32 || z < 0 || z >= 32;
    }

    private int getOffset(int x, int z) {
        return this.offsets[x + z * 32];
    }

    public boolean isChunkSaved(int x, int z) {
        return this.getOffset(x, z) != 0;
    }

    private void setOffset(int x, int z, int offset) throws IOException {
        this.offsets[x + z * 32] = offset;
        this.dataFile.seek(((long)x + (long)z * 32L) * 4L);
        this.dataFile.writeInt(offset);
    }

    private void setChunkTimestamp(int x, int z, int timestamp) throws IOException {
        this.chunkTimestamps[x + z * 32] = timestamp;
        this.dataFile.seek(4096L + ((long)x + (long)z * 32L) * 4L);
        this.dataFile.writeInt(timestamp);
    }

    public void close() throws IOException {
        if (this.dataFile != null) {
            this.dataFile.close();
        }
    }

    class ChunkBuffer
    extends ByteArrayOutputStream {
        private final int chunkX;
        private final int chunkZ;

        public ChunkBuffer(int x, int z) {
            super(8096);
            this.chunkX = x;
            this.chunkZ = z;
        }

        @Override
        public void close() {
            RegionFile.this.write(this.chunkX, this.chunkZ, this.buf, this.count);
        }
    }
}

