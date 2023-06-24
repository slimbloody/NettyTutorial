package com.slimbloody.lightning.simple.client;

import com.slimbloody.lightning.simple.client.handler.HeartBeatTimerHandler;
import com.slimbloody.lightning.simple.client.handler.ImClientHandler;
import com.slimbloody.lightning.simple.console.ConsoleCommandManager;
import com.slimbloody.lightning.simple.handler.PacketCodecHandler;
import com.slimbloody.lightning.simple.protocol.FrameDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyClient {
  public static LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
  public static final int MAX_RETRY = 3;
  public static Thread consoleThread;
  public static void main(String[] args) throws InterruptedException {
    KQueueEventLoopGroup group = new KQueueEventLoopGroup(1);
    Bootstrap bootStrap = new Bootstrap()
      .group(group)
      .channel(KQueueSocketChannel.class)
      .option(ChannelOption.TCP_NODELAY, false)
      .option(ChannelOption.SO_KEEPALIVE, false)
      // 3秒连接超时
      .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
      .handler(new ChannelInitializer<KQueueSocketChannel>() {
        @Override
        protected void initChannel(KQueueSocketChannel ch) throws Exception {
          ch.pipeline().addLast(LOGGING_HANDLER);
          ch.pipeline().addLast(HeartBeatTimerHandler.INSTANCE);
          ch.pipeline().addLast(new FrameDecoder());
          ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
          ch.pipeline().addLast(ImClientHandler.INSTANCE);
        }
      });
  
  
    ChannelFuture channelFuture = connect(bootStrap, MAX_RETRY);
    channelFuture.channel().closeFuture().addListener(
      new GenericFutureListener<Future<? super Void>>() {
        @Override
        public void operationComplete(Future<? super Void> future)
          throws Exception {
          group.shutdownGracefully();
          NettyClient.consoleThread.stop();
        }
      });
  }
  
  private static ChannelFuture connect(Bootstrap bootStrap, int retry)
    throws InterruptedException {
    return bootStrap
      .connect("127.0.0.1", 10000)
      .addListener(new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
          if (future.isSuccess()) {
            Channel channel = future.channel();
            log.info("success connect");
            startConsoleThread(channel);
          } else {
            log.info("connect fail");
            // 2 4 8
            int delay = 1 << (MAX_RETRY - retry) + 1;
            if (retry > 0) {
              log.info("retry" + delay);
              bootStrap.config().group().schedule(
                () -> connect(bootStrap, retry - 1),
                delay,
                TimeUnit.SECONDS
              );
            }
          }
        }
      })
      .sync();
  }
  
  public static void startConsoleThread(Channel channel) {
    NettyClient.consoleThread = new Thread(() -> {
      log.info("input command:");
      while (!Thread.interrupted()) {
        Scanner sc = new Scanner(System.in);
        ConsoleCommandManager.INSTANCE.exec(sc, channel);
      }
    });
    NettyClient.consoleThread.start();
  }
}
