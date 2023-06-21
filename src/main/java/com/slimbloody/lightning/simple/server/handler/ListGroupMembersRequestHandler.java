package com.slimbloody.lightning.simple.server.handler;

import com.slimbloody.lightning.simple.protocol.req.ListGroupMembersRequestPacket;
import com.slimbloody.lightning.simple.protocol.res.ListGroupMembersResponsePacket;
import com.slimbloody.lightning.simple.session.Session;
import com.slimbloody.lightning.simple.session.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {
  public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();
  
  private ListGroupMembersRequestHandler() {
  }
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx,
    ListGroupMembersRequestPacket requestPacket) throws Exception {
    String groupId = requestPacket.getGroupId();
    ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
    ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();
    responsePacket.setGroupId(groupId);
  
    Set<String> onlineGroupUidList = new HashSet<>();
    for (Channel channel : channelGroup) {
      Session session = SessionUtil.getSession(channel);
      String uid = session.getUid();
      onlineGroupUidList.add(uid);
    }
    if (Objects.nonNull(channelGroup)) {
      responsePacket.setUidList(onlineGroupUidList);
    } else {
      responsePacket.setReason("group does not exist");
    }
    ctx.writeAndFlush(requestPacket);
  }
}
