package com.codfish.bikeSalesAndService.infrastructure.database.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class BikeToBuyEntityTest {

    @Test
    void testEntitySettersAndGetters() {
        BikeToBuyEntity bike = new BikeToBuyEntity();
        bike.setBikeToBuyId(1);
        bike.setCategory("Mountain");
        bike.setSubcategory("Cross-country");
        bike.setSerial("123ABC");
        bike.setBrand("Trek");
        bike.setModel("X-Caliber");
        bike.setYear(2021);
        bike.setColor("Red");
        bike.setPrice(BigDecimal.valueOf(999.99));

        assertEquals(1, bike.getBikeToBuyId());
        assertEquals("Mountain", bike.getCategory());
        assertEquals("Cross-country", bike.getSubcategory());
        assertEquals("123ABC", bike.getSerial());
        assertEquals("Trek", bike.getBrand());
        assertEquals("X-Caliber", bike.getModel());
        assertEquals(2021, bike.getYear());
        assertEquals("Red", bike.getColor());
        assertEquals(BigDecimal.valueOf(999.99), bike.getPrice());
    }

    @Test
    void testEqualsAndHashCode() {
        BikeToBuyEntity bike1 = new BikeToBuyEntity(1, "Mountain", "Cross-country", "123ABC", "Trek", "X-Caliber", 2021, "Red", BigDecimal.valueOf(999.99), null);
        BikeToBuyEntity bike2 = new BikeToBuyEntity(1, "Mountain", "Cross-country", "123ABC", "Trek", "X-Caliber", 2021, "Red", BigDecimal.valueOf(999.99), null);

        assertEquals(bike1, bike2);
        assertEquals(bike1.hashCode(), bike2.hashCode());
    }

    @Test
    void testToString() {
        BikeToBuyEntity bike = new BikeToBuyEntity(1, "Mountain", "Cross-country", "123ABC", "Trek", "X-Caliber", 2021, "Red", BigDecimal.valueOf(999.99), null);
        String expectedString = "123ABC";
        assertEquals(expectedString, bike.toString());
    }

    @Test
    void testBuilder() {
        BikeToBuyEntity bike = BikeToBuyEntity.builder()
                .bikeToBuyId(1)
                .category("Mountain")
                .subcategory("Cross-country")
                .serial("123ABC")
                .brand("Trek")
                .model("X-Caliber")
                .year(2021)
                .color("Red")
                .price(BigDecimal.valueOf(999.99))
                .build();

        assertNotNull(bike);
        assertEquals("Mountain", bike.getCategory());
        assertEquals("Cross-country", bike.getSubcategory());
        assertEquals("123ABC", bike.getSerial());
        assertEquals("Trek", bike.getBrand());
        assertEquals("X-Caliber", bike.getModel());
        assertEquals(2021, bike.getYear());
        assertEquals("Red", bike.getColor());
        assertEquals(BigDecimal.valueOf(999.99), bike.getPrice());
    }

}