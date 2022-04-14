package org.lwjgl.opengl;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.PointerBuffer;
import org.lwjgl.Sys;

public class AWTGLCanvas extends Canvas implements DrawableLWJGL, ComponentListener, HierarchyListener {
  private boolean first_run;
  
  private int reentry_count;
  
  private ContextGL context;
  
  private PeerInfo peer_info;
  
  private final ContextAttribs attribs;
  
  private final Drawable drawable;
  
  private final PixelFormat pixel_format;
  
  private Object SYNC_LOCK = new Object();
  
  private boolean update_context;
  
  static {
    Sys.initialize();
  }
  
  private static final AWTCanvasImplementation implementation = createImplementation();
  
  private static final long serialVersionUID = 1L;
  
  static AWTCanvasImplementation createImplementation() {
    switch (LWJGLUtil.getPlatform()) {
      case 1:
        return new LinuxCanvasImplementation();
      case 3:
        return new WindowsCanvasImplementation();
      case 2:
        return new MacOSXCanvasImplementation();
    } 
    throw new IllegalStateException("Unsupported platform");
  }
  
  private void setUpdate() {
    synchronized (this.SYNC_LOCK) {
      this.update_context = true;
    } 
  }
  
  public void setPixelFormat(PixelFormatLWJGL pf) throws LWJGLException {
    throw new UnsupportedOperationException();
  }
  
  public void setPixelFormat(PixelFormatLWJGL pf, ContextAttribs attribs) throws LWJGLException {
    throw new UnsupportedOperationException();
  }
  
  public PixelFormatLWJGL getPixelFormat() {
    return this.pixel_format;
  }
  
  public ContextGL getContext() {
    return this.context;
  }
  
  public ContextGL createSharedContext() throws LWJGLException {
    synchronized (this.SYNC_LOCK) {
      if (this.context == null)
        throw new IllegalStateException("Canvas not yet displayable"); 
      return new ContextGL(this.peer_info, this.context.getContextAttribs(), this.context);
    } 
  }
  
  public void checkGLError() {
    Util.checkGLError();
  }
  
  public void initContext(float r, float g, float b) {
    GL11.glClearColor(r, g, b, 0.0F);
    GL11.glClear(16384);
  }
  
  public AWTGLCanvas() throws LWJGLException {
    this(new PixelFormat());
  }
  
  public AWTGLCanvas(PixelFormat pixel_format) throws LWJGLException {
    this(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(), pixel_format);
  }
  
  public AWTGLCanvas(GraphicsDevice device, PixelFormat pixel_format) throws LWJGLException {
    this(device, pixel_format, (Drawable)null);
  }
  
  public AWTGLCanvas(GraphicsDevice device, PixelFormat pixel_format, Drawable drawable) throws LWJGLException {
    this(device, pixel_format, drawable, (ContextAttribs)null);
  }
  
  public AWTGLCanvas(GraphicsDevice device, PixelFormat pixel_format, Drawable drawable, ContextAttribs attribs) throws LWJGLException {
    super(implementation.findConfiguration(device, pixel_format));
    if (pixel_format == null)
      throw new NullPointerException("Pixel format must be non-null"); 
    addHierarchyListener(this);
    addComponentListener(this);
    this.drawable = drawable;
    this.pixel_format = pixel_format;
    this.attribs = attribs;
  }
  
  public void addNotify() {
    super.addNotify();
  }
  
  public void removeNotify() {
    synchronized (this.SYNC_LOCK) {
      destroy();
      super.removeNotify();
    } 
  }
  
  public void setSwapInterval(int swap_interval) {
    synchronized (this.SYNC_LOCK) {
      if (this.context == null)
        throw new IllegalStateException("Canvas not yet displayable"); 
      ContextGL.setSwapInterval(swap_interval);
    } 
  }
  
  public void setVSyncEnabled(boolean enabled) {
    setSwapInterval(enabled ? 1 : 0);
  }
  
  public void swapBuffers() throws LWJGLException {
    synchronized (this.SYNC_LOCK) {
      if (this.context == null)
        throw new IllegalStateException("Canvas not yet displayable"); 
      ContextGL.swapBuffers();
    } 
  }
  
