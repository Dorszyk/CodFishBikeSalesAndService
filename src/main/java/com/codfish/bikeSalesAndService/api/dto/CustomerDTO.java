package com.codfish.bikeSalesAndService.api.dto;


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

public class CustomerDTO {
    private Integer customerId;

    @Size(min = 3, max = 32)
    private String name;


    @Size(min = 3, max = 36)
    @Pattern(regexp = "[a-zA-ZąęółśżźćńĄĘÓŁŚŻŹĆŃ-]{3,36}")
    private String surname;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private String email;
    @Size
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String phone;
    private String country;
    private String city;
    @Pattern(regexp = "^\\d{2}-\\d{3}$")
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

    private static String generateRandomName() {
        String[] name = {
                "Lena", "Grzegorz", "Izabela", "Szymon", "Oliwia",
                "Filip", "Justyna", "Damian", "Nikola", "Łukasz",
                "Julia", "Kamil", "Patrycja", "Bartosz", "Weronika",
                "Emilia", "Rafał", "Gabriela", "Maciej", "Klaudia",
                "Amelia", "Norbert", "Marta", "Konrad", "Aneta"
        };
        return name[new Random().nextInt(name.length)];
    }

    private static String generateRandomSurname() {
        String[] surname = {
                "Mazur", "Kubiak", "Kalinowski", "Zając", "Leszczyński",
                "Jabłoński", "Król", "Majewski", "Olszewski", "Jaworski",
                "Wróbel", "Malinowski", "Sikora", "Baran", "Rutkowski",
                "Michalak", "Szewczyk", "Ostrowski", "Tomaszewski", "Pietrzak",
                "Marciniak", "Wróblewski", "Zalewski", "Jakubowski", "Jasiński"
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

        String[] domains = {"mailbox.net", "myinbox.org", "emailworld.com", "digitalmail.net"};
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
                "Toruń", "Opole", "Zielona Góra", "Elbląg", "Płock",
                "Wałbrzych", "Tarnów", "Chorzów", "Kalisz", "Koszalin",
                "Legnica", "Grudziądz", "Słupsk", "Jaworzno", "Jelenia Góra",
                "Nowy Sącz", "Konin", "Piotrków Trybunalski", "Inowrocław", "Suwałki"
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
                "Akacjowa", "Wierzbowa", "Topolowa", "Kasztanowa", "Łąkowa",
                "Różana", "Bukowa", "Jesionowa", "Żurawia", "Klonowa",
                "Jagodowa", "Cisowa", "Malinowa", "Modrzewiowa", "Orzechowa",
                "Platanowa", "Rybacka", "Srebrna", "Tulipanowa", "Wrzosowa"
        };
        return streets[new Random().nextInt(streets.length)];
    }

    private static String generateRandomAddressHouseNumber() {
        return String.valueOf(new Random().nextInt(100) + 1);
    }

    private static String generateRandomAddressApartmentNumber() {
        return String.valueOf(new Random().nextInt(50) + 1);
    }

    public static CustomerDTO buildDefault() {
        return CustomerDTO.builder()
                .name(generateRandomName())
                .surname(generateRandomSurname())
                .phone(generateRandomPhone())
                .email(generateRandomEmail())
                .country(generateRandomAddressCountry())
                .city(generateRandomAddressCity())
                .postalCode(generateRandomAddressPostalCode())
                .address(generateRandomAddressStreet())
                .houseNumber(generateRandomAddressHouseNumber())
                .apartmentNumber(generateRandomAddressApartmentNumber())
                .build();
    }
}
