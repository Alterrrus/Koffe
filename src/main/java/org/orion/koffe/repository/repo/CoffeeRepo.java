package org.orion.koffe.repository.repo;

import java.util.List;
import org.orion.koffe.model.Coffee;

public interface CoffeeRepo {

  Coffee save(Coffee coffee);

  Coffee get(int id);

  boolean delete(int id);

  List<Coffee> getAll();
}
