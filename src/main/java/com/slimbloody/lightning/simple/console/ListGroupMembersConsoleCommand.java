package com.slimbloody.lightning.simple.console;

import com.slimbloody.lightning.simple.protocol.req.ListGroupMembersRequestPacket;
import io.netty.channel.Channel;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ListGroupMembersConsoleCommand implements ConsoleCommand {
  @Override
  public void exec(Scanner scanner, Channel channel) {
    log.info("input groupId:");
    String groupId = scanner.next();
    channel.writeAndFlush(new ListGroupMembersRequestPacket(groupId));
  }
}

