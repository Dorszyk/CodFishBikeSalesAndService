package com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa;

import com.codfish.bikeSalesAndService.infrastructure.database.entity.BikeToBuyEntity;
import com.codfish.bikeSalesAndService.integration.configuration.PersistenceContainerTestConfiguration;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static com.codfish.bikeSalesAndService.util.EntityFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class BikeToBuyJpaRepositoryTest {

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