package io.netty.handler.codec.spdy;

import io.netty.util.internal.StringUtil;

public class DefaultSpdyGoAwayFrame implements SpdyGoAwayFrame {
  private int lastGoodStreamId;
  
  private SpdySessionStatus status;
  
  public DefaultSpdyGoAwayFrame(int lastGoodStreamId) {
    this(lastGoodStreamId, 0);
  }
  
  public DefaultSpdyGoAwayFrame(int lastGoodStreamId, int statusCode) {
    this(lastGoodStreamId, SpdySessionStatus.valueOf(statusCode));
  }
  
  public DefaultSpdyGoAwayFrame(int lastGoodStreamId, SpdySessionStatus status) {
    setLastGoodStreamId(lastGoodStreamId);
    setStatus(status);
  }
  
  public int lastGoodStreamId() {
    return this.lastGoodStreamId;
  }
  
  public SpdyGoAwayFrame setLastGoodStreamId(int lastGoodStreamId) {
    if (lastGoodStreamId < 0)
      throw new IllegalArgumentException("Last-good-stream-ID cannot be negative: " + lastGoodStreamId); 
    this.lastGoodStreamId = lastGoodStreamId;
    return this;
  }
  
  public SpdySessionStatus status() {
    return this.status;
  }
  
  public SpdyGoAwayFrame setStatus(SpdySessionStatus status) {
    this.status = status;
    return this;
  }
  
  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append(StringUtil.simpleClassName(this));
    buf.append(StringUtil.NEWLINE);
    buf.append("--> Last-good-stream-ID = ");
    buf.append(lastGoodStreamId());
    buf.append(StringUtil.NEWLINE);
    buf.append("--> Status: ");
    buf.append(status());
    return buf.toString();
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\io\netty\handler\codec\spdy\DefaultSpdyGoAwayFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */