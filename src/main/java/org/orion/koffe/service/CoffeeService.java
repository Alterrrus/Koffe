package org.orion.koffe.service;


import static org.orion.koffe.util.ValidationUtil.checkNew;
import static org.orion.koffe.util.ValidationUtil.checkNotFoundWithId;

import java.util.List;
import org.orion.koffe.model.Coffee;
import org.orion.koffe.repository.repo.CoffeeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CoffeeService {

  private final CoffeeRepo repo;

  protected final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  public CoffeeService(CoffeeRepo repo) {
    this.repo = repo;
  }

  public Coffee get(Integer id) {
    log.info("get");
    return checkNotFoundWithId(repo.get(id), id);
  }

  public Coffee create(Coffee coffee) {
    log.info("create");
    checkNew(coffee);
    Assert.notNull(coffee, "Coffee must not be null");
    return repo.save(coffee);
  }

  public void delete(int id) {
    log.info("delete");
    checkNotFoundWithId(repo.delete(id), id);

  }

  public List<Coffee> getAll() {
    log.info("getAll");
    return repo.getAll();
  }


}
