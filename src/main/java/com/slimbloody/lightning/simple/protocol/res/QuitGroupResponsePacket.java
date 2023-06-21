package com.slimbloody.lightning.simple.protocol.res;

import com.slimbloody.lightning.simple.protocol.Command;
import com.slimbloody.lightning.simple.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuitGroupResponsePacket extends Packet {
  private String groupId;
  private boolean success = true;
  private String reason;
  
  @Override
  public byte getCommand() {
    return Command.QUIT_GROUP_RESPONSE;
  }
}
