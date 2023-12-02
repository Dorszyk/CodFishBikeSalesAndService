package com.codfish.bikeSalesAndService.domain;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.With;

import java.time.OffsetDateTime;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "bikeServiceRequestId")
@ToString(of = {"bikeServiceRequestId", "bikeServiceRequestNumber", "receivedDateTime", "completedDateTime", "customerComment"})
public class BikeServiceRequest {

    Integer bikeServiceRequestId;
    String bikeServiceRequestNumber;
    OffsetDateTime receivedDateTime;
    OffsetDateTime completedDateTime;
    String customerComment;
    Customer customer;
    BikeToService bike;
    Set<ServicePerson> servicePersons;
    Set<ServicePart> servicePart;
}
