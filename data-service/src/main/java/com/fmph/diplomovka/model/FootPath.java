package com.fmph.diplomovka.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "foot_paths")
public class FootPath implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(nullable = false)
  private Long id;

  @Column(name = "time", nullable = false)
  private Integer time;

  @ManyToOne(optional = true)
  @JoinColumn(name = "from_stop", nullable = false)
  private Stop fromStop;

  @ManyToOne(optional = true)
  @JoinColumn(name = "to_stop", nullable = false)
  private Stop toStop;

  public FootPath(Stop fromStop, Stop toStop, Integer time) {
    this.time = time;
    this.fromStop = fromStop;
    this.toStop = toStop;
  }

  public FootPath() {
  }

  public FootPath(Long id, Stop fromStop, Stop toStop, Integer time) {
    this.id = id;
    this.time = time;
    this.fromStop = fromStop;
    this.toStop = toStop;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getTime() {
    return time;
  }

  public void setTime(Integer time) {
    this.time = time;
  }

  public Stop getFromStop() {
    return fromStop;
  }

  public void setFromStop(Stop fromStop) {
    this.fromStop = fromStop;
  }

  public Stop getToStop() {
    return toStop;
  }

  public void setToStop(Stop toStop) {
    this.toStop = toStop;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    FootPath footPath = (FootPath) o;
    return id.equals(footPath.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
