package com.slimbloody.lightning.simple.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {
  public final static PacketEncoder INSTANCE = new PacketEncoder();

  @Override
  protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out)
    throws Exception {
    PacketCodec.INSTANCE.encode(out, packet);
  }
}
