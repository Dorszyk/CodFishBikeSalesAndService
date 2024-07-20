package com.codfish.bikeSalesAndService.domain;

import lombok.*;

import java.util.Objects;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "serial")
@ToString(of = {"bikeToServiceId", "serial", "brand", "model", "year"})
public class BikeToService {

    Integer bikeToServiceId;
    String serial;
    String brand;
    String model;
    Integer year;

    Set<BikeServiceRequest> bikeServiceRequests;

    public boolean shouldExistsInBikeToBuy() {
        return Objects.nonNull(serial)
                && Objects.isNull(brand)
                && Objects.isNull(model)
                && Objects.isNull(year);
    }
}