package com.codfish.bikeSalesAndService.domain;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.With;

import java.time.OffsetDateTime;

@With
@Value
@Builder
@EqualsAndHashCode(of = "invoiceNumber")
@ToString(of = {"invoiceId", "invoiceNumber", "dateTime"})
public class Invoice {

    Integer invoiceId;
    String invoiceNumber;
    OffsetDateTime dateTime;
    BikeToBuy bike;
    Customer customer;
    Salesman salesman;
}
