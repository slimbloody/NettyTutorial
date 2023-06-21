package com.slimbloody.lightning.simple.protocol.res;

import com.slimbloody.lightning.simple.protocol.Command;
import com.slimbloody.lightning.simple.protocol.Packet;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGroupResponsePacket extends Packet {
  private boolean success;
  private String groupId;
  private List<String> uidList;
  private String reason;
  
  @Override
  public byte getCommand() {
    return Command.CREATE_GROUP_RESPONSE;
  }
}
