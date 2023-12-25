package com.codfish.bikeSalesAndService.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonRepairingDTO {

    Integer personRepairingId;
    String name;
    String surname;
    String codeNameSurname;
    Integer userId;
    String userName;
    String email;
    String password;
    String roles;
}
