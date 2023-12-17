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
public class PartDTO {

    private Integer partId;
    private String serialNumber;
    private String description;
    private BigDecimal price;

}
