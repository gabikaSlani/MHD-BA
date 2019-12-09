package com.fmph.diplomovka.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "stops")
public class Stop implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "zone", nullable = false, columnDefinition = "int default 100")
    private Integer zone;

    @OneToOne(mappedBy = "stop", cascade = CascadeType.ALL)
    private Coords coords;

    @Column(name = "on_request", nullable = false, columnDefinition = "boolean default false")
    private Boolean onRequest;

    @OneToMany(mappedBy = "stop", cascade = CascadeType.ALL)
    private Set<StopTime> stopTimes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getZone() {
        return zone;
    }

    public void setZone(Integer zone) {
        this.zone = zone;
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public Boolean getOnRequest() {
        return onRequest;
    }

    public void setOnRequest(Boolean onRequest) {
        this.onRequest = onRequest;
    }

    public Set<StopTime> getStopTimes() {
        return stopTimes;
    }

    public void setStopTimes(Set<StopTime> stopTimes) {
        this.stopTimes = stopTimes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stop stop = (Stop) o;
        return id.equals(stop.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
