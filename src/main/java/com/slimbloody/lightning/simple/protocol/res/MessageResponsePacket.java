package com.slimbloody.lightning.simple.protocol.res;

import com.slimbloody.lightning.simple.protocol.Command;
import com.slimbloody.lightning.simple.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponsePacket extends Packet {
  private String fromUid;
  private String toUid;
  private String message;
  
  @Override
  public byte getCommand() {
    return Command.MESSAGE_RESPONSE;
  }
}
