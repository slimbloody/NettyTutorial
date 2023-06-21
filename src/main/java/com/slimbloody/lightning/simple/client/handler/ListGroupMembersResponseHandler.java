package com.slimbloody.lightning.simple.client.handler;

import com.slimbloody.lightning.simple.protocol.res.ListGroupMembersResponsePacket;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
public class ListGroupMembersResponseHandler extends
  SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {
  public static final ListGroupMembersResponseHandler INSTANCE = new ListGroupMembersResponseHandler();
  
  private ListGroupMembersResponseHandler() {
  }
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx,
    ListGroupMembersResponsePacket responsePacket) throws Exception {
    if (StringUtil.isNullOrEmpty(responsePacket.getReason())) {
      log.info("uids from group: " + responsePacket.getUidList());
    } else {
      log.info("get uids from group failed: " + responsePacket.getReason());
    }
  }
  
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    throws Exception {
    log.info(cause.toString());
  }
}
