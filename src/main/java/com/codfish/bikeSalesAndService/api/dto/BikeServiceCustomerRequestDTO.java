package com.codfish.bikeSalesAndService.api.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BikeServiceCustomerRequestDTO {

    private String existingCustomerEmail;

    private String customerName;
    private String customerSurname;
    @Size
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
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
                .existingBikeSerial("CUBE633101,9030202422,UA#42949802")
                .customerComment("Przegląd podstawowy po zakupie nowego roweru i przejachaniu 100km")
                .customerName("Piotr")
                .customerSurname("Dorsz")
                .customerPhone("+48 777 777 777")
                .customerEmail("piotr.dorsz777@gmail.com")
                .customerAddressCountry("Polska")
                .customerAddressCity("Wrocław")
                .customerAddressPostalCode("77-777")
                .customerAddressStreet("Niebieskiego Nieba")
                .customerAddressHouseNumber("77")
                .customerAddressApartmentNumber("7")
                .build();

    }

    public boolean isNewBikeCandidate() {
        return isNullOrEmpty(existingCustomerEmail) && isNullOrEmpty(existingBikeSerial);
    }

    private static boolean isNullOrEmpty(String existingBikeSerialCustomerEmail) {
        return existingBikeSerialCustomerEmail == null || existingBikeSerialCustomerEmail.isBlank();
    }

    private static void addFieldToMap(Map<String, String> map, String fieldName, String value) {
        if (value != null) {
            map.put(fieldName, value);
        }
    }

    public Map<String, String> asMap() {
        Map<String, String> result = new HashMap<>();
        addFieldToMap(result, "existingCustomerEmail", existingCustomerEmail);
        addFieldToMap(result, "existingBikeSerial", existingBikeSerial);
        addFieldToMap(result, "customerComment", customerComment);
        addFieldToMap(result, "customerName", customerName);
        addFieldToMap(result, "customerSurname", customerSurname);
        addFieldToMap(result, "customerPhone", customerPhone);
        addFieldToMap(result, "customerEmail", customerEmail);
        addFieldToMap(result, "customerAddressCountry", customerAddressCountry);
        addFieldToMap(result, "customerAddressCity", customerAddressCity);
        addFieldToMap(result, "customerAddressPostalCode", customerAddressPostalCode);
        addFieldToMap(result, "customerAddressStreet", customerAddressStreet);
        addFieldToMap(result, "customerAddressHouseNumber", customerAddressHouseNumber);
        addFieldToMap(result, "customerAddressApartmentNumber", customerAddressApartmentNumber);
        return result;
    }


}
