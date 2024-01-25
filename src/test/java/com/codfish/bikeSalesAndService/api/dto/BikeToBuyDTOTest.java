package com.codfish.bikeSalesAndService.api.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BikeToBuyDTOTest {

    @Test
    void testGettersAndSetters() {
        BikeToBuyDTO bike = new BikeToBuyDTO();
        bike.setBikeToBuyId(1);
        bike.setCategory("Mountain Bike");
        bike.setSubcategory("Cross Country");
        bike.setSerial("SN1234");
        bike.setBrand("Trek");
        bike.setModel("X-Caliber");
        bike.setYear(2021);
        bike.setColor("Red");
        bike.setPrice(BigDecimal.valueOf(999.99));

        assertEquals(1, bike.getBikeToBuyId());
        assertEquals("Mountain Bike", bike.getCategory());
        assertEquals("Cross Country", bike.getSubcategory());
        assertEquals("SN1234", bike.getSerial());
        assertEquals("Trek", bike.getBrand());
        assertEquals("X-Caliber", bike.getModel());
        assertEquals(2021, bike.getYear());
        assertEquals("Red", bike.getColor());
        assertEquals(BigDecimal.valueOf(999.99), bike.getPrice());
    }

    @Test
    void testBuilder() {
        BikeToBuyDTO bike = BikeToBuyDTO.builder()
                .bikeToBuyId(1)
                .category("Mountain Bike")
                .subcategory("Cross Country")
                .serial("SN1234")
                .brand("Trek")
                .model("X-Caliber")
                .year(2021)
                .color("Red")
                .price(BigDecimal.valueOf(999.99))
                .build();

        assertNotNull(bike);
        assertEquals(1, bike.getBikeToBuyId());

    }

    @Test
    void testBuildDefault() {
        BikeToBuyDTO defaultBike = BikeToBuyDTO.buildDefault();
        assertNotNull(defaultBike);
        assertNotNull(defaultBike.getCategory());
        assertNotNull(defaultBike.getSubcategory());
        assertNotNull(defaultBike.getSerial());
        assertNotNull(defaultBike.getBrand());
        assertNotNull(defaultBike.getModel());
        assertNotNull(defaultBike.getYear());
        assertNotNull(defaultBike.getColor());
        assertNotNull(defaultBike.getPrice());
        assertTrue(defaultBike.getPrice().compareTo(BigDecimal.ZERO) > 0);
    }

}
