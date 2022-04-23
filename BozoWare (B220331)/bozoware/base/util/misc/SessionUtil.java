// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.util.misc;

public class SessionUtil
{
    static int bruh;
    
    public static long getCurrentTime() {
        return System.currentTimeMillis() - SessionUtil.bruh;
    }
    
    public void reset() {
        SessionUtil.bruh = (int)System.currentTimeMillis();
    }
    
    static {
        SessionUtil.bruh = (int)System.currentTimeMillis();
    }
}
