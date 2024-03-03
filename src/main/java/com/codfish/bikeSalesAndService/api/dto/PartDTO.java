package com.codfish.bikeSalesAndService.api.dto;

import jakarta.validation.constraints.Size;
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
    @Size(min = 3, max = 32)
    private String serialNumber;
    @Size(min = 3, max = 132)
    private String description;
    private BigDecimal price;

}
