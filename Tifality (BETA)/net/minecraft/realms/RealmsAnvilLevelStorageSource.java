/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  atq
 *  atr
 *  ats
 *  nu
 */
package net.minecraft.realms;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.realms.RealmsLevelSummary;

public class RealmsAnvilLevelStorageSource {
    private atr levelStorageSource;

    public RealmsAnvilLevelStorageSource(atr atr2) {
        this.levelStorageSource = atr2;
    }

    public String getName() {
        return this.levelStorageSource.a();
    }

    public boolean levelExists(String string) {
        return this.levelStorageSource.f(string);
    }

    public boolean convertLevel(String string, nu nu2) {
        return this.levelStorageSource.a(string, nu2);
    }

    public boolean requiresConversion(String string) {
        return this.levelStorageSource.b(string);
    }

    public boolean isNewLevelIdAcceptable(String string) {
        return this.levelStorageSource.d(string);
    }

    public boolean deleteLevel(String string) {
        return this.levelStorageSource.e(string);
    }

    public boolean isConvertible(String string) {
        return this.levelStorageSource.a(string);
    }

    public void renameLevel(String string, String string2) {
        this.levelStorageSource.a(string, string2);
    }

    public void clearAll() {
        this.levelStorageSource.d();
    }

    public List<RealmsLevelSummary> getLevelList() throws atq {
        ArrayList<RealmsLevelSummary> arrayList = Lists.newArrayList();
        for (ats ats2 : this.levelStorageSource.b()) {
            arrayList.add(new RealmsLevelSummary(ats2));
        }
        return arrayList;
    }
}

