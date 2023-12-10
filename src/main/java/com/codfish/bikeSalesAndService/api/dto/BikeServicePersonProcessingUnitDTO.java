package com.codfish.bikeSalesAndService.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BikeServicePersonProcessingUnitDTO {

    private String personRepairingCodeNameSurname;
    private String bikeSerial;
    private String partSerialNumber;
    private String description;
    private Integer partQuantity;
    private String serviceCode;
    private Integer hours;
    private String personRepairingComment;
    private Boolean done;
    public static BikeServicePersonProcessingUnitDTO buildDefault(){
        return BikeServicePersonProcessingUnitDTO.builder()
                .partQuantity(1)
                .hours(1)
                .personRepairingComment("Serwis rowerowy wykonany. Dokręcono luzy na kierownicy oraz nasmarowano łańcuch.")
                .done(true)
                .build();
    }
}
