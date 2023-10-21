package com.codfish.bikeSalesAndService.domain;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.List;

@Value
@Builder
@ToString(of = "bikeSerial")
public class BikeHistory {

    String bikeSerial;
    List<BikeServiceRequest> bikeServiceRequests;

    @Value
    @Builder
    @ToString(of = {"bikeServiceRequestNumber", "receivedDataTime", "completedDataTime", "customerComment"})
    public static class BikeServiceRequest {

        String bikeServiceRequestNumber;
        OffsetDateTime receivedDataTime;
        OffsetDateTime completedDataTime;
        String customerComment;
        List<Service> services;
        List<Part> parts;
    }
}
