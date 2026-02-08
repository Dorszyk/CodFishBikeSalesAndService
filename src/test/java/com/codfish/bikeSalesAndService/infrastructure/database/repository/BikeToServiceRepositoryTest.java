package com.codfish.bikeSalesAndService.infrastructure.database.repository;

import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.BikeToServiceEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.BikeToServiceJpaRepository;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper.BikeToServiceEntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BikeToServiceRepositoryTest {

    @Mock
    private BikeToServiceJpaRepository bikeToServiceJpaRepository;

    @Mock
    private BikeToServiceEntityMapper bikeToServiceEntityMapper;

    @InjectMocks
    private BikeToServiceRepository bikeToServiceRepository;

    @Test
    void shouldThrowNotFoundExceptionWhenBikeNotFoundBySerial() {
        // given
        String bikeSerial = "nonExistentSerial";
        when(bikeToServiceJpaRepository.findBySerial(anyString())).thenReturn(null);

        // when, then
        assertThrows(NotFoundException.class, () -> bikeToServiceRepository.findBikeHistoryBySerial(bikeSerial));
    }
}
