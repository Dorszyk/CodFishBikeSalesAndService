package com.codfish.bikeSalesAndService.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

        public BigDecimal getTotalPartsPrice() {
            return parts.stream()
                    .map(PartDTO::getPrice)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        public BigDecimal getTotalServicePrice() {
            return services.stream()
                    .map(ServiceDTO::getPrice)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        public BigDecimal getTotalPrice() {
            BigDecimal totalPartsPrice = getTotalPartsPrice();
            BigDecimal totalServicePrice = getTotalServicePrice();
            return totalPartsPrice.add(totalServicePrice);
        }
    }
}
