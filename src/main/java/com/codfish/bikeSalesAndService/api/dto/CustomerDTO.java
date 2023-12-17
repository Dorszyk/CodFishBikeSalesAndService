package com.codfish.bikeSalesAndService.api.dto;


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

public class CustomerDTO {
    private Integer customerId;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String country;
    private String city;
    private String postalCode;
    private String address;
    private String houseNumber;
    private String apartmentNumber;

    public Map<String, String> asMap() {
        Map<String, String> result = new HashMap<>();
        if (customerId != null) result.put("customerId", customerId.toString());
        if (name != null) result.put("name", name);
        if (surname != null) result.put("surname", surname);
        if (email != null) result.put("email", email);
        if (phone != null) result.put("phone", phone);
        if (country != null) result.put("country", country);
        if (city != null) result.put("city", city);
        if (postalCode != null) result.put("postalCode", postalCode);
        if (address != null) result.put("address", address);
        if (houseNumber != null) result.put("houseNumber", houseNumber);
        if (apartmentNumber != null) result.put("apartmentNumber", apartmentNumber);
        return result;
    }
}
