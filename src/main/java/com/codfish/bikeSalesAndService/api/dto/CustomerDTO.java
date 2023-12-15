package com.codfish.bikeSalesAndService.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    public Map<String, String> asMap(){
        Map<String, String> result = new HashMap<>();
        Optional.ofNullable(customerId).ifPresent(value -> result.put("customerId",value.toString()));
        Optional.ofNullable(name).ifPresent(value -> result.put("name",value.toString()));
        Optional.ofNullable(surname).ifPresent(value -> result.put("surname",value.toString()));
        Optional.ofNullable(email).ifPresent(value -> result.put("email",value.toString()));
        Optional.ofNullable(phone).ifPresent(value -> result.put("phone",value.toString()));
        Optional.ofNullable(country).ifPresent(value -> result.put("country", value.toString()));
        Optional.ofNullable(city).ifPresent(value -> result.put("city", value.toString()));
        Optional.ofNullable(postalCode).ifPresent(value -> result.put("postalCode", value.toString()));
        Optional.ofNullable(address).ifPresent(value -> result.put("address",value.toString()));
        Optional.ofNullable(houseNumber).ifPresent(value -> result.put("houseNumber", value.toString()));
        Optional.ofNullable(apartmentNumber).ifPresent(value -> result.put("apartmentNumber", value.toString()));

        return result;
    }

}
