package com.codfish.bikeSalesAndService.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class BikePurchaseRequest {

    String existingCustomerEmail;
    String customerName;
    String customerSurname;
    String customerPhone;
    String customerEmail;
    String customerAddressCountry;
    String customerAddressCity;
    String customerAddressPostalCode;
    String customerAddressStreet;
    String customerAddressHouseNumber;
    String customerAddressApartmentNumber;
    String bikeSerial;
    String salesmanCodeNameSurname;
}