package com.slimbloody.lightning.simple.server.handler;

import com.slimbloody.lightning.simple.protocol.req.GroupMessageRequestPacket;
import com.slimbloody.lightning.simple.protocol.res.GroupMessageResponsePacket;
import com.slimbloody.lightning.simple.session.Session;
import com.slimbloody.lightning.simple.session.SessionUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

@Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
  public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();
  
  private GroupMessageRequestHandler() {
  }
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx,
    GroupMessageRequestPacket packet) throws Exception {
    GroupMessageResponsePacket groupMessageResponsePacket = new GroupMessageResponsePacket(
      packet.getToGroupId(),
      SessionUtil.getSession(ctx.channel()).getUid(),
      packet.getMessage()
    );
  
    ChannelGroup channelGroup = SessionUtil.getChannelGroup(packet.getToGroupId());
    channelGroup.writeAndFlush(groupMessageResponsePacket);
  }
}
