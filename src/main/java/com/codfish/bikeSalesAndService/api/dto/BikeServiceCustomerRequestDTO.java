package com.codfish.bikeSalesAndService.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BikeServiceCustomerRequestDTO {

    private String existingCustomerEmail;

    private String customerName;
    private String customerSurname;
    private String customerPhone;
    private String customerEmail;
    private String customerAddressCountry;
    private String customerAddressCity;
    private String customerAddressPostalCode;
    private String customerAddressStreet;
    private String customerAddressHouseNumber;
    private String customerAddressApartmentNumber;

    private String existingBikeSerial;
    private String bikeSerial;
    private String bikeBrand;
    private String bikeModel;
    private Integer bikeYear;

    private String customerComment;

    public static BikeServiceCustomerRequestDTO buildDefault() {
        return BikeServiceCustomerRequestDTO.builder()
                .existingCustomerEmail("joanna.malinowska@gmail.com")
                .existingBikeSerial("CUBE633101")
                .customerComment("Przegląd podstawowy po zakupie nowego roweru i przejachaniu 100km")
                .build();
    }
    public boolean isNewBikeCandidate() {
        return isNullOrEmpty(existingCustomerEmail) && isNullOrEmpty(existingBikeSerial);
    }
    private static boolean isNullOrEmpty(String existingBikeSerialCustomerEmail) {
        return existingBikeSerialCustomerEmail == null || existingBikeSerialCustomerEmail.isBlank();
    }


}
