package org.orion.koffe.repository.impl;

import java.util.List;
import org.orion.koffe.model.Coffee;
import org.orion.koffe.repository.CoffeeJpa;
import org.orion.koffe.repository.repo.CoffeeRepo;
import org.springframework.stereotype.Repository;

@Repository
public class CoffeeImpl implements CoffeeRepo {

  private final CoffeeJpa repo;

  public CoffeeImpl(CoffeeJpa repo) {
    this.repo = repo;
  }

  @Override
  public Coffee save(Coffee coffee) {
    return repo.save(coffee);
  }

  @Override
  public Coffee get(int id) {
    return repo.findById(id).orElse(null);
  }

  @Override
  public boolean delete(int id) {
    return repo.delete(id) != 0;
  }

  @Override
  public List<Coffee> getAll() {
    return repo.findAll();
  }
}
