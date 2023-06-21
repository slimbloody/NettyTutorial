package com.slimbloody.lightning.simple.protocol.req;

import com.slimbloody.lightning.simple.protocol.Command;
import com.slimbloody.lightning.simple.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequestPacket extends Packet {
  private String toUid;
  private String message;
  
  @Override
  public byte getCommand() {
    return Command.MESSAGE_REQUEST;
  }
}
