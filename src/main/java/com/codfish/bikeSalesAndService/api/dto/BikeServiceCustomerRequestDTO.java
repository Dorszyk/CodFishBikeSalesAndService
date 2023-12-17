package com.codfish.bikeSalesAndService.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BikeServiceCustomerRequestDTO {

    @Email
    private String existingCustomerEmail;

    private String customerName;
    private String customerSurname;
    @Size
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String customerPhone;
    private String customerEmail;
    private String customerAddressCountry;
    private String customerAddressCity;
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

    public static BikeServiceCustomerRequestDTO buildDefault() {
        return BikeServiceCustomerRequestDTO.builder()
                .existingCustomerEmail("joanna.malinowska@gmail.com")
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
                .customerComment("Przegląd podstawowy po zakupie nowego roweru i przejachaniu 100km\nCentrowanie koła\nWymiana płynu hamulcowego\nRegulacja przerzutki")
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

    private static void addFieldToMap(Map<String, String> map, String fieldName, String value) {
        if (value != null) {
            map.put(fieldName, value);
        }
    }

    public Map<String, String> asMap() {
        Map<String, String> result = new HashMap<>();
        addFieldToMap(result, "existingCustomerEmail", existingCustomerEmail);
        addFieldToMap(result, "customerComment", customerComment);
        addFieldToMap(result, "customerName", customerName);
        addFieldToMap(result, "customerSurname", customerSurname);
        addFieldToMap(result, "customerPhone", customerPhone);
        addFieldToMap(result, "customerEmail", customerEmail);
        addFieldToMap(result, "customerAddressCountry", customerAddressCountry);
        addFieldToMap(result, "customerAddressCity", customerAddressCity);
        addFieldToMap(result, "customerAddressPostalCode", customerAddressPostalCode);
        addFieldToMap(result, "customerAddressStreet", customerAddressStreet);
        addFieldToMap(result, "customerAddressHouseNumber", customerAddressHouseNumber);
        addFieldToMap(result, "customerAddressApartmentNumber", customerAddressApartmentNumber);
        addFieldToMap(result, "existingBikeSerial", existingBikeSerial);
        addFieldToMap(result, "bikeSerial", bikeSerial);
        addFieldToMap(result, "bikeBrand", bikeBrand);
        addFieldToMap(result, "bikeModel", bikeModel);
        addFieldToMap(result, "bikeYear", String.valueOf(bikeYear));
        return result;
    }

}
