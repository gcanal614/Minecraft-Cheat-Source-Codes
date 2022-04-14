package org.newdawn.slick.opengl;

import java.io.*;
import java.util.*;

public class CompositeIOException extends IOException
{
    private ArrayList exceptions;
    
    public CompositeIOException() {
        this.exceptions = new ArrayList();
    }
    
    public void addException(final Exception e) {
        this.exceptions.add(e);
    }
    
    @Override
    public String getMessage() {
        String msg = "Composite Exception: \n";
        for (int i = 0; i < this.exceptions.size(); ++i) {
            msg = msg + "\t" + this.exceptions.get(i).getMessage() + "\n";
        }
        return msg;
    }
}
