package com.slimbloody.lightning.simple.client.handler;

import com.slimbloody.lightning.simple.protocol.res.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
  public static final CreateGroupResponseHandler INSTANCE = new CreateGroupResponseHandler();
  
  private CreateGroupResponseHandler() {
  }
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx,
    CreateGroupResponsePacket responsePacket) throws Exception {
    if (responsePacket.isSuccess()) {
      log.info("create group success: " + responsePacket.getGroupId());
      log.info("addedUidList: " + responsePacket.getUidList());
    } else {
      log.info("create group fail: " + responsePacket.getReason());
    }
  }
  
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    throws Exception {
    log.info(cause.toString());
  }
}
