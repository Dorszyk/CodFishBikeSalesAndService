package com.codfish.bikeSalesAndService.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BikeHistoryDTO {

    private String bikeSerial;
    private List<ServiceRequestDTO> bikeServiceRequests;


    public static BikeHistoryDTO buildDefault() {
        return BikeHistoryDTO.builder()
                .bikeSerial("empty")
                .bikeServiceRequests(Collections.emptyList())
                .build();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ServiceRequestDTO {
        private String bikeServiceRequestNumber;
        private String receivedDateTime;
        private String completedDateTime;
        private String customerComment;
        private List<ServiceDTO> services;
        private List<PartDTO> parts;
    }
}
