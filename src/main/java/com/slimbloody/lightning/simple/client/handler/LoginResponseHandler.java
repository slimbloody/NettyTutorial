package com.slimbloody.lightning.simple.client.handler;

import com.slimbloody.lightning.simple.protocol.res.LoginResponsePacket;
import com.slimbloody.lightning.simple.session.Session;
import com.slimbloody.lightning.simple.session.SessionUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
  public static final LoginResponseHandler INSTANCE = new LoginResponseHandler();
  
  private LoginResponseHandler() {
  }
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx,
    LoginResponsePacket loginResponsePacket) throws Exception {
    if (loginResponsePacket.isSuccess())  {
      String uid = loginResponsePacket.getUid();
      String username = loginResponsePacket.getUsername();
      log.info("login success " + uid);
      SessionUtil.bindSession(new Session(uid, username),ctx.channel());
    } else {
      log.info("login error " + loginResponsePacket.getReason());
    }
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) {
    System.out.println("客户端连接被关闭!");
  }
  
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    throws Exception {
    log.info(cause.toString());
  }
}
