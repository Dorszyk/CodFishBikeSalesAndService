package com.codfish.bikeSalesAndService.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BikeToServiceDTO {

    private String serial;
    private String brand;
    private String model;
    private Integer year;
}

