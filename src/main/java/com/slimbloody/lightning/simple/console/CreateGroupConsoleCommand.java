package com.slimbloody.lightning.simple.console;

import com.slimbloody.lightning.simple.protocol.req.CreateGroupRequestPacket;
import io.netty.channel.Channel;
import java.util.Arrays;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateGroupConsoleCommand implements ConsoleCommand {
  private static final String USER_ID_SPLITER = ",";

  @Override
  public void exec(Scanner scanner, Channel channel) {
    log.info("input uid you want to invite to the group,split by `,`");
    String uidsString = scanner.next();
  
    CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket(
      Arrays.asList(uidsString.split(USER_ID_SPLITER)));
    channel.writeAndFlush(createGroupRequestPacket);
  }
}
