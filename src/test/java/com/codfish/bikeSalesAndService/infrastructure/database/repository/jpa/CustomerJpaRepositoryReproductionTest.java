package com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa;

import com.codfish.bikeSalesAndService.infrastructure.database.entity.AddressEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.CustomerEntity;
import com.codfish.bikeSalesAndService.integration.configuration.PersistenceContainerTestConfiguration;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CustomerJpaRepositoryReproductionTest {

    private final CustomerJpaRepository customerJpaRepository;
    private final AddressJpaRepository addressJpaRepository;

    @Test
    void shouldAllowMultipleCustomersToShareSameAddress() {
        // given
        AddressEntity address = addressJpaRepository.saveAndFlush(AddressEntity.builder()
                .country("Poland")
                .city("Warsaw")
                .postalCode("00-001")
                .address("Common Street")
                .houseNumber("10")
                .apartmentNumber("1")
                .build());

        CustomerEntity customer1 = CustomerEntity.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .phone("+48 111")
                .address(address)
                .build();

        CustomerEntity customer2 = CustomerEntity.builder()
                .name("Jane")
                .surname("Smith")
                .email("jane.smith@example.com")
                .phone("+48 222")
                .address(address)
                .build();

        customerJpaRepository.saveAndFlush(customer1);
        customerJpaRepository.saveAndFlush(customer2);

        // when
        List<CustomerEntity> available = customerJpaRepository.findAvailable();

        // then
        assertThat(available)
                .extracting(CustomerEntity::getEmail)
                .contains("john.doe@example.com", "jane.smith@example.com");
        
        assertThat(customer1.getAddress().getAddressId()).isEqualTo(customer2.getAddress().getAddressId());
    }
}
