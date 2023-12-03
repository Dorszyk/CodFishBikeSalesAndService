package com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa;

import com.codfish.bikeSalesAndService.infrastructure.database.entity.BikeToBuyEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.InvoiceEntity;
import com.codfish.bikeSalesAndService.integration.configuration.PersistenceContainerTestConfiguration;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.codfish.bikeSalesAndService.util.EntityFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class BikeToBuyJpaRepositoryTest {

    @MockBean
    private EntityManager entityManager;

    @InjectMocks
    private BikeToBuyJpaRepository repository;

    @Mock
    private BikeToBuyJpaRepository bikeToBuyJpaRepository;

    @Test
    void thatCarCanBeSavedCorrectly() {
        // given
        var bikes = List.of(someBike1(), someBike2(), someBike3());
        bikeToBuyJpaRepository.saveAllAndFlush(bikes);

        // when
        List<BikeToBuyEntity> availableBikes = bikeToBuyJpaRepository.findAvailableBike();

        // then
        assertThat(availableBikes).hasSize(21);
    }

}