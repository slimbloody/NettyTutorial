package com.slimbloody.lightning.simple.console;

import com.slimbloody.lightning.simple.protocol.req.GroupMessageRequestPacket;
import io.netty.channel.Channel;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SendToGroupConsoleCommand implements ConsoleCommand {
  
  @Override
  public void exec(Scanner scanner, Channel channel) {
  
    log.info("groupId:");
    String toGroupId = scanner.next();
    log.info("message:");
    String message = scanner.next();
    channel.writeAndFlush(new GroupMessageRequestPacket(toGroupId, message));
  }
}
