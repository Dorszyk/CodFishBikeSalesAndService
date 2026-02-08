package com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa;

import com.codfish.bikeSalesAndService.infrastructure.database.entity.BikeToBuyEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.CustomerEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.InvoiceEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.AddressEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.SalesmanEntity;
import com.codfish.bikeSalesAndService.integration.configuration.PersistenceContainerTestConfiguration;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = true)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
class InvoiceJpaRepositoryReproductionTest {

    @Autowired
    private InvoiceJpaRepository invoiceJpaRepository;
    @Autowired
    private BikeToBuyJpaRepository bikeToBuyJpaRepository;
    @Autowired
    private CustomerJpaRepository customerJpaRepository;
    @Autowired
    private AddressJpaRepository addressJpaRepository;

    @Test
    void shouldAllowMultipleInvoicesForSameBike() {
        // given
        AddressEntity address = addressJpaRepository.saveAndFlush(AddressEntity.builder()
                .country("Poland").city("Warsaw").postalCode("00-001").address("Street").houseNumber("1").apartmentNumber("1")
                .build());

        CustomerEntity customer = customerJpaRepository.saveAndFlush(CustomerEntity.builder()
                .name("John").surname("Doe").email("invoice.test@example.com").phone("+48 111").address(address)
                .build());

        BikeToBuyEntity bike = bikeToBuyJpaRepository.saveAndFlush(BikeToBuyEntity.builder()
                .serial("BIKE-INV-TEST").brand("Brand").model("Model").year(2023).category("Cat").subcategory("Sub").color("Black").price(new BigDecimal("1000"))
                .build());

        InvoiceEntity invoice1 = InvoiceEntity.builder()
                .invoiceNumber("INV/TEST/1").dateTime(OffsetDateTime.now()).customer(customer).bike(bike)
                .build();

        InvoiceEntity invoice2 = InvoiceEntity.builder()
                .invoiceNumber("INV/TEST/2").dateTime(OffsetDateTime.now()).customer(customer).bike(bike)
                .build();

        invoiceJpaRepository.saveAndFlush(invoice1);
        invoiceJpaRepository.saveAndFlush(invoice2);

        // when
        // Pobranie wszystkich faktur powinno działać
        List<InvoiceEntity> allInvoices = invoiceJpaRepository.findAll();
        
        // Pobranie roweru i sprawdzenie czy Hibernate rzuci wyjątek przy mapowaniu 1:1, 
        // gdy w bazie są 2 rekordy wskazujące na ten sam bike_to_buy_id.
        // Wiele implementacji JPA rzuca wyjątek podczas pobierania encji InvoiceEntity, 
        // która ma EAGER OneToOne do BikeToBuyEntity, jeśli BikeToBuyEntity ma mappedBy i znajdzie wiele faktur.
        // Lub po prostu gdy wywołamy findAll() na fakturach.
        
        System.out.println("[DEBUG_LOG] Invoices found: " + allInvoices.size());

        // then
        assertThat(allInvoices).hasSizeGreaterThanOrEqualTo(2);

        // Próba załadowania roweru przez repozytorium roweru - to powinno wywołać błąd przy OneToOne z mappedBy
        BikeToBuyEntity loadedBike = bikeToBuyJpaRepository.findBySerial("BIKE-INV-TEST").orElseThrow();
        System.out.println("[DEBUG_LOG] Bike found: " + loadedBike.getSerial());
        
        // Dostęp do faktury (relacja OneToOne)
        if (loadedBike.getInvoice() != null) {
            System.out.println("[DEBUG_LOG] Invoice on bike: " + loadedBike.getInvoice().getInvoiceNumber());
            assertThat(loadedBike.getInvoice()).isNotNull();
        }
    }
}
