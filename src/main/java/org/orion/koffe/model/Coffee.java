package org.orion.koffe.model;


import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "coffee")
public class Coffee extends AbstractBaseEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private CoffeeType coffeeType;
  @Column(name = "date_time", nullable = false)
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime dateTime = LocalDateTime.now();
  @Column(name = "sugar")
  @Min(0)
  @Max(10)
  private Integer sugar;

  public Coffee(CoffeeType coffeeType, LocalDateTime dateTime, Integer sugar) {
    this.coffeeType = coffeeType;
    this.dateTime = dateTime;
    this.sugar = sugar;

  }

  public Coffee(CoffeeType coffeeType, Integer sugar) {
    this.coffeeType = coffeeType;
    this.sugar = sugar;

  }

  public Coffee(Integer id, CoffeeType coffeeType, LocalDateTime dateTime, Integer sugar) {
    super(id);
    this.coffeeType = coffeeType;
    this.dateTime = dateTime;
    this.sugar = sugar;
  }

  public Coffee() {

  }

  public CoffeeType getCoffeeType() {
    return coffeeType;
  }

  public void setCoffeeType(CoffeeType coffeeType) {
    this.coffeeType = coffeeType;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public Integer getSugar() {
    return sugar;
  }

  public void setSugar(Integer sugar) {
    this.sugar = sugar;
  }

  @Override
  public String toString() {
    return "Coffee{" +
        "coffeeType=" + coffeeType +
        ", dateTime=" + dateTime +
        ", sugar=" + sugar +
        ", id=" + id +
        '}';
  }
}
