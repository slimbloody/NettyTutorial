package com.slimbloody.lightning.simple.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FrameDecoder extends LengthFieldBasedFrameDecoder {
  private static final int LENGTH_FIELD_OFFSET = 7;
  private static final int LENGTH_FIELD_LENGTH = 4;
  
  public FrameDecoder() {
    super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
  }
  
  @Override
  protected Object decode(ChannelHandlerContext ctx, ByteBuf in)
    throws Exception {
    if (in.getInt(in.readerIndex()) != PacketCodec.MAGIC_NUMBER) {
      log.error("magic number error, not correct protocol");
      ctx.channel().close();
      return null;
    }
    return super.decode(ctx, in);
  }
}
