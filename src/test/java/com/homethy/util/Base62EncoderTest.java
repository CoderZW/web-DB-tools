package com.homethy.util;

import com.homethy.util.crypto.Base62Encoder;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class Base62EncoderTest {
  @Test
  public void testDecode() {
    String result = Base62Encoder.encode(25);
    assertTrue(Base62Encoder.decode(result) == 25);
  }
}
