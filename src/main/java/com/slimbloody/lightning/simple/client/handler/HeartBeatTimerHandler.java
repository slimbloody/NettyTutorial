package com.slimbloody.lightning.simple.client.handler;

import com.slimbloody.lightning.simple.protocol.req.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

// todo:改造成有读写就不回复
@Slf4j
@Sharable
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {
  private static final int HEARTBEAT_INTERVAL = 60;
  
  public static final HeartBeatTimerHandler INSTANCE = new HeartBeatTimerHandler();
  
  private HeartBeatTimerHandler() {
  }
  
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    scheduleSendHeartBeat(ctx);
    super.channelActive(ctx);
  }
  
  private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
    ctx.executor().schedule(() -> {
      if (ctx.channel().isActive()) {
        ctx.writeAndFlush(new HeartBeatRequestPacket());
        scheduleSendHeartBeat(ctx);
      }
    }, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
  }
  
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    throws Exception {
    log.info(cause.toString());
  }
}
