package org.newdawn.slick.font.effects;

import java.util.*;

public interface ConfigurableEffect extends Effect
{
    List getValues();
    
    void setValues(final List p0);
    
    public interface Value
    {
        String getName();
        
        void setString(final String p0);
        
        String getString();
        
        Object getObject();
        
        void showDialog();
    }
}
