/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.math;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.math.MathPreconditions;
import java.math.RoundingMode;

@GwtCompatible(emulated=true)
public final class IntMath {
    @VisibleForTesting
    static final int MAX_POWER_OF_SQRT2_UNSIGNED = -1257966797;
    @VisibleForTesting
    static final byte[] maxLog10ForLeadingZeros = new byte[]{9, 9, 9, 8, 8, 8, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0, 0, 0, 0};
    @VisibleForTesting
    static final int[] powersOf10 = new int[]{1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
    @VisibleForTesting
    static final int[] halfPowersOf10 = new int[]{3, 31, 316, 3162, 31622, 316227, 3162277, 31622776, 316227766, Integer.MAX_VALUE};
    @VisibleForTesting
    static final int FLOOR_SQRT_MAX_INT = 46340;
    private static final int[] factorials = new int[]{1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600};
    @VisibleForTesting
    static int[] biggestBinomials = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, 65536, 2345, 477, 193, 110, 75, 58, 49, 43, 39, 37, 35, 34, 34, 33};

    public static boolean isPowerOfTwo(int x) {
        return x > 0 & (x & x - 1) == 0;
    }

    @VisibleForTesting
    static int lessThanBranchFree(int x, int y) {
        return ~(~(x - y)) >>> 31;
    }

    public static int log2(int x, RoundingMode mode) {
        MathPreconditions.checkPositive("x", x);
        switch (mode) {
            case UNNECESSARY: {
                MathPreconditions.checkRoundingUnnecessary(IntMath.isPowerOfTwo(x));
            }
            case DOWN: 
            case FLOOR: {
                return 31 - Integer.numberOfLeadingZeros(x);
            }
            case UP: 
            case CEILING: {
                return 32 - Integer.numberOfLeadingZeros(x - 1);
            }
            case HALF_DOWN: 
            case HALF_UP: 
            case HALF_EVEN: {
                int leadingZeros = Integer.numberOfLeadingZeros(x);
                int cmp = -1257966797 >>> leadingZeros;
                int logFloor = 31 - leadingZeros;
                return logFloor + IntMath.lessThanBranchFree(cmp, x);
            }
        }
        throw new AssertionError();
    }

    @GwtIncompatible(value="need BigIntegerMath to adequately test")
    public static int log10(int x, RoundingMode mode) {
        MathPreconditions.checkPositive("x", x);
        int logFloor = IntMath.log10Floor(x);
        int floorPow = powersOf10[logFloor];
        switch (mode) {
            case UNNECESSARY: {
                MathPreconditions.checkRoundingUnnecessary(x == floorPow);
            }
            case DOWN: 
            case FLOOR: {
                return logFloor;
            }
            case UP: 
            case CEILING: {
                return logFloor + IntMath.lessThanBranchFree(floorPow, x);
            }
            case HALF_DOWN: 
            case HALF_UP: 
            case HALF_EVEN: {
                return logFloor + IntMath.lessThanBranchFree(halfPowersOf10[logFloor], x);
            }
        }
        throw new AssertionError();
    }

    private static int log10Floor(int x) {
        byte y = maxLog10ForLeadingZeros[Integer.numberOfLeadingZeros(x)];
        return y - IntMath.lessThanBranchFree(x, powersOf10[y]);
    }

    @GwtIncompatible(value="failing tests")
    public static int pow(int b2, int k) {
        MathPreconditions.checkNonNegative("exponent", k);
        switch (b2) {
            case 0: {
                return k == 0 ? 1 : 0;
            }
            case 1: {
                return 1;
            }
            case -1: {
                return (k & 1) == 0 ? 1 : -1;
            }
            case 2: {
                return k < 32 ? 1 << k : 0;
            }
            case -2: {
                if (k < 32) {
                    return (k & 1) == 0 ? 1 << k : -(1 << k);
                }
                return 0;
            }
        }
        int accum = 1;
        while (true) {
            switch (k) {
                case 0: {
                    return accum;
                }
                case 1: {
                    return b2 * accum;
                }
            }
            accum *= (k & 1) == 0 ? 1 : b2;
            b2 *= b2;
            k >>= 1;
        }
    }

    @GwtIncompatible(value="need BigIntegerMath to adequately test")
    public static int sqrt(int x, RoundingMode mode) {
        MathPreconditions.checkNonNegative("x", x);
        int sqrtFloor = IntMath.sqrtFloor(x);
        switch (mode) {
            case UNNECESSARY: {
                MathPreconditions.checkRoundingUnnecessary(sqrtFloor * sqrtFloor == x);
            }
            case DOWN: 
            case FLOOR: {
                return sqrtFloor;
            }
            case UP: 
            case CEILING: {
                return sqrtFloor + IntMath.lessThanBranchFree(sqrtFloor * sqrtFloor, x);
            }
            case HALF_DOWN: 
            case HALF_UP: 
            case HALF_EVEN: {
                int halfSquare = sqrtFloor * sqrtFloor + sqrtFloor;
                return sqrtFloor + IntMath.lessThanBranchFree(halfSquare, x);
            }
        }
        throw new AssertionError();
    }

    private static int sqrtFloor(int x) {
        return (int)Math.sqrt(x);
    }

