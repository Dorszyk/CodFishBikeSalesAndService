package com.codfish.bikeSalesAndService.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BikePurchaseDTO {

    @Email
    private String existingCustomerEmail;

    private String customerName;
    private String customerSurname;

    @Size
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String customerPhone;
    @Email
    private String customerEmail;
    private String customerAddressCountry;
    private String customerAddressCity;
    private String customerAddressPostalCode;
    private String customerAddressStreet;
    private String customerAddressHouseNumber;
    private String customerAddressApartmentNumber;

    private String bikeSerial;
    private String salesmanCodeNameSurname;

    public static BikePurchaseDTO buildDefaultData() {
        return BikePurchaseDTO.builder()
                .customerName("Joanna")
                .customerSurname("Malinowska")
                .customerPhone("+48 508 581 441")
                .customerEmail("joanna.malinowska@gmail.com")
                .customerAddressCountry("Polska")
                .customerAddressCity("Gdańsk")
                .customerAddressPostalCode("80-180")
                .customerAddressStreet("Piotrkowska")
                .customerAddressHouseNumber("10")
                .customerAddressApartmentNumber("15")
                .build();
    }

    private static void addFieldToMap(Map<String, String> map, String fieldName, String value) {
        if (value != null) {
            map.put(fieldName, value);
        }
    }

    public Map<String, String> asMap() {
        Map<String, String> result = new HashMap<>();

        addFieldToMap(result, "customerName", customerName);
        addFieldToMap(result, "customerSurname", customerSurname);
        addFieldToMap(result, "customerPhone", customerPhone);
        addFieldToMap(result, "customerEmail", customerEmail);
        addFieldToMap(result, "existingCustomerEmail", existingCustomerEmail);
        addFieldToMap(result, "customerAddressCountry", customerAddressCountry);
        addFieldToMap(result, "customerAddressCity", customerAddressCity);
        addFieldToMap(result, "customerAddressPostalCode", customerAddressPostalCode);
        addFieldToMap(result, "customerAddressStreet", customerAddressStreet);
        addFieldToMap(result, "customerAddressHouseNumber", customerAddressHouseNumber);
        addFieldToMap(result, "customerAddressApartmentNumber", customerAddressApartmentNumber);
        addFieldToMap(result, "bikeSerial", bikeSerial);
        addFieldToMap(result, "salesmanCodeNameSurname", salesmanCodeNameSurname);

        return result;
    }

}
