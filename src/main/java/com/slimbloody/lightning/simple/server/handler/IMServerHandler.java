package com.slimbloody.lightning.simple.server.handler;

import com.slimbloody.lightning.simple.protocol.Command;
import com.slimbloody.lightning.simple.protocol.Packet;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
public class IMServerHandler extends SimpleChannelInboundHandler<Packet> {
  public final static IMServerHandler INSTANCE = new IMServerHandler();
  private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap = new HashMap<>();
  
  private IMServerHandler() {
    handlerMap.put(Command.LOGIN_REQUEST, LoginRequestHandler.INSTANCE);
    handlerMap.put(Command.LOGOUT_REQUEST, LogoutRequestHandler.INSTANCE);
    handlerMap.put(Command.MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
    handlerMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestHandler.INSTANCE);
    handlerMap.put(Command.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestHandler.INSTANCE);
    handlerMap.put(Command.JOIN_GROUP_REQUEST, JoinGroupRequestHandler.INSTANCE);
    handlerMap.put(Command.QUIT_GROUP_REQUEST, QuitGroupRequestHandler.INSTANCE);
    handlerMap.put(Command.GROUP_MESSAGE_REQUEST, GroupMessageRequestHandler.INSTANCE);
  }
  
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Packet packet)
    throws Exception {
    // 这里调用是不是就多释放了一次byteBuf
    handlerMap.get(packet.getCommand()).channelRead(ctx, packet);
  }
}
