package org.orion.koffe;


import java.time.LocalDateTime;
import java.util.List;
import org.orion.koffe.model.Coffee;
import org.orion.koffe.model.CoffeeType;

public class RestTestData {


  public static final TestMatcher<Coffee> TEST_MATCHER1 = TestMatcher.usingFieldsWithIgnoringAssertions(Coffee.class);
  public static final TestMatcher<Coffee> TEST_MATCHER = TestMatcher.usingEqualsAssertions(Coffee.class);
  public static final Coffee coffe1 = new Coffee(10000, CoffeeType.AMERICANO,
      LocalDateTime.of(2020, 1, 30, 10, 0, 0), 1);
  public static final Coffee coffe2 = new Coffee(10001, CoffeeType.CAPPUCCINO,
      LocalDateTime.of(2020, 1, 30, 10, 0, 1), 6);
  public static final Coffee coffe3 = new Coffee(10002, CoffeeType.LATTE,
      LocalDateTime.of(2020, 1, 30, 10, 0, 5), 5);
  public static final Coffee coffe4 = new Coffee(10003, CoffeeType.ESPRESSO,
      LocalDateTime.of(2020, 1, 30, 10, 0, 10), 10);
  public static final List<Coffee> coffeeList = List.of(coffe1, coffe2, coffe3, coffe4);


}
