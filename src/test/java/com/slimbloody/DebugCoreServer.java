package com.slimbloody;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DebugCoreServer {
  public static void main(String[] args) {
    new ServerBootstrap().group(new NioEventLoopGroup())
      .channel(NioServerSocketChannel.class)
      .childHandler(new ChannelInitializer<NioSocketChannel>() {
        @Override
        protected void initChannel(NioSocketChannel ch) throws Exception {
          ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
        }
      })
      .bind(10000);
  }
}
