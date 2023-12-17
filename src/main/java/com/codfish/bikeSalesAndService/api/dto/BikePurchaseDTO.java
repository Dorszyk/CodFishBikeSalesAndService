package com.codfish.bikeSalesAndService.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

        return new Random().nextInt(90) + 10 + "-" + new Random().nextInt(900);
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

    public static BikePurchaseDTO buildDefaultData() {
        return BikePurchaseDTO.builder()
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
