package com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper;

import com.codfish.bikeSalesAndService.domain.BikeHistory;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.BikeServiceRequestEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.PersonRepairingEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.ServiceEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.ServicePersonEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("local")
class BikeServiceRequestEntityMapperTest {

    @Autowired
    private BikeServiceRequestEntityMapper bikeServiceRequestEntityMapper;

    @Test
    void shouldMapTechnicianCommentFromServicePersonEntities() {
        // given
        PersonRepairingEntity person = PersonRepairingEntity.builder().codeNameSurname("T1").build();

        ServicePersonEntity sp1 = ServicePersonEntity.builder()
                .servicePersonId(1)
                .comment("Comment 1")
                .service(ServiceEntity.builder().serviceCode("S1").build())
                .personRepairing(person)
                .build();
        ServicePersonEntity sp2 = ServicePersonEntity.builder()
                .servicePersonId(2)
                .comment("Comment 2")
                .service(ServiceEntity.builder().serviceCode("S2").build())
                .personRepairing(person)
                .build();
        ServicePersonEntity sp3 = ServicePersonEntity.builder()
                .servicePersonId(3)
                .comment(null)
                .service(ServiceEntity.builder().serviceCode("S3").build())
                .personRepairing(person)
                .build();
        ServicePersonEntity sp4 = ServicePersonEntity.builder()
                .servicePersonId(4)
                .comment("Comment 1") // duplicate comment but different ID
                .service(ServiceEntity.builder().serviceCode("S4").build())
                .personRepairing(person)
                .build();
        ServicePersonEntity sp5 = ServicePersonEntity.builder()
                .servicePersonId(5)
                .comment("No comment")
                .service(ServiceEntity.builder().serviceCode("S5").build())
                .personRepairing(person)
                .build();

        BikeServiceRequestEntity entity = BikeServiceRequestEntity.builder()
                .bikeServiceRequestNumber("REQ1")
                .servicePerson(Set.of(sp1, sp2, sp3, sp4, sp5))
                .servicePart(Set.of())
                .build();

        // when
        BikeHistory.BikeServiceRequest result = bikeServiceRequestEntityMapper.mapToBikeHistoryRequest(entity);

        // then
        assertNotNull(result);
        // Comments should be joined with "; ", distinct and non-null. 
        // Order might vary because of Set, but we expect "Comment 1; Comment 2" or "Comment 2; Comment 1"
        String comment = result.getTechnicianComment();
        if (comment.equals("Comment 1; Comment 2")) {
            assertEquals("Comment 1; Comment 2", comment);
        } else {
            assertEquals("Comment 2; Comment 1", comment);
        }
    }
}
