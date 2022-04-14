package io.netty.channel.udt;

import com.barchart.udt.OptionUDT;
import com.barchart.udt.SocketUDT;
import com.barchart.udt.nio.ChannelUDT;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import java.io.IOException;
import java.util.Map;

public class DefaultUdtChannelConfig extends DefaultChannelConfig implements UdtChannelConfig {
  private static final int K = 1024;
  
  private static final int M = 1048576;
  
  private volatile int protocolReceiveBuferSize = 10485760;
  
  private volatile int protocolSendBuferSize = 10485760;
  
  private volatile int systemReceiveBufferSize = 1048576;
  
  private volatile int systemSendBuferSize = 1048576;
  
  private volatile int allocatorReceiveBufferSize = 131072;
  
  private volatile int allocatorSendBufferSize = 131072;
  
  private volatile int soLinger;
  
  private volatile boolean reuseAddress = true;
  
  public DefaultUdtChannelConfig(UdtChannel channel, ChannelUDT channelUDT, boolean apply) throws IOException {
    super(channel);
    if (apply)
      apply(channelUDT); 
  }
  
  protected void apply(ChannelUDT channelUDT) throws IOException {
    SocketUDT socketUDT = channelUDT.socketUDT();
    socketUDT.setReuseAddress(isReuseAddress());
    socketUDT.setSendBufferSize(getSendBufferSize());
    if (getSoLinger() <= 0) {
      socketUDT.setSoLinger(false, 0);
    } else {
      socketUDT.setSoLinger(true, getSoLinger());
    } 
    socketUDT.setOption(OptionUDT.Protocol_Receive_Buffer_Size, Integer.valueOf(getProtocolReceiveBufferSize()));
    socketUDT.setOption(OptionUDT.Protocol_Send_Buffer_Size, Integer.valueOf(getProtocolSendBufferSize()));
    socketUDT.setOption(OptionUDT.System_Receive_Buffer_Size, Integer.valueOf(getSystemReceiveBufferSize()));
    socketUDT.setOption(OptionUDT.System_Send_Buffer_Size, Integer.valueOf(getSystemSendBufferSize()));
  }
  
  public int getProtocolReceiveBufferSize() {
    return this.protocolReceiveBuferSize;
  }
  
  public <T> T getOption(ChannelOption<T> option) {
    if (option == UdtChannelOption.PROTOCOL_RECEIVE_BUFFER_SIZE)
      return (T)Integer.valueOf(getProtocolReceiveBufferSize()); 
    if (option == UdtChannelOption.PROTOCOL_SEND_BUFFER_SIZE)
      return (T)Integer.valueOf(getProtocolSendBufferSize()); 
    if (option == UdtChannelOption.SYSTEM_RECEIVE_BUFFER_SIZE)
      return (T)Integer.valueOf(getSystemReceiveBufferSize()); 
    if (option == UdtChannelOption.SYSTEM_SEND_BUFFER_SIZE)
      return (T)Integer.valueOf(getSystemSendBufferSize()); 
    if (option == UdtChannelOption.SO_RCVBUF)
      return (T)Integer.valueOf(getReceiveBufferSize()); 
    if (option == UdtChannelOption.SO_SNDBUF)
      return (T)Integer.valueOf(getSendBufferSize()); 
    if (option == UdtChannelOption.SO_REUSEADDR)
      return (T)Boolean.valueOf(isReuseAddress()); 
    if (option == UdtChannelOption.SO_LINGER)
      return (T)Integer.valueOf(getSoLinger()); 
    return (T)super.getOption(option);
  }
  
  public Map<ChannelOption<?>, Object> getOptions() {
    return getOptions(super.getOptions(), new ChannelOption[] { UdtChannelOption.PROTOCOL_RECEIVE_BUFFER_SIZE, UdtChannelOption.PROTOCOL_SEND_BUFFER_SIZE, UdtChannelOption.SYSTEM_RECEIVE_BUFFER_SIZE, UdtChannelOption.SYSTEM_SEND_BUFFER_SIZE, UdtChannelOption.SO_RCVBUF, UdtChannelOption.SO_SNDBUF, UdtChannelOption.SO_REUSEADDR, UdtChannelOption.SO_LINGER });
  }
  
  public int getReceiveBufferSize() {
    return this.allocatorReceiveBufferSize;
  }
  
  public int getSendBufferSize() {
    return this.allocatorSendBufferSize;
  }
  