  public boolean isCurrent() throws LWJGLException {
    synchronized (this.SYNC_LOCK) {
      if (this.context == null)
        throw new IllegalStateException("Canvas not yet displayable"); 
      return this.context.isCurrent();
    } 
  }
  
  public void makeCurrent() throws LWJGLException {
    synchronized (this.SYNC_LOCK) {
      if (this.context == null)
        throw new IllegalStateException("Canvas not yet displayable"); 
      this.context.makeCurrent();
    } 
  }
  
  public void releaseContext() throws LWJGLException {
    synchronized (this.SYNC_LOCK) {
      if (this.context == null)
        throw new IllegalStateException("Canvas not yet displayable"); 
      if (this.context.isCurrent())
        this.context.releaseCurrent(); 
    } 
  }
  
  public final void destroy() {
    synchronized (this.SYNC_LOCK) {
      try {
        if (this.context != null) {
          this.context.forceDestroy();
          this.context = null;
          this.reentry_count = 0;
          this.peer_info.destroy();
          this.peer_info = null;
        } 
      } catch (LWJGLException e) {
        throw new RuntimeException(e);
      } 
    } 
  }
  
  public final void setCLSharingProperties(PointerBuffer properties) throws LWJGLException {
    synchronized (this.SYNC_LOCK) {
      if (this.context == null)
        throw new IllegalStateException("Canvas not yet displayable"); 
      this.context.setCLSharingProperties(properties);
    } 
  }
  
  protected void initGL() {}
  
  protected void paintGL() {}
  
  public final void paint(Graphics g) {
    LWJGLException exception = null;
    synchronized (this.SYNC_LOCK) {
      if (!isDisplayable())
        return; 
      try {
        if (this.peer_info == null)
          this.peer_info = implementation.createPeerInfo(this, this.pixel_format, this.attribs); 
        this.peer_info.lockAndGetHandle();
        try {
          if (this.context == null) {
            this.context = new ContextGL(this.peer_info, this.attribs, (this.drawable != null) ? (ContextGL)((DrawableLWJGL)this.drawable).getContext() : null);
            this.first_run = true;
          } 
          if (this.reentry_count == 0)
            this.context.makeCurrent(); 
          this.reentry_count++;
          try {
            if (this.update_context) {
              this.context.update();
              this.update_context = false;
            } 
            if (this.first_run) {
              this.first_run = false;
              initGL();
            } 
            paintGL();
          } finally {
            this.reentry_count--;
            if (this.reentry_count == 0)
              this.context.releaseCurrent(); 
          } 
        } finally {
          this.peer_info.unlock();
        } 
      } catch (LWJGLException e) {
        exception = e;
      } 
    } 
    if (exception != null)
      exceptionOccurred(exception); 
  }
  
  protected void exceptionOccurred(LWJGLException exception) {
    LWJGLUtil.log("Unhandled exception occurred, skipping paint(): " + exception);
  }
  
  public void update(Graphics g) {
    paint(g);
  }
  
  public void componentShown(ComponentEvent e) {}
  
  public void componentHidden(ComponentEvent e) {}
  
  public void componentResized(ComponentEvent e) {
    setUpdate();
  }
  
  public void componentMoved(ComponentEvent e) {
    setUpdate();
  }
  
  public void setLocation(int x, int y) {
    super.setLocation(x, y);
    setUpdate();
  }
  
  public void setLocation(Point p) {
    super.setLocation(p);
    setUpdate();
  }
  
  public void setSize(Dimension d) {
    super.setSize(d);
    setUpdate();
  }
  
  public void setSize(int width, int height) {
    super.setSize(width, height);
    setUpdate();
  }
  
  public void setBounds(int x, int y, int width, int height) {
    super.setBounds(x, y, width, height);
    setUpdate();
  }
  
  public void hierarchyChanged(HierarchyEvent e) {
    setUpdate();
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\org\lwjgl\opengl\AWTGLCanvas.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */