package com.slimbloody.lightning.simple.session;

import io.netty.util.AttributeKey;

public interface Attributes {
  AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
