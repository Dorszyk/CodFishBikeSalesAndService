package com.codfish.bikeSalesAndService.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BikeToBuyDTO {

    private Integer bikeToBuyId;
    private String category;
    private String subcategory;
    private String serial;
    private String brand;
    private String model;
    private Integer year;
    private String color;
    private BigDecimal price;

    private static String generateRandomCategory() {
        String[] categories = {
                "Mountain Bike",
                "Road Bike",
                "Hybrid Bike",
                "Electric Bike",
                "BMX Bike",
                "Cyclocross Bike",
                "Gravel Bike",
                "Touring Bike",
                "Folding Bike",
                "Fat Bike",
                "Single Speed/Fixie Bike",
                "Track Bike",
                "Recumbent Bike",
                "Tandem Bike",
                "Kids' Bike",
                "Cargo Bike",
                "City Bike",
                "Commuter Bike",
                "Adventure Bike",
                "Triathlon Bike"
        };
        return categories[new Random().nextInt(categories.length)];
    }

    private static String generateRandomSubcategory() {
        String[] subcategories = {
                "Cross Country",
                "Downhill",
                "Endurance",
                "City Bike",
                "Trail",
                "All Mountain",
                "Freeride",
                "Dirt Jump",
                "Time Trial",
                "Triathlon",
                "Track Racing",
                "Cyclocross",
                "Gravel",
                "Touring",
                "Urban Commuter",
                "Folding",
                "Fat Tire",
                "Electric Road",
                "Electric Mountain",
                "Hybrid Electric",
                "Fitness",
                "Dual-Sport",
                "Cruiser",
                "Cargo",
                "Recumbent",
                "Tandem",
                "BMX Freestyle",
                "BMX Racing",
                "Kids' Mountain",
                "Kids' Road"
        };
        return subcategories[new Random().nextInt(subcategories.length)];
    }

    private static String generateRandomSerial() {
        return "SN" + new Random().nextInt(10000);
    }

    private static String generateRandomBrand() {
        String[] brands = {
                "Trek",
                "Specialized",
                "Giant",
                "Cannondale",
                "Scott",
                "Merida",
                "Cube",
                "Santa Cruz",
                "Bianchi",
                "GT",
                "Orbea",
                "Salsa",
                "Kona",
                "Surly",
                "Yeti",
                "Pivot",
                "BMC",
                "Cervélo",
                "Pinarello",
                "Lapierre",
                "Focus",
                "Raleigh",
                "Diamondback",
                "Norco",
                "Fuji"
        };
        return brands[new Random().nextInt(brands.length)];
    }

    private static String generateRandomModel() {
        String[] models = {
                "Rockhopper Comp",
                "Defy Advanced Pro",
                "Synapse Carbon Disc",
                "Spark RC 900",
                "Big Nine 300",
                "Reaction C:62 Pro",
                "Chameleon 7 S",
                "Impulso Allroad",
                "Zaskar LT Expert",
                "Rallon M-Team",
                "CrossTrail Elite",
                "Talon 29 2",
                "Stumpjumper ST Alloy",
                "Slash 9.8 XT",
                "Speedster 10",
                "Checkpoint ALR 5",
                "Trance 29 2",
                "Topstone Neo Carbon 3",
                "Epic Hardtail Comp",
                "Aspect 940",
                "Fathom 1",
                "Sirrus X 4.0",
                "Supersix Evo Hi-Mod",
                "Allez Sprint Comp",
                "Marlin 7"
        };
        return models[new Random().nextInt(models.length)];
    }

    private static Integer generateRandomYear() {
        return 2020 + new Random().nextInt(4);
    }

    private static String generateRandomColor() {
        String[] colors = {
                "Aqua Marine",
                "Coral Pink",
                "Lavender Purple",
                "Midnight Blue",
                "Neon Green",
                "Sunset Orange",
                "Electric Yellow",
                "Magenta",
                "Turquoise",
                "Charcoal Grey",
                "Fuchsia",
                "Lime Green",
                "Olive Drab",
                "Plum Purple",
                "Teal",
                "Burnt Sienna",
                "Gold",
                "Iridescent",
                "Pewter",
                "Raspberry"
        };
        return colors[new Random().nextInt(colors.length)];
    }

    private static BigDecimal generateRandomPrice() {

        return BigDecimal.valueOf(new Random().nextInt(27500) + 2501);
    }

    public static BikeToBuyDTO buildDefault() {
        return BikeToBuyDTO.builder()
                .category(generateRandomCategory())
                .subcategory(generateRandomSubcategory())
                .serial(generateRandomSerial())
                .brand(generateRandomBrand())
                .model(generateRandomModel())
                .year(generateRandomYear())
                .color(generateRandomColor())
                .price(generateRandomPrice())
                .build();
    }

    private static void addFieldToMap(Map<String, String> map, String fieldName, String value) {
        if (value != null) {
            map.put(fieldName, value);
        }
    }

   /* public Map<String, String> asMap() {
        Map<String, String> result = new HashMap<>();
        addFieldToMap(result, "category", category);
        addFieldToMap(result, "subcategory", subcategory);
        addFieldToMap(result, "serial", serial);
        addFieldToMap(result, "brand", brand);
        addFieldToMap(result, "model", model);
        addFieldToMap(result, "year", String.valueOf(year));
        addFieldToMap(result, "color", color);
        addFieldToMap(result, "price", String.valueOf(price));
        return result;
    }*/
}