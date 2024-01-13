package com.codfish.bikeSalesAndService.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Random;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BikeServicePersonProcessingUnitDTO {

    private String personRepairingCodeNameSurname;
    private String bikeSerial;
    private List<String> partSerialNumber;
    private String description;
    private Integer partQuantity;
    private String serviceCode;
    private Integer hours;
    private String personRepairingComment;
    private Boolean done;

    private static String generateRandomPersonRepairingComment() {
        String[] repairs = {
                "Dokręcono luzy na kierownicy",
                "Nasmarowano łańcuch",
                "Wymieniono zużyte klocki hamulcowe",
                "Wyregulowano przerzutki",
                "Naprawiono uszkodzoną dętkę",
                "Zastąpiono uszkodzone szprychy",
                "Czyszczenie i konserwacja piast",
                "Wymiana łożysk w support",
                "Naprawa systemu elektrycznego w rowerze elektrycznym",
                "Wymiana starych opon na nowe"
        };

        String[] services = {
                "Przegląd ogólny roweru",
                "Serwis hamulców",
                "Serwis zawieszenia",
                "Regulacja systemu napędowego",
                "Serwis kół",
                "Wymiana części eksploatacyjnych",
                "Serwis rowerów elektrycznych",
                "Aktualizacja oprogramowania roweru elektrycznego",
                "Profesjonalne czyszczenie roweru",
                "Indywidualne dostosowanie ustawień roweru do użytkownika"
        };

        Random random = new Random();
        String repair = repairs[random.nextInt(repairs.length)];
        String service = services[random.nextInt(services.length)];

        return repair + ". " + service + " wykonana.";
    }

    public static BikeServicePersonProcessingUnitDTO buildDefault(){
        return BikeServicePersonProcessingUnitDTO.builder()
                .partQuantity(1)
                .hours(1)
                .personRepairingComment(generateRandomPersonRepairingComment())
                .done(true)
                .build();
    }
}
