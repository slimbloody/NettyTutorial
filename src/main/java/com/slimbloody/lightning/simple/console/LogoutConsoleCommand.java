package com.slimbloody.lightning.simple.console;

import com.slimbloody.lightning.simple.protocol.req.LogoutRequestPacket;
import io.netty.channel.Channel;
import java.util.Scanner;

public class LogoutConsoleCommand implements ConsoleCommand {
  @Override
  public void exec(Scanner scanner, Channel channel) {
    LogoutRequestPacket logoutRequestPacket = new LogoutRequestPacket();
    channel.writeAndFlush(logoutRequestPacket);
  }
}
