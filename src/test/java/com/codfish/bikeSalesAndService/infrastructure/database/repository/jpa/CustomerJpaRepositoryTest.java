package com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa;

import com.codfish.bikeSalesAndService.infrastructure.database.entity.AddressEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.BikeServiceRequestEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.BikeToServiceEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.CustomerEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.InvoiceEntity;
import com.codfish.bikeSalesAndService.integration.configuration.PersistenceContainerTestConfiguration;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CustomerJpaRepositoryTest {

    private final CustomerJpaRepository customerJpaRepository;
    private final InvoiceJpaRepository invoiceJpaRepository;
    private final BikeServiceRequestJpaRepository bikeServiceRequestJpaRepository;
    private final AddressJpaRepository addressJpaRepository;
    private final BikeToServiceJpaRepository bikeToServiceJpaRepository;

    @Test
    void findAvailableShouldReturnCustomerWithOnlyInvoice() {
        // given
        AddressEntity address = addressJpaRepository.saveAndFlush(AddressEntity.builder()
                .country("Poland")
                .city("Warsaw")
                .postalCode("00-001")
                .address("Street 1")
                .houseNumber("10")
                .apartmentNumber("1")
                .build());

        CustomerEntity customer = customerJpaRepository.saveAndFlush(CustomerEntity.builder()
                .name("John")
                .surname("Doe")
                .email("john@example.com")
                .phone("+48 123 456 789")
                .address(address)
                .build());

        InvoiceEntity invoice = invoiceJpaRepository.saveAndFlush(InvoiceEntity.builder()
                .invoiceNumber("INV/1")
                .dateTime(OffsetDateTime.now())
                .customer(customer)
                .build());

        // when
        List<CustomerEntity> available = customerJpaRepository.findAvailable();

        // then
        assertThat(available).contains(customer);
    }

    @Test
    void findAvailableShouldReturnCustomerWithOnlyServiceRequest() {
        // given
        AddressEntity address = addressJpaRepository.saveAndFlush(AddressEntity.builder()
                .country("Poland")
                .city("Krakow")
                .postalCode("30-001")
                .address("Street 2")
                .houseNumber("20")
                .apartmentNumber("2")
                .build());

        CustomerEntity customer = customerJpaRepository.saveAndFlush(CustomerEntity.builder()
                .name("Jane")
                .surname("Doe")
                .email("jane@example.com")
                .phone("+48 111 222 333")
                .address(address)
                .build());

        BikeToServiceEntity bike = bikeToServiceJpaRepository.saveAndFlush(BikeToServiceEntity.builder()
                .serial("SN1")
                .brand("Brand1")
                .model("Model1")
                .year(2023)
                .build());

        BikeServiceRequestEntity request = bikeServiceRequestJpaRepository.saveAndFlush(BikeServiceRequestEntity.builder()
                .bikeServiceRequestNumber("REQ/1")
                .receivedDateTime(OffsetDateTime.now())
                .customer(customer)
                .bike(bike)
                .build());

        // when
        List<CustomerEntity> available = customerJpaRepository.findAvailable();

        // then
        assertThat(available).contains(customer);
    }

    @Test
    void findAvailableShouldReturnCustomerWithBothInvoiceAndServiceRequest() {
        // given
        AddressEntity address = addressJpaRepository.saveAndFlush(AddressEntity.builder()
                .country("Poland")
                .city("Gdansk")
                .postalCode("80-001")
                .address("Street 3")
                .houseNumber("30")
                .apartmentNumber("3")
                .build());

        CustomerEntity customer = customerJpaRepository.saveAndFlush(CustomerEntity.builder()
                .name("Bob")
                .surname("Smith")
                .email("bob@example.com")
                .phone("+48 999 888 777")
                .address(address)
                .build());

        invoiceJpaRepository.saveAndFlush(InvoiceEntity.builder()
                .invoiceNumber("INV/2")
                .dateTime(OffsetDateTime.now())
                .customer(customer)
                .build());

        BikeToServiceEntity bike = bikeToServiceJpaRepository.saveAndFlush(BikeToServiceEntity.builder()
                .serial("SN2")
                .brand("Brand2")
                .model("Model2")
                .year(2023)
                .build());

        bikeServiceRequestJpaRepository.saveAndFlush(BikeServiceRequestEntity.builder()
                .bikeServiceRequestNumber("REQ/2")
                .receivedDateTime(OffsetDateTime.now())
                .customer(customer)
                .bike(bike)
                .build());

        // when
        List<CustomerEntity> available = customerJpaRepository.findAvailable();

        // then
        assertThat(available).contains(customer);
    }
}
