package com.slimbloody.lightning.simple.server.handler;

import com.slimbloody.lightning.simple.exceptions.ChannelGroupNotExistException;
import com.slimbloody.lightning.simple.exceptions.SessionOfflineException;
import com.slimbloody.lightning.simple.protocol.req.JoinGroupRequestPacket;
import com.slimbloody.lightning.simple.protocol.res.JoinGroupResponsePacket;
import com.slimbloody.lightning.simple.session.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
  public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();
  
  private JoinGroupRequestHandler() {
  }
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx,
    JoinGroupRequestPacket requestPacket) throws Exception {
    String groupId = requestPacket.getGroupId();
  
    JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();
    responsePacket.setGroupId(groupId);
    responsePacket.setSuccess(true);

    try {
      SessionUtil.joinChannelGroup(groupId, ctx.channel());
    } catch (SessionOfflineException | ChannelGroupNotExistException e) {
      log.info(e.getMessage());
      responsePacket.setSuccess(false);
      responsePacket.setReason(e.getMessage());
    }
    
    ctx.writeAndFlush(responsePacket);
  }
}
