/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.util;

import com.sun.media.sound.WaveFileReader;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import org.lwjgl.LWJGLUtil;

public class WaveData {
    public final ByteBuffer data;
    public final int format;
    public final int samplerate;

    private WaveData(ByteBuffer data2, int format, int samplerate) {
        this.data = data2;
        this.format = format;
        this.samplerate = samplerate;
    }

    public void dispose() {
        this.data.clear();
    }

    public static WaveData create(URL path) {
        try {
            WaveFileReader wfr = new WaveFileReader();
            return WaveData.create(wfr.getAudioInputStream(new BufferedInputStream(path.openStream())));
        }
        catch (Exception e) {
            LWJGLUtil.log("Unable to create from: " + path + ", " + e.getMessage());
            return null;
        }
    }

    public static WaveData create(String path) {
        return WaveData.create(Thread.currentThread().getContextClassLoader().getResource(path));
    }

    public static WaveData create(InputStream is) {
        try {
            return WaveData.create(AudioSystem.getAudioInputStream(is));
        }
        catch (Exception e) {
            LWJGLUtil.log("Unable to create from inputstream, " + e.getMessage());
            return null;
        }
    }

    public static WaveData create(byte[] buffer) {
        try {
            return WaveData.create(AudioSystem.getAudioInputStream(new BufferedInputStream(new ByteArrayInputStream(buffer))));
        }
        catch (Exception e) {
            LWJGLUtil.log("Unable to create from byte array, " + e.getMessage());
            return null;
        }
    }

    public static WaveData create(ByteBuffer buffer) {
        try {
            byte[] bytes = null;
            if (buffer.hasArray()) {
                bytes = buffer.array();
            } else {
                bytes = new byte[buffer.capacity()];
                buffer.get(bytes);
            }
            return WaveData.create(bytes);
        }
        catch (Exception e) {
            LWJGLUtil.log("Unable to create from ByteBuffer, " + e.getMessage());
            return null;
        }
    }

    public static WaveData create(AudioInputStream ais) {
        AudioFormat audioformat = ais.getFormat();
        int channels = 0;
        if (audioformat.getChannels() == 1) {
            if (audioformat.getSampleSizeInBits() == 8) {
                channels = 4352;
            } else if (audioformat.getSampleSizeInBits() == 16) {
                channels = 4353;
            } else assert (false) : "Illegal sample size";
        } else if (audioformat.getChannels() == 2) {
            if (audioformat.getSampleSizeInBits() == 8) {
                channels = 4354;
            } else if (audioformat.getSampleSizeInBits() == 16) {
                channels = 4355;
            } else assert (false) : "Illegal sample size";
        } else assert (false) : "Only mono or stereo is supported";
        ByteBuffer buffer = null;
        try {
            int available = ais.available();
            if (available <= 0) {
                available = ais.getFormat().getChannels() * (int)ais.getFrameLength() * ais.getFormat().getSampleSizeInBits() / 8;
            }
            byte[] buf = new byte[ais.available()];
            int read = 0;
            for (int total = 0; (read = ais.read(buf, total, buf.length - total)) != -1 && total < buf.length; total += read) {
            }
            buffer = WaveData.convertAudioBytes(buf, audioformat.getSampleSizeInBits() == 16, audioformat.isBigEndian() ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        }
        catch (IOException ioe) {
            return null;
        }
        WaveData wavedata = new WaveData(buffer, channels, (int)audioformat.getSampleRate());
        try {
            ais.close();
        }
        catch (IOException ioe) {
            // empty catch block
        }
        return wavedata;
    }

    private static ByteBuffer convertAudioBytes(byte[] audio_bytes, boolean two_bytes_data, ByteOrder order) {
        ByteBuffer dest = ByteBuffer.allocateDirect(audio_bytes.length);
        dest.order(ByteOrder.nativeOrder());
        ByteBuffer src = ByteBuffer.wrap(audio_bytes);
        src.order(order);
        if (two_bytes_data) {
            ShortBuffer dest_short = dest.asShortBuffer();
            ShortBuffer src_short = src.asShortBuffer();
            while (src_short.hasRemaining()) {
                dest_short.put(src_short.get());
            }
        } else {
            while (src.hasRemaining()) {
                dest.put(src.get());
            }
        }
        dest.rewind();
        return dest;
    }
}

