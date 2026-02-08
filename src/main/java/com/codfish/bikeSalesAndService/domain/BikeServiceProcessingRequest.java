package com.codfish.bikeSalesAndService.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.List;
import java.util.Objects;

@With
@Value
@Builder
public class BikeServiceProcessingRequest {

    String personRepairingCodeNameSurname;
    String bikeSerial;
    List<PartEntry> parts;
    List<ServiceEntry> services;
    String description;
    Integer hours;
    String comment;
    Boolean done;

    @Value
    @Builder
    public static class PartEntry {
        String partSerialNumber;
        Integer partQuantity;
    }

    @Value
    @Builder
    public static class ServiceEntry {
        String serviceCode;
        Integer hours;
    }

    public boolean partsIncluded() {
        return Objects.nonNull(parts) && !parts.isEmpty();
    }

    public boolean servicesIncluded() {
        return Objects.nonNull(services) && !services.isEmpty();
    }
}
