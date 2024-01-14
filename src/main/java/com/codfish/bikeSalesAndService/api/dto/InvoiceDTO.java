package com.codfish.bikeSalesAndService.api.dto;

import com.codfish.bikeSalesAndService.infrastructure.database.entity.BikeToBuyEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.CustomerEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.SalesmanEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {


    private Integer invoiceId;
    private String invoiceNumber;
    private OffsetDateTime dateTime;
    private BikeToBuyEntity bike;
    private CustomerEntity customer;
    private SalesmanEntity salesman;

}