package com.slimbloody.lightning.simple.client.handler;

import com.slimbloody.lightning.simple.protocol.res.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {
  public static final QuitGroupResponseHandler INSTANCE = new QuitGroupResponseHandler();
  
  private QuitGroupResponseHandler() {
  }
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx,
    QuitGroupResponsePacket responsePacket) throws Exception {
    if (responsePacket.isSuccess()) {
      log.info("quit group success: " + responsePacket.getGroupId());
    } else {
      log.info("quit group failed: " + responsePacket.getGroupId());
    }
  }
  
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    throws Exception {
    log.info(cause.toString());
  }
}
