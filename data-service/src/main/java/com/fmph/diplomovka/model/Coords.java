package com.fmph.diplomovka.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "coords")
public class Coords implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(nullable = false)
  private Long id;

  @Column(name = "latitude", nullable = false)
  private Double latitude;

  @Column(name = "longitude", nullable = false)
  private Double longitude;

  @OneToOne
  @MapsId
  private Stop stop;

  public Coords() {
  }

  public Coords(Double latitude, Double longitude, Stop stop) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.stop = stop;
  }

  public Coords(Long id, Double latitude, Double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.id = id;
  }

  public Coords(Long id, Double latitude, Double longitude, Stop stop) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.id = id;
    this.stop = stop;
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double lattitude) {
    this.latitude = lattitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Stop getStop() {
    return stop;
  }

  public void setStop(Stop stop) {
    this.stop = stop;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    Coords coords = (Coords) o;
    return id.equals(coords.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
