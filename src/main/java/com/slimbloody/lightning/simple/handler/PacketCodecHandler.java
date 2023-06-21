package com.slimbloody.lightning.simple.handler;

import com.slimbloody.lightning.simple.protocol.Packet;
import com.slimbloody.lightning.simple.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import java.util.List;

@Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {
  public static final PacketCodecHandler INSTANCE = new PacketCodecHandler();
  
  @Override
  protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> out)
    throws Exception {
    ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
    out.add(PacketCodec.INSTANCE.encode(byteBuf, packet));
  }
  
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf,
    List<Object> out) throws Exception {
    out.add(PacketCodec.INSTANCE.decode(byteBuf));
  }
}
