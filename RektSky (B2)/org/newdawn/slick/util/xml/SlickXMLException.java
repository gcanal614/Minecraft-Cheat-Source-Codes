package org.newdawn.slick.util.xml;

import org.newdawn.slick.*;

public class SlickXMLException extends SlickException
{
    public SlickXMLException(final String message) {
        super(message);
    }
    
    public SlickXMLException(final String message, final Throwable e) {
        super(message, e);
    }
}
