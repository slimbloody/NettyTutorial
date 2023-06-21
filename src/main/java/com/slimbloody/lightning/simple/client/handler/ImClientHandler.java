package com.slimbloody.lightning.simple.client.handler;

import com.slimbloody.lightning.simple.protocol.Command;
import com.slimbloody.lightning.simple.protocol.Packet;
import com.slimbloody.lightning.simple.protocol.PacketCodec;
import com.slimbloody.lightning.simple.protocol.req.LoginRequestPacket;
import com.slimbloody.lightning.simple.protocol.res.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
public class ImClientHandler extends SimpleChannelInboundHandler<Packet> {
  public static final ImClientHandler INSTANCE = new ImClientHandler();
  
  private final Map<Byte, SimpleChannelInboundHandler<?>> handlerMap = new HashMap<>();
  
  private ImClientHandler() {
    handlerMap.put(Command.LOGIN_RESPONSE, LoginResponseHandler.INSTANCE);
    handlerMap.put(Command.MESSAGE_RESPONSE, MessageResponseHandler.INSTANCE);
    handlerMap.put(Command.LOGOUT_RESPONSE, LogoutResponseHandler.INSTANCE);
    handlerMap.put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponseHandler.INSTANCE);
    handlerMap.put(Command.LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponseHandler.INSTANCE);
    handlerMap.put(Command.JOIN_GROUP_RESPONSE, JoinGroupResponseHandler.INSTANCE);
    handlerMap.put(Command.QUIT_GROUP_RESPONSE, QuitGroupResponseHandler.INSTANCE);
    handlerMap.put(Command.GROUP_MESSAGE_RESPONSE, GroupMessageResponseHandler.INSTANCE);
    handlerMap.put(Command.HEARTBEAT_RESPONSE, HeartBeatResponseHandler.INSTANCE);
  }
  
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    ctx.fireChannelActive();
  }
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Packet packet)
    throws Exception {
    handlerMap.get(packet.getCommand()).channelRead(ctx, packet);
  }
  
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    throws Exception {
    log.info(cause.toString());
  }
}