    public static int divide(int p, int q, RoundingMode mode) {
        boolean increment;
        Preconditions.checkNotNull(mode);
        if (q == 0) {
            throw new ArithmeticException("/ by zero");
        }
        int div = p / q;
        int rem = p - q * div;
        if (rem == 0) {
            return div;
        }
        int signum = 1 | (p ^ q) >> 31;
        switch (mode) {
            case UNNECESSARY: {
                MathPreconditions.checkRoundingUnnecessary(rem == 0);
            }
            case DOWN: {
                increment = false;
                break;
            }
            case UP: {
                increment = true;
                break;
            }
            case CEILING: {
                increment = signum > 0;
                break;
            }
            case FLOOR: {
                increment = signum < 0;
                break;
            }
            case HALF_DOWN: 
            case HALF_UP: 
            case HALF_EVEN: {
                int absRem = Math.abs(rem);
                int cmpRemToHalfDivisor = absRem - (Math.abs(q) - absRem);
                if (cmpRemToHalfDivisor == 0) {
                    increment = mode == RoundingMode.HALF_UP || mode == RoundingMode.HALF_EVEN & (div & 1) != 0;
                    break;
                }
                increment = cmpRemToHalfDivisor > 0;
                break;
            }
            default: {
                throw new AssertionError();
            }
        }
        return increment ? div + signum : div;
    }

    public static int mod(int x, int m) {
        if (m <= 0) {
            throw new ArithmeticException("Modulus " + m + " must be > 0");
        }
        int result2 = x % m;
        return result2 >= 0 ? result2 : result2 + m;
    }

    public static int gcd(int a2, int b2) {
        MathPreconditions.checkNonNegative("a", a2);
        MathPreconditions.checkNonNegative("b", b2);
        if (a2 == 0) {
            return b2;
        }
        if (b2 == 0) {
            return a2;
        }
        int aTwos = Integer.numberOfTrailingZeros(a2);
        a2 >>= aTwos;
        int bTwos = Integer.numberOfTrailingZeros(b2);
        b2 >>= bTwos;
        while (a2 != b2) {
            int delta = a2 - b2;
            int minDeltaOrZero = delta & delta >> 31;
            a2 = delta - minDeltaOrZero - minDeltaOrZero;
            b2 += minDeltaOrZero;
            a2 >>= Integer.numberOfTrailingZeros(a2);
        }
        return a2 << Math.min(aTwos, bTwos);
    }

    public static int checkedAdd(int a2, int b2) {
        long result2 = (long)a2 + (long)b2;
        MathPreconditions.checkNoOverflow(result2 == (long)((int)result2));
        return (int)result2;
    }

    public static int checkedSubtract(int a2, int b2) {
        long result2 = (long)a2 - (long)b2;
        MathPreconditions.checkNoOverflow(result2 == (long)((int)result2));
        return (int)result2;
    }

    public static int checkedMultiply(int a2, int b2) {
        long result2 = (long)a2 * (long)b2;
        MathPreconditions.checkNoOverflow(result2 == (long)((int)result2));
        return (int)result2;
    }

    public static int checkedPow(int b2, int k) {
        MathPreconditions.checkNonNegative("exponent", k);
        switch (b2) {
            case 0: {
                return k == 0 ? 1 : 0;
            }
            case 1: {
                return 1;
            }
            case -1: {
                return (k & 1) == 0 ? 1 : -1;
            }
            case 2: {
                MathPreconditions.checkNoOverflow(k < 31);
                return 1 << k;
            }
            case -2: {
                MathPreconditions.checkNoOverflow(k < 32);
                return (k & 1) == 0 ? 1 << k : -1 << k;
            }
        }
        int accum = 1;
        while (true) {
            switch (k) {
                case 0: {
                    return accum;
                }
                case 1: {
                    return IntMath.checkedMultiply(accum, b2);
                }
            }
            if ((k & 1) != 0) {
                accum = IntMath.checkedMultiply(accum, b2);
            }
            if ((k >>= 1) <= 0) continue;
            MathPreconditions.checkNoOverflow(-46340 <= b2 & b2 <= 46340);
            b2 *= b2;
        }
    }

    public static int factorial(int n) {
        MathPreconditions.checkNonNegative("n", n);
        return n < factorials.length ? factorials[n] : Integer.MAX_VALUE;
    }

    @GwtIncompatible(value="need BigIntegerMath to adequately test")
    public static int binomial(int n, int k) {
        MathPreconditions.checkNonNegative("n", n);
        MathPreconditions.checkNonNegative("k", k);
        Preconditions.checkArgument(k <= n, "k (%s) > n (%s)", k, n);
        if (k > n >> 1) {
            k = n - k;
        }
        if (k >= biggestBinomials.length || n > biggestBinomials[k]) {
            return Integer.MAX_VALUE;
        }
        switch (k) {
            case 0: {
                return 1;
            }
            case 1: {
                return n;
            }
        }
        long result2 = 1L;
        for (int i = 0; i < k; ++i) {
            result2 *= (long)(n - i);
            result2 /= (long)(i + 1);
        }
        return (int)result2;
    }

    public static int mean(int x, int y) {
        return (x & y) + ((x ^ y) >> 1);
    }

    private IntMath() {
    }
}

