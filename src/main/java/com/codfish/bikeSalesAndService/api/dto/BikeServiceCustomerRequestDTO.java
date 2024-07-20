package com.codfish.bikeSalesAndService.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BikeServiceCustomerRequestDTO {

    @Email
    private String existingCustomerEmail;

    @Size(min = 3, max = 32)
    private String customerName;

    @Size(min = 3, max = 36)
    @Pattern(regexp = "[a-zA-ZąęółśżźćńĄĘÓŁŚŻŹĆŃ-]{3,36}")
    private String customerSurname;

    @Size
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String customerPhone;
    @Email
    private String customerEmail;
    private String customerAddressCountry;
    private String customerAddressCity;
    @Pattern(regexp = "^\\d{2}-\\d{3}$")
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

    private static String generateRandomName() {
        String[] name = {
                "Anna", "Krzysztof", "Maria", "Paweł", "Agnieszka",
                "Jan", "Barbara", "Tomasz", "Katarzyna", "Andrzej",
                "Ewa", "Marcin", "Magdalena", "Jacek", "Monika",
                "Zuzanna", "Piotr", "Dorota", "Michał", "Aleksandra",
                "Maja", "Jakub", "Natalia", "Mateusz", "Karolina"
        };
        return name[new Random().nextInt(name.length)];
    }

    private static String generateRandomSurname() {
        String[] surname = {
                "Nowak", "Kowalski", "Wiśniewski", "Dąbrowski", "Lewandowski",
                "Wójcik", "Kamiński", "Kaczmarek", "Zieliński", "Szymański",
                "Woźniak", "Kozłowski", "Jankowski", "Wojciechowski", "Kwiatkowski",
                "Krawczyk", "Kaczmarczyk", "Piotrowski", "Grabowski", "Nowakowski",
                "Pawłowski", "Michalski", "Nowicki", "Adamczyk", "Dudek"
        };
        return surname[new Random().nextInt(surname.length)];
    }

    private static String removePolishCharacters(String input) {
        return input
                .replaceAll("ą", "a")
                .replaceAll("ć", "c")
                .replaceAll("ę", "e")
                .replaceAll("ł", "l")
                .replaceAll("ń", "n")
                .replaceAll("ó", "o")
                .replaceAll("ś", "s")
                .replaceAll("ź", "z")
                .replaceAll("ż", "z");
    }

    private static String generateRandomEmail() {
        Random random = new Random();
        int randomNumber = random.nextInt(10000);

        String name = removePolishCharacters(generateRandomName().toLowerCase());
        String surname = removePolishCharacters(generateRandomSurname().toLowerCase());

        String[] domains = {"example.com", "sample.net", "demo.org", "testmail.com"};
        String domain = domains[random.nextInt(domains.length)];

        return name + "." + surname + randomNumber + "@" + domain;
    }

    private static String generateRandomPhone() {
        return "+48 " + (new Random().nextInt(900) + 100) + " " + (new Random().nextInt(900) + 100) + " " + (new Random().nextInt(900) + 100);
    }

    private static String generateRandomBikeSerial() {
        return "BS" + new Random().nextInt(10000);
    }

    private static String generateRandomBikeBrand() {
        String[] brands = {"Cube", "Specialized", "Giant", "Cannondale", "Scott", "Merida", "Author", "Kellys", "Cube"};
        return brands[new Random().nextInt(brands.length)];
    }

    private static String generateRandomBikeModel() {
        String[] models = {"Model1", "Model2", "Model3", "Model4", "Model5", "Model6", "Model7", "Model8", "Model9", "Model10"};
        return models[new Random().nextInt(models.length)];
    }

    private static Integer generateRandomBikeYear() {
        int currentYear = java.time.Year.now().getValue();
        return new Random().nextInt(currentYear - 2010 + 1) + 2010;
    }

    private static String generateRandomAddressCountry() {
        String[] countries = {"Polska"};
        return countries[new Random().nextInt(countries.length)];
    }

    private static String generateRandomAddressCity() {
        String[] cities = {
                "Warszawa", "Kraków", "Wrocław", "Poznań", "Gdańsk",
                "Szczecin", "Lublin", "Bydgoszcz", "Łódź", "Katowice",
                "Rzeszów", "Olsztyn", "Białystok", "Gdynia", "Częstochowa",
                "Radom", "Sosnowiec", "Kielce", "Gliwice", "Zabrze"
        };
        return cities[new Random().nextInt(cities.length)];
    }

    private static String generateRandomAddressPostalCode() {
        Random random = new Random();
        int firstPart = random.nextInt(90) + 10;
        int secondPart = random.nextInt(900) + 100;
        return firstPart + "-" + secondPart;
    }

    private static String generateRandomAddressStreet() {
        String[] streets = {
                "Kwiatowa", "Słoneczna", "Leśna", "Morska", "Górska",
                "Długa", "Krótka", "Ogrodowa", "Piękna", "Parkowa",
                "Sportowa", "Szkolna", "Wolności", "Lipowa", "Brzozowa",
                "Zielona", "Polna", "Klonowa", "Wiśniowa", "Sosnowa"
        };
        return streets[new Random().nextInt(streets.length)];
    }

    private static String generateRandomAddressHouseNumber() {
        return String.valueOf(new Random().nextInt(100) + 1);
    }

    private static String generateRandomAddressApartmentNumber() {
        return String.valueOf(new Random().nextInt(50) + 1);
    }

    private static String generateRandomCustomerComment() {
        String[] issues = {
                "Przegląd podstawowy po zakupie nowego roweru",
                "Centrowanie koła",
                "Wymiana płynu hamulcowego",
                "Regulacja przerzutki",
                "Sprawdzenie stanu łańcucha",
                "Naprawa uszkodzonej dętki",
                "Kontrola ciśnienia w oponach",
                "Wymiana zużytych klocków hamulcowych",
                "Regulacja hamulców",
                "Konserwacja amortyzatorów",
                "Kalibracja systemu elektrycznego w rowerze elektrycznym",
                "Wymiana i regulacja siodełka",
                "Naprawa lub wymiana przerwanej linki hamulcowej",
                "Regulacja kierownicy i systemu sterowania",
                "Naprawa lub wymiana uszkodzonych pedałów",
                "Wymiana łożysk w piastach kół",
                "Naprawa uszkodzeń ramy roweru",
                "Wymiana starych lub uszkodzonych opon",
                "Regulacja i konserwacja zawieszenia przedniego i tylnego",
                "Czyszczenie i konserwacja napędu roweru"
        };
        String[] additionalComments = {
                "Słychać dziwne dźwięki podczas jazdy",
                "Rower był niedawno używany w trudnych warunkach",
                "Mam problemy z przerzutkami przy wyższych prędkościach",
                "Hamulce piszczą przy mocnym hamowaniu",
                "Rower nie był serwisowany od dłuższego czasu",
                "Potrzebuję szybkiej naprawy przed nadchodzącym wyścigiem",
                "Zauważyłem wycieki oleju z amortyzatora",
                "Rower kupiony z drugiej ręki, chciałbym przegląd ogólny",
                "Chciałbym przygotować rower na sezon",
                "Potrzebuję porady w zakresie konserwacji roweru"
        };

        Random random = new Random();
        String issue = issues[random.nextInt(issues.length)];
        String additionalComment = additionalComments[random.nextInt(additionalComments.length)];

        return issue + ". " + additionalComment;
    }

    public static BikeServiceCustomerRequestDTO buildDefault() {
        return BikeServiceCustomerRequestDTO.builder()
                .existingCustomerEmail("")
                .existingBikeSerial("")
                .customerName(generateRandomName())
                .customerSurname(generateRandomSurname())
                .customerPhone(generateRandomPhone())
                .customerEmail(generateRandomEmail())
                .customerAddressCountry(generateRandomAddressCountry())
                .customerAddressCity(generateRandomAddressCity())
                .customerAddressPostalCode(generateRandomAddressPostalCode())
                .customerAddressStreet(generateRandomAddressStreet())
                .customerAddressHouseNumber(generateRandomAddressHouseNumber())
                .customerAddressApartmentNumber(generateRandomAddressApartmentNumber())
                .customerComment(generateRandomCustomerComment())
                .bikeSerial(generateRandomBikeSerial())
                .bikeBrand(generateRandomBikeBrand())
                .bikeModel(generateRandomBikeModel())
                .bikeYear(generateRandomBikeYear())
                .build();
    }

    public boolean isNewBikeCandidate() {
        return isNullOrEmpty(existingCustomerEmail) && isNullOrEmpty(existingBikeSerial);
    }

    private static boolean isNullOrEmpty(String existingBikeSerialCustomerEmail) {
        return existingBikeSerialCustomerEmail == null || existingBikeSerialCustomerEmail.isBlank();
    }
}