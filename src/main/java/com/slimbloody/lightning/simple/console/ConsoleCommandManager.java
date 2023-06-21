package com.slimbloody.lightning.simple.console;

import com.slimbloody.lightning.simple.session.SessionUtil;
import io.netty.channel.Channel;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsoleCommandManager implements ConsoleCommand {
  public static final ConsoleCommandManager INSTANCE = new ConsoleCommandManager();
  
  private Map<String, ConsoleCommand> consoleCommandMap = new HashMap<>();

  private ConsoleCommandManager() {
    consoleCommandMap.put("login", new LoginConsoleCommand());
    consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
    consoleCommandMap.put("logout", new LogoutConsoleCommand());
    consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());
    consoleCommandMap.put("joinGroup", new JoinGroupConsoleCommand());
    consoleCommandMap.put("quitGroup", new QuitGroupConsoleCommand());
    consoleCommandMap.put("listGroupMembers", new ListGroupMembersConsoleCommand());
    consoleCommandMap.put("sendToGroup", new SendToGroupConsoleCommand());
  }
  
  @Override
  public void exec(Scanner scanner, Channel channel) {
    String command = scanner.next();
    ConsoleCommand consoleCommand = consoleCommandMap.get(command);
    if (consoleCommand != null) {
      if (!(consoleCommand instanceof LoginConsoleCommand)) {
        if (!SessionUtil.hasLogin(channel)) {
          log.error("has not login");
          return;
        }
      }
      consoleCommand.exec(scanner, channel);
    } else {
      log.error("error command:" + command);
    }
  }
}