  public int getSoLinger() {
    return this.soLinger;
  }
  
  public boolean isReuseAddress() {
    return this.reuseAddress;
  }
  
  public UdtChannelConfig setProtocolReceiveBufferSize(int protocolReceiveBuferSize) {
    this.protocolReceiveBuferSize = protocolReceiveBuferSize;
    return this;
  }
  
  public <T> boolean setOption(ChannelOption<T> option, T value) {
    validate(option, value);
    if (option == UdtChannelOption.PROTOCOL_RECEIVE_BUFFER_SIZE) {
      setProtocolReceiveBufferSize(((Integer)value).intValue());
    } else if (option == UdtChannelOption.PROTOCOL_SEND_BUFFER_SIZE) {
      setProtocolSendBufferSize(((Integer)value).intValue());
    } else if (option == UdtChannelOption.SYSTEM_RECEIVE_BUFFER_SIZE) {
      setSystemReceiveBufferSize(((Integer)value).intValue());
    } else if (option == UdtChannelOption.SYSTEM_SEND_BUFFER_SIZE) {
      setSystemSendBufferSize(((Integer)value).intValue());
    } else if (option == UdtChannelOption.SO_RCVBUF) {
      setReceiveBufferSize(((Integer)value).intValue());
    } else if (option == UdtChannelOption.SO_SNDBUF) {
      setSendBufferSize(((Integer)value).intValue());
    } else if (option == UdtChannelOption.SO_REUSEADDR) {
      setReuseAddress(((Boolean)value).booleanValue());
    } else if (option == UdtChannelOption.SO_LINGER) {
      setSoLinger(((Integer)value).intValue());
    } else {
      return super.setOption(option, value);
    } 
    return true;
  }
  
  public UdtChannelConfig setReceiveBufferSize(int receiveBufferSize) {
    this.allocatorReceiveBufferSize = receiveBufferSize;
    return this;
  }
  
  public UdtChannelConfig setReuseAddress(boolean reuseAddress) {
    this.reuseAddress = reuseAddress;
    return this;
  }
  
  public UdtChannelConfig setSendBufferSize(int sendBufferSize) {
    this.allocatorSendBufferSize = sendBufferSize;
    return this;
  }
  
  public UdtChannelConfig setSoLinger(int soLinger) {
    this.soLinger = soLinger;
    return this;
  }
  
  public int getSystemReceiveBufferSize() {
    return this.systemReceiveBufferSize;
  }
  
  public UdtChannelConfig setSystemSendBufferSize(int systemReceiveBufferSize) {
    this.systemReceiveBufferSize = systemReceiveBufferSize;
    return this;
  }
  
  public int getProtocolSendBufferSize() {
    return this.protocolSendBuferSize;
  }
  
  public UdtChannelConfig setProtocolSendBufferSize(int protocolSendBuferSize) {
    this.protocolSendBuferSize = protocolSendBuferSize;
    return this;
  }
  
  public UdtChannelConfig setSystemReceiveBufferSize(int systemSendBuferSize) {
    this.systemSendBuferSize = systemSendBuferSize;
    return this;
  }
  
  public int getSystemSendBufferSize() {
    return this.systemSendBuferSize;
  }
  
  public UdtChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
    super.setConnectTimeoutMillis(connectTimeoutMillis);
    return this;
  }
  
  public UdtChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
    super.setMaxMessagesPerRead(maxMessagesPerRead);
    return this;
  }
  
  public UdtChannelConfig setWriteSpinCount(int writeSpinCount) {
    super.setWriteSpinCount(writeSpinCount);
    return this;
  }
  
  public UdtChannelConfig setAllocator(ByteBufAllocator allocator) {
    super.setAllocator(allocator);
    return this;
  }
  
  public UdtChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
    super.setRecvByteBufAllocator(allocator);
    return this;
  }
  
  public UdtChannelConfig setAutoRead(boolean autoRead) {
    super.setAutoRead(autoRead);
    return this;
  }
  
  public UdtChannelConfig setAutoClose(boolean autoClose) {
    super.setAutoClose(autoClose);
    return this;
  }
  
  public UdtChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
    super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
    return this;
  }
  
  public UdtChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
    super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
    return this;
  }
  
  public UdtChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
    super.setMessageSizeEstimator(estimator);
    return this;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\io\netty\channe\\udt\DefaultUdtChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */