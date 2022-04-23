/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.profiler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Profiler {
    private static final Logger logger = LogManager.getLogger();
    private final List<String> sectionList = Lists.newArrayList();
    private final List<Long> timestampList = Lists.newArrayList();
    public boolean profilingEnabled;
    private String profilingSection = "";
    private final Map<String, Long> profilingMap = Maps.newHashMap();

    public void clearProfiling() {
        this.profilingMap.clear();
        this.profilingSection = "";
        this.sectionList.clear();
    }

    public void startSection(String p_startSection_1_) {
        if (this.profilingEnabled) {
            if (this.profilingSection.length() > 0) {
                this.profilingSection = this.profilingSection + ".";
            }
            this.profilingSection = this.profilingSection + p_startSection_1_;
            this.sectionList.add(this.profilingSection);
            this.timestampList.add(System.nanoTime());
        }
    }

    public void endSection() {
        if (this.profilingEnabled) {
            long lvt_1_1_ = System.nanoTime();
            long lvt_3_1_ = this.timestampList.remove(this.timestampList.size() - 1);
            this.sectionList.remove(this.sectionList.size() - 1);
            long lvt_5_1_ = lvt_1_1_ - lvt_3_1_;
            if (this.profilingMap.containsKey(this.profilingSection)) {
                this.profilingMap.put(this.profilingSection, this.profilingMap.get(this.profilingSection) + lvt_5_1_);
            } else {
                this.profilingMap.put(this.profilingSection, lvt_5_1_);
            }
            if (lvt_5_1_ > 100000000L) {
                logger.warn("Something's taking too long! '" + this.profilingSection + "' took aprox " + (double)lvt_5_1_ / 1000000.0 + " ms");
            }
            this.profilingSection = !this.sectionList.isEmpty() ? this.sectionList.get(this.sectionList.size() - 1) : "";
        }
    }

    public List<Result> getProfilingData(String p_getProfilingData_1_) {
        if (!this.profilingEnabled) {
            return null;
        }
        long lvt_3_1_ = this.profilingMap.containsKey("root") ? this.profilingMap.get("root") : 0L;
        long lvt_5_1_ = this.profilingMap.containsKey(p_getProfilingData_1_) ? this.profilingMap.get(p_getProfilingData_1_) : -1L;
        ArrayList<Result> lvt_7_1_ = Lists.newArrayList();
        if (p_getProfilingData_1_.length() > 0) {
            p_getProfilingData_1_ = p_getProfilingData_1_ + ".";
        }
        long lvt_8_1_ = 0L;
        for (String lvt_11_1_ : this.profilingMap.keySet()) {
            if (lvt_11_1_.length() <= p_getProfilingData_1_.length() || !lvt_11_1_.startsWith(p_getProfilingData_1_) || lvt_11_1_.indexOf(".", p_getProfilingData_1_.length() + 1) >= 0) continue;
            lvt_8_1_ += this.profilingMap.get(lvt_11_1_).longValue();
        }
        float lvt_10_2_ = lvt_8_1_;
        if (lvt_8_1_ < lvt_5_1_) {
            lvt_8_1_ = lvt_5_1_;
        }
        if (lvt_3_1_ < lvt_8_1_) {
            lvt_3_1_ = lvt_8_1_;
        }
        for (String lvt_12_2_ : this.profilingMap.keySet()) {
            if (lvt_12_2_.length() <= p_getProfilingData_1_.length() || !lvt_12_2_.startsWith(p_getProfilingData_1_) || lvt_12_2_.indexOf(".", p_getProfilingData_1_.length() + 1) >= 0) continue;
            long lvt_13_1_ = this.profilingMap.get(lvt_12_2_);
            double lvt_15_1_ = (double)lvt_13_1_ * 100.0 / (double)lvt_8_1_;
            double lvt_17_1_ = (double)lvt_13_1_ * 100.0 / (double)lvt_3_1_;
            String lvt_19_1_ = lvt_12_2_.substring(p_getProfilingData_1_.length());
            lvt_7_1_.add(new Result(lvt_19_1_, lvt_15_1_, lvt_17_1_));
        }
        for (String lvt_12_2_ : this.profilingMap.keySet()) {
            this.profilingMap.put(lvt_12_2_, this.profilingMap.get(lvt_12_2_) * 999L / 1000L);
        }
        if ((float)lvt_8_1_ > lvt_10_2_) {
            lvt_7_1_.add(new Result("unspecified", (double)((float)lvt_8_1_ - lvt_10_2_) * 100.0 / (double)lvt_8_1_, (double)((float)lvt_8_1_ - lvt_10_2_) * 100.0 / (double)lvt_3_1_));
        }
        Collections.sort(lvt_7_1_);
        lvt_7_1_.add(0, new Result(p_getProfilingData_1_, 100.0, (double)lvt_8_1_ * 100.0 / (double)lvt_3_1_));
        return lvt_7_1_;
    }

    public void endStartSection(String p_endStartSection_1_) {
        this.endSection();
        this.startSection(p_endStartSection_1_);
    }

    public String getNameOfLastSection() {
        return this.sectionList.size() == 0 ? "[UNKNOWN]" : this.sectionList.get(this.sectionList.size() - 1);
    }

    public static final class Result
    implements Comparable<Result> {
        public double field_76332_a;
        public double field_76330_b;
        public String field_76331_c;

        public Result(String p_i1554_1_, double p_i1554_2_, double p_i1554_4_) {
            this.field_76331_c = p_i1554_1_;
            this.field_76332_a = p_i1554_2_;
            this.field_76330_b = p_i1554_4_;
        }

        @Override
        public int compareTo(Result p_compareTo_1_) {
            if (p_compareTo_1_.field_76332_a < this.field_76332_a) {
                return -1;
            }
            return p_compareTo_1_.field_76332_a > this.field_76332_a ? 1 : p_compareTo_1_.field_76331_c.compareTo(this.field_76331_c);
        }

        public int getColor() {
            return (this.field_76331_c.hashCode() & 0xAAAAAA) + 0x444444;
        }
    }
}

