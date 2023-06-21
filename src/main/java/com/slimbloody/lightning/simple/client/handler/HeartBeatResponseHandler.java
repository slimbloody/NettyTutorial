package com.slimbloody.lightning.simple.client.handler;

import com.slimbloody.lightning.simple.protocol.req.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
public class HeartBeatResponseHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {
  public static final HeartBeatResponseHandler INSTANCE = new HeartBeatResponseHandler();
  
  private HeartBeatResponseHandler() {
  }
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx,
    HeartBeatRequestPacket msg) throws Exception {
    log.info("receive beat");
  }
  
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    throws Exception {
    log.info(cause.toString());
  }
}
