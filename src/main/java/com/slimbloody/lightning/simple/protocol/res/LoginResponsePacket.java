package com.slimbloody.lightning.simple.protocol.res;

import com.slimbloody.lightning.simple.protocol.Command;
import com.slimbloody.lightning.simple.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponsePacket extends Packet {
  private String uid;
  private String username;
  private boolean success;
  private String reason;
  
  @Override
  public byte getCommand() {
    return Command.LOGIN_RESPONSE;
  }
}
