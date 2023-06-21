package com.slimbloody.lightning.simple.server;

import com.slimbloody.lightning.simple.handler.IMIdleStateHandler;
import com.slimbloody.lightning.simple.handler.PacketCodecHandler;
import com.slimbloody.lightning.simple.protocol.FrameDecoder;
import com.slimbloody.lightning.simple.server.handler.HeartBeatRequestHandler;
import com.slimbloody.lightning.simple.server.handler.IMServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.kqueue.KQueueSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {
  public static LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
  
  public static void main(String[] args) {
    EventLoopGroup bossGroup = new KQueueEventLoopGroup(1);
    EventLoopGroup workerGroup = new KQueueEventLoopGroup(8);
    ServerBootstrap serverBootstrap = new ServerBootstrap().group(bossGroup,
        workerGroup)
      .channel(KQueueServerSocketChannel.class)
      .childOption(ChannelOption.SO_BACKLOG, 1024)
      .childOption(ChannelOption.SO_KEEPALIVE, false)
      .childOption(ChannelOption.TCP_NODELAY, true)
      .childHandler(new ChannelInitializer<KQueueSocketChannel>() {
        @Override
        protected void initChannel(KQueueSocketChannel ch) throws Exception {
          ch.pipeline().addLast(LOGGING_HANDLER);
          ch.pipeline().addLast(IMIdleStateHandler.INSTANCE);
          ch.pipeline().addLast(new FrameDecoder());
          ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
          ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
          ch.pipeline().addLast(IMServerHandler.INSTANCE);
        }
      });
    // .attr() serverBootStrap.attr() 一般用不上
    // .childAttr() channel.attr()
    // 绑定端口号,从1000开始递增
    ChannelFuture channelFuture = bind(serverBootstrap, 10000, false);
    
    // .sync()在外面finally块调用shutdownGracefully也可以
    channelFuture.channel().closeFuture().addListener(
      new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
          bossGroup.shutdownGracefully();
          workerGroup.shutdownGracefully();
        }
      }
    );
  }
  
  private static ChannelFuture bind(ServerBootstrap serverBootstrap,
    int port, boolean inc) {
    return serverBootstrap.bind(port)
      .addListener(new GenericFutureListener<Future<? super Void>>() {
        @Override
        public void operationComplete(Future<? super Void> future)
          throws Exception {
          if (future.isSuccess()) {
            log.info("server success");
          } else {
            // if port is used, port + 1
            log.info("server failed");
 
            if (inc) {
              int newPort = port + 1;
              bind(serverBootstrap, newPort, inc);
              log.info("try new port " + newPort);
            }
          }
        }
      });
  }
}
