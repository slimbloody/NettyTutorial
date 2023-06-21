package com.slimbloody.lightning.simple.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  private String uid;
  private String username;
  private String pwd;
}
