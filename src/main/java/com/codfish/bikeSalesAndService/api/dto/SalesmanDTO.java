package com.codfish.bikeSalesAndService.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesmanDTO {

    Integer salesmanId;
    String name;
    String surname;
    String codeNameSurname;
    Integer userId;
}
