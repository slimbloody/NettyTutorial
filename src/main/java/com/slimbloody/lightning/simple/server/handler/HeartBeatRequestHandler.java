package com.slimbloody.lightning.simple.server.handler;

import com.slimbloody.lightning.simple.protocol.req.HeartBeatRequestPacket;
import com.slimbloody.lightning.simple.protocol.res.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

// todo:改造成有读写就不回复
@Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {
  public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();
  
  private HeartBeatRequestHandler() {
  }
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx,
    HeartBeatRequestPacket heartBeatRequestPacket) throws Exception {
    ctx.writeAndFlush(new HeartBeatResponsePacket());
  }
}
