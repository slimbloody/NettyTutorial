package com.slimbloody.lightning.simple.console;


import com.slimbloody.lightning.simple.protocol.req.JoinGroupRequestPacket;
import io.netty.channel.Channel;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JoinGroupConsoleCommand implements ConsoleCommand {
  @Override
  public void exec(Scanner scanner, Channel channel) {
    JoinGroupRequestPacket requestPacket = new JoinGroupRequestPacket();
    log.info("input join groupId:");
    String groupId = scanner.next();
    channel.writeAndFlush(new JoinGroupRequestPacket(groupId));
  }
}
