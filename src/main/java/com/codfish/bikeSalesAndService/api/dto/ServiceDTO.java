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
public class ServiceDTO {

    private Integer serviceId;
    @Size(min = 3, max = 32)
    private String serviceCode;
    @Size(min = 3, max = 132)
    private String description;
    private BigDecimal price;
}
