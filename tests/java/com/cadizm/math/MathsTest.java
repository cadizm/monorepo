package com.cadizm.math;

import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.Test;

import static com.cadizm.math.Maths.gcd;
import static com.cadizm.math.Maths.lcm;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MathsTest {

  @Test
  public void testGcd() {
    assertEquals(15, gcd(210, 45));
    assertEquals(15, gcd(45, 210));
  }

  @Test
  public void testGcdInvalidInput() {
    assertThrows(IllegalArgumentException.class, () -> gcd(0, 2));
    assertThrows(IllegalArgumentException.class, () -> gcd(17, 0));
  }

  @Test
  public void testLcm() {
    assertEquals(12, lcm(4, 6));
    assertEquals(629, lcm(17, 37));
  }

  @Test
  public void testBigIntegerLcm() {
    assertEquals(new BigInteger("12"), lcm(new BigInteger("4"), new BigInteger("6")));
    assertEquals(new BigInteger("629"), lcm(new BigInteger("17"), new BigInteger("37")));
  }

  @Test
  public void testListBigIntegerLcm() {
    var expected = new BigInteger("13334102464297");
    var actual = lcm(List.of(22199, 16579, 13207, 17141, 18827, 14893));

    assertEquals(expected, actual);
  }

  @Test
  public void testLcmInvalidInput() {
    assertThrows(IllegalArgumentException.class, () -> lcm(0, 2));
    assertThrows(IllegalArgumentException.class, () -> lcm(17, 0));
  }
}
