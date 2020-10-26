package org.orion.koffe.web;

import java.net.URI;
import java.util.List;
import org.orion.koffe.View;
import org.orion.koffe.model.Coffee;
import org.orion.koffe.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class CoffeeController {

  private final CoffeeService service;

  @Autowired
  public CoffeeController(CoffeeService service) {
    this.service = service;
  }

  @GetMapping
  public List<Coffee> getListCoffee() {
    return service.getAll();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Coffee> makeCoffee(
      @Validated(View.Web.class) @RequestBody Coffee coffee) {
    Coffee created = service.create(coffee);
    URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/coffee" + "/{id}")
        .buildAndExpand(created.getId()).toUri();
    return ResponseEntity.created(uriOfNewResource).body(created);
  }


  @GetMapping("/coffee/{id}")
  public Coffee getCoffee(@PathVariable int id) {
    return service.get(id);
  }
/**
 * only for restfull crud*/

 /* @DeleteMapping("/coffee/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void drinkCoffee(@PathVariable int id) {
    service.delete(id);
  }*/
}
