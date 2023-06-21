package com.slimbloody.lightning.simple.console;

import com.slimbloody.lightning.simple.protocol.req.MessageRequestPacket;
import io.netty.channel.Channel;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SendToUserConsoleCommand implements ConsoleCommand {
  @Override
  public void exec(Scanner scanner, Channel channel) {
    log.info("toUid:");
    String toUserId = scanner.next();
    log.info("message:");
    String message = scanner.next();
    channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
  }
}
