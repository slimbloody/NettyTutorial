package com.slimbloody.lightning.simple.client.handler;

import com.slimbloody.lightning.simple.protocol.res.LogoutResponsePacket;
import com.slimbloody.lightning.simple.session.SessionUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {
  public static final LogoutResponseHandler INSTANCE = new LogoutResponseHandler();
  
  private LogoutResponseHandler() {
  }
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx,
    LogoutResponsePacket msg) throws Exception {
    SessionUtil.unBindSession(ctx.channel());
    ctx.close();
  }
  
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    throws Exception {
    log.info(cause.toString());
  }
}
