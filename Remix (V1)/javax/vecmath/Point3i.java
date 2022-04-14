package javax.vecmath;

import java.io.*;

public class Point3i extends Tuple3i implements Serializable
{
    static final long serialVersionUID = 6149289077348153921L;
    
    public Point3i(final int n, final int n2, final int n3) {
        super(n, n2, n3);
    }
    
    public Point3i(final int[] array) {
        super(array);
    }
    
    public Point3i(final Tuple3i tuple3i) {
        super(tuple3i);
    }
    
    public Point3i() {
    }
}
