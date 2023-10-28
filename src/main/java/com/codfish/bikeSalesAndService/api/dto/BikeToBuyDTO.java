package com.codfish.bikeSalesAndService.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BikeToBuyDTO {

    private String bikeSerial;
    private String brand;
    private String model;
    private Integer year;
    private String color;
    private BigDecimal price;

}