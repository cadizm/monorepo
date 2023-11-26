package com.cadizm.math;

import org.junit.jupiter.api.Test;

import static com.cadizm.math.Product.digitsProduct;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

  @Test
  public void testDigitsProduct() {
    assertEquals(1, digitsProduct(1));
    assertEquals(10, digitsProduct(0));
    assertEquals(26, digitsProduct(12));
    assertEquals(399, digitsProduct(243));
    assertEquals(589, digitsProduct(360));
    assertEquals(889, digitsProduct(576));
    assertEquals(2559, digitsProduct(450));
  }

  @Test
  public void testDigitsNoProduct() {
    assertEquals(-1, digitsProduct(13));
    assertEquals(-1, digitsProduct(17));
    assertEquals(-1, digitsProduct(19));
  }
}
