package com.slimbloody.lightning.simple.server.handler;

import com.slimbloody.lightning.simple.protocol.req.CreateGroupRequestPacket;
import com.slimbloody.lightning.simple.protocol.res.CreateGroupResponsePacket;
import com.slimbloody.lightning.simple.session.GroupIdGenerator;
import com.slimbloody.lightning.simple.session.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
  public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();
  private CreateGroupRequestHandler() {
  }
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx,
    CreateGroupRequestPacket createGroupRequestPacket) throws Exception {
    List<String> uidList = createGroupRequestPacket.getUidList();
  
    // todo:给这个群主所在的executor给channelGroup,不够完善
    DefaultChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
    
    // 不在线的添加不成功
    List<String> addedUidList = new ArrayList<>();
    for(String uid: uidList) {
      Channel channel = SessionUtil.getChannel(uid);
      if (Objects.nonNull(channel)) {
        channelGroup.add(channel);
        addedUidList.add(uid);
      }
    }
  
    String groupId = GroupIdGenerator.genGroupId();
    CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket(true, groupId, addedUidList, "");
  
    channelGroup.writeAndFlush(createGroupResponsePacket);
  
    SessionUtil.bindChannelGroup(groupId, channelGroup);
  }
}
