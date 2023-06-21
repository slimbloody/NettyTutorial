package com.slimbloody.lightning.simple.server.handler;

import com.slimbloody.lightning.simple.dto.UserInfo;
import com.slimbloody.lightning.simple.protocol.req.LoginRequestPacket;
import com.slimbloody.lightning.simple.protocol.res.LoginResponsePacket;
import com.slimbloody.lightning.simple.server.User;
import com.slimbloody.lightning.simple.server.UserMap;
import com.slimbloody.lightning.simple.session.Session;
import com.slimbloody.lightning.simple.session.SessionUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
  public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();
  
  private LoginRequestHandler() {
  }
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
    UserInfo userInfo = validate(loginRequestPacket);
    LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
    loginResponsePacket.setVersion(loginRequestPacket.getVersion());

    if (Objects.isNull(userInfo)) {
      loginResponsePacket.setReason("uid and pwd not match");
      loginResponsePacket.setSuccess(false);
  
      SessionUtil.bindSession(new Session(userInfo.getUid(), userInfo.getUsername()), ctx.channel());
    } else {
      loginResponsePacket.setUid(userInfo.getUid());
      loginResponsePacket.setUsername(userInfo.getUsername());
      loginResponsePacket.setSuccess(true);
    }
  
    // todo: 要不要channel
    ctx.writeAndFlush(loginResponsePacket);
  }
  
  private UserInfo validate(LoginRequestPacket loginRequestPacket) throws IOException {
    User user = UserMap.userMap.get(loginRequestPacket.getUid());
    boolean validate = Optional.ofNullable(user.getPwd())
      .orElseThrow(() -> new IOException("empty pwd"))
      .equals(loginRequestPacket.getPassword());

    if (validate) {
      return new UserInfo(user.getUid(), user.getUsername());
    } else {
      return null;
    }
  }
  
  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    SessionUtil.unBindSession(ctx.channel());
  }
}
