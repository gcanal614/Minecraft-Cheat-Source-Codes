package org.newdawn.slick.util;

import java.net.*;
import java.io.*;

public class ClasspathLocation implements ResourceLocation
{
    @Override
    public URL getResource(final String ref) {
        final String cpRef = ref.replace('\\', '/');
        return ResourceLoader.class.getClassLoader().getResource(cpRef);
    }
    
    @Override
    public InputStream getResourceAsStream(final String ref) {
        final String cpRef = ref.replace('\\', '/');
        return ResourceLoader.class.getClassLoader().getResourceAsStream(cpRef);
    }
}
