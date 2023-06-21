package com.slimbloody.chapter3.imEp.protocol;


import com.slimbloody.horse.chapter3.imEp.imServer.config.Config;
import com.slimbloody.horse.chapter3.imEp.message.LoginRequestMessage;
import com.slimbloody.horse.chapter3.imEp.message.Message;
import com.slimbloody.horse.chapter3.imEp.protocol.ProtocolFrameDecoder;
import com.slimbloody.horse.chapter3.imEp.protocol.SharableMessageCodec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class SerializerTest {
  public static void main(String[] args) {
    EmbeddedChannel channel = new EmbeddedChannel(
      new ProtocolFrameDecoder(),
      // 记录write原始的java对象
      new LoggingHandler(LogLevel.DEBUG),
      new SharableMessageCodec(),
      // 记录write的byteBuf的对象
      new LoggingHandler(LogLevel.DEBUG)
    );
  
    LoginRequestMessage loginReqMsg = new LoginRequestMessage("zhangsan", "123");
    channel.writeOutbound(loginReqMsg);
  
    ByteBuf byteBuf = messageToBytes(loginReqMsg);
    channel.writeInbound(byteBuf);
  }
  
  // 因为MessageToMessageCodec分配的buf在对象里面,外面拿不到
  // 所以在这里要额外多一个辅助方法
  public static ByteBuf messageToBytes(Message message) {
    ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
    // 1.魔数(4B)
    // todo:改成string,slimbabe,8B
    // byteBuf.writeBytes("1234".getBytes(Charset.defaultCharset()));
    byteBuf.writeBytes(new byte[] {1, 2, 3, 4});
    // 2.版本(1B)
    byteBuf.writeByte(1);
    // 3.序列化算法(1B):先用jdk,以后再支持多种, 0:jdk, 1:json
    byteBuf.writeByte(0);
    // 4.指令类型(1B)
    byteBuf.writeByte(message.getMessageType());
    // 5.sequenceId(4B)
    byteBuf.writeInt(message.getSequenceId());
    // 6.填充(1B)
    // todo:11111111 如果作为补码表示有符号数 那么真值为：-2^(7)+2^6 + 2^5 + ... + 2^0 = -1
    byteBuf.writeByte(0xff);
  
    // 序列化成字节码数组
    byte[] bytes = Config.getSerializerAlgorithm().serialize(message);
  
    // 5.长度
    byteBuf.writeInt(bytes.length);
    // 写入content
    byteBuf.writeBytes(bytes);
    log.info("write done");
    
    return byteBuf;
  }
}