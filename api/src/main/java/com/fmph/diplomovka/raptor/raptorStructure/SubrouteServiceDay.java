package com.fmph.diplomovka.raptor.raptorStructure;

import com.fmph.diplomovka.model.ServiceDay;
import com.fmph.diplomovka.raptor.dataStructure.models.Subroute;
import java.util.Objects;

public class SubrouteServiceDay {

  private Subroute subroute;
  private ServiceDay serviceDay;

  public SubrouteServiceDay(Subroute subroute, ServiceDay serviceDay) {
    this.subroute = subroute;
    this.serviceDay = serviceDay;
  }

  public Subroute getSubroute() {
    return subroute;
  }

  public void setSubroute(Subroute subroute) {
    this.subroute = subroute;
  }

  public ServiceDay getServiceDay() {
    return serviceDay;
  }

  public void setServiceDay(ServiceDay serviceDay) {
    this.serviceDay = serviceDay;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    SubrouteServiceDay that = (SubrouteServiceDay) o;
    return Objects.equals(subroute, that.subroute) &&
        Objects.equals(serviceDay, that.serviceDay);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subroute, serviceDay);
  }

}
