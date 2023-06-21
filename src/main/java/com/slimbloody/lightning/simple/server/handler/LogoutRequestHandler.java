package com.slimbloody.lightning.simple.server.handler;

import com.slimbloody.lightning.simple.protocol.req.LogoutRequestPacket;
import com.slimbloody.lightning.simple.protocol.res.LogoutResponsePacket;
import com.slimbloody.lightning.simple.session.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {
  public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();
  
  private LogoutRequestHandler() {
  }
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx,
    LogoutRequestPacket msg) throws Exception {
    SessionUtil.unBindSession(ctx.channel());
  
    LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
    logoutResponsePacket.setSuccess(true);
    // 让client去执行channel close操作
    ctx.writeAndFlush(logoutResponsePacket);
  }
}
