package com.codfish.bikeSalesAndService.domain;


import jakarta.persistence.Column;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
@EqualsAndHashCode(of = "servicePartId")
@ToString(of = {"servicePartId", "quantity"})
public class ServicePart {

     Integer servicePartId;
     Integer quantity;
     BikeServiceRequest bikeServiceRequest;
     Part part;
}
