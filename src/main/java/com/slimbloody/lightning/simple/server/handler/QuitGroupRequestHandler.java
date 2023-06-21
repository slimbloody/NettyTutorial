package com.slimbloody.lightning.simple.server.handler;

import com.slimbloody.lightning.simple.exceptions.ChannelGroupNotExistException;
import com.slimbloody.lightning.simple.protocol.req.QuitGroupRequestPacket;
import com.slimbloody.lightning.simple.protocol.res.QuitGroupResponsePacket;
import com.slimbloody.lightning.simple.session.SessionUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Sharable
@Slf4j
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
  public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx,
    QuitGroupRequestPacket requestPacket) throws Exception {
    String groupId = requestPacket.getGroupId();

    QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();
    responsePacket.setGroupId(groupId);
    
    try {
      SessionUtil.quitChannelGroup(groupId, ctx.channel());
    } catch (ChannelGroupNotExistException e) {
      responsePacket.setSuccess(false);
      responsePacket.setReason(e.getMessage());
    }
    ctx.writeAndFlush(responsePacket);
  }
}
