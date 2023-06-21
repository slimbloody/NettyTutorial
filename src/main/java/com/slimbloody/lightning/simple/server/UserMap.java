package com.slimbloody.lightning.simple.server;

import java.util.HashMap;

public class UserMap {
  public static HashMap<String, User> userMap = new HashMap<>();
  
  static {
    userMap.put(
      "1000",
      new User(
        "1000",
        "zhangsan",
        "123456"
      )
    );
    userMap.put(
      "1001",
      new User(
        "1001",
        "lisi",
        "123456"
      )
    );
    userMap.put(
      "1002",
      new User(
        "1002",
        "wangwu",
        "123456"
      )
    );
  }
}
