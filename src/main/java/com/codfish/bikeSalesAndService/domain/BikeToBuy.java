package com.codfish.bikeSalesAndService.domain;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;

@With
@Value
@Builder
@EqualsAndHashCode
@ToString(of = {"bikeToBuyId","serial","model","year"})
public class BikeToBuy {

    Integer bikeToBuyId;
    String serial;
    String brand;
    String model;
    Integer year;
    String color;
    BigDecimal price;
    Invoice invoice;
}
