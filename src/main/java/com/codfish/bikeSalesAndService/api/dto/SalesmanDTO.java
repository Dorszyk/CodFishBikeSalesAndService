package com.codfish.bikeSalesAndService.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesmanDTO {

    private Integer salesmanId;

    @Size(min = 3, max = 32)
    private String name;

    @Size(min = 3, max = 32)
    @Pattern(regexp = "[a-zA-ZąęółśżźćńĄĘÓŁŚŻŹĆŃ-]{3,32}")
    private String surname;

    @Size(min = 3, max = 10)
    @Pattern(regexp = "SAL\\d{5}[A-Z]{2}")
    private String codeNameSurname;

    private Integer userId;


    @Size(min = 3, max = 50)
    private String userName;


    @NotBlank
    @Size(min = 3, max = 132)
    @Email
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private String email;

    @NotBlank
    @Size(min = 8, max = 132)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,132}$")
    private String password;
    private String roles;
}
