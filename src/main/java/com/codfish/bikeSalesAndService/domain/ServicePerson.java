package com.codfish.bikeSalesAndService.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
@EqualsAndHashCode(of = "servicePersonId")
@ToString(of = {"servicePersonId", "hours", "comment"})
public class ServicePerson {

    Integer servicePersonId;
    Integer hours;
    String comment;
    BikeServiceRequest bikeServiceRequest;
    PersonRepairing personRepairing;
    Service service;

}
