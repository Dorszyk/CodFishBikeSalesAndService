package com.codfish.bikeSalesAndService.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.With;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "email")
@ToString(of = {"customerId", "name", "surname", "email"})
public class Customer {

    Integer customerId;
    String name;
    String surname;
    String phone;
    String email;
    Address address;

    Set<Invoice> invoices;
    Set<BikeServiceRequest> bikeServiceRequests;
    public Set<Invoice> getInvoice() {
        return Objects.isNull(invoices) ? new HashSet<>() : invoices;
    }
    public Set<BikeServiceRequest> getBikeServiceRequests(){
        return Objects.isNull(bikeServiceRequests)? new HashSet<>(): bikeServiceRequests;
    }
}
