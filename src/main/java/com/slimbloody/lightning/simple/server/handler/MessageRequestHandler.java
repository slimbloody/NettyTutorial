package com.slimbloody.lightning.simple.server.handler;

import com.slimbloody.lightning.simple.protocol.req.MessageRequestPacket;
import com.slimbloody.lightning.simple.protocol.res.MessageResponsePacket;
import com.slimbloody.lightning.simple.session.Session;
import com.slimbloody.lightning.simple.session.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Sharable
@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
  
  public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();
  
  private MessageRequestHandler() {
  }
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx,
    MessageRequestPacket msg) throws Exception {
    Session fromSession = SessionUtil.getSession(ctx.channel());
  
    Channel toChannel = SessionUtil.getChannel(msg.getToUid());
  
    if (Objects.nonNull(toChannel)) {
      toChannel.writeAndFlush(
        new MessageResponsePacket(
          fromSession.getUid(),
          msg.getToUid(),
          msg.getMessage()
        )
      ).addListener(future -> {
        if (future.isSuccess()) {
          log.info("send done");
        } else {
          log.info("error");
        }
      });
    } else {
      log.info("to uid " + msg.getToUid() + " offline");
    }
  }
}
