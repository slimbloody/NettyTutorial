package com.slimbloody.lightning.simple.console;

import com.slimbloody.lightning.simple.protocol.req.QuitGroupRequestPacket;
import io.netty.channel.Channel;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QuitGroupConsoleCommand implements ConsoleCommand {
  @Override
  public void exec(Scanner scanner, Channel channel) {
    log.info("input quit groupId:");
    String groupId = scanner.next();
  
    QuitGroupRequestPacket quitGroupRequestPacket = new QuitGroupRequestPacket(groupId);
    channel.writeAndFlush(quitGroupRequestPacket);
  }
}
