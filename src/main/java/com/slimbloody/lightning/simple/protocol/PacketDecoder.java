package com.slimbloody.lightning.simple.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

// 在channelRead里面调用decode,在cumulator里面会把ByteBuf释放掉,不需要自己操心了
// todo:不用ByteToMessageDecoder是因为用byteBuf承接的,所以这里也属于Message
@Sharable
public class PacketDecoder extends ByteToMessageDecoder {
  
  public static final PacketDecoder INSTANCE = new PacketDecoder();
  
  private PacketDecoder() {
  }
  
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
    out.add(PacketCodec.INSTANCE.decode(in));
  }
}
