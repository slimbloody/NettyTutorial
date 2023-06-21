package com.slimbloody.lightning.simple.client.handler;

import com.slimbloody.lightning.simple.protocol.res.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
  public static final JoinGroupResponseHandler INSTANCE = new JoinGroupResponseHandler();
  
  private JoinGroupResponseHandler() {
  }
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx,
    JoinGroupResponsePacket responsePacket) throws Exception {
    if (responsePacket.isSuccess()) {
      log.info("join group success: " + responsePacket.getGroupId());
    } else {
      log.info("join group fail: " + responsePacket.getReason());
    }
  }
  
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    throws Exception {
    log.info(cause.toString());
  }
}
