package com.slimbloody.lightning.simple.server.handler;

import com.slimbloody.lightning.simple.session.SessionUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {
  public static final AuthHandler INSTANCE = new AuthHandler();
  
  private AuthHandler() {
  }
  
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg)
    throws Exception {
    if (!SessionUtil.hasLogin(ctx.channel())) {
      ctx.channel().close();
    } else {
      // validate only once, then remove itself
      ctx.pipeline().remove(this);
      super.channelRead(ctx, msg);
    }
  }
}
