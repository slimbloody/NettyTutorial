package com.slimbloody.lightning.simple.client.handler;

import com.slimbloody.lightning.simple.protocol.res.GroupMessageResponsePacket;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
public class GroupMessageResponseHandler extends
  SimpleChannelInboundHandler<GroupMessageResponsePacket> {
  public static final GroupMessageResponseHandler INSTANCE = new GroupMessageResponseHandler();
  
  private GroupMessageResponseHandler() {
  }
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx,
    GroupMessageResponsePacket packet) throws Exception {
    log.info("group " + packet.getFromGroupId() + " get message: " + packet.getMessage() + " from uid " + packet.getFromUid());
  }
  
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    throws Exception {
    log.info(cause.toString());
  }
}
