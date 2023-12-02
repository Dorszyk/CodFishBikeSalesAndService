package com.codfish.bikeSalesAndService.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BikeServiceRequestDTO {

    private String bikeServiceRequestNumber;
    private String receivedDateTime;
    private String completedDateTime;
    private String customerComment;
    private String bikeSerial;
}

