package com.slimbloody.chapter3.imEp.protocol;

import com.slimbloody.horse.chapter3.imEp.message.LoginRequestMessage;
import com.slimbloody.horse.chapter3.imEp.protocol.MessageCodec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

// 线程安全的handler会加@Sharable,像LoggingHandler这种是无状态的,所以不怕
// 解码器都不能sharable
@Slf4j
public class TestEp1 {
  public static void main(String[] args) throws Exception {
    EmbeddedChannel channel = new EmbeddedChannel(
      // 解决半包粘包
      // 因为是自己去取了魔数一类的数据,所以不需要跳过了
      new LengthFieldBasedFrameDecoder(
        1024,
        12,
        4,
        0,
        0
      ),
      //
      new LoggingHandler(LogLevel.DEBUG),
      new MessageCodec()
    );
    LoginRequestMessage loginReq = new LoginRequestMessage(
      "slim",
      "123"
    );
    
    // encode
    channel.writeOutbound();
    
    // decode
    ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
    new MessageCodec().encode(null, loginReq, buf);
    buf.retain();
    buf.markReaderIndex();
    channel.writeInbound(buf);
    
    // todo: byteBuf 读指针放到特定位置
    // 读完了要重置读指针
    // 验证粘包半包
    buf.retain();
    buf.resetReaderIndex();
    ByteBuf s1 = buf.slice(0, 100);
    ByteBuf s2 = buf.slice(100, buf.readableBytes() - 100);
    channel.writeInbound(s1);
    channel.writeInbound(s2);
  }
}
