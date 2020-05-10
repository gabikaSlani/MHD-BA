package com.fmph.diplomovka.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "stop_areas")
public class StopArea implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @OneToMany(mappedBy = "stopArea", cascade = CascadeType.ALL)
  private Set<Stop> stops = new HashSet<>();

  public StopArea() {
  }

  public StopArea(String name) {
    this.name = name;
  }

  public StopArea(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public StopArea(Long id, String name, Set<Stop> stops) {
    this.id = id;
    this.name = name;
    this.stops = stops;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Stop> getStops() {
    return stops;
  }

  public void setStops(Set<Stop> stops) {
    this.stops = stops;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
