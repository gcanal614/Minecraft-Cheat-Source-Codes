/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world;

public enum EnumDifficulty {
    PEACEFUL(0, "options.difficulty.peaceful"),
    EASY(1, "options.difficulty.easy"),
    NORMAL(2, "options.difficulty.normal"),
    HARD(3, "options.difficulty.hard");

    private static final EnumDifficulty[] difficultyEnums;
    private final int difficultyId;
    private final String difficultyResourceKey;

    static {
        difficultyEnums = new EnumDifficulty[EnumDifficulty.values().length];
        EnumDifficulty[] enumDifficultyArray = EnumDifficulty.values();
        int n = enumDifficultyArray.length;
        int n2 = 0;
        while (n2 < n) {
            EnumDifficulty enumdifficulty;
            EnumDifficulty.difficultyEnums[enumdifficulty.difficultyId] = enumdifficulty = enumDifficultyArray[n2];
            ++n2;
        }
    }

    private EnumDifficulty(int difficultyIdIn, String difficultyResourceKeyIn) {
        this.difficultyId = difficultyIdIn;
        this.difficultyResourceKey = difficultyResourceKeyIn;
    }

    public int getDifficultyId() {
        return this.difficultyId;
    }

    public static EnumDifficulty getDifficultyEnum(int p_151523_0_) {
        return difficultyEnums[p_151523_0_ % difficultyEnums.length];
    }

    public String getDifficultyResourceKey() {
        return this.difficultyResourceKey;
    }
}

