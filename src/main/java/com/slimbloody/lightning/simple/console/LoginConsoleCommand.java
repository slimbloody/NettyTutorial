package com.slimbloody.lightning.simple.console;

import com.slimbloody.lightning.simple.protocol.req.LoginRequestPacket;
import io.netty.channel.Channel;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginConsoleCommand implements ConsoleCommand {
  @Override
  public void exec(Scanner scanner, Channel channel) {
    log.info("input uid:");
    String uid = scanner.next();
    log.info("input password:");
    String pwd = scanner.next();
    channel.writeAndFlush(new LoginRequestPacket(uid, pwd));
  }
}
