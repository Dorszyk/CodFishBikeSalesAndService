package com.codfish.bikeSalesAndService.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
@EqualsAndHashCode(of = "addressId")
@ToString(of = {"addressId", "country", "city", "postalCode", "address", "houseNumber", "apartmentNumber"})
public class Address {

    Integer addressId;
    String country;
    String city;
    String postalCode;
    String address;
    String houseNumber;
    String apartmentNumber;
    Customer customer;
}
