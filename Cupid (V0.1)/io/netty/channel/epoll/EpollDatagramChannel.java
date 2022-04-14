package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.channel.AbstractChannel;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultAddressedEnvelope;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramChannelConfig;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;

public final class EpollDatagramChannel extends AbstractEpollChannel implements DatagramChannel {
  private static final ChannelMetadata METADATA = new ChannelMetadata(true);
  
  private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(DatagramPacket.class) + ", " + StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(InetSocketAddress.class) + ">, " + StringUtil.simpleClassName(ByteBuf.class) + ')';
  
  private volatile InetSocketAddress local;
  
  private volatile InetSocketAddress remote;
  
  private volatile boolean connected;
  
  private final EpollDatagramChannelConfig config;
  
  public EpollDatagramChannel() {
    super(Native.socketDgramFd(), 1);
    this.config = new EpollDatagramChannelConfig(this);
  }
  
  public ChannelMetadata metadata() {
    return METADATA;
  }
  
  public boolean isActive() {
    return (this.fd != -1 && ((((Boolean)this.config.<Boolean>getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION)).booleanValue() && isRegistered()) || this.active));
  }
  
  public boolean isConnected() {
    return this.connected;
  }
  
  public ChannelFuture joinGroup(InetAddress multicastAddress) {
    return joinGroup(multicastAddress, newPromise());
  }
  
  public ChannelFuture joinGroup(InetAddress multicastAddress, ChannelPromise promise) {
    try {
      return joinGroup(multicastAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), (InetAddress)null, promise);
    } catch (SocketException e) {
      promise.setFailure(e);
      return (ChannelFuture)promise;
    } 
  }
  
  public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
    return joinGroup(multicastAddress, networkInterface, newPromise());
  }
  
  public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
    return joinGroup(multicastAddress.getAddress(), networkInterface, (InetAddress)null, promise);
  }
  
  public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
    return joinGroup(multicastAddress, networkInterface, source, newPromise());
  }
  
  public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
    if (multicastAddress == null)
      throw new NullPointerException("multicastAddress"); 
    if (networkInterface == null)
      throw new NullPointerException("networkInterface"); 
    promise.setFailure(new UnsupportedOperationException("Multicast not supported"));
    return (ChannelFuture)promise;
  }
  
  public ChannelFuture leaveGroup(InetAddress multicastAddress) {
    return leaveGroup(multicastAddress, newPromise());
  }
  
  public ChannelFuture leaveGroup(InetAddress multicastAddress, ChannelPromise promise) {
    try {
      return leaveGroup(multicastAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), (InetAddress)null, promise);
    } catch (SocketException e) {
      promise.setFailure(e);
      return (ChannelFuture)promise;
    } 
  }
  
  public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
    return leaveGroup(multicastAddress, networkInterface, newPromise());
  }
  
  public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
    return leaveGroup(multicastAddress.getAddress(), networkInterface, (InetAddress)null, promise);
  }
  
  public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
    return leaveGroup(multicastAddress, networkInterface, source, newPromise());
  }
  
  public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
    if (multicastAddress == null)
      throw new NullPointerException("multicastAddress"); 
    if (networkInterface == null)
      throw new NullPointerException("networkInterface"); 
    promise.setFailure(new UnsupportedOperationException("Multicast not supported"));
    return (ChannelFuture)promise;
  }
  
  public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock) {
    return block(multicastAddress, networkInterface, sourceToBlock, newPromise());
  }
  
  public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock, ChannelPromise promise) {
    if (multicastAddress == null)
      throw new NullPointerException("multicastAddress"); 
    if (sourceToBlock == null)
      throw new NullPointerException("sourceToBlock"); 
    if (networkInterface == null)
      throw new NullPointerException("networkInterface"); 
    promise.setFailure(new UnsupportedOperationException("Multicast not supported"));
    return (ChannelFuture)promise;
  }
  
  public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock) {
    return block(multicastAddress, sourceToBlock, newPromise());
  }
  
  public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock, ChannelPromise promise) {
    try {
      return block(multicastAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), sourceToBlock, promise);
    } catch (Throwable e) {
      promise.setFailure(e);
      return (ChannelFuture)promise;
    } 
  }
  
  protected AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
    return new EpollDatagramChannelUnsafe();
  }
  
  protected InetSocketAddress localAddress0() {
    return this.local;
  }
  
  protected InetSocketAddress remoteAddress0() {
    return this.remote;
  }
  
  protected void doBind(SocketAddress localAddress) throws Exception {
    InetSocketAddress addr = (InetSocketAddress)localAddress;
    checkResolvable(addr);
    Native.bind(this.fd, addr.getAddress(), addr.getPort());
    this.local = Native.localAddress(this.fd);
    this.active = true;
  }
  
  protected void doWrite(ChannelOutboundBuffer in) throws Exception {
    while (true) {
      Object msg = in.current();
      if (msg == null) {
        clearEpollOut();
        break;
      } 
      try {
        boolean done = false;
        for (int i = config().getWriteSpinCount() - 1; i >= 0; i--) {
          if (doWriteMessage(msg)) {
            done = true;
            break;
          } 
        } 
        if (done) {
          in.remove();
          continue;
        } 
        setEpollOut();
        break;
      } catch (IOException e) {
        in.remove(e);
      } 
    } 
  }
  
  private boolean doWriteMessage(Object msg) throws IOException {
    ByteBuf data;
    InetSocketAddress remoteAddress;
    int writtenBytes;
    if (msg instanceof AddressedEnvelope) {
      AddressedEnvelope<ByteBuf, InetSocketAddress> envelope = (AddressedEnvelope<ByteBuf, InetSocketAddress>)msg;
      data = (ByteBuf)envelope.content();
      remoteAddress = (InetSocketAddress)envelope.recipient();
    } else {
      data = (ByteBuf)msg;
      remoteAddress = null;
    } 
    int dataLen = data.readableBytes();
    if (dataLen == 0)
      return true; 
    if (remoteAddress == null) {
      remoteAddress = this.remote;
      if (remoteAddress == null)
        throw new NotYetConnectedException(); 
    } 
    if (data.hasMemoryAddress()) {
      long memoryAddress = data.memoryAddress();
      writtenBytes = Native.sendToAddress(this.fd, memoryAddress, data.readerIndex(), data.writerIndex(), remoteAddress.getAddress(), remoteAddress.getPort());
    } else {
      ByteBuffer nioData = data.internalNioBuffer(data.readerIndex(), data.readableBytes());
      writtenBytes = Native.sendTo(this.fd, nioData, nioData.position(), nioData.limit(), remoteAddress.getAddress(), remoteAddress.getPort());
    } 
    return (writtenBytes > 0);
  }
  
  protected Object filterOutboundMessage(Object msg) {
    if (msg instanceof DatagramPacket) {
      DatagramPacket packet = (DatagramPacket)msg;
      ByteBuf content = (ByteBuf)packet.content();
      if (content.hasMemoryAddress())
        return msg; 
      return new DatagramPacket(newDirectBuffer(packet, content), (InetSocketAddress)packet.recipient());
    } 
    if (msg instanceof ByteBuf) {
      ByteBuf buf = (ByteBuf)msg;
      if (buf.hasMemoryAddress())
        return msg; 
      return newDirectBuffer(buf);
    } 
    if (msg instanceof AddressedEnvelope) {
      AddressedEnvelope<Object, SocketAddress> e = (AddressedEnvelope<Object, SocketAddress>)msg;
      if (e.content() instanceof ByteBuf && (e.recipient() == null || e.recipient() instanceof InetSocketAddress)) {
        ByteBuf content = (ByteBuf)e.content();
        if (content.hasMemoryAddress())
          return e; 
        return new DefaultAddressedEnvelope(newDirectBuffer(e, content), e.recipient());
      } 
    } 
    throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
  }
  
  public EpollDatagramChannelConfig config() {
    return this.config;
  }
  
  protected void doDisconnect() throws Exception {
    this.connected = false;
  }
  
  final class EpollDatagramChannelUnsafe extends AbstractEpollChannel.AbstractEpollUnsafe {
    private RecvByteBufAllocator.Handle allocHandle;
    
    public void connect(SocketAddress remote, SocketAddress local, ChannelPromise channelPromise) {
      boolean success = false;
      try {
        try {
          InetSocketAddress remoteAddress = (InetSocketAddress)remote;
          if (local != null) {
            InetSocketAddress localAddress = (InetSocketAddress)local;
            EpollDatagramChannel.this.doBind(localAddress);
          } 
          AbstractEpollChannel.checkResolvable(remoteAddress);
          EpollDatagramChannel.this.remote = remoteAddress;
          EpollDatagramChannel.this.local = Native.localAddress(EpollDatagramChannel.this.fd);
          success = true;
        } finally {
          if (!success) {
            EpollDatagramChannel.this.doClose();
          } else {
            channelPromise.setSuccess();
            EpollDatagramChannel.this.connected = true;
          } 
        } 
      } catch (Throwable cause) {
        channelPromise.setFailure(cause);
      } 
    }
    
    void epollInReady() {
      DatagramChannelConfig config = EpollDatagramChannel.this.config();
      RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
      if (allocHandle == null)
        this.allocHandle = allocHandle = config.getRecvByteBufAllocator().newHandle(); 
      assert EpollDatagramChannel.this.eventLoop().inEventLoop();
      ChannelPipeline pipeline = EpollDatagramChannel.this.pipeline();
      try {
        while (true) {
          ByteBuf data = null;
          try {
            EpollDatagramChannel.DatagramSocketAddress remoteAddress;
            data = allocHandle.allocate(config.getAllocator());
            int writerIndex = data.writerIndex();
            if (data.hasMemoryAddress()) {
              remoteAddress = Native.recvFromAddress(EpollDatagramChannel.this.fd, data.memoryAddress(), writerIndex, data.capacity());
            } else {
              ByteBuffer nioData = data.internalNioBuffer(writerIndex, data.writableBytes());
              remoteAddress = Native.recvFrom(EpollDatagramChannel.this.fd, nioData, nioData.position(), nioData.limit());
            } 
            if (remoteAddress == null) {
              if (data != null)
                data.release(); 
              break;
            } 
            int readBytes = remoteAddress.receivedAmount;
            data.writerIndex(data.writerIndex() + readBytes);
            allocHandle.record(readBytes);
            this.readPending = false;
            pipeline.fireChannelRead(new DatagramPacket(data, (InetSocketAddress)localAddress(), remoteAddress));
            data = null;
          } catch (Throwable t) {
            pipeline.fireChannelReadComplete();
            pipeline.fireExceptionCaught(t);
          } finally {
            if (data != null)
              data.release(); 
          } 
        } 
      } finally {
        if (!EpollDatagramChannel.this.config().isAutoRead() && !this.readPending)
          EpollDatagramChannel.this.clearEpollIn(); 
      } 
    }
  }
  
  static final class DatagramSocketAddress extends InetSocketAddress {
    private static final long serialVersionUID = 1348596211215015739L;
    
    final int receivedAmount;
    
    DatagramSocketAddress(String addr, int port, int receivedAmount) {
      super(addr, port);
      this.receivedAmount = receivedAmount;
    }
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\io\netty\channel\epoll\EpollDatagramChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */