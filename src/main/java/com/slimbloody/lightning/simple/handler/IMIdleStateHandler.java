package com.slimbloody.lightning.simple.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IMIdleStateHandler extends IdleStateHandler {
  private static final int READER_IDLE_TIME = 180;
  
  public static final IMIdleStateHandler INSTANCE = new IMIdleStateHandler();
  
  private IMIdleStateHandler() {
    super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
  }
  
  @Override
  protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt)
    throws Exception {
    if (evt == IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT) {
      log.info("idle " + READER_IDLE_TIME + " seconds channel closed");
      ctx.channel().close();
    }
  }
}
