package org.orion.koffe.service;


import static org.orion.koffe.RestTestData.TEST_MATCHER1;
import static org.orion.koffe.RestTestData.coffe4;
import static org.orion.koffe.RestTestData.coffeeList;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.orion.koffe.model.Coffee;
import org.orion.koffe.model.CoffeeType;
import org.orion.koffe.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

public class CoffeeServiceTest extends AbstractServiceTest {

  @Autowired
  private CoffeeService service;

  @Test
  void delete() throws Exception {
    service.delete(10000);
    Assertions.assertThrows(NotFoundException.class, () -> service.get(10000));
  }

  @Test
  void deleteNotFound() throws Exception {
    Assertions.assertThrows(NotFoundException.class, () -> service.delete(1));
  }
  @Test
  void create() throws Exception {
    Coffee created = service.
        create(
            new Coffee(null, CoffeeType.AMERICANO, LocalDateTime.of(2020, 1, 30, 10, 0, 0), 1));
    Integer id = created.id();
    Coffee newCoffee = new Coffee(null, CoffeeType.AMERICANO,
        LocalDateTime.of(2020, 1, 30, 10, 0, 0), 1);
    newCoffee.setId(id);
    TEST_MATCHER1.assertMatch(created, newCoffee);
    TEST_MATCHER1.assertMatch(service.get(id), newCoffee);
  }

  @Test
  void get() throws Exception {
    Coffee actual = service.get(10003);
    TEST_MATCHER1.assertMatch(actual, coffe4);
  }
  @Test
  void getAll(){
    List<Coffee> actual= service.getAll();
    TEST_MATCHER1.assertMatch(actual,coffeeList);
  }


}
