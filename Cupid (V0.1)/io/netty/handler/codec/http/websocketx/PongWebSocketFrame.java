package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCounted;

public class PongWebSocketFrame extends WebSocketFrame {
  public PongWebSocketFrame() {
    super(Unpooled.buffer(0));
  }
  
  public PongWebSocketFrame(ByteBuf binaryData) {
    super(binaryData);
  }
  
  public PongWebSocketFrame(boolean finalFragment, int rsv, ByteBuf binaryData) {
    super(finalFragment, rsv, binaryData);
  }
  
  public PongWebSocketFrame copy() {
    return new PongWebSocketFrame(isFinalFragment(), rsv(), content().copy());
  }
  
  public PongWebSocketFrame duplicate() {
    return new PongWebSocketFrame(isFinalFragment(), rsv(), content().duplicate());
  }
  
  public PongWebSocketFrame retain() {
    super.retain();
    return this;
  }
  
  public PongWebSocketFrame retain(int increment) {
    super.retain(increment);
    return this;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\io\netty\handler\codec\http\websocketx\PongWebSocketFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */