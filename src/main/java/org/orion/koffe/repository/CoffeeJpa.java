package org.orion.koffe.repository;

import org.orion.koffe.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CoffeeJpa extends JpaRepository<Coffee, Integer> {

  @Modifying
  @Transactional
  @Query("DELETE FROM Coffee c WHERE c.id=:id")
  int delete(@Param("id") int id);


}
