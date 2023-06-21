package com.slimbloody.lightning.simple.client.handler;

import com.slimbloody.lightning.simple.protocol.res.MessageResponsePacket;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
  public static final MessageResponseHandler INSTANCE = new MessageResponseHandler();
  
  private MessageResponseHandler() {
  }
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx,
    MessageResponsePacket messageResponsePacket) throws Exception {
    String fromUid = messageResponsePacket.getFromUid();
    String toUid = messageResponsePacket.getToUid();
    String message = messageResponsePacket.getMessage();
  
    log.info(fromUid + " to " + toUid + " " + message);
  }
  
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    throws Exception {
    log.info(cause.toString());
  }
}
