package com.codfish.bikeSalesAndService.infrastructure.database.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
class AddressEntityTest {

    @Test
    void testEntityConstructionAndGetters() {
        AddressEntity address = new AddressEntity();
        address.setAddressId(1);
        address.setCountry("Polska");
        address.setCity("Gdańsk");
        address.setPostalCode("80-000");
        address.setAddress("ul. Długa");
        address.setHouseNumber("10");
        address.setApartmentNumber("5");

        assertEquals(1, address.getAddressId());
        assertEquals("Polska", address.getCountry());
        assertEquals("Gdańsk", address.getCity());
        assertEquals("80-000", address.getPostalCode());
        assertEquals("ul. Długa", address.getAddress());
        assertEquals("10", address.getHouseNumber());
        assertEquals("5", address.getApartmentNumber());
    }

    @Test
    void testEqualsAndHashCode() {
        AddressEntity address1 = new AddressEntity(1, "Polska", "Gdańsk", "80-000", "ul. Długa", "10", "5", null);
        AddressEntity address2 = new AddressEntity(1, "Polska", "Gdańsk", "80-000", "ul. Długa", "10", "5", null);

        assertEquals(address1, address2);
        assertEquals(address1.hashCode(), address2.hashCode());
    }

    @Test
    void testToString() {
        AddressEntity address = new AddressEntity(1, "Polska", "Gdańsk", "80-000",
                "ul. Długa", "10", "5", null);
        String expectedString = "AddressEntity(addressId=1, country=Polska, city=Gdańsk, postalCode=80-000, address=ul. Długa)";
        assertTrue(address.toString().contains(expectedString));
    }

    @Test
    void testBuilder() {
        AddressEntity address = AddressEntity.builder()
                .addressId(1)
                .country("Polska")
                .city("Gdańsk")
                .postalCode("80-000")
                .address("ul. Długa")
                .houseNumber("10")
                .apartmentNumber("5")
                .build();

        assertNotNull(address);
        assertEquals(1, address.getAddressId());
        assertEquals("Polska", address.getCountry());
        assertEquals("Gdańsk", address.getCity());
        assertEquals("80-000", address.getPostalCode());
        assertEquals("ul. Długa", address.getAddress());
        assertEquals("10", address.getHouseNumber());
        assertEquals("5", address.getApartmentNumber());
    }

}