package com.slimbloody.lightning.simple.protocol.res;

import com.slimbloody.lightning.simple.protocol.Command;
import com.slimbloody.lightning.simple.protocol.Packet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListGroupMembersResponsePacket extends Packet {
  private String groupId;
  private Set<String> uidList;
  private String reason;
  
  @Override
  public byte getCommand() {
    return Command.LIST_GROUP_MEMBERS_RESPONSE;
  }
}
